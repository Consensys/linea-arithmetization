package net.consensys.linea;

import static org.assertj.core.api.Assertions.assertThat;

import net.consensys.linea.corset.CorsetValidator;
import net.consensys.linea.zktracer.ZkTracer;
import org.hyperledger.besu.datatypes.BlobGas;
import org.hyperledger.besu.datatypes.Hash;
import org.hyperledger.besu.datatypes.Wei;
import org.hyperledger.besu.ethereum.core.BlockHeader;
import org.hyperledger.besu.ethereum.core.BlockHeaderBuilder;
import org.hyperledger.besu.ethereum.core.MutableWorldState;
import org.hyperledger.besu.ethereum.core.Transaction;
import org.hyperledger.besu.ethereum.mainnet.MainnetTransactionProcessor;
import org.hyperledger.besu.ethereum.mainnet.ProtocolSpec;
import org.hyperledger.besu.ethereum.mainnet.TransactionValidationParams;
import org.hyperledger.besu.ethereum.processing.TransactionProcessingResult;
import org.hyperledger.besu.ethereum.referencetests.ReferenceTestWorldState;
import org.hyperledger.besu.ethereum.rlp.RLP;
import org.hyperledger.besu.ethereum.vm.CachingBlockHashLookup;
import org.hyperledger.besu.evm.account.Account;
import org.hyperledger.besu.evm.log.Log;
import org.hyperledger.besu.evm.worldstate.WorldUpdater;
import org.hyperledger.besu.testutil.JsonTestParameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GeneralStateReferenceTestTools {
  private static final ReferenceTestProtocolSchedules
    REFERENCE_TEST_PROTOCOL_SCHEDULES =
    ReferenceTestProtocolSchedules.create();
  private static final List<String> SPECS_PRIOR_TO_DELETING_EMPTY_ACCOUNTS =
    Arrays.asList("Frontier", "Homestead", "EIP150");

  private static MainnetTransactionProcessor transactionProcessor(final String name) {
    return protocolSpec(name).getTransactionProcessor();
  }

  private static ProtocolSpec protocolSpec(final String name) {
    return REFERENCE_TEST_PROTOCOL_SCHEDULES
      .getByName(name)
      .getByBlockHeader(BlockHeaderBuilder.createDefault().buildBlockHeader());
  }

  private static final List<String> EIPS_TO_RUN;

  static {
    final String eips =
      System.getProperty(
        "test.ethereum.state.eips",
        "Frontier,Homestead,EIP150,EIP158,Byzantium,Constantinople,ConstantinopleFix,Istanbul,Berlin,"
          + "London" /* + ",Merge,Shanghai,Cancun,Prague,Osaka,Bogota"*/);
    EIPS_TO_RUN = Arrays.asList(eips.split(","));
  }

  private static final JsonTestParameters<?, ?> params =
    JsonTestParameters.create(
        GeneralStateTestCaseSpec.class, GeneralStateTestCaseEipSpec.class)
      .generator(
        (testName, fullPath, stateSpec, collector) -> {
          final String prefix = testName + "-";
          for (final Map.Entry<String, List<GeneralStateTestCaseEipSpec>> entry :
            stateSpec.finalStateSpecs().entrySet()) {
            final String eip = entry.getKey();
            final boolean runTest = EIPS_TO_RUN.contains(eip);
            final List<GeneralStateTestCaseEipSpec> eipSpecs = entry.getValue();
            if (eipSpecs.size() == 1) {
              collector.add(prefix + eip, fullPath, eipSpecs.get(0), runTest);
            } else {
              for (int i = 0; i < eipSpecs.size(); i++) {
                collector.add(
                  prefix + eip + '[' + i + ']', fullPath, eipSpecs.get(i), runTest);
              }
            }
          }
        });

  static {
    if (EIPS_TO_RUN.isEmpty()) {
      params.ignoreAll();
    }

    // Consumes a huge amount of memory
    params.ignore("static_Call1MB1024Calldepth-\\w");
    params.ignore("ShanghaiLove_.*");

    // Don't do time consuming tests
    params.ignore("CALLBlake2f_MaxRounds.*");
    params.ignore("loopMul-.*");

    // Reference Tests are old.  Max blob count is 6.
    params.ignore("blobhashListBounds5");

    // EOF tests are written against an older version of the spec
    params.ignore("/stEOF/");
  }

  private GeneralStateReferenceTestTools() {
    // utility class
  }

  public static Collection<Object[]> generateTestParametersForConfig(final String[] filePath) {
    return params.generate(filePath);
  }

  public static void executeTest(final GeneralStateTestCaseEipSpec spec) {
    final BlockHeader blockHeader = spec.getBlockHeader();
    final ReferenceTestWorldState initialWorldState = (ReferenceTestWorldState) spec.getInitialWorldState();
    final Transaction transaction = spec.getTransaction();

    // Sometimes the tests ask us assemble an invalid transaction.  If we have
    // no valid transaction then there is no test.  GeneralBlockChain tests
    // will handle the case where we receive the TXs in a serialized form.
    if (transaction == null) {
      assertThat(spec.getExpectException())
        .withFailMessage("Transaction was not assembled, but no exception was expected")
        .isNotNull();
      return;
    }

    final MutableWorldState worldState = initialWorldState.copy();
    // Several of the GeneralStateTests check if the transaction could potentially
    // consume more gas than is left for the block it's attempted to be included in.
    // This check is performed within the `BlockImporter` rather than inside the
    // `TransactionProcessor`, so these tests are skipped.
    if (transaction.getGasLimit() > blockHeader.getGasLimit() - blockHeader.getGasUsed()) {
      return;
    }

    final MainnetTransactionProcessor processor = transactionProcessor(spec.getFork());
    final WorldUpdater worldStateUpdater = worldState.updater();
    final ReferenceTestBlockchain blockchain = new ReferenceTestBlockchain(blockHeader.getNumber());
    final Wei blobGasPrice =
      protocolSpec(spec.getFork())
        .getFeeMarket()
        .blobGasPricePerGas(blockHeader.getExcessBlobGas().orElse(BlobGas.ZERO));

    final ZkTracer zkTracer = new ZkTracer();
    zkTracer.traceStartConflation(1);

    final TransactionProcessingResult result =
      processor.processTransaction(
        blockchain,
        worldStateUpdater,
        blockHeader,
        transaction,
        blockHeader.getCoinbase(),
        zkTracer,
        new CachingBlockHashLookup(blockHeader, blockchain),
        false,
        TransactionValidationParams.processingBlock(),
        blobGasPrice);
    if (result.isInvalid()) {
      assertThat(spec.getExpectException())
        .withFailMessage(() -> result.getValidationResult().getErrorMessage())
        .isNotNull();
      return;
    }

    zkTracer.traceEndConflation();

    assertThat(spec.getExpectException())
      .withFailMessage("Exception was expected - " + spec.getExpectException())
      .isNull();

    final Account coinbase = worldStateUpdater.getOrCreate(spec.getBlockHeader().getCoinbase());
    if (coinbase != null && coinbase.isEmpty() && shouldClearEmptyAccounts(spec.getFork())) {
      worldStateUpdater.deleteAccount(coinbase.getAddress());
    }
    worldStateUpdater.commit();
    worldState.persist(blockHeader);

    // Check the world state root hash.
    final Hash expectedRootHash = spec.getExpectedRootHash();
    assertThat(worldState.rootHash())
      .withFailMessage(
        "Unexpected world state root hash; expected state: %s, computed state: %s",
        spec.getExpectedRootHash(), worldState.rootHash())
      .isEqualTo(expectedRootHash);

    // Check the logs.
    final Hash expectedLogsHash = spec.getExpectedLogsHash();
    Optional.ofNullable(expectedLogsHash)
      .ifPresent(
        expected -> {
          final List<Log> logs = result.getLogs();

          assertThat(Hash.hash(RLP.encode(out -> out.writeList(logs, Log::writeTo))))
            .withFailMessage("Unmatched logs hash. Generated logs: %s", logs)
            .isEqualTo(expected);
        });

    assertThat(CorsetValidator.isValid(zkTracer.getJsonTrace())).isTrue();
  }

  private static boolean shouldClearEmptyAccounts(final String eip) {
    return !SPECS_PRIOR_TO_DELETING_EMPTY_ACCOUNTS.contains(eip);
  }
}