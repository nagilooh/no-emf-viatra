/*******************************************************************************
 * Copyright (c) 2010-2013, Zoltan Ujhelyi, Istvan Rath and Daniel Varro
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-v20.html.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.viatra.addon.viewers.runtime.model.listeners;

import org.eclipse.viatra.addon.viewers.runtime.notation.Containment;
import org.eclipse.viatra.addon.viewers.runtime.notation.Edge;
import org.eclipse.viatra.addon.viewers.runtime.notation.Item;

/**
 * Listener interface for the appearance and disappearance of elements.
 * @author Zoltan Ujhelyi
 *
 */
public interface IViewerStateListener {

    void itemAppeared(Item item);

    void itemDisappeared(Item item);

    void containmentAppeared(Containment containment);

    void containmentDisappeared(Containment containment);

    void edgeAppeared(Edge edge);

    void edgeDisappeared(Edge edge);

}
