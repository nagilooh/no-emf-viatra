/*******************************************************************************
 * Copyright (c) 2010-2019, Tamas Szabo, Istvan Rath and Daniel Varro
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-v20.html.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.viatra.query.runtime.rete.matcher;

import org.eclipse.viatra.query.runtime.matchers.backend.IQueryBackend;
import org.eclipse.viatra.query.runtime.matchers.context.IQueryBackendContext;

/**
 * A {@link ReteBackendFactory} implementation that creates {@link ReteEngine}s that use differential dataflow
 * evaluation.
 * 
 * @author Tamas Szabo
 * @since 2.2
 */
public class DifferentialReteBackendFactory extends ReteBackendFactory {

    public static final DifferentialReteBackendFactory INSTANCE = new DifferentialReteBackendFactory();

    @Override
    public IQueryBackend create(IQueryBackendContext context) {
        return create(context, false, true);
    }

    @Override
    public int hashCode() {
        return DifferentialReteBackendFactory.class.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof DifferentialReteBackendFactory)) {
            return false;
        }
        return true;
    }

}
