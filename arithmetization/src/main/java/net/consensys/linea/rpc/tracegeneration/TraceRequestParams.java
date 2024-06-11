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

package net.consensys.linea.rpc.tracegeneration;

import java.security.InvalidParameterException;
import java.util.Map;

import net.consensys.linea.rpc.Converters;
import net.consensys.linea.zktracer.ZkTracer;

/** Holds needed parameters for sending an execution trace generation request. */
@SuppressWarnings("unused")
public record TraceRequestParams(
    long startBlockNumber, long endBlockNumber, String expectedTracesEngineVersion) {
  private static final int EXPECTED_PARAMS_SIZE = 1;

  /**
   * Parses a list of params to a {@link TraceRequestParams} object.
   *
   * @param params an array of parameters.
   * @return a parsed {@link TraceRequestParams} object..
   */
  public static TraceRequestParams createTraceParams(final Object[] params) {
    // validate params size
    if (params.length != EXPECTED_PARAMS_SIZE) {
      throw new InvalidParameterException(
          "Expected a single params object in the params array but got %d"
              .formatted(params.length));
    }

    final Map<String, Object> traceParams = Converters.objectToMap(params[0]);

    final long fromBlock = (long) traceParams.get("startBlockNumber");
    final long toBlock = (long) traceParams.get("endBlockNumber");
    final String version = traceParams.get("expectedTracesEngineVersion").toString();

    if (!version.equals(getTracerRuntime())) {
      throw new InvalidParameterException(
          String.format(
              "INVALID_TRACES_VERSION: Runtime version is %s, requesting version %s",
              getTracerRuntime(), version));
    }

    return new TraceRequestParams(fromBlock, toBlock, version);
  }

  private static String getTracerRuntime() {
    return ZkTracer.class.getPackage().getSpecificationVersion();
  }
}
