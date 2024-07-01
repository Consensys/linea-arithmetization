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

package net.consensys.linea.zktracer.module.hub.fragment;

import static net.consensys.linea.zktracer.types.AddressUtils.highPart;
import static net.consensys.linea.zktracer.types.AddressUtils.lowPart;

import net.consensys.linea.zktracer.module.hub.Hub;
import net.consensys.linea.zktracer.module.hub.Trace;
import net.consensys.linea.zktracer.runtime.callstack.CallFrame;
import net.consensys.linea.zktracer.runtime.callstack.CallStack;
import net.consensys.linea.zktracer.types.Either;
import net.consensys.linea.zktracer.types.MemorySpan;
import org.apache.tuweni.bytes.Bytes;
import org.hyperledger.besu.datatypes.Address;

public record ContextFragment(
    Hub hub,
    CallStack callStack,
    // Left: callFrameId, Right: contextNumber
    Either<Integer, Integer> callFrameReference,
    int returnDataContextNumber,
    MemorySpan returnDataSegment,
    boolean updateReturnData)
    implements TraceFragment {

  public static ContextFragment readContextDataByContextNumber(
      final Hub hub, final int contextNumber) {
    CallStack callStack = hub.callStack();
    return new ContextFragment(
        hub,
        callStack,
        Either.right(contextNumber),
        callStack.getByContextNumber(contextNumber).returnDataContextNumber(),
        callStack.current().latestReturnDataSource().snapshot(),
        false);
  }

  public static ContextFragment readContextDataById(final Hub hub, final int contextId) {
    CallStack callStack = hub.callStack();
    return new ContextFragment(
        hub,
        callStack,
        Either.left(contextId),
        callStack.getById(contextId).returnDataContextNumber(),
        callStack.current().latestReturnDataSource().snapshot(),
        false);
  }

  public static ContextFragment readCurrentContextData(final Hub hub) {
    return readContextDataById(hub, hub.callStack().current().id());
  }

  public static ContextFragment initializeExecutionContext(final Hub hub) {
    return new ContextFragment(
        hub,
        hub.callStack(),
        Either.right(hub.stamp() + 1),
        0,
        MemorySpan.fromStartEnd(
            0, hub.txStack().current().getBesuTransaction().getData().map(Bytes::size).orElse(0)),
        false);
  }

  public static ContextFragment executionProvidesEmptyReturnData(final Hub hub) {
    CallStack callStack = hub.callStack();
    return new ContextFragment(
        hub,
        callStack,
        Either.left(callStack.parent().id()),
        hub.callStack().current().contextNumber(),
        MemorySpan.empty(),
        true);
  }

  public static ContextFragment nonExecutionEmptyReturnData(final Hub hub) {
    CallStack callStack = hub.callStack();
    return new ContextFragment(
        hub,
        callStack,
        Either.left(callStack.current().id()),
        hub.newChildContextNumber(),
        MemorySpan.empty(),
        true);
  }

  public static ContextFragment executionReturnData(final Hub hub) {
    CallStack callStack = hub.callStack();
    return new ContextFragment(
        hub,
        callStack,
        Either.left(callStack.parent().id()),
        hub.currentFrame().contextNumber(),
        callStack.current().returnDataSource(),
        true);
  }

  public static ContextFragment enterContext(final Hub hub, final CallFrame calledCallFrame) {
    CallStack callStack = hub.callStack();
    return new ContextFragment(
        hub, callStack, Either.left(calledCallFrame.id()), 0, MemorySpan.empty(), false);
  }

  public static ContextFragment providesReturnData(
      final Hub hub, int receiverContextNumber, int providerContextNumber) {
    CallStack callStack = hub.callStack();
    return new ContextFragment(
        hub,
        callStack,
        Either.right(receiverContextNumber),
        providerContextNumber,
        callStack.current().latestReturnDataSource().snapshot(), // TODO: is this what we want ?
        true);
  }

  @Override
  public Trace trace(Trace trace) {
    final CallFrame callFrame =
        this.callFrameReference.map(this.callStack::getById, this.callStack::getByContextNumber);
    final CallFrame parent = callStack.getParentOf(callFrame.id());

    final Address address = callFrame.accountAddress();
    final Address codeAddress = callFrame.byteCodeAddress();
    final Address callerAddress = callFrame.callerAddress();

    return trace
        .peekAtContext(true)
        .pContextContextNumber(callFrame.contextNumber())
        .pContextCallStackDepth((short) callFrame.depth())
        .pContextIsRoot(callFrame.isRoot())
        .pContextIsStatic(callFrame.type().isStatic())
        .pContextAccountAddressHi(highPart(address))
        .pContextAccountAddressLo(lowPart(address))
        .pContextAccountDeploymentNumber(callFrame.accountDeploymentNumber())
        .pContextByteCodeAddressHi(highPart(codeAddress))
        .pContextByteCodeAddressLo(lowPart(codeAddress))
        .pContextByteCodeDeploymentNumber(callFrame.codeDeploymentNumber())
        .pContextByteCodeDeploymentStatus(callFrame.isDeployment() ? 1 : 0)
        .pContextByteCodeCodeFragmentIndex(callFrame.getCodeFragmentIndex(hub))
        .pContextCallerAddressHi(highPart(callerAddress))
        .pContextCallerAddressLo(lowPart(callerAddress))
        .pContextCallValue(callFrame.value())
        .pContextCallDataContextNumber(parent.contextNumber())
        .pContextCallDataOffset(callFrame.callDataInfo().memorySpan().offset())
        .pContextCallDataSize(callFrame.callDataInfo().memorySpan().length())
        .pContextReturnAtOffset(callFrame.requestedReturnDataTarget().offset())
        .pContextReturnAtCapacity(callFrame.requestedReturnDataTarget().length())
        .pContextUpdate(updateReturnData)
        .pContextReturnDataContextNumber(returnDataContextNumber)
        //             callFrame.id() == 0
        //                 ? callFrame.universalParentReturnDataContextNumber
        //                 : callFrame.lastCallee().map(c ->
        // callStack.getById(c).contextNumber()).orElse(0))
        .pContextReturnDataOffset(returnDataSegment.offset())
        .pContextReturnDataSize(returnDataSegment.length());
  }
}
