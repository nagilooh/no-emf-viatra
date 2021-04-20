package org.eclipse.viatra.query.runtime.api.generic;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.viatra.query.runtime.api.IQuerySpecification;
import org.eclipse.viatra.query.runtime.api.impl.BaseQueryGroup;

public class TabularQueryGroup extends BaseQueryGroup {

    protected Set<IQuerySpecification<?>> querySpecifications = new HashSet<IQuerySpecification<?>>();

    @Override
    public Set<IQuerySpecification<?>> getSpecifications() {
        return querySpecifications;
    }
	
	public void addQuerySpecification(TabularQuerySpecification querySpecification) {
		this.querySpecifications.add(querySpecification);
	}
}
