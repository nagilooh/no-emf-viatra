package org.eclipse.viatra.query.runtime.api.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.eclipse.viatra.query.runtime.matchers.backend.QueryEvaluationHint;
import org.eclipse.viatra.query.runtime.matchers.psystem.PBody;
import org.eclipse.viatra.query.runtime.matchers.psystem.PVariable;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicdeferred.Equality;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicdeferred.ExportedParameter;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicdeferred.Inequality;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicdeferred.NegativePatternCall;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicenumerables.BinaryReflexiveTransitiveClosure;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicenumerables.BinaryTransitiveClosure;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicenumerables.PositivePatternCall;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicenumerables.TypeConstraint;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.BasePQuery;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PParameter;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PParameterDirection;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PQuery;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PVisibility;
import org.eclipse.viatra.query.runtime.matchers.tuple.Tuples;
import org.eclipse.viatra.query.runtime.tabular.generic.types.StringExactInstancesKey;
import org.eclipse.viatra.query.runtime.tabular.generic.types.StringStructuralFeatureInstancesKey;

public class TabularPQuery extends BasePQuery {

	private final Map<String, PParameter> parameters = new HashMap<String, PParameter>();
	private String fullyQualifiedName;
	private LinkedList<PBody> bodies = new LinkedList<PBody>();
	private List<ExportedParameter> symbolicParameters;

	public TabularPQuery(String fullyQualifiedName) {
		super(PVisibility.PUBLIC);
		this.fullyQualifiedName = fullyQualifiedName;
		PBody body = new PBody(this);
		bodies.add(body);
	}

	@Override
	public String getFullyQualifiedName() {
		return fullyQualifiedName;
	}

	public TabularPQuery addParameter(String name, String inputKey) {
		return addParameter(name, inputKey, "String");
	}

	public TabularPQuery addParameter(String name, String inputKey, String typeName) {
		PParameter parameter = new PParameter(name, typeName, new StringExactInstancesKey(inputKey),
				PParameterDirection.INOUT);
		parameters.put(name, parameter);
		
		PBody body = bodies.peekLast();
		List<ExportedParameter> symbolicParameters = new ArrayList<>();
		parameters.forEach((pName, pParameter) -> {
			PVariable var = body.getOrCreateVariableByName(pName);
			symbolicParameters.add(new ExportedParameter(body, var, pParameter));
		});
		body.setSymbolicParameters(symbolicParameters);
		
		return this;
	}

	@Override
	public List<PParameter> getParameters() {
		return new ArrayList<PParameter>(parameters.values());
	}

	// Type constraint
	public TabularPQuery addConstraint(String type, String name) {
		PBody body = bodies.peekLast();
		PVariable var = body.getOrCreateVariableByName(name);
		new TypeConstraint(body, Tuples.flatTupleOf(var), new StringExactInstancesKey(type));
		return this;
	}

	// Relation constraint
	public TabularPQuery addConstraint(String type, String sourceName, String targetName) {
		PBody body = bodies.peekLast();
		PVariable var_source = body.getOrCreateVariableByName(sourceName);
		PVariable var_target = body.getOrCreateVariableByName(targetName);
		new TypeConstraint(body, Tuples.flatTupleOf(var_source, var_target),
				new StringStructuralFeatureInstancesKey(type));
		return this;
	}

	// Create new Body
	public TabularPQuery or() {
		PBody body = new PBody(this);
		List<ExportedParameter> symbolicParameters = new ArrayList<>();
		parameters.forEach((name, parameter) -> {
			PVariable var = body.getOrCreateVariableByName(name);
			symbolicParameters.add(new ExportedParameter(body, var, parameter));
		});
		body.setSymbolicParameters(symbolicParameters);
		bodies.add(body);
		return this;
	}

	// Equality constraint
	public TabularPQuery addEquality(String sourceName, String targetName) {
		PBody body = bodies.peekLast();
		PVariable var_source = body.getOrCreateVariableByName(sourceName);
		PVariable var_target = body.getOrCreateVariableByName(targetName);
		new Equality(body, var_source, var_target);
		return this;
	}

	// Inequality constraint
	public TabularPQuery addInequality(String sourceName, String targetName) {
		PBody body = bodies.peekLast();
		PVariable var_source = body.getOrCreateVariableByName(sourceName);
		PVariable var_target = body.getOrCreateVariableByName(targetName);
		new Inequality(body, var_source, var_target);
		return this;
	}

	// Positive pattern call
	public TabularPQuery addPatternCall(PQuery query, String... names) {
		PBody body = bodies.peekLast();
		PVariable[] vars = new PVariable[names.length];
		for (int i = 0; i < names.length; i++) {
			vars[i] = body.getOrCreateVariableByName(names[i]);
		}
		new PositivePatternCall(body, Tuples.flatTupleOf(vars), query);
		return this;
	}

	// Negative pattern call
	public TabularPQuery addNegativePatternCall(PQuery query, String... names) {
		PBody body = bodies.peekLast();
		PVariable[] vars = new PVariable[names.length];
		for (int i = 0; i < names.length; i++) {
			vars[i] = body.getOrCreateVariableByName(names[i]);
		}
		new NegativePatternCall(body, Tuples.flatTupleOf(vars), query);
		return this;
	}

	// Binary transitive closure pattern call
	public TabularPQuery addBinaryTransitiveClosure(PQuery query, String sourceName, String targetName) {
		PBody body = bodies.peekLast();
		PVariable var_source = body.getOrCreateVariableByName(sourceName);
		PVariable var_target = body.getOrCreateVariableByName(targetName);
		new BinaryTransitiveClosure(body, Tuples.flatTupleOf(var_source, var_target), query);
		return this;
	}

	// Binary reflexive transitive closure pattern call
	public TabularPQuery addBinaryReflexiveTransitiveClosure(PQuery query, String sourceName, String targetName) {
		PBody body = bodies.peekLast();
		PVariable var_source = body.getOrCreateVariableByName(sourceName);
		PVariable var_target = body.getOrCreateVariableByName(targetName);
		new BinaryReflexiveTransitiveClosure(body, Tuples.flatTupleOf(var_source, var_target), query,
				query.getParameters().get(0).getDeclaredUnaryType());
		return this;
	}

	@Override
	public Set<PBody> doGetContainedBodies() {
		setEvaluationHints(new QueryEvaluationHint(null, QueryEvaluationHint.BackendRequirement.UNSPECIFIED));
		return new LinkedHashSet<PBody>(bodies);
	}

	public void addSymbolicParameters(ExportedParameter symbolicParameter) {
		checkMutability();
		if (symbolicParameters == null) {
			symbolicParameters = new ArrayList<>();
		}
		symbolicParameters.add(symbolicParameter);
	}

}
