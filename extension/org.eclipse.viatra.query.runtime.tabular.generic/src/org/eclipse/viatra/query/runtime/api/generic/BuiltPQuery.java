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

public class BuiltPQuery extends BasePQuery {

    private final Map<String, PParameter> parameters = new HashMap<String, PParameter>();
    private String fullyQualifiedName;
    private Queue<PBody> bodies = new LinkedList<PBody>();
    private List<ExportedParameter> symbolicParameters;
	
    public BuiltPQuery(String fullyQualifiedName) {
      super(PVisibility.PUBLIC);
      this.fullyQualifiedName = fullyQualifiedName;
    }

	@Override
	public String getFullyQualifiedName() {
		return fullyQualifiedName;
	}
		
	public BuiltPQuery addParameter(String name, String inputKey) {
		return addParameter(name, inputKey, "String");
	}
	
	public BuiltPQuery addParameter(String name, String inputKey, String typeName) {
		PParameter parameter = new PParameter(name, typeName, new StringExactInstancesKey(inputKey), PParameterDirection.INOUT);
		parameters.put(name,  parameter);
		return this;
	}

	@Override
	public List<PParameter> getParameters() {
		return new ArrayList<PParameter>(parameters.values());
	}
	
	// Type constraint
	public BuiltPQuery addConstraint(String type, String name) {
		PBody body = bodies.peek();
		PVariable var = body.getOrCreateVariableByName(name);
		new TypeConstraint(body, Tuples.flatTupleOf(var), new StringExactInstancesKey(type));
		return this;
	}
	
	// Relation constraint
	public BuiltPQuery addConstraint(String type, String sourceName, String targetName) {
		PBody body = bodies.peek();
		PVariable var_source = body.getOrCreateVariableByName(sourceName);
		PVariable var_target = body.getOrCreateVariableByName(targetName);
		new TypeConstraint(body, Tuples.flatTupleOf(var_source, var_target), new StringStructuralFeatureInstancesKey(type));
		return this;
	}
	
	// Create new Body
	public BuiltPQuery addBody() {
		PBody body = new PBody(this);
		parameters.forEach((name, parameter) -> {
			PVariable var = body.getOrCreateVariableByName(name);
			addSymbolicParameters(new ExportedParameter(body, var, parameter));
		});
		body.setSymbolicParameters(symbolicParameters);
		bodies.add(body);
		return this;
	}
	
	// Equality constraint
	public BuiltPQuery addEquality(String type, String sourceName, String targetName) {
		PBody body = bodies.peek();
		PVariable var_source = body.getOrCreateVariableByName(sourceName);
		PVariable var_target = body.getOrCreateVariableByName(targetName);
		new Equality(body, var_source, var_target);
		return this;
	}
	
	// Inequality constraint
	public BuiltPQuery addInequality(String type, String sourceName, String targetName) {
		PBody body = bodies.peek();
		PVariable var_source = body.getOrCreateVariableByName(sourceName);
		PVariable var_target = body.getOrCreateVariableByName(targetName);
		new Inequality(body, var_source, var_target);
		return this;
	}
	
	// Positive pattern call
	public BuiltPQuery addPatternCall(PQuery query, String... names) {
		PBody body = bodies.peek();
		PVariable[] vars = new PVariable[names.length];
		for (int i = 0; i < names.length; i++) {
			vars[i] = body.getOrCreateVariableByName(names[i]);
		}
		new PositivePatternCall(body, Tuples.flatTupleOf(vars), query);
		return this;
	}
	
	// Negative pattern call
	public BuiltPQuery addNegativePatternCall(PQuery query, String... names) {
		PBody body = bodies.peek();
		PVariable[] vars = new PVariable[names.length];
		for (int i = 0; i < names.length; i++) {
			vars[i] = body.getOrCreateVariableByName(names[i]);
		}
		new NegativePatternCall(body, Tuples.flatTupleOf(vars), query);
		return this;
	}
	
	// Binary transitive closure pattern call
	public BuiltPQuery addBinaryTransitiveClosure(PQuery query, String sourceName, String targetName) {
		PBody body = bodies.peek();
		PVariable var_source = body.getOrCreateVariableByName(sourceName);
		PVariable var_target = body.getOrCreateVariableByName(targetName);
		new BinaryTransitiveClosure(body, Tuples.flatTupleOf(var_source, var_target), query);
		return this;
	}
	
	// Binary reflexive transitive closure pattern call
	public BuiltPQuery addBinaryReflexiveTransitiveClosure(PQuery query, String sourceName, String targetName) {
		PBody body = bodies.peek();
		PVariable var_source = body.getOrCreateVariableByName(sourceName);
		PVariable var_target = body.getOrCreateVariableByName(targetName);
		new BinaryReflexiveTransitiveClosure(body, Tuples.flatTupleOf(var_source, var_target), query, new StringExactInstancesKey(query.getParameters().get(0).getTypeName()));
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
