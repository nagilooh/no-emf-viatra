package org.eclipse.viatra.query.runtime.api.generic;

import org.eclipse.viatra.query.runtime.api.GenericPatternMatcher;
import org.eclipse.viatra.query.runtime.api.GenericQuerySpecification;
import org.eclipse.viatra.query.runtime.api.ViatraQueryEngine;
import org.eclipse.viatra.query.runtime.api.scope.QueryScope;
import org.eclipse.viatra.query.runtime.emf.EMFScope;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PQuery;

public class EmfQuerySpecification extends GenericQuerySpecification<GenericPatternMatcher> {

	public EmfQuerySpecification(PQuery wrappedPQuery) {
		super(wrappedPQuery);
	}

	@Override
	public Class<? extends QueryScope> getPreferredScopeClass() {
		return EMFScope.class;
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
