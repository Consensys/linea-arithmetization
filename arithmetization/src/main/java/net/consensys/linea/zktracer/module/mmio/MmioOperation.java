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

package net.consensys.linea.zktracer.module.mmio;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import net.consensys.linea.zktracer.container.ModuleOperation;
import net.consensys.linea.zktracer.module.hub.State;
import net.consensys.linea.zktracer.runtime.microdata.MicroData;

@RequiredArgsConstructor
@Accessors(fluent = true)
class MmioOperation extends ModuleOperation {
  @Getter private final MicroData microData;
  @Getter private final MmioDataProcessor mmioDataProcessor;
  @Getter private final State.TxState.Stamps moduleStamps;
  @Getter private final int microStamp;
  @Getter private final boolean isInitCode;

  @Override
  protected int computeLineCount() {
    return 1 + mmioDataProcessor.maxCounter();
  }
}