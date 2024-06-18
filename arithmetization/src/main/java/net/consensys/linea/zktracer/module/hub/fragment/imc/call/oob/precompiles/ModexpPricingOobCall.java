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

package net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.precompiles;

import static net.consensys.linea.zktracer.types.Conversions.bigIntegerToBytes;
import static net.consensys.linea.zktracer.types.Conversions.booleanToBytes;

import java.math.BigInteger;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.OobCall;
import net.consensys.linea.zktracer.module.hub.precompiles.PrecompileInvocation;
import net.consensys.linea.zktracer.module.oob.Trace;
import org.apache.tuweni.bytes.Bytes;

@Getter
@Setter
@RequiredArgsConstructor
public class ModexpPricingOobCall implements OobCall {

  final PrecompileInvocation p;
  BigInteger callGas;
  BigInteger returnAtCapacity;
  boolean success;
  BigInteger exponentLog;
  int maxMbsBbs;

  BigInteger returnGas;
  boolean returnAtCapacityNonZero;

  @Override
  public int oobInstruction() {
    return 0;
  }

  @Override
  public net.consensys.linea.zktracer.module.hub.Trace trace(net.consensys.linea.zktracer.module.hub.Trace trace) {
    return null;
  }

  @Override
  public Trace trace(Trace trace) {
    return trace
            .data1(bigIntegerToBytes(callGas))
            .data2(ZERO)
            .data3(bigIntegerToBytes(returnAtCapacity))
            .data4(booleanToBytes(success))
            .data5(bigIntegerToBytes(returnGas))
            .data6(bigIntegerToBytes(exponentLog))
            .data7(Bytes.of(maxMbsBbs))
            .data8(booleanToBytes(returnAtCapacityNonZero));
  }

}
