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

import net.consensys.linea.zktracer.module.hub.Hub;
import net.consensys.linea.zktracer.module.hub.fragment.TraceFragment;
import net.consensys.linea.zktracer.module.hub.fragment.imc.ImcFragment;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.MxpCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.mmu.MmuCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.OobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.opcodes.CallDataLoadOobCall;

public class StackRam extends TraceSection {

  public static void appendToTrace(Hub hub) {

    ImcFragment imcFragment = ImcFragment.empty(hub);

    switch (hub.opCode()) {
      case MSTORE, MSTORE8, MLOAD -> {
        MxpCall mxpCall = new MxpCall(hub);
        imcFragment.callMxp(mxpCall);
      }
      case CALLDATALOAD -> {
        OobCall oobCall = new CallDataLoadOobCall();
        imcFragment.callOob(oobCall);
      }
    }

    boolean triggerMmu = hub.pch().exceptions().none();

    if (triggerMmu) {
      switch (hub.opCode()) {
        case MSTORE -> {
          imcFragment.callMmu(MmuCall.mstore(hub));
        }
        case MSTORE8 -> {
          imcFragment.callMmu(MmuCall.mstore8(hub));
        }
        case MLOAD -> {
          imcFragment.callMmu(MmuCall.mload(hub));
        }
        case CALLDATALOAD -> {
          imcFragment.callMmu(MmuCall.callDataLoad(hub));
        }
        default -> {
          throw new RuntimeException("Opcode not part of the stack ram family");
        }
      }
    }
  }

  public StackRam(Hub hub, TraceFragment... chunks) {
    super(hub);
    this.addFragmentsAndStack(hub, chunks);
  }
}
