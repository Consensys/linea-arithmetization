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

package net.consensys.linea.zktracer.module.hub.section.calls;

import net.consensys.linea.zktracer.module.hub.AccountSnapshot;
import net.consensys.linea.zktracer.module.hub.Hub;
import net.consensys.linea.zktracer.module.hub.defer.NextContextDefer;
import net.consensys.linea.zktracer.module.hub.defer.PostExecDefer;
import net.consensys.linea.zktracer.module.hub.defer.PostTransactionDefer;
import net.consensys.linea.zktracer.module.hub.fragment.ContextFragment;
import net.consensys.linea.zktracer.module.hub.fragment.DomSubStampsSubFragment;
import net.consensys.linea.zktracer.module.hub.fragment.account.AccountFragment;
import net.consensys.linea.zktracer.module.hub.fragment.imc.ImcFragment;
import net.consensys.linea.zktracer.module.hub.fragment.scenario.ScenarioFragment;
import net.consensys.linea.zktracer.module.hub.section.TraceSection;
import net.consensys.linea.zktracer.runtime.callstack.CallFrame;
import org.apache.tuweni.bytes.Bytes;
import org.hyperledger.besu.datatypes.Address;
import org.hyperledger.besu.datatypes.Transaction;
import org.hyperledger.besu.evm.account.Account;
import org.hyperledger.besu.evm.frame.MessageFrame;
import org.hyperledger.besu.evm.operation.Operation;
import org.hyperledger.besu.evm.worldstate.WorldView;

public class SmartContractCallSection extends TraceSection
    implements PostTransactionDefer, PostExecDefer, NextContextDefer {
  private final Bytes rawCalledAddress;
  private final CallFrame callerCallFrame;
  private final int calledCallFrameId;
  private final AccountSnapshot preCallCallerAccountSnapshot;
  private final AccountSnapshot preCallCalleeAccountSnapshot;

  private AccountSnapshot inCallCallerAccountSnapshot;
  private AccountSnapshot inCallCalleeAccountSnapshot;

  private AccountSnapshot postCallCallerAccountSnapshot;
  private AccountSnapshot postCallCalleeAccountSnapshot;

  private final ScenarioFragment scenarioFragment;
  private final ImcFragment imcFragment;

  public SmartContractCallSection(
      Hub hub,
      AccountSnapshot preCallCallerAccountSnapshot,
      AccountSnapshot preCallCalleeAccountSnapshot,
      Bytes rawCalledAddress,
      ImcFragment imcFragment) {
    super(hub);
    this.rawCalledAddress = rawCalledAddress;
    this.callerCallFrame = hub.currentFrame();
    this.calledCallFrameId = hub.callStack().futureId();
    this.preCallCallerAccountSnapshot = preCallCallerAccountSnapshot;
    this.preCallCalleeAccountSnapshot = preCallCalleeAccountSnapshot;
    this.imcFragment = imcFragment;
    this.scenarioFragment =
        ScenarioFragment.forSmartContractCallSection(
            hub, calledCallFrameId, this.callerCallFrame.id());

    this.addStack(hub);

    hub.defers().postExec(this);
    hub.defers().nextContext(this, hub.currentFrame().id());
    hub.defers().postTx(this);
  }

  @Override
  public void runPostExec(Hub hub, MessageFrame frame, Operation.OperationResult operationResult) {
    final Address callerAddress = preCallCallerAccountSnapshot.address();
    final Account callerAccount = frame.getWorldUpdater().get(callerAddress);
    final Address calledAddress = preCallCalleeAccountSnapshot.address();
    final Account calledAccount = frame.getWorldUpdater().get(calledAddress);

    this.postCallCallerAccountSnapshot =
        AccountSnapshot.fromAccount(
            callerAccount,
            frame.isAddressWarm(callerAddress),
            hub.transients().conflation().deploymentInfo().number(callerAddress),
            hub.transients().conflation().deploymentInfo().isDeploying(callerAddress));
    this.postCallCalleeAccountSnapshot =
        AccountSnapshot.fromAccount(
            calledAccount,
            frame.isAddressWarm(calledAddress),
            hub.transients().conflation().deploymentInfo().number(calledAddress),
            hub.transients().conflation().deploymentInfo().isDeploying(calledAddress));
  }

  @Override
  public void runNextContext(Hub hub, MessageFrame frame) {
    final Address callerAddress = preCallCallerAccountSnapshot.address();
    final Account callerAccount = frame.getWorldUpdater().get(callerAddress);
    final Address calledAddress = preCallCalleeAccountSnapshot.address();
    final Account calledAccount = frame.getWorldUpdater().get(calledAddress);

    this.inCallCallerAccountSnapshot =
        AccountSnapshot.fromAccount(
            callerAccount,
            frame.isAddressWarm(callerAddress),
            hub.transients().conflation().deploymentInfo().number(callerAddress),
            hub.transients().conflation().deploymentInfo().isDeploying(callerAddress));
    this.inCallCalleeAccountSnapshot =
        AccountSnapshot.fromAccount(
            calledAccount,
            frame.isAddressWarm(calledAddress),
            hub.transients().conflation().deploymentInfo().number(calledAddress),
            hub.transients().conflation().deploymentInfo().isDeploying(calledAddress));
  }

  @Override
  public void runPostTx(Hub hub, WorldView state, Transaction tx, boolean isSuccessful) {
    final AccountFragment.AccountFragmentFactory accountFragmentFactory =
        hub.factories().accountFragment();
    final CallFrame calledCallFrame = hub.callStack().getById(this.calledCallFrameId);
    this.scenarioFragment.runPostTx(hub, state, tx, isSuccessful);

    DomSubStampsSubFragment firstCallerDoingDomSubStamps =
        DomSubStampsSubFragment.standardDomSubStamps(hub, 0);

    DomSubStampsSubFragment firstCalleeDoingDomSubStamps =
        DomSubStampsSubFragment.standardDomSubStamps(hub, 1);

    this.addFragmentsWithoutStack(
        this.scenarioFragment,
        ContextFragment.readCurrentContextData(hub),
        this.imcFragment,
        accountFragmentFactory.make(
            this.preCallCallerAccountSnapshot,
            this.inCallCallerAccountSnapshot,
            firstCallerDoingDomSubStamps),
        accountFragmentFactory.makeWithTrm(
            this.preCallCalleeAccountSnapshot,
            this.inCallCalleeAccountSnapshot,
            this.rawCalledAddress,
            firstCalleeDoingDomSubStamps));

    // caller: WILL_REVERT
    // child:  FAILURE
    // TODO: get the right account snapshots
    if (callerCallFrame.willRevert() && calledCallFrame.selfReverts()) {
      this.addFragmentsWithoutStack(
          accountFragmentFactory.make(
              this.inCallCallerAccountSnapshot,
              this.preCallCallerAccountSnapshot,
              DomSubStampsSubFragment.revertsWithChildDomSubStamps(hub, calledCallFrame, 2)),
          accountFragmentFactory.make(
              this.inCallCalleeAccountSnapshot,
              this.postCallCalleeAccountSnapshot,
              DomSubStampsSubFragment.revertsWithChildDomSubStamps(hub, calledCallFrame, 3)),
          accountFragmentFactory.make(
              this.postCallCalleeAccountSnapshot,
              this.preCallCalleeAccountSnapshot,
              DomSubStampsSubFragment.revertWithCurrentDomSubStamps(hub, 4)));
    }

    // caller: WILL_REVERT
    // child:  SUCCESS
    // TODO: get the right account snapshots
    if (callerCallFrame.willRevert() && !calledCallFrame.selfReverts()) {
      this.addFragmentsWithoutStack(
          accountFragmentFactory.make(
              this.inCallCallerAccountSnapshot,
              this.preCallCallerAccountSnapshot,
              DomSubStampsSubFragment.revertWithCurrentDomSubStamps(hub, 2)),
          accountFragmentFactory.make(
              this.inCallCalleeAccountSnapshot,
              this.preCallCalleeAccountSnapshot,
              DomSubStampsSubFragment.revertWithCurrentDomSubStamps(hub, 3)));
    }

    // caller: WONT_REVERT
    // child:  FAILURE
    // TODO: get the right account snapshots
    if (!callerCallFrame.willRevert() && calledCallFrame.selfReverts()) {
      if (calledCallFrame.selfReverts()) {
        this.addFragmentsWithoutStack(
            accountFragmentFactory.make(
                this.inCallCallerAccountSnapshot,
                this.postCallCallerAccountSnapshot,
                DomSubStampsSubFragment.revertsWithChildDomSubStamps(hub, calledCallFrame, 2)),
            accountFragmentFactory.make(
                this.inCallCalleeAccountSnapshot,
                this.postCallCalleeAccountSnapshot,
                DomSubStampsSubFragment.revertsWithChildDomSubStamps(hub, calledCallFrame, 3)));
      }
    }

    this.addFragmentsWithoutStack(ContextFragment.enterContext(hub, calledCallFrame));
  }
}
