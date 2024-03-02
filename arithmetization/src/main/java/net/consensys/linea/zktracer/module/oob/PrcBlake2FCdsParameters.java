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

package net.consensys.linea.zktracer.module.oob;

import static net.consensys.linea.zktracer.types.Conversions.bigIntegerToBytes;
import static net.consensys.linea.zktracer.types.Conversions.booleanToBytes;

import java.math.BigInteger;

import lombok.Getter;
import lombok.Setter;
import org.apache.tuweni.bytes.Bytes;

@Getter
public class PrcBlake2FCdsParameters implements OobParameters {
  BigInteger cds;
  BigInteger returnAtCapacity;
  @Setter boolean success;
  @Setter boolean returnAtCapacityNonZero;

  public PrcBlake2FCdsParameters(BigInteger cds, BigInteger returnAtCapacity) {
    this.cds = cds;
    this.returnAtCapacity = returnAtCapacity;
  }

  @Override
  public Trace trace(Trace trace) {
    return trace
        .data1(ZERO)
        .data2(bigIntegerToBytes(cds))
        .data3(bigIntegerToBytes(returnAtCapacity))
        .data4(booleanToBytes(success)) // Set after the constructor
        .data5(ZERO)
        .data6(Bytes.of(0))
        .data7(Bytes.of(0))
        .data8(booleanToBytes(returnAtCapacityNonZero)); // Set after the constructor
  }
}
