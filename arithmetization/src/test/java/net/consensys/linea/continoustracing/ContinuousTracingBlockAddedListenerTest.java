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
package net.consensys.linea.continoustracing;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import net.consensys.linea.continoustracing.exception.InvalidBlockTraceException;
import net.consensys.linea.continoustracing.exception.TraceVerificationException;
import net.consensys.linea.zktracer.ZkTracer;
import org.hyperledger.besu.datatypes.Hash;
import org.hyperledger.besu.plugin.data.AddedBlockContext;
import org.hyperledger.besu.plugin.data.BlockHeader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContinuousTracingBlockAddedListenerTest {
  private static final Hash BLOCK_HASH =
      Hash.fromHexString("0x0000000000000000000000000000000000000000000000000000000000000042");

  private ContinuousTracingBlockAddedListener continuousTracingBlockAddedListener;

  @Mock ContinuousTracer continuousTracerMock;
  @Mock AddedBlockContext addedBlockContext;
  @Mock BlockHeader blockHeaderMock;
  @Mock TraceFailureHandler traceFailureHandlerMock;

  @Test
  void shouldDoNothingIfContinuousTracingIsDisabled()
      throws TraceVerificationException, InvalidBlockTraceException {
    continuousTracingBlockAddedListener =
        new ContinuousTracingBlockAddedListener(
            continuousTracerMock, traceFailureHandlerMock, "testZkEvmBin");

    continuousTracingBlockAddedListener.onBlockAdded(addedBlockContext);
    verify(continuousTracerMock, never())
        .verifyTraceOfBlock(any(), matches("testZkEvmBin"), new ZkTracer());
  }

  @Test
  void shouldTraceBlockIfContinuousTracingIsEnabled()
      throws TraceVerificationException, InvalidBlockTraceException {
    continuousTracingBlockAddedListener =
        new ContinuousTracingBlockAddedListener(
            continuousTracerMock, traceFailureHandlerMock, "testZkEvmBin");

    when(addedBlockContext.getBlockHeader()).thenReturn(blockHeaderMock);
    when(blockHeaderMock.getBlockHash()).thenReturn(BLOCK_HASH);

    continuousTracingBlockAddedListener.onBlockAdded(addedBlockContext);
    verify(continuousTracerMock, times(1))
        .verifyTraceOfBlock(eq(BLOCK_HASH), matches("testZkEvmBin"), new ZkTracer());
  }
}