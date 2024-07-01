/*
 * Copyright Consensys Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package net.consensys.linea.zktracer.module.hub.section;

import net.consensys.linea.zktracer.module.hub.AccountSnapshot;
import net.consensys.linea.zktracer.module.hub.Hub;
import net.consensys.linea.zktracer.module.hub.defer.NextContextDefer;
import net.consensys.linea.zktracer.module.hub.defer.PostExecDefer;
import net.consensys.linea.zktracer.module.hub.defer.PostTransactionDefer;
import net.consensys.linea.zktracer.module.hub.defer.ReEnterContextDefer;
import net.consensys.linea.zktracer.module.hub.fragment.ContextFragment;
import net.consensys.linea.zktracer.module.hub.fragment.DomSubStampsSubFragment;
import net.consensys.linea.zktracer.module.hub.fragment.account.AccountFragment;
import net.consensys.linea.zktracer.module.hub.fragment.imc.ImcFragment;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.MxpCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.opcodes.CreateOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.scenario.ScenarioFragment;
import net.consensys.linea.zktracer.module.hub.signals.AbortingConditions;
import net.consensys.linea.zktracer.module.hub.signals.Exceptions;
import net.consensys.linea.zktracer.module.hub.signals.FailureConditions;
import net.consensys.linea.zktracer.opcode.OpCode;
import net.consensys.linea.zktracer.opcode.gas.GasConstants;
import net.consensys.linea.zktracer.opcode.gas.projector.GasProjection;
import org.hyperledger.besu.datatypes.Address;
import org.hyperledger.besu.datatypes.Transaction;
import org.hyperledger.besu.evm.frame.MessageFrame;
import org.hyperledger.besu.evm.operation.Operation;
import org.hyperledger.besu.evm.worldstate.WorldView;

public class CreateSection extends TraceSection
    implements PostExecDefer, NextContextDefer, PostTransactionDefer, ReEnterContextDefer {
  private final int creatorContextId;
  private final boolean emptyInitCode;
  private final OpCode opCode;
  private final long initialGas;
  private final AbortingConditions aborts;
  private final Exceptions exceptions;
  private final FailureConditions failures;
  private final ScenarioFragment scenarioFragment;

  // Just before create
  private final AccountSnapshot oldCreatorSnapshot;
  private final AccountSnapshot oldCreatedSnapshot;

  // Just after create but before entering child frame
  private AccountSnapshot midCreatorSnapshot;
  private AccountSnapshot midCreatedSnapshot;

  // After return from child-context
  private AccountSnapshot newCreatorSnapshot;
  private AccountSnapshot newCreatedSnapshot;

  private boolean createSuccessful;

  /* true if the putatively created account already has code **/
  private boolean targetHasCode() {
    return !oldCreatedSnapshot.code().isEmpty();
  }

  public CreateSection(
      Hub hub, AccountSnapshot oldCreatorSnapshot, AccountSnapshot oldCreatedSnapshot) {
    super(hub);
    this.creatorContextId = hub.currentFrame().id();
    this.opCode = hub.opCode();
    this.emptyInitCode = hub.transients().op().callDataSegment().isEmpty();
    this.initialGas = hub.messageFrame().getRemainingGas();
    this.aborts = hub.pch().abortingConditions().snapshot();
    this.exceptions = hub.pch().exceptions().snapshot();
    this.failures = hub.pch().failureConditions().snapshot();

    this.oldCreatorSnapshot = oldCreatorSnapshot;
    this.oldCreatedSnapshot = oldCreatedSnapshot;

    this.scenarioFragment = ScenarioFragment.forCreate(hub, this.targetHasCode());

    this.addStack(hub);

    // Will be traced in one (and only one!) of these depending on the hubSuccess of
    // the operation
    hub.defers().postExec(this);
    hub.defers().nextContext(this, hub.currentFrame().id());
    hub.defers().reEntry(this);
  }

  @Override
  public void runPostExec(Hub hub, MessageFrame frame, Operation.OperationResult operationResult) {
    Address creatorAddress = oldCreatorSnapshot.address();
    this.midCreatorSnapshot =
        AccountSnapshot.fromAccount(
            frame.getWorldUpdater().get(creatorAddress),
            true,
            hub.transients().conflation().deploymentInfo().number(creatorAddress),
            hub.transients().conflation().deploymentInfo().isDeploying(creatorAddress));

    Address createdAddress = oldCreatedSnapshot.address();
    this.midCreatedSnapshot =
        AccountSnapshot.fromAccount(
            frame.getWorldUpdater().get(createdAddress),
            true,
            hub.transients().conflation().deploymentInfo().number(createdAddress),
            hub.transients().conflation().deploymentInfo().isDeploying(createdAddress));
    // Pre-emptively set new* snapshots in case we never enter the child frame.
    // Will be overwritten if we enter the child frame and runNextContext is explicitly called by
    // the defer registry.
    this.runAtReEnter(hub, frame);
  }

  @Override
  public void runNextContext(Hub hub, MessageFrame frame) {}

  @Override
  public void runAtReEnter(Hub hub, MessageFrame frame) {
    this.createSuccessful = !frame.getStackItem(0).isZero();

    Address creatorAddress = oldCreatorSnapshot.address();
    this.newCreatorSnapshot =
        AccountSnapshot.fromAccount(
            frame.getWorldUpdater().get(creatorAddress),
            true,
            hub.transients().conflation().deploymentInfo().number(creatorAddress),
            hub.transients().conflation().deploymentInfo().isDeploying(creatorAddress));

    Address createdAddress = oldCreatedSnapshot.address();
    this.newCreatedSnapshot =
        AccountSnapshot.fromAccount(
            frame.getWorldUpdater().get(createdAddress),
            true,
            hub.transients().conflation().deploymentInfo().number(createdAddress),
            hub.transients().conflation().deploymentInfo().isDeploying(createdAddress));
  }

  @Override
  public void runPostTx(Hub hub, WorldView state, Transaction tx, boolean isSuccessful) {
    final AccountFragment.AccountFragmentFactory accountFragmentFactory =
        hub.factories().accountFragment();
    final boolean creatorWillRevert = hub.callStack().getById(this.creatorContextId).hasReverted();
    final GasProjection projection = Hub.GAS_PROJECTOR.of(hub.messageFrame(), hub.opCode());
    final long upfrontCost =
        projection.memoryExpansion() + projection.linearPerWord() + GasConstants.G_TX_CREATE.cost();

    final ImcFragment commonImcFragment =
        ImcFragment.empty(hub).callOob(new CreateOobCall()).callMxp(MxpCall.build(hub))
        //         .callStp(
        //             new StpCall(
        //                 this.opCode.byteValue(),
        //                 EWord.of(this.initialGas),
        //                 EWord.ZERO,
        //                 false,
        //                 oldCreatedSnapshot.isWarm(),
        //                 this.exceptions.outOfGasException(),
        //                 upfrontCost,
        //                 allButOneSixtyFourth(this.initialGas - upfrontCost),
        //                 0))
        ;

    this.scenarioFragment.runPostTx(hub, state, tx, isSuccessful);
    this.addFragmentsWithoutStack(scenarioFragment);
    if (this.exceptions.staticException()) {
      this.addFragmentsWithoutStack(
          ImcFragment.empty(hub),
          ContextFragment.readCurrentContextData(hub),
          ContextFragment.executionProvidesEmptyReturnData(hub));
    } else if (this.exceptions.memoryExpansion()) {
      this.addFragmentsWithoutStack(
          ImcFragment.empty(hub).callMxp(MxpCall.build(hub)),
          ContextFragment.executionProvidesEmptyReturnData(hub));
    } else if (this.exceptions.outOfGas()) {
      this.addFragmentsWithoutStack(
          commonImcFragment, ContextFragment.executionProvidesEmptyReturnData(hub));
    } else if (this.aborts.any()) {
      this.addFragmentsWithoutStack(
          commonImcFragment,
          ContextFragment.readCurrentContextData(hub),
          accountFragmentFactory.make(
              oldCreatorSnapshot,
              newCreatorSnapshot,
              DomSubStampsSubFragment.standardDomSubStamps(hub, 0)),
          ContextFragment.nonExecutionEmptyReturnData(hub));
    } else if (this.failures.any()) {
      if (creatorWillRevert) {
        this.addFragmentsWithoutStack(
            commonImcFragment,
            accountFragmentFactory.make(
                oldCreatorSnapshot,
                newCreatorSnapshot,
                DomSubStampsSubFragment.standardDomSubStamps(hub, 0)),
            accountFragmentFactory.make(
                oldCreatedSnapshot,
                newCreatedSnapshot,
                DomSubStampsSubFragment.standardDomSubStamps(hub, 1)),
            accountFragmentFactory.make(
                newCreatorSnapshot,
                oldCreatorSnapshot,
                DomSubStampsSubFragment.revertWithCurrentDomSubStamps(hub, 2)),
            accountFragmentFactory.make(
                newCreatedSnapshot,
                oldCreatedSnapshot,
                DomSubStampsSubFragment.revertWithCurrentDomSubStamps(hub, 3)),
            ContextFragment.nonExecutionEmptyReturnData(hub));
      } else {
        this.addFragmentsWithoutStack(
            commonImcFragment,
            accountFragmentFactory.make(
                oldCreatorSnapshot,
                newCreatorSnapshot,
                DomSubStampsSubFragment.standardDomSubStamps(hub, 0)),
            accountFragmentFactory.make(
                oldCreatedSnapshot,
                newCreatedSnapshot,
                DomSubStampsSubFragment.standardDomSubStamps(hub, 1)),
            ContextFragment.nonExecutionEmptyReturnData(hub));
      }
    } else {
      // entry into the CREATE operation
      if (this.emptyInitCode) {
        if (creatorWillRevert) {
          this.addFragmentsWithoutStack(
              commonImcFragment,
              accountFragmentFactory.make(
                  oldCreatorSnapshot,
                  newCreatorSnapshot,
                  DomSubStampsSubFragment.standardDomSubStamps(hub, 0)),
              accountFragmentFactory.make(
                  oldCreatedSnapshot,
                  newCreatedSnapshot,
                  DomSubStampsSubFragment.standardDomSubStamps(hub, 1)),
              accountFragmentFactory.make(
                  newCreatorSnapshot,
                  oldCreatorSnapshot,
                  DomSubStampsSubFragment.revertWithCurrentDomSubStamps(hub, 2)),
              accountFragmentFactory.make(
                  newCreatedSnapshot,
                  oldCreatedSnapshot,
                  DomSubStampsSubFragment.revertWithCurrentDomSubStamps(hub, 3)),
              ContextFragment.nonExecutionEmptyReturnData(hub));
        } else {
          this.addFragmentsWithoutStack(
              commonImcFragment,
              accountFragmentFactory.make(
                  oldCreatorSnapshot,
                  newCreatorSnapshot,
                  DomSubStampsSubFragment.standardDomSubStamps(hub, 0)),
              accountFragmentFactory.make(
                  oldCreatedSnapshot,
                  newCreatedSnapshot,
                  DomSubStampsSubFragment.standardDomSubStamps(hub, 1)),
              ContextFragment.nonExecutionEmptyReturnData(hub));
        }
      } else {
        // non empty code
        final int createeContextId = hub.callStack().futureId();
        if (this.createSuccessful) {
          if (creatorWillRevert) {
            this.addFragmentsWithoutStack(
                commonImcFragment,
                accountFragmentFactory.make(
                    oldCreatorSnapshot,
                    midCreatorSnapshot,
                    DomSubStampsSubFragment.standardDomSubStamps(hub, 0)),
                accountFragmentFactory.make(
                    oldCreatedSnapshot,
                    midCreatedSnapshot,
                    DomSubStampsSubFragment.standardDomSubStamps(hub, 1)),
                accountFragmentFactory.make(
                    midCreatorSnapshot,
                    oldCreatorSnapshot,
                    DomSubStampsSubFragment.revertWithCurrentDomSubStamps(hub, 2)),
                accountFragmentFactory.make(
                    midCreatedSnapshot,
                    oldCreatedSnapshot,
                    DomSubStampsSubFragment.revertWithCurrentDomSubStamps(hub, 3)),
                ContextFragment.initializeExecutionContext(hub));

          } else {
            this.addFragmentsWithoutStack(
                commonImcFragment,
                accountFragmentFactory.make(
                    oldCreatorSnapshot,
                    midCreatorSnapshot,
                    DomSubStampsSubFragment.standardDomSubStamps(hub, 0)),
                accountFragmentFactory.make(
                    oldCreatedSnapshot,
                    midCreatedSnapshot,
                    DomSubStampsSubFragment.standardDomSubStamps(hub, 1)),
                ContextFragment.initializeExecutionContext(hub));
          }
        } else {
          if (creatorWillRevert) {
            this.addFragmentsWithoutStack(
                commonImcFragment,
                accountFragmentFactory.make(
                    oldCreatorSnapshot,
                    midCreatorSnapshot,
                    DomSubStampsSubFragment.standardDomSubStamps(hub, 0)),
                accountFragmentFactory.make(
                    oldCreatedSnapshot,
                    midCreatedSnapshot,
                    DomSubStampsSubFragment.standardDomSubStamps(hub, 1)),
                accountFragmentFactory.make(
                    midCreatorSnapshot,
                    newCreatorSnapshot,
                    DomSubStampsSubFragment.revertsWithChildDomSubStamps(
                        hub, hub.callStack().getById(createeContextId), 2)),
                accountFragmentFactory.make(
                    midCreatedSnapshot,
                    newCreatedSnapshot,
                    DomSubStampsSubFragment.revertsWithChildDomSubStamps(
                        hub, hub.callStack().getById(createeContextId), 3)),
                accountFragmentFactory.make(
                    newCreatorSnapshot,
                    oldCreatorSnapshot,
                    DomSubStampsSubFragment.revertWithCurrentDomSubStamps(hub, 4)),
                accountFragmentFactory.make(
                    newCreatedSnapshot,
                    oldCreatedSnapshot,
                    DomSubStampsSubFragment.revertWithCurrentDomSubStamps(hub, 5)),
                ContextFragment.initializeExecutionContext(hub));
          } else {
            this.addFragmentsWithoutStack(
                commonImcFragment,
                accountFragmentFactory.make(
                    oldCreatorSnapshot,
                    midCreatorSnapshot,
                    DomSubStampsSubFragment.standardDomSubStamps(hub, 0)),
                accountFragmentFactory.make(
                    oldCreatedSnapshot,
                    midCreatedSnapshot,
                    DomSubStampsSubFragment.standardDomSubStamps(hub, 1)),
                accountFragmentFactory.make(
                    midCreatorSnapshot,
                    newCreatorSnapshot,
                    DomSubStampsSubFragment.revertsWithChildDomSubStamps(
                        hub, hub.callStack().getById(createeContextId), 2)),
                accountFragmentFactory.make(
                    midCreatedSnapshot,
                    newCreatedSnapshot,
                    DomSubStampsSubFragment.revertsWithChildDomSubStamps(
                        hub, hub.callStack().getById(createeContextId), 3)),
                ContextFragment.initializeExecutionContext(hub));
          }
        }
      }
    }
  }
}
