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
package net.consensys.linea.zktracer.instructionprocessing;

import net.consensys.linea.zktracer.opcode.OpCode;
import net.consensys.linea.zktracer.testing.BytecodeCompiler;
import net.consensys.linea.zktracer.testing.BytecodeRunner;
import net.consensys.linea.zktracer.testing.EvmExtension;
import org.apache.tuweni.bytes.Bytes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(EvmExtension.class)
public class JumpTests {

  /**
   * NOTE: the bytecode we propose will at time use the following offsets (unless the initial push
   * is large!) bytecode: PUSHX pcNew // offsets: 0, 1 JUMP // offset: 2 INVALID // offset: 3
   * JUMPDEST // offset: 4 PUSH1 0x5b // offsets: 5, 6 <- 0x5b is the byte value of JUMPDEST
   */
  Bytes testCodeForJumpScenario(String pcNew) {
    return BytecodeCompiler.newProgram()
        .push(pcNew)
        .op(OpCode.JUMP)
        .op(OpCode.INVALID)
        .op(OpCode.JUMPDEST)
        .push(OpCode.JUMPDEST.byteValue()) // false JUMPDEST
        .compile();
  }

  @Test
  void jumpOntoJumpDestTest() {
    BytecodeRunner.of(testCodeForJumpScenario("4")).run();
  }

  @Test
  void jumpOntoInvalidTest() {
    BytecodeRunner.of(testCodeForJumpScenario("3")).run();
  }

  @Test
  void jumpOntoJumpDestByteOwnedBySomePush() {
    BytecodeRunner.of(testCodeForJumpScenario("5")).run();
  }

  @Test
  void jumpOutOfBoundsSmall() {
    BytecodeRunner.of(testCodeForJumpScenario("0xff")).run();
  }

  @Test
  void jumpOutOfBoundsMaxUint128() {
    BytecodeRunner.of(testCodeForJumpScenario("0xffffffffffffffffffffffffffffffff")).run();
  }

  @Test
  void jumpOutOfBoundsTwoToThe128() {
    BytecodeRunner.of(testCodeForJumpScenario("0x0100000000000000000000000000000000")).run();
  }

  @Test
  void jumpOutOfBoundsTwoToThe128Plus4() {
    BytecodeRunner.of(testCodeForJumpScenario("0x0100000000000000000000000000000004")).run();
  }

  @Test
  void jumpOutOfBoundsMaxUint256() {
    BytecodeRunner.of(
            testCodeForJumpScenario(
                "0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"))
        .run();
  }
}
