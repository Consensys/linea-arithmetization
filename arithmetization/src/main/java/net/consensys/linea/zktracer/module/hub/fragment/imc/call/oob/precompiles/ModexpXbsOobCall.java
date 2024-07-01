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

import static net.consensys.linea.zktracer.module.constants.GlobalConstants.OOB_INST_MODEXP_XBS;
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
public class ModexpXbsOobCall implements OobCall {

  final PrecompileInvocation p;
  final ModexpXbsCase modexpXbsCase;
  BigInteger xbsHi;
  BigInteger xbsLo;
  BigInteger ybsLo;
  boolean computeMax;

  BigInteger maxXbsYbs;
  boolean xbsNonZero;

  @Override
  public int oobInstruction() {
    return OOB_INST_MODEXP_XBS;
  }

  @Override
  public net.consensys.linea.zktracer.module.oob.Trace trace(
      net.consensys.linea.zktracer.module.oob.Trace trace) {
    return trace
        .data1(bigIntegerToBytes(xbsHi))
        .data2(bigIntegerToBytes(xbsLo))
        .data3(bigIntegerToBytes(ybsLo))
        .data4(booleanToBytes(computeMax))
        .data5(ZERO)
        .data6(ZERO)
        .data7(bigIntegerToBytes(maxXbsYbs))
        .data8(booleanToBytes(xbsNonZero));
  }

  @Override
  public Trace trace(Trace trace) {
    return trace
        .pMiscOobFlag(true)
        .pMiscOobInst(oobInstruction())
        .pMiscOobData1(bigIntegerToBytes(xbsHi))
        .pMiscOobData2(bigIntegerToBytes(xbsLo))
        .pMiscOobData3(bigIntegerToBytes(ybsLo))
        .pMiscOobData4(booleanToBytes(computeMax))
        .pMiscOobData5(ZERO)
        .pMiscOobData6(ZERO)
        .pMiscOobData7(bigIntegerToBytes(maxXbsYbs))
        .pMiscOobData8(booleanToBytes(xbsNonZero));
  }
}
