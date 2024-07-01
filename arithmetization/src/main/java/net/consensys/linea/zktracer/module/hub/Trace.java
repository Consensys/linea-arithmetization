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

package net.consensys.linea.zktracer.module.hub;

import java.nio.MappedByteBuffer;
import java.util.BitSet;
import java.util.List;

import net.consensys.linea.zktracer.ColumnHeader;
import net.consensys.linea.zktracer.types.UnsignedByte;
import org.apache.tuweni.bytes.Bytes;

/**
 * WARNING: This code is generated automatically.
 *
 * <p>Any modifications to this code may be overwritten and could lead to unexpected behavior.
 * Please DO NOT ATTEMPT TO MODIFY this code directly.
 */
public class Trace {
  public static final int DOM_SUB_STAMP_OFFSET___REVERT = 0x8;
  public static final int DOM_SUB_STAMP_OFFSET___SELFDESTRUCT = 0xc;
  public static final int MULTIPLIER___DOM_SUB_STAMPS = 0x10;
  public static final int MULTIPLIER___STACK_HEIGHT = 0x8;

  private final BitSet filled = new BitSet();
  private int currentLine = 0;

  private final MappedByteBuffer absoluteTransactionNumber;
  private final MappedByteBuffer accFinal;
  private final MappedByteBuffer accFirst;
  private final MappedByteBuffer
      addressHiXorAccountAddressHiXorCcrsStampXorPrcCalleeGasXorStaticGasXorAddressHiXorBatchNum;
  private final MappedByteBuffer
      addressLoXorAccountAddressLoXorExpData1XorHashInfoKeccakHiXorAddressLoXorCoinbaseAddressLo;
  private final MappedByteBuffer alpha;
  private final MappedByteBuffer
      balanceNewXorCallerAddressLoXorExpData3XorPushValueHiXorStorageKeyLoXorInitialBalance;
  private final MappedByteBuffer
      balanceXorByteCodeAddressLoXorExpData2XorHashInfoKeccakLoXorStorageKeyHiXorFromAddressLo;
  private final MappedByteBuffer batchNumber;
  private final MappedByteBuffer callDataOffsetXorMmuSize;
  private final MappedByteBuffer callDataSizeXorMmuSrcId;
  private final MappedByteBuffer callStackDepth;
  private final MappedByteBuffer callerContextNumber;
  private final MappedByteBuffer codeFragmentIndex;
  private final MappedByteBuffer
      codeFragmentIndexXorAccountDeploymentNumberXorExpInstXorPrcCallerGasXorDeploymentNumberXorCallDataSize;
  private final MappedByteBuffer codeHashHiNewXorExpData5XorValueCurrLoXorValue;
  private final MappedByteBuffer
      codeHashHiXorCallValueXorExpData4XorPushValueLoXorValueCurrHiXorToAddressLo;
  private final MappedByteBuffer codeHashLoNewXorMmuLimb2XorValueNextLo;
  private final MappedByteBuffer codeHashLoXorMmuLimb1XorValueNextHi;
  private final MappedByteBuffer
      codeSizeNewXorByteCodeCodeFragmentIndexXorMmuExoSumXorPrcCdsXorFromAddressHi;
  private final MappedByteBuffer
      codeSizeXorByteCodeAddressHiXorMmuAuxIdXorPrcCdoXorDeploymentNumberInftyXorCoinbaseAddressHi;
  private final MappedByteBuffer conAgain;
  private final MappedByteBuffer conFirst;
  private final MappedByteBuffer contextGetsReverted;
  private final MappedByteBuffer contextMayChange;
  private final MappedByteBuffer contextNumber;
  private final MappedByteBuffer contextNumberNew;
  private final MappedByteBuffer contextNumberXorMmuTgtId;
  private final MappedByteBuffer contextRevertStamp;
  private final MappedByteBuffer contextSelfReverts;
  private final MappedByteBuffer contextWillRevert;
  private final MappedByteBuffer counterNsr;
  private final MappedByteBuffer counterTli;
  private final MappedByteBuffer createFailureConditionWontRevertXorIcpx;
  private final MappedByteBuffer createNonemptyInitCodeFailureWillRevertXorInvalidFlag;
  private final MappedByteBuffer createNonemptyInitCodeFailureWontRevertXorJumpx;
  private final MappedByteBuffer
      createNonemptyInitCodeSuccessWillRevertXorJumpDestinationVettingRequired;
  private final MappedByteBuffer createNonemptyInitCodeSuccessWontRevertXorJumpFlag;
  private final MappedByteBuffer delta;
  private final MappedByteBuffer
      deploymentNumberInftyXorByteCodeDeploymentStatusXorMmuPhaseXorPrcRaoXorToAddressHi;
  private final MappedByteBuffer
      deploymentNumberNewXorCallerAddressHiXorMmuRefOffsetXorPrcReturnGas;
  private final MappedByteBuffer
      deploymentNumberXorByteCodeDeploymentNumberXorMmuInstXorPrcRacXorInitCodeSize;
  private final MappedByteBuffer
      deploymentStatusInftyXorIsStaticXorExpFlagXorCallEoaSuccessCallerWillRevertXorAddFlagXorUnconstrainedFirstXorIsDeployment;
  private final MappedByteBuffer
      deploymentStatusNewXorUpdateXorMmuFlagXorCallEoaSuccessCallerWontRevertXorBinFlagXorValueCurrChangesXorIsType2;
  private final MappedByteBuffer
      deploymentStatusXorIsRootXorCcsrFlagXorCallAbortXorAccFlagXorUnconstrainedFinalXorCopyTxcd;
  private final MappedByteBuffer domStamp;
  private final MappedByteBuffer exceptionAhoy;
  private final MappedByteBuffer
      existsNewXorMxpDeploysXorCallPrcFailureXorCallFlagXorValueCurrIsZeroXorStatusCode;
  private final MappedByteBuffer
      existsXorMmuSuccessBitXorCallExceptionXorBtcFlagXorValueCurrIsOrigXorRequiresEvmExecution;
  private final MappedByteBuffer gasActual;
  private final MappedByteBuffer gasCost;
  private final MappedByteBuffer gasExpected;
  private final MappedByteBuffer gasLimit;
  private final MappedByteBuffer gasNext;
  private final MappedByteBuffer gasPrice;
  private final MappedByteBuffer
      hasCodeNewXorMxpMtntopXorCallPrcSuccessCallerWontRevertXorCopyFlagXorValueNextIsOrig;
  private final MappedByteBuffer
      hasCodeXorMxpFlagXorCallPrcSuccessCallerWillRevertXorConFlagXorValueNextIsCurr;
  private final MappedByteBuffer hashInfoStamp;
  private final MappedByteBuffer height;
  private final MappedByteBuffer heightNew;
  private final MappedByteBuffer hubStamp;
  private final MappedByteBuffer hubStampTransactionEnd;
  private final MappedByteBuffer instruction;
  private final MappedByteBuffer
      isPrecompileXorMxpMxpxXorCallSmcFailureCallerWillRevertXorCreateFlagXorValueNextIsZero;
  private final MappedByteBuffer logInfoStamp;
  private final MappedByteBuffer
      markedForSelfdestructNewXorStpExistsXorCallSmcSuccessCallerWillRevertXorDecFlag2XorWarmth;
  private final MappedByteBuffer
      markedForSelfdestructXorOobFlagXorCallSmcFailureCallerWontRevertXorDecFlag1XorValueOrigIsZero;
  private final MappedByteBuffer mmuStamp;
  private final MappedByteBuffer mxpOffset2Hi;
  private final MappedByteBuffer mxpOffset2Lo;
  private final MappedByteBuffer mxpSize1Hi;
  private final MappedByteBuffer mxpSize1Lo;
  private final MappedByteBuffer mxpSize2Hi;
  private final MappedByteBuffer mxpSize2Lo;
  private final MappedByteBuffer mxpStamp;
  private final MappedByteBuffer mxpWords;
  private final MappedByteBuffer nbAdded;
  private final MappedByteBuffer nbRemoved;
  private final MappedByteBuffer nonStackRows;
  private final MappedByteBuffer nonce;
  private final MappedByteBuffer nonceNewXorStpGasPaidOutOfPocketXorGasInitiallyAvailable;
  private final MappedByteBuffer nonceXorStpGasMxpXorBasefee;
  private final MappedByteBuffer oobData1;
  private final MappedByteBuffer oobData2;
  private final MappedByteBuffer oobData3;
  private final MappedByteBuffer oobData4;
  private final MappedByteBuffer oobData5;
  private final MappedByteBuffer oobData6;
  private final MappedByteBuffer oobData7;
  private final MappedByteBuffer oobData8;
  private final MappedByteBuffer peekAtAccount;
  private final MappedByteBuffer peekAtContext;
  private final MappedByteBuffer peekAtMiscellaneous;
  private final MappedByteBuffer peekAtScenario;
  private final MappedByteBuffer peekAtStack;
  private final MappedByteBuffer peekAtStorage;
  private final MappedByteBuffer peekAtTransaction;
  private final MappedByteBuffer prcBlake2FXorKecFlag;
  private final MappedByteBuffer prcEcaddXorLogFlag;
  private final MappedByteBuffer prcEcmulXorLogInfoFlag;
  private final MappedByteBuffer prcEcpairingXorMachineStateFlag;
  private final MappedByteBuffer prcEcrecoverXorMaxcsx;
  private final MappedByteBuffer prcFailureKnownToHubXorModFlag;
  private final MappedByteBuffer prcFailureKnownToRamXorMulFlag;
  private final MappedByteBuffer prcIdentityXorMxpx;
  private final MappedByteBuffer prcModexpXorMxpFlag;
  private final MappedByteBuffer prcRipemd160XorOogx;
  private final MappedByteBuffer prcSha2256XorOpcx;
  private final MappedByteBuffer prcSuccessCallerWillRevertXorPushpopFlag;
  private final MappedByteBuffer prcSuccessCallerWontRevertXorRdcx;
  private final MappedByteBuffer priorityFeePerGas;
  private final MappedByteBuffer programCounter;
  private final MappedByteBuffer programCounterNew;
  private final MappedByteBuffer refundCounter;
  private final MappedByteBuffer refundCounterInfinity;
  private final MappedByteBuffer refundCounterNew;
  private final MappedByteBuffer refundEffective;
  private final MappedByteBuffer returnAtCapacityXorMxpInst;
  private final MappedByteBuffer returnAtOffsetXorOobInst;
  private final MappedByteBuffer returnDataContextNumberXorStpGasStipend;
  private final MappedByteBuffer returnDataOffsetXorStpInstruction;
  private final MappedByteBuffer returnDataSize;
  private final MappedByteBuffer returnExceptionXorShfFlag;
  private final MappedByteBuffer returnFromDeploymentEmptyCodeWillRevertXorSox;
  private final MappedByteBuffer returnFromDeploymentEmptyCodeWontRevertXorSstorex;
  private final MappedByteBuffer returnFromDeploymentNonemptyCodeWillRevertXorStackramFlag;
  private final MappedByteBuffer returnFromDeploymentNonemptyCodeWontRevertXorStackItemPop1;
  private final MappedByteBuffer returnFromMessageCallWillTouchRamXorStackItemPop2;
  private final MappedByteBuffer returnFromMessageCallWontTouchRamXorStackItemPop3;
  private final MappedByteBuffer rlpaddrDepAddrHiXorCallDataContextNumberXorMmuRefSize;
  private final MappedByteBuffer rlpaddrDepAddrLoXorMmuSrcOffsetHiXorValueOrigHi;
  private final MappedByteBuffer
      rlpaddrFlagXorStpFlagXorCallSmcSuccessCallerWontRevertXorDecFlag3XorWarmthNew;
  private final MappedByteBuffer rlpaddrKecHiXorMmuSrcOffsetLoXorValueOrigLo;
  private final MappedByteBuffer rlpaddrKecLoXorMmuTgtOffsetLo;
  private final MappedByteBuffer rlpaddrRecipeXorStpOogxXorCreateAbortXorDecFlag4;
  private final MappedByteBuffer rlpaddrSaltHiXorMxpGasMxp;
  private final MappedByteBuffer rlpaddrSaltLoXorMxpOffset1Hi;
  private final MappedByteBuffer romlexFlagXorStpWarmthXorCreateEmptyInitCodeWillRevertXorDupFlag;
  private final MappedByteBuffer selfdestructExceptionXorStackItemPop4;
  private final MappedByteBuffer selfdestructWillRevertXorStaticx;
  private final MappedByteBuffer selfdestructWontRevertAlreadyMarkedXorStaticFlag;
  private final MappedByteBuffer selfdestructWontRevertNotYetMarkedXorStoFlag;
  private final MappedByteBuffer stackItemHeight1;
  private final MappedByteBuffer stackItemHeight2;
  private final MappedByteBuffer stackItemHeight3;
  private final MappedByteBuffer stackItemHeight4;
  private final MappedByteBuffer stackItemStamp1;
  private final MappedByteBuffer stackItemStamp2;
  private final MappedByteBuffer stackItemStamp3;
  private final MappedByteBuffer stackItemStamp4;
  private final MappedByteBuffer stackItemValueHi1;
  private final MappedByteBuffer stackItemValueHi2;
  private final MappedByteBuffer stackItemValueHi3;
  private final MappedByteBuffer stackItemValueHi4;
  private final MappedByteBuffer stackItemValueLo1;
  private final MappedByteBuffer stackItemValueLo2;
  private final MappedByteBuffer stackItemValueLo3;
  private final MappedByteBuffer stackItemValueLo4;
  private final MappedByteBuffer stoFinal;
  private final MappedByteBuffer stoFirst;
  private final MappedByteBuffer stpGasHi;
  private final MappedByteBuffer stpGasLo;
  private final MappedByteBuffer stpGasUpfrontGasCostXorGasLeftover;
  private final MappedByteBuffer stpValueHi;
  private final MappedByteBuffer stpValueLo;
  private final MappedByteBuffer subStamp;
  private final MappedByteBuffer sux;
  private final MappedByteBuffer swapFlag;
  private final MappedByteBuffer trmFlagXorCreateEmptyInitCodeWontRevertXorExtFlag;
  private final MappedByteBuffer trmRawAddressHiXorMxpOffset1Lo;
  private final MappedByteBuffer twoLineInstruction;
  private final MappedByteBuffer txExec;
  private final MappedByteBuffer txFinl;
  private final MappedByteBuffer txInit;
  private final MappedByteBuffer txSkip;
  private final MappedByteBuffer txWarm;
  private final MappedByteBuffer txnFlag;
  private final MappedByteBuffer warmthNewXorCreateFailureConditionWillRevertXorHashInfoFlag;
  private final MappedByteBuffer warmthXorCreateExceptionXorHaltFlag;
  private final MappedByteBuffer wcpFlag;

  static List<ColumnHeader> headers(int length) {
    return List.of(
        new ColumnHeader("hub.ABSOLUTE_TRANSACTION_NUMBER", 4, length),
        new ColumnHeader("hub.acc_FINAL", 1, length),
        new ColumnHeader("hub.acc_FIRST", 1, length),
        new ColumnHeader(
            "hub.ADDRESS_HI_xor_ACCOUNT_ADDRESS_HI_xor_CCRS_STAMP_xor_PRC_CALLEE_GAS_xor_STATIC_GAS_xor_ADDRESS_HI_xor_BATCH_NUM",
            8,
            length),
        new ColumnHeader(
            "hub.ADDRESS_LO_xor_ACCOUNT_ADDRESS_LO_xor_EXP_DATA_1_xor_HASH_INFO_KECCAK_HI_xor_ADDRESS_LO_xor_COINBASE_ADDRESS_LO",
            32,
            length),
        new ColumnHeader("hub.ALPHA", 1, length),
        new ColumnHeader(
            "hub.BALANCE_NEW_xor_CALLER_ADDRESS_LO_xor_EXP_DATA_3_xor_PUSH_VALUE_HI_xor_STORAGE_KEY_LO_xor_INITIAL_BALANCE",
            32,
            length),
        new ColumnHeader(
            "hub.BALANCE_xor_BYTE_CODE_ADDRESS_LO_xor_EXP_DATA_2_xor_HASH_INFO_KECCAK_LO_xor_STORAGE_KEY_HI_xor_FROM_ADDRESS_LO",
            32,
            length),
        new ColumnHeader("hub.BATCH_NUMBER", 4, length),
        new ColumnHeader("hub.CALL_DATA_OFFSET_xor_MMU_SIZE", 8, length),
        new ColumnHeader("hub.CALL_DATA_SIZE_xor_MMU_SRC_ID", 8, length),
        new ColumnHeader("hub.CALL_STACK_DEPTH", 2, length),
        new ColumnHeader("hub.CALLER_CONTEXT_NUMBER", 8, length),
        new ColumnHeader("hub.CODE_FRAGMENT_INDEX", 8, length),
        new ColumnHeader(
            "hub.CODE_FRAGMENT_INDEX_xor_ACCOUNT_DEPLOYMENT_NUMBER_xor_EXP_INST_xor_PRC_CALLER_GAS_xor_DEPLOYMENT_NUMBER_xor_CALL_DATA_SIZE",
            8,
            length),
        new ColumnHeader(
            "hub.CODE_HASH_HI_NEW_xor_EXP_DATA_5_xor_VALUE_CURR_LO_xor_VALUE", 32, length),
        new ColumnHeader(
            "hub.CODE_HASH_HI_xor_CALL_VALUE_xor_EXP_DATA_4_xor_PUSH_VALUE_LO_xor_VALUE_CURR_HI_xor_TO_ADDRESS_LO",
            32,
            length),
        new ColumnHeader("hub.CODE_HASH_LO_NEW_xor_MMU_LIMB_2_xor_VALUE_NEXT_LO", 32, length),
        new ColumnHeader("hub.CODE_HASH_LO_xor_MMU_LIMB_1_xor_VALUE_NEXT_HI", 32, length),
        new ColumnHeader(
            "hub.CODE_SIZE_NEW_xor_BYTE_CODE_CODE_FRAGMENT_INDEX_xor_MMU_EXO_SUM_xor_PRC_CDS_xor_FROM_ADDRESS_HI",
            8,
            length),
        new ColumnHeader(
            "hub.CODE_SIZE_xor_BYTE_CODE_ADDRESS_HI_xor_MMU_AUX_ID_xor_PRC_CDO_xor_DEPLOYMENT_NUMBER_INFTY_xor_COINBASE_ADDRESS_HI",
            8,
            length),
        new ColumnHeader("hub.con_AGAIN", 1, length),
        new ColumnHeader("hub.con_FIRST", 1, length),
        new ColumnHeader("hub.CONTEXT_GETS_REVERTED", 1, length),
        new ColumnHeader("hub.CONTEXT_MAY_CHANGE", 1, length),
        new ColumnHeader("hub.CONTEXT_NUMBER", 8, length),
        new ColumnHeader("hub.CONTEXT_NUMBER_NEW", 8, length),
        new ColumnHeader("hub.CONTEXT_NUMBER_xor_MMU_TGT_ID", 8, length),
        new ColumnHeader("hub.CONTEXT_REVERT_STAMP", 8, length),
        new ColumnHeader("hub.CONTEXT_SELF_REVERTS", 1, length),
        new ColumnHeader("hub.CONTEXT_WILL_REVERT", 1, length),
        new ColumnHeader("hub.COUNTER_NSR", 2, length),
        new ColumnHeader("hub.COUNTER_TLI", 1, length),
        new ColumnHeader("hub.CREATE_FAILURE_CONDITION_WONT_REVERT_xor_ICPX", 1, length),
        new ColumnHeader(
            "hub.CREATE_NONEMPTY_INIT_CODE_FAILURE_WILL_REVERT_xor_INVALID_FLAG", 1, length),
        new ColumnHeader("hub.CREATE_NONEMPTY_INIT_CODE_FAILURE_WONT_REVERT_xor_JUMPX", 1, length),
        new ColumnHeader(
            "hub.CREATE_NONEMPTY_INIT_CODE_SUCCESS_WILL_REVERT_xor_JUMP_DESTINATION_VETTING_REQUIRED",
            1,
            length),
        new ColumnHeader(
            "hub.CREATE_NONEMPTY_INIT_CODE_SUCCESS_WONT_REVERT_xor_JUMP_FLAG", 1, length),
        new ColumnHeader("hub.DELTA", 1, length),
        new ColumnHeader(
            "hub.DEPLOYMENT_NUMBER_INFTY_xor_BYTE_CODE_DEPLOYMENT_STATUS_xor_MMU_PHASE_xor_PRC_RAO_xor_TO_ADDRESS_HI",
            8,
            length),
        new ColumnHeader(
            "hub.DEPLOYMENT_NUMBER_NEW_xor_CALLER_ADDRESS_HI_xor_MMU_REF_OFFSET_xor_PRC_RETURN_GAS",
            8,
            length),
        new ColumnHeader(
            "hub.DEPLOYMENT_NUMBER_xor_BYTE_CODE_DEPLOYMENT_NUMBER_xor_MMU_INST_xor_PRC_RAC_xor_INIT_CODE_SIZE",
            8,
            length),
        new ColumnHeader(
            "hub.DEPLOYMENT_STATUS_INFTY_xor_IS_STATIC_xor_EXP_FLAG_xor_CALL_EOA_SUCCESS_CALLER_WILL_REVERT_xor_ADD_FLAG_xor_UNCONSTRAINED_FIRST_xor_IS_DEPLOYMENT",
            1,
            length),
        new ColumnHeader(
            "hub.DEPLOYMENT_STATUS_NEW_xor_UPDATE_xor_MMU_FLAG_xor_CALL_EOA_SUCCESS_CALLER_WONT_REVERT_xor_BIN_FLAG_xor_VALUE_CURR_CHANGES_xor_IS_TYPE2",
            1,
            length),
        new ColumnHeader(
            "hub.DEPLOYMENT_STATUS_xor_IS_ROOT_xor_CCSR_FLAG_xor_CALL_ABORT_xor_ACC_FLAG_xor_UNCONSTRAINED_FINAL_xor_COPY_TXCD",
            1,
            length),
        new ColumnHeader("hub.DOM_STAMP", 8, length),
        new ColumnHeader("hub.EXCEPTION_AHOY", 1, length),
        new ColumnHeader(
            "hub.EXISTS_NEW_xor_MXP_DEPLOYS_xor_CALL_PRC_FAILURE_xor_CALL_FLAG_xor_VALUE_CURR_IS_ZERO_xor_STATUS_CODE",
            1,
            length),
        new ColumnHeader(
            "hub.EXISTS_xor_MMU_SUCCESS_BIT_xor_CALL_EXCEPTION_xor_BTC_FLAG_xor_VALUE_CURR_IS_ORIG_xor_REQUIRES_EVM_EXECUTION",
            1,
            length),
        new ColumnHeader("hub.GAS_ACTUAL", 8, length),
        new ColumnHeader("hub.GAS_COST", 32, length),
        new ColumnHeader("hub.GAS_EXPECTED", 8, length),
        new ColumnHeader("hub.GAS_LIMIT", 32, length),
        new ColumnHeader("hub.GAS_NEXT", 8, length),
        new ColumnHeader("hub.GAS_PRICE", 32, length),
        new ColumnHeader(
            "hub.HAS_CODE_NEW_xor_MXP_MTNTOP_xor_CALL_PRC_SUCCESS_CALLER_WONT_REVERT_xor_COPY_FLAG_xor_VALUE_NEXT_IS_ORIG",
            1,
            length),
        new ColumnHeader(
            "hub.HAS_CODE_xor_MXP_FLAG_xor_CALL_PRC_SUCCESS_CALLER_WILL_REVERT_xor_CON_FLAG_xor_VALUE_NEXT_IS_CURR",
            1,
            length),
        new ColumnHeader("hub.HASH_INFO_STAMP", 8, length),
        new ColumnHeader("hub.HEIGHT", 2, length),
        new ColumnHeader("hub.HEIGHT_NEW", 2, length),
        new ColumnHeader("hub.HUB_STAMP", 8, length),
        new ColumnHeader("hub.HUB_STAMP_TRANSACTION_END", 8, length),
        new ColumnHeader("hub.INSTRUCTION", 32, length),
        new ColumnHeader(
            "hub.IS_PRECOMPILE_xor_MXP_MXPX_xor_CALL_SMC_FAILURE_CALLER_WILL_REVERT_xor_CREATE_FLAG_xor_VALUE_NEXT_IS_ZERO",
            1,
            length),
        new ColumnHeader("hub.LOG_INFO_STAMP", 8, length),
        new ColumnHeader(
            "hub.MARKED_FOR_SELFDESTRUCT_NEW_xor_STP_EXISTS_xor_CALL_SMC_SUCCESS_CALLER_WILL_REVERT_xor_DEC_FLAG_2_xor_WARMTH",
            1,
            length),
        new ColumnHeader(
            "hub.MARKED_FOR_SELFDESTRUCT_xor_OOB_FLAG_xor_CALL_SMC_FAILURE_CALLER_WONT_REVERT_xor_DEC_FLAG_1_xor_VALUE_ORIG_IS_ZERO",
            1,
            length),
        new ColumnHeader("hub.MMU_STAMP", 8, length),
        new ColumnHeader("hub.MXP_OFFSET_2_HI", 32, length),
        new ColumnHeader("hub.MXP_OFFSET_2_LO", 32, length),
        new ColumnHeader("hub.MXP_SIZE_1_HI", 32, length),
        new ColumnHeader("hub.MXP_SIZE_1_LO", 32, length),
        new ColumnHeader("hub.MXP_SIZE_2_HI", 32, length),
        new ColumnHeader("hub.MXP_SIZE_2_LO", 32, length),
        new ColumnHeader("hub.MXP_STAMP", 8, length),
        new ColumnHeader("hub.MXP_WORDS", 32, length),
        new ColumnHeader("hub.NB_ADDED", 1, length),
        new ColumnHeader("hub.NB_REMOVED", 1, length),
        new ColumnHeader("hub.NON_STACK_ROWS", 2, length),
        new ColumnHeader("hub.NONCE", 32, length),
        new ColumnHeader(
            "hub.NONCE_NEW_xor_STP_GAS_PAID_OUT_OF_POCKET_xor_GAS_INITIALLY_AVAILABLE", 32, length),
        new ColumnHeader("hub.NONCE_xor_STP_GAS_MXP_xor_BASEFEE", 32, length),
        new ColumnHeader("hub.OOB_DATA_1", 32, length),
        new ColumnHeader("hub.OOB_DATA_2", 32, length),
        new ColumnHeader("hub.OOB_DATA_3", 32, length),
        new ColumnHeader("hub.OOB_DATA_4", 32, length),
        new ColumnHeader("hub.OOB_DATA_5", 32, length),
        new ColumnHeader("hub.OOB_DATA_6", 32, length),
        new ColumnHeader("hub.OOB_DATA_7", 32, length),
        new ColumnHeader("hub.OOB_DATA_8", 32, length),
        new ColumnHeader("hub.PEEK_AT_ACCOUNT", 1, length),
        new ColumnHeader("hub.PEEK_AT_CONTEXT", 1, length),
        new ColumnHeader("hub.PEEK_AT_MISCELLANEOUS", 1, length),
        new ColumnHeader("hub.PEEK_AT_SCENARIO", 1, length),
        new ColumnHeader("hub.PEEK_AT_STACK", 1, length),
        new ColumnHeader("hub.PEEK_AT_STORAGE", 1, length),
        new ColumnHeader("hub.PEEK_AT_TRANSACTION", 1, length),
        new ColumnHeader("hub.PRC_BLAKE2f_xor_KEC_FLAG", 1, length),
        new ColumnHeader("hub.PRC_ECADD_xor_LOG_FLAG", 1, length),
        new ColumnHeader("hub.PRC_ECMUL_xor_LOG_INFO_FLAG", 1, length),
        new ColumnHeader("hub.PRC_ECPAIRING_xor_MACHINE_STATE_FLAG", 1, length),
        new ColumnHeader("hub.PRC_ECRECOVER_xor_MAXCSX", 1, length),
        new ColumnHeader("hub.PRC_FAILURE_KNOWN_TO_HUB_xor_MOD_FLAG", 1, length),
        new ColumnHeader("hub.PRC_FAILURE_KNOWN_TO_RAM_xor_MUL_FLAG", 1, length),
        new ColumnHeader("hub.PRC_IDENTITY_xor_MXPX", 1, length),
        new ColumnHeader("hub.PRC_MODEXP_xor_MXP_FLAG", 1, length),
        new ColumnHeader("hub.PRC_RIPEMD-160_xor_OOGX", 1, length),
        new ColumnHeader("hub.PRC_SHA2-256_xor_OPCX", 1, length),
        new ColumnHeader("hub.PRC_SUCCESS_CALLER_WILL_REVERT_xor_PUSHPOP_FLAG", 1, length),
        new ColumnHeader("hub.PRC_SUCCESS_CALLER_WONT_REVERT_xor_RDCX", 1, length),
        new ColumnHeader("hub.PRIORITY_FEE_PER_GAS", 32, length),
        new ColumnHeader("hub.PROGRAM_COUNTER", 8, length),
        new ColumnHeader("hub.PROGRAM_COUNTER_NEW", 8, length),
        new ColumnHeader("hub.REFUND_COUNTER", 8, length),
        new ColumnHeader("hub.REFUND_COUNTER_INFINITY", 32, length),
        new ColumnHeader("hub.REFUND_COUNTER_NEW", 8, length),
        new ColumnHeader("hub.REFUND_EFFECTIVE", 32, length),
        new ColumnHeader("hub.RETURN_AT_CAPACITY_xor_MXP_INST", 8, length),
        new ColumnHeader("hub.RETURN_AT_OFFSET_xor_OOB_INST", 8, length),
        new ColumnHeader("hub.RETURN_DATA_CONTEXT_NUMBER_xor_STP_GAS_STIPEND", 8, length),
        new ColumnHeader("hub.RETURN_DATA_OFFSET_xor_STP_INSTRUCTION", 8, length),
        new ColumnHeader("hub.RETURN_DATA_SIZE", 8, length),
        new ColumnHeader("hub.RETURN_EXCEPTION_xor_SHF_FLAG", 1, length),
        new ColumnHeader("hub.RETURN_FROM_DEPLOYMENT_EMPTY_CODE_WILL_REVERT_xor_SOX", 1, length),
        new ColumnHeader(
            "hub.RETURN_FROM_DEPLOYMENT_EMPTY_CODE_WONT_REVERT_xor_SSTOREX", 1, length),
        new ColumnHeader(
            "hub.RETURN_FROM_DEPLOYMENT_NONEMPTY_CODE_WILL_REVERT_xor_STACKRAM_FLAG", 1, length),
        new ColumnHeader(
            "hub.RETURN_FROM_DEPLOYMENT_NONEMPTY_CODE_WONT_REVERT_xor_STACK_ITEM_POP_1", 1, length),
        new ColumnHeader(
            "hub.RETURN_FROM_MESSAGE_CALL_WILL_TOUCH_RAM_xor_STACK_ITEM_POP_2", 1, length),
        new ColumnHeader(
            "hub.RETURN_FROM_MESSAGE_CALL_WONT_TOUCH_RAM_xor_STACK_ITEM_POP_3", 1, length),
        new ColumnHeader(
            "hub.RLPADDR_DEP_ADDR_HI_xor_CALL_DATA_CONTEXT_NUMBER_xor_MMU_REF_SIZE", 8, length),
        new ColumnHeader(
            "hub.RLPADDR_DEP_ADDR_LO_xor_MMU_SRC_OFFSET_HI_xor_VALUE_ORIG_HI", 32, length),
        new ColumnHeader(
            "hub.RLPADDR_FLAG_xor_STP_FLAG_xor_CALL_SMC_SUCCESS_CALLER_WONT_REVERT_xor_DEC_FLAG_3_xor_WARMTH_NEW",
            1,
            length),
        new ColumnHeader("hub.RLPADDR_KEC_HI_xor_MMU_SRC_OFFSET_LO_xor_VALUE_ORIG_LO", 32, length),
        new ColumnHeader("hub.RLPADDR_KEC_LO_xor_MMU_TGT_OFFSET_LO", 32, length),
        new ColumnHeader(
            "hub.RLPADDR_RECIPE_xor_STP_OOGX_xor_CREATE_ABORT_xor_DEC_FLAG_4", 1, length),
        new ColumnHeader("hub.RLPADDR_SALT_HI_xor_MXP_GAS_MXP", 32, length),
        new ColumnHeader("hub.RLPADDR_SALT_LO_xor_MXP_OFFSET_1_HI", 32, length),
        new ColumnHeader(
            "hub.ROMLEX_FLAG_xor_STP_WARMTH_xor_CREATE_EMPTY_INIT_CODE_WILL_REVERT_xor_DUP_FLAG",
            1,
            length),
        new ColumnHeader("hub.SELFDESTRUCT_EXCEPTION_xor_STACK_ITEM_POP_4", 1, length),
        new ColumnHeader("hub.SELFDESTRUCT_WILL_REVERT_xor_STATICX", 1, length),
        new ColumnHeader("hub.SELFDESTRUCT_WONT_REVERT_ALREADY_MARKED_xor_STATIC_FLAG", 1, length),
        new ColumnHeader("hub.SELFDESTRUCT_WONT_REVERT_NOT_YET_MARKED_xor_STO_FLAG", 1, length),
        new ColumnHeader("hub.STACK_ITEM_HEIGHT_1", 32, length),
        new ColumnHeader("hub.STACK_ITEM_HEIGHT_2", 32, length),
        new ColumnHeader("hub.STACK_ITEM_HEIGHT_3", 32, length),
        new ColumnHeader("hub.STACK_ITEM_HEIGHT_4", 32, length),
        new ColumnHeader("hub.STACK_ITEM_STAMP_1", 32, length),
        new ColumnHeader("hub.STACK_ITEM_STAMP_2", 32, length),
        new ColumnHeader("hub.STACK_ITEM_STAMP_3", 32, length),
        new ColumnHeader("hub.STACK_ITEM_STAMP_4", 32, length),
        new ColumnHeader("hub.STACK_ITEM_VALUE_HI_1", 32, length),
        new ColumnHeader("hub.STACK_ITEM_VALUE_HI_2", 32, length),
        new ColumnHeader("hub.STACK_ITEM_VALUE_HI_3", 32, length),
        new ColumnHeader("hub.STACK_ITEM_VALUE_HI_4", 32, length),
        new ColumnHeader("hub.STACK_ITEM_VALUE_LO_1", 32, length),
        new ColumnHeader("hub.STACK_ITEM_VALUE_LO_2", 32, length),
        new ColumnHeader("hub.STACK_ITEM_VALUE_LO_3", 32, length),
        new ColumnHeader("hub.STACK_ITEM_VALUE_LO_4", 32, length),
        new ColumnHeader("hub.sto_FINAL", 1, length),
        new ColumnHeader("hub.sto_FIRST", 1, length),
        new ColumnHeader("hub.STP_GAS_HI", 32, length),
        new ColumnHeader("hub.STP_GAS_LO", 32, length),
        new ColumnHeader("hub.STP_GAS_UPFRONT_GAS_COST_xor_GAS_LEFTOVER", 32, length),
        new ColumnHeader("hub.STP_VALUE_HI", 32, length),
        new ColumnHeader("hub.STP_VALUE_LO", 32, length),
        new ColumnHeader("hub.SUB_STAMP", 8, length),
        new ColumnHeader("hub.SUX", 1, length),
        new ColumnHeader("hub.SWAP_FLAG", 1, length),
        new ColumnHeader(
            "hub.TRM_FLAG_xor_CREATE_EMPTY_INIT_CODE_WONT_REVERT_xor_EXT_FLAG", 1, length),
        new ColumnHeader("hub.TRM_RAW_ADDRESS_HI_xor_MXP_OFFSET_1_LO", 32, length),
        new ColumnHeader("hub.TWO_LINE_INSTRUCTION", 1, length),
        new ColumnHeader("hub.TX_EXEC", 1, length),
        new ColumnHeader("hub.TX_FINL", 1, length),
        new ColumnHeader("hub.TX_INIT", 1, length),
        new ColumnHeader("hub.TX_SKIP", 1, length),
        new ColumnHeader("hub.TX_WARM", 1, length),
        new ColumnHeader("hub.TXN_FLAG", 1, length),
        new ColumnHeader(
            "hub.WARMTH_NEW_xor_CREATE_FAILURE_CONDITION_WILL_REVERT_xor_HASH_INFO_FLAG",
            1,
            length),
        new ColumnHeader("hub.WARMTH_xor_CREATE_EXCEPTION_xor_HALT_FLAG", 1, length),
        new ColumnHeader("hub.WCP_FLAG", 1, length));
  }

  public Trace(List<MappedByteBuffer> buffers) {
    this.absoluteTransactionNumber = buffers.get(0);
    this.accFinal = buffers.get(1);
    this.accFirst = buffers.get(2);
    this
            .addressHiXorAccountAddressHiXorCcrsStampXorPrcCalleeGasXorStaticGasXorAddressHiXorBatchNum =
        buffers.get(3);
    this
            .addressLoXorAccountAddressLoXorExpData1XorHashInfoKeccakHiXorAddressLoXorCoinbaseAddressLo =
        buffers.get(4);
    this.alpha = buffers.get(5);
    this.balanceNewXorCallerAddressLoXorExpData3XorPushValueHiXorStorageKeyLoXorInitialBalance =
        buffers.get(6);
    this.balanceXorByteCodeAddressLoXorExpData2XorHashInfoKeccakLoXorStorageKeyHiXorFromAddressLo =
        buffers.get(7);
    this.batchNumber = buffers.get(8);
    this.callDataOffsetXorMmuSize = buffers.get(9);
    this.callDataSizeXorMmuSrcId = buffers.get(10);
    this.callStackDepth = buffers.get(11);
    this.callerContextNumber = buffers.get(12);
    this.codeFragmentIndex = buffers.get(13);
    this
            .codeFragmentIndexXorAccountDeploymentNumberXorExpInstXorPrcCallerGasXorDeploymentNumberXorCallDataSize =
        buffers.get(14);
    this.codeHashHiNewXorExpData5XorValueCurrLoXorValue = buffers.get(15);
    this.codeHashHiXorCallValueXorExpData4XorPushValueLoXorValueCurrHiXorToAddressLo =
        buffers.get(16);
    this.codeHashLoNewXorMmuLimb2XorValueNextLo = buffers.get(17);
    this.codeHashLoXorMmuLimb1XorValueNextHi = buffers.get(18);
    this.codeSizeNewXorByteCodeCodeFragmentIndexXorMmuExoSumXorPrcCdsXorFromAddressHi =
        buffers.get(19);
    this
            .codeSizeXorByteCodeAddressHiXorMmuAuxIdXorPrcCdoXorDeploymentNumberInftyXorCoinbaseAddressHi =
        buffers.get(20);
    this.conAgain = buffers.get(21);
    this.conFirst = buffers.get(22);
    this.contextGetsReverted = buffers.get(23);
    this.contextMayChange = buffers.get(24);
    this.contextNumber = buffers.get(25);
    this.contextNumberNew = buffers.get(26);
    this.contextNumberXorMmuTgtId = buffers.get(27);
    this.contextRevertStamp = buffers.get(28);
    this.contextSelfReverts = buffers.get(29);
    this.contextWillRevert = buffers.get(30);
    this.counterNsr = buffers.get(31);
    this.counterTli = buffers.get(32);
    this.createFailureConditionWontRevertXorIcpx = buffers.get(33);
    this.createNonemptyInitCodeFailureWillRevertXorInvalidFlag = buffers.get(34);
    this.createNonemptyInitCodeFailureWontRevertXorJumpx = buffers.get(35);
    this.createNonemptyInitCodeSuccessWillRevertXorJumpDestinationVettingRequired = buffers.get(36);
    this.createNonemptyInitCodeSuccessWontRevertXorJumpFlag = buffers.get(37);
    this.delta = buffers.get(38);
    this.deploymentNumberInftyXorByteCodeDeploymentStatusXorMmuPhaseXorPrcRaoXorToAddressHi =
        buffers.get(39);
    this.deploymentNumberNewXorCallerAddressHiXorMmuRefOffsetXorPrcReturnGas = buffers.get(40);
    this.deploymentNumberXorByteCodeDeploymentNumberXorMmuInstXorPrcRacXorInitCodeSize =
        buffers.get(41);
    this
            .deploymentStatusInftyXorIsStaticXorExpFlagXorCallEoaSuccessCallerWillRevertXorAddFlagXorUnconstrainedFirstXorIsDeployment =
        buffers.get(42);
    this
            .deploymentStatusNewXorUpdateXorMmuFlagXorCallEoaSuccessCallerWontRevertXorBinFlagXorValueCurrChangesXorIsType2 =
        buffers.get(43);
    this
            .deploymentStatusXorIsRootXorCcsrFlagXorCallAbortXorAccFlagXorUnconstrainedFinalXorCopyTxcd =
        buffers.get(44);
    this.domStamp = buffers.get(45);
    this.exceptionAhoy = buffers.get(46);
    this.existsNewXorMxpDeploysXorCallPrcFailureXorCallFlagXorValueCurrIsZeroXorStatusCode =
        buffers.get(47);
    this.existsXorMmuSuccessBitXorCallExceptionXorBtcFlagXorValueCurrIsOrigXorRequiresEvmExecution =
        buffers.get(48);
    this.gasActual = buffers.get(49);
    this.gasCost = buffers.get(50);
    this.gasExpected = buffers.get(51);
    this.gasLimit = buffers.get(52);
    this.gasNext = buffers.get(53);
    this.gasPrice = buffers.get(54);
    this.hasCodeNewXorMxpMtntopXorCallPrcSuccessCallerWontRevertXorCopyFlagXorValueNextIsOrig =
        buffers.get(55);
    this.hasCodeXorMxpFlagXorCallPrcSuccessCallerWillRevertXorConFlagXorValueNextIsCurr =
        buffers.get(56);
    this.hashInfoStamp = buffers.get(57);
    this.height = buffers.get(58);
    this.heightNew = buffers.get(59);
    this.hubStamp = buffers.get(60);
    this.hubStampTransactionEnd = buffers.get(61);
    this.instruction = buffers.get(62);
    this.isPrecompileXorMxpMxpxXorCallSmcFailureCallerWillRevertXorCreateFlagXorValueNextIsZero =
        buffers.get(63);
    this.logInfoStamp = buffers.get(64);
    this.markedForSelfdestructNewXorStpExistsXorCallSmcSuccessCallerWillRevertXorDecFlag2XorWarmth =
        buffers.get(65);
    this
            .markedForSelfdestructXorOobFlagXorCallSmcFailureCallerWontRevertXorDecFlag1XorValueOrigIsZero =
        buffers.get(66);
    this.mmuStamp = buffers.get(67);
    this.mxpOffset2Hi = buffers.get(68);
    this.mxpOffset2Lo = buffers.get(69);
    this.mxpSize1Hi = buffers.get(70);
    this.mxpSize1Lo = buffers.get(71);
    this.mxpSize2Hi = buffers.get(72);
    this.mxpSize2Lo = buffers.get(73);
    this.mxpStamp = buffers.get(74);
    this.mxpWords = buffers.get(75);
    this.nbAdded = buffers.get(76);
    this.nbRemoved = buffers.get(77);
    this.nonStackRows = buffers.get(78);
    this.nonce = buffers.get(79);
    this.nonceNewXorStpGasPaidOutOfPocketXorGasInitiallyAvailable = buffers.get(80);
    this.nonceXorStpGasMxpXorBasefee = buffers.get(81);
    this.oobData1 = buffers.get(82);
    this.oobData2 = buffers.get(83);
    this.oobData3 = buffers.get(84);
    this.oobData4 = buffers.get(85);
    this.oobData5 = buffers.get(86);
    this.oobData6 = buffers.get(87);
    this.oobData7 = buffers.get(88);
    this.oobData8 = buffers.get(89);
    this.peekAtAccount = buffers.get(90);
    this.peekAtContext = buffers.get(91);
    this.peekAtMiscellaneous = buffers.get(92);
    this.peekAtScenario = buffers.get(93);
    this.peekAtStack = buffers.get(94);
    this.peekAtStorage = buffers.get(95);
    this.peekAtTransaction = buffers.get(96);
    this.prcBlake2FXorKecFlag = buffers.get(97);
    this.prcEcaddXorLogFlag = buffers.get(98);
    this.prcEcmulXorLogInfoFlag = buffers.get(99);
    this.prcEcpairingXorMachineStateFlag = buffers.get(100);
    this.prcEcrecoverXorMaxcsx = buffers.get(101);
    this.prcFailureKnownToHubXorModFlag = buffers.get(102);
    this.prcFailureKnownToRamXorMulFlag = buffers.get(103);
    this.prcIdentityXorMxpx = buffers.get(104);
    this.prcModexpXorMxpFlag = buffers.get(105);
    this.prcRipemd160XorOogx = buffers.get(106);
    this.prcSha2256XorOpcx = buffers.get(107);
    this.prcSuccessCallerWillRevertXorPushpopFlag = buffers.get(108);
    this.prcSuccessCallerWontRevertXorRdcx = buffers.get(109);
    this.priorityFeePerGas = buffers.get(110);
    this.programCounter = buffers.get(111);
    this.programCounterNew = buffers.get(112);
    this.refundCounter = buffers.get(113);
    this.refundCounterInfinity = buffers.get(114);
    this.refundCounterNew = buffers.get(115);
    this.refundEffective = buffers.get(116);
    this.returnAtCapacityXorMxpInst = buffers.get(117);
    this.returnAtOffsetXorOobInst = buffers.get(118);
    this.returnDataContextNumberXorStpGasStipend = buffers.get(119);
    this.returnDataOffsetXorStpInstruction = buffers.get(120);
    this.returnDataSize = buffers.get(121);
    this.returnExceptionXorShfFlag = buffers.get(122);
    this.returnFromDeploymentEmptyCodeWillRevertXorSox = buffers.get(123);
    this.returnFromDeploymentEmptyCodeWontRevertXorSstorex = buffers.get(124);
    this.returnFromDeploymentNonemptyCodeWillRevertXorStackramFlag = buffers.get(125);
    this.returnFromDeploymentNonemptyCodeWontRevertXorStackItemPop1 = buffers.get(126);
    this.returnFromMessageCallWillTouchRamXorStackItemPop2 = buffers.get(127);
    this.returnFromMessageCallWontTouchRamXorStackItemPop3 = buffers.get(128);
    this.rlpaddrDepAddrHiXorCallDataContextNumberXorMmuRefSize = buffers.get(129);
    this.rlpaddrDepAddrLoXorMmuSrcOffsetHiXorValueOrigHi = buffers.get(130);
    this.rlpaddrFlagXorStpFlagXorCallSmcSuccessCallerWontRevertXorDecFlag3XorWarmthNew =
        buffers.get(131);
    this.rlpaddrKecHiXorMmuSrcOffsetLoXorValueOrigLo = buffers.get(132);
    this.rlpaddrKecLoXorMmuTgtOffsetLo = buffers.get(133);
    this.rlpaddrRecipeXorStpOogxXorCreateAbortXorDecFlag4 = buffers.get(134);
    this.rlpaddrSaltHiXorMxpGasMxp = buffers.get(135);
    this.rlpaddrSaltLoXorMxpOffset1Hi = buffers.get(136);
    this.romlexFlagXorStpWarmthXorCreateEmptyInitCodeWillRevertXorDupFlag = buffers.get(137);
    this.selfdestructExceptionXorStackItemPop4 = buffers.get(138);
    this.selfdestructWillRevertXorStaticx = buffers.get(139);
    this.selfdestructWontRevertAlreadyMarkedXorStaticFlag = buffers.get(140);
    this.selfdestructWontRevertNotYetMarkedXorStoFlag = buffers.get(141);
    this.stackItemHeight1 = buffers.get(142);
    this.stackItemHeight2 = buffers.get(143);
    this.stackItemHeight3 = buffers.get(144);
    this.stackItemHeight4 = buffers.get(145);
    this.stackItemStamp1 = buffers.get(146);
    this.stackItemStamp2 = buffers.get(147);
    this.stackItemStamp3 = buffers.get(148);
    this.stackItemStamp4 = buffers.get(149);
    this.stackItemValueHi1 = buffers.get(150);
    this.stackItemValueHi2 = buffers.get(151);
    this.stackItemValueHi3 = buffers.get(152);
    this.stackItemValueHi4 = buffers.get(153);
    this.stackItemValueLo1 = buffers.get(154);
    this.stackItemValueLo2 = buffers.get(155);
    this.stackItemValueLo3 = buffers.get(156);
    this.stackItemValueLo4 = buffers.get(157);
    this.stoFinal = buffers.get(158);
    this.stoFirst = buffers.get(159);
    this.stpGasHi = buffers.get(160);
    this.stpGasLo = buffers.get(161);
    this.stpGasUpfrontGasCostXorGasLeftover = buffers.get(162);
    this.stpValueHi = buffers.get(163);
    this.stpValueLo = buffers.get(164);
    this.subStamp = buffers.get(165);
    this.sux = buffers.get(166);
    this.swapFlag = buffers.get(167);
    this.trmFlagXorCreateEmptyInitCodeWontRevertXorExtFlag = buffers.get(168);
    this.trmRawAddressHiXorMxpOffset1Lo = buffers.get(169);
    this.twoLineInstruction = buffers.get(170);
    this.txExec = buffers.get(171);
    this.txFinl = buffers.get(172);
    this.txInit = buffers.get(173);
    this.txSkip = buffers.get(174);
    this.txWarm = buffers.get(175);
    this.txnFlag = buffers.get(176);
    this.warmthNewXorCreateFailureConditionWillRevertXorHashInfoFlag = buffers.get(177);
    this.warmthXorCreateExceptionXorHaltFlag = buffers.get(178);
    this.wcpFlag = buffers.get(179);
  }

  public int size() {
    if (!filled.isEmpty()) {
      throw new RuntimeException("Cannot measure a trace with a non-validated row.");
    }

    return this.currentLine;
  }

  public Trace absoluteTransactionNumber(final int b) {
    if (filled.get(0)) {
      throw new IllegalStateException("hub.ABSOLUTE_TRANSACTION_NUMBER already set");
    } else {
      filled.set(0);
    }

    absoluteTransactionNumber.putInt(b);

    return this;
  }

  public Trace accFinal(final Boolean b) {
    if (filled.get(46)) {
      throw new IllegalStateException("hub.acc_FINAL already set");
    } else {
      filled.set(46);
    }

    accFinal.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace accFirst(final Boolean b) {
    if (filled.get(47)) {
      throw new IllegalStateException("hub.acc_FIRST already set");
    } else {
      filled.set(47);
    }

    accFirst.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace batchNumber(final int b) {
    if (filled.get(1)) {
      throw new IllegalStateException("hub.BATCH_NUMBER already set");
    } else {
      filled.set(1);
    }

    batchNumber.putInt(b);

    return this;
  }

  public Trace callerContextNumber(final long b) {
    if (filled.get(2)) {
      throw new IllegalStateException("hub.CALLER_CONTEXT_NUMBER already set");
    } else {
      filled.set(2);
    }

    callerContextNumber.putLong(b);

    return this;
  }

  public Trace codeFragmentIndex(final long b) {
    if (filled.get(3)) {
      throw new IllegalStateException("hub.CODE_FRAGMENT_INDEX already set");
    } else {
      filled.set(3);
    }

    codeFragmentIndex.putLong(b);

    return this;
  }

  public Trace conAgain(final Boolean b) {
    if (filled.get(48)) {
      throw new IllegalStateException("hub.con_AGAIN already set");
    } else {
      filled.set(48);
    }

    conAgain.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace conFirst(final Boolean b) {
    if (filled.get(49)) {
      throw new IllegalStateException("hub.con_FIRST already set");
    } else {
      filled.set(49);
    }

    conFirst.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace contextGetsReverted(final Boolean b) {
    if (filled.get(4)) {
      throw new IllegalStateException("hub.CONTEXT_GETS_REVERTED already set");
    } else {
      filled.set(4);
    }

    contextGetsReverted.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace contextMayChange(final Boolean b) {
    if (filled.get(5)) {
      throw new IllegalStateException("hub.CONTEXT_MAY_CHANGE already set");
    } else {
      filled.set(5);
    }

    contextMayChange.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace contextNumber(final long b) {
    if (filled.get(6)) {
      throw new IllegalStateException("hub.CONTEXT_NUMBER already set");
    } else {
      filled.set(6);
    }

    contextNumber.putLong(b);

    return this;
  }

  public Trace contextNumberNew(final long b) {
    if (filled.get(7)) {
      throw new IllegalStateException("hub.CONTEXT_NUMBER_NEW already set");
    } else {
      filled.set(7);
    }

    contextNumberNew.putLong(b);

    return this;
  }

  public Trace contextRevertStamp(final long b) {
    if (filled.get(8)) {
      throw new IllegalStateException("hub.CONTEXT_REVERT_STAMP already set");
    } else {
      filled.set(8);
    }

    contextRevertStamp.putLong(b);

    return this;
  }

  public Trace contextSelfReverts(final Boolean b) {
    if (filled.get(9)) {
      throw new IllegalStateException("hub.CONTEXT_SELF_REVERTS already set");
    } else {
      filled.set(9);
    }

    contextSelfReverts.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace contextWillRevert(final Boolean b) {
    if (filled.get(10)) {
      throw new IllegalStateException("hub.CONTEXT_WILL_REVERT already set");
    } else {
      filled.set(10);
    }

    contextWillRevert.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace counterNsr(final short b) {
    if (filled.get(11)) {
      throw new IllegalStateException("hub.COUNTER_NSR already set");
    } else {
      filled.set(11);
    }

    counterNsr.putShort(b);

    return this;
  }

  public Trace counterTli(final Boolean b) {
    if (filled.get(12)) {
      throw new IllegalStateException("hub.COUNTER_TLI already set");
    } else {
      filled.set(12);
    }

    counterTli.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace domStamp(final long b) {
    if (filled.get(13)) {
      throw new IllegalStateException("hub.DOM_STAMP already set");
    } else {
      filled.set(13);
    }

    domStamp.putLong(b);

    return this;
  }

  public Trace exceptionAhoy(final Boolean b) {
    if (filled.get(14)) {
      throw new IllegalStateException("hub.EXCEPTION_AHOY already set");
    } else {
      filled.set(14);
    }

    exceptionAhoy.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace gasActual(final long b) {
    if (filled.get(15)) {
      throw new IllegalStateException("hub.GAS_ACTUAL already set");
    } else {
      filled.set(15);
    }

    gasActual.putLong(b);

    return this;
  }

  public Trace gasCost(final Bytes b) {
    if (filled.get(16)) {
      throw new IllegalStateException("hub.GAS_COST already set");
    } else {
      filled.set(16);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      gasCost.put((byte) 0);
    }
    gasCost.put(b.toArrayUnsafe());

    return this;
  }

  public Trace gasExpected(final long b) {
    if (filled.get(17)) {
      throw new IllegalStateException("hub.GAS_EXPECTED already set");
    } else {
      filled.set(17);
    }

    gasExpected.putLong(b);

    return this;
  }

  public Trace gasNext(final long b) {
    if (filled.get(18)) {
      throw new IllegalStateException("hub.GAS_NEXT already set");
    } else {
      filled.set(18);
    }

    gasNext.putLong(b);

    return this;
  }

  public Trace hashInfoStamp(final long b) {
    if (filled.get(19)) {
      throw new IllegalStateException("hub.HASH_INFO_STAMP already set");
    } else {
      filled.set(19);
    }

    hashInfoStamp.putLong(b);

    return this;
  }

  public Trace height(final short b) {
    if (filled.get(20)) {
      throw new IllegalStateException("hub.HEIGHT already set");
    } else {
      filled.set(20);
    }

    height.putShort(b);

    return this;
  }

  public Trace heightNew(final short b) {
    if (filled.get(21)) {
      throw new IllegalStateException("hub.HEIGHT_NEW already set");
    } else {
      filled.set(21);
    }

    heightNew.putShort(b);

    return this;
  }

  public Trace hubStamp(final long b) {
    if (filled.get(22)) {
      throw new IllegalStateException("hub.HUB_STAMP already set");
    } else {
      filled.set(22);
    }

    hubStamp.putLong(b);

    return this;
  }

  public Trace hubStampTransactionEnd(final long b) {
    if (filled.get(23)) {
      throw new IllegalStateException("hub.HUB_STAMP_TRANSACTION_END already set");
    } else {
      filled.set(23);
    }

    hubStampTransactionEnd.putLong(b);

    return this;
  }

  public Trace logInfoStamp(final long b) {
    if (filled.get(24)) {
      throw new IllegalStateException("hub.LOG_INFO_STAMP already set");
    } else {
      filled.set(24);
    }

    logInfoStamp.putLong(b);

    return this;
  }

  public Trace mmuStamp(final long b) {
    if (filled.get(25)) {
      throw new IllegalStateException("hub.MMU_STAMP already set");
    } else {
      filled.set(25);
    }

    mmuStamp.putLong(b);

    return this;
  }

  public Trace mxpStamp(final long b) {
    if (filled.get(26)) {
      throw new IllegalStateException("hub.MXP_STAMP already set");
    } else {
      filled.set(26);
    }

    mxpStamp.putLong(b);

    return this;
  }

  public Trace nonStackRows(final short b) {
    if (filled.get(27)) {
      throw new IllegalStateException("hub.NON_STACK_ROWS already set");
    } else {
      filled.set(27);
    }

    nonStackRows.putShort(b);

    return this;
  }

  public Trace pAccountAddressHi(final long b) {
    if (filled.get(106)) {
      throw new IllegalStateException("hub.account/ADDRESS_HI already set");
    } else {
      filled.set(106);
    }

    addressHiXorAccountAddressHiXorCcrsStampXorPrcCalleeGasXorStaticGasXorAddressHiXorBatchNum
        .putLong(b);

    return this;
  }

  public Trace pAccountAddressLo(final Bytes b) {
    if (filled.get(131)) {
      throw new IllegalStateException("hub.account/ADDRESS_LO already set");
    } else {
      filled.set(131);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      addressLoXorAccountAddressLoXorExpData1XorHashInfoKeccakHiXorAddressLoXorCoinbaseAddressLo
          .put((byte) 0);
    }
    addressLoXorAccountAddressLoXorExpData1XorHashInfoKeccakHiXorAddressLoXorCoinbaseAddressLo.put(
        b.toArrayUnsafe());

    return this;
  }

  public Trace pAccountBalance(final Bytes b) {
    if (filled.get(132)) {
      throw new IllegalStateException("hub.account/BALANCE already set");
    } else {
      filled.set(132);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      balanceXorByteCodeAddressLoXorExpData2XorHashInfoKeccakLoXorStorageKeyHiXorFromAddressLo.put(
          (byte) 0);
    }
    balanceXorByteCodeAddressLoXorExpData2XorHashInfoKeccakLoXorStorageKeyHiXorFromAddressLo.put(
        b.toArrayUnsafe());

    return this;
  }

  public Trace pAccountBalanceNew(final Bytes b) {
    if (filled.get(133)) {
      throw new IllegalStateException("hub.account/BALANCE_NEW already set");
    } else {
      filled.set(133);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      balanceNewXorCallerAddressLoXorExpData3XorPushValueHiXorStorageKeyLoXorInitialBalance.put(
          (byte) 0);
    }
    balanceNewXorCallerAddressLoXorExpData3XorPushValueHiXorStorageKeyLoXorInitialBalance.put(
        b.toArrayUnsafe());

    return this;
  }

  public Trace pAccountCodeFragmentIndex(final long b) {
    if (filled.get(107)) {
      throw new IllegalStateException("hub.account/CODE_FRAGMENT_INDEX already set");
    } else {
      filled.set(107);
    }

    codeFragmentIndexXorAccountDeploymentNumberXorExpInstXorPrcCallerGasXorDeploymentNumberXorCallDataSize
        .putLong(b);

    return this;
  }

  public Trace pAccountCodeHashHi(final Bytes b) {
    if (filled.get(134)) {
      throw new IllegalStateException("hub.account/CODE_HASH_HI already set");
    } else {
      filled.set(134);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      codeHashHiXorCallValueXorExpData4XorPushValueLoXorValueCurrHiXorToAddressLo.put((byte) 0);
    }
    codeHashHiXorCallValueXorExpData4XorPushValueLoXorValueCurrHiXorToAddressLo.put(
        b.toArrayUnsafe());

    return this;
  }

  public Trace pAccountCodeHashHiNew(final Bytes b) {
    if (filled.get(135)) {
      throw new IllegalStateException("hub.account/CODE_HASH_HI_NEW already set");
    } else {
      filled.set(135);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      codeHashHiNewXorExpData5XorValueCurrLoXorValue.put((byte) 0);
    }
    codeHashHiNewXorExpData5XorValueCurrLoXorValue.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pAccountCodeHashLo(final Bytes b) {
    if (filled.get(136)) {
      throw new IllegalStateException("hub.account/CODE_HASH_LO already set");
    } else {
      filled.set(136);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      codeHashLoXorMmuLimb1XorValueNextHi.put((byte) 0);
    }
    codeHashLoXorMmuLimb1XorValueNextHi.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pAccountCodeHashLoNew(final Bytes b) {
    if (filled.get(137)) {
      throw new IllegalStateException("hub.account/CODE_HASH_LO_NEW already set");
    } else {
      filled.set(137);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      codeHashLoNewXorMmuLimb2XorValueNextLo.put((byte) 0);
    }
    codeHashLoNewXorMmuLimb2XorValueNextLo.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pAccountCodeSize(final long b) {
    if (filled.get(108)) {
      throw new IllegalStateException("hub.account/CODE_SIZE already set");
    } else {
      filled.set(108);
    }

    codeSizeXorByteCodeAddressHiXorMmuAuxIdXorPrcCdoXorDeploymentNumberInftyXorCoinbaseAddressHi
        .putLong(b);

    return this;
  }

  public Trace pAccountCodeSizeNew(final long b) {
    if (filled.get(109)) {
      throw new IllegalStateException("hub.account/CODE_SIZE_NEW already set");
    } else {
      filled.set(109);
    }

    codeSizeNewXorByteCodeCodeFragmentIndexXorMmuExoSumXorPrcCdsXorFromAddressHi.putLong(b);

    return this;
  }

  public Trace pAccountDeploymentNumber(final long b) {
    if (filled.get(110)) {
      throw new IllegalStateException("hub.account/DEPLOYMENT_NUMBER already set");
    } else {
      filled.set(110);
    }

    deploymentNumberXorByteCodeDeploymentNumberXorMmuInstXorPrcRacXorInitCodeSize.putLong(b);

    return this;
  }

  public Trace pAccountDeploymentNumberInfty(final long b) {
    if (filled.get(111)) {
      throw new IllegalStateException("hub.account/DEPLOYMENT_NUMBER_INFTY already set");
    } else {
      filled.set(111);
    }

    deploymentNumberInftyXorByteCodeDeploymentStatusXorMmuPhaseXorPrcRaoXorToAddressHi.putLong(b);

    return this;
  }

  public Trace pAccountDeploymentNumberNew(final long b) {
    if (filled.get(112)) {
      throw new IllegalStateException("hub.account/DEPLOYMENT_NUMBER_NEW already set");
    } else {
      filled.set(112);
    }

    deploymentNumberNewXorCallerAddressHiXorMmuRefOffsetXorPrcReturnGas.putLong(b);

    return this;
  }

  public Trace pAccountDeploymentStatus(final Boolean b) {
    if (filled.get(52)) {
      throw new IllegalStateException("hub.account/DEPLOYMENT_STATUS already set");
    } else {
      filled.set(52);
    }

    deploymentStatusXorIsRootXorCcsrFlagXorCallAbortXorAccFlagXorUnconstrainedFinalXorCopyTxcd.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pAccountDeploymentStatusInfty(final Boolean b) {
    if (filled.get(53)) {
      throw new IllegalStateException("hub.account/DEPLOYMENT_STATUS_INFTY already set");
    } else {
      filled.set(53);
    }

    deploymentStatusInftyXorIsStaticXorExpFlagXorCallEoaSuccessCallerWillRevertXorAddFlagXorUnconstrainedFirstXorIsDeployment
        .put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pAccountDeploymentStatusNew(final Boolean b) {
    if (filled.get(54)) {
      throw new IllegalStateException("hub.account/DEPLOYMENT_STATUS_NEW already set");
    } else {
      filled.set(54);
    }

    deploymentStatusNewXorUpdateXorMmuFlagXorCallEoaSuccessCallerWontRevertXorBinFlagXorValueCurrChangesXorIsType2
        .put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pAccountExists(final Boolean b) {
    if (filled.get(55)) {
      throw new IllegalStateException("hub.account/EXISTS already set");
    } else {
      filled.set(55);
    }

    existsXorMmuSuccessBitXorCallExceptionXorBtcFlagXorValueCurrIsOrigXorRequiresEvmExecution.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pAccountExistsNew(final Boolean b) {
    if (filled.get(56)) {
      throw new IllegalStateException("hub.account/EXISTS_NEW already set");
    } else {
      filled.set(56);
    }

    existsNewXorMxpDeploysXorCallPrcFailureXorCallFlagXorValueCurrIsZeroXorStatusCode.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pAccountHasCode(final Boolean b) {
    if (filled.get(57)) {
      throw new IllegalStateException("hub.account/HAS_CODE already set");
    } else {
      filled.set(57);
    }

    hasCodeXorMxpFlagXorCallPrcSuccessCallerWillRevertXorConFlagXorValueNextIsCurr.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pAccountHasCodeNew(final Boolean b) {
    if (filled.get(58)) {
      throw new IllegalStateException("hub.account/HAS_CODE_NEW already set");
    } else {
      filled.set(58);
    }

    hasCodeNewXorMxpMtntopXorCallPrcSuccessCallerWontRevertXorCopyFlagXorValueNextIsOrig.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pAccountIsPrecompile(final Boolean b) {
    if (filled.get(59)) {
      throw new IllegalStateException("hub.account/IS_PRECOMPILE already set");
    } else {
      filled.set(59);
    }

    isPrecompileXorMxpMxpxXorCallSmcFailureCallerWillRevertXorCreateFlagXorValueNextIsZero.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pAccountMarkedForSelfdestruct(final Boolean b) {
    if (filled.get(60)) {
      throw new IllegalStateException("hub.account/MARKED_FOR_SELFDESTRUCT already set");
    } else {
      filled.set(60);
    }

    markedForSelfdestructXorOobFlagXorCallSmcFailureCallerWontRevertXorDecFlag1XorValueOrigIsZero
        .put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pAccountMarkedForSelfdestructNew(final Boolean b) {
    if (filled.get(61)) {
      throw new IllegalStateException("hub.account/MARKED_FOR_SELFDESTRUCT_NEW already set");
    } else {
      filled.set(61);
    }

    markedForSelfdestructNewXorStpExistsXorCallSmcSuccessCallerWillRevertXorDecFlag2XorWarmth.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pAccountNonce(final Bytes b) {
    if (filled.get(122)) {
      throw new IllegalStateException("hub.account/NONCE already set");
    } else {
      filled.set(122);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      nonceXorStpGasMxpXorBasefee.put((byte) 0);
    }
    nonceXorStpGasMxpXorBasefee.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pAccountNonceNew(final Bytes b) {
    if (filled.get(123)) {
      throw new IllegalStateException("hub.account/NONCE_NEW already set");
    } else {
      filled.set(123);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      nonceNewXorStpGasPaidOutOfPocketXorGasInitiallyAvailable.put((byte) 0);
    }
    nonceNewXorStpGasPaidOutOfPocketXorGasInitiallyAvailable.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pAccountRlpaddrDepAddrHi(final long b) {
    if (filled.get(113)) {
      throw new IllegalStateException("hub.account/RLPADDR_DEP_ADDR_HI already set");
    } else {
      filled.set(113);
    }

    rlpaddrDepAddrHiXorCallDataContextNumberXorMmuRefSize.putLong(b);

    return this;
  }

  public Trace pAccountRlpaddrDepAddrLo(final Bytes b) {
    if (filled.get(138)) {
      throw new IllegalStateException("hub.account/RLPADDR_DEP_ADDR_LO already set");
    } else {
      filled.set(138);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      rlpaddrDepAddrLoXorMmuSrcOffsetHiXorValueOrigHi.put((byte) 0);
    }
    rlpaddrDepAddrLoXorMmuSrcOffsetHiXorValueOrigHi.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pAccountRlpaddrFlag(final Boolean b) {
    if (filled.get(62)) {
      throw new IllegalStateException("hub.account/RLPADDR_FLAG already set");
    } else {
      filled.set(62);
    }

    rlpaddrFlagXorStpFlagXorCallSmcSuccessCallerWontRevertXorDecFlag3XorWarmthNew.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pAccountRlpaddrKecHi(final Bytes b) {
    if (filled.get(139)) {
      throw new IllegalStateException("hub.account/RLPADDR_KEC_HI already set");
    } else {
      filled.set(139);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      rlpaddrKecHiXorMmuSrcOffsetLoXorValueOrigLo.put((byte) 0);
    }
    rlpaddrKecHiXorMmuSrcOffsetLoXorValueOrigLo.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pAccountRlpaddrKecLo(final Bytes b) {
    if (filled.get(140)) {
      throw new IllegalStateException("hub.account/RLPADDR_KEC_LO already set");
    } else {
      filled.set(140);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      rlpaddrKecLoXorMmuTgtOffsetLo.put((byte) 0);
    }
    rlpaddrKecLoXorMmuTgtOffsetLo.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pAccountRlpaddrRecipe(final Boolean b) {
    if (filled.get(63)) {
      throw new IllegalStateException("hub.account/RLPADDR_RECIPE already set");
    } else {
      filled.set(63);
    }

    rlpaddrRecipeXorStpOogxXorCreateAbortXorDecFlag4.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pAccountRlpaddrSaltHi(final Bytes b) {
    if (filled.get(141)) {
      throw new IllegalStateException("hub.account/RLPADDR_SALT_HI already set");
    } else {
      filled.set(141);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      rlpaddrSaltHiXorMxpGasMxp.put((byte) 0);
    }
    rlpaddrSaltHiXorMxpGasMxp.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pAccountRlpaddrSaltLo(final Bytes b) {
    if (filled.get(142)) {
      throw new IllegalStateException("hub.account/RLPADDR_SALT_LO already set");
    } else {
      filled.set(142);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      rlpaddrSaltLoXorMxpOffset1Hi.put((byte) 0);
    }
    rlpaddrSaltLoXorMxpOffset1Hi.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pAccountRomlexFlag(final Boolean b) {
    if (filled.get(64)) {
      throw new IllegalStateException("hub.account/ROMLEX_FLAG already set");
    } else {
      filled.set(64);
    }

    romlexFlagXorStpWarmthXorCreateEmptyInitCodeWillRevertXorDupFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pAccountTrmFlag(final Boolean b) {
    if (filled.get(65)) {
      throw new IllegalStateException("hub.account/TRM_FLAG already set");
    } else {
      filled.set(65);
    }

    trmFlagXorCreateEmptyInitCodeWontRevertXorExtFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pAccountTrmRawAddressHi(final Bytes b) {
    if (filled.get(143)) {
      throw new IllegalStateException("hub.account/TRM_RAW_ADDRESS_HI already set");
    } else {
      filled.set(143);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      trmRawAddressHiXorMxpOffset1Lo.put((byte) 0);
    }
    trmRawAddressHiXorMxpOffset1Lo.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pAccountWarmth(final Boolean b) {
    if (filled.get(66)) {
      throw new IllegalStateException("hub.account/WARMTH already set");
    } else {
      filled.set(66);
    }

    warmthXorCreateExceptionXorHaltFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pAccountWarmthNew(final Boolean b) {
    if (filled.get(67)) {
      throw new IllegalStateException("hub.account/WARMTH_NEW already set");
    } else {
      filled.set(67);
    }

    warmthNewXorCreateFailureConditionWillRevertXorHashInfoFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pContextAccountAddressHi(final long b) {
    if (filled.get(106)) {
      throw new IllegalStateException("hub.context/ACCOUNT_ADDRESS_HI already set");
    } else {
      filled.set(106);
    }

    addressHiXorAccountAddressHiXorCcrsStampXorPrcCalleeGasXorStaticGasXorAddressHiXorBatchNum
        .putLong(b);

    return this;
  }

  public Trace pContextAccountAddressLo(final Bytes b) {
    if (filled.get(131)) {
      throw new IllegalStateException("hub.context/ACCOUNT_ADDRESS_LO already set");
    } else {
      filled.set(131);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      addressLoXorAccountAddressLoXorExpData1XorHashInfoKeccakHiXorAddressLoXorCoinbaseAddressLo
          .put((byte) 0);
    }
    addressLoXorAccountAddressLoXorExpData1XorHashInfoKeccakHiXorAddressLoXorCoinbaseAddressLo.put(
        b.toArrayUnsafe());

    return this;
  }

  public Trace pContextAccountDeploymentNumber(final long b) {
    if (filled.get(107)) {
      throw new IllegalStateException("hub.context/ACCOUNT_DEPLOYMENT_NUMBER already set");
    } else {
      filled.set(107);
    }

    codeFragmentIndexXorAccountDeploymentNumberXorExpInstXorPrcCallerGasXorDeploymentNumberXorCallDataSize
        .putLong(b);

    return this;
  }

  public Trace pContextByteCodeAddressHi(final long b) {
    if (filled.get(108)) {
      throw new IllegalStateException("hub.context/BYTE_CODE_ADDRESS_HI already set");
    } else {
      filled.set(108);
    }

    codeSizeXorByteCodeAddressHiXorMmuAuxIdXorPrcCdoXorDeploymentNumberInftyXorCoinbaseAddressHi
        .putLong(b);

    return this;
  }

  public Trace pContextByteCodeAddressLo(final Bytes b) {
    if (filled.get(132)) {
      throw new IllegalStateException("hub.context/BYTE_CODE_ADDRESS_LO already set");
    } else {
      filled.set(132);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      balanceXorByteCodeAddressLoXorExpData2XorHashInfoKeccakLoXorStorageKeyHiXorFromAddressLo.put(
          (byte) 0);
    }
    balanceXorByteCodeAddressLoXorExpData2XorHashInfoKeccakLoXorStorageKeyHiXorFromAddressLo.put(
        b.toArrayUnsafe());

    return this;
  }

  public Trace pContextByteCodeCodeFragmentIndex(final long b) {
    if (filled.get(109)) {
      throw new IllegalStateException("hub.context/BYTE_CODE_CODE_FRAGMENT_INDEX already set");
    } else {
      filled.set(109);
    }

    codeSizeNewXorByteCodeCodeFragmentIndexXorMmuExoSumXorPrcCdsXorFromAddressHi.putLong(b);

    return this;
  }

  public Trace pContextByteCodeDeploymentNumber(final long b) {
    if (filled.get(110)) {
      throw new IllegalStateException("hub.context/BYTE_CODE_DEPLOYMENT_NUMBER already set");
    } else {
      filled.set(110);
    }

    deploymentNumberXorByteCodeDeploymentNumberXorMmuInstXorPrcRacXorInitCodeSize.putLong(b);

    return this;
  }

  public Trace pContextByteCodeDeploymentStatus(final long b) {
    if (filled.get(111)) {
      throw new IllegalStateException("hub.context/BYTE_CODE_DEPLOYMENT_STATUS already set");
    } else {
      filled.set(111);
    }

    deploymentNumberInftyXorByteCodeDeploymentStatusXorMmuPhaseXorPrcRaoXorToAddressHi.putLong(b);

    return this;
  }

  public Trace pContextCallDataContextNumber(final long b) {
    if (filled.get(113)) {
      throw new IllegalStateException("hub.context/CALL_DATA_CONTEXT_NUMBER already set");
    } else {
      filled.set(113);
    }

    rlpaddrDepAddrHiXorCallDataContextNumberXorMmuRefSize.putLong(b);

    return this;
  }

  public Trace pContextCallDataOffset(final long b) {
    if (filled.get(114)) {
      throw new IllegalStateException("hub.context/CALL_DATA_OFFSET already set");
    } else {
      filled.set(114);
    }

    callDataOffsetXorMmuSize.putLong(b);

    return this;
  }

  public Trace pContextCallDataSize(final long b) {
    if (filled.get(115)) {
      throw new IllegalStateException("hub.context/CALL_DATA_SIZE already set");
    } else {
      filled.set(115);
    }

    callDataSizeXorMmuSrcId.putLong(b);

    return this;
  }

  public Trace pContextCallStackDepth(final short b) {
    if (filled.get(105)) {
      throw new IllegalStateException("hub.context/CALL_STACK_DEPTH already set");
    } else {
      filled.set(105);
    }

    callStackDepth.putShort(b);

    return this;
  }

  public Trace pContextCallValue(final Bytes b) {
    if (filled.get(134)) {
      throw new IllegalStateException("hub.context/CALL_VALUE already set");
    } else {
      filled.set(134);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      codeHashHiXorCallValueXorExpData4XorPushValueLoXorValueCurrHiXorToAddressLo.put((byte) 0);
    }
    codeHashHiXorCallValueXorExpData4XorPushValueLoXorValueCurrHiXorToAddressLo.put(
        b.toArrayUnsafe());

    return this;
  }

  public Trace pContextCallerAddressHi(final long b) {
    if (filled.get(112)) {
      throw new IllegalStateException("hub.context/CALLER_ADDRESS_HI already set");
    } else {
      filled.set(112);
    }

    deploymentNumberNewXorCallerAddressHiXorMmuRefOffsetXorPrcReturnGas.putLong(b);

    return this;
  }

  public Trace pContextCallerAddressLo(final Bytes b) {
    if (filled.get(133)) {
      throw new IllegalStateException("hub.context/CALLER_ADDRESS_LO already set");
    } else {
      filled.set(133);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      balanceNewXorCallerAddressLoXorExpData3XorPushValueHiXorStorageKeyLoXorInitialBalance.put(
          (byte) 0);
    }
    balanceNewXorCallerAddressLoXorExpData3XorPushValueHiXorStorageKeyLoXorInitialBalance.put(
        b.toArrayUnsafe());

    return this;
  }

  public Trace pContextContextNumber(final long b) {
    if (filled.get(116)) {
      throw new IllegalStateException("hub.context/CONTEXT_NUMBER already set");
    } else {
      filled.set(116);
    }

    contextNumberXorMmuTgtId.putLong(b);

    return this;
  }

  public Trace pContextIsRoot(final Boolean b) {
    if (filled.get(52)) {
      throw new IllegalStateException("hub.context/IS_ROOT already set");
    } else {
      filled.set(52);
    }

    deploymentStatusXorIsRootXorCcsrFlagXorCallAbortXorAccFlagXorUnconstrainedFinalXorCopyTxcd.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pContextIsStatic(final Boolean b) {
    if (filled.get(53)) {
      throw new IllegalStateException("hub.context/IS_STATIC already set");
    } else {
      filled.set(53);
    }

    deploymentStatusInftyXorIsStaticXorExpFlagXorCallEoaSuccessCallerWillRevertXorAddFlagXorUnconstrainedFirstXorIsDeployment
        .put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pContextReturnAtCapacity(final long b) {
    if (filled.get(117)) {
      throw new IllegalStateException("hub.context/RETURN_AT_CAPACITY already set");
    } else {
      filled.set(117);
    }

    returnAtCapacityXorMxpInst.putLong(b);

    return this;
  }

  public Trace pContextReturnAtOffset(final long b) {
    if (filled.get(118)) {
      throw new IllegalStateException("hub.context/RETURN_AT_OFFSET already set");
    } else {
      filled.set(118);
    }

    returnAtOffsetXorOobInst.putLong(b);

    return this;
  }

  public Trace pContextReturnDataContextNumber(final long b) {
    if (filled.get(119)) {
      throw new IllegalStateException("hub.context/RETURN_DATA_CONTEXT_NUMBER already set");
    } else {
      filled.set(119);
    }

    returnDataContextNumberXorStpGasStipend.putLong(b);

    return this;
  }

  public Trace pContextReturnDataOffset(final long b) {
    if (filled.get(120)) {
      throw new IllegalStateException("hub.context/RETURN_DATA_OFFSET already set");
    } else {
      filled.set(120);
    }

    returnDataOffsetXorStpInstruction.putLong(b);

    return this;
  }

  public Trace pContextReturnDataSize(final long b) {
    if (filled.get(121)) {
      throw new IllegalStateException("hub.context/RETURN_DATA_SIZE already set");
    } else {
      filled.set(121);
    }

    returnDataSize.putLong(b);

    return this;
  }

  public Trace pContextUpdate(final Boolean b) {
    if (filled.get(54)) {
      throw new IllegalStateException("hub.context/UPDATE already set");
    } else {
      filled.set(54);
    }

    deploymentStatusNewXorUpdateXorMmuFlagXorCallEoaSuccessCallerWontRevertXorBinFlagXorValueCurrChangesXorIsType2
        .put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pMiscCcrsStamp(final long b) {
    if (filled.get(106)) {
      throw new IllegalStateException("hub.misc/CCRS_STAMP already set");
    } else {
      filled.set(106);
    }

    addressHiXorAccountAddressHiXorCcrsStampXorPrcCalleeGasXorStaticGasXorAddressHiXorBatchNum
        .putLong(b);

    return this;
  }

  public Trace pMiscCcsrFlag(final Boolean b) {
    if (filled.get(52)) {
      throw new IllegalStateException("hub.misc/CCSR_FLAG already set");
    } else {
      filled.set(52);
    }

    deploymentStatusXorIsRootXorCcsrFlagXorCallAbortXorAccFlagXorUnconstrainedFinalXorCopyTxcd.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pMiscExpData1(final Bytes b) {
    if (filled.get(131)) {
      throw new IllegalStateException("hub.misc/EXP_DATA_1 already set");
    } else {
      filled.set(131);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      addressLoXorAccountAddressLoXorExpData1XorHashInfoKeccakHiXorAddressLoXorCoinbaseAddressLo
          .put((byte) 0);
    }
    addressLoXorAccountAddressLoXorExpData1XorHashInfoKeccakHiXorAddressLoXorCoinbaseAddressLo.put(
        b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscExpData2(final Bytes b) {
    if (filled.get(132)) {
      throw new IllegalStateException("hub.misc/EXP_DATA_2 already set");
    } else {
      filled.set(132);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      balanceXorByteCodeAddressLoXorExpData2XorHashInfoKeccakLoXorStorageKeyHiXorFromAddressLo.put(
          (byte) 0);
    }
    balanceXorByteCodeAddressLoXorExpData2XorHashInfoKeccakLoXorStorageKeyHiXorFromAddressLo.put(
        b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscExpData3(final Bytes b) {
    if (filled.get(133)) {
      throw new IllegalStateException("hub.misc/EXP_DATA_3 already set");
    } else {
      filled.set(133);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      balanceNewXorCallerAddressLoXorExpData3XorPushValueHiXorStorageKeyLoXorInitialBalance.put(
          (byte) 0);
    }
    balanceNewXorCallerAddressLoXorExpData3XorPushValueHiXorStorageKeyLoXorInitialBalance.put(
        b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscExpData4(final Bytes b) {
    if (filled.get(134)) {
      throw new IllegalStateException("hub.misc/EXP_DATA_4 already set");
    } else {
      filled.set(134);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      codeHashHiXorCallValueXorExpData4XorPushValueLoXorValueCurrHiXorToAddressLo.put((byte) 0);
    }
    codeHashHiXorCallValueXorExpData4XorPushValueLoXorValueCurrHiXorToAddressLo.put(
        b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscExpData5(final Bytes b) {
    if (filled.get(135)) {
      throw new IllegalStateException("hub.misc/EXP_DATA_5 already set");
    } else {
      filled.set(135);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      codeHashHiNewXorExpData5XorValueCurrLoXorValue.put((byte) 0);
    }
    codeHashHiNewXorExpData5XorValueCurrLoXorValue.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscExpFlag(final Boolean b) {
    if (filled.get(53)) {
      throw new IllegalStateException("hub.misc/EXP_FLAG already set");
    } else {
      filled.set(53);
    }

    deploymentStatusInftyXorIsStaticXorExpFlagXorCallEoaSuccessCallerWillRevertXorAddFlagXorUnconstrainedFirstXorIsDeployment
        .put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pMiscExpInst(final long b) {
    if (filled.get(107)) {
      throw new IllegalStateException("hub.misc/EXP_INST already set");
    } else {
      filled.set(107);
    }

    codeFragmentIndexXorAccountDeploymentNumberXorExpInstXorPrcCallerGasXorDeploymentNumberXorCallDataSize
        .putLong(b);

    return this;
  }

  public Trace pMiscMmuAuxId(final long b) {
    if (filled.get(108)) {
      throw new IllegalStateException("hub.misc/MMU_AUX_ID already set");
    } else {
      filled.set(108);
    }

    codeSizeXorByteCodeAddressHiXorMmuAuxIdXorPrcCdoXorDeploymentNumberInftyXorCoinbaseAddressHi
        .putLong(b);

    return this;
  }

  public Trace pMiscMmuExoSum(final long b) {
    if (filled.get(109)) {
      throw new IllegalStateException("hub.misc/MMU_EXO_SUM already set");
    } else {
      filled.set(109);
    }

    codeSizeNewXorByteCodeCodeFragmentIndexXorMmuExoSumXorPrcCdsXorFromAddressHi.putLong(b);

    return this;
  }

  public Trace pMiscMmuFlag(final Boolean b) {
    if (filled.get(54)) {
      throw new IllegalStateException("hub.misc/MMU_FLAG already set");
    } else {
      filled.set(54);
    }

    deploymentStatusNewXorUpdateXorMmuFlagXorCallEoaSuccessCallerWontRevertXorBinFlagXorValueCurrChangesXorIsType2
        .put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pMiscMmuInst(final long b) {
    if (filled.get(110)) {
      throw new IllegalStateException("hub.misc/MMU_INST already set");
    } else {
      filled.set(110);
    }

    deploymentNumberXorByteCodeDeploymentNumberXorMmuInstXorPrcRacXorInitCodeSize.putLong(b);

    return this;
  }

  public Trace pMiscMmuLimb1(final Bytes b) {
    if (filled.get(136)) {
      throw new IllegalStateException("hub.misc/MMU_LIMB_1 already set");
    } else {
      filled.set(136);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      codeHashLoXorMmuLimb1XorValueNextHi.put((byte) 0);
    }
    codeHashLoXorMmuLimb1XorValueNextHi.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscMmuLimb2(final Bytes b) {
    if (filled.get(137)) {
      throw new IllegalStateException("hub.misc/MMU_LIMB_2 already set");
    } else {
      filled.set(137);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      codeHashLoNewXorMmuLimb2XorValueNextLo.put((byte) 0);
    }
    codeHashLoNewXorMmuLimb2XorValueNextLo.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscMmuPhase(final long b) {
    if (filled.get(111)) {
      throw new IllegalStateException("hub.misc/MMU_PHASE already set");
    } else {
      filled.set(111);
    }

    deploymentNumberInftyXorByteCodeDeploymentStatusXorMmuPhaseXorPrcRaoXorToAddressHi.putLong(b);

    return this;
  }

  public Trace pMiscMmuRefOffset(final long b) {
    if (filled.get(112)) {
      throw new IllegalStateException("hub.misc/MMU_REF_OFFSET already set");
    } else {
      filled.set(112);
    }

    deploymentNumberNewXorCallerAddressHiXorMmuRefOffsetXorPrcReturnGas.putLong(b);

    return this;
  }

  public Trace pMiscMmuRefSize(final long b) {
    if (filled.get(113)) {
      throw new IllegalStateException("hub.misc/MMU_REF_SIZE already set");
    } else {
      filled.set(113);
    }

    rlpaddrDepAddrHiXorCallDataContextNumberXorMmuRefSize.putLong(b);

    return this;
  }

  public Trace pMiscMmuSize(final long b) {
    if (filled.get(114)) {
      throw new IllegalStateException("hub.misc/MMU_SIZE already set");
    } else {
      filled.set(114);
    }

    callDataOffsetXorMmuSize.putLong(b);

    return this;
  }

  public Trace pMiscMmuSrcId(final long b) {
    if (filled.get(115)) {
      throw new IllegalStateException("hub.misc/MMU_SRC_ID already set");
    } else {
      filled.set(115);
    }

    callDataSizeXorMmuSrcId.putLong(b);

    return this;
  }

  public Trace pMiscMmuSrcOffsetHi(final Bytes b) {
    if (filled.get(138)) {
      throw new IllegalStateException("hub.misc/MMU_SRC_OFFSET_HI already set");
    } else {
      filled.set(138);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      rlpaddrDepAddrLoXorMmuSrcOffsetHiXorValueOrigHi.put((byte) 0);
    }
    rlpaddrDepAddrLoXorMmuSrcOffsetHiXorValueOrigHi.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscMmuSrcOffsetLo(final Bytes b) {
    if (filled.get(139)) {
      throw new IllegalStateException("hub.misc/MMU_SRC_OFFSET_LO already set");
    } else {
      filled.set(139);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      rlpaddrKecHiXorMmuSrcOffsetLoXorValueOrigLo.put((byte) 0);
    }
    rlpaddrKecHiXorMmuSrcOffsetLoXorValueOrigLo.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscMmuSuccessBit(final Boolean b) {
    if (filled.get(55)) {
      throw new IllegalStateException("hub.misc/MMU_SUCCESS_BIT already set");
    } else {
      filled.set(55);
    }

    existsXorMmuSuccessBitXorCallExceptionXorBtcFlagXorValueCurrIsOrigXorRequiresEvmExecution.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pMiscMmuTgtId(final long b) {
    if (filled.get(116)) {
      throw new IllegalStateException("hub.misc/MMU_TGT_ID already set");
    } else {
      filled.set(116);
    }

    contextNumberXorMmuTgtId.putLong(b);

    return this;
  }

  public Trace pMiscMmuTgtOffsetLo(final Bytes b) {
    if (filled.get(140)) {
      throw new IllegalStateException("hub.misc/MMU_TGT_OFFSET_LO already set");
    } else {
      filled.set(140);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      rlpaddrKecLoXorMmuTgtOffsetLo.put((byte) 0);
    }
    rlpaddrKecLoXorMmuTgtOffsetLo.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscMxpDeploys(final Boolean b) {
    if (filled.get(56)) {
      throw new IllegalStateException("hub.misc/MXP_DEPLOYS already set");
    } else {
      filled.set(56);
    }

    existsNewXorMxpDeploysXorCallPrcFailureXorCallFlagXorValueCurrIsZeroXorStatusCode.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pMiscMxpFlag(final Boolean b) {
    if (filled.get(57)) {
      throw new IllegalStateException("hub.misc/MXP_FLAG already set");
    } else {
      filled.set(57);
    }

    hasCodeXorMxpFlagXorCallPrcSuccessCallerWillRevertXorConFlagXorValueNextIsCurr.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pMiscMxpGasMxp(final Bytes b) {
    if (filled.get(141)) {
      throw new IllegalStateException("hub.misc/MXP_GAS_MXP already set");
    } else {
      filled.set(141);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      rlpaddrSaltHiXorMxpGasMxp.put((byte) 0);
    }
    rlpaddrSaltHiXorMxpGasMxp.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscMxpInst(final long b) {
    if (filled.get(117)) {
      throw new IllegalStateException("hub.misc/MXP_INST already set");
    } else {
      filled.set(117);
    }

    returnAtCapacityXorMxpInst.putLong(b);

    return this;
  }

  public Trace pMiscMxpMtntop(final Boolean b) {
    if (filled.get(58)) {
      throw new IllegalStateException("hub.misc/MXP_MTNTOP already set");
    } else {
      filled.set(58);
    }

    hasCodeNewXorMxpMtntopXorCallPrcSuccessCallerWontRevertXorCopyFlagXorValueNextIsOrig.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pMiscMxpMxpx(final Boolean b) {
    if (filled.get(59)) {
      throw new IllegalStateException("hub.misc/MXP_MXPX already set");
    } else {
      filled.set(59);
    }

    isPrecompileXorMxpMxpxXorCallSmcFailureCallerWillRevertXorCreateFlagXorValueNextIsZero.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pMiscMxpOffset1Hi(final Bytes b) {
    if (filled.get(142)) {
      throw new IllegalStateException("hub.misc/MXP_OFFSET_1_HI already set");
    } else {
      filled.set(142);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      rlpaddrSaltLoXorMxpOffset1Hi.put((byte) 0);
    }
    rlpaddrSaltLoXorMxpOffset1Hi.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscMxpOffset1Lo(final Bytes b) {
    if (filled.get(143)) {
      throw new IllegalStateException("hub.misc/MXP_OFFSET_1_LO already set");
    } else {
      filled.set(143);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      trmRawAddressHiXorMxpOffset1Lo.put((byte) 0);
    }
    trmRawAddressHiXorMxpOffset1Lo.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscMxpOffset2Hi(final Bytes b) {
    if (filled.get(144)) {
      throw new IllegalStateException("hub.misc/MXP_OFFSET_2_HI already set");
    } else {
      filled.set(144);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      mxpOffset2Hi.put((byte) 0);
    }
    mxpOffset2Hi.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscMxpOffset2Lo(final Bytes b) {
    if (filled.get(145)) {
      throw new IllegalStateException("hub.misc/MXP_OFFSET_2_LO already set");
    } else {
      filled.set(145);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      mxpOffset2Lo.put((byte) 0);
    }
    mxpOffset2Lo.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscMxpSize1Hi(final Bytes b) {
    if (filled.get(146)) {
      throw new IllegalStateException("hub.misc/MXP_SIZE_1_HI already set");
    } else {
      filled.set(146);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      mxpSize1Hi.put((byte) 0);
    }
    mxpSize1Hi.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscMxpSize1Lo(final Bytes b) {
    if (filled.get(147)) {
      throw new IllegalStateException("hub.misc/MXP_SIZE_1_LO already set");
    } else {
      filled.set(147);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      mxpSize1Lo.put((byte) 0);
    }
    mxpSize1Lo.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscMxpSize2Hi(final Bytes b) {
    if (filled.get(148)) {
      throw new IllegalStateException("hub.misc/MXP_SIZE_2_HI already set");
    } else {
      filled.set(148);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      mxpSize2Hi.put((byte) 0);
    }
    mxpSize2Hi.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscMxpSize2Lo(final Bytes b) {
    if (filled.get(149)) {
      throw new IllegalStateException("hub.misc/MXP_SIZE_2_LO already set");
    } else {
      filled.set(149);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      mxpSize2Lo.put((byte) 0);
    }
    mxpSize2Lo.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscMxpWords(final Bytes b) {
    if (filled.get(150)) {
      throw new IllegalStateException("hub.misc/MXP_WORDS already set");
    } else {
      filled.set(150);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      mxpWords.put((byte) 0);
    }
    mxpWords.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscOobData1(final Bytes b) {
    if (filled.get(151)) {
      throw new IllegalStateException("hub.misc/OOB_DATA_1 already set");
    } else {
      filled.set(151);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      oobData1.put((byte) 0);
    }
    oobData1.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscOobData2(final Bytes b) {
    if (filled.get(152)) {
      throw new IllegalStateException("hub.misc/OOB_DATA_2 already set");
    } else {
      filled.set(152);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      oobData2.put((byte) 0);
    }
    oobData2.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscOobData3(final Bytes b) {
    if (filled.get(153)) {
      throw new IllegalStateException("hub.misc/OOB_DATA_3 already set");
    } else {
      filled.set(153);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      oobData3.put((byte) 0);
    }
    oobData3.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscOobData4(final Bytes b) {
    if (filled.get(154)) {
      throw new IllegalStateException("hub.misc/OOB_DATA_4 already set");
    } else {
      filled.set(154);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      oobData4.put((byte) 0);
    }
    oobData4.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscOobData5(final Bytes b) {
    if (filled.get(155)) {
      throw new IllegalStateException("hub.misc/OOB_DATA_5 already set");
    } else {
      filled.set(155);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      oobData5.put((byte) 0);
    }
    oobData5.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscOobData6(final Bytes b) {
    if (filled.get(156)) {
      throw new IllegalStateException("hub.misc/OOB_DATA_6 already set");
    } else {
      filled.set(156);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      oobData6.put((byte) 0);
    }
    oobData6.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscOobData7(final Bytes b) {
    if (filled.get(157)) {
      throw new IllegalStateException("hub.misc/OOB_DATA_7 already set");
    } else {
      filled.set(157);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      oobData7.put((byte) 0);
    }
    oobData7.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscOobData8(final Bytes b) {
    if (filled.get(158)) {
      throw new IllegalStateException("hub.misc/OOB_DATA_8 already set");
    } else {
      filled.set(158);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      oobData8.put((byte) 0);
    }
    oobData8.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscOobFlag(final Boolean b) {
    if (filled.get(60)) {
      throw new IllegalStateException("hub.misc/OOB_FLAG already set");
    } else {
      filled.set(60);
    }

    markedForSelfdestructXorOobFlagXorCallSmcFailureCallerWontRevertXorDecFlag1XorValueOrigIsZero
        .put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pMiscOobInst(final long b) {
    if (filled.get(118)) {
      throw new IllegalStateException("hub.misc/OOB_INST already set");
    } else {
      filled.set(118);
    }

    returnAtOffsetXorOobInst.putLong(b);

    return this;
  }

  public Trace pMiscStpExists(final Boolean b) {
    if (filled.get(61)) {
      throw new IllegalStateException("hub.misc/STP_EXISTS already set");
    } else {
      filled.set(61);
    }

    markedForSelfdestructNewXorStpExistsXorCallSmcSuccessCallerWillRevertXorDecFlag2XorWarmth.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pMiscStpFlag(final Boolean b) {
    if (filled.get(62)) {
      throw new IllegalStateException("hub.misc/STP_FLAG already set");
    } else {
      filled.set(62);
    }

    rlpaddrFlagXorStpFlagXorCallSmcSuccessCallerWontRevertXorDecFlag3XorWarmthNew.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pMiscStpGasHi(final Bytes b) {
    if (filled.get(159)) {
      throw new IllegalStateException("hub.misc/STP_GAS_HI already set");
    } else {
      filled.set(159);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      stpGasHi.put((byte) 0);
    }
    stpGasHi.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscStpGasLo(final Bytes b) {
    if (filled.get(160)) {
      throw new IllegalStateException("hub.misc/STP_GAS_LO already set");
    } else {
      filled.set(160);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      stpGasLo.put((byte) 0);
    }
    stpGasLo.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscStpGasMxp(final Bytes b) {
    if (filled.get(122)) {
      throw new IllegalStateException("hub.misc/STP_GAS_MXP already set");
    } else {
      filled.set(122);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      nonceXorStpGasMxpXorBasefee.put((byte) 0);
    }
    nonceXorStpGasMxpXorBasefee.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscStpGasPaidOutOfPocket(final Bytes b) {
    if (filled.get(123)) {
      throw new IllegalStateException("hub.misc/STP_GAS_PAID_OUT_OF_POCKET already set");
    } else {
      filled.set(123);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      nonceNewXorStpGasPaidOutOfPocketXorGasInitiallyAvailable.put((byte) 0);
    }
    nonceNewXorStpGasPaidOutOfPocketXorGasInitiallyAvailable.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscStpGasStipend(final long b) {
    if (filled.get(119)) {
      throw new IllegalStateException("hub.misc/STP_GAS_STIPEND already set");
    } else {
      filled.set(119);
    }

    returnDataContextNumberXorStpGasStipend.putLong(b);

    return this;
  }

  public Trace pMiscStpGasUpfrontGasCost(final Bytes b) {
    if (filled.get(124)) {
      throw new IllegalStateException("hub.misc/STP_GAS_UPFRONT_GAS_COST already set");
    } else {
      filled.set(124);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      stpGasUpfrontGasCostXorGasLeftover.put((byte) 0);
    }
    stpGasUpfrontGasCostXorGasLeftover.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscStpInstruction(final long b) {
    if (filled.get(120)) {
      throw new IllegalStateException("hub.misc/STP_INSTRUCTION already set");
    } else {
      filled.set(120);
    }

    returnDataOffsetXorStpInstruction.putLong(b);

    return this;
  }

  public Trace pMiscStpOogx(final Boolean b) {
    if (filled.get(63)) {
      throw new IllegalStateException("hub.misc/STP_OOGX already set");
    } else {
      filled.set(63);
    }

    rlpaddrRecipeXorStpOogxXorCreateAbortXorDecFlag4.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pMiscStpValueHi(final Bytes b) {
    if (filled.get(161)) {
      throw new IllegalStateException("hub.misc/STP_VALUE_HI already set");
    } else {
      filled.set(161);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      stpValueHi.put((byte) 0);
    }
    stpValueHi.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscStpValueLo(final Bytes b) {
    if (filled.get(162)) {
      throw new IllegalStateException("hub.misc/STP_VALUE_LO already set");
    } else {
      filled.set(162);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      stpValueLo.put((byte) 0);
    }
    stpValueLo.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pMiscStpWarmth(final Boolean b) {
    if (filled.get(64)) {
      throw new IllegalStateException("hub.misc/STP_WARMTH already set");
    } else {
      filled.set(64);
    }

    romlexFlagXorStpWarmthXorCreateEmptyInitCodeWillRevertXorDupFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioCallAbort(final Boolean b) {
    if (filled.get(52)) {
      throw new IllegalStateException("hub.scenario/CALL_ABORT already set");
    } else {
      filled.set(52);
    }

    deploymentStatusXorIsRootXorCcsrFlagXorCallAbortXorAccFlagXorUnconstrainedFinalXorCopyTxcd.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioCallEoaSuccessCallerWillRevert(final Boolean b) {
    if (filled.get(53)) {
      throw new IllegalStateException(
          "hub.scenario/CALL_EOA_SUCCESS_CALLER_WILL_REVERT already set");
    } else {
      filled.set(53);
    }

    deploymentStatusInftyXorIsStaticXorExpFlagXorCallEoaSuccessCallerWillRevertXorAddFlagXorUnconstrainedFirstXorIsDeployment
        .put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioCallEoaSuccessCallerWontRevert(final Boolean b) {
    if (filled.get(54)) {
      throw new IllegalStateException(
          "hub.scenario/CALL_EOA_SUCCESS_CALLER_WONT_REVERT already set");
    } else {
      filled.set(54);
    }

    deploymentStatusNewXorUpdateXorMmuFlagXorCallEoaSuccessCallerWontRevertXorBinFlagXorValueCurrChangesXorIsType2
        .put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioCallException(final Boolean b) {
    if (filled.get(55)) {
      throw new IllegalStateException("hub.scenario/CALL_EXCEPTION already set");
    } else {
      filled.set(55);
    }

    existsXorMmuSuccessBitXorCallExceptionXorBtcFlagXorValueCurrIsOrigXorRequiresEvmExecution.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioCallPrcFailure(final Boolean b) {
    if (filled.get(56)) {
      throw new IllegalStateException("hub.scenario/CALL_PRC_FAILURE already set");
    } else {
      filled.set(56);
    }

    existsNewXorMxpDeploysXorCallPrcFailureXorCallFlagXorValueCurrIsZeroXorStatusCode.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioCallPrcSuccessCallerWillRevert(final Boolean b) {
    if (filled.get(57)) {
      throw new IllegalStateException(
          "hub.scenario/CALL_PRC_SUCCESS_CALLER_WILL_REVERT already set");
    } else {
      filled.set(57);
    }

    hasCodeXorMxpFlagXorCallPrcSuccessCallerWillRevertXorConFlagXorValueNextIsCurr.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioCallPrcSuccessCallerWontRevert(final Boolean b) {
    if (filled.get(58)) {
      throw new IllegalStateException(
          "hub.scenario/CALL_PRC_SUCCESS_CALLER_WONT_REVERT already set");
    } else {
      filled.set(58);
    }

    hasCodeNewXorMxpMtntopXorCallPrcSuccessCallerWontRevertXorCopyFlagXorValueNextIsOrig.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioCallSmcFailureCallerWillRevert(final Boolean b) {
    if (filled.get(59)) {
      throw new IllegalStateException(
          "hub.scenario/CALL_SMC_FAILURE_CALLER_WILL_REVERT already set");
    } else {
      filled.set(59);
    }

    isPrecompileXorMxpMxpxXorCallSmcFailureCallerWillRevertXorCreateFlagXorValueNextIsZero.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioCallSmcFailureCallerWontRevert(final Boolean b) {
    if (filled.get(60)) {
      throw new IllegalStateException(
          "hub.scenario/CALL_SMC_FAILURE_CALLER_WONT_REVERT already set");
    } else {
      filled.set(60);
    }

    markedForSelfdestructXorOobFlagXorCallSmcFailureCallerWontRevertXorDecFlag1XorValueOrigIsZero
        .put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioCallSmcSuccessCallerWillRevert(final Boolean b) {
    if (filled.get(61)) {
      throw new IllegalStateException(
          "hub.scenario/CALL_SMC_SUCCESS_CALLER_WILL_REVERT already set");
    } else {
      filled.set(61);
    }

    markedForSelfdestructNewXorStpExistsXorCallSmcSuccessCallerWillRevertXorDecFlag2XorWarmth.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioCallSmcSuccessCallerWontRevert(final Boolean b) {
    if (filled.get(62)) {
      throw new IllegalStateException(
          "hub.scenario/CALL_SMC_SUCCESS_CALLER_WONT_REVERT already set");
    } else {
      filled.set(62);
    }

    rlpaddrFlagXorStpFlagXorCallSmcSuccessCallerWontRevertXorDecFlag3XorWarmthNew.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioCreateAbort(final Boolean b) {
    if (filled.get(63)) {
      throw new IllegalStateException("hub.scenario/CREATE_ABORT already set");
    } else {
      filled.set(63);
    }

    rlpaddrRecipeXorStpOogxXorCreateAbortXorDecFlag4.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioCreateEmptyInitCodeWillRevert(final Boolean b) {
    if (filled.get(64)) {
      throw new IllegalStateException(
          "hub.scenario/CREATE_EMPTY_INIT_CODE_WILL_REVERT already set");
    } else {
      filled.set(64);
    }

    romlexFlagXorStpWarmthXorCreateEmptyInitCodeWillRevertXorDupFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioCreateEmptyInitCodeWontRevert(final Boolean b) {
    if (filled.get(65)) {
      throw new IllegalStateException(
          "hub.scenario/CREATE_EMPTY_INIT_CODE_WONT_REVERT already set");
    } else {
      filled.set(65);
    }

    trmFlagXorCreateEmptyInitCodeWontRevertXorExtFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioCreateException(final Boolean b) {
    if (filled.get(66)) {
      throw new IllegalStateException("hub.scenario/CREATE_EXCEPTION already set");
    } else {
      filled.set(66);
    }

    warmthXorCreateExceptionXorHaltFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioCreateFailureConditionWillRevert(final Boolean b) {
    if (filled.get(67)) {
      throw new IllegalStateException(
          "hub.scenario/CREATE_FAILURE_CONDITION_WILL_REVERT already set");
    } else {
      filled.set(67);
    }

    warmthNewXorCreateFailureConditionWillRevertXorHashInfoFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioCreateFailureConditionWontRevert(final Boolean b) {
    if (filled.get(68)) {
      throw new IllegalStateException(
          "hub.scenario/CREATE_FAILURE_CONDITION_WONT_REVERT already set");
    } else {
      filled.set(68);
    }

    createFailureConditionWontRevertXorIcpx.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioCreateNonemptyInitCodeFailureWillRevert(final Boolean b) {
    if (filled.get(69)) {
      throw new IllegalStateException(
          "hub.scenario/CREATE_NONEMPTY_INIT_CODE_FAILURE_WILL_REVERT already set");
    } else {
      filled.set(69);
    }

    createNonemptyInitCodeFailureWillRevertXorInvalidFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioCreateNonemptyInitCodeFailureWontRevert(final Boolean b) {
    if (filled.get(70)) {
      throw new IllegalStateException(
          "hub.scenario/CREATE_NONEMPTY_INIT_CODE_FAILURE_WONT_REVERT already set");
    } else {
      filled.set(70);
    }

    createNonemptyInitCodeFailureWontRevertXorJumpx.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioCreateNonemptyInitCodeSuccessWillRevert(final Boolean b) {
    if (filled.get(71)) {
      throw new IllegalStateException(
          "hub.scenario/CREATE_NONEMPTY_INIT_CODE_SUCCESS_WILL_REVERT already set");
    } else {
      filled.set(71);
    }

    createNonemptyInitCodeSuccessWillRevertXorJumpDestinationVettingRequired.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioCreateNonemptyInitCodeSuccessWontRevert(final Boolean b) {
    if (filled.get(72)) {
      throw new IllegalStateException(
          "hub.scenario/CREATE_NONEMPTY_INIT_CODE_SUCCESS_WONT_REVERT already set");
    } else {
      filled.set(72);
    }

    createNonemptyInitCodeSuccessWontRevertXorJumpFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioPrcBlake2F(final Boolean b) {
    if (filled.get(73)) {
      throw new IllegalStateException("hub.scenario/PRC_BLAKE2f already set");
    } else {
      filled.set(73);
    }

    prcBlake2FXorKecFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioPrcCalleeGas(final long b) {
    if (filled.get(106)) {
      throw new IllegalStateException("hub.scenario/PRC_CALLEE_GAS already set");
    } else {
      filled.set(106);
    }

    addressHiXorAccountAddressHiXorCcrsStampXorPrcCalleeGasXorStaticGasXorAddressHiXorBatchNum
        .putLong(b);

    return this;
  }

  public Trace pScenarioPrcCallerGas(final long b) {
    if (filled.get(107)) {
      throw new IllegalStateException("hub.scenario/PRC_CALLER_GAS already set");
    } else {
      filled.set(107);
    }

    codeFragmentIndexXorAccountDeploymentNumberXorExpInstXorPrcCallerGasXorDeploymentNumberXorCallDataSize
        .putLong(b);

    return this;
  }

  public Trace pScenarioPrcCdo(final long b) {
    if (filled.get(108)) {
      throw new IllegalStateException("hub.scenario/PRC_CDO already set");
    } else {
      filled.set(108);
    }

    codeSizeXorByteCodeAddressHiXorMmuAuxIdXorPrcCdoXorDeploymentNumberInftyXorCoinbaseAddressHi
        .putLong(b);

    return this;
  }

  public Trace pScenarioPrcCds(final long b) {
    if (filled.get(109)) {
      throw new IllegalStateException("hub.scenario/PRC_CDS already set");
    } else {
      filled.set(109);
    }

    codeSizeNewXorByteCodeCodeFragmentIndexXorMmuExoSumXorPrcCdsXorFromAddressHi.putLong(b);

    return this;
  }

  public Trace pScenarioPrcEcadd(final Boolean b) {
    if (filled.get(74)) {
      throw new IllegalStateException("hub.scenario/PRC_ECADD already set");
    } else {
      filled.set(74);
    }

    prcEcaddXorLogFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioPrcEcmul(final Boolean b) {
    if (filled.get(75)) {
      throw new IllegalStateException("hub.scenario/PRC_ECMUL already set");
    } else {
      filled.set(75);
    }

    prcEcmulXorLogInfoFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioPrcEcpairing(final Boolean b) {
    if (filled.get(76)) {
      throw new IllegalStateException("hub.scenario/PRC_ECPAIRING already set");
    } else {
      filled.set(76);
    }

    prcEcpairingXorMachineStateFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioPrcEcrecover(final Boolean b) {
    if (filled.get(77)) {
      throw new IllegalStateException("hub.scenario/PRC_ECRECOVER already set");
    } else {
      filled.set(77);
    }

    prcEcrecoverXorMaxcsx.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioPrcFailureKnownToHub(final Boolean b) {
    if (filled.get(78)) {
      throw new IllegalStateException("hub.scenario/PRC_FAILURE_KNOWN_TO_HUB already set");
    } else {
      filled.set(78);
    }

    prcFailureKnownToHubXorModFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioPrcFailureKnownToRam(final Boolean b) {
    if (filled.get(79)) {
      throw new IllegalStateException("hub.scenario/PRC_FAILURE_KNOWN_TO_RAM already set");
    } else {
      filled.set(79);
    }

    prcFailureKnownToRamXorMulFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioPrcIdentity(final Boolean b) {
    if (filled.get(80)) {
      throw new IllegalStateException("hub.scenario/PRC_IDENTITY already set");
    } else {
      filled.set(80);
    }

    prcIdentityXorMxpx.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioPrcModexp(final Boolean b) {
    if (filled.get(81)) {
      throw new IllegalStateException("hub.scenario/PRC_MODEXP already set");
    } else {
      filled.set(81);
    }

    prcModexpXorMxpFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioPrcRac(final long b) {
    if (filled.get(110)) {
      throw new IllegalStateException("hub.scenario/PRC_RAC already set");
    } else {
      filled.set(110);
    }

    deploymentNumberXorByteCodeDeploymentNumberXorMmuInstXorPrcRacXorInitCodeSize.putLong(b);

    return this;
  }

  public Trace pScenarioPrcRao(final long b) {
    if (filled.get(111)) {
      throw new IllegalStateException("hub.scenario/PRC_RAO already set");
    } else {
      filled.set(111);
    }

    deploymentNumberInftyXorByteCodeDeploymentStatusXorMmuPhaseXorPrcRaoXorToAddressHi.putLong(b);

    return this;
  }

  public Trace pScenarioPrcReturnGas(final long b) {
    if (filled.get(112)) {
      throw new IllegalStateException("hub.scenario/PRC_RETURN_GAS already set");
    } else {
      filled.set(112);
    }

    deploymentNumberNewXorCallerAddressHiXorMmuRefOffsetXorPrcReturnGas.putLong(b);

    return this;
  }

  public Trace pScenarioPrcRipemd160(final Boolean b) {
    if (filled.get(82)) {
      throw new IllegalStateException("hub.scenario/PRC_RIPEMD-160 already set");
    } else {
      filled.set(82);
    }

    prcRipemd160XorOogx.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioPrcSha2256(final Boolean b) {
    if (filled.get(83)) {
      throw new IllegalStateException("hub.scenario/PRC_SHA2-256 already set");
    } else {
      filled.set(83);
    }

    prcSha2256XorOpcx.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioPrcSuccessCallerWillRevert(final Boolean b) {
    if (filled.get(84)) {
      throw new IllegalStateException("hub.scenario/PRC_SUCCESS_CALLER_WILL_REVERT already set");
    } else {
      filled.set(84);
    }

    prcSuccessCallerWillRevertXorPushpopFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioPrcSuccessCallerWontRevert(final Boolean b) {
    if (filled.get(85)) {
      throw new IllegalStateException("hub.scenario/PRC_SUCCESS_CALLER_WONT_REVERT already set");
    } else {
      filled.set(85);
    }

    prcSuccessCallerWontRevertXorRdcx.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioReturnException(final Boolean b) {
    if (filled.get(86)) {
      throw new IllegalStateException("hub.scenario/RETURN_EXCEPTION already set");
    } else {
      filled.set(86);
    }

    returnExceptionXorShfFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioReturnFromDeploymentEmptyCodeWillRevert(final Boolean b) {
    if (filled.get(87)) {
      throw new IllegalStateException(
          "hub.scenario/RETURN_FROM_DEPLOYMENT_EMPTY_CODE_WILL_REVERT already set");
    } else {
      filled.set(87);
    }

    returnFromDeploymentEmptyCodeWillRevertXorSox.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioReturnFromDeploymentEmptyCodeWontRevert(final Boolean b) {
    if (filled.get(88)) {
      throw new IllegalStateException(
          "hub.scenario/RETURN_FROM_DEPLOYMENT_EMPTY_CODE_WONT_REVERT already set");
    } else {
      filled.set(88);
    }

    returnFromDeploymentEmptyCodeWontRevertXorSstorex.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioReturnFromDeploymentNonemptyCodeWillRevert(final Boolean b) {
    if (filled.get(89)) {
      throw new IllegalStateException(
          "hub.scenario/RETURN_FROM_DEPLOYMENT_NONEMPTY_CODE_WILL_REVERT already set");
    } else {
      filled.set(89);
    }

    returnFromDeploymentNonemptyCodeWillRevertXorStackramFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioReturnFromDeploymentNonemptyCodeWontRevert(final Boolean b) {
    if (filled.get(90)) {
      throw new IllegalStateException(
          "hub.scenario/RETURN_FROM_DEPLOYMENT_NONEMPTY_CODE_WONT_REVERT already set");
    } else {
      filled.set(90);
    }

    returnFromDeploymentNonemptyCodeWontRevertXorStackItemPop1.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioReturnFromMessageCallWillTouchRam(final Boolean b) {
    if (filled.get(91)) {
      throw new IllegalStateException(
          "hub.scenario/RETURN_FROM_MESSAGE_CALL_WILL_TOUCH_RAM already set");
    } else {
      filled.set(91);
    }

    returnFromMessageCallWillTouchRamXorStackItemPop2.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioReturnFromMessageCallWontTouchRam(final Boolean b) {
    if (filled.get(92)) {
      throw new IllegalStateException(
          "hub.scenario/RETURN_FROM_MESSAGE_CALL_WONT_TOUCH_RAM already set");
    } else {
      filled.set(92);
    }

    returnFromMessageCallWontTouchRamXorStackItemPop3.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioSelfdestructException(final Boolean b) {
    if (filled.get(93)) {
      throw new IllegalStateException("hub.scenario/SELFDESTRUCT_EXCEPTION already set");
    } else {
      filled.set(93);
    }

    selfdestructExceptionXorStackItemPop4.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioSelfdestructWillRevert(final Boolean b) {
    if (filled.get(94)) {
      throw new IllegalStateException("hub.scenario/SELFDESTRUCT_WILL_REVERT already set");
    } else {
      filled.set(94);
    }

    selfdestructWillRevertXorStaticx.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioSelfdestructWontRevertAlreadyMarked(final Boolean b) {
    if (filled.get(95)) {
      throw new IllegalStateException(
          "hub.scenario/SELFDESTRUCT_WONT_REVERT_ALREADY_MARKED already set");
    } else {
      filled.set(95);
    }

    selfdestructWontRevertAlreadyMarkedXorStaticFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pScenarioSelfdestructWontRevertNotYetMarked(final Boolean b) {
    if (filled.get(96)) {
      throw new IllegalStateException(
          "hub.scenario/SELFDESTRUCT_WONT_REVERT_NOT_YET_MARKED already set");
    } else {
      filled.set(96);
    }

    selfdestructWontRevertNotYetMarkedXorStoFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackAccFlag(final Boolean b) {
    if (filled.get(52)) {
      throw new IllegalStateException("hub.stack/ACC_FLAG already set");
    } else {
      filled.set(52);
    }

    deploymentStatusXorIsRootXorCcsrFlagXorCallAbortXorAccFlagXorUnconstrainedFinalXorCopyTxcd.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackAddFlag(final Boolean b) {
    if (filled.get(53)) {
      throw new IllegalStateException("hub.stack/ADD_FLAG already set");
    } else {
      filled.set(53);
    }

    deploymentStatusInftyXorIsStaticXorExpFlagXorCallEoaSuccessCallerWillRevertXorAddFlagXorUnconstrainedFirstXorIsDeployment
        .put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackAlpha(final UnsignedByte b) {
    if (filled.get(101)) {
      throw new IllegalStateException("hub.stack/ALPHA already set");
    } else {
      filled.set(101);
    }

    alpha.put(b.toByte());

    return this;
  }

  public Trace pStackBinFlag(final Boolean b) {
    if (filled.get(54)) {
      throw new IllegalStateException("hub.stack/BIN_FLAG already set");
    } else {
      filled.set(54);
    }

    deploymentStatusNewXorUpdateXorMmuFlagXorCallEoaSuccessCallerWontRevertXorBinFlagXorValueCurrChangesXorIsType2
        .put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackBtcFlag(final Boolean b) {
    if (filled.get(55)) {
      throw new IllegalStateException("hub.stack/BTC_FLAG already set");
    } else {
      filled.set(55);
    }

    existsXorMmuSuccessBitXorCallExceptionXorBtcFlagXorValueCurrIsOrigXorRequiresEvmExecution.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackCallFlag(final Boolean b) {
    if (filled.get(56)) {
      throw new IllegalStateException("hub.stack/CALL_FLAG already set");
    } else {
      filled.set(56);
    }

    existsNewXorMxpDeploysXorCallPrcFailureXorCallFlagXorValueCurrIsZeroXorStatusCode.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackConFlag(final Boolean b) {
    if (filled.get(57)) {
      throw new IllegalStateException("hub.stack/CON_FLAG already set");
    } else {
      filled.set(57);
    }

    hasCodeXorMxpFlagXorCallPrcSuccessCallerWillRevertXorConFlagXorValueNextIsCurr.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackCopyFlag(final Boolean b) {
    if (filled.get(58)) {
      throw new IllegalStateException("hub.stack/COPY_FLAG already set");
    } else {
      filled.set(58);
    }

    hasCodeNewXorMxpMtntopXorCallPrcSuccessCallerWontRevertXorCopyFlagXorValueNextIsOrig.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackCreateFlag(final Boolean b) {
    if (filled.get(59)) {
      throw new IllegalStateException("hub.stack/CREATE_FLAG already set");
    } else {
      filled.set(59);
    }

    isPrecompileXorMxpMxpxXorCallSmcFailureCallerWillRevertXorCreateFlagXorValueNextIsZero.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackDecFlag1(final Boolean b) {
    if (filled.get(60)) {
      throw new IllegalStateException("hub.stack/DEC_FLAG_1 already set");
    } else {
      filled.set(60);
    }

    markedForSelfdestructXorOobFlagXorCallSmcFailureCallerWontRevertXorDecFlag1XorValueOrigIsZero
        .put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackDecFlag2(final Boolean b) {
    if (filled.get(61)) {
      throw new IllegalStateException("hub.stack/DEC_FLAG_2 already set");
    } else {
      filled.set(61);
    }

    markedForSelfdestructNewXorStpExistsXorCallSmcSuccessCallerWillRevertXorDecFlag2XorWarmth.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackDecFlag3(final Boolean b) {
    if (filled.get(62)) {
      throw new IllegalStateException("hub.stack/DEC_FLAG_3 already set");
    } else {
      filled.set(62);
    }

    rlpaddrFlagXorStpFlagXorCallSmcSuccessCallerWontRevertXorDecFlag3XorWarmthNew.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackDecFlag4(final Boolean b) {
    if (filled.get(63)) {
      throw new IllegalStateException("hub.stack/DEC_FLAG_4 already set");
    } else {
      filled.set(63);
    }

    rlpaddrRecipeXorStpOogxXorCreateAbortXorDecFlag4.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackDelta(final UnsignedByte b) {
    if (filled.get(102)) {
      throw new IllegalStateException("hub.stack/DELTA already set");
    } else {
      filled.set(102);
    }

    delta.put(b.toByte());

    return this;
  }

  public Trace pStackDupFlag(final Boolean b) {
    if (filled.get(64)) {
      throw new IllegalStateException("hub.stack/DUP_FLAG already set");
    } else {
      filled.set(64);
    }

    romlexFlagXorStpWarmthXorCreateEmptyInitCodeWillRevertXorDupFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackExtFlag(final Boolean b) {
    if (filled.get(65)) {
      throw new IllegalStateException("hub.stack/EXT_FLAG already set");
    } else {
      filled.set(65);
    }

    trmFlagXorCreateEmptyInitCodeWontRevertXorExtFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackHaltFlag(final Boolean b) {
    if (filled.get(66)) {
      throw new IllegalStateException("hub.stack/HALT_FLAG already set");
    } else {
      filled.set(66);
    }

    warmthXorCreateExceptionXorHaltFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackHashInfoFlag(final Boolean b) {
    if (filled.get(67)) {
      throw new IllegalStateException("hub.stack/HASH_INFO_FLAG already set");
    } else {
      filled.set(67);
    }

    warmthNewXorCreateFailureConditionWillRevertXorHashInfoFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackHashInfoKeccakHi(final Bytes b) {
    if (filled.get(131)) {
      throw new IllegalStateException("hub.stack/HASH_INFO_KECCAK_HI already set");
    } else {
      filled.set(131);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      addressLoXorAccountAddressLoXorExpData1XorHashInfoKeccakHiXorAddressLoXorCoinbaseAddressLo
          .put((byte) 0);
    }
    addressLoXorAccountAddressLoXorExpData1XorHashInfoKeccakHiXorAddressLoXorCoinbaseAddressLo.put(
        b.toArrayUnsafe());

    return this;
  }

  public Trace pStackHashInfoKeccakLo(final Bytes b) {
    if (filled.get(132)) {
      throw new IllegalStateException("hub.stack/HASH_INFO_KECCAK_LO already set");
    } else {
      filled.set(132);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      balanceXorByteCodeAddressLoXorExpData2XorHashInfoKeccakLoXorStorageKeyHiXorFromAddressLo.put(
          (byte) 0);
    }
    balanceXorByteCodeAddressLoXorExpData2XorHashInfoKeccakLoXorStorageKeyHiXorFromAddressLo.put(
        b.toArrayUnsafe());

    return this;
  }

  public Trace pStackIcpx(final Boolean b) {
    if (filled.get(68)) {
      throw new IllegalStateException("hub.stack/ICPX already set");
    } else {
      filled.set(68);
    }

    createFailureConditionWontRevertXorIcpx.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackInstruction(final Bytes b) {
    if (filled.get(163)) {
      throw new IllegalStateException("hub.stack/INSTRUCTION already set");
    } else {
      filled.set(163);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      instruction.put((byte) 0);
    }
    instruction.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pStackInvalidFlag(final Boolean b) {
    if (filled.get(69)) {
      throw new IllegalStateException("hub.stack/INVALID_FLAG already set");
    } else {
      filled.set(69);
    }

    createNonemptyInitCodeFailureWillRevertXorInvalidFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackJumpDestinationVettingRequired(final Boolean b) {
    if (filled.get(71)) {
      throw new IllegalStateException("hub.stack/JUMP_DESTINATION_VETTING_REQUIRED already set");
    } else {
      filled.set(71);
    }

    createNonemptyInitCodeSuccessWillRevertXorJumpDestinationVettingRequired.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackJumpFlag(final Boolean b) {
    if (filled.get(72)) {
      throw new IllegalStateException("hub.stack/JUMP_FLAG already set");
    } else {
      filled.set(72);
    }

    createNonemptyInitCodeSuccessWontRevertXorJumpFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackJumpx(final Boolean b) {
    if (filled.get(70)) {
      throw new IllegalStateException("hub.stack/JUMPX already set");
    } else {
      filled.set(70);
    }

    createNonemptyInitCodeFailureWontRevertXorJumpx.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackKecFlag(final Boolean b) {
    if (filled.get(73)) {
      throw new IllegalStateException("hub.stack/KEC_FLAG already set");
    } else {
      filled.set(73);
    }

    prcBlake2FXorKecFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackLogFlag(final Boolean b) {
    if (filled.get(74)) {
      throw new IllegalStateException("hub.stack/LOG_FLAG already set");
    } else {
      filled.set(74);
    }

    prcEcaddXorLogFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackLogInfoFlag(final Boolean b) {
    if (filled.get(75)) {
      throw new IllegalStateException("hub.stack/LOG_INFO_FLAG already set");
    } else {
      filled.set(75);
    }

    prcEcmulXorLogInfoFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackMachineStateFlag(final Boolean b) {
    if (filled.get(76)) {
      throw new IllegalStateException("hub.stack/MACHINE_STATE_FLAG already set");
    } else {
      filled.set(76);
    }

    prcEcpairingXorMachineStateFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackMaxcsx(final Boolean b) {
    if (filled.get(77)) {
      throw new IllegalStateException("hub.stack/MAXCSX already set");
    } else {
      filled.set(77);
    }

    prcEcrecoverXorMaxcsx.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackModFlag(final Boolean b) {
    if (filled.get(78)) {
      throw new IllegalStateException("hub.stack/MOD_FLAG already set");
    } else {
      filled.set(78);
    }

    prcFailureKnownToHubXorModFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackMulFlag(final Boolean b) {
    if (filled.get(79)) {
      throw new IllegalStateException("hub.stack/MUL_FLAG already set");
    } else {
      filled.set(79);
    }

    prcFailureKnownToRamXorMulFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackMxpFlag(final Boolean b) {
    if (filled.get(81)) {
      throw new IllegalStateException("hub.stack/MXP_FLAG already set");
    } else {
      filled.set(81);
    }

    prcModexpXorMxpFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackMxpx(final Boolean b) {
    if (filled.get(80)) {
      throw new IllegalStateException("hub.stack/MXPX already set");
    } else {
      filled.set(80);
    }

    prcIdentityXorMxpx.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackNbAdded(final UnsignedByte b) {
    if (filled.get(103)) {
      throw new IllegalStateException("hub.stack/NB_ADDED already set");
    } else {
      filled.set(103);
    }

    nbAdded.put(b.toByte());

    return this;
  }

  public Trace pStackNbRemoved(final UnsignedByte b) {
    if (filled.get(104)) {
      throw new IllegalStateException("hub.stack/NB_REMOVED already set");
    } else {
      filled.set(104);
    }

    nbRemoved.put(b.toByte());

    return this;
  }

  public Trace pStackOogx(final Boolean b) {
    if (filled.get(82)) {
      throw new IllegalStateException("hub.stack/OOGX already set");
    } else {
      filled.set(82);
    }

    prcRipemd160XorOogx.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackOpcx(final Boolean b) {
    if (filled.get(83)) {
      throw new IllegalStateException("hub.stack/OPCX already set");
    } else {
      filled.set(83);
    }

    prcSha2256XorOpcx.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackPushValueHi(final Bytes b) {
    if (filled.get(133)) {
      throw new IllegalStateException("hub.stack/PUSH_VALUE_HI already set");
    } else {
      filled.set(133);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      balanceNewXorCallerAddressLoXorExpData3XorPushValueHiXorStorageKeyLoXorInitialBalance.put(
          (byte) 0);
    }
    balanceNewXorCallerAddressLoXorExpData3XorPushValueHiXorStorageKeyLoXorInitialBalance.put(
        b.toArrayUnsafe());

    return this;
  }

  public Trace pStackPushValueLo(final Bytes b) {
    if (filled.get(134)) {
      throw new IllegalStateException("hub.stack/PUSH_VALUE_LO already set");
    } else {
      filled.set(134);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      codeHashHiXorCallValueXorExpData4XorPushValueLoXorValueCurrHiXorToAddressLo.put((byte) 0);
    }
    codeHashHiXorCallValueXorExpData4XorPushValueLoXorValueCurrHiXorToAddressLo.put(
        b.toArrayUnsafe());

    return this;
  }

  public Trace pStackPushpopFlag(final Boolean b) {
    if (filled.get(84)) {
      throw new IllegalStateException("hub.stack/PUSHPOP_FLAG already set");
    } else {
      filled.set(84);
    }

    prcSuccessCallerWillRevertXorPushpopFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackRdcx(final Boolean b) {
    if (filled.get(85)) {
      throw new IllegalStateException("hub.stack/RDCX already set");
    } else {
      filled.set(85);
    }

    prcSuccessCallerWontRevertXorRdcx.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackShfFlag(final Boolean b) {
    if (filled.get(86)) {
      throw new IllegalStateException("hub.stack/SHF_FLAG already set");
    } else {
      filled.set(86);
    }

    returnExceptionXorShfFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackSox(final Boolean b) {
    if (filled.get(87)) {
      throw new IllegalStateException("hub.stack/SOX already set");
    } else {
      filled.set(87);
    }

    returnFromDeploymentEmptyCodeWillRevertXorSox.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackSstorex(final Boolean b) {
    if (filled.get(88)) {
      throw new IllegalStateException("hub.stack/SSTOREX already set");
    } else {
      filled.set(88);
    }

    returnFromDeploymentEmptyCodeWontRevertXorSstorex.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackStackItemHeight1(final Bytes b) {
    if (filled.get(164)) {
      throw new IllegalStateException("hub.stack/STACK_ITEM_HEIGHT_1 already set");
    } else {
      filled.set(164);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      stackItemHeight1.put((byte) 0);
    }
    stackItemHeight1.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pStackStackItemHeight2(final Bytes b) {
    if (filled.get(165)) {
      throw new IllegalStateException("hub.stack/STACK_ITEM_HEIGHT_2 already set");
    } else {
      filled.set(165);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      stackItemHeight2.put((byte) 0);
    }
    stackItemHeight2.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pStackStackItemHeight3(final Bytes b) {
    if (filled.get(166)) {
      throw new IllegalStateException("hub.stack/STACK_ITEM_HEIGHT_3 already set");
    } else {
      filled.set(166);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      stackItemHeight3.put((byte) 0);
    }
    stackItemHeight3.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pStackStackItemHeight4(final Bytes b) {
    if (filled.get(167)) {
      throw new IllegalStateException("hub.stack/STACK_ITEM_HEIGHT_4 already set");
    } else {
      filled.set(167);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      stackItemHeight4.put((byte) 0);
    }
    stackItemHeight4.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pStackStackItemPop1(final Boolean b) {
    if (filled.get(90)) {
      throw new IllegalStateException("hub.stack/STACK_ITEM_POP_1 already set");
    } else {
      filled.set(90);
    }

    returnFromDeploymentNonemptyCodeWontRevertXorStackItemPop1.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackStackItemPop2(final Boolean b) {
    if (filled.get(91)) {
      throw new IllegalStateException("hub.stack/STACK_ITEM_POP_2 already set");
    } else {
      filled.set(91);
    }

    returnFromMessageCallWillTouchRamXorStackItemPop2.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackStackItemPop3(final Boolean b) {
    if (filled.get(92)) {
      throw new IllegalStateException("hub.stack/STACK_ITEM_POP_3 already set");
    } else {
      filled.set(92);
    }

    returnFromMessageCallWontTouchRamXorStackItemPop3.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackStackItemPop4(final Boolean b) {
    if (filled.get(93)) {
      throw new IllegalStateException("hub.stack/STACK_ITEM_POP_4 already set");
    } else {
      filled.set(93);
    }

    selfdestructExceptionXorStackItemPop4.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackStackItemStamp1(final Bytes b) {
    if (filled.get(168)) {
      throw new IllegalStateException("hub.stack/STACK_ITEM_STAMP_1 already set");
    } else {
      filled.set(168);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      stackItemStamp1.put((byte) 0);
    }
    stackItemStamp1.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pStackStackItemStamp2(final Bytes b) {
    if (filled.get(169)) {
      throw new IllegalStateException("hub.stack/STACK_ITEM_STAMP_2 already set");
    } else {
      filled.set(169);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      stackItemStamp2.put((byte) 0);
    }
    stackItemStamp2.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pStackStackItemStamp3(final Bytes b) {
    if (filled.get(170)) {
      throw new IllegalStateException("hub.stack/STACK_ITEM_STAMP_3 already set");
    } else {
      filled.set(170);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      stackItemStamp3.put((byte) 0);
    }
    stackItemStamp3.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pStackStackItemStamp4(final Bytes b) {
    if (filled.get(171)) {
      throw new IllegalStateException("hub.stack/STACK_ITEM_STAMP_4 already set");
    } else {
      filled.set(171);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      stackItemStamp4.put((byte) 0);
    }
    stackItemStamp4.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pStackStackItemValueHi1(final Bytes b) {
    if (filled.get(172)) {
      throw new IllegalStateException("hub.stack/STACK_ITEM_VALUE_HI_1 already set");
    } else {
      filled.set(172);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      stackItemValueHi1.put((byte) 0);
    }
    stackItemValueHi1.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pStackStackItemValueHi2(final Bytes b) {
    if (filled.get(173)) {
      throw new IllegalStateException("hub.stack/STACK_ITEM_VALUE_HI_2 already set");
    } else {
      filled.set(173);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      stackItemValueHi2.put((byte) 0);
    }
    stackItemValueHi2.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pStackStackItemValueHi3(final Bytes b) {
    if (filled.get(174)) {
      throw new IllegalStateException("hub.stack/STACK_ITEM_VALUE_HI_3 already set");
    } else {
      filled.set(174);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      stackItemValueHi3.put((byte) 0);
    }
    stackItemValueHi3.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pStackStackItemValueHi4(final Bytes b) {
    if (filled.get(175)) {
      throw new IllegalStateException("hub.stack/STACK_ITEM_VALUE_HI_4 already set");
    } else {
      filled.set(175);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      stackItemValueHi4.put((byte) 0);
    }
    stackItemValueHi4.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pStackStackItemValueLo1(final Bytes b) {
    if (filled.get(176)) {
      throw new IllegalStateException("hub.stack/STACK_ITEM_VALUE_LO_1 already set");
    } else {
      filled.set(176);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      stackItemValueLo1.put((byte) 0);
    }
    stackItemValueLo1.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pStackStackItemValueLo2(final Bytes b) {
    if (filled.get(177)) {
      throw new IllegalStateException("hub.stack/STACK_ITEM_VALUE_LO_2 already set");
    } else {
      filled.set(177);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      stackItemValueLo2.put((byte) 0);
    }
    stackItemValueLo2.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pStackStackItemValueLo3(final Bytes b) {
    if (filled.get(178)) {
      throw new IllegalStateException("hub.stack/STACK_ITEM_VALUE_LO_3 already set");
    } else {
      filled.set(178);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      stackItemValueLo3.put((byte) 0);
    }
    stackItemValueLo3.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pStackStackItemValueLo4(final Bytes b) {
    if (filled.get(179)) {
      throw new IllegalStateException("hub.stack/STACK_ITEM_VALUE_LO_4 already set");
    } else {
      filled.set(179);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      stackItemValueLo4.put((byte) 0);
    }
    stackItemValueLo4.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pStackStackramFlag(final Boolean b) {
    if (filled.get(89)) {
      throw new IllegalStateException("hub.stack/STACKRAM_FLAG already set");
    } else {
      filled.set(89);
    }

    returnFromDeploymentNonemptyCodeWillRevertXorStackramFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackStaticFlag(final Boolean b) {
    if (filled.get(95)) {
      throw new IllegalStateException("hub.stack/STATIC_FLAG already set");
    } else {
      filled.set(95);
    }

    selfdestructWontRevertAlreadyMarkedXorStaticFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackStaticGas(final long b) {
    if (filled.get(106)) {
      throw new IllegalStateException("hub.stack/STATIC_GAS already set");
    } else {
      filled.set(106);
    }

    addressHiXorAccountAddressHiXorCcrsStampXorPrcCalleeGasXorStaticGasXorAddressHiXorBatchNum
        .putLong(b);

    return this;
  }

  public Trace pStackStaticx(final Boolean b) {
    if (filled.get(94)) {
      throw new IllegalStateException("hub.stack/STATICX already set");
    } else {
      filled.set(94);
    }

    selfdestructWillRevertXorStaticx.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackStoFlag(final Boolean b) {
    if (filled.get(96)) {
      throw new IllegalStateException("hub.stack/STO_FLAG already set");
    } else {
      filled.set(96);
    }

    selfdestructWontRevertNotYetMarkedXorStoFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackSux(final Boolean b) {
    if (filled.get(97)) {
      throw new IllegalStateException("hub.stack/SUX already set");
    } else {
      filled.set(97);
    }

    sux.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackSwapFlag(final Boolean b) {
    if (filled.get(98)) {
      throw new IllegalStateException("hub.stack/SWAP_FLAG already set");
    } else {
      filled.set(98);
    }

    swapFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackTxnFlag(final Boolean b) {
    if (filled.get(99)) {
      throw new IllegalStateException("hub.stack/TXN_FLAG already set");
    } else {
      filled.set(99);
    }

    txnFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStackWcpFlag(final Boolean b) {
    if (filled.get(100)) {
      throw new IllegalStateException("hub.stack/WCP_FLAG already set");
    } else {
      filled.set(100);
    }

    wcpFlag.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStorageAddressHi(final long b) {
    if (filled.get(106)) {
      throw new IllegalStateException("hub.storage/ADDRESS_HI already set");
    } else {
      filled.set(106);
    }

    addressHiXorAccountAddressHiXorCcrsStampXorPrcCalleeGasXorStaticGasXorAddressHiXorBatchNum
        .putLong(b);

    return this;
  }

  public Trace pStorageAddressLo(final Bytes b) {
    if (filled.get(131)) {
      throw new IllegalStateException("hub.storage/ADDRESS_LO already set");
    } else {
      filled.set(131);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      addressLoXorAccountAddressLoXorExpData1XorHashInfoKeccakHiXorAddressLoXorCoinbaseAddressLo
          .put((byte) 0);
    }
    addressLoXorAccountAddressLoXorExpData1XorHashInfoKeccakHiXorAddressLoXorCoinbaseAddressLo.put(
        b.toArrayUnsafe());

    return this;
  }

  public Trace pStorageDeploymentNumber(final long b) {
    if (filled.get(107)) {
      throw new IllegalStateException("hub.storage/DEPLOYMENT_NUMBER already set");
    } else {
      filled.set(107);
    }

    codeFragmentIndexXorAccountDeploymentNumberXorExpInstXorPrcCallerGasXorDeploymentNumberXorCallDataSize
        .putLong(b);

    return this;
  }

  public Trace pStorageDeploymentNumberInfty(final long b) {
    if (filled.get(108)) {
      throw new IllegalStateException("hub.storage/DEPLOYMENT_NUMBER_INFTY already set");
    } else {
      filled.set(108);
    }

    codeSizeXorByteCodeAddressHiXorMmuAuxIdXorPrcCdoXorDeploymentNumberInftyXorCoinbaseAddressHi
        .putLong(b);

    return this;
  }

  public Trace pStorageStorageKeyHi(final Bytes b) {
    if (filled.get(132)) {
      throw new IllegalStateException("hub.storage/STORAGE_KEY_HI already set");
    } else {
      filled.set(132);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      balanceXorByteCodeAddressLoXorExpData2XorHashInfoKeccakLoXorStorageKeyHiXorFromAddressLo.put(
          (byte) 0);
    }
    balanceXorByteCodeAddressLoXorExpData2XorHashInfoKeccakLoXorStorageKeyHiXorFromAddressLo.put(
        b.toArrayUnsafe());

    return this;
  }

  public Trace pStorageStorageKeyLo(final Bytes b) {
    if (filled.get(133)) {
      throw new IllegalStateException("hub.storage/STORAGE_KEY_LO already set");
    } else {
      filled.set(133);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      balanceNewXorCallerAddressLoXorExpData3XorPushValueHiXorStorageKeyLoXorInitialBalance.put(
          (byte) 0);
    }
    balanceNewXorCallerAddressLoXorExpData3XorPushValueHiXorStorageKeyLoXorInitialBalance.put(
        b.toArrayUnsafe());

    return this;
  }

  public Trace pStorageUnconstrainedFinal(final Boolean b) {
    if (filled.get(52)) {
      throw new IllegalStateException("hub.storage/UNCONSTRAINED_FINAL already set");
    } else {
      filled.set(52);
    }

    deploymentStatusXorIsRootXorCcsrFlagXorCallAbortXorAccFlagXorUnconstrainedFinalXorCopyTxcd.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStorageUnconstrainedFirst(final Boolean b) {
    if (filled.get(53)) {
      throw new IllegalStateException("hub.storage/UNCONSTRAINED_FIRST already set");
    } else {
      filled.set(53);
    }

    deploymentStatusInftyXorIsStaticXorExpFlagXorCallEoaSuccessCallerWillRevertXorAddFlagXorUnconstrainedFirstXorIsDeployment
        .put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStorageValueCurrChanges(final Boolean b) {
    if (filled.get(54)) {
      throw new IllegalStateException("hub.storage/VALUE_CURR_CHANGES already set");
    } else {
      filled.set(54);
    }

    deploymentStatusNewXorUpdateXorMmuFlagXorCallEoaSuccessCallerWontRevertXorBinFlagXorValueCurrChangesXorIsType2
        .put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStorageValueCurrHi(final Bytes b) {
    if (filled.get(134)) {
      throw new IllegalStateException("hub.storage/VALUE_CURR_HI already set");
    } else {
      filled.set(134);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      codeHashHiXorCallValueXorExpData4XorPushValueLoXorValueCurrHiXorToAddressLo.put((byte) 0);
    }
    codeHashHiXorCallValueXorExpData4XorPushValueLoXorValueCurrHiXorToAddressLo.put(
        b.toArrayUnsafe());

    return this;
  }

  public Trace pStorageValueCurrIsOrig(final Boolean b) {
    if (filled.get(55)) {
      throw new IllegalStateException("hub.storage/VALUE_CURR_IS_ORIG already set");
    } else {
      filled.set(55);
    }

    existsXorMmuSuccessBitXorCallExceptionXorBtcFlagXorValueCurrIsOrigXorRequiresEvmExecution.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStorageValueCurrIsZero(final Boolean b) {
    if (filled.get(56)) {
      throw new IllegalStateException("hub.storage/VALUE_CURR_IS_ZERO already set");
    } else {
      filled.set(56);
    }

    existsNewXorMxpDeploysXorCallPrcFailureXorCallFlagXorValueCurrIsZeroXorStatusCode.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStorageValueCurrLo(final Bytes b) {
    if (filled.get(135)) {
      throw new IllegalStateException("hub.storage/VALUE_CURR_LO already set");
    } else {
      filled.set(135);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      codeHashHiNewXorExpData5XorValueCurrLoXorValue.put((byte) 0);
    }
    codeHashHiNewXorExpData5XorValueCurrLoXorValue.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pStorageValueNextHi(final Bytes b) {
    if (filled.get(136)) {
      throw new IllegalStateException("hub.storage/VALUE_NEXT_HI already set");
    } else {
      filled.set(136);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      codeHashLoXorMmuLimb1XorValueNextHi.put((byte) 0);
    }
    codeHashLoXorMmuLimb1XorValueNextHi.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pStorageValueNextIsCurr(final Boolean b) {
    if (filled.get(57)) {
      throw new IllegalStateException("hub.storage/VALUE_NEXT_IS_CURR already set");
    } else {
      filled.set(57);
    }

    hasCodeXorMxpFlagXorCallPrcSuccessCallerWillRevertXorConFlagXorValueNextIsCurr.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStorageValueNextIsOrig(final Boolean b) {
    if (filled.get(58)) {
      throw new IllegalStateException("hub.storage/VALUE_NEXT_IS_ORIG already set");
    } else {
      filled.set(58);
    }

    hasCodeNewXorMxpMtntopXorCallPrcSuccessCallerWontRevertXorCopyFlagXorValueNextIsOrig.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStorageValueNextIsZero(final Boolean b) {
    if (filled.get(59)) {
      throw new IllegalStateException("hub.storage/VALUE_NEXT_IS_ZERO already set");
    } else {
      filled.set(59);
    }

    isPrecompileXorMxpMxpxXorCallSmcFailureCallerWillRevertXorCreateFlagXorValueNextIsZero.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStorageValueNextLo(final Bytes b) {
    if (filled.get(137)) {
      throw new IllegalStateException("hub.storage/VALUE_NEXT_LO already set");
    } else {
      filled.set(137);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      codeHashLoNewXorMmuLimb2XorValueNextLo.put((byte) 0);
    }
    codeHashLoNewXorMmuLimb2XorValueNextLo.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pStorageValueOrigHi(final Bytes b) {
    if (filled.get(138)) {
      throw new IllegalStateException("hub.storage/VALUE_ORIG_HI already set");
    } else {
      filled.set(138);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      rlpaddrDepAddrLoXorMmuSrcOffsetHiXorValueOrigHi.put((byte) 0);
    }
    rlpaddrDepAddrLoXorMmuSrcOffsetHiXorValueOrigHi.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pStorageValueOrigIsZero(final Boolean b) {
    if (filled.get(60)) {
      throw new IllegalStateException("hub.storage/VALUE_ORIG_IS_ZERO already set");
    } else {
      filled.set(60);
    }

    markedForSelfdestructXorOobFlagXorCallSmcFailureCallerWontRevertXorDecFlag1XorValueOrigIsZero
        .put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStorageValueOrigLo(final Bytes b) {
    if (filled.get(139)) {
      throw new IllegalStateException("hub.storage/VALUE_ORIG_LO already set");
    } else {
      filled.set(139);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      rlpaddrKecHiXorMmuSrcOffsetLoXorValueOrigLo.put((byte) 0);
    }
    rlpaddrKecHiXorMmuSrcOffsetLoXorValueOrigLo.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pStorageWarmth(final Boolean b) {
    if (filled.get(61)) {
      throw new IllegalStateException("hub.storage/WARMTH already set");
    } else {
      filled.set(61);
    }

    markedForSelfdestructNewXorStpExistsXorCallSmcSuccessCallerWillRevertXorDecFlag2XorWarmth.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pStorageWarmthNew(final Boolean b) {
    if (filled.get(62)) {
      throw new IllegalStateException("hub.storage/WARMTH_NEW already set");
    } else {
      filled.set(62);
    }

    rlpaddrFlagXorStpFlagXorCallSmcSuccessCallerWontRevertXorDecFlag3XorWarmthNew.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pTransactionBasefee(final Bytes b) {
    if (filled.get(122)) {
      throw new IllegalStateException("hub.transaction/BASEFEE already set");
    } else {
      filled.set(122);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      nonceXorStpGasMxpXorBasefee.put((byte) 0);
    }
    nonceXorStpGasMxpXorBasefee.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pTransactionBatchNum(final long b) {
    if (filled.get(106)) {
      throw new IllegalStateException("hub.transaction/BATCH_NUM already set");
    } else {
      filled.set(106);
    }

    addressHiXorAccountAddressHiXorCcrsStampXorPrcCalleeGasXorStaticGasXorAddressHiXorBatchNum
        .putLong(b);

    return this;
  }

  public Trace pTransactionCallDataSize(final long b) {
    if (filled.get(107)) {
      throw new IllegalStateException("hub.transaction/CALL_DATA_SIZE already set");
    } else {
      filled.set(107);
    }

    codeFragmentIndexXorAccountDeploymentNumberXorExpInstXorPrcCallerGasXorDeploymentNumberXorCallDataSize
        .putLong(b);

    return this;
  }

  public Trace pTransactionCoinbaseAddressHi(final long b) {
    if (filled.get(108)) {
      throw new IllegalStateException("hub.transaction/COINBASE_ADDRESS_HI already set");
    } else {
      filled.set(108);
    }

    codeSizeXorByteCodeAddressHiXorMmuAuxIdXorPrcCdoXorDeploymentNumberInftyXorCoinbaseAddressHi
        .putLong(b);

    return this;
  }

  public Trace pTransactionCoinbaseAddressLo(final Bytes b) {
    if (filled.get(131)) {
      throw new IllegalStateException("hub.transaction/COINBASE_ADDRESS_LO already set");
    } else {
      filled.set(131);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      addressLoXorAccountAddressLoXorExpData1XorHashInfoKeccakHiXorAddressLoXorCoinbaseAddressLo
          .put((byte) 0);
    }
    addressLoXorAccountAddressLoXorExpData1XorHashInfoKeccakHiXorAddressLoXorCoinbaseAddressLo.put(
        b.toArrayUnsafe());

    return this;
  }

  public Trace pTransactionCopyTxcd(final Boolean b) {
    if (filled.get(52)) {
      throw new IllegalStateException("hub.transaction/COPY_TXCD already set");
    } else {
      filled.set(52);
    }

    deploymentStatusXorIsRootXorCcsrFlagXorCallAbortXorAccFlagXorUnconstrainedFinalXorCopyTxcd.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pTransactionFromAddressHi(final long b) {
    if (filled.get(109)) {
      throw new IllegalStateException("hub.transaction/FROM_ADDRESS_HI already set");
    } else {
      filled.set(109);
    }

    codeSizeNewXorByteCodeCodeFragmentIndexXorMmuExoSumXorPrcCdsXorFromAddressHi.putLong(b);

    return this;
  }

  public Trace pTransactionFromAddressLo(final Bytes b) {
    if (filled.get(132)) {
      throw new IllegalStateException("hub.transaction/FROM_ADDRESS_LO already set");
    } else {
      filled.set(132);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      balanceXorByteCodeAddressLoXorExpData2XorHashInfoKeccakLoXorStorageKeyHiXorFromAddressLo.put(
          (byte) 0);
    }
    balanceXorByteCodeAddressLoXorExpData2XorHashInfoKeccakLoXorStorageKeyHiXorFromAddressLo.put(
        b.toArrayUnsafe());

    return this;
  }

  public Trace pTransactionGasInitiallyAvailable(final Bytes b) {
    if (filled.get(123)) {
      throw new IllegalStateException("hub.transaction/GAS_INITIALLY_AVAILABLE already set");
    } else {
      filled.set(123);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      nonceNewXorStpGasPaidOutOfPocketXorGasInitiallyAvailable.put((byte) 0);
    }
    nonceNewXorStpGasPaidOutOfPocketXorGasInitiallyAvailable.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pTransactionGasLeftover(final Bytes b) {
    if (filled.get(124)) {
      throw new IllegalStateException("hub.transaction/GAS_LEFTOVER already set");
    } else {
      filled.set(124);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      stpGasUpfrontGasCostXorGasLeftover.put((byte) 0);
    }
    stpGasUpfrontGasCostXorGasLeftover.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pTransactionGasLimit(final Bytes b) {
    if (filled.get(125)) {
      throw new IllegalStateException("hub.transaction/GAS_LIMIT already set");
    } else {
      filled.set(125);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      gasLimit.put((byte) 0);
    }
    gasLimit.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pTransactionGasPrice(final Bytes b) {
    if (filled.get(126)) {
      throw new IllegalStateException("hub.transaction/GAS_PRICE already set");
    } else {
      filled.set(126);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      gasPrice.put((byte) 0);
    }
    gasPrice.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pTransactionInitCodeSize(final long b) {
    if (filled.get(110)) {
      throw new IllegalStateException("hub.transaction/INIT_CODE_SIZE already set");
    } else {
      filled.set(110);
    }

    deploymentNumberXorByteCodeDeploymentNumberXorMmuInstXorPrcRacXorInitCodeSize.putLong(b);

    return this;
  }

  public Trace pTransactionInitialBalance(final Bytes b) {
    if (filled.get(133)) {
      throw new IllegalStateException("hub.transaction/INITIAL_BALANCE already set");
    } else {
      filled.set(133);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      balanceNewXorCallerAddressLoXorExpData3XorPushValueHiXorStorageKeyLoXorInitialBalance.put(
          (byte) 0);
    }
    balanceNewXorCallerAddressLoXorExpData3XorPushValueHiXorStorageKeyLoXorInitialBalance.put(
        b.toArrayUnsafe());

    return this;
  }

  public Trace pTransactionIsDeployment(final Boolean b) {
    if (filled.get(53)) {
      throw new IllegalStateException("hub.transaction/IS_DEPLOYMENT already set");
    } else {
      filled.set(53);
    }

    deploymentStatusInftyXorIsStaticXorExpFlagXorCallEoaSuccessCallerWillRevertXorAddFlagXorUnconstrainedFirstXorIsDeployment
        .put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pTransactionIsType2(final Boolean b) {
    if (filled.get(54)) {
      throw new IllegalStateException("hub.transaction/IS_TYPE2 already set");
    } else {
      filled.set(54);
    }

    deploymentStatusNewXorUpdateXorMmuFlagXorCallEoaSuccessCallerWontRevertXorBinFlagXorValueCurrChangesXorIsType2
        .put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace pTransactionNonce(final Bytes b) {
    if (filled.get(127)) {
      throw new IllegalStateException("hub.transaction/NONCE already set");
    } else {
      filled.set(127);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      nonce.put((byte) 0);
    }
    nonce.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pTransactionPriorityFeePerGas(final Bytes b) {
    if (filled.get(128)) {
      throw new IllegalStateException("hub.transaction/PRIORITY_FEE_PER_GAS already set");
    } else {
      filled.set(128);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      priorityFeePerGas.put((byte) 0);
    }
    priorityFeePerGas.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pTransactionRefundCounterInfinity(final Bytes b) {
    if (filled.get(129)) {
      throw new IllegalStateException("hub.transaction/REFUND_COUNTER_INFINITY already set");
    } else {
      filled.set(129);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      refundCounterInfinity.put((byte) 0);
    }
    refundCounterInfinity.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pTransactionRefundEffective(final Bytes b) {
    if (filled.get(130)) {
      throw new IllegalStateException("hub.transaction/REFUND_EFFECTIVE already set");
    } else {
      filled.set(130);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      refundEffective.put((byte) 0);
    }
    refundEffective.put(b.toArrayUnsafe());

    return this;
  }

  public Trace pTransactionRequiresEvmExecution(final Boolean b) {
    if (filled.get(55)) {
      throw new IllegalStateException("hub.transaction/REQUIRES_EVM_EXECUTION already set");
    } else {
      filled.set(55);
    }

    existsXorMmuSuccessBitXorCallExceptionXorBtcFlagXorValueCurrIsOrigXorRequiresEvmExecution.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pTransactionStatusCode(final Boolean b) {
    if (filled.get(56)) {
      throw new IllegalStateException("hub.transaction/STATUS_CODE already set");
    } else {
      filled.set(56);
    }

    existsNewXorMxpDeploysXorCallPrcFailureXorCallFlagXorValueCurrIsZeroXorStatusCode.put(
        (byte) (b ? 1 : 0));

    return this;
  }

  public Trace pTransactionToAddressHi(final long b) {
    if (filled.get(111)) {
      throw new IllegalStateException("hub.transaction/TO_ADDRESS_HI already set");
    } else {
      filled.set(111);
    }

    deploymentNumberInftyXorByteCodeDeploymentStatusXorMmuPhaseXorPrcRaoXorToAddressHi.putLong(b);

    return this;
  }

  public Trace pTransactionToAddressLo(final Bytes b) {
    if (filled.get(134)) {
      throw new IllegalStateException("hub.transaction/TO_ADDRESS_LO already set");
    } else {
      filled.set(134);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      codeHashHiXorCallValueXorExpData4XorPushValueLoXorValueCurrHiXorToAddressLo.put((byte) 0);
    }
    codeHashHiXorCallValueXorExpData4XorPushValueLoXorValueCurrHiXorToAddressLo.put(
        b.toArrayUnsafe());

    return this;
  }

  public Trace pTransactionValue(final Bytes b) {
    if (filled.get(135)) {
      throw new IllegalStateException("hub.transaction/VALUE already set");
    } else {
      filled.set(135);
    }

    final byte[] bs = b.toArrayUnsafe();
    for (int i = bs.length; i < 32; i++) {
      codeHashHiNewXorExpData5XorValueCurrLoXorValue.put((byte) 0);
    }
    codeHashHiNewXorExpData5XorValueCurrLoXorValue.put(b.toArrayUnsafe());

    return this;
  }

  public Trace peekAtAccount(final Boolean b) {
    if (filled.get(28)) {
      throw new IllegalStateException("hub.PEEK_AT_ACCOUNT already set");
    } else {
      filled.set(28);
    }

    peekAtAccount.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace peekAtContext(final Boolean b) {
    if (filled.get(29)) {
      throw new IllegalStateException("hub.PEEK_AT_CONTEXT already set");
    } else {
      filled.set(29);
    }

    peekAtContext.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace peekAtMiscellaneous(final Boolean b) {
    if (filled.get(30)) {
      throw new IllegalStateException("hub.PEEK_AT_MISCELLANEOUS already set");
    } else {
      filled.set(30);
    }

    peekAtMiscellaneous.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace peekAtScenario(final Boolean b) {
    if (filled.get(31)) {
      throw new IllegalStateException("hub.PEEK_AT_SCENARIO already set");
    } else {
      filled.set(31);
    }

    peekAtScenario.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace peekAtStack(final Boolean b) {
    if (filled.get(32)) {
      throw new IllegalStateException("hub.PEEK_AT_STACK already set");
    } else {
      filled.set(32);
    }

    peekAtStack.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace peekAtStorage(final Boolean b) {
    if (filled.get(33)) {
      throw new IllegalStateException("hub.PEEK_AT_STORAGE already set");
    } else {
      filled.set(33);
    }

    peekAtStorage.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace peekAtTransaction(final Boolean b) {
    if (filled.get(34)) {
      throw new IllegalStateException("hub.PEEK_AT_TRANSACTION already set");
    } else {
      filled.set(34);
    }

    peekAtTransaction.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace programCounter(final long b) {
    if (filled.get(35)) {
      throw new IllegalStateException("hub.PROGRAM_COUNTER already set");
    } else {
      filled.set(35);
    }

    programCounter.putLong(b);

    return this;
  }

  public Trace programCounterNew(final long b) {
    if (filled.get(36)) {
      throw new IllegalStateException("hub.PROGRAM_COUNTER_NEW already set");
    } else {
      filled.set(36);
    }

    programCounterNew.putLong(b);

    return this;
  }

  public Trace refundCounter(final long b) {
    if (filled.get(37)) {
      throw new IllegalStateException("hub.REFUND_COUNTER already set");
    } else {
      filled.set(37);
    }

    refundCounter.putLong(b);

    return this;
  }

  public Trace refundCounterNew(final long b) {
    if (filled.get(38)) {
      throw new IllegalStateException("hub.REFUND_COUNTER_NEW already set");
    } else {
      filled.set(38);
    }

    refundCounterNew.putLong(b);

    return this;
  }

  public Trace stoFinal(final Boolean b) {
    if (filled.get(50)) {
      throw new IllegalStateException("hub.sto_FINAL already set");
    } else {
      filled.set(50);
    }

    stoFinal.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace stoFirst(final Boolean b) {
    if (filled.get(51)) {
      throw new IllegalStateException("hub.sto_FIRST already set");
    } else {
      filled.set(51);
    }

    stoFirst.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace subStamp(final long b) {
    if (filled.get(39)) {
      throw new IllegalStateException("hub.SUB_STAMP already set");
    } else {
      filled.set(39);
    }

    subStamp.putLong(b);

    return this;
  }

  public Trace twoLineInstruction(final Boolean b) {
    if (filled.get(40)) {
      throw new IllegalStateException("hub.TWO_LINE_INSTRUCTION already set");
    } else {
      filled.set(40);
    }

    twoLineInstruction.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace txExec(final Boolean b) {
    if (filled.get(41)) {
      throw new IllegalStateException("hub.TX_EXEC already set");
    } else {
      filled.set(41);
    }

    txExec.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace txFinl(final Boolean b) {
    if (filled.get(42)) {
      throw new IllegalStateException("hub.TX_FINL already set");
    } else {
      filled.set(42);
    }

    txFinl.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace txInit(final Boolean b) {
    if (filled.get(43)) {
      throw new IllegalStateException("hub.TX_INIT already set");
    } else {
      filled.set(43);
    }

    txInit.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace txSkip(final Boolean b) {
    if (filled.get(44)) {
      throw new IllegalStateException("hub.TX_SKIP already set");
    } else {
      filled.set(44);
    }

    txSkip.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace txWarm(final Boolean b) {
    if (filled.get(45)) {
      throw new IllegalStateException("hub.TX_WARM already set");
    } else {
      filled.set(45);
    }

    txWarm.put((byte) (b ? 1 : 0));

    return this;
  }

  public Trace validateRow() {
    if (!filled.get(0)) {
      throw new IllegalStateException("hub.ABSOLUTE_TRANSACTION_NUMBER has not been filled");
    }

    if (!filled.get(46)) {
      throw new IllegalStateException("hub.acc_FINAL has not been filled");
    }

    if (!filled.get(47)) {
      throw new IllegalStateException("hub.acc_FIRST has not been filled");
    }

    if (!filled.get(106)) {
      throw new IllegalStateException(
          "hub.ADDRESS_HI_xor_ACCOUNT_ADDRESS_HI_xor_CCRS_STAMP_xor_PRC_CALLEE_GAS_xor_STATIC_GAS_xor_ADDRESS_HI_xor_BATCH_NUM has not been filled");
    }

    if (!filled.get(131)) {
      throw new IllegalStateException(
          "hub.ADDRESS_LO_xor_ACCOUNT_ADDRESS_LO_xor_EXP_DATA_1_xor_HASH_INFO_KECCAK_HI_xor_ADDRESS_LO_xor_COINBASE_ADDRESS_LO has not been filled");
    }

    if (!filled.get(101)) {
      throw new IllegalStateException("hub.ALPHA has not been filled");
    }

    if (!filled.get(133)) {
      throw new IllegalStateException(
          "hub.BALANCE_NEW_xor_CALLER_ADDRESS_LO_xor_EXP_DATA_3_xor_PUSH_VALUE_HI_xor_STORAGE_KEY_LO_xor_INITIAL_BALANCE has not been filled");
    }

    if (!filled.get(132)) {
      throw new IllegalStateException(
          "hub.BALANCE_xor_BYTE_CODE_ADDRESS_LO_xor_EXP_DATA_2_xor_HASH_INFO_KECCAK_LO_xor_STORAGE_KEY_HI_xor_FROM_ADDRESS_LO has not been filled");
    }

    if (!filled.get(1)) {
      throw new IllegalStateException("hub.BATCH_NUMBER has not been filled");
    }

    if (!filled.get(114)) {
      throw new IllegalStateException("hub.CALL_DATA_OFFSET_xor_MMU_SIZE has not been filled");
    }

    if (!filled.get(115)) {
      throw new IllegalStateException("hub.CALL_DATA_SIZE_xor_MMU_SRC_ID has not been filled");
    }

    if (!filled.get(105)) {
      throw new IllegalStateException("hub.CALL_STACK_DEPTH has not been filled");
    }

    if (!filled.get(2)) {
      throw new IllegalStateException("hub.CALLER_CONTEXT_NUMBER has not been filled");
    }

    if (!filled.get(3)) {
      throw new IllegalStateException("hub.CODE_FRAGMENT_INDEX has not been filled");
    }

    if (!filled.get(107)) {
      throw new IllegalStateException(
          "hub.CODE_FRAGMENT_INDEX_xor_ACCOUNT_DEPLOYMENT_NUMBER_xor_EXP_INST_xor_PRC_CALLER_GAS_xor_DEPLOYMENT_NUMBER_xor_CALL_DATA_SIZE has not been filled");
    }

    if (!filled.get(135)) {
      throw new IllegalStateException(
          "hub.CODE_HASH_HI_NEW_xor_EXP_DATA_5_xor_VALUE_CURR_LO_xor_VALUE has not been filled");
    }

    if (!filled.get(134)) {
      throw new IllegalStateException(
          "hub.CODE_HASH_HI_xor_CALL_VALUE_xor_EXP_DATA_4_xor_PUSH_VALUE_LO_xor_VALUE_CURR_HI_xor_TO_ADDRESS_LO has not been filled");
    }

    if (!filled.get(137)) {
      throw new IllegalStateException(
          "hub.CODE_HASH_LO_NEW_xor_MMU_LIMB_2_xor_VALUE_NEXT_LO has not been filled");
    }

    if (!filled.get(136)) {
      throw new IllegalStateException(
          "hub.CODE_HASH_LO_xor_MMU_LIMB_1_xor_VALUE_NEXT_HI has not been filled");
    }

    if (!filled.get(109)) {
      throw new IllegalStateException(
          "hub.CODE_SIZE_NEW_xor_BYTE_CODE_CODE_FRAGMENT_INDEX_xor_MMU_EXO_SUM_xor_PRC_CDS_xor_FROM_ADDRESS_HI has not been filled");
    }

    if (!filled.get(108)) {
      throw new IllegalStateException(
          "hub.CODE_SIZE_xor_BYTE_CODE_ADDRESS_HI_xor_MMU_AUX_ID_xor_PRC_CDO_xor_DEPLOYMENT_NUMBER_INFTY_xor_COINBASE_ADDRESS_HI has not been filled");
    }

    if (!filled.get(48)) {
      throw new IllegalStateException("hub.con_AGAIN has not been filled");
    }

    if (!filled.get(49)) {
      throw new IllegalStateException("hub.con_FIRST has not been filled");
    }

    if (!filled.get(4)) {
      throw new IllegalStateException("hub.CONTEXT_GETS_REVERTED has not been filled");
    }

    if (!filled.get(5)) {
      throw new IllegalStateException("hub.CONTEXT_MAY_CHANGE has not been filled");
    }

    if (!filled.get(6)) {
      throw new IllegalStateException("hub.CONTEXT_NUMBER has not been filled");
    }

    if (!filled.get(7)) {
      throw new IllegalStateException("hub.CONTEXT_NUMBER_NEW has not been filled");
    }

    if (!filled.get(116)) {
      throw new IllegalStateException("hub.CONTEXT_NUMBER_xor_MMU_TGT_ID has not been filled");
    }

    if (!filled.get(8)) {
      throw new IllegalStateException("hub.CONTEXT_REVERT_STAMP has not been filled");
    }

    if (!filled.get(9)) {
      throw new IllegalStateException("hub.CONTEXT_SELF_REVERTS has not been filled");
    }

    if (!filled.get(10)) {
      throw new IllegalStateException("hub.CONTEXT_WILL_REVERT has not been filled");
    }

    if (!filled.get(11)) {
      throw new IllegalStateException("hub.COUNTER_NSR has not been filled");
    }

    if (!filled.get(12)) {
      throw new IllegalStateException("hub.COUNTER_TLI has not been filled");
    }

    if (!filled.get(68)) {
      throw new IllegalStateException(
          "hub.CREATE_FAILURE_CONDITION_WONT_REVERT_xor_ICPX has not been filled");
    }

    if (!filled.get(69)) {
      throw new IllegalStateException(
          "hub.CREATE_NONEMPTY_INIT_CODE_FAILURE_WILL_REVERT_xor_INVALID_FLAG has not been filled");
    }

    if (!filled.get(70)) {
      throw new IllegalStateException(
          "hub.CREATE_NONEMPTY_INIT_CODE_FAILURE_WONT_REVERT_xor_JUMPX has not been filled");
    }

    if (!filled.get(71)) {
      throw new IllegalStateException(
          "hub.CREATE_NONEMPTY_INIT_CODE_SUCCESS_WILL_REVERT_xor_JUMP_DESTINATION_VETTING_REQUIRED has not been filled");
    }

    if (!filled.get(72)) {
      throw new IllegalStateException(
          "hub.CREATE_NONEMPTY_INIT_CODE_SUCCESS_WONT_REVERT_xor_JUMP_FLAG has not been filled");
    }

    if (!filled.get(102)) {
      throw new IllegalStateException("hub.DELTA has not been filled");
    }

    if (!filled.get(111)) {
      throw new IllegalStateException(
          "hub.DEPLOYMENT_NUMBER_INFTY_xor_BYTE_CODE_DEPLOYMENT_STATUS_xor_MMU_PHASE_xor_PRC_RAO_xor_TO_ADDRESS_HI has not been filled");
    }

    if (!filled.get(112)) {
      throw new IllegalStateException(
          "hub.DEPLOYMENT_NUMBER_NEW_xor_CALLER_ADDRESS_HI_xor_MMU_REF_OFFSET_xor_PRC_RETURN_GAS has not been filled");
    }

    if (!filled.get(110)) {
      throw new IllegalStateException(
          "hub.DEPLOYMENT_NUMBER_xor_BYTE_CODE_DEPLOYMENT_NUMBER_xor_MMU_INST_xor_PRC_RAC_xor_INIT_CODE_SIZE has not been filled");
    }

    if (!filled.get(53)) {
      throw new IllegalStateException(
          "hub.DEPLOYMENT_STATUS_INFTY_xor_IS_STATIC_xor_EXP_FLAG_xor_CALL_EOA_SUCCESS_CALLER_WILL_REVERT_xor_ADD_FLAG_xor_UNCONSTRAINED_FIRST_xor_IS_DEPLOYMENT has not been filled");
    }

    if (!filled.get(54)) {
      throw new IllegalStateException(
          "hub.DEPLOYMENT_STATUS_NEW_xor_UPDATE_xor_MMU_FLAG_xor_CALL_EOA_SUCCESS_CALLER_WONT_REVERT_xor_BIN_FLAG_xor_VALUE_CURR_CHANGES_xor_IS_TYPE2 has not been filled");
    }

    if (!filled.get(52)) {
      throw new IllegalStateException(
          "hub.DEPLOYMENT_STATUS_xor_IS_ROOT_xor_CCSR_FLAG_xor_CALL_ABORT_xor_ACC_FLAG_xor_UNCONSTRAINED_FINAL_xor_COPY_TXCD has not been filled");
    }

    if (!filled.get(13)) {
      throw new IllegalStateException("hub.DOM_STAMP has not been filled");
    }

    if (!filled.get(14)) {
      throw new IllegalStateException("hub.EXCEPTION_AHOY has not been filled");
    }

    if (!filled.get(56)) {
      throw new IllegalStateException(
          "hub.EXISTS_NEW_xor_MXP_DEPLOYS_xor_CALL_PRC_FAILURE_xor_CALL_FLAG_xor_VALUE_CURR_IS_ZERO_xor_STATUS_CODE has not been filled");
    }

    if (!filled.get(55)) {
      throw new IllegalStateException(
          "hub.EXISTS_xor_MMU_SUCCESS_BIT_xor_CALL_EXCEPTION_xor_BTC_FLAG_xor_VALUE_CURR_IS_ORIG_xor_REQUIRES_EVM_EXECUTION has not been filled");
    }

    if (!filled.get(15)) {
      throw new IllegalStateException("hub.GAS_ACTUAL has not been filled");
    }

    if (!filled.get(16)) {
      throw new IllegalStateException("hub.GAS_COST has not been filled");
    }

    if (!filled.get(17)) {
      throw new IllegalStateException("hub.GAS_EXPECTED has not been filled");
    }

    if (!filled.get(125)) {
      throw new IllegalStateException("hub.GAS_LIMIT has not been filled");
    }

    if (!filled.get(18)) {
      throw new IllegalStateException("hub.GAS_NEXT has not been filled");
    }

    if (!filled.get(126)) {
      throw new IllegalStateException("hub.GAS_PRICE has not been filled");
    }

    if (!filled.get(58)) {
      throw new IllegalStateException(
          "hub.HAS_CODE_NEW_xor_MXP_MTNTOP_xor_CALL_PRC_SUCCESS_CALLER_WONT_REVERT_xor_COPY_FLAG_xor_VALUE_NEXT_IS_ORIG has not been filled");
    }

    if (!filled.get(57)) {
      throw new IllegalStateException(
          "hub.HAS_CODE_xor_MXP_FLAG_xor_CALL_PRC_SUCCESS_CALLER_WILL_REVERT_xor_CON_FLAG_xor_VALUE_NEXT_IS_CURR has not been filled");
    }

    if (!filled.get(19)) {
      throw new IllegalStateException("hub.HASH_INFO_STAMP has not been filled");
    }

    if (!filled.get(20)) {
      throw new IllegalStateException("hub.HEIGHT has not been filled");
    }

    if (!filled.get(21)) {
      throw new IllegalStateException("hub.HEIGHT_NEW has not been filled");
    }

    if (!filled.get(22)) {
      throw new IllegalStateException("hub.HUB_STAMP has not been filled");
    }

    if (!filled.get(23)) {
      throw new IllegalStateException("hub.HUB_STAMP_TRANSACTION_END has not been filled");
    }

    if (!filled.get(163)) {
      throw new IllegalStateException("hub.INSTRUCTION has not been filled");
    }

    if (!filled.get(59)) {
      throw new IllegalStateException(
          "hub.IS_PRECOMPILE_xor_MXP_MXPX_xor_CALL_SMC_FAILURE_CALLER_WILL_REVERT_xor_CREATE_FLAG_xor_VALUE_NEXT_IS_ZERO has not been filled");
    }

    if (!filled.get(24)) {
      throw new IllegalStateException("hub.LOG_INFO_STAMP has not been filled");
    }

    if (!filled.get(61)) {
      throw new IllegalStateException(
          "hub.MARKED_FOR_SELFDESTRUCT_NEW_xor_STP_EXISTS_xor_CALL_SMC_SUCCESS_CALLER_WILL_REVERT_xor_DEC_FLAG_2_xor_WARMTH has not been filled");
    }

    if (!filled.get(60)) {
      throw new IllegalStateException(
          "hub.MARKED_FOR_SELFDESTRUCT_xor_OOB_FLAG_xor_CALL_SMC_FAILURE_CALLER_WONT_REVERT_xor_DEC_FLAG_1_xor_VALUE_ORIG_IS_ZERO has not been filled");
    }

    if (!filled.get(25)) {
      throw new IllegalStateException("hub.MMU_STAMP has not been filled");
    }

    if (!filled.get(144)) {
      throw new IllegalStateException("hub.MXP_OFFSET_2_HI has not been filled");
    }

    if (!filled.get(145)) {
      throw new IllegalStateException("hub.MXP_OFFSET_2_LO has not been filled");
    }

    if (!filled.get(146)) {
      throw new IllegalStateException("hub.MXP_SIZE_1_HI has not been filled");
    }

    if (!filled.get(147)) {
      throw new IllegalStateException("hub.MXP_SIZE_1_LO has not been filled");
    }

    if (!filled.get(148)) {
      throw new IllegalStateException("hub.MXP_SIZE_2_HI has not been filled");
    }

    if (!filled.get(149)) {
      throw new IllegalStateException("hub.MXP_SIZE_2_LO has not been filled");
    }

    if (!filled.get(26)) {
      throw new IllegalStateException("hub.MXP_STAMP has not been filled");
    }

    if (!filled.get(150)) {
      throw new IllegalStateException("hub.MXP_WORDS has not been filled");
    }

    if (!filled.get(103)) {
      throw new IllegalStateException("hub.NB_ADDED has not been filled");
    }

    if (!filled.get(104)) {
      throw new IllegalStateException("hub.NB_REMOVED has not been filled");
    }

    if (!filled.get(27)) {
      throw new IllegalStateException("hub.NON_STACK_ROWS has not been filled");
    }

    if (!filled.get(127)) {
      throw new IllegalStateException("hub.NONCE has not been filled");
    }

    if (!filled.get(123)) {
      throw new IllegalStateException(
          "hub.NONCE_NEW_xor_STP_GAS_PAID_OUT_OF_POCKET_xor_GAS_INITIALLY_AVAILABLE has not been filled");
    }

    if (!filled.get(122)) {
      throw new IllegalStateException("hub.NONCE_xor_STP_GAS_MXP_xor_BASEFEE has not been filled");
    }

    if (!filled.get(151)) {
      throw new IllegalStateException("hub.OOB_DATA_1 has not been filled");
    }

    if (!filled.get(152)) {
      throw new IllegalStateException("hub.OOB_DATA_2 has not been filled");
    }

    if (!filled.get(153)) {
      throw new IllegalStateException("hub.OOB_DATA_3 has not been filled");
    }

    if (!filled.get(154)) {
      throw new IllegalStateException("hub.OOB_DATA_4 has not been filled");
    }

    if (!filled.get(155)) {
      throw new IllegalStateException("hub.OOB_DATA_5 has not been filled");
    }

    if (!filled.get(156)) {
      throw new IllegalStateException("hub.OOB_DATA_6 has not been filled");
    }

    if (!filled.get(157)) {
      throw new IllegalStateException("hub.OOB_DATA_7 has not been filled");
    }

    if (!filled.get(158)) {
      throw new IllegalStateException("hub.OOB_DATA_8 has not been filled");
    }

    if (!filled.get(28)) {
      throw new IllegalStateException("hub.PEEK_AT_ACCOUNT has not been filled");
    }

    if (!filled.get(29)) {
      throw new IllegalStateException("hub.PEEK_AT_CONTEXT has not been filled");
    }

    if (!filled.get(30)) {
      throw new IllegalStateException("hub.PEEK_AT_MISCELLANEOUS has not been filled");
    }

    if (!filled.get(31)) {
      throw new IllegalStateException("hub.PEEK_AT_SCENARIO has not been filled");
    }

    if (!filled.get(32)) {
      throw new IllegalStateException("hub.PEEK_AT_STACK has not been filled");
    }

    if (!filled.get(33)) {
      throw new IllegalStateException("hub.PEEK_AT_STORAGE has not been filled");
    }

    if (!filled.get(34)) {
      throw new IllegalStateException("hub.PEEK_AT_TRANSACTION has not been filled");
    }

    if (!filled.get(73)) {
      throw new IllegalStateException("hub.PRC_BLAKE2f_xor_KEC_FLAG has not been filled");
    }

    if (!filled.get(74)) {
      throw new IllegalStateException("hub.PRC_ECADD_xor_LOG_FLAG has not been filled");
    }

    if (!filled.get(75)) {
      throw new IllegalStateException("hub.PRC_ECMUL_xor_LOG_INFO_FLAG has not been filled");
    }

    if (!filled.get(76)) {
      throw new IllegalStateException(
          "hub.PRC_ECPAIRING_xor_MACHINE_STATE_FLAG has not been filled");
    }

    if (!filled.get(77)) {
      throw new IllegalStateException("hub.PRC_ECRECOVER_xor_MAXCSX has not been filled");
    }

    if (!filled.get(78)) {
      throw new IllegalStateException(
          "hub.PRC_FAILURE_KNOWN_TO_HUB_xor_MOD_FLAG has not been filled");
    }

    if (!filled.get(79)) {
      throw new IllegalStateException(
          "hub.PRC_FAILURE_KNOWN_TO_RAM_xor_MUL_FLAG has not been filled");
    }

    if (!filled.get(80)) {
      throw new IllegalStateException("hub.PRC_IDENTITY_xor_MXPX has not been filled");
    }

    if (!filled.get(81)) {
      throw new IllegalStateException("hub.PRC_MODEXP_xor_MXP_FLAG has not been filled");
    }

    if (!filled.get(82)) {
      throw new IllegalStateException("hub.PRC_RIPEMD-160_xor_OOGX has not been filled");
    }

    if (!filled.get(83)) {
      throw new IllegalStateException("hub.PRC_SHA2-256_xor_OPCX has not been filled");
    }

    if (!filled.get(84)) {
      throw new IllegalStateException(
          "hub.PRC_SUCCESS_CALLER_WILL_REVERT_xor_PUSHPOP_FLAG has not been filled");
    }

    if (!filled.get(85)) {
      throw new IllegalStateException(
          "hub.PRC_SUCCESS_CALLER_WONT_REVERT_xor_RDCX has not been filled");
    }

    if (!filled.get(128)) {
      throw new IllegalStateException("hub.PRIORITY_FEE_PER_GAS has not been filled");
    }

    if (!filled.get(35)) {
      throw new IllegalStateException("hub.PROGRAM_COUNTER has not been filled");
    }

    if (!filled.get(36)) {
      throw new IllegalStateException("hub.PROGRAM_COUNTER_NEW has not been filled");
    }

    if (!filled.get(37)) {
      throw new IllegalStateException("hub.REFUND_COUNTER has not been filled");
    }

    if (!filled.get(129)) {
      throw new IllegalStateException("hub.REFUND_COUNTER_INFINITY has not been filled");
    }

    if (!filled.get(38)) {
      throw new IllegalStateException("hub.REFUND_COUNTER_NEW has not been filled");
    }

    if (!filled.get(130)) {
      throw new IllegalStateException("hub.REFUND_EFFECTIVE has not been filled");
    }

    if (!filled.get(117)) {
      throw new IllegalStateException("hub.RETURN_AT_CAPACITY_xor_MXP_INST has not been filled");
    }

    if (!filled.get(118)) {
      throw new IllegalStateException("hub.RETURN_AT_OFFSET_xor_OOB_INST has not been filled");
    }

    if (!filled.get(119)) {
      throw new IllegalStateException(
          "hub.RETURN_DATA_CONTEXT_NUMBER_xor_STP_GAS_STIPEND has not been filled");
    }

    if (!filled.get(120)) {
      throw new IllegalStateException(
          "hub.RETURN_DATA_OFFSET_xor_STP_INSTRUCTION has not been filled");
    }

    if (!filled.get(121)) {
      throw new IllegalStateException("hub.RETURN_DATA_SIZE has not been filled");
    }

    if (!filled.get(86)) {
      throw new IllegalStateException("hub.RETURN_EXCEPTION_xor_SHF_FLAG has not been filled");
    }

    if (!filled.get(87)) {
      throw new IllegalStateException(
          "hub.RETURN_FROM_DEPLOYMENT_EMPTY_CODE_WILL_REVERT_xor_SOX has not been filled");
    }

    if (!filled.get(88)) {
      throw new IllegalStateException(
          "hub.RETURN_FROM_DEPLOYMENT_EMPTY_CODE_WONT_REVERT_xor_SSTOREX has not been filled");
    }

    if (!filled.get(89)) {
      throw new IllegalStateException(
          "hub.RETURN_FROM_DEPLOYMENT_NONEMPTY_CODE_WILL_REVERT_xor_STACKRAM_FLAG has not been filled");
    }

    if (!filled.get(90)) {
      throw new IllegalStateException(
          "hub.RETURN_FROM_DEPLOYMENT_NONEMPTY_CODE_WONT_REVERT_xor_STACK_ITEM_POP_1 has not been filled");
    }

    if (!filled.get(91)) {
      throw new IllegalStateException(
          "hub.RETURN_FROM_MESSAGE_CALL_WILL_TOUCH_RAM_xor_STACK_ITEM_POP_2 has not been filled");
    }

    if (!filled.get(92)) {
      throw new IllegalStateException(
          "hub.RETURN_FROM_MESSAGE_CALL_WONT_TOUCH_RAM_xor_STACK_ITEM_POP_3 has not been filled");
    }

    if (!filled.get(113)) {
      throw new IllegalStateException(
          "hub.RLPADDR_DEP_ADDR_HI_xor_CALL_DATA_CONTEXT_NUMBER_xor_MMU_REF_SIZE has not been filled");
    }

    if (!filled.get(138)) {
      throw new IllegalStateException(
          "hub.RLPADDR_DEP_ADDR_LO_xor_MMU_SRC_OFFSET_HI_xor_VALUE_ORIG_HI has not been filled");
    }

    if (!filled.get(62)) {
      throw new IllegalStateException(
          "hub.RLPADDR_FLAG_xor_STP_FLAG_xor_CALL_SMC_SUCCESS_CALLER_WONT_REVERT_xor_DEC_FLAG_3_xor_WARMTH_NEW has not been filled");
    }

    if (!filled.get(139)) {
      throw new IllegalStateException(
          "hub.RLPADDR_KEC_HI_xor_MMU_SRC_OFFSET_LO_xor_VALUE_ORIG_LO has not been filled");
    }

    if (!filled.get(140)) {
      throw new IllegalStateException(
          "hub.RLPADDR_KEC_LO_xor_MMU_TGT_OFFSET_LO has not been filled");
    }

    if (!filled.get(63)) {
      throw new IllegalStateException(
          "hub.RLPADDR_RECIPE_xor_STP_OOGX_xor_CREATE_ABORT_xor_DEC_FLAG_4 has not been filled");
    }

    if (!filled.get(141)) {
      throw new IllegalStateException("hub.RLPADDR_SALT_HI_xor_MXP_GAS_MXP has not been filled");
    }

    if (!filled.get(142)) {
      throw new IllegalStateException(
          "hub.RLPADDR_SALT_LO_xor_MXP_OFFSET_1_HI has not been filled");
    }

    if (!filled.get(64)) {
      throw new IllegalStateException(
          "hub.ROMLEX_FLAG_xor_STP_WARMTH_xor_CREATE_EMPTY_INIT_CODE_WILL_REVERT_xor_DUP_FLAG has not been filled");
    }

    if (!filled.get(93)) {
      throw new IllegalStateException(
          "hub.SELFDESTRUCT_EXCEPTION_xor_STACK_ITEM_POP_4 has not been filled");
    }

    if (!filled.get(94)) {
      throw new IllegalStateException(
          "hub.SELFDESTRUCT_WILL_REVERT_xor_STATICX has not been filled");
    }

    if (!filled.get(95)) {
      throw new IllegalStateException(
          "hub.SELFDESTRUCT_WONT_REVERT_ALREADY_MARKED_xor_STATIC_FLAG has not been filled");
    }

    if (!filled.get(96)) {
      throw new IllegalStateException(
          "hub.SELFDESTRUCT_WONT_REVERT_NOT_YET_MARKED_xor_STO_FLAG has not been filled");
    }

    if (!filled.get(164)) {
      throw new IllegalStateException("hub.STACK_ITEM_HEIGHT_1 has not been filled");
    }

    if (!filled.get(165)) {
      throw new IllegalStateException("hub.STACK_ITEM_HEIGHT_2 has not been filled");
    }

    if (!filled.get(166)) {
      throw new IllegalStateException("hub.STACK_ITEM_HEIGHT_3 has not been filled");
    }

    if (!filled.get(167)) {
      throw new IllegalStateException("hub.STACK_ITEM_HEIGHT_4 has not been filled");
    }

    if (!filled.get(168)) {
      throw new IllegalStateException("hub.STACK_ITEM_STAMP_1 has not been filled");
    }

    if (!filled.get(169)) {
      throw new IllegalStateException("hub.STACK_ITEM_STAMP_2 has not been filled");
    }

    if (!filled.get(170)) {
      throw new IllegalStateException("hub.STACK_ITEM_STAMP_3 has not been filled");
    }

    if (!filled.get(171)) {
      throw new IllegalStateException("hub.STACK_ITEM_STAMP_4 has not been filled");
    }

    if (!filled.get(172)) {
      throw new IllegalStateException("hub.STACK_ITEM_VALUE_HI_1 has not been filled");
    }

    if (!filled.get(173)) {
      throw new IllegalStateException("hub.STACK_ITEM_VALUE_HI_2 has not been filled");
    }

    if (!filled.get(174)) {
      throw new IllegalStateException("hub.STACK_ITEM_VALUE_HI_3 has not been filled");
    }

    if (!filled.get(175)) {
      throw new IllegalStateException("hub.STACK_ITEM_VALUE_HI_4 has not been filled");
    }

    if (!filled.get(176)) {
      throw new IllegalStateException("hub.STACK_ITEM_VALUE_LO_1 has not been filled");
    }

    if (!filled.get(177)) {
      throw new IllegalStateException("hub.STACK_ITEM_VALUE_LO_2 has not been filled");
    }

    if (!filled.get(178)) {
      throw new IllegalStateException("hub.STACK_ITEM_VALUE_LO_3 has not been filled");
    }

    if (!filled.get(179)) {
      throw new IllegalStateException("hub.STACK_ITEM_VALUE_LO_4 has not been filled");
    }

    if (!filled.get(50)) {
      throw new IllegalStateException("hub.sto_FINAL has not been filled");
    }

    if (!filled.get(51)) {
      throw new IllegalStateException("hub.sto_FIRST has not been filled");
    }

    if (!filled.get(159)) {
      throw new IllegalStateException("hub.STP_GAS_HI has not been filled");
    }

    if (!filled.get(160)) {
      throw new IllegalStateException("hub.STP_GAS_LO has not been filled");
    }

    if (!filled.get(124)) {
      throw new IllegalStateException(
          "hub.STP_GAS_UPFRONT_GAS_COST_xor_GAS_LEFTOVER has not been filled");
    }

    if (!filled.get(161)) {
      throw new IllegalStateException("hub.STP_VALUE_HI has not been filled");
    }

    if (!filled.get(162)) {
      throw new IllegalStateException("hub.STP_VALUE_LO has not been filled");
    }

    if (!filled.get(39)) {
      throw new IllegalStateException("hub.SUB_STAMP has not been filled");
    }

    if (!filled.get(97)) {
      throw new IllegalStateException("hub.SUX has not been filled");
    }

    if (!filled.get(98)) {
      throw new IllegalStateException("hub.SWAP_FLAG has not been filled");
    }

    if (!filled.get(65)) {
      throw new IllegalStateException(
          "hub.TRM_FLAG_xor_CREATE_EMPTY_INIT_CODE_WONT_REVERT_xor_EXT_FLAG has not been filled");
    }

    if (!filled.get(143)) {
      throw new IllegalStateException(
          "hub.TRM_RAW_ADDRESS_HI_xor_MXP_OFFSET_1_LO has not been filled");
    }

    if (!filled.get(40)) {
      throw new IllegalStateException("hub.TWO_LINE_INSTRUCTION has not been filled");
    }

    if (!filled.get(41)) {
      throw new IllegalStateException("hub.TX_EXEC has not been filled");
    }

    if (!filled.get(42)) {
      throw new IllegalStateException("hub.TX_FINL has not been filled");
    }

    if (!filled.get(43)) {
      throw new IllegalStateException("hub.TX_INIT has not been filled");
    }

    if (!filled.get(44)) {
      throw new IllegalStateException("hub.TX_SKIP has not been filled");
    }

    if (!filled.get(45)) {
      throw new IllegalStateException("hub.TX_WARM has not been filled");
    }

    if (!filled.get(99)) {
      throw new IllegalStateException("hub.TXN_FLAG has not been filled");
    }

    if (!filled.get(67)) {
      throw new IllegalStateException(
          "hub.WARMTH_NEW_xor_CREATE_FAILURE_CONDITION_WILL_REVERT_xor_HASH_INFO_FLAG has not been filled");
    }

    if (!filled.get(66)) {
      throw new IllegalStateException(
          "hub.WARMTH_xor_CREATE_EXCEPTION_xor_HALT_FLAG has not been filled");
    }

    if (!filled.get(100)) {
      throw new IllegalStateException("hub.WCP_FLAG has not been filled");
    }

    filled.clear();
    this.currentLine++;

    return this;
  }

  public Trace fillAndValidateRow() {
    if (!filled.get(0)) {
      absoluteTransactionNumber.position(absoluteTransactionNumber.position() + 4);
    }

    if (!filled.get(46)) {
      accFinal.position(accFinal.position() + 1);
    }

    if (!filled.get(47)) {
      accFirst.position(accFirst.position() + 1);
    }

    if (!filled.get(106)) {
      addressHiXorAccountAddressHiXorCcrsStampXorPrcCalleeGasXorStaticGasXorAddressHiXorBatchNum
          .position(
              addressHiXorAccountAddressHiXorCcrsStampXorPrcCalleeGasXorStaticGasXorAddressHiXorBatchNum
                      .position()
                  + 8);
    }

    if (!filled.get(131)) {
      addressLoXorAccountAddressLoXorExpData1XorHashInfoKeccakHiXorAddressLoXorCoinbaseAddressLo
          .position(
              addressLoXorAccountAddressLoXorExpData1XorHashInfoKeccakHiXorAddressLoXorCoinbaseAddressLo
                      .position()
                  + 32);
    }

    if (!filled.get(101)) {
      alpha.position(alpha.position() + 1);
    }

    if (!filled.get(133)) {
      balanceNewXorCallerAddressLoXorExpData3XorPushValueHiXorStorageKeyLoXorInitialBalance
          .position(
              balanceNewXorCallerAddressLoXorExpData3XorPushValueHiXorStorageKeyLoXorInitialBalance
                      .position()
                  + 32);
    }

    if (!filled.get(132)) {
      balanceXorByteCodeAddressLoXorExpData2XorHashInfoKeccakLoXorStorageKeyHiXorFromAddressLo
          .position(
              balanceXorByteCodeAddressLoXorExpData2XorHashInfoKeccakLoXorStorageKeyHiXorFromAddressLo
                      .position()
                  + 32);
    }

    if (!filled.get(1)) {
      batchNumber.position(batchNumber.position() + 4);
    }

    if (!filled.get(114)) {
      callDataOffsetXorMmuSize.position(callDataOffsetXorMmuSize.position() + 8);
    }

    if (!filled.get(115)) {
      callDataSizeXorMmuSrcId.position(callDataSizeXorMmuSrcId.position() + 8);
    }

    if (!filled.get(105)) {
      callStackDepth.position(callStackDepth.position() + 2);
    }

    if (!filled.get(2)) {
      callerContextNumber.position(callerContextNumber.position() + 8);
    }

    if (!filled.get(3)) {
      codeFragmentIndex.position(codeFragmentIndex.position() + 8);
    }

    if (!filled.get(107)) {
      codeFragmentIndexXorAccountDeploymentNumberXorExpInstXorPrcCallerGasXorDeploymentNumberXorCallDataSize
          .position(
              codeFragmentIndexXorAccountDeploymentNumberXorExpInstXorPrcCallerGasXorDeploymentNumberXorCallDataSize
                      .position()
                  + 8);
    }

    if (!filled.get(135)) {
      codeHashHiNewXorExpData5XorValueCurrLoXorValue.position(
          codeHashHiNewXorExpData5XorValueCurrLoXorValue.position() + 32);
    }

    if (!filled.get(134)) {
      codeHashHiXorCallValueXorExpData4XorPushValueLoXorValueCurrHiXorToAddressLo.position(
          codeHashHiXorCallValueXorExpData4XorPushValueLoXorValueCurrHiXorToAddressLo.position()
              + 32);
    }

    if (!filled.get(137)) {
      codeHashLoNewXorMmuLimb2XorValueNextLo.position(
          codeHashLoNewXorMmuLimb2XorValueNextLo.position() + 32);
    }

    if (!filled.get(136)) {
      codeHashLoXorMmuLimb1XorValueNextHi.position(
          codeHashLoXorMmuLimb1XorValueNextHi.position() + 32);
    }

    if (!filled.get(109)) {
      codeSizeNewXorByteCodeCodeFragmentIndexXorMmuExoSumXorPrcCdsXorFromAddressHi.position(
          codeSizeNewXorByteCodeCodeFragmentIndexXorMmuExoSumXorPrcCdsXorFromAddressHi.position()
              + 8);
    }

    if (!filled.get(108)) {
      codeSizeXorByteCodeAddressHiXorMmuAuxIdXorPrcCdoXorDeploymentNumberInftyXorCoinbaseAddressHi
          .position(
              codeSizeXorByteCodeAddressHiXorMmuAuxIdXorPrcCdoXorDeploymentNumberInftyXorCoinbaseAddressHi
                      .position()
                  + 8);
    }

    if (!filled.get(48)) {
      conAgain.position(conAgain.position() + 1);
    }

    if (!filled.get(49)) {
      conFirst.position(conFirst.position() + 1);
    }

    if (!filled.get(4)) {
      contextGetsReverted.position(contextGetsReverted.position() + 1);
    }

    if (!filled.get(5)) {
      contextMayChange.position(contextMayChange.position() + 1);
    }

    if (!filled.get(6)) {
      contextNumber.position(contextNumber.position() + 8);
    }

    if (!filled.get(7)) {
      contextNumberNew.position(contextNumberNew.position() + 8);
    }

    if (!filled.get(116)) {
      contextNumberXorMmuTgtId.position(contextNumberXorMmuTgtId.position() + 8);
    }

    if (!filled.get(8)) {
      contextRevertStamp.position(contextRevertStamp.position() + 8);
    }

    if (!filled.get(9)) {
      contextSelfReverts.position(contextSelfReverts.position() + 1);
    }

    if (!filled.get(10)) {
      contextWillRevert.position(contextWillRevert.position() + 1);
    }

    if (!filled.get(11)) {
      counterNsr.position(counterNsr.position() + 2);
    }

    if (!filled.get(12)) {
      counterTli.position(counterTli.position() + 1);
    }

    if (!filled.get(68)) {
      createFailureConditionWontRevertXorIcpx.position(
          createFailureConditionWontRevertXorIcpx.position() + 1);
    }

    if (!filled.get(69)) {
      createNonemptyInitCodeFailureWillRevertXorInvalidFlag.position(
          createNonemptyInitCodeFailureWillRevertXorInvalidFlag.position() + 1);
    }

    if (!filled.get(70)) {
      createNonemptyInitCodeFailureWontRevertXorJumpx.position(
          createNonemptyInitCodeFailureWontRevertXorJumpx.position() + 1);
    }

    if (!filled.get(71)) {
      createNonemptyInitCodeSuccessWillRevertXorJumpDestinationVettingRequired.position(
          createNonemptyInitCodeSuccessWillRevertXorJumpDestinationVettingRequired.position() + 1);
    }

    if (!filled.get(72)) {
      createNonemptyInitCodeSuccessWontRevertXorJumpFlag.position(
          createNonemptyInitCodeSuccessWontRevertXorJumpFlag.position() + 1);
    }

    if (!filled.get(102)) {
      delta.position(delta.position() + 1);
    }

    if (!filled.get(111)) {
      deploymentNumberInftyXorByteCodeDeploymentStatusXorMmuPhaseXorPrcRaoXorToAddressHi.position(
          deploymentNumberInftyXorByteCodeDeploymentStatusXorMmuPhaseXorPrcRaoXorToAddressHi
                  .position()
              + 8);
    }

    if (!filled.get(112)) {
      deploymentNumberNewXorCallerAddressHiXorMmuRefOffsetXorPrcReturnGas.position(
          deploymentNumberNewXorCallerAddressHiXorMmuRefOffsetXorPrcReturnGas.position() + 8);
    }

    if (!filled.get(110)) {
      deploymentNumberXorByteCodeDeploymentNumberXorMmuInstXorPrcRacXorInitCodeSize.position(
          deploymentNumberXorByteCodeDeploymentNumberXorMmuInstXorPrcRacXorInitCodeSize.position()
              + 8);
    }

    if (!filled.get(53)) {
      deploymentStatusInftyXorIsStaticXorExpFlagXorCallEoaSuccessCallerWillRevertXorAddFlagXorUnconstrainedFirstXorIsDeployment
          .position(
              deploymentStatusInftyXorIsStaticXorExpFlagXorCallEoaSuccessCallerWillRevertXorAddFlagXorUnconstrainedFirstXorIsDeployment
                      .position()
                  + 1);
    }

    if (!filled.get(54)) {
      deploymentStatusNewXorUpdateXorMmuFlagXorCallEoaSuccessCallerWontRevertXorBinFlagXorValueCurrChangesXorIsType2
          .position(
              deploymentStatusNewXorUpdateXorMmuFlagXorCallEoaSuccessCallerWontRevertXorBinFlagXorValueCurrChangesXorIsType2
                      .position()
                  + 1);
    }

    if (!filled.get(52)) {
      deploymentStatusXorIsRootXorCcsrFlagXorCallAbortXorAccFlagXorUnconstrainedFinalXorCopyTxcd
          .position(
              deploymentStatusXorIsRootXorCcsrFlagXorCallAbortXorAccFlagXorUnconstrainedFinalXorCopyTxcd
                      .position()
                  + 1);
    }

    if (!filled.get(13)) {
      domStamp.position(domStamp.position() + 8);
    }

    if (!filled.get(14)) {
      exceptionAhoy.position(exceptionAhoy.position() + 1);
    }

    if (!filled.get(56)) {
      existsNewXorMxpDeploysXorCallPrcFailureXorCallFlagXorValueCurrIsZeroXorStatusCode.position(
          existsNewXorMxpDeploysXorCallPrcFailureXorCallFlagXorValueCurrIsZeroXorStatusCode
                  .position()
              + 1);
    }

    if (!filled.get(55)) {
      existsXorMmuSuccessBitXorCallExceptionXorBtcFlagXorValueCurrIsOrigXorRequiresEvmExecution
          .position(
              existsXorMmuSuccessBitXorCallExceptionXorBtcFlagXorValueCurrIsOrigXorRequiresEvmExecution
                      .position()
                  + 1);
    }

    if (!filled.get(15)) {
      gasActual.position(gasActual.position() + 8);
    }

    if (!filled.get(16)) {
      gasCost.position(gasCost.position() + 32);
    }

    if (!filled.get(17)) {
      gasExpected.position(gasExpected.position() + 8);
    }

    if (!filled.get(125)) {
      gasLimit.position(gasLimit.position() + 32);
    }

    if (!filled.get(18)) {
      gasNext.position(gasNext.position() + 8);
    }

    if (!filled.get(126)) {
      gasPrice.position(gasPrice.position() + 32);
    }

    if (!filled.get(58)) {
      hasCodeNewXorMxpMtntopXorCallPrcSuccessCallerWontRevertXorCopyFlagXorValueNextIsOrig.position(
          hasCodeNewXorMxpMtntopXorCallPrcSuccessCallerWontRevertXorCopyFlagXorValueNextIsOrig
                  .position()
              + 1);
    }

    if (!filled.get(57)) {
      hasCodeXorMxpFlagXorCallPrcSuccessCallerWillRevertXorConFlagXorValueNextIsCurr.position(
          hasCodeXorMxpFlagXorCallPrcSuccessCallerWillRevertXorConFlagXorValueNextIsCurr.position()
              + 1);
    }

    if (!filled.get(19)) {
      hashInfoStamp.position(hashInfoStamp.position() + 8);
    }

    if (!filled.get(20)) {
      height.position(height.position() + 2);
    }

    if (!filled.get(21)) {
      heightNew.position(heightNew.position() + 2);
    }

    if (!filled.get(22)) {
      hubStamp.position(hubStamp.position() + 8);
    }

    if (!filled.get(23)) {
      hubStampTransactionEnd.position(hubStampTransactionEnd.position() + 8);
    }

    if (!filled.get(163)) {
      instruction.position(instruction.position() + 32);
    }

    if (!filled.get(59)) {
      isPrecompileXorMxpMxpxXorCallSmcFailureCallerWillRevertXorCreateFlagXorValueNextIsZero
          .position(
              isPrecompileXorMxpMxpxXorCallSmcFailureCallerWillRevertXorCreateFlagXorValueNextIsZero
                      .position()
                  + 1);
    }

    if (!filled.get(24)) {
      logInfoStamp.position(logInfoStamp.position() + 8);
    }

    if (!filled.get(61)) {
      markedForSelfdestructNewXorStpExistsXorCallSmcSuccessCallerWillRevertXorDecFlag2XorWarmth
          .position(
              markedForSelfdestructNewXorStpExistsXorCallSmcSuccessCallerWillRevertXorDecFlag2XorWarmth
                      .position()
                  + 1);
    }

    if (!filled.get(60)) {
      markedForSelfdestructXorOobFlagXorCallSmcFailureCallerWontRevertXorDecFlag1XorValueOrigIsZero
          .position(
              markedForSelfdestructXorOobFlagXorCallSmcFailureCallerWontRevertXorDecFlag1XorValueOrigIsZero
                      .position()
                  + 1);
    }

    if (!filled.get(25)) {
      mmuStamp.position(mmuStamp.position() + 8);
    }

    if (!filled.get(144)) {
      mxpOffset2Hi.position(mxpOffset2Hi.position() + 32);
    }

    if (!filled.get(145)) {
      mxpOffset2Lo.position(mxpOffset2Lo.position() + 32);
    }

    if (!filled.get(146)) {
      mxpSize1Hi.position(mxpSize1Hi.position() + 32);
    }

    if (!filled.get(147)) {
      mxpSize1Lo.position(mxpSize1Lo.position() + 32);
    }

    if (!filled.get(148)) {
      mxpSize2Hi.position(mxpSize2Hi.position() + 32);
    }

    if (!filled.get(149)) {
      mxpSize2Lo.position(mxpSize2Lo.position() + 32);
    }

    if (!filled.get(26)) {
      mxpStamp.position(mxpStamp.position() + 8);
    }

    if (!filled.get(150)) {
      mxpWords.position(mxpWords.position() + 32);
    }

    if (!filled.get(103)) {
      nbAdded.position(nbAdded.position() + 1);
    }

    if (!filled.get(104)) {
      nbRemoved.position(nbRemoved.position() + 1);
    }

    if (!filled.get(27)) {
      nonStackRows.position(nonStackRows.position() + 2);
    }

    if (!filled.get(127)) {
      nonce.position(nonce.position() + 32);
    }

    if (!filled.get(123)) {
      nonceNewXorStpGasPaidOutOfPocketXorGasInitiallyAvailable.position(
          nonceNewXorStpGasPaidOutOfPocketXorGasInitiallyAvailable.position() + 32);
    }

    if (!filled.get(122)) {
      nonceXorStpGasMxpXorBasefee.position(nonceXorStpGasMxpXorBasefee.position() + 32);
    }

    if (!filled.get(151)) {
      oobData1.position(oobData1.position() + 32);
    }

    if (!filled.get(152)) {
      oobData2.position(oobData2.position() + 32);
    }

    if (!filled.get(153)) {
      oobData3.position(oobData3.position() + 32);
    }

    if (!filled.get(154)) {
      oobData4.position(oobData4.position() + 32);
    }

    if (!filled.get(155)) {
      oobData5.position(oobData5.position() + 32);
    }

    if (!filled.get(156)) {
      oobData6.position(oobData6.position() + 32);
    }

    if (!filled.get(157)) {
      oobData7.position(oobData7.position() + 32);
    }

    if (!filled.get(158)) {
      oobData8.position(oobData8.position() + 32);
    }

    if (!filled.get(28)) {
      peekAtAccount.position(peekAtAccount.position() + 1);
    }

    if (!filled.get(29)) {
      peekAtContext.position(peekAtContext.position() + 1);
    }

    if (!filled.get(30)) {
      peekAtMiscellaneous.position(peekAtMiscellaneous.position() + 1);
    }

    if (!filled.get(31)) {
      peekAtScenario.position(peekAtScenario.position() + 1);
    }

    if (!filled.get(32)) {
      peekAtStack.position(peekAtStack.position() + 1);
    }

    if (!filled.get(33)) {
      peekAtStorage.position(peekAtStorage.position() + 1);
    }

    if (!filled.get(34)) {
      peekAtTransaction.position(peekAtTransaction.position() + 1);
    }

    if (!filled.get(73)) {
      prcBlake2FXorKecFlag.position(prcBlake2FXorKecFlag.position() + 1);
    }

    if (!filled.get(74)) {
      prcEcaddXorLogFlag.position(prcEcaddXorLogFlag.position() + 1);
    }

    if (!filled.get(75)) {
      prcEcmulXorLogInfoFlag.position(prcEcmulXorLogInfoFlag.position() + 1);
    }

    if (!filled.get(76)) {
      prcEcpairingXorMachineStateFlag.position(prcEcpairingXorMachineStateFlag.position() + 1);
    }

    if (!filled.get(77)) {
      prcEcrecoverXorMaxcsx.position(prcEcrecoverXorMaxcsx.position() + 1);
    }

    if (!filled.get(78)) {
      prcFailureKnownToHubXorModFlag.position(prcFailureKnownToHubXorModFlag.position() + 1);
    }

    if (!filled.get(79)) {
      prcFailureKnownToRamXorMulFlag.position(prcFailureKnownToRamXorMulFlag.position() + 1);
    }

    if (!filled.get(80)) {
      prcIdentityXorMxpx.position(prcIdentityXorMxpx.position() + 1);
    }

    if (!filled.get(81)) {
      prcModexpXorMxpFlag.position(prcModexpXorMxpFlag.position() + 1);
    }

    if (!filled.get(82)) {
      prcRipemd160XorOogx.position(prcRipemd160XorOogx.position() + 1);
    }

    if (!filled.get(83)) {
      prcSha2256XorOpcx.position(prcSha2256XorOpcx.position() + 1);
    }

    if (!filled.get(84)) {
      prcSuccessCallerWillRevertXorPushpopFlag.position(
          prcSuccessCallerWillRevertXorPushpopFlag.position() + 1);
    }

    if (!filled.get(85)) {
      prcSuccessCallerWontRevertXorRdcx.position(prcSuccessCallerWontRevertXorRdcx.position() + 1);
    }

    if (!filled.get(128)) {
      priorityFeePerGas.position(priorityFeePerGas.position() + 32);
    }

    if (!filled.get(35)) {
      programCounter.position(programCounter.position() + 8);
    }

    if (!filled.get(36)) {
      programCounterNew.position(programCounterNew.position() + 8);
    }

    if (!filled.get(37)) {
      refundCounter.position(refundCounter.position() + 8);
    }

    if (!filled.get(129)) {
      refundCounterInfinity.position(refundCounterInfinity.position() + 32);
    }

    if (!filled.get(38)) {
      refundCounterNew.position(refundCounterNew.position() + 8);
    }

    if (!filled.get(130)) {
      refundEffective.position(refundEffective.position() + 32);
    }

    if (!filled.get(117)) {
      returnAtCapacityXorMxpInst.position(returnAtCapacityXorMxpInst.position() + 8);
    }

    if (!filled.get(118)) {
      returnAtOffsetXorOobInst.position(returnAtOffsetXorOobInst.position() + 8);
    }

    if (!filled.get(119)) {
      returnDataContextNumberXorStpGasStipend.position(
          returnDataContextNumberXorStpGasStipend.position() + 8);
    }

    if (!filled.get(120)) {
      returnDataOffsetXorStpInstruction.position(returnDataOffsetXorStpInstruction.position() + 8);
    }

    if (!filled.get(121)) {
      returnDataSize.position(returnDataSize.position() + 8);
    }

    if (!filled.get(86)) {
      returnExceptionXorShfFlag.position(returnExceptionXorShfFlag.position() + 1);
    }

    if (!filled.get(87)) {
      returnFromDeploymentEmptyCodeWillRevertXorSox.position(
          returnFromDeploymentEmptyCodeWillRevertXorSox.position() + 1);
    }

    if (!filled.get(88)) {
      returnFromDeploymentEmptyCodeWontRevertXorSstorex.position(
          returnFromDeploymentEmptyCodeWontRevertXorSstorex.position() + 1);
    }

    if (!filled.get(89)) {
      returnFromDeploymentNonemptyCodeWillRevertXorStackramFlag.position(
          returnFromDeploymentNonemptyCodeWillRevertXorStackramFlag.position() + 1);
    }

    if (!filled.get(90)) {
      returnFromDeploymentNonemptyCodeWontRevertXorStackItemPop1.position(
          returnFromDeploymentNonemptyCodeWontRevertXorStackItemPop1.position() + 1);
    }

    if (!filled.get(91)) {
      returnFromMessageCallWillTouchRamXorStackItemPop2.position(
          returnFromMessageCallWillTouchRamXorStackItemPop2.position() + 1);
    }

    if (!filled.get(92)) {
      returnFromMessageCallWontTouchRamXorStackItemPop3.position(
          returnFromMessageCallWontTouchRamXorStackItemPop3.position() + 1);
    }

    if (!filled.get(113)) {
      rlpaddrDepAddrHiXorCallDataContextNumberXorMmuRefSize.position(
          rlpaddrDepAddrHiXorCallDataContextNumberXorMmuRefSize.position() + 8);
    }

    if (!filled.get(138)) {
      rlpaddrDepAddrLoXorMmuSrcOffsetHiXorValueOrigHi.position(
          rlpaddrDepAddrLoXorMmuSrcOffsetHiXorValueOrigHi.position() + 32);
    }

    if (!filled.get(62)) {
      rlpaddrFlagXorStpFlagXorCallSmcSuccessCallerWontRevertXorDecFlag3XorWarmthNew.position(
          rlpaddrFlagXorStpFlagXorCallSmcSuccessCallerWontRevertXorDecFlag3XorWarmthNew.position()
              + 1);
    }

    if (!filled.get(139)) {
      rlpaddrKecHiXorMmuSrcOffsetLoXorValueOrigLo.position(
          rlpaddrKecHiXorMmuSrcOffsetLoXorValueOrigLo.position() + 32);
    }

    if (!filled.get(140)) {
      rlpaddrKecLoXorMmuTgtOffsetLo.position(rlpaddrKecLoXorMmuTgtOffsetLo.position() + 32);
    }

    if (!filled.get(63)) {
      rlpaddrRecipeXorStpOogxXorCreateAbortXorDecFlag4.position(
          rlpaddrRecipeXorStpOogxXorCreateAbortXorDecFlag4.position() + 1);
    }

    if (!filled.get(141)) {
      rlpaddrSaltHiXorMxpGasMxp.position(rlpaddrSaltHiXorMxpGasMxp.position() + 32);
    }

    if (!filled.get(142)) {
      rlpaddrSaltLoXorMxpOffset1Hi.position(rlpaddrSaltLoXorMxpOffset1Hi.position() + 32);
    }

    if (!filled.get(64)) {
      romlexFlagXorStpWarmthXorCreateEmptyInitCodeWillRevertXorDupFlag.position(
          romlexFlagXorStpWarmthXorCreateEmptyInitCodeWillRevertXorDupFlag.position() + 1);
    }

    if (!filled.get(93)) {
      selfdestructExceptionXorStackItemPop4.position(
          selfdestructExceptionXorStackItemPop4.position() + 1);
    }

    if (!filled.get(94)) {
      selfdestructWillRevertXorStaticx.position(selfdestructWillRevertXorStaticx.position() + 1);
    }

    if (!filled.get(95)) {
      selfdestructWontRevertAlreadyMarkedXorStaticFlag.position(
          selfdestructWontRevertAlreadyMarkedXorStaticFlag.position() + 1);
    }

    if (!filled.get(96)) {
      selfdestructWontRevertNotYetMarkedXorStoFlag.position(
          selfdestructWontRevertNotYetMarkedXorStoFlag.position() + 1);
    }

    if (!filled.get(164)) {
      stackItemHeight1.position(stackItemHeight1.position() + 32);
    }

    if (!filled.get(165)) {
      stackItemHeight2.position(stackItemHeight2.position() + 32);
    }

    if (!filled.get(166)) {
      stackItemHeight3.position(stackItemHeight3.position() + 32);
    }

    if (!filled.get(167)) {
      stackItemHeight4.position(stackItemHeight4.position() + 32);
    }

    if (!filled.get(168)) {
      stackItemStamp1.position(stackItemStamp1.position() + 32);
    }

    if (!filled.get(169)) {
      stackItemStamp2.position(stackItemStamp2.position() + 32);
    }

    if (!filled.get(170)) {
      stackItemStamp3.position(stackItemStamp3.position() + 32);
    }

    if (!filled.get(171)) {
      stackItemStamp4.position(stackItemStamp4.position() + 32);
    }

    if (!filled.get(172)) {
      stackItemValueHi1.position(stackItemValueHi1.position() + 32);
    }

    if (!filled.get(173)) {
      stackItemValueHi2.position(stackItemValueHi2.position() + 32);
    }

    if (!filled.get(174)) {
      stackItemValueHi3.position(stackItemValueHi3.position() + 32);
    }

    if (!filled.get(175)) {
      stackItemValueHi4.position(stackItemValueHi4.position() + 32);
    }

    if (!filled.get(176)) {
      stackItemValueLo1.position(stackItemValueLo1.position() + 32);
    }

    if (!filled.get(177)) {
      stackItemValueLo2.position(stackItemValueLo2.position() + 32);
    }

    if (!filled.get(178)) {
      stackItemValueLo3.position(stackItemValueLo3.position() + 32);
    }

    if (!filled.get(179)) {
      stackItemValueLo4.position(stackItemValueLo4.position() + 32);
    }

    if (!filled.get(50)) {
      stoFinal.position(stoFinal.position() + 1);
    }

    if (!filled.get(51)) {
      stoFirst.position(stoFirst.position() + 1);
    }

    if (!filled.get(159)) {
      stpGasHi.position(stpGasHi.position() + 32);
    }

    if (!filled.get(160)) {
      stpGasLo.position(stpGasLo.position() + 32);
    }

    if (!filled.get(124)) {
      stpGasUpfrontGasCostXorGasLeftover.position(
          stpGasUpfrontGasCostXorGasLeftover.position() + 32);
    }

    if (!filled.get(161)) {
      stpValueHi.position(stpValueHi.position() + 32);
    }

    if (!filled.get(162)) {
      stpValueLo.position(stpValueLo.position() + 32);
    }

    if (!filled.get(39)) {
      subStamp.position(subStamp.position() + 8);
    }

    if (!filled.get(97)) {
      sux.position(sux.position() + 1);
    }

    if (!filled.get(98)) {
      swapFlag.position(swapFlag.position() + 1);
    }

    if (!filled.get(65)) {
      trmFlagXorCreateEmptyInitCodeWontRevertXorExtFlag.position(
          trmFlagXorCreateEmptyInitCodeWontRevertXorExtFlag.position() + 1);
    }

    if (!filled.get(143)) {
      trmRawAddressHiXorMxpOffset1Lo.position(trmRawAddressHiXorMxpOffset1Lo.position() + 32);
    }

    if (!filled.get(40)) {
      twoLineInstruction.position(twoLineInstruction.position() + 1);
    }

    if (!filled.get(41)) {
      txExec.position(txExec.position() + 1);
    }

    if (!filled.get(42)) {
      txFinl.position(txFinl.position() + 1);
    }

    if (!filled.get(43)) {
      txInit.position(txInit.position() + 1);
    }

    if (!filled.get(44)) {
      txSkip.position(txSkip.position() + 1);
    }

    if (!filled.get(45)) {
      txWarm.position(txWarm.position() + 1);
    }

    if (!filled.get(99)) {
      txnFlag.position(txnFlag.position() + 1);
    }

    if (!filled.get(67)) {
      warmthNewXorCreateFailureConditionWillRevertXorHashInfoFlag.position(
          warmthNewXorCreateFailureConditionWillRevertXorHashInfoFlag.position() + 1);
    }

    if (!filled.get(66)) {
      warmthXorCreateExceptionXorHaltFlag.position(
          warmthXorCreateExceptionXorHaltFlag.position() + 1);
    }

    if (!filled.get(100)) {
      wcpFlag.position(wcpFlag.position() + 1);
    }

    filled.clear();
    this.currentLine++;

    return this;
  }

  public void build() {
    if (!filled.isEmpty()) {
      throw new IllegalStateException("Cannot build trace with a non-validated row.");
    }
  }
}
