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

import static org.hyperledger.besu.evm.internal.Words.clampedToLong;

import net.consensys.linea.zktracer.module.hub.Hub;
import net.consensys.linea.zktracer.module.hub.fragment.imc.ImcFragment;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.MxpCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.mmu.MmuCall;
import org.apache.tuweni.bytes.Bytes;
import org.bouncycastle.crypto.digests.KeccakDigest;

public class KeccakSection extends TraceSection {

  public static void appendToTrace(Hub hub) {

    final KeccakSection currentSection = new KeccakSection(hub);
    hub.addTraceSection(currentSection);

    ImcFragment imcFragment = ImcFragment.empty(hub);
    currentSection.addFragmentsAndStack(hub, imcFragment);

    MxpCall mxpCall = new MxpCall(hub);
    imcFragment.callMxp(mxpCall);

    final boolean mayTriggerNonTrivialOperation = mxpCall.isMayTriggerNonTrivialMmuOperation();
    final boolean triggerMmu = mayTriggerNonTrivialOperation & hub.pch().exceptions().none();

    if (triggerMmu) {
      imcFragment.callMmu(MmuCall.sha3(hub));

      // TODO: computing the hash shouldn't be done here
      final long offset = clampedToLong(hub.messageFrame().getStackItem(0));
      final long size = clampedToLong(hub.messageFrame().getStackItem(1));
      Bytes dataToHash = hub.messageFrame().shadowReadMemory(offset, size);
      KeccakDigest keccakDigest = new KeccakDigest(256);
      keccakDigest.update(dataToHash.toArray(), 0, dataToHash.size());
      byte[] hashOutput = new byte[keccakDigest.getDigestSize()];
      keccakDigest.doFinal(hashOutput, 0);

      // retroactively set HASH_INFO_FLAG and HASH_INFO_KECCAK_HI, HASH_INFO_KECCAK_LO
      currentSection.triggerHashInfo(Bytes.of(hashOutput));
    }
  }

  private KeccakSection(Hub hub) {
    super(hub);
  }
}
