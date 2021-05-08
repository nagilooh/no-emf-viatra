package org.eclipse.viatra.query.runtime.api.generic;

import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PQuery;
import org.eclipse.viatra.query.runtime.matchers.psystem.rewriters.PBodyNormalizer;

public class GenericPBodyNormalizer extends PBodyNormalizer {

	public GenericPBodyNormalizer() {
		super(null);
	}
	
	@Override
    protected boolean shouldCalculateImpliedTypes(PQuery query) {
        return false;
    }

}
