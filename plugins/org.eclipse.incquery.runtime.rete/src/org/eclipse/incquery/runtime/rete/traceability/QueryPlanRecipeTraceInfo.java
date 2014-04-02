/*******************************************************************************
 * Copyright (c) 2010-2014, Bergmann Gabor, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Bergmann Gabor - initial API and implementation
 *******************************************************************************/
package org.eclipse.incquery.runtime.rete.traceability;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.incquery.runtime.matchers.planning.SubPlan;
import org.eclipse.incquery.runtime.matchers.psystem.PVariable;
import org.eclipse.incquery.runtime.rete.recipes.ReteNodeRecipe;

/**
 * A trace marker associating a Rete recipe with a query SubPlan.
 *
 * TODO: if SubPlan will be orderless (PVariable set instead of tuple), then the variable tuple must be constructed here.
 */
public class QueryPlanRecipeTraceInfo extends RecipeTraceInfo implements PatternTraceInfo {

	SubPlan subPlan;
	List<PVariable> variablesTuple;
	Map<PVariable, Integer> posMapping;
	
	public QueryPlanRecipeTraceInfo(SubPlan subPlan, List<PVariable> variablesTuple,
			ReteNodeRecipe recipe,
			Collection<? extends RecipeTraceInfo> parentRecipeTraces) {
		super(recipe, parentRecipeTraces);
		this.subPlan = subPlan;
		this.variablesTuple = variablesTuple;
		
		this.posMapping = new HashMap<PVariable, Integer>();
		for (int i = 0; i < variablesTuple.size(); ++i)
			posMapping.put(variablesTuple.get(i), i);
	}
	public QueryPlanRecipeTraceInfo(SubPlan subPlan, List<PVariable> variablesTuple,
			ReteNodeRecipe recipe,
			RecipeTraceInfo... parentRecipeTraces) {
		this(subPlan, variablesTuple, recipe, Arrays.asList(parentRecipeTraces));
	}

	public SubPlan getSubPlan() {
		return subPlan;
	}

	@Override
	public String getPatternName() {
		return subPlan.getBody().getPattern().getFullyQualifiedName();
	}
	public List<PVariable> getVariablesTuple() {
		return variablesTuple;
	}
	public Map<PVariable, Integer> getPosMapping() {
		return posMapping;
	}
	
	
	
	
	
	

}
