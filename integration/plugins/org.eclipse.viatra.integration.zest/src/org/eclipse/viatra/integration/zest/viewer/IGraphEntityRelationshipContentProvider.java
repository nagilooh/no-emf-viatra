/*******************************************************************************
 * Copyright (c) 2010-2016, Zoltan Ujhelyi, IncQuery Labs Ltd.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-v20.html.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.viatra.integration.zest.viewer;

import org.eclipse.jface.viewers.IContentProvider;

/**
 * @author Zoltan Ujhelyi
 *
 */
public interface IGraphEntityRelationshipContentProvider extends IContentProvider, INestedGraphContentProvider {

    /**
     * Returns all content elements which represent nodes on the first level of
     * the graph. If no nodes exist, either an empty array or <code>null</code>
     * is returned.
     *
     * @return All content elements which represent nodes on the first level of
     *         the graph.
     */
    public Object[] getNodes();
    
    /**
     * Returns all content elements which represent edges.
     *
     * @param source
     *            A model object representing the source of an edge.
     * @param target
     *            A model object representing the target of an edge.
     */
    public Object[] getEdges(Object source, Object target);
    
}
