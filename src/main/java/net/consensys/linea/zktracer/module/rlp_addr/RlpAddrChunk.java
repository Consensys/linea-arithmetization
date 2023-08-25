package net.consensys.linea.zktracer.module.rlp_addr;

import java.util.Optional;

import net.consensys.linea.zktracer.opcode.OpCode;
import org.apache.tuweni.bytes.Bytes32;
import org.hyperledger.besu.datatypes.Address;

public record RlpAddrChunk(
    OpCode opCode,
    Optional<Long> nonce,
    Address addr,
    Optional<Bytes32> salt,
    Optional<Bytes32> kec) {
  public RlpAddrChunk(OpCode opCode, long nonce, Address address) {
    this(opCode, Optional.of(nonce), address, Optional.empty(), Optional.empty());
  }

  public RlpAddrChunk(OpCode opCode, Address address, Bytes32 salt, Bytes32 kec) {
    this(opCode, Optional.empty(), address, Optional.of(salt), Optional.of(kec));
  }
}
