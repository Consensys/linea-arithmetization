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

package net.consensys.linea.zktracer.module.hub.fragment;

import java.math.BigInteger;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.consensys.linea.zktracer.module.hub.Hub;
import net.consensys.linea.zktracer.module.hub.HubProcessingPhase;
import net.consensys.linea.zktracer.module.hub.State;
import net.consensys.linea.zktracer.module.hub.Trace;
import net.consensys.linea.zktracer.module.hub.signals.AbortingConditions;
import net.consensys.linea.zktracer.module.hub.signals.Exceptions;
import net.consensys.linea.zktracer.module.hub.signals.FailureConditions;
import net.consensys.linea.zktracer.opcode.InstructionFamily;
import net.consensys.linea.zktracer.opcode.OpCode;
import net.consensys.linea.zktracer.runtime.callstack.CallFrame;
import net.consensys.linea.zktracer.types.TransactionProcessingMetadata;
import org.apache.tuweni.bytes.Bytes;

@Accessors(fluent = true, chain = false)
@Builder
public final class CommonFragment implements TraceFragment {
  private final Hub hub;
  private final int absoluteTransactionNumber;
  private final int relativeBlockNumber;
  private final HubProcessingPhase hubProcessingPhase;
  private final State.TxState.Stamps stamps;
  private final InstructionFamily instructionFamily;
  private final Exceptions exceptions;
  private final AbortingConditions abortingConditions;
  private final FailureConditions failureConditions;
  private final int callFrameId;
  private final int callerContextNumber;
  @Getter private final int contextNumber;
  private final int contextNumberNew;
  private final int revertStamp;
  @Getter final short height;
  @Getter final short heightNew;
  @Getter private final int pc;
  @Setter private int pcNew;
  private int codeDeploymentNumber;
  private final boolean codeDeploymentStatus;
  private final long gasExpected;
  private final long gasActual;
  private final long gasCost;
  private final long gasNext;
  @Getter private final long refundDelta;
  @Setter private long gasRefund;
  @Getter @Setter private boolean twoLineInstruction;
  @Getter @Setter private boolean twoLineInstructionCounter;
  @Getter @Setter private int numberOfNonStackRows;
  @Getter @Setter private int nonStackRowsCounter;

  public static CommonFragment fromHub(
      final Hub hub, final CallFrame frame, boolean tliCounter, int nonStackRowCounter) {

    final boolean noStackException = hub.pch().exceptions().noStackException();
    final long refundDelta =
        noStackException ? Hub.GAS_PROJECTOR.of(frame.frame(), hub.opCode()).refund() : 0;

    // TODO: partial solution, will not work in general
    final long gasExpected = hub.expectedGas();
    final long gasActual = hub.remainingGas();
    final long gasCost =
        noStackException ? Hub.GAS_PROJECTOR.of(frame.frame(), hub.opCode()).staticGas() : 0;
    final long gasNext = hub.pch().exceptions().any() ? 0 : gasActual - gasCost;

    final int height = hub.currentFrame().stack().getHeight();
    final int heightNew =
        (noStackException
            ? height
                - hub.opCode().getData().stackSettings().delta()
                + hub.opCode().getData().stackSettings().alpha()
            : 0);
    final boolean hubInExecPhase = hub.state.getProcessingPhase() == HubProcessingPhase.TX_EXEC;
    final int pc = hubInExecPhase ? frame.pc() : 0;
    final int pcNew = computePcNew(hub, pc, noStackException, hubInExecPhase);

    return CommonFragment.builder()
        .hub(hub)
        .absoluteTransactionNumber(hub.transients().tx().getAbsoluteTransactionNumber())
        .relativeBlockNumber(hub.transients().conflation().number())
        .hubProcessingPhase(hub.state.getProcessingPhase())
        .stamps(hub.state.stamps().snapshot())
        .instructionFamily(hub.opCodeData().instructionFamily())
        .exceptions(hub.pch().exceptions().snapshot())
        .abortingConditions(hub.pch().abortingConditions().snapshot())
        .failureConditions(hub.pch().failureConditions().snapshot())
        .callFrameId(frame.id())
        .contextNumber(frame.contextNumber())
        .contextNumberNew(hub.contextNumberNew(frame))
        .pc(pc)
        .pcNew(pcNew)
        .height((short) height)
        .heightNew((short) heightNew)
        .codeDeploymentNumber(frame.codeDeploymentNumber())
        .codeDeploymentStatus(frame.underDeployment())
        .gasExpected(gasExpected)
        .gasActual(gasActual)
        .gasCost(gasCost)
        .gasNext(gasNext)
        .callerContextNumber(hub.callStack().getParentOf(frame.id()).contextNumber())
        .refundDelta(refundDelta)
        .twoLineInstruction(hub.opCodeData().stackSettings().twoLinesInstruction())
        .twoLineInstructionCounter(tliCounter)
        .nonStackRowsCounter(nonStackRowCounter)
        .build();
  }

  private static int computePcNew(
      final Hub hub, final int pc, boolean noStackException, boolean hubInExecPhase) {
    OpCode opCode = hub.opCode();
    if (!(noStackException && hubInExecPhase)) {
      return 0;
    }

    if (opCode.getData().isPush()) {
      return pc + opCode.byteValue() - OpCode.PUSH1.byteValue() + 2;
    }

    if (opCode.isJump()) {
      final BigInteger prospectivePcNew = hub.currentFrame().frame().getStackItem(0).toBigInteger();
      final BigInteger codeSize = BigInteger.valueOf(hub.currentFrame().code().getSize());

      final int attemptedPcNew =
          codeSize.compareTo(prospectivePcNew) > 0 ? prospectivePcNew.intValueExact() : 0;

      if (opCode.equals(OpCode.JUMP)) {
        return attemptedPcNew;
      }

      if (opCode.equals(OpCode.JUMPI)) {
        BigInteger condition = hub.currentFrame().frame().getStackItem(1).toBigInteger();
        if (!condition.equals(BigInteger.ZERO)) {
          return attemptedPcNew;
        }
      }
    }
    ;

    return pc + 1;
  }

  public boolean txReverts() {
    return hub.txStack()
        .getByAbsoluteTransactionNumber(this.absoluteTransactionNumber)
        .statusCode();
  }

  @Override
  public Trace trace(Trace trace) {
    throw new UnsupportedOperationException("should never be called");
  }

  public Trace trace(Trace trace, int stackHeight, int stackHeightNew) {
    final CallFrame frame = this.hub.callStack().getById(this.callFrameId);
    final TransactionProcessingMetadata tx =
        hub.txStack().getByAbsoluteTransactionNumber(this.absoluteTransactionNumber);
    final int codeFragmentIndex =
        this.hubProcessingPhase == HubProcessingPhase.TX_EXEC
            ? this.hub.getCfiByMetaData(
                frame.byteCodeAddress(), frame.codeDeploymentNumber(), frame.underDeployment())
            : 0;
    final boolean selfReverts = frame.selfReverts();
    final boolean getsReverted = frame.getsReverted();
    final boolean willRevert = frame.willRevert();

    return trace
        .absoluteTransactionNumber(tx.getAbsoluteTransactionNumber())
        .batchNumber(this.relativeBlockNumber)
        .txSkip(this.hubProcessingPhase == HubProcessingPhase.TX_SKIP)
        .txWarm(this.hubProcessingPhase == HubProcessingPhase.TX_WARM)
        .txInit(this.hubProcessingPhase == HubProcessingPhase.TX_INIT)
        .txExec(this.hubProcessingPhase == HubProcessingPhase.TX_EXEC)
        .txFinl(this.hubProcessingPhase == HubProcessingPhase.TX_FINAL)
        .hubStamp(this.stamps.hub())
        .hubStampTransactionEnd(tx.getHubStampTransactionEnd())
        .contextMayChange(
            this.hubProcessingPhase == HubProcessingPhase.TX_EXEC
                && ((instructionFamily == InstructionFamily.CALL
                        || instructionFamily == InstructionFamily.CREATE
                        || instructionFamily == InstructionFamily.HALT
                        || instructionFamily == InstructionFamily.INVALID)
                    || exceptions.any()))
        .exceptionAhoy(exceptions.any())
        .logInfoStamp(this.stamps.log())
        .mmuStamp(this.stamps.mmu())
        .mxpStamp(this.stamps.mxp())
        .contextNumber(contextNumber)
        .contextNumberNew(contextNumberNew)
        .callerContextNumber(callerContextNumber)
        .contextWillRevert(willRevert)
        .contextGetsReverted(getsReverted)
        .contextSelfReverts(selfReverts)
        .contextRevertStamp(revertStamp)
        .codeFragmentIndex(codeFragmentIndex)
        .programCounter(pc)
        .programCounterNew(pcNew)
        .height((short) stackHeight)
        .heightNew((short) stackHeightNew)
        .gasExpected(gasExpected)
        .gasActual(gasActual)
        .gasCost(Bytes.ofUnsignedLong(gasCost))
        .gasNext(gasNext)
        .refundCounter(gasRefund)
        .refundCounterNew(gasRefund + (willRevert ? 0 : refundDelta))
        .twoLineInstruction(twoLineInstruction)
        .counterTli(twoLineInstructionCounter)
        .nonStackRows((short) numberOfNonStackRows)
        .counterNsr((short) nonStackRowsCounter);
  }
}
