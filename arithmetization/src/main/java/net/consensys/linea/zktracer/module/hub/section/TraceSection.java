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

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;
import lombok.Getter;
import net.consensys.linea.zktracer.module.hub.DeploymentExceptions;
import net.consensys.linea.zktracer.module.hub.Hub;
import net.consensys.linea.zktracer.module.hub.Trace;
import net.consensys.linea.zktracer.module.hub.fragment.CommonFragment;
import net.consensys.linea.zktracer.module.hub.fragment.StackFragment;
import net.consensys.linea.zktracer.module.hub.fragment.TraceFragment;
import net.consensys.linea.zktracer.module.hub.fragment.TransactionFragment;
import net.consensys.linea.zktracer.opcode.OpCode;
import net.consensys.linea.zktracer.runtime.callstack.CallFrame;
import net.consensys.linea.zktracer.runtime.stack.StackLine;
import org.hyperledger.besu.evm.worldstate.WorldView;

/** A TraceSection gather the trace lines linked to a single operation */
public abstract class TraceSection {
  /**
   * A TraceLine stores the information required to generate a trace line.
   *
   * @param common data required to trace shared columns
   * @param specific data required to trace perspective-specific columns
   */
  public record TraceLine(CommonFragment common, TraceFragment specific) {
    /**
     * Trace the line in the given trace builder.
     *
     * @param trace where to trace the line
     * @return the trace builder
     */
    public Trace trace(Trace trace) {
      Preconditions.checkNotNull(common);
      Preconditions.checkNotNull(specific);

      common.trace(trace);
      specific.trace(trace);

      return trace.fillAndValidateRow();
    }
  }

  /** Count the stack lines */
  @Getter private int stackRowsCounter;
  /** Count the non-stack lines */
  private int nonStackRowsCounter;

  /** A list of {@link TraceLine} representing the trace lines associated with this section. */
  @Getter List<TraceLine> lines = new ArrayList<>(32);

  /**
   * Fill the columns shared by all type of lines.
   *
   * @return a {@link CommonFragment} representing the shared columns
   */
  private CommonFragment traceCommon(Hub hub, CallFrame callFrame) {
    OpCode opCode = callFrame.opCode();
    long refund = 0;
    if (hub.pch().exceptions().noStackException()) {
      refund = Hub.gp.of(callFrame.frame(), opCode).refund();
    }

    return new CommonFragment(
        hub.transients().tx().number(),
        hub.transients().conflation().number(),
        hub.transients().tx().state(),
        hub.stamp(),
        0, // retconned
        false, // retconned
        hub.opCodeData().instructionFamily(),
        hub.pch().exceptions().snapshot(),
        callFrame.id(),
        callFrame.contextNumber(),
        callFrame.contextNumber(),
        0, // retconned
        false, // retconned
        false, // retconned
        callFrame.pc(),
        callFrame.pc(), // retconned later on
        callFrame.addressAsEWord(),
        callFrame.codeDeploymentNumber(),
        callFrame.underDeployment(),
        callFrame.accountDeploymentNumber(),
        0,
        0,
        0,
        0,
        refund,
        0,
        hub.opCodeData().stackSettings().twoLinesInstruction(),
        this.stackRowsCounter == 1,
        0, // retconned on sealing
        this.nonStackRowsCounter);
  }

  /** Default creator for an empty section. */
  public TraceSection() {}

  /**
   * Add a fragment to the section while pairing it to its common piece.
   *
   * @param hub the execution context
   * @param fragment the fragment to insert
   */
  public final void addFragment(Hub hub, CallFrame callFrame, TraceFragment fragment) {
    Preconditions.checkArgument(!(fragment instanceof CommonFragment));

    if (fragment instanceof StackFragment) {
      this.stackRowsCounter++;
    } else {
      this.nonStackRowsCounter++;
    }

    this.lines.add(new TraceLine(traceCommon(hub, callFrame), fragment));
  }

  /**
   * Add the fragments containing the stack lines.
   *
   * @param hub the execution context
   */
  public final void addStack(Hub hub) {
    for (var stackFragment : this.makeStackFragments(hub, hub.currentFrame())) {
      this.addFragment(hub, hub.currentFrame(), stackFragment);
    }
  }

  /**
   * Create several {@link TraceLine} within this section for the specified fragments.
   *
   * @param hub the Hub linked to fragments execution
   * @param fragments the fragments to add to the section
   */
  public final void addFragmentsWithoutStack(
      Hub hub, CallFrame callFrame, TraceFragment... fragments) {
    for (TraceFragment f : fragments) {
      this.addFragment(hub, callFrame, f);
    }
  }

  /**
   * Create several {@link TraceLine} within this section for the specified fragments.
   *
   * @param hub the Hub linked to fragments execution
   * @param fragments the fragments to add to the section
   */
  public final void addFragmentsWithoutStack(Hub hub, TraceFragment... fragments) {
    for (TraceFragment chunk : fragments) {
      this.addFragment(hub, hub.currentFrame(), chunk);
    }
  }

  /**
   * Insert {@link TraceLine} related to the current state of the stack, then insert the provided
   * fragments in a single swoop.
   *
   * @param hub the execution context
   * @param callFrame the {@link CallFrame} containing the execution context; typically the current
   *     one in the hub for most instructions, but may be the parent one for e.g. CREATE*
   * @param fragments the fragments to insert
   */
  public final void addFragmentsAndStack(Hub hub, CallFrame callFrame, TraceFragment... fragments) {
    this.addStack(hub);
    this.addFragmentsWithoutStack(hub, callFrame, fragments);
  }

  /**
   * Insert {@link TraceLine} related to the current state of the stack of the current {@link
   * CallFrame}, then insert the provided fragments in a single swoop.
   *
   * @param hub the execution context
   * @param fragments the fragments to insert
   */
  public final void addFragmentsAndStack(Hub hub, TraceFragment... fragments) {
    this.addStack(hub);
    this.addFragmentsWithoutStack(hub, hub.currentFrame(), fragments);
  }

  /**
   * Returns the context number associated with the operation encoded by this TraceLine.
   *
   * @return the CN
   */
  public final int contextNumber() {
    return this.lines.get(0).common.contextNumber();
  }

  /**
   * Set the new context number associated with the operation encoded by this TraceLine.
   *
   * @param contextNumber the new CN
   */
  public final void setContextNumber(int contextNumber) {
    for (TraceLine line : this.lines) {
      line.common.newContextNumber(contextNumber);
    }
  }

  /**
   * Returns the program counter associated with the operation encoded by this TraceSection.
   *
   * @return the PC
   */
  public final int pc() {
    return this.lines.get(0).common.pc();
  }

  /**
   * This method is called when the TraceSection is finished, to build required information
   * post-hoc.
   *
   * @param hub the linked {@link Hub} context
   */
  public void seal(Hub hub) {
    int nonStackLineNumbers =
        (int) this.lines.stream().filter(l -> !(l.specific instanceof StackFragment)).count();
    int nonStackLineCounter = 0;
    for (TraceLine line : this.lines) {
      if (!(line.specific instanceof StackFragment)) {
        nonStackLineCounter++;
        line.common.nonStackRowsCounter(nonStackLineCounter);
      }
      line.common.newPc(hub.lastPc());
      line.common.newContextNumber(hub.lastContextNumber());
      line.common.numberOfNonStackRows(nonStackLineNumbers);
    }
  }

  /**
   * Returns whether the opcode encoded in this section is part of a reverted context. As it is
   * section-specific, we simply take the first one.
   *
   * @return true if the context reverted
   */
  public final boolean hasReverted() {
    return this.lines.get(0).common.txReverts();
  }

  /**
   * Returns the gas refund delta incurred by this operation. As it is section-specific, we simply
   * take the first one.
   *
   * @return the gas delta
   */
  public final long refundDelta() {
    return this.lines.get(0).common.refundDelta();
  }

  /**
   * Update this section with the current refunded gas as computed by the hub.
   *
   * @param refundedGas the refunded gas provided by the hub
   */
  public void setFinalGasRefundCounter(long refundedGas) {
    for (TraceLine line : this.lines) {
      if (line.specific instanceof TransactionFragment fragment) {
        fragment.setGasRefundFinalCounter(refundedGas);
      }
    }
  }

  /**
   * Update the stack fragments of the section with the provided {@link DeploymentExceptions}.
   *
   * @param contEx the computed exceptions
   */
  public void setContextExceptions(DeploymentExceptions contEx) {
    for (TraceLine line : this.lines) {
      if (line.specific instanceof StackFragment fragment) {
        fragment.contextExceptions(contEx);
      }
    }
  }

  /**
   * This method is called when the transaction is finished to build required information post-hoc.
   *
   * @param hub the linked {@link Hub} context
   */
  public final void postTxRetcon(Hub hub, long leftoverGas, long gasRefund) {
    for (TraceLine line : this.lines) {
      line.common().postTxRetcon(hub);
      line.common().gasRefund(gasRefund);
      if (line.specific instanceof TransactionFragment fragment) {
        fragment.setGasRefundAmount(gasRefund);
        fragment.setLeftoverGas(leftoverGas);
      }
    }
  }

  /**
   * This method is called when the conflation is finished to build required information post-hoc.
   *
   * @param hub the linked {@link Hub} context
   * @param state the blockchain state after the conflation execution
   */
  public final void postConflationRetcon(Hub hub, WorldView state) {
    for (TraceLine line : this.lines) {
      line.common().postConflationRetcon(hub, state);
      line.specific().postConflationRetcon(hub, state);
    }
  }

  private List<TraceFragment> makeStackFragments(final Hub hub, CallFrame f) {
    List<TraceFragment> r = new ArrayList<>(2);
    if (f.pending().getLines().isEmpty()) {
      for (int i = 0; i < (f.opCodeData().stackSettings().twoLinesInstruction() ? 2 : 1); i++) {
        r.add(
            StackFragment.prepare(
                hub,
                f.stack().snapshot(),
                new StackLine().asStackOperations(),
                hub.pch().exceptions().snapshot(),
                hub.pch().aborts().snapshot(),
                Hub.gp.of(f.frame(), f.opCode()),
                f.underDeployment()));
      }
    } else {
      for (StackLine line : f.pending().getLines()) {
        r.add(
            StackFragment.prepare(
                hub,
                f.stack().snapshot(),
                line.asStackOperations(),
                hub.pch().exceptions().snapshot(),
                hub.pch().aborts().snapshot(),
                Hub.gp.of(f.frame(), f.opCode()),
                f.underDeployment()));
      }
    }
    return r;
  }
}
