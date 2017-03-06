/*******************************************************************************
 * Copyright (c) 2010-2015, Bergmann Gabor, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Bergmann Gabor - initial API and implementation
 *******************************************************************************/
package org.eclipse.viatra.query.runtime.matchers.context;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.SetMultimap;

/**
 * Provides metamodel information (relationship of input keys) to query evaluator backends at runtime and at query planning time.
 * 
 * @noimplement Implementors should extend {@link AbstractQueryMetaContext} instead of directly implementing this interface.
 * @author Bergmann Gabor
 */
public interface IQueryMetaContext {
	
	/**
	 * Returns true iff instance tuples of the given key can be enumerated.
	 * <p> If false, the runtime can only test tuple membership in the extensional relation identified by the key, but not enumerate member tuples in general.
	 * <p> Equivalent to {@link IInputKey#isEnumerable()}.
	 */
	boolean isEnumerable(IInputKey key);
	
	/**
	 * Returns true iff the set of instance tuples of the given key is immutable.
	 * <p> If false, the runtime provides notifications upon change.
	 */
	boolean isStateless(IInputKey key);
	
	/**
	 * Returns known direct implications, e.g. edge supertypes, edge opposites, node type constraints, etc.
     * <p> Note that for the obvious reasons, enumerable keys can only be implied by enumerable keys.
	 */
	Collection<InputKeyImplication> getImplications(IInputKey implyingKey);
    
    /**
     * Returns known "double dispatch" implications, where the given implying key implies other input keys under certain additional conditions (themselves input keys).
     * For example, a "type x, unscoped" input key may imply the "type x, in scope" input key under the condition of the input key "x is in scope"
     * 
     * <p> Note that for the obvious reasons, enumerable keys can only be implied by enumerable keys (either as the implying key or as the additional condition).
     * <p> Note that symmetry is not required, i.e. the additional conditions do not have to list the same conditional implication.
     * @return multi-map, where the keys are additional conditions and the values are input key implications jointly implied by the condition and the given implying key.
     * @since 1.6
     */
    SetMultimap<InputKeyImplication, InputKeyImplication> getConditionalImplications(IInputKey implyingKey);
	
	/**
	 * Returns functional dependencies of the input key expressed in terms of column indices.
	 * 
	 * <p> Each entry of the map is a functional dependency rule, where the entry key specifies source columns and the entry value specifies target columns. 
	 */
	Map<Set<Integer>, Set<Integer>> getFunctionalDependencies(IInputKey key);
	
	/**
	 * For query normalizing, this is the order suggested for trying to eliminate input keys.
	 * @since 1.6
	 */
	Comparator<IInputKey> getSuggestedEliminationOrdering();
	
	/**
	 * Tells whether the given {@link IInputKey} is an edge and may lead out of scope.
	 * 
	 * @since 1.6
	 */
	boolean canLeadOutOfScope(IInputKey key);
}
