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

import static net.consensys.linea.zktracer.module.hub.fragment.ContextFragment.executionProvidesEmptyReturnData;
import static net.consensys.linea.zktracer.module.hub.fragment.ContextFragment.readCurrentContextData;

import com.google.common.base.Preconditions;
import net.consensys.linea.zktracer.module.hub.AccountSnapshot;
import net.consensys.linea.zktracer.module.hub.Hub;
import net.consensys.linea.zktracer.module.hub.fragment.DomSubStampsSubFragment;
import net.consensys.linea.zktracer.module.hub.fragment.TraceFragment;
import net.consensys.linea.zktracer.module.hub.fragment.account.AccountFragment;
import net.consensys.linea.zktracer.types.Bytecode;
import org.hyperledger.besu.datatypes.Address;

public class StopSection extends TraceSection {

  public static void appendTo(Hub hub) {
    if (!hub.currentFrame().underDeployment()) {
      hub.addTraceSection(messageCallStopSection(hub));
    } else if (hub.currentFrame().willRevert()) {
      hub.addTraceSection(deploymentStopSection(hub));
    }
  }

  public StopSection(Hub hub, TraceFragment... fragments) {
    this.addFragmentsAndStack(hub, fragments);
  }

  public static StopSection messageCallStopSection(Hub hub) {
    StopSection messageCallStopSetion =
        new StopSection(hub, readCurrentContextData(hub), executionProvidesEmptyReturnData(hub));
    return messageCallStopSetion;
  }

  public static StopSection deploymentStopSection(Hub hub) {
    AccountFragment.AccountFragmentFactory accountFragmentFactory =
        hub.factories().accountFragment();

    final Address address = hub.currentFrame().accountAddress();
    final int deploymentNumber = hub.transients().conflation().deploymentInfo().number(address);
    final boolean deploymentStatus =
        hub.transients().conflation().deploymentInfo().isDeploying(address);

    // we should be deploying
    Preconditions.checkArgument(deploymentStatus);

    AccountSnapshot beforeEmptyDeployment =
        AccountSnapshot.fromAddress(address, true, deploymentNumber, deploymentStatus);
    AccountSnapshot afterEmptyDeployment = beforeEmptyDeployment.deployByteCode(Bytecode.EMPTY);
    StopSection stopWhileDeploying =
        new StopSection(
            hub,
            readCurrentContextData(hub),
            // current (under deployment => deployed with empty byte code)
            accountFragmentFactory.make(
                beforeEmptyDeployment,
                afterEmptyDeployment,
                DomSubStampsSubFragment.standardDomSubStamps(hub, 0)));

    if (hub.currentFrame().willRevert()) {
      // undoing of the above
      stopWhileDeploying.addFragmentsWithoutStack(
          hub,
          accountFragmentFactory.make(
              afterEmptyDeployment,
              beforeEmptyDeployment,
              DomSubStampsSubFragment.revertWithCurrentDomSubStamps(hub, 1)),
          executionProvidesEmptyReturnData(hub));
    } else {
      stopWhileDeploying.addFragmentsWithoutStack(hub, executionProvidesEmptyReturnData(hub));
    }

    return stopWhileDeploying;
  }

  public static StopSection unrevertedDeploymentStopSection(Hub hub) {

    AccountFragment.AccountFragmentFactory accountFragmentFactory =
        hub.factories().accountFragment();

    final Address address = hub.currentFrame().accountAddress();
    final int deploymentNumber = hub.transients().conflation().deploymentInfo().number(address);
    final boolean deploymentStatus =
        hub.transients().conflation().deploymentInfo().isDeploying(address);

    // we should be deploying
    Preconditions.checkArgument(deploymentStatus);

    return new StopSection(
        hub,
        readCurrentContextData(hub),
        // current (under deployment => deployed with empty byte code)
        executionProvidesEmptyReturnData(hub));
  }
}
