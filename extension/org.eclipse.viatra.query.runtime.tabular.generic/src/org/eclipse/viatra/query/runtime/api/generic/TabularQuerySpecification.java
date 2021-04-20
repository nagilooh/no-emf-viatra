package org.eclipse.viatra.query.runtime.api.generic;

import org.eclipse.viatra.query.runtime.api.GenericPatternMatcher;
import org.eclipse.viatra.query.runtime.api.GenericQuerySpecification;
import org.eclipse.viatra.query.runtime.api.ViatraQueryEngine;
import org.eclipse.viatra.query.runtime.api.scope.QueryScope;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PQuery;
import org.eclipse.viatra.query.runtime.tabular.TabularIndexHost.TabularIndexScope;

public class TabularQuerySpecification extends GenericQuerySpecification<GenericPatternMatcher> {

	public TabularQuerySpecification(PQuery wrappedPQuery) {
		super(wrappedPQuery);
	}

	@Override
	public Class<? extends QueryScope> getPreferredScopeClass() {
		return TabularIndexScope.class;
	}

	@Override
	protected GenericPatternMatcher instantiate(ViatraQueryEngine engine) {
		return defaultInstantiate(engine);
	}
	
    @Override
    public GenericPatternMatcher instantiate() {
        return new GenericPatternMatcher(this);
    }

}
