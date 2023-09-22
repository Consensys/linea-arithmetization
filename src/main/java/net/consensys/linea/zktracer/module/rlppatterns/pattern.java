package net.consensys.linea.zktracer.module.rlppatterns;

import java.math.BigInteger;

import com.google.common.base.Preconditions;
import org.apache.tuweni.bytes.Bytes;

public class pattern {
  /**
   * Returns the size of RLP(something) where something is of size inputSize (!=1) (it can be ZERO
   * though).
   */
  public static int outerRlpSize(int inputSize) {
    int rlpSize = inputSize;
    if (inputSize == 1) {
      // TODO panic
    } else {
      rlpSize += 1;
      if (inputSize >= 56) {
        rlpSize += Bytes.minimalBytes(inputSize).size();
      }
    }
    return rlpSize;
  }

  /**
   * Add zeroes to the left of the {@link Bytes} to create {@link Bytes} of the given size. The
   * wantedSize must be at least the size of the Bytes.
   *
   * @param input
   * @param wantedSize
   * @return
   */
  public static Bytes padToGivenSizeWithLeftZero(Bytes input, int wantedSize) {
    Preconditions.checkArgument(
        wantedSize >= input.size(), "wantedSize can't be shorter than the input size");
    byte nullByte = 0;

    return Bytes.concatenate(Bytes.repeat(nullByte, wantedSize - input.size()), input);
  }

  public static Bytes bigIntegerToBytes(BigInteger big) {
    byte[] byteArray;
    byteArray = big.toByteArray();
    Bytes bytes;
    if (byteArray[0] == 0) {
      Bytes tmp = Bytes.wrap(byteArray);
      bytes = Bytes.wrap(tmp.slice(1, tmp.size() - 1));
    } else {
      bytes = Bytes.wrap(byteArray);
    }
    return bytes;
  }

  public static Bytes padToGivenSizeWithRightZero(Bytes input, int wantedSize) {
    Preconditions.checkArgument(
        wantedSize >= input.size(), "wantedSize can't be shorter than the input size");
    byte nullByte = 0;

    return Bytes.concatenate(input, Bytes.repeat(nullByte, wantedSize - input.size()));
  }

  /**
   * Create the Power and AccSize list of the ByteCountAndPower RLP pattern.
   *
   * @param inputByteLen represents the number of meaningful bytes of inputByte, i.e. without the
   *     zero left padding
   * @param nbStep
   * @return
   */
  public static RlpByteCountAndPowerOutput byteCounting(int inputByteLen, int nbStep) {
    RlpByteCountAndPowerOutput output = new RlpByteCountAndPowerOutput();

    BigInteger power;
    int accByteSize = 0;
    int offset = 16 - nbStep;

    if (inputByteLen == nbStep) {
      power = BigInteger.valueOf(256).pow(offset);
      accByteSize = 1;
    } else {
      offset += 1;
      power = BigInteger.valueOf(256).pow(offset);
    }

    output.getPowerList().add(0, power);
    output.getAccByteSizeList().add(0, accByteSize);

    for (int i = 1; i < nbStep; i++) {
      if (inputByteLen + i < nbStep) {
        power = power.multiply(BigInteger.valueOf(256));
      } else {
        accByteSize += 1;
      }
      output.getPowerList().add(i, power);
      output.getAccByteSizeList().add(i, accByteSize);
    }
    return output;
  }

  /**
   * Create the Bit and BitDec list of the RLP pattern of an int.
   *
   * @param input
   * @param nbStep
   * @return
   */
  public static RlpBitDecOutput bitDecomposition(int input, int nbStep) {
    Preconditions.checkArgument(nbStep >= 8, "Number of steps must be at least 8");

    RlpBitDecOutput output = new RlpBitDecOutput();
    // Set to zero first value
    for (int i = 0; i < nbStep; i++) {
      output.getBitAccList().add(i, 0);
      output.getBitDecList().add(i, false);
    }

    int bitAcc = 0;
    boolean bitDec = false;
    double div = 0;

    for (int i = 7; i >= 0; i--) {
      div = Math.pow(2, i);
      bitAcc *= 2;

      if (input >= div) {
        bitDec = true;
        bitAcc += 1;
        input -= (int) div;
      } else {
        bitDec = false;
      }

      output.getBitDecList().add(nbStep - i - 1, bitDec);
      output.getBitAccList().add(nbStep - i - 1, bitAcc);
    }
    return output;
  }
}
