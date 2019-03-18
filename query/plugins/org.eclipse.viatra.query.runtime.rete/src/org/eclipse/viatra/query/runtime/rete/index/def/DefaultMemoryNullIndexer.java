/*******************************************************************************
 * Copyright (c) 2004-2008 Gabor Bergmann and Daniel Varro
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-v20.html.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/

package org.eclipse.viatra.query.runtime.rete.index.def;

import java.util.Collection;
import java.util.List;

import org.eclipse.viatra.query.runtime.matchers.tuple.Tuple;
import org.eclipse.viatra.query.runtime.rete.index.NullIndexer;
import org.eclipse.viatra.query.runtime.rete.network.Receiver;
import org.eclipse.viatra.query.runtime.rete.network.ReteContainer;
import org.eclipse.viatra.query.runtime.rete.network.Supplier;

/**
 * Defines a trivial indexer that projects the contents of a memory-equipped node to the empty tuple, and can therefore
 * save space. Can only exist in connection with a memory, and must be operated by another node. Do not attach parents
 * directly!
 * 
 * @author Gabor Bergmann
 * @noimplement Rely on the provided implementations
 * @noreference Use only via standard Node and Indexer interfaces
 * @noinstantiate This class is not intended to be instantiated by clients.
 */
public class DefaultMemoryNullIndexer extends NullIndexer {

    Collection<Tuple> memory;

    /**
     * @param reteContainer
     * @param tupleWidth
     *            the width of the tuples of memoryNode
     * @param memory
     *            the memory whose contents are to be null-indexed
     * @param parent
     *            the parent node that owns the memory
     */
    public DefaultMemoryNullIndexer(ReteContainer reteContainer, int tupleWidth, Collection<Tuple> memory,
            Supplier parent, Receiver activeNode, List<ListenerSubscription> sharedSubscriptionList) {
        super(reteContainer, tupleWidth, parent, activeNode, sharedSubscriptionList);
        this.memory = memory;
    }

    @Override
    protected Collection<Tuple> getTuples() {
        return this.memory;
    }

}
