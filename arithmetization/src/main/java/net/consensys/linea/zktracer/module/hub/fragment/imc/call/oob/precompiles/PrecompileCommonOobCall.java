/*
 * Copyright ConsenSys AG.
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

package net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.precompiles;

import static net.consensys.linea.zktracer.module.constants.GlobalConstants.*;
import static net.consensys.linea.zktracer.types.Conversions.bigIntegerToBytes;
import static net.consensys.linea.zktracer.types.Conversions.booleanToBytes;

import java.math.BigInteger;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.consensys.linea.zktracer.module.hub.Trace;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.OobCall;
import net.consensys.linea.zktracer.module.hub.precompiles.PrecompileInvocation;

@Getter
@Setter
@RequiredArgsConstructor
public class PrecompileCommonOobCall implements OobCall {

  final PrecompileInvocation p;
  BigInteger callGas;
  BigInteger cds;
  BigInteger returnAtCapacity;
  boolean hubSuccess;
  BigInteger returnGas;
  boolean returnAtCapacityNonZero;
  boolean cdsIsZero; // Necessary to compute extractCallData and emptyCallData

  public boolean getExtractCallData() {
    return hubSuccess && !cdsIsZero;
  }

  public boolean getCallDataIsEmpty() {
    return hubSuccess && cdsIsZero;
  }

  @Override
  public int oobInstruction() {
    return switch (p.precompile().address.trimLeadingZeros().toInt()) {
      case 1 -> OOB_INST_ECRECOVER;
      case 2 -> OOB_INST_SHA2;
      case 3 -> OOB_INST_RIPEMD;
      case 4 -> OOB_INST_IDENTITY;
      case 6 -> OOB_INST_ECADD;
      case 7 -> OOB_INST_ECMUL;
      case 8 -> OOB_INST_ECPAIRING;
      default -> throw new RuntimeException("precompile not 'common'");
    };
  }

  @Override
  public net.consensys.linea.zktracer.module.oob.Trace trace(
      net.consensys.linea.zktracer.module.oob.Trace trace) {
    return trace
        .data1(bigIntegerToBytes(callGas))
        .data2(bigIntegerToBytes(cds))
        .data3(bigIntegerToBytes(returnAtCapacity))
        .data4(booleanToBytes(hubSuccess)) // Set after the constructor
        .data5(bigIntegerToBytes(returnGas)) // Set after the constructor
        .data6(booleanToBytes(getExtractCallData())) // Derived from other parameters
        .data7(booleanToBytes(getCallDataIsEmpty())) // Derived from other parameters
        .data8(booleanToBytes(returnAtCapacityNonZero)); // Set after the constructor
  }

  @Override
  public Trace trace(Trace trace) {
    return trace
        .pMiscOobFlag(true)
        .pMiscOobInst(oobInstruction())
        .pMiscOobData1(bigIntegerToBytes(callGas))
        .pMiscOobData2(bigIntegerToBytes(cds))
        .pMiscOobData3(bigIntegerToBytes(returnAtCapacity))
        .pMiscOobData4(booleanToBytes(hubSuccess)) // Set after the constructor
        .pMiscOobData5(bigIntegerToBytes(returnGas)) // Set after the constructor
        .pMiscOobData6(booleanToBytes(getExtractCallData())) // Derived from other parameters
        .pMiscOobData7(booleanToBytes(getCallDataIsEmpty())) // Derived from other parameters
        .pMiscOobData8(booleanToBytes(returnAtCapacityNonZero)); // Set after the constructor
  }
}
