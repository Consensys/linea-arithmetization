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

package net.consensys.linea.zktracer.module.hub.precompiles;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import net.consensys.linea.zktracer.module.hub.Hub;
import net.consensys.linea.zktracer.module.hub.fragment.ContextFragment;
import net.consensys.linea.zktracer.module.hub.fragment.TraceFragment;
import net.consensys.linea.zktracer.module.hub.fragment.imc.ImcFragment;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.exp.ModexpLogExpCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.mmu.MmuCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.precompiles.Blake2fCallDataSizeOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.precompiles.Blake2fParamsOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.precompiles.ModexpCallDataSizeOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.precompiles.ModexpExtractOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.precompiles.ModexpLeadOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.precompiles.ModexpPricingOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.precompiles.ModexpXbsCase;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.precompiles.ModexpXbsOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.precompiles.PrecompileCommonOobCall;

@RequiredArgsConstructor
public class PrecompileLinesGenerator {
  public static List<TraceFragment> generateFor(final Hub hub, final PrecompileInvocation p) {
    final List<TraceFragment> r = new ArrayList<>(10);
    switch (p.precompile()) {
      case EC_RECOVER -> {
        if (p.hubFailure()) {
          r.add(ImcFragment.empty(hub).callOob(new PrecompileCommonOobCall(p)));
        } else {
          final boolean recoverySuccessful =
              ((EcRecoverMetadata) p.metadata()).recoverySuccessful();

          r.add(
              ImcFragment.empty(hub)
                  .callOob(new PrecompileCommonOobCall(p))
                  .callMmu(
                      p.callDataSource().isEmpty()
                          ? MmuCall.nop()
                          : MmuCall.forEcRecover(hub, p, recoverySuccessful, 0)));
          r.add(
              ImcFragment.empty(hub)
                  .callMmu(
                      p.callDataSource().isEmpty()
                          ? MmuCall.nop()
                          : MmuCall.forEcRecover(hub, p, recoverySuccessful, 1)));
          r.add(
              ImcFragment.empty(hub)
                  .callMmu(
                      p.callDataSource().isEmpty()
                          ? MmuCall.nop()
                          : MmuCall.forEcRecover(hub, p, recoverySuccessful, 2)));
        }
      }
      case SHA2_256 -> {
        if (p.hubFailure()) {
          r.add(ImcFragment.empty(hub).callOob(new PrecompileCommonOobCall(p)));
        } else {
          r.add(
              ImcFragment.empty(hub)
                  .callOob(new PrecompileCommonOobCall(p))
                  .callMmu(
                      p.callDataSource().isEmpty() ? MmuCall.nop() : MmuCall.forSha2(hub, p, 0)));
          r.add(
              ImcFragment.empty(hub)
                  .callMmu(
                      p.callDataSource().isEmpty() ? MmuCall.nop() : MmuCall.forSha2(hub, p, 1)));
          r.add(
              ImcFragment.empty(hub)
                  .callMmu(
                      p.callDataSource().isEmpty() ? MmuCall.nop() : MmuCall.forSha2(hub, p, 2)));
        }
      }
      case RIPEMD_160 -> {
        if (p.hubFailure()) {
          r.add(ImcFragment.empty(hub).callOob(new PrecompileCommonOobCall(p)));
        } else {
          r.add(
              ImcFragment.empty(hub)
                  .callOob(new PrecompileCommonOobCall(p))
                  .callMmu(
                      p.callDataSource().isEmpty()
                          ? MmuCall.nop()
                          : MmuCall.forRipeMd160(hub, p, 0)));
          r.add(
              ImcFragment.empty(hub)
                  .callMmu(
                      p.callDataSource().isEmpty()
                          ? MmuCall.nop()
                          : MmuCall.forRipeMd160(hub, p, 1)));
          r.add(
              ImcFragment.empty(hub)
                  .callMmu(
                      p.callDataSource().isEmpty()
                          ? MmuCall.nop()
                          : MmuCall.forRipeMd160(hub, p, 2)));
        }
      }
      case IDENTITY -> {
        if (p.hubFailure()) {
          r.add(ImcFragment.empty(hub).callOob(new PrecompileCommonOobCall(p)));
        } else {
          r.add(
              ImcFragment.empty(hub)
                  .callOob(new PrecompileCommonOobCall(p))
                  .callMmu(MmuCall.forIdentity(hub, p, 0)));
          r.add(ImcFragment.empty(hub).callMmu(MmuCall.forIdentity(hub, p, 1)));
        }
      }
      case MODEXP -> {
        final ModExpMetadata m = (ModExpMetadata) p.metadata();
        final int bbsInt = m.bbs().toUnsignedBigInteger().intValueExact();
        final int ebsInt = m.ebs().toUnsignedBigInteger().intValueExact();
        final int mbsInt = m.mbs().toUnsignedBigInteger().intValueExact();

        r.add(ImcFragment.empty(hub).callOob(new ModexpCallDataSizeOobCall(p)));
        r.add(
            ImcFragment.empty(hub)
                .callOob(new ModexpXbsOobCall(p, ModexpXbsCase.OOB_INST_MODEXP_BBS))
                .callMmu(m.extractBbs() ? MmuCall.forModExp(hub, p, 2) : MmuCall.nop()));
        r.add(
            ImcFragment.empty(hub)
                .callOob(new ModexpXbsOobCall(p, ModexpXbsCase.OOB_INST_MODEXP_EBS))
                .callMmu(m.extractEbs() ? MmuCall.forModExp(hub, p, 3) : MmuCall.nop()));
        r.add(
            ImcFragment.empty(hub)
                .callOob(new ModexpXbsOobCall(p, ModexpXbsCase.OOB_INST_MODEXP_MBS))
                .callMmu(m.extractEbs() ? MmuCall.forModExp(hub, p, 4) : MmuCall.nop()));
        final ImcFragment line5 =
            ImcFragment.empty(hub)
                .callOob(new ModexpLeadOobCall(p))
                .callMmu(m.loadRawLeadingWord() ? MmuCall.forModExp(hub, p, 5) : MmuCall.nop());
        if (m.loadRawLeadingWord()) {
          line5.callExp(new ModexpLogExpCall(p));
        }
        r.add(line5);
        r.add(ImcFragment.empty(hub).callOob(new ModexpPricingOobCall(p)));
        if (p.ramFailure()) {
          r.add(ContextFragment.nonExecutionEmptyReturnData(hub));
        } else {
          r.add(
              ImcFragment.empty(hub)
                  .callOob(new ModexpExtractOobCall(p))
                  .callMmu(m.extractModulus() ? MmuCall.forModExp(hub, p, 7) : MmuCall.nop()));

          if (m.extractModulus()) {
            r.add(ImcFragment.empty(hub).callMmu(MmuCall.forModExp(hub, p, 8)));
            r.add(ImcFragment.empty(hub).callMmu(MmuCall.forModExp(hub, p, 9)));
            r.add(ImcFragment.empty(hub).callMmu(MmuCall.forModExp(hub, p, 10)));
          } else {
            for (int i = 0; i < 4; i++) {
              r.add(ImcFragment.empty(hub));
            }
          }

          if (!m.mbs().isZero() && !p.requestedReturnDataTarget().isEmpty()) {
            r.add(ImcFragment.empty(hub).callMmu(MmuCall.forModExp(hub, p, 11)));
          }

          r.add(
              ContextFragment.providesReturnData(
                  hub, hub.currentFrame().contextNumber(), hub.newChildContextNumber()));
        }
      }
      case EC_ADD -> {
        if (p.hubFailure()) {
          r.add(ImcFragment.empty(hub).callOob(new PrecompileCommonOobCall(p)));
          r.add(ContextFragment.nonExecutionEmptyReturnData(hub));
        } else if (p.ramFailure()) {
          r.add(
              ImcFragment.empty(hub)
                  .callOob(new PrecompileCommonOobCall(p))
                  .callMmu(MmuCall.forEcAdd(hub, p, 0)));
          r.add(ContextFragment.nonExecutionEmptyReturnData(hub));
        } else {
          r.add(
              ImcFragment.empty(hub)
                  .callOob(new PrecompileCommonOobCall(p))
                  .callMmu(MmuCall.forEcAdd(hub, p, 0)));
          r.add(
              ImcFragment.empty(hub)
                  .callMmu(
                      p.callDataSource().isEmpty() ? MmuCall.nop() : MmuCall.forEcAdd(hub, p, 1)));
          r.add(
              ImcFragment.empty(hub)
                  .callMmu(
                      p.requestedReturnDataTarget().isEmpty()
                          ? MmuCall.nop()
                          : MmuCall.forEcAdd(hub, p, 2)));
          r.add(
              ContextFragment.providesReturnData(
                  hub, hub.currentFrame().contextNumber(), hub.newChildContextNumber()));
        }
      }
      case EC_MUL -> {
        if (p.hubFailure()) {
          r.add(ImcFragment.empty(hub).callOob(new PrecompileCommonOobCall(p)));
          r.add(ContextFragment.nonExecutionEmptyReturnData(hub));
        } else if (p.ramFailure()) {
          r.add(
              ImcFragment.empty(hub)
                  .callOob(new PrecompileCommonOobCall(p))
                  .callMmu(MmuCall.forEcMul(hub, p, 0)));
          r.add(ContextFragment.nonExecutionEmptyReturnData(hub));
        } else {
          r.add(
              ImcFragment.empty(hub)
                  .callOob(new PrecompileCommonOobCall(p))
                  .callMmu(MmuCall.forEcMul(hub, p, 0)));
          r.add(
              ImcFragment.empty(hub)
                  .callMmu(
                      p.callDataSource().isEmpty() ? MmuCall.nop() : MmuCall.forEcMul(hub, p, 1)));
          r.add(
              ImcFragment.empty(hub)
                  .callMmu(
                      p.requestedReturnDataTarget().isEmpty()
                          ? MmuCall.nop()
                          : MmuCall.forEcMul(hub, p, 2)));
          r.add(
              ContextFragment.providesReturnData(
                  hub, hub.currentFrame().contextNumber(), hub.newChildContextNumber()));
        }
      }
      case EC_PAIRING -> {
        if (p.hubFailure()) {
          r.add(ImcFragment.empty(hub).callOob(new PrecompileCommonOobCall(p)));
          r.add(ContextFragment.nonExecutionEmptyReturnData(hub));
        } else if (p.ramFailure()) {
          r.add(
              ImcFragment.empty(hub)
                  .callOob(new PrecompileCommonOobCall(p))
                  .callMmu(MmuCall.forEcPairing(hub, p, 0)));
          r.add(ContextFragment.nonExecutionEmptyReturnData(hub));
        } else {
          r.add(
              ImcFragment.empty(hub)
                  .callOob(new PrecompileCommonOobCall(p))
                  .callMmu(MmuCall.forEcPairing(hub, p, 0)));
          r.add(ImcFragment.empty(hub).callMmu(MmuCall.forEcPairing(hub, p, 1)));
          r.add(
              ImcFragment.empty(hub)
                  .callMmu(
                      p.requestedReturnDataTarget().isEmpty()
                          ? MmuCall.nop()
                          : MmuCall.forEcPairing(hub, p, 2)));
          r.add(
              ContextFragment.providesReturnData(
                  hub, hub.currentFrame().contextNumber(), hub.newChildContextNumber()));
        }
      }
      case BLAKE2F -> {
        if (p.hubFailure()) {
          r.add(ImcFragment.empty(hub).callOob(new Blake2fCallDataSizeOobCall(p)));
        } else if (p.ramFailure()) {
          r.add(
              ImcFragment.empty(hub)
                  .callOob(new Blake2fCallDataSizeOobCall(p))
                  .callMmu(MmuCall.forBlake2f(hub, p, 0)));
          r.add(ImcFragment.empty(hub).callOob(new Blake2fParamsOobCall(p)));
        } else {
          r.add(
              ImcFragment.empty(hub)
                  .callOob(new Blake2fCallDataSizeOobCall(p))
                  .callMmu(MmuCall.forBlake2f(hub, p, 0)));
          r.add(
              ImcFragment.empty(hub)
                  .callOob(new Blake2fParamsOobCall(p))
                  .callMmu(MmuCall.forBlake2f(hub, p, 1)));
          r.add(ImcFragment.empty(hub).callMmu(MmuCall.forBlake2f(hub, p, 2)));
          r.add(ImcFragment.empty(hub).callMmu(MmuCall.forBlake2f(hub, p, 3)));
        }
      }
    }

    r.add(
        p.success()
            ? ContextFragment.providesReturnData(
                hub, hub.currentFrame().contextNumber(), hub.newChildContextNumber())
            : ContextFragment.nonExecutionEmptyReturnData(hub));
    return r;
  }
}
