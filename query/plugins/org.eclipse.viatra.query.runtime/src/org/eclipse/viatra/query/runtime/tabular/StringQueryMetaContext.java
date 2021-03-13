/*******************************************************************************
 * Copyright (c) 2010-2015, Bergmann Gabor, Istvan Rath and Daniel Varro
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-v20.html.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.viatra.query.runtime.tabular;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.viatra.query.runtime.emf.types.EStructuralFeatureInstancesKey;
import org.eclipse.viatra.query.runtime.matchers.context.AbstractQueryMetaContext;
import org.eclipse.viatra.query.runtime.matchers.context.IInputKey;
import org.eclipse.viatra.query.runtime.matchers.context.InputKeyImplication;
import org.eclipse.viatra.query.runtime.tabular.types.BaseStringTypeKey;
import org.eclipse.viatra.query.runtime.tabular.types.StringStructuralFeatureInstancesKey;

/**
 * The meta context information for EMF scopes.
 * 
 * <p> The runtime context may specialize answers with a given scope. 
 * In a static context, a conservative default version ({@link #DEFAULT}) can be used instead. 
 * 
 * <p> TODO generics? 
 * @author Bergmann Gabor
 *
 */
public final class StringQueryMetaContext extends AbstractQueryMetaContext {

	@Override
	public boolean isEnumerable(IInputKey key) {		
        ensureValidKey(key);
        return key.isEnumerable();
	}

	@Override
	public boolean isStateless(IInputKey key) {
        ensureValidKey(key);
        return key instanceof BaseStringTypeKey<?>;
	}

	@Override
	public Collection<InputKeyImplication> getImplications(IInputKey implyingKey) {
        ensureValidKey(implyingKey);
        return new HashSet<InputKeyImplication>();
	}		

	@Override
    public Map<Set<Integer>, Set<Integer>> getFunctionalDependencies(IInputKey key) {
        ensureValidKey(key);
        if (key instanceof StringStructuralFeatureInstancesKey) {
            return new HashMap<Set<Integer>, Set<Integer>>();
        } else {
            return Collections.emptyMap();
        }
    }

    public void ensureValidKey(IInputKey key) {
        if (! (key instanceof BaseStringTypeKey<?>))
            illegalInputKey(key);
    }

    public void illegalInputKey(IInputKey key) {
        throw new IllegalArgumentException("The input key " + key + " is not a valid String input key.");
    }
	
}
