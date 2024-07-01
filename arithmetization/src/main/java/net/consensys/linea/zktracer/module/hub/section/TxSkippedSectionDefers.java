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

package net.consensys.linea.zktracer.module.hub.section;

import static net.consensys.linea.zktracer.types.AddressUtils.isPrecompile;

import net.consensys.linea.zktracer.module.hub.AccountSnapshot;
import net.consensys.linea.zktracer.module.hub.Hub;
import net.consensys.linea.zktracer.module.hub.defer.PostTransactionDefer;
import net.consensys.linea.zktracer.module.hub.fragment.DomSubStampsSubFragment;
import net.consensys.linea.zktracer.module.hub.fragment.TraceFragment;
import net.consensys.linea.zktracer.module.hub.fragment.TransactionFragment;
import net.consensys.linea.zktracer.module.hub.transients.Transients;
import net.consensys.linea.zktracer.types.TransactionProcessingMetadata;
import org.hyperledger.besu.datatypes.Address;
import org.hyperledger.besu.datatypes.Transaction;
import org.hyperledger.besu.evm.worldstate.WorldView;

/**
 * SkippedTransaction latches data at the pre-execution of the transaction data that will be used
 * later, through a {@link PostTransactionDefer}, to generate the trace chunks required for the
 * proving of a pure transaction.
 */
public class TxSkippedSectionDefers implements PostTransactionDefer {
  final TransactionProcessingMetadata txMetadata;
  final AccountSnapshot oldFromAccount;
  final AccountSnapshot oldToAccount;
  final AccountSnapshot oldMinerAccount;

  public TxSkippedSectionDefers(
      WorldView world, TransactionProcessingMetadata txMetadata, Transients transients) {
    this.txMetadata = txMetadata;

    // From account information
    final Address fromAddress = txMetadata.getBesuTransaction().getSender();
    this.oldFromAccount =
        AccountSnapshot.fromAccount(
            world.get(fromAddress),
            isPrecompile(fromAddress),
            transients.conflation().deploymentInfo().number(fromAddress),
            false);

    // To account information
    final Address toAddress = txMetadata.getEffectiveTo();
    if (txMetadata.isDeployment()) {
      transients.conflation().deploymentInfo().deploy(toAddress);
    }
    this.oldToAccount =
        AccountSnapshot.fromAccount(
            world.get(toAddress),
            isPrecompile(toAddress),
            transients.conflation().deploymentInfo().number(toAddress),
            false);

    // Miner account information
    final Address minerAddress = txMetadata.getCoinbase();
    this.oldMinerAccount =
        AccountSnapshot.fromAccount(
            world.get(minerAddress),
            isPrecompile(minerAddress),
            transients.conflation().deploymentInfo().number(transients.block().minerAddress()),
            false);
  }

  @Override
  public void runPostTx(Hub hub, WorldView state, Transaction tx, boolean isSuccessful) {
    final Address fromAddress = this.oldFromAccount.address();
    final Address toAddress = this.oldToAccount.address();
    final Address minerAddress = this.txMetadata.getCoinbase();
    hub.transients().conflation().deploymentInfo().unmarkDeploying(toAddress);

    final AccountSnapshot newFromAccount =
        AccountSnapshot.fromAccount(
            state.get(fromAddress),
            this.oldFromAccount.isWarm(),
            hub.transients().conflation().deploymentInfo().number(fromAddress),
            false);

    final AccountSnapshot newToAccount =
        AccountSnapshot.fromAccount(
            state.get(toAddress),
            this.oldToAccount.isWarm(),
            hub.transients().conflation().deploymentInfo().number(toAddress),
            false);

    final AccountSnapshot newMinerAccount =
        AccountSnapshot.fromAccount(
            state.get(minerAddress),
            this.oldMinerAccount.isWarm(),
            hub.transients().conflation().deploymentInfo().number(minerAddress),
            false);

    // Append the final chunk to the hub chunks
    hub.addTraceSection(
        new TxSkippedSection(
            hub,
            // 3 lines -- account changes
            // From
            hub.factories()
                .accountFragment()
                .make(
                    oldFromAccount,
                    newFromAccount,
                    DomSubStampsSubFragment.standardDomSubStamps(hub, 0)),
            // To
            hub.factories()
                .accountFragment()
                .make(
                    oldToAccount,
                    newToAccount,
                    DomSubStampsSubFragment.standardDomSubStamps(hub, 1)),
            // Miner
            hub.factories()
                .accountFragment()
                .make(
                    oldMinerAccount,
                    newMinerAccount,
                    DomSubStampsSubFragment.standardDomSubStamps(hub, 2)),

            // 1 line -- transaction data
            TransactionFragment.prepare(hub.txStack().current())));
  }

  public static class TxSkippedSection extends TraceSection {
    public TxSkippedSection(Hub hub, TraceFragment... fragments) {
      super(hub, (short) 4);
      this.addFragmentsWithoutStack(fragments);
    }
  }
}
