/*******************************************************************************
 * Copyright (c) 2010-2018, Gabor Bergmann, IncQuery Labs Ltd.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-v20.html.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.viatra.query.runtime.matchers.memories.timely;

import java.util.Iterator;

import org.eclipse.viatra.query.runtime.matchers.memories.MaskedTupleMemory;
import org.eclipse.viatra.query.runtime.matchers.memories.TimestampReplacement;
import org.eclipse.viatra.query.runtime.matchers.tuple.Tuple;
import org.eclipse.viatra.query.runtime.matchers.tuple.TupleMask;
import org.eclipse.viatra.query.runtime.matchers.util.TimelyMemory;

abstract class AbstractTimelyTrivialMaskedMemory<Timestamp extends Comparable<Timestamp>> extends MaskedTupleMemory<Timestamp> {

    protected final TimelyMemory<Timestamp> memory;

    protected AbstractTimelyTrivialMaskedMemory(final TupleMask mask, final Object owner) {
        super(mask, owner);
        this.memory = new TimelyMemory<Timestamp>();
    }
    
    @Override
    public void initializeWith(final MaskedTupleMemory<Timestamp> other, final Timestamp defaultValue) {
        final Iterator<Tuple> itr = other.iterator();
        while (itr.hasNext()) {
            this.addWithTimestamp(itr.next(), defaultValue);
        }
    }

    @Override
    public void clear() {
        this.memory.clear();
    }

    @Override
    public int getTotalSize() {
        return this.memory.size();
    }

    @Override
    public Iterator<Tuple> iterator() {
        return this.memory.keySet().iterator();
    }
    
    @Override
    public TimestampReplacement<Timestamp> removeWithTimestamp(final Tuple tuple, final Timestamp timestamp) {
        return removeWithTimestamp(tuple, null, timestamp);
    }

    @Override
    public TimestampReplacement<Timestamp> addWithTimestamp(final Tuple tuple, final Timestamp timestamp) {
        return addWithTimestamp(tuple, null, timestamp);
    }

}