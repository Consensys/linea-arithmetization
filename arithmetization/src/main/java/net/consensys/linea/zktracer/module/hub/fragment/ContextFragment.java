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
import net.consensys.linea.zktracer.runtime.callstack.CallFrameType;
import net.consensys.linea.zktracer.runtime.callstack.CallStack;
import net.consensys.linea.zktracer.types.Either;
import net.consensys.linea.zktracer.types.MemorySpan;
import org.apache.tuweni.bytes.Bytes;
import org.hyperledger.besu.datatypes.Address;
import org.hyperledger.besu.evm.internal.Words;

public record ContextFragment(
    Hub hub,
    CallStack callStack,
    // Left: callFrameId, Right: contextNumber
    Either<Integer, Integer> callFrameReference,
    MemorySpan returnDataSegment,
    boolean updateCallerReturndata)
    implements TraceFragment {

  // TODO: in the spec readContextData accepts an argument
  //  which is _some_ context number, not necessarily that
  //  of the currently executing execution context.
  public static ContextFragment readCurrentContextData(final Hub hub) {
    CallStack callStack = hub.callStack();
    return new ContextFragment(
        hub,
        callStack,
        Either.left(callStack.current().id()),
        callStack.current().latestReturnDataSource().snapshot(),
        false);
  }

  public static ContextFragment initializeExecutionContext(final Hub hub) {
    return new ContextFragment(
        hub,
        hub.callStack(),
        Either.right(hub.stamp() + 1),
        MemorySpan.fromStartEnd(
            0, hub.txStack().current().getBesuTransaction().getData().map(Bytes::size).orElse(0)),
        false);
  }

  public static ContextFragment executionProvidesEmptyReturnData(final Hub hub) {
    CallStack callStack = hub.callStack();
    return new ContextFragment(
        hub, callStack, Either.left(callStack.parent().id()), MemorySpan.empty(), true);
  }

  public static ContextFragment nonExecutionEmptyReturnData(final Hub hub) {
    CallStack callStack = hub.callStack();
    return new ContextFragment(
        hub, callStack, Either.left(callStack.parent().id()), MemorySpan.empty(), true);
  }

  public static ContextFragment executionReturnData(final Hub hub) {
    CallStack callStack = hub.callStack();
    return new ContextFragment(
        hub,
        callStack,
        Either.left(callStack.parent().id()),
        callStack.current().returnDataSource(),
        true);
  }

  public static ContextFragment enterContext(final Hub hub, final CallFrame calledCallFrame) {
    CallStack callStack = hub.callStack();
    return new ContextFragment(
        hub, callStack, Either.left(calledCallFrame.id()), MemorySpan.empty(), false);
  }

  public static ContextFragment providesReturnData(final Hub hub) {
    CallStack callStack = hub.callStack();
    return new ContextFragment(
        hub,
        callStack,
        Either.left(callStack.current().id()),
        callStack.current().latestReturnDataSource().snapshot(),
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

    final int cfi =
        callFrame == CallFrame.EMPTY || callFrame.type() == CallFrameType.MANTLE
            ? 0
            : hub.getCfiByMetaData(
                Words.toAddress(codeAddress),
                callFrame.codeDeploymentNumber(),
                callFrame.underDeployment());

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
        .pContextByteCodeDeploymentStatus(callFrame.underDeployment() ? 1 : 0)
        .pContextByteCodeCodeFragmentIndex(cfi)
        .pContextCallerAddressHi(highPart(callerAddress))
        .pContextCallerAddressLo(lowPart(callerAddress))
        .pContextCallValue(callFrame.value())
        .pContextCallDataContextNumber(parent.contextNumber())
        .pContextCallDataOffset(callFrame.callDataInfo().memorySpan().offset())
        .pContextCallDataSize(callFrame.callDataInfo().memorySpan().length())
        .pContextReturnAtOffset(callFrame.requestedReturnDataTarget().offset())
        .pContextReturnAtCapacity(callFrame.requestedReturnDataTarget().length())
        .pContextUpdate(updateCallerReturndata)
        .pContextReturnDataContextNumber(
            callFrame.id() == 0
                ? callFrame.universalParentReturnDataContextNumber
                : callFrame.lastCallee().map(c -> callStack.getById(c).contextNumber()).orElse(0))
        .pContextReturnDataOffset(returnDataSegment.offset())
        .pContextReturnDataSize(returnDataSegment.length());
  }
}
