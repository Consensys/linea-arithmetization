/*
 * Copyright ConsenSys AG.
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

package net.consensys.linea.zktracer.module.oob;

import static com.google.common.math.BigIntegerMath.log2;
import static java.lang.Byte.toUnsignedInt;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static net.consensys.linea.zktracer.module.constants.GlobalConstants.GAS_CONST_G_CALL_STIPEND;
import static net.consensys.linea.zktracer.module.constants.GlobalConstants.OOB_INST_BLAKE_CDS;
import static net.consensys.linea.zktracer.module.constants.GlobalConstants.OOB_INST_BLAKE_PARAMS;
import static net.consensys.linea.zktracer.module.constants.GlobalConstants.OOB_INST_CALL;
import static net.consensys.linea.zktracer.module.constants.GlobalConstants.OOB_INST_CDL;
import static net.consensys.linea.zktracer.module.constants.GlobalConstants.OOB_INST_CREATE;
import static net.consensys.linea.zktracer.module.constants.GlobalConstants.OOB_INST_DEPLOYMENT;
import static net.consensys.linea.zktracer.module.constants.GlobalConstants.OOB_INST_ECADD;
import static net.consensys.linea.zktracer.module.constants.GlobalConstants.OOB_INST_ECMUL;
import static net.consensys.linea.zktracer.module.constants.GlobalConstants.OOB_INST_ECPAIRING;
import static net.consensys.linea.zktracer.module.constants.GlobalConstants.OOB_INST_ECRECOVER;
import static net.consensys.linea.zktracer.module.constants.GlobalConstants.OOB_INST_IDENTITY;
import static net.consensys.linea.zktracer.module.constants.GlobalConstants.OOB_INST_JUMP;
import static net.consensys.linea.zktracer.module.constants.GlobalConstants.OOB_INST_JUMPI;
import static net.consensys.linea.zktracer.module.constants.GlobalConstants.OOB_INST_MODEXP_CDS;
import static net.consensys.linea.zktracer.module.constants.GlobalConstants.OOB_INST_MODEXP_EXTRACT;
import static net.consensys.linea.zktracer.module.constants.GlobalConstants.OOB_INST_MODEXP_LEAD;
import static net.consensys.linea.zktracer.module.constants.GlobalConstants.OOB_INST_MODEXP_PRICING;
import static net.consensys.linea.zktracer.module.constants.GlobalConstants.OOB_INST_MODEXP_XBS;
import static net.consensys.linea.zktracer.module.constants.GlobalConstants.OOB_INST_RDC;
import static net.consensys.linea.zktracer.module.constants.GlobalConstants.OOB_INST_RIPEMD;
import static net.consensys.linea.zktracer.module.constants.GlobalConstants.OOB_INST_SHA2;
import static net.consensys.linea.zktracer.module.constants.GlobalConstants.OOB_INST_SSTORE;
import static net.consensys.linea.zktracer.module.constants.GlobalConstants.OOB_INST_XCALL;
import static net.consensys.linea.zktracer.module.oob.Trace.CT_MAX_BLAKE2F_CDS;
import static net.consensys.linea.zktracer.module.oob.Trace.CT_MAX_BLAKE2F_PARAMS;
import static net.consensys.linea.zktracer.module.oob.Trace.CT_MAX_CALL;
import static net.consensys.linea.zktracer.module.oob.Trace.CT_MAX_CDL;
import static net.consensys.linea.zktracer.module.oob.Trace.CT_MAX_CREATE;
import static net.consensys.linea.zktracer.module.oob.Trace.CT_MAX_DEPLOYMENT;
import static net.consensys.linea.zktracer.module.oob.Trace.CT_MAX_ECADD;
import static net.consensys.linea.zktracer.module.oob.Trace.CT_MAX_ECMUL;
import static net.consensys.linea.zktracer.module.oob.Trace.CT_MAX_ECPAIRING;
import static net.consensys.linea.zktracer.module.oob.Trace.CT_MAX_ECRECOVER;
import static net.consensys.linea.zktracer.module.oob.Trace.CT_MAX_IDENTITY;
import static net.consensys.linea.zktracer.module.oob.Trace.CT_MAX_JUMP;
import static net.consensys.linea.zktracer.module.oob.Trace.CT_MAX_JUMPI;
import static net.consensys.linea.zktracer.module.oob.Trace.CT_MAX_MODEXP_CDS;
import static net.consensys.linea.zktracer.module.oob.Trace.CT_MAX_MODEXP_EXTRACT;
import static net.consensys.linea.zktracer.module.oob.Trace.CT_MAX_MODEXP_LEAD;
import static net.consensys.linea.zktracer.module.oob.Trace.CT_MAX_MODEXP_PRICING;
import static net.consensys.linea.zktracer.module.oob.Trace.CT_MAX_MODEXP_XBS;
import static net.consensys.linea.zktracer.module.oob.Trace.CT_MAX_RDC;
import static net.consensys.linea.zktracer.module.oob.Trace.CT_MAX_RIPEMD;
import static net.consensys.linea.zktracer.module.oob.Trace.CT_MAX_SHA2;
import static net.consensys.linea.zktracer.module.oob.Trace.CT_MAX_SSTORE;
import static net.consensys.linea.zktracer.module.oob.Trace.CT_MAX_XCALL;
import static net.consensys.linea.zktracer.module.oob.Trace.G_QUADDIVISOR;
import static net.consensys.linea.zktracer.types.AddressUtils.getDeploymentAddress;
import static net.consensys.linea.zktracer.types.Conversions.bigIntegerToBoolean;
import static net.consensys.linea.zktracer.types.Conversions.booleanToBigInteger;
import static net.consensys.linea.zktracer.types.Conversions.booleanToInt;

import java.math.BigInteger;
import java.math.RoundingMode;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.consensys.linea.zktracer.ZkTracer;
import net.consensys.linea.zktracer.container.ModuleOperation;
import net.consensys.linea.zktracer.module.add.Add;
import net.consensys.linea.zktracer.module.hub.Hub;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.OobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.opcodes.CallDataLoadOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.opcodes.CallOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.opcodes.CreateOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.opcodes.DeploymentOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.opcodes.JumpOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.opcodes.JumpiOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.opcodes.ReturnDataCopyOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.opcodes.SstoreOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.opcodes.XCallOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.precompiles.Blake2fCallDataSizeOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.precompiles.Blake2fParamsOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.precompiles.ModexpCallDataSizeOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.precompiles.ModexpExtractOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.precompiles.ModexpLeadOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.precompiles.ModexpPricingOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.precompiles.ModexpXbsOobCall;
import net.consensys.linea.zktracer.module.hub.fragment.imc.call.oob.precompiles.PrecompileCommonOobCall;
import net.consensys.linea.zktracer.module.mod.Mod;
import net.consensys.linea.zktracer.module.wcp.Wcp;
import net.consensys.linea.zktracer.opcode.OpCode;
import net.consensys.linea.zktracer.types.EWord;
import net.consensys.linea.zktracer.types.UnsignedByte;
import org.apache.tuweni.bytes.Bytes;
import org.hyperledger.besu.datatypes.Address;
import org.hyperledger.besu.evm.account.Account;
import org.hyperledger.besu.evm.frame.MessageFrame;
import org.hyperledger.besu.evm.internal.Words;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class OobOperation extends ModuleOperation {
  @EqualsAndHashCode.Include private int oobInst;
  @EqualsAndHashCode.Include @Setter private OobCall oobCall;

  private int maxCt;
  private boolean isJump;
  private boolean isJumpi;
  private boolean isRdc;
  private boolean isCdl;
  private boolean isXCall;
  private boolean isCall;
  private boolean isCreate;
  private boolean isSstore;
  private boolean isDeployment;

  private boolean isEcRecover;
  private boolean isSha2;
  private boolean isRipemd;
  private boolean isIdentity;
  private boolean isEcadd;
  private boolean isEcmul;
  private boolean isEcpairing;
  private boolean isBlake2FCds;
  private boolean isBlake2FParams;
  private boolean isModexpCds;
  private boolean isModexpXbs;
  private boolean isModexpLead;
  private boolean prcModexpPricing;
  private boolean prcModexpExtract;

  private boolean isModexpBbs;
  private boolean isModexpEbs;
  private boolean isModexpMbs;

  private final boolean[] addFlag;
  private final boolean[] modFlag;
  private final boolean[] wcpFlag;

  private final UnsignedByte[] outgoingInst;

  private final BigInteger[] outgoingData1;
  private final BigInteger[] outgoingData2;
  private final BigInteger[] outgoingData3;
  private final BigInteger[] outgoingData4;

  private final BigInteger[] outgoingResLo;

  private int wghtSum;

  private BigInteger precompileCost;

  // Modules for lookups
  private final Add add;
  private final Mod mod;
  private final Wcp wcp;
  private final Hub hub;

  public OobOperation(
      OobCall oobCall,
      final MessageFrame frame,
      final Add add,
      final Mod mod,
      final Wcp wcp,
      final Hub hub) {
    this.oobCall = oobCall;

    this.add = add;
    this.mod = mod;
    this.wcp = wcp;
    this.hub = hub;

    setFlagsAndWghtSumAndIncomingInstAndMaxCt();

    // Init arrays
    int nRows = nRows();
    addFlag = new boolean[nRows];
    modFlag = new boolean[nRows];
    wcpFlag = new boolean[nRows];

    outgoingInst = new UnsignedByte[nRows];
    outgoingData1 = new BigInteger[nRows];
    outgoingData2 = new BigInteger[nRows];
    outgoingData3 = new BigInteger[nRows];
    outgoingData4 = new BigInteger[nRows];
    outgoingResLo = new BigInteger[nRows];

    // TODO: ensure that the nonce update for CREATE is not already done
    populateColumns(frame);
  }

  private void setFlagsAndWghtSumAndIncomingInstAndMaxCt() {
    switch (oobCall.oobInstruction()) {
      case OOB_INST_JUMP -> {
        isJump = true;
        wghtSum = OOB_INST_JUMP;
        maxCt = CT_MAX_JUMP;
      }
      case OOB_INST_JUMPI -> {
        isJumpi = true;
        wghtSum = OOB_INST_JUMPI;
        maxCt = CT_MAX_JUMPI;
      }
      case OOB_INST_RDC -> {
        isRdc = true;
        wghtSum = OOB_INST_RDC;
        maxCt = CT_MAX_RDC;
      }
      case OOB_INST_CDL -> {
        isCdl = true;
        wghtSum = OOB_INST_CDL;
        maxCt = CT_MAX_CDL;
      }
      case OOB_INST_CALL -> {
        isCall = true;
        wghtSum = OOB_INST_CALL;
        maxCt = CT_MAX_CALL;
      }
      case OOB_INST_XCALL -> {
        isXCall = true;
        wghtSum = OOB_INST_XCALL;
        maxCt = CT_MAX_XCALL;
      }
      case OOB_INST_CREATE -> {
        isCreate = true;
        wghtSum = OOB_INST_CREATE;
        maxCt = CT_MAX_CREATE;
      }
      case OOB_INST_SSTORE -> {
        isSstore = true;
        wghtSum = OOB_INST_SSTORE;
        maxCt = CT_MAX_SSTORE;
      }
      case OOB_INST_DEPLOYMENT -> {
        isDeployment = true;
        wghtSum = OOB_INST_DEPLOYMENT;
        maxCt = CT_MAX_DEPLOYMENT;
      }
      case OOB_INST_ECRECOVER -> {
        isEcRecover = true;
        wghtSum = OOB_INST_ECRECOVER;
        maxCt = CT_MAX_ECRECOVER;
      }
      case OOB_INST_SHA2 -> {
        isSha2 = true;
        wghtSum = OOB_INST_SHA2;
        maxCt = CT_MAX_SHA2;
      }
      case OOB_INST_RIPEMD -> {
        isRipemd = true;
        wghtSum = OOB_INST_RIPEMD;
        maxCt = CT_MAX_RIPEMD;
      }
      case OOB_INST_IDENTITY -> {
        isIdentity = true;
        wghtSum = OOB_INST_IDENTITY;
        maxCt = CT_MAX_IDENTITY;
      }
      case OOB_INST_ECADD -> {
        isEcadd = true;
        wghtSum = OOB_INST_ECADD;
        maxCt = CT_MAX_ECADD;
      }
      case OOB_INST_ECMUL -> {
        isEcmul = true;
        wghtSum = OOB_INST_ECMUL;
        maxCt = CT_MAX_ECMUL;
      }
      case OOB_INST_ECPAIRING -> {
        isEcpairing = true;
        wghtSum = OOB_INST_ECPAIRING;
        maxCt = CT_MAX_ECPAIRING;
      }
      case OOB_INST_BLAKE_CDS -> {
        isBlake2FCds = true;
        wghtSum = OOB_INST_BLAKE_CDS;
        maxCt = CT_MAX_BLAKE2F_CDS;
      }
      case OOB_INST_BLAKE_PARAMS -> {
        isBlake2FParams = true;
        wghtSum = OOB_INST_BLAKE_PARAMS;
        maxCt = CT_MAX_BLAKE2F_PARAMS;
      }
      case OOB_INST_MODEXP_CDS -> {
        isModexpCds = true;
        wghtSum = OOB_INST_MODEXP_CDS;
        maxCt = CT_MAX_MODEXP_CDS;
      }
      case OOB_INST_MODEXP_XBS -> {
        isModexpXbs = true;

        switch (((ModexpXbsOobCall) oobCall).getModexpXbsCase()) {
          case OOB_INST_MODEXP_BBS -> isModexpBbs = true;
          case OOB_INST_MODEXP_EBS -> isModexpEbs = true;
          case OOB_INST_MODEXP_MBS -> isModexpMbs = true;
        }

        wghtSum = OOB_INST_MODEXP_XBS;
        maxCt = CT_MAX_MODEXP_XBS;
      }
      case OOB_INST_MODEXP_LEAD -> {
        isModexpLead = true;
        wghtSum = OOB_INST_MODEXP_LEAD;
        maxCt = CT_MAX_MODEXP_LEAD;
      }
      case OOB_INST_MODEXP_PRICING -> {
        prcModexpPricing = true;
        wghtSum = OOB_INST_MODEXP_PRICING;
        maxCt = CT_MAX_MODEXP_PRICING;
      }
      case OOB_INST_MODEXP_EXTRACT -> {
        prcModexpExtract = true;
        wghtSum = OOB_INST_MODEXP_EXTRACT;
        maxCt = CT_MAX_MODEXP_EXTRACT;
      }
    }
    oobInst = wghtSum;
  }

  public boolean isInst() {
    return isJump
        || isJumpi
        || isRdc
        || isCdl
        || isCall
        || isXCall
        || isCreate
        || isSstore
        || isDeployment;
  }

  public boolean isCommonPrecompile() {
    return isEcRecover || isSha2 || isRipemd || isIdentity || isEcadd || isEcmul || isEcpairing;
  }

  public boolean isBlakePrecompile() {
    return isBlake2FCds || isBlake2FParams;
  }

  public boolean isModexpPrecompile() {
    return isModexpCds || isModexpXbs || isModexpLead || prcModexpPricing || prcModexpExtract;
  }

  public boolean isPrecompile() {
    return isCommonPrecompile() || isBlakePrecompile() || isModexpPrecompile();
  }

  public int nRows() {
    return maxCt + 1;
  }

  private void populateColumns(final MessageFrame frame) {
    final OpCode opCode = OpCode.of(frame.getCurrentOperation().getOpcode());

    if (isInst()) {
      if (isJump) {
        JumpOobCall jumpOobCall = (JumpOobCall) oobCall;
        jumpOobCall.setPcNew(EWord.of(frame.getStackItem(0)));
        jumpOobCall.setCodeSize(BigInteger.valueOf(frame.getCode().getSize()));
        setJump(jumpOobCall);
      } else if (isJumpi) {
        JumpiOobCall jumpiOobCall = (JumpiOobCall) oobCall;
        jumpiOobCall.setPcNew(EWord.of(frame.getStackItem(0)));
        jumpiOobCall.setJumpCondition(EWord.of(frame.getStackItem(1)));
        jumpiOobCall.setCodeSize(BigInteger.valueOf(frame.getCode().getSize()));
        setJumpi(jumpiOobCall);
      } else if (isRdc) {
        ReturnDataCopyOobCall rdcOobCall = (ReturnDataCopyOobCall) oobCall;
        rdcOobCall.setOffset(EWord.of(frame.getStackItem(0)));
        rdcOobCall.setSize(EWord.of(frame.getStackItem(1)));
        rdcOobCall.setRds(BigInteger.valueOf(frame.getReturnData().size()));
        setRdc(rdcOobCall);
      } else if (isCdl) {
        CallDataLoadOobCall cdlOobCall = (CallDataLoadOobCall) oobCall;
        cdlOobCall.setOffset(EWord.of(frame.getStackItem(0)));
        cdlOobCall.setCds(BigInteger.valueOf(frame.getInputData().size()));
        setCdl(cdlOobCall);
      } else if (isSstore) {
        final SstoreOobCall sstoreOobCall = (SstoreOobCall) oobCall;
        sstoreOobCall.setGas(BigInteger.valueOf(frame.getRemainingGas()));
        setSstore(sstoreOobCall);
      } else if (isDeployment) {
        final DeploymentOobCall deploymentOobCall = (DeploymentOobCall) oobCall;
        deploymentOobCall.setSize(EWord.of(frame.getStackItem(0)));
        setDeployment(deploymentOobCall);
      } else if (isXCall) {
        XCallOobCall xCallOobCall = (XCallOobCall) oobCall;
        xCallOobCall.setValue(EWord.of(frame.getStackItem(2)));
        setXCall(xCallOobCall);
      } else if (isCall) {
        final Account callerAccount = frame.getWorldUpdater().get(frame.getRecipientAddress());
        // DELEGATECALL, STATICCALL cases
        EWord value = EWord.ZERO;
        boolean nonZeroValue = false;
        // CALL, CALLCODE cases
        if (opCode == OpCode.CALL || opCode == OpCode.CALLCODE) {
          value = EWord.of(frame.getStackItem(2));
          nonZeroValue = !frame.getStackItem(2).isZero();
        }
        CallOobCall callOobCall = (CallOobCall) oobCall;
        callOobCall.setValue(value);
        callOobCall.setBalance(callerAccount.getBalance().toUnsignedBigInteger());
        callOobCall.setCallStackDepth(BigInteger.valueOf(frame.getDepth()));
        setCall(callOobCall);
      } else if (isCreate) {
        final Account creatorAccount = frame.getWorldUpdater().get(frame.getRecipientAddress());
        final Address deploymentAddress = getDeploymentAddress(frame);
        final Account deployedAccount = frame.getWorldUpdater().get(deploymentAddress);

        long nonce = 0;
        boolean hasCode = false;
        if (deployedAccount != null) {
          nonce = deployedAccount.getNonce();
          hasCode = deployedAccount.hasCode();
        }

        final CreateOobCall createOobCall = (CreateOobCall) oobCall;
        createOobCall.setValue(EWord.of(frame.getStackItem(0)));
        createOobCall.setBalance(creatorAccount.getBalance().toUnsignedBigInteger());
        createOobCall.setNonce(BigInteger.valueOf(nonce));
        createOobCall.setHasCode(hasCode);
        createOobCall.setCallStackDepth(BigInteger.valueOf(frame.getDepth()));
        setCreate(createOobCall);
      }
    } else if (isPrecompile()) {
      // DELEGATECALL, STATICCALL cases
      int argsOffset = 2;
      // this corresponds to argsSize on evm.codes
      int cdsIndex = 3;
      // this corresponds to retSize on evm.codes
      int returnAtCapacityIndex = 5;
      // value is not part of the arguments for DELEGATECALL and STATICCALL
      boolean transfersValue = false;
      // CALL, CALLCODE cases
      if (opCode == OpCode.CALL || opCode == OpCode.CALLCODE) {
        argsOffset = 3;
        cdsIndex = 4;
        returnAtCapacityIndex = 6;
        transfersValue = !frame.getStackItem(2).isZero();
      }

      final BigInteger callGas =
          BigInteger.valueOf(
              ZkTracer.gasCalculator.gasAvailableForChildCall(
                  frame, Words.clampedToLong(frame.getStackItem(0)), transfersValue));

      final BigInteger cds = EWord.of(frame.getStackItem(cdsIndex)).toUnsignedBigInteger();
      // Note that this check will disappear since it will be the MXP module taking care of it
      /* TODO: reenable this check */
      // if (cds.compareTo(EWord.of(frame.getStackItem(cdsIndex)).loBigInt()) > 0) {
      //  throw new IllegalArgumentException("cds hi part is non-zero");
      // }

      final BigInteger returnAtCapacity =
          EWord.of(frame.getStackItem(returnAtCapacityIndex)).toUnsignedBigInteger();

      if (isCommonPrecompile()) {
        PrecompileCommonOobCall prcCommonOobCall = (PrecompileCommonOobCall) oobCall;

        prcCommonOobCall.setCallGas(callGas);
        prcCommonOobCall.setCds(cds);
        prcCommonOobCall.setReturnAtCapacity(returnAtCapacity);
        setPrcCommon(prcCommonOobCall);
        if (isEcRecover || isEcadd || isEcmul) {
          setPrcEcRecoverPrcEcaddPrcEcmul(prcCommonOobCall);
        } else if (isSha2 || isRipemd || isIdentity) {
          setPrcSha2PrcRipemdPrcIdentity(prcCommonOobCall);
        } else if (isEcpairing) {
          setEcpairing(prcCommonOobCall);
        }
      } else if (isModexpPrecompile()) {
        final Bytes unpaddedCallData = frame.shadowReadMemory(argsOffset, cds.longValue());
        // pad unpaddedCallData to 96
        final Bytes paddedCallData =
            cds.intValue() < 96
                ? Bytes.concatenate(unpaddedCallData, Bytes.repeat((byte) 0, 96 - cds.intValue()))
                : unpaddedCallData;

        // cds and the data below can be int when compared (after size check)
        final BigInteger bbs = paddedCallData.slice(0, 32).toUnsignedBigInteger();
        final BigInteger ebs = paddedCallData.slice(32, 32).toUnsignedBigInteger();
        final BigInteger mbs = paddedCallData.slice(64, 32).toUnsignedBigInteger();

        // Check if bbs, ebs and mbs are <= 512
        if (bbs.compareTo(BigInteger.valueOf(512)) > 0
            || ebs.compareTo(BigInteger.valueOf(512)) > 0
            || mbs.compareTo(BigInteger.valueOf(512)) > 0) {
          throw new IllegalArgumentException("byte sizes are too big");
        }

        // pad paddedCallData to 96 + bbs + ebs
        final Bytes doublePaddedCallData =
            cds.intValue() < 96 + bbs.intValue() + ebs.intValue()
                ? Bytes.concatenate(
                    paddedCallData,
                    Bytes.repeat((byte) 0, 96 + bbs.intValue() + ebs.intValue() - cds.intValue()))
                : paddedCallData;

        final BigInteger leadingBytesOfExponent =
            doublePaddedCallData
                .slice(96 + bbs.intValue(), min(ebs.intValue(), 32))
                .toUnsignedBigInteger();

        BigInteger exponentLog;
        if (ebs.intValue() <= 32 && leadingBytesOfExponent.signum() == 0) {
          exponentLog = BigInteger.ZERO;
        } else if (ebs.intValue() <= 32 && leadingBytesOfExponent.signum() != 0) {
          exponentLog = BigInteger.valueOf(log2(leadingBytesOfExponent, RoundingMode.FLOOR));
        } else if (ebs.intValue() > 32 && leadingBytesOfExponent.signum() != 0) {
          exponentLog =
              BigInteger.valueOf(8)
                  .multiply(ebs.subtract(BigInteger.valueOf(32)))
                  .add(BigInteger.valueOf(log2(leadingBytesOfExponent, RoundingMode.FLOOR)));
        } else {
          exponentLog = BigInteger.valueOf(8).multiply(ebs.subtract(BigInteger.valueOf(32)));
        }
        if (isModexpCds) {
          final ModexpCallDataSizeOobCall prcModexpCdsCall = (ModexpCallDataSizeOobCall) oobCall;
          prcModexpCdsCall.setCds(cds);
          setModexpCds(prcModexpCdsCall);
        } else if (isModexpXbs) {
          final ModexpXbsOobCall prcModexpXbsOobCall;
          if (isModexpBbs) {
            prcModexpXbsOobCall = (ModexpXbsOobCall) oobCall;
            prcModexpXbsOobCall.setXbsHi(EWord.of(bbs).hiBigInt());
            prcModexpXbsOobCall.setXbsLo(EWord.of(bbs).loBigInt());
            prcModexpXbsOobCall.setYbsLo(BigInteger.ZERO);
            prcModexpXbsOobCall.setComputeMax(false);
          } else if (isModexpEbs) {
            prcModexpXbsOobCall = (ModexpXbsOobCall) oobCall;
            prcModexpXbsOobCall.setXbsHi(EWord.of(ebs).hiBigInt());
            prcModexpXbsOobCall.setXbsLo(EWord.of(ebs).loBigInt());
            prcModexpXbsOobCall.setYbsLo(BigInteger.ZERO);
            prcModexpXbsOobCall.setComputeMax(false);
          } else if (isModexpMbs) {
            prcModexpXbsOobCall = (ModexpXbsOobCall) oobCall;
            prcModexpXbsOobCall.setXbsHi(EWord.of(mbs).hiBigInt());
            prcModexpXbsOobCall.setXbsLo(EWord.of(mbs).loBigInt());
            prcModexpXbsOobCall.setYbsLo(EWord.of(bbs).loBigInt());
            prcModexpXbsOobCall.setComputeMax(true);
          } else {
            throw new RuntimeException("modexpXbsCase is not set to a valid value");
          }
          setModexpXbs(prcModexpXbsOobCall);
        } else if (isModexpLead) {
          final ModexpLeadOobCall prcModexpLeadOobCall = (ModexpLeadOobCall) oobCall;
          prcModexpLeadOobCall.setBbs(bbs);
          prcModexpLeadOobCall.setCds(cds);
          prcModexpLeadOobCall.setEbs(ebs);
          setModexpLead(prcModexpLeadOobCall);
        } else if (prcModexpPricing) {
          int maxMbsBbs = max(mbs.intValue(), bbs.intValue());
          final ModexpPricingOobCall prcModexpPricingOobCall = (ModexpPricingOobCall) oobCall;
          prcModexpPricingOobCall.setCallGas(callGas);
          prcModexpPricingOobCall.setReturnAtCapacity(returnAtCapacity);
          prcModexpPricingOobCall.setExponentLog(exponentLog);
          prcModexpPricingOobCall.setMaxMbsBbs(maxMbsBbs);
          setPrcModexpPricing(prcModexpPricingOobCall);
        } else if (prcModexpExtract) {
          final ModexpExtractOobCall prcModexpExtractOobCall = (ModexpExtractOobCall) oobCall;
          prcModexpExtractOobCall.setCds(cds);
          prcModexpExtractOobCall.setBbs(bbs);
          prcModexpExtractOobCall.setEbs(ebs);
          prcModexpExtractOobCall.setMbs(mbs);
          setPrcModexpExtract(prcModexpExtractOobCall);
        }
      } else if (isBlakePrecompile()) {
        if (isBlake2FCds) {
          final Blake2fCallDataSizeOobCall prcBlake2FCdsCall = (Blake2fCallDataSizeOobCall) oobCall;
          prcBlake2FCdsCall.setCds(cds);
          prcBlake2FCdsCall.setReturnAtCapacity(returnAtCapacity);
          setBlake2FCds(prcBlake2FCdsCall);
        } else if (isBlake2FParams) {
          final BigInteger blakeR =
              frame
                  .shadowReadMemory(argsOffset, cds.longValue())
                  .slice(0, 4)
                  .toUnsignedBigInteger();

          final BigInteger blakeF =
              BigInteger.valueOf(
                  toUnsignedInt(frame.shadowReadMemory(argsOffset, cds.longValue()).get(212)));

          final Blake2fParamsOobCall prcBlake2FParamsOobCall = (Blake2fParamsOobCall) oobCall;
          prcBlake2FParamsOobCall.setCallGas(callGas);
          prcBlake2FParamsOobCall.setBlakeR(blakeR);
          prcBlake2FParamsOobCall.setBlakeF(blakeF);

          setBlake2FParams(prcBlake2FParamsOobCall);
        }
      } else {
        throw new RuntimeException("no opcode or precompile flag was set to true");
      }
    }
  }

  // Constraint systems for populating lookups
  private void callToADD(
      int k, BigInteger arg1Hi, BigInteger arg1Lo, BigInteger arg2Hi, BigInteger arg2Lo) {
    // TODO: reactivate preconditions and remove conditional initialization
    // Preconditions.checkArgument(arg1Hi.toByteArray().length <= 16);
    // Preconditions.checkArgument(arg1Lo.toByteArray().length <= 16);
    // Preconditions.checkArgument(arg2Hi.toByteArray().length <= 16);
    // Preconditions.checkArgument(arg2Lo.toByteArray().length <= 16);
    final EWord arg1 =
        arg1Lo.toByteArray().length <= 16 ? EWord.of(arg1Hi, arg1Lo) : EWord.of(arg1Lo);
    final EWord arg2 =
        arg2Lo.toByteArray().length <= 16 ? EWord.of(arg2Hi, arg2Lo) : EWord.of(arg2Lo);
    addFlag[k] = true;
    modFlag[k] = false;
    wcpFlag[k] = false;
    outgoingInst[k] = UnsignedByte.of(OpCode.ADD.byteValue());
    outgoingData1[k] = arg1Hi;
    outgoingData2[k] = arg1Lo;
    outgoingData3[k] = arg2Hi;
    outgoingData4[k] = arg2Lo;
    outgoingResLo[k] =
        BigInteger.ZERO; // This value is never used and BigInteger.ZERO is a dummy one

    // lookup
    add.callADD(arg1, arg2);
  }

  private BigInteger callToDIV(
      int k, BigInteger arg1Hi, BigInteger arg1Lo, BigInteger arg2Hi, BigInteger arg2Lo) {
    // TODO: reactivate preconditions and remove conditional initialization
    // Preconditions.checkArgument(arg1Hi.toByteArray().length <= 16);
    // Preconditions.checkArgument(arg1Lo.toByteArray().length <= 16);
    // Preconditions.checkArgument(arg2Hi.toByteArray().length <= 16);
    // Preconditions.checkArgument(arg2Lo.toByteArray().length <= 16);
    final EWord arg1 =
        arg1Lo.toByteArray().length <= 16 ? EWord.of(arg1Hi, arg1Lo) : EWord.of(arg1Lo);
    final EWord arg2 =
        arg2Lo.toByteArray().length <= 16 ? EWord.of(arg2Hi, arg2Lo) : EWord.of(arg2Lo);
    addFlag[k] = false;
    modFlag[k] = true;
    wcpFlag[k] = false;
    outgoingInst[k] = UnsignedByte.of(OpCode.DIV.byteValue());
    outgoingData1[k] = arg1Hi;
    outgoingData2[k] = arg1Lo;
    outgoingData3[k] = arg2Hi;
    outgoingData4[k] = arg2Lo;
    outgoingResLo[k] = mod.callDIV(arg1, arg2);
    return outgoingResLo[k];
  }

  private BigInteger callToMOD(
      int k, BigInteger arg1Hi, BigInteger arg1Lo, BigInteger arg2Hi, BigInteger arg2Lo) {
    // TODO: reactivate preconditions and remove conditional initialization
    // Preconditions.checkArgument(arg1Hi.toByteArray().length <= 16);
    // Preconditions.checkArgument(arg1Lo.toByteArray().length <= 16);
    // Preconditions.checkArgument(arg2Hi.toByteArray().length <= 16);
    // Preconditions.checkArgument(arg2Lo.toByteArray().length <= 16);
    final EWord arg1 =
        arg1Lo.toByteArray().length <= 16 ? EWord.of(arg1Hi, arg1Lo) : EWord.of(arg1Lo);
    final EWord arg2 =
        arg2Lo.toByteArray().length <= 16 ? EWord.of(arg2Hi, arg2Lo) : EWord.of(arg2Lo);
    addFlag[k] = false;
    modFlag[k] = true;
    wcpFlag[k] = false;
    outgoingInst[k] = UnsignedByte.of(OpCode.MOD.byteValue());
    outgoingData1[k] = arg1Hi;
    outgoingData2[k] = arg1Lo;
    outgoingData3[k] = arg2Hi;
    outgoingData4[k] = arg2Lo;
    outgoingResLo[k] = mod.callMOD(arg1, arg2);
    return outgoingResLo[k];
  }

  private boolean callToLT(
      int k, BigInteger arg1Hi, BigInteger arg1Lo, BigInteger arg2Hi, BigInteger arg2Lo) {
    // TODO: reactivate preconditions and remove conditional initialization
    // Preconditions.checkArgument(arg1Hi.toByteArray().length <= 16);
    // Preconditions.checkArgument(arg1Lo.toByteArray().length <= 16);
    // Preconditions.checkArgument(arg2Hi.toByteArray().length <= 16);
    // Preconditions.checkArgument(arg2Lo.toByteArray().length <= 16);
    final EWord arg1 =
        arg1Lo.toByteArray().length <= 16 ? EWord.of(arg1Hi, arg1Lo) : EWord.of(arg1Lo);
    final EWord arg2 =
        arg2Lo.toByteArray().length <= 16 ? EWord.of(arg2Hi, arg2Lo) : EWord.of(arg2Lo);
    addFlag[k] = false;
    modFlag[k] = false;
    wcpFlag[k] = true;
    outgoingInst[k] = UnsignedByte.of(OpCode.LT.byteValue());
    outgoingData1[k] = arg1Hi;
    outgoingData2[k] = arg1Lo;
    outgoingData3[k] = arg2Hi;
    outgoingData4[k] = arg2Lo;
    boolean r = wcp.callLT(arg1, arg2);
    outgoingResLo[k] = booleanToBigInteger(r);
    return r;
  }

  private boolean callToGT(
      int k, BigInteger arg1Hi, BigInteger arg1Lo, BigInteger arg2Hi, BigInteger arg2Lo) {
    // TODO: reactivate preconditions and remove conditional initialization
    // Preconditions.checkArgument(arg1Hi.toByteArray().length <= 16);
    // Preconditions.checkArgument(arg1Lo.toByteArray().length <= 16);
    // Preconditions.checkArgument(arg2Hi.toByteArray().length <= 16);
    // Preconditions.checkArgument(arg2Lo.toByteArray().length <= 16);
    final EWord arg1 =
        arg1Lo.toByteArray().length <= 16 ? EWord.of(arg1Hi, arg1Lo) : EWord.of(arg1Lo);
    final EWord arg2 =
        arg2Lo.toByteArray().length <= 16 ? EWord.of(arg2Hi, arg2Lo) : EWord.of(arg2Lo);
    addFlag[k] = false;
    modFlag[k] = false;
    wcpFlag[k] = true;
    outgoingInst[k] = UnsignedByte.of(OpCode.GT.byteValue());
    outgoingData1[k] = arg1Hi;
    outgoingData2[k] = arg1Lo;
    outgoingData3[k] = arg2Hi;
    outgoingData4[k] = arg2Lo;
    boolean r = wcp.callGT(arg1, arg2);
    outgoingResLo[k] = booleanToBigInteger(r);
    return r;
  }

  private boolean callToISZERO(final int k, final BigInteger arg1Hi, final BigInteger arg1Lo) {
    // TODO: reactivate preconditions and remove conditional initialization
    // Preconditions.checkArgument(arg1Hi.toByteArray().length <= 16);
    // Preconditions.checkArgument(arg1Lo.toByteArray().length <= 16);
    final EWord arg1 =
        arg1Lo.toByteArray().length <= 16 ? EWord.of(arg1Hi, arg1Lo) : EWord.of(arg1Lo);
    addFlag[k] = false;
    modFlag[k] = false;
    wcpFlag[k] = true;
    outgoingInst[k] = UnsignedByte.of(OpCode.ISZERO.byteValue());
    outgoingData1[k] = arg1Hi;
    outgoingData2[k] = arg1Lo;
    outgoingData3[k] = BigInteger.ZERO;
    outgoingData4[k] = BigInteger.ZERO;
    boolean r = wcp.callISZERO(arg1);
    outgoingResLo[k] = booleanToBigInteger(r);
    return r;
  }

  private boolean callToEQ(
      int k, BigInteger arg1Hi, BigInteger arg1Lo, BigInteger arg2Hi, BigInteger arg2Lo) {
    // TODO: reactivate preconditions and remove conditional initialization
    // Preconditions.checkArgument(arg1Hi.toByteArray().length <= 16);
    // Preconditions.checkArgument(arg1Lo.toByteArray().length <= 16);
    // Preconditions.checkArgument(arg2Hi.toByteArray().length <= 16);
    // Preconditions.checkArgument(arg2Lo.toByteArray().length <= 16);
    final EWord arg1 =
        arg1Lo.toByteArray().length <= 16 ? EWord.of(arg1Hi, arg1Lo) : EWord.of(arg1Lo);
    final EWord arg2 =
        arg2Lo.toByteArray().length <= 16 ? EWord.of(arg2Hi, arg2Lo) : EWord.of(arg2Lo);
    addFlag[k] = false;
    modFlag[k] = false;
    wcpFlag[k] = true;
    outgoingInst[k] = UnsignedByte.of(OpCode.EQ.byteValue());
    outgoingData1[k] = arg1Hi;
    outgoingData2[k] = arg1Lo;
    outgoingData3[k] = arg2Hi;
    outgoingData4[k] = arg2Lo;
    boolean r = wcp.callEQ(arg1, arg2);
    outgoingResLo[k] = booleanToBigInteger(r);
    return r;
  }

  private void noCall(int k) {
    addFlag[k] = false;
    modFlag[k] = false;
    wcpFlag[k] = false;
    outgoingInst[k] = UnsignedByte.of(0);
    outgoingData1[k] = BigInteger.ZERO;
    outgoingData2[k] = BigInteger.ZERO;
    outgoingData3[k] = BigInteger.ZERO;
    outgoingData4[k] = BigInteger.ZERO;
    outgoingResLo[k] = BigInteger.ZERO;
  }

  // Methods to populate columns
  private void setJump(JumpOobCall jumpOobCall) {
    // row i
    final boolean validPcNew =
        callToLT(
            0,
            jumpOobCall.pcNewHi(),
            jumpOobCall.pcNewLo(),
            BigInteger.ZERO,
            jumpOobCall.getCodeSize());

    // Set jumpGuaranteedException
    jumpOobCall.setJumpGuaranteedException(!validPcNew);

    // Set jumpMustBeAttempted
    jumpOobCall.setJumpMustBeAttempted(validPcNew);
  }

  private void setJumpi(JumpiOobCall jumpiOobCall) {
    // row i
    final boolean validPcNew =
        callToLT(
            0,
            jumpiOobCall.pcNewHi(),
            jumpiOobCall.pcNewLo(),
            BigInteger.ZERO,
            jumpiOobCall.getCodeSize());

    // row i + 1
    final boolean jumpCondIsZero =
        callToISZERO(1, jumpiOobCall.jumpConditionHi(), jumpiOobCall.jumpConditionLo());

    // Set jumpNotAttempted
    jumpiOobCall.setJumpNotAttempted(jumpCondIsZero);

    // Set jumpGuaranteedException
    jumpiOobCall.setJumpGuanranteedException(!jumpCondIsZero && !validPcNew);

    // Set jumpMustBeAttempted
    jumpiOobCall.setJumpMustBeAttempted(!jumpCondIsZero && validPcNew);
  }

  private void setRdc(ReturnDataCopyOobCall rdcOobCall) {
    // row i
    final boolean rdcRoob = !callToISZERO(0, rdcOobCall.offsetHi(), rdcOobCall.sizeHi());

    // row i + 1
    if (!rdcRoob) {
      callToADD(1, BigInteger.ZERO, rdcOobCall.offsetLo(), BigInteger.ZERO, rdcOobCall.sizeLo());
    } else {
      noCall(1);
    }
    final BigInteger sum =
        addFlag[1] ? rdcOobCall.offsetLo().add(rdcOobCall.sizeLo()) : BigInteger.ZERO;

    // row i + 2
    boolean rdcSoob = false;
    if (!rdcRoob) {
      rdcSoob =
          callToGT(
              2,
              EWord.of(sum).hiBigInt(),
              EWord.of(sum).loBigInt(),
              BigInteger.ZERO,
              rdcOobCall.getRds());
    } else {
      noCall(2);
    }

    // Set rdcx
    rdcOobCall.setRdcx(rdcRoob || rdcSoob);
  }

  private void setCdl(CallDataLoadOobCall cdlOobCall) {
    // row i
    final boolean touchesRam =
        callToLT(
            0, cdlOobCall.offsetHi(), cdlOobCall.offsetLo(), BigInteger.ZERO, cdlOobCall.getCds());

    // Set cdlOutOfBounds
    cdlOobCall.setCdlOutOfBounds(!touchesRam);
  }

  private void setSstore(SstoreOobCall sstoreOobCall) {
    // row i
    final boolean sufficientGas =
        callToLT(
            0,
            BigInteger.ZERO,
            BigInteger.valueOf(GAS_CONST_G_CALL_STIPEND),
            BigInteger.ZERO,
            sstoreOobCall.getGas());

    // Set sstorex
    sstoreOobCall.setSstorex(!sufficientGas);
  }

  private void setDeployment(DeploymentOobCall deploymentOobCall) {
    // row i
    final boolean exceedsMaxCodeSize =
        callToLT(
            0,
            BigInteger.ZERO,
            BigInteger.valueOf(24576),
            deploymentOobCall.sizeHi(),
            deploymentOobCall.sizeLo());

    // Set maxCodeSizeException
    deploymentOobCall.setMaxCodeSizeException(exceedsMaxCodeSize);
  }

  private void setXCall(XCallOobCall xCallOobCall) {
    // row i
    boolean valueIsZero = callToISZERO(0, xCallOobCall.valueHi(), xCallOobCall.valueLo());

    // Set valueIsNonzero
    xCallOobCall.setValueIsNonzero(!valueIsZero);

    // Set valueIsZero
    xCallOobCall.setValueIsZero(valueIsZero);
  }

  private void setCall(CallOobCall callOobCall) {
    // row i
    boolean insufficientBalanceAbort =
        callToLT(
            0,
            BigInteger.ZERO,
            callOobCall.getBalance(),
            callOobCall.valueHi(),
            callOobCall.valueLo());

    // row i + 1
    final boolean callStackDepthAbort =
        !callToLT(
            1,
            BigInteger.ZERO,
            callOobCall.getCallStackDepth(),
            BigInteger.ZERO,
            BigInteger.valueOf(1024));

    // row i + 2
    boolean valueIsZero = callToISZERO(2, callOobCall.valueHi(), callOobCall.valueLo());

    // Set valueIsNonzero
    callOobCall.setValueIsNonzero(!valueIsZero);

    // Set abortingCondition
    callOobCall.setAbortingCondition(insufficientBalanceAbort || callStackDepthAbort);
  }

  private void setCreate(CreateOobCall createOobCall) {
    // row i
    final boolean insufficientBalanceAbort =
        callToLT(
            0,
            BigInteger.ZERO,
            createOobCall.getBalance(),
            createOobCall.valueHi(),
            createOobCall.valueLo());

    // row i + 1
    final boolean callStackDepthAbort =
        !callToLT(
            1,
            BigInteger.ZERO,
            createOobCall.getCallStackDepth(),
            BigInteger.ZERO,
            BigInteger.valueOf(1024));

    // row i + 2
    final boolean nonzeroNonce = !callToISZERO(2, BigInteger.ZERO, createOobCall.getNonce());

    // Set aborting condition
    createOobCall.setAbortingCondition(insufficientBalanceAbort || callStackDepthAbort);

    // Set failureCondition
    createOobCall.setFailureCondition(
        !createOobCall.isAbortingCondition() && (createOobCall.isHasCode() || nonzeroNonce));
  }

  private void setPrcCommon(PrecompileCommonOobCall prcOobCall) {
    // row i
    final boolean cdsIsZero = callToISZERO(0, BigInteger.ZERO, prcOobCall.getCds());

    // row i + 1
    final boolean returnAtCapacityIsZero =
        callToISZERO(1, BigInteger.ZERO, prcOobCall.getReturnAtCapacity());

    // Set cdsIsZero
    prcOobCall.setCdsIsZero(cdsIsZero);

    // Set returnAtCapacityIsZero
    prcOobCall.setReturnAtCapacityNonZero(!returnAtCapacityIsZero);
  }

  private void setPrcEcRecoverPrcEcaddPrcEcmul(PrecompileCommonOobCall prcCommonOobCall) {
    precompileCost =
        BigInteger.valueOf(
            3000L * booleanToInt(isEcRecover)
                + 150L * booleanToInt(isEcadd)
                + 6000L * booleanToInt(isEcmul));

    // row i + 2
    final boolean insufficientGas =
        callToLT(
            2, BigInteger.ZERO, prcCommonOobCall.getCallGas(), BigInteger.ZERO, precompileCost);

    // Set hubSuccess
    final boolean hubSuccess = !insufficientGas;
    prcCommonOobCall.setHubSuccess(hubSuccess);

    // Set returnGas
    final BigInteger returnGas =
        hubSuccess ? prcCommonOobCall.getCallGas().subtract(precompileCost) : BigInteger.ZERO;
    prcCommonOobCall.setReturnGas(returnGas);
  }

  private void setPrcSha2PrcRipemdPrcIdentity(PrecompileCommonOobCall prcCommonOobCall) {
    // row i + 2
    final BigInteger ceil =
        callToDIV(
            2,
            BigInteger.ZERO,
            prcCommonOobCall.getCds().add(BigInteger.valueOf(31)),
            BigInteger.ZERO,
            BigInteger.valueOf(32));

    precompileCost =
        (BigInteger.valueOf(5).add(ceil))
            .multiply(
                BigInteger.valueOf(
                    12L * booleanToInt(isSha2)
                        + 120L * booleanToInt(isRipemd)
                        + 3L * booleanToInt(isIdentity)));

    // row i + 3
    final boolean insufficientGas =
        callToLT(
            3, BigInteger.ZERO, prcCommonOobCall.getCallGas(), BigInteger.ZERO, precompileCost);

    // Set hubSuccess
    final boolean hubSuccess = !insufficientGas;
    prcCommonOobCall.setHubSuccess(hubSuccess);

    // Set returnGas
    final BigInteger returnGas =
        hubSuccess ? prcCommonOobCall.getCallGas().subtract(precompileCost) : BigInteger.ZERO;
    prcCommonOobCall.setReturnGas(returnGas);
  }

  private void setEcpairing(PrecompileCommonOobCall prcCommonOobCall) {
    // row i + 2
    final BigInteger remainder =
        callToMOD(
            2,
            BigInteger.ZERO,
            prcCommonOobCall.getCds(),
            // 16 bytes
            BigInteger.ZERO,
            BigInteger.valueOf(192));

    // row i + 3
    final boolean isMultipleOf192 = callToISZERO(3, BigInteger.ZERO, remainder);

    precompileCost = BigInteger.ZERO;
    if (isMultipleOf192) {
      precompileCost =
          BigInteger.valueOf(45000)
              .add(
                  BigInteger.valueOf(34000)
                      .multiply(prcCommonOobCall.getCds().divide(BigInteger.valueOf(192))));
    }

    // row i + 4
    boolean insufficientGas = false;
    if (isMultipleOf192) {
      insufficientGas =
          callToLT(
              4, BigInteger.ZERO, prcCommonOobCall.getCallGas(), BigInteger.ZERO, precompileCost);
    } else {
      noCall(4);
    }

    // Set hubSuccess
    final boolean hubSuccess = isMultipleOf192 && !insufficientGas;
    prcCommonOobCall.setHubSuccess(hubSuccess);

    // Set returnGas
    final BigInteger returnGas =
        hubSuccess ? prcCommonOobCall.getCallGas().subtract(precompileCost) : BigInteger.ZERO;
    prcCommonOobCall.setReturnGas(returnGas);
  }

  private void setModexpCds(ModexpCallDataSizeOobCall prcModexpCdsCall) {
    // row i
    final boolean extractBbs =
        callToLT(0, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, prcModexpCdsCall.getCds());

    // row i + 1
    final boolean extractEbs =
        callToLT(
            1, BigInteger.ZERO, prcModexpCdsCall.getCds(), BigInteger.ZERO, BigInteger.valueOf(32));

    // row i + 2
    final boolean extractMbs =
        callToLT(
            2, BigInteger.ZERO, prcModexpCdsCall.getCds(), BigInteger.ZERO, BigInteger.valueOf(64));

    // Set extractBbs
    prcModexpCdsCall.setExtractBbs(extractBbs);

    // Set extractEbs
    prcModexpCdsCall.setExtractEbs(extractEbs);

    // Set extractMbs
    prcModexpCdsCall.setExtractMbs(extractMbs);
  }

  private void setModexpXbs(ModexpXbsOobCall prcModexpXbsOobCall) {
    // row i
    final boolean compTo512 =
        callToLT(
            0,
            prcModexpXbsOobCall.getXbsHi(),
            prcModexpXbsOobCall.getXbsLo(),
            BigInteger.ZERO,
            BigInteger.valueOf(513));

    // row i + 1
    final boolean comp =
        callToLT(
            1,
            BigInteger.ZERO,
            prcModexpXbsOobCall.getXbsLo(),
            BigInteger.ZERO,
            prcModexpXbsOobCall.getYbsLo());

    // row i + 2
    callToISZERO(2, BigInteger.ZERO, prcModexpXbsOobCall.getXbsLo());

    // Set maxXbsYbs and xbsNonZero
    if (!prcModexpXbsOobCall.isComputeMax()) {
      prcModexpXbsOobCall.setMaxXbsYbs(BigInteger.ZERO);
      prcModexpXbsOobCall.setXbsNonZero(false);
    } else {
      prcModexpXbsOobCall.setMaxXbsYbs(
          comp ? prcModexpXbsOobCall.getYbsLo() : prcModexpXbsOobCall.getXbsLo());
      prcModexpXbsOobCall.setXbsNonZero(!bigIntegerToBoolean(outgoingResLo[2]));
    }
  }

  private void setModexpLead(ModexpLeadOobCall prcModexpLeadOobCall) {
    // row i
    final boolean ebsIsZero = callToISZERO(0, BigInteger.ZERO, prcModexpLeadOobCall.getEbs());

    // row i + 1
    final boolean ebsLessThan32 =
        callToLT(
            1,
            BigInteger.ZERO,
            prcModexpLeadOobCall.getEbs(),
            BigInteger.ZERO,
            BigInteger.valueOf(32));

    // row i + 2
    final boolean callDataContainsExponentBytes =
        callToLT(
            2,
            BigInteger.ZERO,
            BigInteger.valueOf(96).add(prcModexpLeadOobCall.getBbs()),
            BigInteger.ZERO,
            prcModexpLeadOobCall.getCds());

    // row i + 3
    boolean comp = false;
    if (callDataContainsExponentBytes) {
      comp =
          callToLT(
              3,
              BigInteger.ZERO,
              prcModexpLeadOobCall
                  .getCds()
                  .subtract(BigInteger.valueOf(96).add(prcModexpLeadOobCall.getBbs())),
              BigInteger.ZERO,
              BigInteger.valueOf(32));
    } else {
      noCall(3);
    }

    // Set loadLead
    final boolean loadLead = callDataContainsExponentBytes && !ebsIsZero;
    prcModexpLeadOobCall.setLoadLead(loadLead);

    // Set cdsCutoff
    if (!callDataContainsExponentBytes) {
      prcModexpLeadOobCall.setCdsCutoff(0);
    } else {
      prcModexpLeadOobCall.setCdsCutoff(
          comp
              ? (prcModexpLeadOobCall
                  .getCds()
                  .subtract(BigInteger.valueOf(96).add(prcModexpLeadOobCall.getBbs()))
                  .intValue())
              : 32);
    }
    // Set ebsCutoff
    prcModexpLeadOobCall.setEbsCutoff(
        ebsLessThan32 ? prcModexpLeadOobCall.getEbs().intValue() : 32);

    // Set subEbs32
    prcModexpLeadOobCall.setSubEbs32(
        ebsLessThan32 ? 0 : prcModexpLeadOobCall.getEbs().intValue() - 32);
  }

  private void setPrcModexpPricing(ModexpPricingOobCall prcModexpPricingOobCall) {
    // row i
    final boolean returnAtCapacityIsZero =
        callToISZERO(0, BigInteger.ZERO, prcModexpPricingOobCall.getReturnAtCapacity());

    // row i + 1
    final boolean exponentLogIsZero =
        callToISZERO(1, BigInteger.ZERO, prcModexpPricingOobCall.getExponentLog());

    // row i + 2
    final BigInteger fOfMax =
        callToDIV(
            2,
            BigInteger.ZERO,
            BigInteger.valueOf(
                (long) prcModexpPricingOobCall.getMaxMbsBbs()
                        * prcModexpPricingOobCall.getMaxMbsBbs()
                    + 7),
            BigInteger.ZERO,
            BigInteger.valueOf(8));

    // row i + 3
    BigInteger bigNumerator;
    if (!exponentLogIsZero) {
      bigNumerator = fOfMax.multiply(prcModexpPricingOobCall.getExponentLog());
    } else {
      bigNumerator = fOfMax;
    }
    final BigInteger bigQuotient =
        callToDIV(
            3, BigInteger.ZERO, bigNumerator, BigInteger.ZERO, BigInteger.valueOf(G_QUADDIVISOR));

    // row i + 4
    final boolean bigQuotientLT200 =
        callToLT(4, BigInteger.ZERO, bigQuotient, BigInteger.ZERO, BigInteger.valueOf(200));

    // row i + 5
    precompileCost = bigQuotientLT200 ? BigInteger.valueOf(200) : bigQuotient;

    final boolean ramSuccess =
        !callToLT(
            5,
            BigInteger.ZERO,
            prcModexpPricingOobCall.getCallGas(),
            BigInteger.ZERO,
            precompileCost);

    // Set ramSuccess
    prcModexpPricingOobCall.setRamSuccess(ramSuccess);

    // Set returnGas
    final BigInteger returnGas =
        ramSuccess
            ? prcModexpPricingOobCall.getCallGas().subtract(precompileCost)
            : BigInteger.ZERO;
    prcModexpPricingOobCall.setReturnGas(returnGas);

    // Set returnAtCapacityNonZero
    prcModexpPricingOobCall.setReturnAtCapacityNonZero(!returnAtCapacityIsZero);
  }

  private void setPrcModexpExtract(ModexpExtractOobCall prcModexpExtractOobCall) {
    // row i
    final boolean bbsIsZero = callToISZERO(0, BigInteger.ZERO, prcModexpExtractOobCall.getBbs());

    // row i + 1
    final boolean ebsIsZero = callToISZERO(1, BigInteger.ZERO, prcModexpExtractOobCall.getEbs());

    // row i + 2
    final boolean mbsIsZero = callToISZERO(2, BigInteger.ZERO, prcModexpExtractOobCall.getMbs());

    // row i + 3
    final boolean callDataExtendsBeyondExponent =
        callToLT(
            3,
            BigInteger.ZERO,
            BigInteger.valueOf(96)
                .add(prcModexpExtractOobCall.getBbs().add(prcModexpExtractOobCall.getEbs())),
            BigInteger.ZERO,
            prcModexpExtractOobCall.getCds());

    // Set extractModulus
    final boolean extractModulus = callDataExtendsBeyondExponent && !mbsIsZero;
    prcModexpExtractOobCall.setExtractModulus(extractModulus);

    // Set extractBase
    final boolean extractBase = extractModulus && !bbsIsZero;
    prcModexpExtractOobCall.setExtractBase(extractBase);

    // Set extractExponent
    final boolean extractExponent = extractModulus && !ebsIsZero;
    prcModexpExtractOobCall.setExtractExponent(extractExponent);
  }

  private void setBlake2FCds(Blake2fCallDataSizeOobCall prcBlake2FCdsCall) {
    // row i
    final boolean validCds =
        callToEQ(
            0,
            BigInteger.ZERO,
            prcBlake2FCdsCall.getCds(),
            BigInteger.ZERO,
            BigInteger.valueOf(213));

    // row i + 1
    final boolean returnAtCapacityIsZero =
        callToISZERO(1, BigInteger.ZERO, prcBlake2FCdsCall.getReturnAtCapacity());

    // Set hubSuccess
    prcBlake2FCdsCall.setHubSuccess(validCds);

    // Set returnAtCapacityNonZero
    prcBlake2FCdsCall.setReturnAtCapacityNonZero(!returnAtCapacityIsZero);
  }

  private void setBlake2FParams(Blake2fParamsOobCall prcBlake2FParamsOobCall) {
    // row i
    final boolean sufficientGas =
        !callToLT(
            0,
            BigInteger.ZERO,
            prcBlake2FParamsOobCall.getCallGas(),
            BigInteger.ZERO,
            prcBlake2FParamsOobCall.getBlakeR()); // = ramSuccess

    // row i + 1
    final boolean fIsABit =
        callToEQ(
            1,
            BigInteger.ZERO,
            prcBlake2FParamsOobCall.getBlakeF(),
            BigInteger.ZERO,
            prcBlake2FParamsOobCall.getBlakeF().multiply(prcBlake2FParamsOobCall.getBlakeF()));

    // Set ramSuccess
    final boolean ramSuccess = sufficientGas && fIsABit;
    prcBlake2FParamsOobCall.setRamSuccess(ramSuccess);

    // Set returnGas
    final BigInteger returnGas =
        ramSuccess
            ? (prcBlake2FParamsOobCall.getCallGas().subtract(prcBlake2FParamsOobCall.getBlakeR()))
            : BigInteger.ZERO;

    prcBlake2FParamsOobCall.setReturnGas(returnGas);
  }

  @Override
  protected int computeLineCount() {
    return this.nRows();
  }
}
