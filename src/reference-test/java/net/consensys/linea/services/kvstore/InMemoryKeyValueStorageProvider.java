package net.consensys.linea.services.kvstore;

import org.hyperledger.besu.ethereum.bonsai.BonsaiWorldStateProvider;
import org.hyperledger.besu.ethereum.bonsai.cache.CachedMerkleTrieLoader;
import org.hyperledger.besu.ethereum.chain.Blockchain;
import org.hyperledger.besu.ethereum.chain.DefaultBlockchain;
import org.hyperledger.besu.ethereum.chain.MutableBlockchain;
import org.hyperledger.besu.ethereum.chain.VariablesStorage;
import org.hyperledger.besu.ethereum.core.Block;
import org.hyperledger.besu.ethereum.core.BlockHeaderFunctions;
import org.hyperledger.besu.ethereum.core.MutableWorldState;
import org.hyperledger.besu.ethereum.mainnet.MainnetBlockHeaderFunctions;
import org.hyperledger.besu.ethereum.privacy.storage.PrivateStateKeyValueStorage;
import org.hyperledger.besu.ethereum.privacy.storage.PrivateStateStorage;
import org.hyperledger.besu.ethereum.storage.keyvalue.KeyValueStoragePrefixedKeyBlockchainStorage;
import org.hyperledger.besu.ethereum.storage.keyvalue.KeyValueStorageProvider;
import org.hyperledger.besu.ethereum.storage.keyvalue.VariablesKeyValueStorage;
import org.hyperledger.besu.ethereum.storage.keyvalue.WorldStateKeyValueStorage;
import org.hyperledger.besu.ethereum.storage.keyvalue.WorldStatePreimageKeyValueStorage;
import org.hyperledger.besu.ethereum.worldstate.DataStorageFormat;
import org.hyperledger.besu.ethereum.worldstate.DefaultMutableWorldState;
import org.hyperledger.besu.ethereum.worldstate.DefaultWorldStateArchive;
import org.hyperledger.besu.metrics.noop.NoOpMetricsSystem;

public class InMemoryKeyValueStorageProvider extends KeyValueStorageProvider {

  public InMemoryKeyValueStorageProvider() {
    super(
      segmentIdentifiers -> new SegmentedInMemoryKeyValueStorage(),
      new InMemoryKeyValueStorage(),
      new NoOpMetricsSystem());
  }

  public static MutableBlockchain createInMemoryBlockchain(final Block genesisBlock) {
    return createInMemoryBlockchain(genesisBlock, createInMemoryVariablesStorage());
  }

  public static MutableBlockchain createInMemoryBlockchain(
    final Block genesisBlock, final VariablesStorage variablesStorage) {
    return createInMemoryBlockchain(
      genesisBlock, new MainnetBlockHeaderFunctions(), variablesStorage);
  }

  public static MutableBlockchain createInMemoryBlockchain(
    final Block genesisBlock, final BlockHeaderFunctions blockHeaderFunctions) {
    return createInMemoryBlockchain(
      genesisBlock, blockHeaderFunctions, createInMemoryVariablesStorage());
  }

  public static MutableBlockchain createInMemoryBlockchain(
    final Block genesisBlock,
    final BlockHeaderFunctions blockHeaderFunctions,
    final VariablesStorage variablesStorage) {
    final InMemoryKeyValueStorage keyValueStorage = new InMemoryKeyValueStorage();
    return DefaultBlockchain.createMutable(
      genesisBlock,
      new KeyValueStoragePrefixedKeyBlockchainStorage(
        keyValueStorage, variablesStorage, blockHeaderFunctions),
      new NoOpMetricsSystem(),
      0);
  }

  public static DefaultWorldStateArchive createInMemoryWorldStateArchive() {
    return new DefaultWorldStateArchive(
      new WorldStateKeyValueStorage(new InMemoryKeyValueStorage()),
      new WorldStatePreimageKeyValueStorage(new InMemoryKeyValueStorage()));
  }

  public static BonsaiWorldStateProvider createBonsaiInMemoryWorldStateArchive(
    final Blockchain blockchain) {
    final InMemoryKeyValueStorageProvider inMemoryKeyValueStorageProvider =
      new InMemoryKeyValueStorageProvider();
    final CachedMerkleTrieLoader cachedMerkleTrieLoader =
      new CachedMerkleTrieLoader(new NoOpMetricsSystem());
    return new BonsaiWorldStateProvider(
      inMemoryKeyValueStorageProvider,
      blockchain,
      cachedMerkleTrieLoader,
      new NoOpMetricsSystem(),
      null);
  }

  public static MutableWorldState createInMemoryWorldState() {
    final InMemoryKeyValueStorageProvider provider = new InMemoryKeyValueStorageProvider();
    return new DefaultMutableWorldState(
      provider.createWorldStateStorage(DataStorageFormat.FOREST),
      provider.createWorldStatePreimageStorage());
  }

  public static PrivateStateStorage createInMemoryPrivateStateStorage() {
    return new PrivateStateKeyValueStorage(new InMemoryKeyValueStorage());
  }

  public static VariablesStorage createInMemoryVariablesStorage() {
    return new VariablesKeyValueStorage(new InMemoryKeyValueStorage());
  }
}