/*******************************************************************************
 * Copyright (c) 2010-2013, istvanrath, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   istvanrath - initial API and implementation
 *******************************************************************************/
package org.eclipse.incquery.viewers.runtime.extensions;

import java.util.List;
import java.util.Set;

import org.eclipse.incquery.viewers.runtime.model.Edge;
import org.eclipse.incquery.viewers.runtime.model.Item;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author istvanrath
 *
 */
public class SelectionHelper {

	public Set<ISelectionChangedListener> selectionChangedListeners = Sets.newHashSet();
	
	public ISelectionChangedListener trickyListener = new ISelectionChangedListener() {
		
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			for (ISelectionChangedListener l : selectionChangedListeners) {
				l.selectionChanged(new SelectionChangedEvent(event.getSelectionProvider(), unwrapElements(event.getSelection())));
			}
		}
	};
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ISelection unwrapElements(ISelection sel) {
    	List proxy = Lists.newArrayList();
    	if (sel instanceof IStructuredSelection) {
	    	for (Object e : ((IStructuredSelection)sel).toArray()) {
	    		if (e instanceof Item) {
	    			proxy.add(((Item)e).getParamObject());
	    		}
	    		else if (e instanceof Edge) {
	    			proxy.add(((Edge)e).getSource().getParamObject());
	    			proxy.add(((Edge)e).getTarget().getParamObject());
	    		}
	    	}
    	}
    	return new StructuredSelection(proxy);
    }
}
