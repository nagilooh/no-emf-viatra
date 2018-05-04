/*******************************************************************************
 * Copyright (c) 2010-2018, Gabor Bergmann, IncQuery Labs Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gabor Bergmann - initial API and implementation
 *******************************************************************************/
package org.eclipse.viatra.query.runtime.matchers.scopes.tables;

import java.util.Map;

import org.eclipse.viatra.query.runtime.matchers.context.IInputKey;
import org.eclipse.viatra.query.runtime.matchers.memories.MaskedTupleMemory;
import org.eclipse.viatra.query.runtime.matchers.tuple.ITuple;
import org.eclipse.viatra.query.runtime.matchers.tuple.Tuple;
import org.eclipse.viatra.query.runtime.matchers.tuple.TupleMask;
import org.eclipse.viatra.query.runtime.matchers.util.CollectionsFactory;
import org.eclipse.viatra.query.runtime.matchers.util.CollectionsFactory.BucketType;
import org.eclipse.viatra.query.runtime.matchers.util.Direction;
import org.eclipse.viatra.query.runtime.matchers.util.IMemory;

/**
 * Demo default implementation.
 * <p>
 * <strong>EXPERIMENTAL</strong>. This class or interface has been added as
 * part of a work in progress. There is no guarantee that this API will
 * work or that it will remain the same.
 *
 * @since 2.0
 * @author Gabor Bergmann
 */
public class DefaultIndexTable extends AbstractIndexTable implements ITableWriterGeneric {

    protected IMemory<Tuple> rows = CollectionsFactory.createMultiset(); // TODO use SetMemory if unique
    protected Map<TupleMask, MaskedTupleMemory> indexMemories = CollectionsFactory.createMap();
    private boolean unique;

    /**
     * @param unique
     *            client promises to only insert a given tuple with multiplicity one
     */
    public DefaultIndexTable(IInputKey inputKey, ITableContext tableContext, boolean unique) {
        super(inputKey, tableContext);
        this.unique = unique;
    }

    @Override
    public void write(Direction direction, Tuple row) {
        if (direction == Direction.INSERT) {
            boolean changed = rows.addOne(row);
            if (unique && !changed) {
                String msg = String.format(
                        "Error: trying to add duplicate row %s to the unique table %s. This indicates some errors in underlying model representation.",
                        row, getInputKey().getPrettyPrintableName());
                logError(msg);
            }
            if (changed) {
                for (MaskedTupleMemory indexMemory : indexMemories.values()) {
                    indexMemory.add(row);
                }
            }
        } else { // DELETE
            boolean changed = rows.removeOne(row);
            if (unique && !changed) {
                String msg = String.format(
                        "Error: trying to remove duplicate value %s from the unique table %s. This indicates some errors in underlying model representation.",
                        row, getInputKey().getPrettyPrintableName());
                logError(msg);
            }
            if (changed) {
                for (MaskedTupleMemory indexMemory : indexMemories.values()) {
                    indexMemory.remove(row);
                }
            }
        }
    }

    @Override
    public boolean containsTuple(ITuple seed) {
        return rows.distinctValues().contains(seed);
    }

    private MaskedTupleMemory getIndexMemory(TupleMask seedMask) {
        return indexMemories.computeIfAbsent(seedMask, mask -> {
            MaskedTupleMemory memory = MaskedTupleMemory.create(seedMask, BucketType.SETS, DefaultIndexTable.this);
            for (Tuple tuple : rows.distinctValues()) {
                memory.add(tuple);
            }
            return memory;
        });
    }
    
    @Override
    public int countTuples(TupleMask seedMask, ITuple seed) {
        switch (seedMask.getSize()) {
        case 0: // unseeded
            return rows.size();
        default:
            return getIndexMemory(seedMask).get(seed).size();
        }
    }

    @Override
    public Iterable<Tuple> enumerateTuples(TupleMask seedMask, ITuple seed) {
        return getIndexMemory(seedMask).get(seed);
    }

    @Override
    public Iterable<? extends Object> enumerateValues(TupleMask seedMask, ITuple seed) {
        // we assume there is a single omitted index in the mask
        int queriedColumn = seedMask.getFirstOmittedIndex().getAsInt();

        return () -> getIndexMemory(seedMask).get(seed).stream()
                .map(row2 -> row2.get(queriedColumn)).iterator();
    }
}
