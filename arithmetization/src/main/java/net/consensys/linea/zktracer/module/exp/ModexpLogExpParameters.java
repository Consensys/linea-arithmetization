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

package net.consensys.linea.zktracer.module.exp;

import java.math.BigInteger;

import net.consensys.linea.zktracer.types.EWord;
import org.apache.tuweni.bytes.Bytes;

public record ModexpLogExpParameters(
    EWord rawLead, int cdsCutoff, int ebsCutoff, BigInteger leadLog, Bytes trim, Bytes lead)
    implements ExpParameters {

  public BigInteger rawLeadHi() {
    return rawLead.hiBigInt();
  }

  public BigInteger rawLeadLo() {
    return rawLead.loBigInt();
  }

  public BigInteger trimHi() {
    return EWord.of(trim).hiBigInt();
  }

  public BigInteger trimLo() {
    return EWord.of(trim).loBigInt();
  }
}
