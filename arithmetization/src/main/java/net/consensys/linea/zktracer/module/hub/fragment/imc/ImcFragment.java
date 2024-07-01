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

package net.consensys.linea.zktracer.module.hub.fragment.imc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.consensys.linea.zktracer.module.hub.Hub;
import net.consensys.linea.zktracer.module.hub.Trace;
import net.consensys.linea.zktracer.module.hub.fragment.TraceFragment;
import net.consensys.linea.zktracer.module.hub.fragment.TraceSubFragment;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.MxpCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.StpCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.exp.ExpCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.exp.ExplogExpCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.mmu.MmuCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.OobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.opcodes.CallDataLoadOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.opcodes.CallOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.opcodes.DeploymentOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.opcodes.JumpOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.opcodes.JumpiOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.opcodes.SstoreOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.opcodes.XCallOobCall;
import net.consensys.linea.zktracer.opcode.OpCode;
import net.consensys.linea.zktracer.opcode.gas.GasConstants;
import net.consensys.linea.zktracer.types.EWord;
import net.consensys.linea.zktracer.types.TransactionProcessingMetadata;
import org.apache.tuweni.bytes.Bytes;
import org.hyperledger.besu.evm.account.Account;
import org.hyperledger.besu.evm.frame.MessageFrame;
import org.hyperledger.besu.evm.internal.Words;

/**
 * IMCFragments embed data required for Inter-Module Communication, i.e. data that are required to
 * correctly trigger other modules from the Hub.
 */
public class ImcFragment implements TraceFragment {
  /** the list of modules to trigger withing this fragment. */
  private final List<TraceSubFragment> moduleCalls = new ArrayList<>(5);

  private final Hub hub;

  private boolean expIsSet = false;
  private boolean modExpIsSet = false;
  private boolean oobIsSet = false;
  private boolean mxpIsSet = false;
  private boolean mmuIsSet = false;
  private boolean stpIsSet = false;

  private ImcFragment(final Hub hub) {
    this.hub = hub;
  }

  /**
   * Create an empty ImcFragment to be filled with specialized methods.
   *
   * @return an empty ImcFragment
   */
  public static ImcFragment empty(final Hub hub) {
    return new ImcFragment(hub);
  }

  /**
   * Create an ImcFragment to be used in the transaction initialization phase.
   *
   * @param hub the execution context
   * @return the ImcFragment for the TxInit phase
   */
  public static ImcFragment forTxInit(final Hub hub) {
    // isdeployment == false
    // non empty calldata
    final TransactionProcessingMetadata currentTx = hub.txStack().current();
    final boolean isMessageCallTransaction = currentTx.getBesuTransaction().getTo().isPresent();

    final Optional<Bytes> txData = currentTx.getBesuTransaction().getData();
    final boolean shouldCopyTxCallData =
        isMessageCallTransaction
            && txData.isPresent()
            && !txData.get().isEmpty()
            && currentTx.requiresEvmExecution();

    final ImcFragment emptyFragment = ImcFragment.empty(hub);

    return shouldCopyTxCallData ? emptyFragment.callMmu(MmuCall.txInit(hub)) : emptyFragment;
  }

  /**
   * Create an ImcFragment to be used when executing a *CALL.
   *
   * @param hub the execution context
   * @param callerAccount the caller account
   * @param calledAccount the (maybe non-existing) called account
   * @return the ImcFragment for the *CALL
   */
  public static ImcFragment forCall(
      Hub hub, Account callerAccount, Optional<Account> calledAccount) {
    final ImcFragment r = new ImcFragment(hub);

    if (hub.pch().signals().mxp()) {
      r.callMxp(MxpCall.build(hub));
    }

    if (hub.pch().signals().oob()) {
      switch (hub.opCode()) {
        case CALL, STATICCALL, DELEGATECALL, CALLCODE -> {
          if (hub.opCode().equals(OpCode.CALL) && hub.pch().exceptions().any()) {
            r.callOob(new XCallOobCall());
          } else {
            r.callOob(new CallOobCall());
          }
        }
        default -> throw new IllegalArgumentException("unexpected opcode for IMC/CALL");
      }
    }

    if (hub.pch().signals().stp()) {
      final long gas = Words.clampedToLong(hub.messageFrame().getStackItem(0));
      EWord value = EWord.ZERO;
      if (hub.opCode().isAnyOf(OpCode.CALL, OpCode.CALLCODE)) {
        value = EWord.of(hub.messageFrame().getStackItem(2));
      }

      final long stipend = value.isZero() ? 0 : GasConstants.G_CALL_STIPEND.cost();
      final long upfrontCost = Hub.GAS_PROJECTOR.of(hub.messageFrame(), hub.opCode()).total();

      // TODO: @Olivier get memory expansion gas
      long memoryExpansionGas = 0xdeadbeefL;
      StpCall stpCall = new StpCall(hub, memoryExpansionGas);

      r.callStp(stpCall);

      //              EWord.of(gas),
      //              value,
      //              calledAccount.isPresent(),
      //              calledAccount
      //                  .map(a -> hub.messageFrame().isAddressWarm(a.getAddress()))
      //                  .orElse(false),
      //              hub.pch().exceptions().outOfGasException(),
      //              upfrontCost,
      //              Math.max(
      //                  Words.unsignedMin(
      //                      allButOneSixtyFourth(hub.messageFrame().getRemainingGas() -
      // upfrontCost),
      //                      gas),
      //                  0),
      //              stipend)
      //    );
    }

    return r;
  }

  public static ImcFragment forOpcode(Hub hub, MessageFrame frame) {
    final ImcFragment r = new ImcFragment(hub);

    if (hub.pch().signals().mxp()) {
      r.callMxp(MxpCall.build(hub));
    }

    if (hub.pch().signals().exp()) {
      r.callExp(new ExplogExpCall());
    }

    if (hub.pch().signals().exp() && !hub.pch().exceptions().stackException()) {
      hub.exp().tracePreOpcode(frame);
    }

    if (hub.pch().signals().mmu()) {
      switch (hub.opCode()) {
        case SHA3 -> r.callMmu(MmuCall.sha3(hub));
        case CALLDATALOAD -> r.callMmu(MmuCall.callDataLoad(hub));
        case CALLDATACOPY -> r.callMmu(MmuCall.callDataCopy(hub));
        case CODECOPY -> r.callMmu(MmuCall.codeCopy(hub));
        case EXTCODECOPY -> r.callMmu(MmuCall.extCodeCopy(hub));
        case RETURNDATACOPY -> r.callMmu(MmuCall.returnDataCopy(hub));
        case MLOAD -> r.callMmu(MmuCall.mload(hub));
        case MSTORE -> r.callMmu(MmuCall.mstore(hub));
        case MSTORE8 -> r.callMmu(MmuCall.mstore8(hub));
          // case LOG0, LOG1, LOG2, LOG3, LOG4 -> r.callMmu(MmuCall.log(hub)); done elsewhere
        case CREATE -> r.callMmu(MmuCall.create(hub));
        case RETURN -> r.callMmu(
            hub.currentFrame().isDeployment()
                ? MmuCall.returnFromDeployment(
                    hub) // TODO Add a MMU call to MMU_INST_INVALID_CODE8PREFIX
                : MmuCall.returnFromCall(hub));
        case CREATE2 -> r.callMmu(MmuCall.create2(hub));
        case REVERT -> r.callMmu(MmuCall.revert(hub));
      }
    }

    if (hub.pch().signals().oob()) {
      switch (hub.opCode()) {
        case JUMP -> r.callOob(new JumpOobCall());
        case JUMPI -> r.callOob(new JumpiOobCall());
        case CALLDATALOAD -> r.callOob(new CallDataLoadOobCall());
        case SSTORE -> r.callOob(new SstoreOobCall());
        case CALL, CALLCODE -> {
          r.callOob(new CallOobCall());
        }
        case DELEGATECALL, STATICCALL -> {
          r.callOob(new CallOobCall());
        }
        case RETURN -> {
          if (hub.currentFrame().isDeployment()) {
            r.callOob(new DeploymentOobCall());
          }
        }
        default -> throw new IllegalArgumentException(
            "unexpected opcode for OoB %s".formatted(hub.opCode()));
      }
    }

    return r;
  }

  public ImcFragment callOob(OobCall f) {
    if (oobIsSet) {
      throw new IllegalStateException("OOB already called");
    } else {
      oobIsSet = true;
    }
    this.hub.oob().call(f);
    this.moduleCalls.add(f);
    return this;
  }

  public ImcFragment callMmu(MmuCall f) {
    if (mmuIsSet) {
      throw new IllegalStateException("MMU already called");
    } else {
      mmuIsSet = true;
    }
    if (f.instruction() != -1) {
      this.hub.mmu().call(f, this.hub.callStack());
    }

    this.moduleCalls.add(f);
    return this;
  }

  public ImcFragment callExp(ExpCall f) {
    if (expIsSet) {
      throw new IllegalStateException("EXP already called");
    } else {
      expIsSet = true;
    }
    this.hub.exp().call(f);
    this.moduleCalls.add(f);
    return this;
  }

  /*
  public ImcFragment callExp(ExplogExpCall f) {
    if (expIsSet) {
      throw new IllegalStateException("EXP already called");
    } else {
      expIsSet = true;
    }
    this.hub.exp().callExpLogCall(f);
    this.moduleCalls.add(f);
    return this;
  }
  */

  /*
  public ImcFragment callExp(ModexplogExpCall f) {
    if (modExpIsSet) {
      throw new IllegalStateException("MODEXP already called");
    } else {
      modExpIsSet = true;
    }
    this.hub.exp().callModExpLogCall(f);
    this.moduleCalls.add(f);
    return this;
  }
  */

  public ImcFragment callMxp(MxpCall f) {
    if (mxpIsSet) {
      throw new IllegalStateException("MXP already called");
    } else {
      mxpIsSet = true;
    }
    this.hub.mxp().call(f);
    this.moduleCalls.add(f);
    return this;
  }

  public ImcFragment callStp(StpCall f) {
    if (stpIsSet) {
      throw new IllegalStateException("STP already called");
    } else {
      stpIsSet = true;
    }
    this.hub.stp().call(f);
    this.moduleCalls.add(f);
    return this;
  }

  @Override
  public Trace trace(Trace trace) {
    trace.peekAtMiscellaneous(true);

    for (TraceSubFragment subFragment : this.moduleCalls) {
      subFragment.trace(trace, this.hub.state.stamps());
    }

    return trace;
  }
}
