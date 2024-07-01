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

package net.consensys.linea.zktracer.module.hub;

import static net.consensys.linea.zktracer.module.hub.HubProcessingPhase.*;
import static net.consensys.linea.zktracer.module.hub.Trace.MULTIPLIER___STACK_HEIGHT;
import static net.consensys.linea.zktracer.types.AddressUtils.addressFromBytes;
import static net.consensys.linea.zktracer.types.AddressUtils.effectiveToAddress;
import static net.consensys.linea.zktracer.types.AddressUtils.getCreateAddress;

import java.nio.MappedByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import net.consensys.linea.zktracer.ColumnHeader;
import net.consensys.linea.zktracer.module.Module;
import net.consensys.linea.zktracer.module.add.Add;
import net.consensys.linea.zktracer.module.bin.Bin;
import net.consensys.linea.zktracer.module.blake2fmodexpdata.Blake2fModexpData;
import net.consensys.linea.zktracer.module.blockdata.Blockdata;
import net.consensys.linea.zktracer.module.blockhash.Blockhash;
import net.consensys.linea.zktracer.module.ecdata.EcData;
import net.consensys.linea.zktracer.module.euc.Euc;
import net.consensys.linea.zktracer.module.exp.Exp;
import net.consensys.linea.zktracer.module.ext.Ext;
import net.consensys.linea.zktracer.module.hub.defer.*;
import net.consensys.linea.zktracer.module.hub.fragment.*;
import net.consensys.linea.zktracer.module.hub.fragment.imc.ImcFragment;
import net.consensys.linea.zktracer.module.hub.fragment.scenario.ScenarioFragment;
import net.consensys.linea.zktracer.module.hub.precompiles.PrecompileInvocation;
import net.consensys.linea.zktracer.module.hub.section.*;
import net.consensys.linea.zktracer.module.hub.section.TxFinalizationPostTxDefer;
import net.consensys.linea.zktracer.module.hub.section.TxPreWarmingMacroSection;
import net.consensys.linea.zktracer.module.hub.section.TxSkippedSectionDefers;
import net.consensys.linea.zktracer.module.hub.section.calls.FailedCallSection;
import net.consensys.linea.zktracer.module.hub.section.calls.NoCodeCallSection;
import net.consensys.linea.zktracer.module.hub.section.calls.SmartContractCallSection;
import net.consensys.linea.zktracer.module.hub.signals.Exceptions;
import net.consensys.linea.zktracer.module.hub.signals.PlatformController;
import net.consensys.linea.zktracer.module.hub.transients.Transients;
import net.consensys.linea.zktracer.module.limits.Keccak;
import net.consensys.linea.zktracer.module.limits.L2Block;
import net.consensys.linea.zktracer.module.limits.L2L1Logs;
import net.consensys.linea.zktracer.module.limits.precompiles.Blake2fRounds;
import net.consensys.linea.zktracer.module.limits.precompiles.EcAddEffectiveCall;
import net.consensys.linea.zktracer.module.limits.precompiles.EcMulEffectiveCall;
import net.consensys.linea.zktracer.module.limits.precompiles.EcPairingCallEffectiveCall;
import net.consensys.linea.zktracer.module.limits.precompiles.EcPairingMillerLoop;
import net.consensys.linea.zktracer.module.limits.precompiles.EcRecoverEffectiveCall;
import net.consensys.linea.zktracer.module.limits.precompiles.ModexpEffectiveCall;
import net.consensys.linea.zktracer.module.limits.precompiles.RipeMd160Blocks;
import net.consensys.linea.zktracer.module.limits.precompiles.Sha256Blocks;
import net.consensys.linea.zktracer.module.logdata.LogData;
import net.consensys.linea.zktracer.module.loginfo.LogInfo;
import net.consensys.linea.zktracer.module.mmio.Mmio;
import net.consensys.linea.zktracer.module.mmu.Mmu;
import net.consensys.linea.zktracer.module.mod.Mod;
import net.consensys.linea.zktracer.module.mul.Mul;
import net.consensys.linea.zktracer.module.mxp.Mxp;
import net.consensys.linea.zktracer.module.oob.Oob;
import net.consensys.linea.zktracer.module.rlpaddr.RlpAddr;
import net.consensys.linea.zktracer.module.rlptxn.RlpTxn;
import net.consensys.linea.zktracer.module.rlptxrcpt.RlpTxrcpt;
import net.consensys.linea.zktracer.module.rom.Rom;
import net.consensys.linea.zktracer.module.romlex.ContractMetadata;
import net.consensys.linea.zktracer.module.romlex.RomLex;
import net.consensys.linea.zktracer.module.shakiradata.ShakiraData;
import net.consensys.linea.zktracer.module.shf.Shf;
import net.consensys.linea.zktracer.module.stp.Stp;
import net.consensys.linea.zktracer.module.tables.bin.BinRt;
import net.consensys.linea.zktracer.module.tables.instructionDecoder.InstructionDecoder;
import net.consensys.linea.zktracer.module.tables.shf.ShfRt;
import net.consensys.linea.zktracer.module.trm.Trm;
import net.consensys.linea.zktracer.module.txndata.TxnData;
import net.consensys.linea.zktracer.module.wcp.Wcp;
import net.consensys.linea.zktracer.opcode.*;
import net.consensys.linea.zktracer.opcode.gas.projector.GasProjector;
import net.consensys.linea.zktracer.runtime.callstack.CallFrame;
import net.consensys.linea.zktracer.runtime.callstack.CallFrameType;
import net.consensys.linea.zktracer.runtime.callstack.CallStack;
import net.consensys.linea.zktracer.runtime.stack.StackContext;
import net.consensys.linea.zktracer.runtime.stack.StackLine;
import net.consensys.linea.zktracer.types.*;
import org.apache.tuweni.bytes.Bytes;
import org.hyperledger.besu.datatypes.Address;
import org.hyperledger.besu.datatypes.Transaction;
import org.hyperledger.besu.datatypes.Wei;
import org.hyperledger.besu.evm.account.Account;
import org.hyperledger.besu.evm.account.AccountState;
import org.hyperledger.besu.evm.frame.MessageFrame;
import org.hyperledger.besu.evm.internal.Words;
import org.hyperledger.besu.evm.log.Log;
import org.hyperledger.besu.evm.log.LogTopic;
import org.hyperledger.besu.evm.operation.Operation;
import org.hyperledger.besu.evm.worldstate.WorldView;
import org.hyperledger.besu.plugin.data.BlockBody;
import org.hyperledger.besu.plugin.data.BlockHeader;
import org.hyperledger.besu.plugin.data.ProcessableBlockHeader;

@Slf4j
@Accessors(fluent = true)
public class Hub implements Module {
  public static final GasProjector GAS_PROJECTOR = new GasProjector();

  /** accumulate the trace information for the Hub */
  @Getter public final State state = new State();

  /** contain the factories for trace segments that need complex initialization */
  @Getter private final Factories factories;

  /** provides phase-related volatile information */
  @Getter Transients transients;

  /**
   * Long-lived states, not used in tracing per se but keeping track of data of the associated
   * lifetime
   */
  @Getter CallStack callStack = new CallStack();

  /** Stores the transaction Metadata of all the transaction of the conflated block */
  @Getter TransactionStack txStack = new TransactionStack();

  /** Stores all the actions that must be deferred to a later time */
  @Getter private final DeferRegistry defers = new DeferRegistry();

  /** stores all data related to failure states & module activation */
  @Getter private final PlatformController pch;

  private boolean previousOperationWasCallToEcPrecompile;

  @Override
  public String moduleKey() {
    return "HUB";
  }

  @Override
  public List<ColumnHeader> columnsHeaders() {
    return Trace.headers(this.lineCount());
  }

  @Override
  public void commit(List<MappedByteBuffer> buffers) {
    final Trace trace = new Trace(buffers);
    this.state.commit(trace);
  }

  @Override
  public int lineCount() {
    return this.state.lineCount();
  }

  /** List of all modules of the ZK-evm */
  // stateless modules
  @Getter private final Wcp wcp = new Wcp();

  private final Module add = new Add();
  private final Module bin = new Bin();
  private final Blockdata blockdata;
  private final Blockhash blockhash = new Blockhash(wcp);
  private final Euc euc;
  private final Ext ext = new Ext(this);
  private final Mod mod = new Mod();
  private final Module mul = new Mul(this);
  private final Module shf = new Shf();
  private final Trm trm = new Trm();

  // other
  private final RlpTxn rlpTxn;
  private final Rom rom;
  @Getter private final RomLex romLex;
  private final RlpTxrcpt rlpTxrcpt;
  private final LogInfo logInfo;
  private final LogData logData;
  private final Mmio mmio;
  private final RlpAddr rlpAddr = new RlpAddr(this, trm);
  private final TxnData txnData;

  // modules triggered by sub-fragments of the MISCELLANEOUS / IMC perspective
  @Getter private final Stp stp = new Stp(wcp, mod);
  @Getter private final Exp exp;
  @Getter private final Mmu mmu;
  @Getter private final Mxp mxp;
  @Getter private final Oob oob;

  // precompile-linked modules
  private final Blake2fModexpData blake2fModexpData = new Blake2fModexpData(this.wcp);
  @Getter private final EcData ecData;
  private final ModexpEffectiveCall modexpEffectiveCall;
  private final ShakiraData shakiraData = new ShakiraData(this.wcp);
  /*
   * Those modules are not traced, we just compute the number of calls to those
   * precompile to meet the prover limits
   */
  private final List<Module> precompileLimitModules;
  private final L2Block l2Block;

  private final List<Module> modules;

  // reference table modules
  private final List<Module> refTableModules;

  /**
   * @return a list of all modules for which to generate traces
   */
  public List<Module> getModulesToTrace() {
    return Stream.concat(
            this.refTableModules.stream(),
            // Modules
            Stream.of(
                this,
                this.add,
                this.bin,
                this.blake2fModexpData,
                this.ecData,
                this.blockdata,
                this.blockhash,
                this.ext,
                this.euc,
                this.exp,
                this.logData,
                this.logInfo,
                this.mmu, // WARN: must be called before the MMIO
                this.mmio,
                this.mod,
                this.mul,
                this.mxp,
                this.oob,
                this.rlpAddr,
                this.rlpTxn,
                this.rlpTxrcpt,
                this.rom,
                this.romLex,
                this.shakiraData,
                this.shf,
                this.stp,
                this.trm,
                this.txnData,
                this.wcp))
        .toList();
  }

  /**
   * List all the modules for which to generate counters. Intersects with, but is not equal to
   * {@code getModulesToTrace}.
   *
   * @return the modules to count
   */
  public List<Module> getModulesToCount() {
    return Stream.concat(
            Stream.of(
                this,
                this.romLex,
                this.add,
                this.bin,
                this.blockdata,
                this.blockhash,
                this.ext,
                this.ecData,
                this.euc,
                this.mmu,
                this.mmio,
                this.logData,
                this.logInfo,
                this.mod,
                this.mul,
                this.mxp,
                this.oob,
                this.exp,
                this.rlpAddr,
                this.rlpTxn,
                this.rlpTxrcpt,
                this.rom,
                this.shf,
                this.trm,
                this.txnData,
                this.wcp,
                this.l2Block),
            this.precompileLimitModules.stream())
        .toList();
  }

  public Hub(final Address l2l1ContractAddress, final Bytes l2l1Topic) {
    this.l2Block = new L2Block(l2l1ContractAddress, LogTopic.of(l2l1Topic));
    this.transients = new Transients(this);
    this.factories = new Factories(this);

    this.pch = new PlatformController(this);
    this.mxp = new Mxp(this);
    this.exp = new Exp(this.wcp, this);
    this.romLex = new RomLex(this);
    this.rom = new Rom(this.romLex);
    this.rlpTxn = new RlpTxn(this.romLex);
    this.euc = new Euc(this.wcp);
    this.txnData = new TxnData(this.wcp, this.euc);
    this.blockdata = new Blockdata(this.wcp, this.txnData, this.rlpTxn);
    this.rlpTxrcpt = new RlpTxrcpt(txnData);
    this.logData = new LogData(rlpTxrcpt);
    this.logInfo = new LogInfo(rlpTxrcpt);
    this.ecData = new EcData(this, this.wcp, this.ext);
    this.oob = new Oob(this, (Add) this.add, this.mod, this.wcp);
    this.mmu =
        new Mmu(
            this.euc,
            this.wcp,
            this.romLex,
            this.rlpTxn,
            this.rlpTxrcpt,
            this.ecData,
            this.blake2fModexpData,
            this.callStack);
    this.mmio = new Mmio(this.mmu);

    final EcRecoverEffectiveCall ecRec = new EcRecoverEffectiveCall(this);
    this.modexpEffectiveCall = new ModexpEffectiveCall(this, this.blake2fModexpData);
    final EcPairingCallEffectiveCall ecPairingCall = new EcPairingCallEffectiveCall(this);
    final L2Block l2Block = new L2Block(l2l1ContractAddress, LogTopic.of(l2l1Topic));

    this.precompileLimitModules =
        List.of(
            new Sha256Blocks(this, shakiraData),
            ecRec,
            new RipeMd160Blocks(this, shakiraData),
            this.modexpEffectiveCall,
            new EcAddEffectiveCall(this),
            new EcMulEffectiveCall(this),
            ecPairingCall,
            new EcPairingMillerLoop(ecPairingCall),
            new Blake2fRounds(this, this.blake2fModexpData),
            // Block level limits
            l2Block,
            new Keccak(this, ecRec, l2Block, shakiraData),
            new L2L1Logs(l2Block));

    this.refTableModules = List.of(new BinRt(), new InstructionDecoder(), new ShfRt());

    this.modules =
        Stream.concat(
                Stream.of(
                    this.add,
                    this.bin,
                    this.blake2fModexpData,
                    this.blockhash,
                    this.ecData,
                    this.euc,
                    this.ext,
                    this.logData,
                    this.logInfo,
                    this.mmio,
                    this.mmu,
                    this.mod,
                    this.mul,
                    this.mxp,
                    this.oob,
                    this.exp,
                    this.rlpAddr,
                    this.rlpTxn,
                    this.rom,
                    this.romLex,
                    this.shakiraData,
                    this.shf,
                    this.stp,
                    this.trm,
                    this.wcp, /* WARN: must be called BEFORE txnData */
                    this.txnData,
                    this.blockdata, /* WARN: must be called AFTER txnData */
                    this.rlpTxrcpt /* WARN: must be called AFTER txnData */),
                this.precompileLimitModules.stream())
            .toList();
  }

  @Override
  public void enterTransaction() {
    for (Module m : this.modules) {
      m.enterTransaction();
    }
  }

  @Override
  public void popTransaction() {
    this.txStack.pop();
    this.state.pop();
    for (Module m : this.modules) {
      m.popTransaction();
    }
  }

  /** Tracing Operation, triggered by Besu hook */
  @Override
  public void traceStartConflation(long blockCount) {
    for (Module m : this.modules) {
      m.traceStartConflation(blockCount);
    }
  }

  @Override
  public void traceEndConflation(final WorldView world) {
    this.romLex.determineCodeFragmentIndex();
    this.txStack.setCodeFragmentIndex(this);
    this.defers.runPostConflation(this, world);

    for (Module m : this.modules) {
      m.traceEndConflation(world);
    }
  }

  @Override
  public void traceStartBlock(final ProcessableBlockHeader processableBlockHeader) {
    this.state.firstAndLastStorageSlotOccurrences.add(new HashMap<>());
    this.transients().block().update(processableBlockHeader);
    this.txStack.resetBlock();
    for (Module m : this.modules) {
      m.traceStartBlock(processableBlockHeader);
    }
  }

  @Override
  public void traceEndBlock(final BlockHeader blockHeader, final BlockBody blockBody) {
    for (Module m : this.modules) {
      m.traceEndBlock(blockHeader, blockBody);
    }
  }

  public void traceStartTransaction(final WorldView world, final Transaction tx) {
    this.pch.reset();
    this.state.enter();
    this.txStack.enterTransaction(world, tx, transients.block());
    this.defers.postTx(this.state.currentTxTrace());

    this.enterTransaction();

    if (!this.txStack.current().requiresEvmExecution()) {
      this.state.setProcessingPhase(TX_SKIP);
      this.state.stamps().incrementHubStamp();
      this.defers.postTx(
          new TxSkippedSectionDefers(world, this.txStack.current(), this.transients));
    } else {
      if (this.txStack.current().requiresPrewarming()) {
        this.state.setProcessingPhase(TX_WARM);
        new TxPreWarmingMacroSection(world, this);
      }

      new TxInitializationSection(this, world);
    }

    /*
     * TODO: the ID = 0 (universal parent context) context should
     * 1. be shared by all transactions in a conflation (OK)
     * 2. should be the father of all root contexts
     * 3. should have the current root context as its lastCallee()
     */
    this.callStack.getById(0).universalParentReturnDataContextNumber(this.stamp() + 1);

    for (Module m : this.modules) {
      m.traceStartTx(world, this.txStack().current());
    }
  }

  public void traceEndTransaction(
      WorldView world,
      Transaction tx,
      boolean isSuccessful,
      List<Log> logs,
      Set<Address> selfDestructs) {
    this.txStack
        .current()
        .completeLineaTransaction(
            world,
            isSuccessful,
            this.state.stamps().hub(),
            this.state().getProcessingPhase(),
            logs,
            selfDestructs);

    if (!(this.state.getProcessingPhase() == TX_SKIP)) {
      this.state.stamps().incrementHubStamp();
    }

    this.defers.runPostTx(this, world, tx, isSuccessful);

    for (Module m : this.modules) {
      m.traceEndTx(txStack.current());
    }
  }

  @Override
  public void traceContextEnter(MessageFrame frame) {
    this.pch.reset();

    if (frame.getDepth() == 0) {
      // Bedrock...
      final TransactionProcessingMetadata currentTx = transients().tx();
      final Address toAddress = effectiveToAddress(currentTx.getBesuTransaction());
      final boolean isDeployment = this.transients.tx().getBesuTransaction().getTo().isEmpty();

      final boolean shouldCopyTxCallData =
          !isDeployment && !frame.getInputData().isEmpty() && currentTx.requiresEvmExecution();
      // TODO simplify this, the same bedRock context ( = root context ??) seems to be
      // generated in
      // both case
      if (shouldCopyTxCallData) {
        this.callStack.newMantleAndBedrock(
            this.state.stamps().hub(),
            this.transients.tx().getBesuTransaction().getSender(),
            toAddress,
            CallFrameType.MANTLE,
            new Bytecode(
                toAddress == null
                    ? this.transients.tx().getBesuTransaction().getData().orElse(Bytes.EMPTY)
                    : Optional.ofNullable(frame.getWorldUpdater().get(toAddress))
                        .map(AccountState::getCode)
                        .orElse(Bytes.EMPTY)),
            Wei.of(this.transients.tx().getBesuTransaction().getValue().getAsBigInteger()),
            this.transients.tx().getBesuTransaction().getGasLimit(),
            this.transients.tx().getBesuTransaction().getData().orElse(Bytes.EMPTY),
            this.transients.conflation().deploymentInfo().number(toAddress),
            toAddress.isEmpty()
                ? 0
                : this.transients.conflation().deploymentInfo().number(toAddress),
            this.transients.conflation().deploymentInfo().isDeploying(toAddress));
      } else {
        this.callStack.newBedrock(
            this.state.stamps().hub(),
            this.txStack.current().getBesuTransaction().getSender(),
            toAddress,
            CallFrameType.BEDROCK,
            new Bytecode(
                toAddress == null
                    ? this.transients.tx().getBesuTransaction().getData().orElse(Bytes.EMPTY)
                    : Optional.ofNullable(frame.getWorldUpdater().get(toAddress))
                        .map(AccountState::getCode)
                        .orElse(Bytes.EMPTY)),
            Wei.of(this.transients.tx().getBesuTransaction().getValue().getAsBigInteger()),
            this.transients.tx().getBesuTransaction().getGasLimit(),
            this.transients.tx().getBesuTransaction().getData().orElse(Bytes.EMPTY),
            this.transients.conflation().deploymentInfo().number(toAddress),
            toAddress.isEmpty()
                ? 0
                : this.transients.conflation().deploymentInfo().number(toAddress),
            this.transients.conflation().deploymentInfo().isDeploying(toAddress));
      }
    } else {
      // ...or CALL
      final boolean isDeployment = frame.getType() == MessageFrame.Type.CONTRACT_CREATION;
      final Address codeAddress = frame.getContractAddress();
      final CallFrameType frameType =
          frame.isStatic() ? CallFrameType.STATIC : CallFrameType.STANDARD;
      if (isDeployment) {
        this.transients.conflation().deploymentInfo().markDeploying(codeAddress);
      }
      final int codeDeploymentNumber =
          this.transients.conflation().deploymentInfo().number(codeAddress);

      final int callDataOffsetStackArgument =
          callStack.current().opCode().callMayNotTransferValue() ? 2 : 3;

      final long callDataOffset =
          isDeployment
              ? 0
              : Words.clampedToLong(
                  callStack.current().frame().getStackItem(callDataOffsetStackArgument));

      final long callDataSize =
          isDeployment
              ? 0
              : Words.clampedToLong(
                  callStack.current().frame().getStackItem(callDataOffsetStackArgument + 1));

      final long callDataContextNumber = this.callStack.current().contextNumber();

      this.callStack.enter(
          this.state.stamps().hub(),
          frame.getRecipientAddress(),
          frame.getContractAddress(),
          frame.getRecipientAddress(), // TODO: this is likely false
          new Bytecode(frame.getCode().getBytes()),
          frameType,
          frame.getValue(),
          frame.getRemainingGas(),
          frame.getInputData(),
          callDataOffset,
          callDataSize,
          callDataContextNumber,
          this.transients.conflation().deploymentInfo().number(codeAddress),
          codeDeploymentNumber,
          isDeployment);

      this.defers.runNextContext(this, frame);

      for (Module m : this.modules) {
        m.traceContextEnter(frame);
      }
    }
  }

  public void traceContextReEnter(MessageFrame frame) {
    this.defers.runReEntry(this, frame);
    if (this.currentFrame().needsUnlatchingAtReEntry() != null) {
      this.unlatchStack(frame, this.currentFrame().needsUnlatchingAtReEntry());
      this.currentFrame().needsUnlatchingAtReEntry(null);
    }
  }

  @Override
  public void traceContextExit(MessageFrame frame) {
    if (frame.getDepth() > 0) {
      this.transients
          .conflation()
          .deploymentInfo()
          .unmarkDeploying(this.currentFrame().byteCodeAddress());

      DeploymentExceptions contextExceptions =
          DeploymentExceptions.fromFrame(this.currentFrame(), frame);
      this.currentTraceSection().setContextExceptions(contextExceptions);
      if (contextExceptions.any()) {
        this.callStack.revert(this.state.stamps().hub());
      }

      this.callStack.exit();

      for (Module m : this.modules) {
        m.traceContextExit(frame);
      }
    }

    // We take a snapshot before exiting the transaction
    if (frame.getDepth() == 0) {
      final long leftOverGas = frame.getRemainingGas();
      final long gasRefund = frame.getGasRefund();
      final boolean minerIsWarm = frame.isAddressWarm(txStack.current().getCoinbase());

      txStack
          .current()
          .setPreFinalisationValues(
              leftOverGas,
              gasRefund,
              minerIsWarm,
              this.txStack.getAccumulativeGasUsedInBlockBeforeTxStart());
      if (this.state.getProcessingPhase() != TX_SKIP) {
        this.state.setProcessingPhase(TX_FINAL);
        this.defers.postTx(new TxFinalizationPostTxDefer(this, frame.getWorldUpdater()));
      }
    }
  }

  public void tracePreExecution(final MessageFrame frame) {
    Preconditions.checkArgument(
        this.state().processingPhase != TX_SKIP,
        "There can't be any execution if the HUB is in the a skip phase");

    this.processStateExec(frame);
  }

  public void tracePostExecution(MessageFrame frame, Operation.OperationResult operationResult) {
    Preconditions.checkArgument(
        this.state().processingPhase != TX_SKIP,
        "There can't be any execution if the HUB is in the a skip phase");

    final long gasCost = operationResult.getGasCost();
    final TraceSection currentSection = this.state.currentTxTrace().currentSection();

    final Exceptions exceptions = this.pch().exceptions();

    final boolean memoryExpansionException = exceptions.memoryExpansion();
    final boolean outOfGasException = exceptions.outOfGas();
    final boolean unexceptional = exceptions.none();
    final boolean exceptional = exceptions.any();

    /* TODO: Might be dangerous : in some cases, we add fragments at the end of the transaction, we need an other mechanism ... */
    // adds the final context row to reset the caller's return data
    if (exceptional) {
      this.currentTraceSection()
          .addFragmentsWithoutStack(ContextFragment.executionProvidesEmptyReturnData(this));
    }

    // Setting gas cost IN MOST CASES
    // TODO:
    //  * complete this for CREATE's and CALL's
    //    + are we getting the correct cost (i.e. excluding the 63/64-th's) ?
    //  * make sure this aligns with exception handling of the zkevm
    //  * write a method `final boolean requiresGasCost()` (huge switch case)
    if ((!memoryExpansionException & outOfGasException) || unexceptional) {
      currentSection.commonValues.gasCost(gasCost);
      currentSection.commonValues.gasNext(
          unexceptional ? currentSection.commonValues.gasActual - gasCost : 0);
    } else {
      currentSection.commonValues.gasCost(
          0xdeadbeefL); // TODO: fill with correct values --- likely 0
      currentSection.commonValues.gasNext(0);
    }

    if (this.currentFrame().opCode().isCreate() && operationResult.getHaltReason() == null) {
      this.handleCreate(Words.toAddress(frame.getStackItem(0)));
    }

    this.defers.runPostExec(this, frame, operationResult);

    if (this.previousOperationWasCallToEcPrecompile) {
      this.ecData.getEcDataOperation().returnData(frame.getReturnData());
    }

    if (this.currentFrame().needsUnlatchingAtReEntry() == null) {
      this.unlatchStack(frame);
    }

    switch (this.opCodeData().instructionFamily()) {
      case ADD -> {}
      case MOD -> {}
      case MUL -> {}
      case EXT -> {}
      case WCP -> {}
      case BIN -> {}
      case SHF -> {}
      case KEC -> {}
      case CONTEXT -> {}
      case ACCOUNT -> {}
      case COPY -> {}
      case TRANSACTION -> {}
      case BATCH -> {
        this.blockhash.tracePostOpcode(frame);
      }
      case STACK_RAM -> {}
      case STORAGE -> {}
      case JUMP -> {}
      case MACHINE_STATE -> {}
      case PUSH_POP -> {}
      case DUP -> {}
      case SWAP -> {}
      case LOG -> {}
      case CREATE -> {
        this.romLex.tracePostOpcode(frame);
      }
      case CALL -> {}
      case HALT -> {}
      case INVALID -> {}
      default -> {}
    }
  }

  private void handleCreate(Address target) {
    this.transients.conflation().deploymentInfo().deploy(target);
  }

  public int getCfiByMetaData(
      final Address address, final int deploymentNumber, final boolean deploymentStatus) {
    return this.romLex()
        .getCodeFragmentIndexByMetadata(
            ContractMetadata.make(address, deploymentNumber, deploymentStatus));
  }

  public int newChildContextNumber() {
    return 1 + this.stamp();
  }

  public CallFrame currentFrame() {
    if (this.callStack().isEmpty()) {
      return CallFrame.EMPTY;
    }
    return this.callStack.current();
  }

  public int contextNumberNew(CallFrame frame) {
    switch (this.state.getProcessingPhase()) {
      case TX_SKIP, TX_WARM, TX_FINAL -> {
        return 0;
      }
      case TX_INIT -> {
        return newChildContextNumber();
      }
      case TX_EXEC -> {
        final OpCode opCode = this.opCode();

        if (pch.exceptions().any()
            || opCode.getData().instructionFamily().equals(InstructionFamily.HALT)
            || opCode.getData().instructionFamily().equals(InstructionFamily.INVALID)) {
          return this.callStack.depth() == 0 ? 0 : this.callStack().caller().contextNumber();
        }

        final int currentContextNumber = this.callStack().current().contextNumber();

        if (opCode.isCall()) {
          if (pch().abortingConditions().any()) {
            return currentContextNumber;
          }

          final Address calleeAddress =
              Words.toAddress(this.currentFrame().frame().getStackItem(1));

          AtomicInteger newContext = new AtomicInteger(currentContextNumber);

          Optional.ofNullable(frame.frame().getWorldUpdater().get(calleeAddress))
              .map(AccountState::getCode)
              .ifPresent(
                  byteCode -> {
                    if (!byteCode.isEmpty()) {
                      newContext.set(newChildContextNumber());
                    }
                  });
          return newContext.get();
        }

        if (opCode.isCreate()) {
          if (pch().abortingConditions().any() || pch().failureConditions().any()) {
            return currentContextNumber;
          }
          final int initCodeSize = this.currentFrame().frame().getStackItem(2).toInt();
          if (initCodeSize != 0) {
            return newChildContextNumber();
          }
        }

        return currentContextNumber;
      }
      default -> {
        {
          throw new IllegalStateException(
              String.format("Hub can't be in the state %s", this.state.getProcessingPhase()));
        }
      }
    }
  }

  public MessageFrame messageFrame() {
    MessageFrame frame = this.callStack.current().frame();
    return frame;
  }

  private void handleStack(MessageFrame frame) {
    this.currentFrame()
        .stack()
        .processInstruction(this, frame, MULTIPLIER___STACK_HEIGHT * this.state.stamps().hub());
  }

  void triggerModules(MessageFrame frame) {
    if (this.pch.exceptions().none() && this.pch.abortingConditions().none()) {
      for (Module precompileLimit : this.precompileLimitModules) {
        precompileLimit.tracePreOpcode(frame);
      }
    }

    if (this.pch.signals().romLex()) {
      this.romLex.tracePreOpcode(frame);
    }
    if (this.pch.signals().add()) {
      this.add.tracePreOpcode(frame);
    }
    if (this.pch.signals().bin()) {
      this.bin.tracePreOpcode(frame);
    }
    if (this.pch.signals().rlpAddr()) {
      this.rlpAddr.tracePreOpcode(frame);
    }
    if (this.pch.signals().mul()) {
      this.mul.tracePreOpcode(frame);
    }
    if (this.pch.signals().ext()) {
      this.ext.tracePreOpcode(frame);
    }
    if (this.pch.signals().mod()) {
      this.mod.tracePreOpcode(frame);
    }
    if (this.pch.signals().wcp()) {
      this.wcp.tracePreOpcode(frame);
    }
    if (this.pch.signals().shf()) {
      this.shf.tracePreOpcode(frame);
    }
    if (this.pch.signals().mxp()) {
      this.mxp.tracePreOpcode(frame);
    }
    if (this.pch.signals().oob()) {
      this.oob.tracePreOpcode(frame);
    }
    if (this.pch.signals().stp()) {
      this.stp.tracePreOpcode(frame);
    }
    if (this.pch.signals().exp()) {
      this.exp.tracePreOpcode(frame);
    }
    if (this.pch.signals().trm()) {
      this.trm.tracePreOpcode(frame);
    }
    if (this.pch.signals().hashInfo()) {
      // TODO: this.hashInfo.tracePreOpcode(frame);
    }
    if (this.pch.signals().ecData()) {
      this.ecData.tracePreOpcode(frame);
    }
    if (this.pch.signals().blockhash()) {
      this.blockhash.tracePreOpcode(frame);
    }
  }

  public int stamp() {
    return this.state.stamps().hub();
  }

  public OpCodeData opCodeData() {
    return this.currentFrame().opCodeData();
  }

  public OpCode opCode() {
    return this.currentFrame().opCode();
  }

  TraceSection currentTraceSection() {
    return this.state.currentTxTrace().currentSection();
  }

  public void addFragmentsAndStack(TraceFragment... fragments) {
    currentTraceSection().addFragmentsAndStack(this, fragments);
  }

  public void addTraceSection(TraceSection section) {
    this.state.currentTxTrace().add(section);
  }

  private void unlatchStack(MessageFrame frame) {
    this.unlatchStack(frame, this.currentTraceSection());
  }

  public void unlatchStack(MessageFrame frame, TraceSection section) {
    if (this.currentFrame().pending() == null) {
      return;
    }

    final StackContext pending = this.currentFrame().pending();
    for (int i = 0; i < pending.lines().size(); i++) {
      StackLine line = pending.lines().get(i);

      if (line.needsResult()) {
        Bytes result = Bytes.EMPTY;
        // Only pop from the stack if no exceptions have been encountered
        if (this.pch.exceptions().none()) {
          result = frame.getStackItem(0).copy();
        }

        // This works because we are certain that the stack chunks are the first.
        ((StackFragment) section.fragments().get(i))
            .stackOps()
            .get(line.resultColumn() - 1)
            .value(result);
      }
    }

    // TODO: this seems useless now
    // if (this.pch.exceptions().none()) {
    //  for (TraceSection.TraceLine line : section.fragments()) {
    //    if (line.specific() instanceof StackFragment stackFragment) {
    //      stackFragment.feedHashedValue(frame);
    //    }
    //  }
    // }
  }

  void processStateExec(MessageFrame frame) {

    if (previousOperationWasCallToEcPrecompile) {
      this.ecData.getEcDataOperation().returnData(frame.getReturnData());
      previousOperationWasCallToEcPrecompile = false;
    }

    this.currentFrame().frame(frame);
    this.state.stamps().incrementHubStamp();

    this.pch.setup(frame);

    this.handleStack(frame);
    this.triggerModules(frame);

    if (this.pch().exceptions().any() || this.currentFrame().opCode() == OpCode.REVERT) {
      this.callStack.revert(this.state.stamps().hub());
    }

    if (this.currentFrame().stack().isOk()) {
      // TODO: this is insufficient because it neglects:
      //  - exceptions other than stack exceptions e.g.
      //    * STATICX
      //    * MXPX
      //    * OOGX
      //  - it COULD (unlikely ?) produce issues with ABORTS
      //  - it COULD interfere with CALL's to precompiles that don't reach EC_DATA e.g.
      //    * ECRECOVER, ECADD, ECMUL, ECPAIRING with zero call data size parameter
      if (this.pch.signals().ecData()) {
        this.previousOperationWasCallToEcPrecompile = true;
      }
      this.traceOperation(frame);
    } else {

      this.addTraceSection(new StackOnlySection(this));
    }
  }

  // TODO: how do these implementations of remainingGas()
  // and expectedGas() behave with respect to resuming
  // execution after a CALL / CREATE ? One of them is
  // necessarily false ...
  public long remainingGas() {
    return this.state().getProcessingPhase() == TX_EXEC
        ? this.currentFrame().frame().getRemainingGas()
        : 0;
  }

  public long expectedGas() {
    return this.state().getProcessingPhase() == TX_EXEC
        ? this.currentFrame().frame().getRemainingGas()
        : 0;
  }

  public int cumulatedTxCount() {
    return this.state.txCount();
  }

  void traceOperation(MessageFrame frame) {
    switch (this.opCodeData().instructionFamily()) {
      case ADD, MOD, SHF, BIN, WCP, EXT, BATCH, MACHINE_STATE, PUSH_POP, DUP, SWAP, INVALID -> this
          .addTraceSection(new StackOnlySection(this));
      case MUL -> {
        if (this.opCode() == OpCode.EXP) {
          this.addTraceSection(
              new ExpSection(this, ImcFragment.forOpcode(this, this.messageFrame())));
        } else {
          this.addTraceSection(new StackOnlySection(this));
        }
      }
      case HALT -> {
        final CallFrame parentFrame = this.callStack.parent();
        // this.addTraceSection(new StackOnlySection(this));

        switch (this.opCode()) {
          case RETURN -> {
            Bytes returnData = Bytes.EMPTY;
            // Trying to read memory with absurd arguments will throw an exception
            if (pch.exceptions().none()) {
              returnData = this.transients.op().returnData();
            }
            this.currentFrame().returnDataSource(transients.op().returnDataSegment());
            this.currentFrame().returnData(returnData);
            if (!this.pch.exceptions().any() && !this.currentFrame().isDeployment()) {
              parentFrame.latestReturnData(returnData);
            } else {
              parentFrame.latestReturnData(Bytes.EMPTY);
            }
            final ImcFragment imcFragment = ImcFragment.forOpcode(this, frame); // TODO finish it
          }
          case REVERT -> {
            final Bytes returnData = this.transients.op().returnData();
            this.currentFrame().returnDataSource(transients.op().returnDataSegment());
            this.currentFrame().returnData(returnData);
            if (this.pch.exceptions().none()) {
              parentFrame.latestReturnData(returnData);
            } else {
              parentFrame.latestReturnData(Bytes.EMPTY);
            }
            final ImcFragment imcFragment = ImcFragment.forOpcode(this, frame); // TODO finish it
          }
          case STOP -> {
            parentFrame.latestReturnData(Bytes.EMPTY);
            StopSection.appendTo(this);
          }
          case SELFDESTRUCT -> {
            parentFrame.latestReturnData(Bytes.EMPTY);
          }
        }
      }

      case KEC -> {
        KeccakSection.appendToTrace(this);
      }

      case CONTEXT -> this.addTraceSection(
          new ContextLogSection(this, ContextFragment.readCurrentContextData(this)));

      case LOG -> {
        new LogSection(this);
      }
      case ACCOUNT -> {
        TraceSection accountSection = new AccountSection(this);
        if (this.opCode().isAnyOf(OpCode.SELFBALANCE, OpCode.CODESIZE)) {
          accountSection.addFragment(ContextFragment.readCurrentContextData(this));
        }

        final Bytes rawTargetAddress =
            switch (this.currentFrame().opCode()) {
              case BALANCE, EXTCODESIZE, EXTCODEHASH -> frame.getStackItem(0);
              default -> this.currentFrame().accountAddress();
            };
        final Address targetAddress = Words.toAddress(rawTargetAddress);
        final Account targetAccount = frame.getWorldUpdater().get(targetAddress);
        final AccountSnapshot accountBefore =
            AccountSnapshot.fromAccount(
                targetAccount,
                frame.isAddressWarm(targetAddress),
                this.transients.conflation().deploymentInfo().number(targetAddress),
                this.transients.conflation().deploymentInfo().isDeploying(targetAddress));
        final AccountSnapshot accountAfter =
            AccountSnapshot.fromAccount(
                targetAccount,
                true,
                this.transients.conflation().deploymentInfo().number(targetAddress),
                this.transients.conflation().deploymentInfo().isDeploying(targetAddress));
        final DomSubStampsSubFragment doingDomSubStamps =
            DomSubStampsSubFragment.standardDomSubStamps(this, 0);
        accountSection.addFragment(
            this.factories
                .accountFragment()
                .makeWithTrm(accountBefore, accountAfter, rawTargetAddress, doingDomSubStamps));

        if (this.currentFrame().willRevert()) {
          final DomSubStampsSubFragment undoingDomSubStamps =
              DomSubStampsSubFragment.revertWithCurrentDomSubStamps(this, 0);
          accountSection.addFragment(
              this.factories
                  .accountFragment()
                  .make(accountBefore, accountAfter, undoingDomSubStamps));
        }

        this.addTraceSection(accountSection);
      }
      case COPY -> {
        TraceSection copySection = new CopySection(this);
        if (!this.opCode().equals(OpCode.RETURNDATACOPY)) {
          final Bytes rawTargetAddress =
              switch (this.currentFrame().opCode()) {
                case CODECOPY -> this.currentFrame().byteCodeAddress();
                case EXTCODECOPY -> frame.getStackItem(0);
                case CALLDATACOPY -> addressFromBytes(
                    Bytes.fromHexString("0xdeadbeef")); // TODO: implement me please
                default -> throw new IllegalStateException(
                    String.format("unexpected opcode %s", this.opCode()));
              };
          final Address targetAddress = Words.toAddress(rawTargetAddress);
          final Account targetAccount = frame.getWorldUpdater().get(targetAddress);

          AccountSnapshot accountBefore =
              AccountSnapshot.fromAccount(
                  targetAccount,
                  frame.isAddressWarm(targetAddress),
                  this.transients.conflation().deploymentInfo().number(targetAddress),
                  this.transients.conflation().deploymentInfo().isDeploying(targetAddress));

          AccountSnapshot accountAfter =
              AccountSnapshot.fromAccount(
                  targetAccount,
                  true,
                  this.transients.conflation().deploymentInfo().number(targetAddress),
                  this.transients.conflation().deploymentInfo().isDeploying(targetAddress));

          DomSubStampsSubFragment doingDomSubStamps =
              DomSubStampsSubFragment.standardDomSubStamps(this, 0);
          copySection.addFragment(
              this.currentFrame().opCode() == OpCode.EXTCODECOPY
                  ? this.factories
                      .accountFragment()
                      .makeWithTrm(
                          accountBefore, accountBefore, rawTargetAddress, doingDomSubStamps)
                  : this.factories
                      .accountFragment()
                      .make(accountBefore, accountAfter, doingDomSubStamps));

          if (this.callStack.current().willRevert()) {
            DomSubStampsSubFragment undoingDomSubStamps =
                DomSubStampsSubFragment.revertWithCurrentDomSubStamps(this, 0);
            copySection.addFragment(
                this.factories
                    .accountFragment()
                    .make(accountAfter, accountBefore, undoingDomSubStamps));
          }
        } else {
          copySection.addFragment(ContextFragment.readCurrentContextData(this));
        }
        this.addTraceSection(copySection);
      }
      case TRANSACTION -> this.addTraceSection(
          new TransactionSection(this, TransactionFragment.prepare(this.txStack.current())));
      case STACK_RAM -> {
        switch (this.currentFrame().opCode()) {
          case CALLDATALOAD -> {
            final ImcFragment imcFragment = ImcFragment.forOpcode(this, frame);

            this.addTraceSection(
                new StackRam(this, imcFragment, ContextFragment.readCurrentContextData(this)));
          }
          case MLOAD, MSTORE, MSTORE8 -> this.addTraceSection(
              new StackRam(this, ImcFragment.forOpcode(this, frame)));
          default -> throw new IllegalStateException("unexpected STACK_RAM opcode");
        }
      }
      case STORAGE -> {
        switch (this.currentFrame().opCode()) {
          case SSTORE -> {
            SstoreSection.appendSectionTo(this, frame.getWorldUpdater());
          }
          case SLOAD -> {
            SloadSection.appendSectionTo(this, frame.getWorldUpdater());
          }
          default -> throw new IllegalStateException("invalid operation in family STORAGE");
        }
      }
      case CREATE -> {
        final Address myAddress = this.currentFrame().accountAddress();
        final Account myAccount = frame.getWorldUpdater().get(myAddress);
        AccountSnapshot myAccountSnapshot =
            AccountSnapshot.fromAccount(
                myAccount,
                frame.isAddressWarm(myAddress),
                this.transients.conflation().deploymentInfo().number(myAddress),
                this.transients.conflation().deploymentInfo().isDeploying(myAddress));

        final Address createdAddress = getCreateAddress(frame);
        final Account createdAccount = frame.getWorldUpdater().get(createdAddress);
        AccountSnapshot createdAccountSnapshot =
            AccountSnapshot.fromAccount(
                createdAccount,
                frame.isAddressWarm(createdAddress),
                this.transients.conflation().deploymentInfo().number(createdAddress),
                this.transients.conflation().deploymentInfo().isDeploying(createdAddress));

        CreateSection createSection =
            new CreateSection(this, myAccountSnapshot, createdAccountSnapshot);
        this.addTraceSection(createSection);
        this.currentFrame().needsUnlatchingAtReEntry(createSection);
      }

      case CALL -> {
        final Address myAddress = this.currentFrame().accountAddress();
        final Account myAccount = frame.getWorldUpdater().get(myAddress);
        final AccountSnapshot myAccountSnapshot =
            AccountSnapshot.fromAccount(
                myAccount,
                frame.isAddressWarm(myAddress),
                this.transients.conflation().deploymentInfo().number(myAddress),
                this.transients.conflation().deploymentInfo().isDeploying(myAddress));

        final Bytes rawCalledAddress = frame.getStackItem(1);
        final Address calledAddress = Words.toAddress(rawCalledAddress);
        final Optional<Account> calledAccount =
            Optional.ofNullable(frame.getWorldUpdater().get(calledAddress));
        final boolean hasCode = calledAccount.map(AccountState::hasCode).orElse(false);

        final AccountSnapshot calledAccountSnapshot =
            AccountSnapshot.fromAccount(
                calledAccount,
                frame.isAddressWarm(myAddress),
                this.transients.conflation().deploymentInfo().number(myAddress),
                this.transients.conflation().deploymentInfo().isDeploying(myAddress));

        Optional<Precompile> targetPrecompile = Precompile.maybeOf(calledAddress);

        if (this.pch().exceptions().any()) {
          //
          // THERE IS AN EXCEPTION
          //
          if (this.pch().exceptions().staticException()) {
            this.addTraceSection(
                new FailedCallSection(
                    this,
                    ScenarioFragment.forCall(this, hasCode),
                    ImcFragment.forCall(this, myAccount, calledAccount),
                    ContextFragment.readCurrentContextData(this)));
          } else if (this.pch().exceptions().memoryExpansion()) {
            this.addTraceSection(
                new FailedCallSection(
                    this,
                    ScenarioFragment.forCall(this, hasCode),
                    ImcFragment.forCall(this, myAccount, calledAccount)));
          } else if (this.pch().exceptions().outOfGas()) {
            this.addTraceSection(
                new FailedCallSection(
                    this,
                    ScenarioFragment.forCall(this, hasCode),
                    ImcFragment.forCall(this, myAccount, calledAccount),
                    this.factories
                        .accountFragment()
                        .makeWithTrm(
                            calledAccountSnapshot,
                            calledAccountSnapshot,
                            rawCalledAddress,
                            DomSubStampsSubFragment.standardDomSubStamps(this, 0))));
          }
        } else if (this.pch.abortingConditions().any()) {
          //
          // THERE IS AN ABORT
          //
          TraceSection abortedSection =
              new FailedCallSection(
                  this,
                  ScenarioFragment.forCall(this, hasCode),
                  ImcFragment.forCall(this, myAccount, calledAccount),
                  ContextFragment.readCurrentContextData(this),
                  this.factories
                      .accountFragment()
                      .make(
                          myAccountSnapshot,
                          myAccountSnapshot,
                          DomSubStampsSubFragment.standardDomSubStamps(this, 0)),
                  this.factories
                      .accountFragment()
                      .makeWithTrm(
                          calledAccountSnapshot,
                          calledAccountSnapshot,
                          rawCalledAddress,
                          DomSubStampsSubFragment.standardDomSubStamps(this, 1)),
                  ContextFragment.nonExecutionEmptyReturnData(this));
          this.addTraceSection(abortedSection);
        } else {
          final ImcFragment imcFragment = ImcFragment.forOpcode(this, frame);

          if (hasCode) {
            final SmartContractCallSection section =
                new SmartContractCallSection(
                    this, myAccountSnapshot, calledAccountSnapshot, rawCalledAddress, imcFragment);
            this.addTraceSection(section);
            this.currentFrame().needsUnlatchingAtReEntry(section);
          } else {
            //
            // CALL EXECUTED
            //

            // TODO: fill the callee & requested return data for the current call frame
            // TODO: i.e. ensure that the precompile frame behaves as expected

            Optional<PrecompileInvocation> precompileInvocation =
                targetPrecompile.map(p -> PrecompileInvocation.of(this, p));

            // TODO: this is ugly, and surely not at the right place. It should provide the
            // precompile result (from the precompile module)
            // TODO useless (and potentially dangerous) if the precompile is a failure
            if (targetPrecompile.isPresent()) {
              this.callStack.newPrecompileResult(
                  this.stamp(), Bytes.EMPTY, 0, targetPrecompile.get().address);
            }

            final NoCodeCallSection section =
                new NoCodeCallSection(
                    this,
                    precompileInvocation,
                    myAccountSnapshot,
                    calledAccountSnapshot,
                    rawCalledAddress,
                    imcFragment);
            this.addTraceSection(section);
            this.currentFrame().needsUnlatchingAtReEntry(section);
          }
        }
      }

      case JUMP -> {
        AccountSnapshot codeAccountSnapshot =
            AccountSnapshot.fromAccount(
                frame.getWorldUpdater().get(this.currentFrame().byteCodeAddress()),
                true,
                this.transients
                    .conflation()
                    .deploymentInfo()
                    .number(this.currentFrame().byteCodeAddress()),
                this.currentFrame().isDeployment());

        JumpSection jumpSection =
            new JumpSection(
                this,
                ContextFragment.readCurrentContextData(this),
                this.factories
                    .accountFragment()
                    .make(
                        codeAccountSnapshot,
                        codeAccountSnapshot,
                        DomSubStampsSubFragment.standardDomSubStamps(this, 0)),
                ImcFragment.forOpcode(this, frame));

        this.addTraceSection(jumpSection);
      }
    }

    // In all cases, add a context fragment if an exception occurred
    if (this.pch().exceptions().any()) {
      this.currentTraceSection()
          .addFragment(ContextFragment.executionProvidesEmptyReturnData(this));
    }
  }

  public TraceSection getCurrentSection() {
    return this.state.currentTxTrace().currentSection();
  }
}
