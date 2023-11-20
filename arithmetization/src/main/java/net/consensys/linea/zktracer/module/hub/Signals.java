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

package net.consensys.linea.zktracer.module.hub;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import net.consensys.linea.zktracer.opcode.InstructionFamily;
import net.consensys.linea.zktracer.opcode.OpCode;
import org.apache.tuweni.bytes.Bytes32;
import org.hyperledger.besu.datatypes.Address;
import org.hyperledger.besu.evm.frame.MessageFrame;
import org.hyperledger.besu.evm.internal.Words;

import java.util.Set;

/**
 * Encodes the signals triggering other components.
 *
 * <p>When a component is requested, also checks that it may actually be triggered in the current
 * context.
 */
@Accessors(fluent = true)
@RequiredArgsConstructor
public class Signals {
  private final Set<InstructionFamily> FORCE_ENABLE_GAS = Set.of(InstructionFamily.CREATE, InstructionFamily.CALL, InstructionFamily.HALT);

  @Getter private boolean add;
  @Getter private boolean bin;
  @Getter private boolean mul;
  @Getter private boolean ext;
  @Getter private boolean mod;
  @Getter private boolean wcp;
  @Getter private boolean shf;

  @Getter private boolean gas;
  @Getter private boolean mmu;
  @Getter private boolean mxp;
  @Getter private boolean oob;
  @Getter private boolean stipend;
  @Getter private boolean exp;
  @Getter private boolean trm;
  @Getter private boolean hashInfo;
  @Getter private boolean logInfo;
  @Getter private boolean romLex;

  private final PlatformController platformController;

  public void reset() {
    this.add = false;
    this.bin = false;
    this.mul = false;
    this.ext = false;
    this.mod = false;
    this.wcp = false;
    this.shf = false;

    this.gas = false;
    this.mmu = false;
    this.mxp = false;
    this.oob = false;
    this.stipend = false;
    this.exp = false;
    this.trm = false;
    this.hashInfo = false;
    this.romLex = false;
  }

  public Signals snapshot() {
    Signals r = new Signals(null);
    r.add = this.add;
    r.bin = this.bin;
    r.mul = this.mul;
    r.ext = this.ext;
    r.mod = this.mod;
    r.wcp = this.wcp;
    r.shf = this.shf;


    r.gas = this.gas;
    r.mmu = this.mmu;
    r.mxp = this.mxp;
    r.oob = this.oob;
    r.stipend = this.stipend;
    r.exp = this.exp;
    r.trm = this.trm;
    r.hashInfo = this.hashInfo;
    r.romLex = this.romLex;

    return r;
  }

  public Signals wantMmu() {
    this.mmu = true;
    return this;
  }

  public Signals wantMxp() {
    this.mxp = true;
    return this;
  }

  public Signals wantStipend() {
    this.stipend = true;
    return this;
  }

  public Signals wantOob() {
    this.oob = true;
    return this;
  }

  /**
   * Setup all the signalling required to trigger modules for the execution of the current
   * operation.
   *
   * @param frame the currently executing frame
   * @param platformController the parent controller
   * @param hub the execution context
   */
  public void prepare(MessageFrame frame, PlatformController platformController, Hub hub) {
    final OpCode opCode = OpCode.of(frame.getCurrentOperation().getOpcode());
    final Exceptions ex = platformController.exceptions();

    this.gas = ex.any() || this.FORCE_ENABLE_GAS.contains(opCode.getData().instructionFamily());

    if (!ex.noStackException()) {
      return;
    }

    switch (opCode) {
      case CALLDATACOPY, CODECOPY -> {
        this.mxp = ex.outOfMemoryExpansion() || ex.outOfGas() || ex.none();
        this.mmu = ex.none() && !frame.getStackItem(1).isZero();
      }

      case RETURNDATACOPY -> {
        this.oob = ex.none() || ex.returnDataCopyFault();
        this.mxp = ex.none() || ex.outOfMemoryExpansion() || ex.outOfGas();
        this.mmu = ex.none() && !frame.getStackItem(1).isZero();
      }

      case EXTCODECOPY -> {
        boolean nonzeroSize = !frame.getStackItem(2).isZero();
        this.mxp = ex.outOfMemoryExpansion() || ex.outOfGas() || ex.none();
        this.trm = ex.outOfGas() || ex.none();
        this.mmu = ex.none() && nonzeroSize;
        Address address  = Address.extract((Bytes32) frame.getStackItem(0));
        boolean targetAddressHasCode = frame.getWorldUpdater().get(address).hasCode();
        this.romLex = ex.none() && nonzeroSize && targetAddressHasCode;
      }

      case LOG0, LOG1, LOG2, LOG3, LOG4 -> {
        this.mxp = ex.outOfMemoryExpansion() || ex.outOfGas() || ex.none();
        this.mmu = ex.none() && !frame.getStackItem(1).isZero(); // TODO: retcon to false if REVERT
        this.logInfo = this.mmu;
      }

      case CALL, DELEGATECALL, STATICCALL, CALLCODE -> {
        // WARN: nothing to see here, dynamically requested
      }

      case CREATE, CREATE2 -> {
        // WARN: nothing to see here, cf scenarios – vous qui entrez ici, abandonnez tout espoir
      }

      case REVERT -> {
        this.mxp = ex.outOfMemoryExpansion() || ex.outOfGas() || ex.none();
        this.mmu =
            ex.none()
                && !frame.getStackItem(1).isZero()
                && hub.currentFrame().returnDataTarget().length() > 0;
      }

      case RETURN -> {
        final boolean isDeployment = frame.getType() == MessageFrame.Type.CONTRACT_CREATION;
        final boolean sizeNonZero = !frame.getStackItem(1).isZero();

        // WARN: Static part, other modules may be dynamically requested in the hub
        this.mxp =
            ex.outOfMemoryExpansion() || ex.outOfGas() || ex.invalidCodePrefix() || ex.none();
        this.oob = isDeployment && (ex.codeSizeOverflow() || ex.none());
        this.mmu =
            (isDeployment && ex.invalidCodePrefix())
                || (!isDeployment
                    && ex.none()
                    && sizeNonZero
                    && hub.currentFrame().returnDataTarget().length() > 0)
                || (isDeployment && ex.none() && sizeNonZero);
        this.romLex = this.hashInfo = isDeployment && ex.none() && sizeNonZero;
      }

        // TODO: these opcodes
      case EXP -> { this.exp = true; }

      // other opcodes
      case ADD, SUB -> { this.add = !ex.outOfGas(); }
      case MUL -> { this.mul = !ex.outOfGas(); }
      case DIV, SDIV, MOD, SMOD -> { this.mod = !ex.outOfGas(); }
      case ADDMOD, MULMOD -> { this.ext = !ex.outOfGas(); }
      case LT , GT , SLT , SGT, EQ, ISZERO -> { this.bin = !ex.outOfGas(); }
      case AND, OR, XOR, NOT, SIGNEXTEND, BYTE -> { this.bin = !ex.outOfGas(); }
      case SHL, SHR, SAR -> { this.shf = !ex.outOfGas(); }
      case SHA3 -> {
        this.mxp = true;
        this.hashInfo = ex.none() && !frame.getStackItem(0).isZero();
        this.mmu = this.hashInfo;
      }
      case BALANCE, EXTCODESIZE, EXTCODEHASH, SELFDESTRUCT-> {
        this.trm = true;
      }
      case MLOAD, MSTORE, MSTORE8 -> {
        this.mxp = true;
        this.mmu = !ex.any();
      }
      case CALLDATALOAD -> {
        this.oob = true;
        this.mmu = frame.getInputData().size() > Words.clampedToLong(frame.getStackItem(0));
      }
      case SLOAD -> {}
      case SSTORE, JUMP, JUMPI -> {
        this.oob = true;
      }
      case MSIZE -> {
        this.mxp = ex.none();
      }
    }
  }
}
