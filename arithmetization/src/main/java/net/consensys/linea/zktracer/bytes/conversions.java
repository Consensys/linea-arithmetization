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

package net.consensys.linea.zktracer.bytes;

import java.math.BigInteger;

import org.apache.tuweni.bytes.Bytes;

public class conversions {
  public static Bytes bigIntegerToBytes(BigInteger big) {
    Bytes bytes;
    if (big.equals(BigInteger.ZERO)) {
      bytes = Bytes.of(0x00);
    } else {
      byte[] byteArray;
      byteArray = big.toByteArray();
      if (byteArray[0] == 0) {
        Bytes tmp = Bytes.wrap(byteArray);
        bytes = Bytes.wrap(tmp.slice(1, tmp.size() - 1));
      } else {
        bytes = Bytes.wrap(byteArray);
      }
    }
    return bytes;
  }
}