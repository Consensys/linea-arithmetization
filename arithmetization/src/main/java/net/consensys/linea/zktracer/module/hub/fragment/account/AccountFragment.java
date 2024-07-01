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

package net.consensys.linea.zktracer.module.hub.fragment.account;

import static net.consensys.linea.zktracer.types.AddressUtils.highPart;
import static net.consensys.linea.zktracer.types.AddressUtils.isPrecompile;
import static net.consensys.linea.zktracer.types.AddressUtils.lowPart;

import java.util.Optional;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.consensys.linea.zktracer.module.hub.AccountSnapshot;
import net.consensys.linea.zktracer.module.hub.Hub;
import net.consensys.linea.zktracer.module.hub.Trace;
import net.consensys.linea.zktracer.module.hub.defer.DeferRegistry;
import net.consensys.linea.zktracer.module.hub.defer.PostConflationDefer;
import net.consensys.linea.zktracer.module.hub.defer.PostTransactionDefer;
import net.consensys.linea.zktracer.module.hub.fragment.DomSubStampsSubFragment;
import net.consensys.linea.zktracer.module.hub.fragment.TraceFragment;
import net.consensys.linea.zktracer.module.romlex.ContractMetadata;
import net.consensys.linea.zktracer.types.EWord;
import org.apache.tuweni.bytes.Bytes;
import org.hyperledger.besu.datatypes.Hash;
import org.hyperledger.besu.datatypes.Transaction;
import org.hyperledger.besu.evm.worldstate.WorldView;

@Accessors(fluent = true)
public final class AccountFragment
    implements TraceFragment, PostTransactionDefer, PostConflationDefer {

  /**
   * {@link AccountFragment} creation requires access to a {@link DeferRegistry} for post-conflation
   * data gathering, which is provided by this factory.
   */
  @RequiredArgsConstructor
  public static class AccountFragmentFactory {
    private final DeferRegistry defers;

    public AccountFragment make(
        AccountSnapshot oldState,
        AccountSnapshot newState,
        DomSubStampsSubFragment domSubStampsSubFragment) {
      return new AccountFragment(
          this.defers, oldState, newState, Optional.empty(), domSubStampsSubFragment);
    }

    public AccountFragment makeWithTrm(
        AccountSnapshot oldState,
        AccountSnapshot newState,
        Bytes toTrim,
        DomSubStampsSubFragment domSubStampsSubFragment) {
      return new AccountFragment(
          this.defers, oldState, newState, Optional.of(toTrim), domSubStampsSubFragment);
    }
  }

  private final AccountSnapshot oldState;
  private final AccountSnapshot newState;
  @Setter private int deploymentNumberInfinity = 0; // retconned on conflation end
  private final int deploymentNumber;
  private final boolean isDeployment;
  @Setter private boolean existsInfinity = false; // retconned on conflation end
  private int codeFragmentIndex;
  @Setter private boolean requiresRomlex;
  private final Optional<Bytes> addressToTrim;
  private final DomSubStampsSubFragment domSubStampsSubFragment;

  public AccountFragment(
      final DeferRegistry defers,
      AccountSnapshot oldState,
      AccountSnapshot newState,
      Optional<Bytes> addressToTrim,
      DomSubStampsSubFragment domSubStampsSubFragment) {
    Preconditions.checkArgument(oldState.address().equals(newState.address()));

    this.oldState = oldState;
    this.newState = newState;
    this.deploymentNumber = newState.deploymentNumber();
    this.isDeployment = newState.deploymentStatus();
    this.addressToTrim = addressToTrim;
    this.domSubStampsSubFragment = domSubStampsSubFragment;

    defers.postConflation(this);
  }

  @Override
  public Trace trace(Trace trace) {
    final EWord eCodeHash = EWord.of(oldState.code().getCodeHash());
    final EWord eCodeHashNew = EWord.of(newState.code().getCodeHash());

    // tracing
    this.domSubStampsSubFragment.trace(trace);

    return trace
        .peekAtAccount(true)
        .pAccountAddressHi(highPart(oldState.address()))
        .pAccountAddressLo(lowPart(oldState.address()))
        .pAccountNonce(Bytes.ofUnsignedLong(oldState.nonce()))
        .pAccountNonceNew(Bytes.ofUnsignedLong(newState.nonce()))
        .pAccountBalance(oldState.balance())
        .pAccountBalanceNew(newState.balance())
        .pAccountCodeSize(oldState.code().getSize())
        .pAccountCodeSizeNew(newState.code().getSize())
        .pAccountCodeHashHi(eCodeHash.hi())
        .pAccountCodeHashHiNew(eCodeHashNew.hi())
        .pAccountCodeHashLo(eCodeHash.lo())
        .pAccountCodeHashLoNew(eCodeHashNew.lo())
        .pAccountHasCode(oldState.code().getCodeHash() != Hash.EMPTY)
        .pAccountHasCodeNew(newState.code().getCodeHash() != Hash.EMPTY)
        .pAccountCodeFragmentIndex(this.codeFragmentIndex)
        .pAccountRomlexFlag(requiresRomlex)
        .pAccountExists(
            oldState.nonce() > 0
                || oldState.code().getCodeHash() != Hash.EMPTY
                || !oldState.balance().isZero())
        .pAccountExistsNew(
            newState.nonce() > 0
                || newState.code().getCodeHash() != Hash.EMPTY
                || !newState.balance().isZero())
        .pAccountWarmth(oldState.isWarm())
        .pAccountWarmthNew(newState.isWarm())
        .pAccountMarkedForSelfdestruct(false) // TODO:
        .pAccountMarkedForSelfdestructNew(false) // TODO
        .pAccountDeploymentNumber(oldState.deploymentNumber())
        .pAccountDeploymentNumberNew(newState.deploymentNumber())
        .pAccountDeploymentStatus(oldState.deploymentStatus())
        .pAccountDeploymentStatusNew(newState.deploymentStatus())
        .pAccountDeploymentNumberInfty(deploymentNumberInfinity)
        .pAccountDeploymentStatusInfty(existsInfinity)
        .pAccountTrmFlag(this.addressToTrim.isPresent())
        .pAccountTrmRawAddressHi(this.addressToTrim.map(a -> EWord.of(a).hi()).orElse(Bytes.EMPTY))
        .pAccountIsPrecompile(isPrecompile(oldState.address()))
        .pAccountRlpaddrFlag(false) // TODO
        .pAccountRlpaddrRecipe(false) // TODO
        .pAccountRlpaddrDepAddrHi(0) // TODO
        .pAccountRlpaddrDepAddrLo(Bytes.EMPTY) // TODO
        .pAccountRlpaddrSaltHi(Bytes.EMPTY) // TODO
        .pAccountRlpaddrSaltLo(Bytes.EMPTY) // TODO
        .pAccountRlpaddrKecHi(Bytes.EMPTY) // TODO
        .pAccountRlpaddrKecLo(Bytes.EMPTY) // TODO
    ;
  }

  @Override
  public void runPostTx(Hub hub, WorldView state, Transaction tx, boolean isSuccessful) {}

  @Override
  public void runPostConflation(Hub hub, WorldView world) {
    this.deploymentNumberInfinity =
        hub.transients().conflation().deploymentInfo().number(this.oldState.address());
    this.existsInfinity = world.get(this.oldState.address()) != null;
    this.codeFragmentIndex =
        this.requiresRomlex
            ? hub.romLex()
                .getCodeFragmentIndexByMetadata(
                    ContractMetadata.make(
                        this.oldState.address(), this.deploymentNumber, this.isDeployment))
            : 0;
  }
}
