package org.eclipse.viatra.query.runtime.tabular;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.viatra.query.runtime.api.scope.QueryScope;
import org.eclipse.viatra.query.runtime.emf.EMFScope;
import org.eclipse.viatra.query.runtime.matchers.context.IInputKey;
import org.eclipse.viatra.query.runtime.matchers.scopes.IStorageBackend;
import org.eclipse.viatra.query.runtime.matchers.scopes.SimpleRuntimeContext;
import org.eclipse.viatra.query.runtime.matchers.scopes.tables.ITableWriterBinary;
import org.eclipse.viatra.query.runtime.matchers.scopes.tables.ITableWriterUnary;
import org.eclipse.viatra.query.runtime.matchers.util.CollectionsFactory;
import org.eclipse.viatra.query.runtime.tabular.types.StringExactInstancesKey;
import org.eclipse.viatra.query.runtime.tabular.types.StringStructuralFeatureInstancesKey;

/**
 * Simple String-specific demo tabular index host. 
 * 
 * <p> Usage: <ul>
 * <li> First, instantiate index host with given table and relation names
 * <li> Write arbitrary content into the tables using {@link #getTableDirectInstances(String)} and {@link #getTableFeatureSlots(String)}.
 * <li> Initialize and evaluate String-based Viatra queries on the scope provided by {@link #getScope()}, as you would on any other scope (e.g. {@link EMFScope}). 
 * <ul> 
 * 
 * <p>
 * <strong>EXPERIMENTAL</strong>. This class or interface has been added as
 * part of a work in progress. There is no guarantee that this API will
 * work or that it will remain the same.
 *
 * @author Ficsor Attila
 */
public class StringifiedndexHost extends TabularIndexHost {
	
	public StringifiedndexHost(IStorageBackend storage) {
		super(storage, new SimpleRuntimeContext(new StringQueryMetaContext()));
		
//		initTables(packages);
	}
	
	public void initDirectInstances(String... types) {
		for (String type : types) {
			boolean unique = true;
			IInputKey classifierKey = new StringExactInstancesKey(type);
			
			ITableWriterUnary.Table<Object> directTable = newUnaryInputTable(classifierKey, unique);
			tableDirectInstances.put(type, directTable);
		}
	}
	
	public void initFeatures(String... features) {
		for (String feature : features) {
			IInputKey featureKey = new StringStructuralFeatureInstancesKey(feature);
			ITableWriterBinary.Table<Object, Object> featureTable = newBinaryInputTable(featureKey, true);
			tableFeatures.put(feature, featureTable);
		}
	}
	
	@Override
    protected boolean isQueryScopeEmulated(Class<? extends QueryScope> queryScopeClass) {
        return false;
    }
	
	private Map<String, ITableWriterUnary.Table<Object>> tableDirectInstances = CollectionsFactory.createMap();
	private Map<String, ITableWriterBinary.Table<Object, Object>> tableFeatures = CollectionsFactory.createMap();
	
	
	public ITableWriterUnary.Table<Object> getTableDirectInstances(String classifier) {
		return tableDirectInstances.get(classifier);
	}
	public ITableWriterBinary.Table<Object, Object> getTableFeatureSlots(String feature) {
		return tableFeatures.get(feature);
	}


	
    public Set<Entry<String, ITableWriterUnary.Table<Object>>> getAllCurrentTablesDirectInstances() {
        return Collections.unmodifiableSet(tableDirectInstances.entrySet());
    }
    public Set<Entry<String, ITableWriterBinary.Table<Object, Object>>> getAllCurrentTablesFeatures() {
        return Collections.unmodifiableSet(tableFeatures.entrySet());
    }

	
}
