/*
 * Copyright ConsenSys Inc.
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

import net.consensys.linea.zktracer.module.hub.Hub;
import net.consensys.linea.zktracer.module.hub.defer.PostTransactionDefer;
import net.consensys.linea.zktracer.module.hub.fragment.ContextFragment;
import net.consensys.linea.zktracer.module.hub.fragment.imc.ImcFragment;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.MxpCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.mmu.MmuCall;
import net.consensys.linea.zktracer.runtime.LogInvocation;
import org.hyperledger.besu.datatypes.Transaction;
import org.hyperledger.besu.evm.worldstate.WorldView;

public class LogSection implements PostTransactionDefer {

  LogCommonSection sectionPrequel;

  final boolean isStatic;

  final boolean mxpX;
  final boolean oogX;

  ImcFragment miscFragment;
  MxpCall mxpSubFragment;
  LogInvocation logData;

  public LogSection(Hub hub) {
    this.mxpX = hub.pch().exceptions().memoryExpansion();
    this.oogX = hub.pch().exceptions().outOfGas();

    // Static Case
    if (hub.currentFrame().frame().isStatic()) {
      isStatic = true;
      hub.addTraceSection(
          new LogCommonSection(hub, (short) 4, ContextFragment.readCurrentContextData(hub)));
      return;
    }

    // General Case
    isStatic = false;
    this.sectionPrequel =
        new LogCommonSection(hub, (short) 5, ContextFragment.readCurrentContextData(hub));
    hub.addTraceSection(sectionPrequel);
    logData = new LogInvocation(hub);
    mxpSubFragment = MxpCall.build(hub);
    miscFragment = ImcFragment.empty(hub); // TODO: .callMxp(mxpSubFragment.get()));
    hub.defers().postTx(this);
  }

  @Override
  public void runPostTx(Hub hub, WorldView state, Transaction tx, boolean isSuccessful) {
    if (!isStatic) {
      if (!this.logData.reverted()) {
        hub.state.stamps().incrementLogStamp();
        this.sectionPrequel.commonValues.logStamp(hub.state.stamps().log());
      }
      final boolean mmuTrigger = !this.logData.reverted() && this.logData.size != 0;
      if (mmuTrigger) {
        miscFragment.callMmu(MmuCall.LogX(hub, this.logData));
      }
      this.sectionPrequel.addFragment(miscFragment);
    }
  }

  public static class LogCommonSection extends TraceSection {
    public LogCommonSection(Hub hub, short maxNbOfRows, ContextFragment fragment) {
      super(hub, maxNbOfRows);
      this.addFragmentsAndStack(hub, fragment);
    }
  }
}
