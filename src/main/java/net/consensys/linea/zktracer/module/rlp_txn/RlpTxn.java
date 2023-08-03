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

package net.consensys.linea.zktracer.module.rlp_txn;

import java.math.BigInteger;
import java.util.List;

import net.consensys.linea.zktracer.OpCode;
import net.consensys.linea.zktracer.bytes.UnsignedByte;
import net.consensys.linea.zktracer.module.Module;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.bytes.Bytes32;
import org.hyperledger.besu.datatypes.Address;
import org.hyperledger.besu.datatypes.Hash;
import org.hyperledger.besu.datatypes.Transaction;
import org.hyperledger.besu.datatypes.TransactionType;
import org.hyperledger.besu.plugin.data.BlockBody;
import org.hyperledger.besu.plugin.data.BlockHeader;

/** Implementation of a {@link Module} for addition/subtraction. */
public class RlpTxn implements Module {

  int llarge = Trace.LLARGE.intValue();
  int llargemo = Trace.LLARGEMO.intValue();
  int prefix_short_int = Trace.int_short.intValue();
  int prefix_long_int = Trace.int_long.intValue();
  int prefix_short_list = Trace.list_short.intValue();
  int prefix_long_list = Trace.list_long.intValue();
  final Trace.TraceBuilder trace = Trace.builder();

  private Transaction transaction;

  @Override
  public String jsonKey() {
    return "txRlp";
  }

  @Override
  public final List<OpCode> supportedOpCodes() {
    return List.of();
  }

  @Override
  public void traceStartBlock(BlockHeader blockHeader, BlockBody blockBody) {

    RlpTxnToTrace data = new RlpTxnToTrace();
    if (transaction.getType() == TransactionType.FRONTIER) {
      data.TYPE = 0;
    } else {
      data.TYPE = transaction.getType().getSerializedType();
    }

    /** Phase 0 : Golbal RLP prefix */
    data.DATA_LO = BigInteger.valueOf(data.TYPE);
    /** TODO */

    /** Phase 1 : ChainId */
    if (data.TYPE == 1 || data.TYPE == 2) {
      /** TODO check that chainID is an 8 bytes int */
      data = HandlePhaseInteger(data, 1, transaction.getChainId().get(), 8, true, true);
    }

    /** Phase 2 : Nonce */
    BigInteger nonce = BigInteger.valueOf(transaction.getNonce());
    data.DATA_LO = nonce;
    data = HandlePhaseInteger(data, 2, nonce, 8, true, true);

    /** Phase 3 : GasPrice */
    if (data.TYPE == 0 || data.TYPE == 1) {
      BigInteger gasPrice = transaction.getGasPrice().get().getAsBigInteger();
      /** TODO check that gasPrice is an 8 bytes int */
      data.DATA_LO = gasPrice;
      data = HandlePhaseInteger(data, 3, gasPrice, 8, true, true);
    }

    /** Phase 4 : max priority fee per gas (GasTipCap) */
    if (data.TYPE == 2) {
      BigInteger maxPriorityFeePerGas =
          transaction.getMaxPriorityFeePerGas().get().getAsBigInteger();
      data.DATA_HI = maxPriorityFeePerGas;
      /** TODO check that max priority fee per gas is an 8 bytes int */
      data = HandlePhaseInteger(data, 4, maxPriorityFeePerGas, 8, true, true);
    }

    /** Phase 5 : max fee per gas (GasFeeCap) */
    if (data.TYPE == 2) {
      BigInteger maxFeePerGas = transaction.getMaxFeePerGas().get().getAsBigInteger();
      /** TODO check that max fee per gas is an 8 bytes int */
      data.DATA_LO = maxFeePerGas;
      data = HandlePhaseInteger(data, 6, maxFeePerGas, 8, true, true);
    }

    /** Phase 6 : GasLimit */
    BigInteger gasLimit = BigInteger.valueOf(transaction.getGasLimit());
    data.DATA_LO = gasLimit;
    data = HandlePhaseInteger(data, 6, gasLimit, 8, true, true);

    /** Phase 7 : To */
    if (transaction.getTo().isPresent()) {
      data.DATA_HI = transaction.getTo().get().slice(0, 4).toBigInteger();
      data.DATA_LO = transaction.getTo().get().slice(4, 16).toBigInteger();
    } else {
      data.DATA_HI = BigInteger.ZERO;
      data.DATA_LO = BigInteger.ZERO;
    }
    data = HandlePhaseTo(data);

    /** Phase 8 : Value */
    BigInteger value = transaction.getValue().getAsBigInteger();
    /** TODO add check that value is a 12 bytes int */
    data.DATA_LO = value;
    if (transaction.getTo().isEmpty()) {
      data.DATA_HI = BigInteger.ONE;
    } else {
      data.DATA_HI = BigInteger.ZERO;
    }
    data = HandlePhaseInteger(data, 8, value, 12, true, true);

    /** Phase 9 : Data */
    data = HandlePhaseData(data);

    /** Phase 10 : AccessList */

    /** Phase 11 : Beta / w */

    /** Phase 12 : y */

    /** Phase 13 : r */

    /** Phase 14 : s */
  }

  /** Define each phase's constraints */
  private RlpTxnToTrace HandlePhaseGlobalRlpPrefix(RlpTxnToTrace data) {
    int phase = 0;
    /** First, trace the Type prefix of the transaction */
    data.PartialReset(phase, 1, true, true);
    if (data.TYPE != 0) {
      data.LIMB_CONSTRUCTED = true;
      data.LIMB = Bytes.ofUnsignedShort(data.TYPE);
      data.nBYTES = 1;
      data = TraceRow(data);
    } else {
      data.is_padding = true;
      data = TraceRow(data);
    }

    /** RLP prefix of RLP(LT) */
    int nbstep = 8;
    boolean isbytesize = true;
    boolean islist = true;
    boolean lt = true;
    boolean lx = false;
    boolean endphase = false;
    boolean isprefix = false;
    boolean depth1 = false;
    boolean depth2 = false;
    data =
        HandleInt(
            data,
            phase,
            BigInteger.valueOf(data.RLP_LT_BYTESIZE),
            nbstep,
            isbytesize,
            islist,
            lt,
            lx,
            endphase,
            isprefix,
            depth1,
            depth2);

    /** RLP prefix of RLP(LT) */
    lt = false;
    lx = true;
    endphase = true;
    data =
        HandleInt(
            data,
            phase,
            BigInteger.valueOf(data.RLP_LX_BYTESIZE),
            nbstep,
            isbytesize,
            islist,
            lt,
            lx,
            endphase,
            isprefix,
            depth1,
            depth2);

    return data;
  }

  private RlpTxnToTrace HandlePhaseInteger(
      RlpTxnToTrace data, int phase, BigInteger input, int nbstep, boolean lt, boolean lx) {
    boolean isbytesize = false;
    boolean islist = false;
    boolean isprefix = false;
    boolean depth1 = false;
    boolean depth2 = false;
    boolean endphase = true;
    data =
        HandleInt(
            data,
            phase,
            input,
            nbstep,
            isbytesize,
            islist,
            lt,
            lx,
            endphase,
            isprefix,
            depth1,
            depth2);
    return data;
  }

  private RlpTxnToTrace HandlePhaseTo(RlpTxnToTrace data) {
    int phase = 7;
    boolean lt = true;
    boolean lx = true;

    if (transaction.getTo().isEmpty()) {
      data.PartialReset(phase, 1, lt, lx);
      data.LIMB_CONSTRUCTED = true;
      data.LIMB = Bytes.ofUnsignedShort(prefix_short_int);
      data.nBYTES = 1;
      data.end_phase = true;
      data = TraceRow(data);
    } else {
      data = HandleAddress(data, phase, transaction.getTo().get());
    }

    return data;
  }

  private RlpTxnToTrace HandlePhaseData(RlpTxnToTrace traceData) {
    int phase = 9;
    boolean lt = true;
    boolean lx = true;

    if (transaction.getData().isEmpty()) {
      /** Trivial case */
      traceData.PartialReset(phase, 1, lt, lx);
      traceData.LIMB_CONSTRUCTED = true;
      traceData.LIMB = Bytes.ofUnsignedShort(prefix_short_int);
      traceData.nBYTES = 1;
      traceData.is_prefix = true;
      traceData.is_bytesize = true;
      traceData = TraceRow(traceData);

      /** One row of padding */
      traceData.PartialReset(phase, 1, lt, lx);
      traceData.is_padding = true;
      traceData.end_phase = true;
      traceData = TraceRow(traceData);
    } else {
      /** General case */

      /** Initialise DataSize and DataGasCost */
      Bytes data = transaction.getData().get();
      traceData.PartialReset(phase, 8, lt, lx);
      traceData.PHASE_BYTESIZE = data.size();
      for (int i = 0; i < traceData.PHASE_BYTESIZE; i++) {
        if (data.get(i) == 0) {
          traceData.DATAGASCOST += Trace.G_txdatazero.intValue();
        } else {
          traceData.DATAGASCOST += Trace.G_txdatanonzero.intValue();
        }
      }
      traceData.DATA_HI = BigInteger.valueOf(traceData.DATAGASCOST);
      traceData.DATA_LO = BigInteger.valueOf(traceData.PHASE_BYTESIZE);

      /** Trace */

      /** RLP prefix */
      if (traceData.PHASE_BYTESIZE == 1) {
        /** Special case with 1 byte of data */
        traceData.PartialReset(phase, 8, lt, lx);
        traceData.is_prefix = true;
        traceData.INPUT_1 = data;
        RlpTxnBitDecOutput bitDecomposition =
            BitDecomposition(traceData.INPUT_1.get(0), traceData.number_step);
        if (traceData.INPUT_1.get(0) < 128) {
          traceData.is_padding = true;
        } else {
          traceData.is_padding = false;
        }
        for (int ct = 0; ct < traceData.number_step; ct++) {
          traceData.COUNTER = ct;
          traceData.BIT = bitDecomposition.BitDecList.get(ct);
          traceData.BIT_ACC = bitDecomposition.BitAccList.get(ct);
          if (ct == traceData.number_step - 1) {
            traceData.BYTE_1 = traceData.INPUT_1.get(0);
            traceData.ACC_1 = traceData.INPUT_1;
            if (traceData.INPUT_1.get(0) >= 128) {
              traceData.LIMB_CONSTRUCTED = true;
              traceData.LIMB = Bytes.ofUnsignedShort(prefix_short_int + 1);
              traceData.nBYTES = 1;
            }
          }
          traceData = TraceRow(traceData);
        }
      } else {
        /** General case */
        boolean isbytesize = true;
        boolean islist = false;
        boolean endphase = false;
        boolean isprefix = true;
        boolean depth1 = false;
        boolean depth2 = false;
        traceData =
            HandleInt(
                traceData,
                phase,
                BigInteger.valueOf(traceData.PHASE_BYTESIZE),
                8,
                isbytesize,
                islist,
                lt,
                lx,
                endphase,
                isprefix,
                depth1,
                depth2);
      }
      /** 16-rows ct-loop to write all the data */
      int nbstep = 16;
      int nbloop = (traceData.PHASE_BYTESIZE - 1) / 16 + 1;
      data.shiftLeft(8 * (16 * nbloop - traceData.PHASE_BYTESIZE));
      for (int i = 0; i < nbloop; i++) {
        traceData.PartialReset(phase, nbstep, lt, lx);
        traceData.INPUT_1 = data.slice(llarge * i, llarge);
        int accByteSize = 0;
        for (int ct = 0; ct < llarge; ct++) {
          traceData.COUNTER = ct;
          if (traceData.PHASE_BYTESIZE != 0) {
            accByteSize += 1;
          }
          traceData.BYTE_1 = traceData.INPUT_1.get(ct);
          traceData.ACC_1 = traceData.INPUT_1.slice(0, ct);
          traceData.ACC_BYTESIZE = accByteSize;
          if (ct == nbstep - 1) {
            traceData.LIMB_CONSTRUCTED = true;
            traceData.LIMB = traceData.INPUT_1;
            traceData.nBYTES = accByteSize;
          }
          traceData = TraceRow(traceData);
        }
      }
      /** Two rows of padding */
      traceData.PartialReset(phase, 2, lt, lx);
      traceData.is_padding = true;
      traceData = TraceRow(traceData);
      traceData.COUNTER = 1;
      traceData.end_phase = true;
      traceData = TraceRow(traceData);
    }

    /** TODO add check that phasebytesize and datagascost is zero */
    traceData.INDEX_DATA = 0;
    return traceData;
  }

  /** Define the constraint patterns functions */
  private RlpTxnToTrace HandleInt(
      RlpTxnToTrace data,
      int phase,
      BigInteger input,
      int nb_step,
      boolean is_bytesize,
      boolean is_list,
      boolean lt,
      boolean lx,
      boolean end_phase,
      boolean is_prefix,
      boolean depth1,
      boolean depth2) {
    Bytes inputByte = Bytes.ofUnsignedShort(input.byteValue());
    int inputByteLen = inputByte.size();

    data.PartialReset(phase, nb_step, lt, lx);
    data.INPUT_1 = inputByte;
    data.is_bytesize = is_bytesize;
    data.is_list = is_list;
    data.is_prefix = is_prefix;
    data.DEPTH_1 = depth1;
    data.DEPTH_2 = depth2;

    /** Trivial case */
    if (input.equals(BigInteger.ZERO)) {
      data.number_step = 1;
      data.LIMB_CONSTRUCTED = true;
      data.nBYTES = 1;
      data.end_phase = end_phase;
      if (is_list) {
        data.LIMB = Bytes.ofUnsignedShort(prefix_short_list).shiftLeft(8 * llargemo);
      } else {
        data.LIMB = Bytes.ofUnsignedShort(prefix_short_int).shiftLeft(8 * llargemo);
      }
      data = TraceRow(data);
    } else {
      /** General case */

      /** Compute the COMP bit column */
      data.COMP = (input.compareTo(BigInteger.valueOf(56)) >= 0);

      /** Compute acc2LastRowValue, the value of the last row of ACC2 */
      BigInteger acc2LastRowValue = null;
      if (data.COMP) {
        acc2LastRowValue = input.subtract(BigInteger.valueOf(56));
      } else {
        acc2LastRowValue = BigInteger.valueOf(55).subtract(input);
      }
      Byte[] acc2LastRowBytes = ArrayUtils.toObject(acc2LastRowValue.toByteArray());
      /** TODO : give the good size and format for acc2LastRowBytes (we want Bytes) */

      /** Compute the bit decomposition of the last input's byte */
      Byte lastByte = inputByte.get(inputByteLen - 1);
      RlpTxnBitDecOutput bitDecomposition = BitDecomposition(lastByte, nb_step);

      /** Compute the bytesize and Power columns */
      RlpTxnByteCountingOutput byteCounting = ByteCounting(inputByteLen, nb_step);

      /** Now, Trace */
      data.number_step = nb_step;
      Bytes inputBytePadded = inputByte.shiftLeft(8 * (nb_step - inputByteLen));

      for (int ct = 0; ct < nb_step; ct++) {
        data.COUNTER = ct;
        data.BYTE_1 = inputBytePadded.get(ct);
        data.ACC_1 = inputBytePadded.slice(0, ct);
        data.ACC_BYTESIZE = byteCounting.AccByteSizeList.get(ct);
        data.POWER = byteCounting.PowerList.get(ct);
        data.BIT = bitDecomposition.BitDecList.get(ct);
        data.BIT_ACC = bitDecomposition.BitAccList.get(ct);
        data.BYTE_2 = acc2LastRowBytes.get(ct);
        data.ACC_2 = acc2LastRowBytes.slice(0, ct);

        if (ct == nb_step - 2) {
          if (!is_bytesize) {
            if (input.compareTo(BigInteger.valueOf(128)) >= 0) {
              data.LIMB_CONSTRUCTED = true;
              data.LIMB =
                  Bytes.ofUnsignedShort(
                      prefix_short_int + byteCounting.AccByteSizeList.get(nb_step - 1));
              data.nBYTES = 1;
            } else {
              if (!data.COMP) {
                data.LIMB_CONSTRUCTED = false;
                data.LIMB = Bytes.EMPTY;
                data.nBYTES = 0;
              } else {
                if (!data.is_list) {
                  data.LIMB_CONSTRUCTED = true;
                  data.LIMB =
                      Bytes.ofUnsignedShort(
                          prefix_long_int + byteCounting.AccByteSizeList.get(nb_step - 1));
                  data.nBYTES = 1;
                } else {
                  data.LIMB_CONSTRUCTED = true;
                  data.LIMB =
                      Bytes.ofUnsignedShort(
                          prefix_long_list + byteCounting.AccByteSizeList.get(nb_step - 1));
                  data.nBYTES = 1;
                }
              }
            }
          }
        }

        if (ct == nb_step - 1) {
          data.LIMB_CONSTRUCTED = true;
          data.end_phase = end_phase;
          if (!data.is_bytesize) {
            data.LIMB = inputByte;
            data.nBYTES = inputByteLen;
          } else {
            if (data.COMP) {
              data.LIMB = inputByte;
              data.nBYTES = inputByteLen;
            } else {
              if (!data.is_list) {
                data.LIMB = input.add(BigInteger.valueOf(prefix_short_int)).toByteArray();
                data.nBYTES = 1;
              } else {
                data.LIMB = input.add(BigInteger.valueOf(prefix_short_list)).toByteArray();
                data.nBYTES = 1;
              }
            }
          }
        }
        data = TraceRow(data);
      }
    }
    return data;
  }

  private RlpTxnToTrace Handle32BytesInteger(RlpTxnToTrace data, int phase, BigInteger input) {
    data.PartialReset(phase, llarge, true, false);
    if (input.equals(BigInteger.ZERO)) {
      /** Trivial case */
      data.number_step = 1;
      data.LIMB_CONSTRUCTED = true;
      data.LIMB = Bytes.ofUnsignedShort(prefix_short_int);
      data.nBYTES = 1;
      data.end_phase = true;
      data = TraceRow(data);
    } else {
      /** General case */
      Bytes32 inputByte = Bytes32.fromHexString(input);
      /** TODO make it a 32 byte */
      int inputLen = inputByte.size();
      /** TODO have the bytesize of the input before left padding */
      data.INPUT_1 = inputByte.slice(0, llarge);
      data.INPUT_2 = inputByte.slice(llarge, llarge);

      if (inputLen <= data.number_step) {
        RlpTxnByteCountingOutput byteCounting = ByteCounting(inputLen, data.number_step);
        RlpTxnBitDecOutput bitDec =
            BitDecomposition(inputByte.get(inputByte.size() - 1), data.number_step);

        for (int ct = 0; ct < data.number_step; ct++) {
          data.COUNTER = ct;
          data.BYTE_2 = data.INPUT_2.get(ct);
          data.ACC_2 = data.INPUT_2.slice(0, ct);
          data.ACC_BYTESIZE = byteCounting.AccByteSizeList.get(ct);
          data.POWER = byteCounting.PowerList.get(ct);
          data.BIT = bitDec.BitDecList.get(ct);
          data.BIT_ACC = bitDec.BitAccList.get(ct);

          /** if input >= 128, there is a RLP prefix, nothing if 0 < input < 128 */
          if (ct == data.number_step - 2 && input.compareTo(BigInteger.valueOf(128)) >= 0) {
            data.LIMB_CONSTRUCTED = true;
            data.LIMB = Bytes.ofUnsignedShort(prefix_short_int + inputLen);
            data.nBYTES = 1;
          }
          if (ct == data.number_step - 1) {
            data.LIMB_CONSTRUCTED = true;
            data.LIMB = data.INPUT_2.slice(llarge - inputLen, inputLen);
            data.nBYTES = 1;
            data.end_phase = true;
          }
          data = TraceRow(data);
        }
      } else {
        inputLen -= data.number_step;
        RlpTxnByteCountingOutput byteCounting = ByteCounting(inputLen, data.number_step);

        for (int ct = 0; ct < data.number_step; ct++) {
          data.COUNTER = ct;
          data.BYTE_1 = data.INPUT_1.get(ct);
          data.ACC_1 = data.INPUT_1.slice(0, ct);
          data.BYTE_2 = data.INPUT_2.get(ct);
          data.ACC_2 = data.INPUT_2.slice(0, ct);
          data.ACC_BYTESIZE = byteCounting.AccByteSizeList.get(ct);
          data.POWER = byteCounting.PowerList.get(ct);

          if (ct == data.number_step - 3) {
            data.LIMB_CONSTRUCTED = true;
            data.LIMB = Bytes.ofUnsignedShort(prefix_short_int + llarge + inputLen);
            data.nBYTES = 1;
          }
          if (ct == data.number_step - 2) {
            data.LIMB = data.INPUT_1.slice(llarge - inputLen, inputLen);
            data.nBYTES = inputLen;
          }
          if (ct == data.number_step - 1) {
            data.LIMB = data.INPUT_2;
            data.nBYTES = llarge;
            data.end_phase = true;
          }
          data = TraceRow(data);
        }
      }
    }
    return data;
  }

  private RlpTxnToTrace HandleAddress(RlpTxnToTrace data, int phase, Address address) {
    boolean lt = true;
    boolean lx = true;
    data.PartialReset(phase, llarge, lt, lx);
    data.INPUT_1 = address.slice(0, 4).shiftRight(8 * 11);
    data.INPUT_2 = address.slice(4, llarge);

    if (phase == 10) {
      data.DEPTH_1 = true;
    }

    for (int ct = 0; ct < data.number_step; ct++) {
      data.COUNTER = ct;
      data.BYTE_1 = data.INPUT_1.get(ct);
      data.ACC_1 = data.INPUT_1.slice(0, ct);
      data.BYTE_2 = data.INPUT_2.get(ct);
      data.ACC_2 = data.INPUT_2.slice(0, ct);

      if (ct == data.number_step - 3) {
        data.LIMB_CONSTRUCTED = true;
        data.LIMB = Bytes.ofUnsignedInt(prefix_short_int + 20).shiftLeft(8 * llargemo);
        data.nBYTES = 1;
      }

      if (ct == data.number_step - 2) {
        data.LIMB = data.INPUT_1;
        data.nBYTES = 4;
      }
      if (ct == data.number_step - 1) {
        data.LIMB = data.INPUT_2;
        data.nBYTES = llarge;

        if (phase == 7) {
          data.end_phase = true;
        }
      }
      data = TraceRow(data);
    }
    return data;
  }

  private RlpTxnToTrace HandleStorageKey(RlpTxnToTrace data, boolean end_phase, Hash storage_key) {
    data.PartialReset(10, llarge, true, true);
    data.DEPTH_1 = true;
    data.DEPTH_2 = true;
    data.INPUT_1 = storage_key.slice(0, llarge);
    data.INPUT_2 = storage_key.slice(llarge, llarge);

    for (int ct = 0; ct < data.number_step; ct++) {
      data.COUNTER = ct;
      data.BYTE_1 = data.INPUT_1.get(ct);
      data.ACC_1 = data.INPUT_1.slice(0, ct);
      data.BYTE_2 = data.INPUT_2.get(ct);
      data.ACC_2 = data.INPUT_2.slice(0, ct);

      if (ct == data.number_step - 3) {
        data.LIMB_CONSTRUCTED = true;
        data.LIMB = Bytes.ofUnsignedShort(prefix_short_int + 32).shiftLeft(8 * llargemo);
        data.nBYTES = 1;
      }

      if (ct == data.number_step - 2) {
        data.LIMB = data.INPUT_1;
        data.nBYTES = llarge;
      }

      if (ct == data.number_step - 1) {
        data.LIMB = data.INPUT_2;
        data.end_phase = end_phase;
      }

      data = TraceRow(data);
    }

    return data;
  }

  /** Define side functions */
  private RlpTxnBitDecOutput BitDecomposition(Byte inputByte, int nb_step) {
    /** TODO panic if nb_step<8 */
    RlpTxnBitDecOutput output = new RlpTxnBitDecOutput();

    int input = inputByte.intValue();

    int bitAcc = 0;
    int bitDec = 0;
    double div = 0;

    for (int i = 7; i >= 0; i--) {
      div = Math.pow(2, i);

      if (input >= div) {
        bitDec = 1;
        input -= div;
      } else {
        bitDec = 0;
      }

      bitAcc = 2 * bitAcc + bitDec;
      output.BitDecList.set(nb_step - i - 1, bitDec);
      output.BitAccList.set(nb_step - i - 1, bitAcc);
    }
    return output;
  }

  private RlpTxnByteCountingOutput ByteCounting(int inputByteLen, int nb_step) {
    /**
     * inputByteLen represents the number of meaningfull byte of inputByte, ie without the zero left
     * padding
     */
    RlpTxnByteCountingOutput output = new RlpTxnByteCountingOutput();

    BigInteger power = BigInteger.ZERO;
    int accByteSize = 0;
    int baseOffset = 16 - nb_step;

    if (inputByteLen == nb_step) {
      power = BigInteger.valueOf((long) Math.pow(256, baseOffset));
      accByteSize = 1;
    } else {
      baseOffset += 1;
      power = BigInteger.valueOf((long) Math.pow(256, baseOffset));
      accByteSize = 0;
    }
    output.PowerList.set(0, power);
    output.AccByteSizeList.set(0, accByteSize);

    for (int i = 1; i < nb_step; i++) {
      if (inputByteLen + i < nb_step) {
        power.multiply(BigInteger.valueOf(256));
      } else {
        accByteSize += 1;
      }
      output.PowerList.set(i, power);
      output.AccByteSizeList.set(i, accByteSize);
    }
    return output;
  }

  private int OuterRlpSize(int inputSize) {
    /** Returns the size of RLP(something) where something is of size inputSize (>1). */
    int rlpSize = inputSize;
    if (inputSize == 1) {
      /** TODO panic */
    } else {
      rlpSize += 1;
      if (inputSize >= 56) {
        rlpSize += Bytes.ofUnsignedShort(inputSize).size();
      }
    }
    return rlpSize;
  }

  private int InnerRlpSize(int rlpSize) {
    /** If rlpSize >1, return size(something) where rlpSize = size(RLP(something)). */
    if (rlpSize == 0 || rlpSize == 1) {
      /** TODO panic */
    }
    int output = rlpSize;
    if (rlpSize < 57) {
      output -= 1;
    } else if (rlpSize == 57) {
      /** TODO panic */
    } else if (rlpSize < 258) {
      output -= 2;
    } else {
      for (int i = 0; i < 8; i++) {
        if ((rlpSize - 2 - i >= Math.pow(2, i)) && (rlpSize - i - 1 <= Math.pow(2, i + 1))) {
          output -= (2 + i);
        } else if (rlpSize == Math.pow(2, i) + 1 + i) {
          /** TODO panic */
        }
      }
    }
    return output;
  }

  /** Define the Tracer */
  private RlpTxnToTrace TraceRow(RlpTxnToTrace data) {

    /** Decrements RLP_BYTESIZE */
    if (data.phase != 0) {
      if (data.LIMB_CONSTRUCTED && data.LT) {
        data.RLP_LT_BYTESIZE -= data.nBYTES;
      }
      if (data.LIMB_CONSTRUCTED && data.LX) {
        data.RLP_LX_BYTESIZE -= data.nBYTES;
      }
    }

    /** Decrement phaseByteSize and accessTupleByteSize for Phase 10 (AccessList) */
    if (data.phase == 10) {
      /** Decreases PhaseByteSize */
      if (data.DEPTH_1 && data.LIMB_CONSTRUCTED) {
        data.PHASE_BYTESIZE -= data.nBYTES;
      }
      /** Decreases AccessTupleSize */
      if (data.DEPTH_1 && !(data.is_prefix && !data.DEPTH_2) && data.LIMB_CONSTRUCTED) {
        data.ACCESS_TUPLE_BYTESIZE -= data.nBYTES;
      }
    }

    this.trace
        .absTxNum(BigInteger.valueOf(data.ABS_TX_NUM))
        .absTxNumInfiny(BigInteger.valueOf(data.ABS_TX_NUM_INFINY))
        .acc1(data.ACC_1)
        .acc2(data.ACC_2)
        .accBytesize(BigInteger.valueOf(data.ACC_BYTESIZE))
        .accessTupleBytesize(BigInteger.valueOf(data.ACCESS_TUPLE_BYTESIZE))
        .addrHi(data.ADDR_HI)
        .addrLo(data.ADDR_LO)
        .bit(data.BIT)
        .bitAcc(UnsignedByte.of(data.BIT_ACC))
        .byte1(UnsignedByte.of(data.BYTE_1))
        .byte2(UnsignedByte.of(data.BYTE_2))
        .codeFragmentIndex(BigInteger.valueOf(data.CODE_FRAGMENT_INDEX))
        .comp(data.COMP)
        .counter(BigInteger.valueOf(data.COUNTER))
        .dataHi(BigInteger.valueOf(data.DATA_HI))
        .dataLo(BigInteger.valueOf(data.DATA_LO))
        .datagascost(BigInteger.valueOf(data.DATAGASCOST))
        .depth1(data.DEPTH_1)
        .depth2(data.DEPTH_2);
    if (data.COUNTER == data.number_step - 1) {
      this.trace.done(Boolean.TRUE);
    } else {
      this.trace.done(Boolean.FALSE);
    }
    this.trace
        .endPhase(data.end_phase)
        .indexData(BigInteger.valueOf(data.INDEX_DATA))
        .indexLt(BigInteger.valueOf(data.INDEX_LT))
        .indexLx(BigInteger.valueOf(data.INDEX_LX))
        .input1(data.INPUT_1)
        .input2(data.INPUT_2)
        .isBytesize(data.is_bytesize)
        .isList(data.is_list)
        .isPadding(data.is_padding)
        .isPrefix(data.is_prefix)
        .limb(data.LIMB)
        /** TODO make the left padding here */
        .limbConstructed(data.LIMB_CONSTRUCTED)
        .lt(data.LT)
        .lx(data.LX)
        .nBytes(UnsignedByte.of(data.nBYTES))
        .nbAddr(BigInteger.valueOf(data.nb_Addr))
        .nbSto(BigInteger.valueOf(data.nb_Sto))
        .nbStoPerAddr(BigInteger.valueOf(data.nb_Sto_per_Addr))
        .numberStep(UnsignedByte.of(data.number_step));

    /** list phase */
    this.trace
        .phaseBytesize(BigInteger.valueOf(data.PHASE_BYTESIZE))
        .power(data.POWER)
        .requiresEvmExecution(data.REQUIRES_EVM_EXECUTION)
        .rlpLtBytesize(BigInteger.valueOf(data.RLP_LT_BYTESIZE))
        .rlpLxBytesize(BigInteger.valueOf(data.RLP_LX_BYTESIZE))
        .type(UnsignedByte.of(data.TYPE))
        .validateRow();

    /** Increments Index */
    if (data.LIMB_CONSTRUCTED && data.LT) {
      data.INDEX_LT += 1;
    }
    if (data.LIMB_CONSTRUCTED && data.LX) {
      data.INDEX_LX += 1;
    }

    /** Increments IndexData (Phase 9) */
    if (data.phase == 9 && !data.is_prefix && (data.LIMB_CONSTRUCTED || data.is_padding)) {
      data.INDEX_DATA += 1;
    }

    /** Decrements PhaseByteSize and DataGasCost in Data phase (phase 9) */
    if (data.phase == 9) {
      if (data.PHASE_BYTESIZE != 0 && !data.is_prefix) {
        data.PHASE_BYTESIZE -= 1;
        if (data.BYTE_1 == 0) {
          data.DATAGASCOST -= Trace.G_txdatanonzero.intValue();
        } else {
          data.DATAGASCOST -= Trace.G_txdatazero.intValue();
        }
      }
    }
    if (data.end_phase) {
      data.DataHiLoReset();
    }
    return data;
  }

  @Override
  public Object commit() {
    return new RlpTxnTrace(trace.build());
  }
}
