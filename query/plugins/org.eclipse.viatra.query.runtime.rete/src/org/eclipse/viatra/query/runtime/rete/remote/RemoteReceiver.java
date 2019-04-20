/*******************************************************************************
 * Copyright (c) 2004-2008 Gabor Bergmann and Daniel Varro
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-v20.html.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/

package org.eclipse.viatra.query.runtime.rete.remote;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.viatra.query.runtime.matchers.tuple.Tuple;
import org.eclipse.viatra.query.runtime.matchers.util.Direction;
import org.eclipse.viatra.query.runtime.matchers.util.timeline.Timeline;
import org.eclipse.viatra.query.runtime.rete.network.Receiver;
import org.eclipse.viatra.query.runtime.rete.network.ReteContainer;
import org.eclipse.viatra.query.runtime.rete.network.communication.Timestamp;
import org.eclipse.viatra.query.runtime.rete.single.SingleInputNode;

/**
 * This node delivers updates to a remote recipient; no updates are propagated further in this network.
 * 
 * @author Gabor Bergmann
 * 
 */
public class RemoteReceiver extends SingleInputNode {

    List<Address<? extends Receiver>> targets;

    public RemoteReceiver(ReteContainer reteContainer) {
        super(reteContainer);
        targets = new ArrayList<Address<? extends Receiver>>();
    }

    public void addTarget(Address<? extends Receiver> target) {
        targets.add(target);
    }

    @Override
    public void pullInto(Collection<Tuple> collector, boolean flush) {
        propagatePullInto(collector, flush);
    }
    
    @Override
    public void pullIntoWithTimeline(Map<Tuple, Timeline<Timestamp>> collector, boolean flush) {
        throw new UnsupportedOperationException();
    }

    public Collection<Tuple> remotePull(boolean flush) {
        return reteContainer.pullContents(this, flush);
    }

    public void update(Direction direction, Tuple updateElement, Timestamp timestamp) {
        for (Address<? extends Receiver> ad : targets)
            reteContainer.sendUpdateToRemoteAddress(ad, direction, updateElement);
    }

}
