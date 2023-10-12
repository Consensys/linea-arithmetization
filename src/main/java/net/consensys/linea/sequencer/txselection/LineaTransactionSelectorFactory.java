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

package net.consensys.linea.sequencer.txselection;

import net.consensys.linea.sequencer.txselection.selectors.LineaTransactionSelector;
import org.hyperledger.besu.plugin.services.txselection.TransactionSelector;
import org.hyperledger.besu.plugin.services.txselection.TransactionSelectorFactory;

/** Represents a factory for creating transaction selectors. */
public class LineaTransactionSelectorFactory implements TransactionSelectorFactory {
  private final LineaTransactionSelectionCliOptions options;

  public LineaTransactionSelectorFactory(final LineaTransactionSelectionCliOptions options) {
    this.options = options;
  }

  @Override
  public TransactionSelector create() {
    final LineaTransactionSelectionConfiguration lineaConfiguration = options.toDomainObject();
    return new LineaTransactionSelector(lineaConfiguration);
  }
}
