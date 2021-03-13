package org.eclipse.viatra.query.runtime.tabular;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.viatra.query.runtime.matchers.context.AbstractQueryMetaContext;
import org.eclipse.viatra.query.runtime.matchers.context.IInputKey;
import org.eclipse.viatra.query.runtime.matchers.context.InputKeyImplication;
import org.eclipse.viatra.query.runtime.tabular.types.BaseStringTypeKey;
import org.eclipse.viatra.query.runtime.tabular.types.StringStructuralFeatureInstancesKey;

/**
 * The meta context information for String scopes.
 * 
 * <p> TODO generics? 
 * @author Ficsor Attila
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
