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

package net.consensys.linea.zktracer;

import static org.assertj.core.api.Assertions.assertThat;

import net.consensys.linea.config.LineaL1L2BridgeConfiguration;
import org.apache.tuweni.bytes.Bytes;
import org.hyperledger.besu.datatypes.Address;
import org.junit.jupiter.api.Test;

public class ZkTracerTest {

  @Test
  public void createNewTracer() {
    final ZkTracer zkTracer =
        new ZkTracer(
            LineaL1L2BridgeConfiguration.builder()
                .contract(Address.fromHexString("0xDEADBEEF"))
                .topic(Bytes.fromHexString("0x012345"))
                .build());
    assertThat(zkTracer.isExtendedTracing()).isTrue();
  }
}
