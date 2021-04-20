package org.eclipse.viatra.query.runtime.api.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.viatra.query.runtime.api.impl.BaseGeneratedEMFPQuery;
import org.eclipse.viatra.query.runtime.emf.types.EClassTransitiveInstancesKey;
import org.eclipse.viatra.query.runtime.emf.types.EStructuralFeatureInstancesKey;
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
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PParameter;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PParameterDirection;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PQuery;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PVisibility;
import org.eclipse.viatra.query.runtime.matchers.tuple.Tuples;
import org.eclipse.viatra.query.runtime.tabular.generic.types.StringExactInstancesKey;

public class EmfPQuery extends BaseGeneratedEMFPQuery {

    private final Map<String, PParameter> parameters = new HashMap<String, PParameter>();
    private String fullyQualifiedName;
    private Queue<PBody> bodies = new LinkedList<PBody>();
    private List<ExportedParameter> symbolicParameters;
	
    public EmfPQuery(String fullyQualifiedName) {
      super(PVisibility.PUBLIC);
      this.fullyQualifiedName = fullyQualifiedName;
    }

	@Override
	public String getFullyQualifiedName() {
		return fullyQualifiedName;
	}
	
	public EmfPQuery addParameter(String name, String packageURI, String classifierName, String typeName) {
		PParameter parameter = new PParameter(name, typeName, new EClassTransitiveInstancesKey((EClass)getClassifierLiteralSafe(packageURI, classifierName)), PParameterDirection.INOUT);
		parameters.put(name,  parameter);
		return this;
	}

	@Override
	public List<PParameter> getParameters() {
		return new ArrayList<PParameter>(parameters.values());
	}
	
	// Type constraint
	public EmfPQuery addConstraint(String packageUri, String classifierName, String name) {
		PBody body = bodies.peek();
		PVariable var = body.getOrCreateVariableByName(name);
		new TypeConstraint(body, Tuples.flatTupleOf(var), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral(packageUri, classifierName)));
		return this;
	}
	
	// Relation constraint
	public EmfPQuery addConstraint(String packageUri, String className, String featureName, String sourceName, String targetName) {
		PBody body = bodies.peek();
		PVariable var_source = body.getOrCreateVariableByName(sourceName);
		PVariable var_target = body.getOrCreateVariableByName(targetName);
		new TypeConstraint(body, Tuples.flatTupleOf(var_source, var_target), new EStructuralFeatureInstancesKey(getFeatureLiteral(packageUri, className, featureName)));
		return this;
	}
	
	// Create new Body
	public EmfPQuery addBody() {
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
	public EmfPQuery addEquality(String sourceName, String targetName) {
		PBody body = bodies.peek();
		PVariable var_source = body.getOrCreateVariableByName(sourceName);
		PVariable var_target = body.getOrCreateVariableByName(targetName);
		new Equality(body, var_source, var_target);
		return this;
	}
	
	// Inequality constraint
	public EmfPQuery addInequality(String sourceName, String targetName) {
		PBody body = bodies.peek();
		PVariable var_source = body.getOrCreateVariableByName(sourceName);
		PVariable var_target = body.getOrCreateVariableByName(targetName);
		new Inequality(body, var_source, var_target);
		return this;
	}
	
	// Positive pattern call
	public EmfPQuery addPatternCall(PQuery query, String... names) {
		PBody body = bodies.peek();
		PVariable[] vars = new PVariable[names.length];
		for (int i = 0; i < names.length; i++) {
			vars[i] = body.getOrCreateVariableByName(names[i]);
		}
		new PositivePatternCall(body, Tuples.flatTupleOf(vars), query);
		return this;
	}
	
	// Negative pattern call
	public EmfPQuery addNegativePatternCall(PQuery query, String... names) {
		PBody body = bodies.peek();
		PVariable[] vars = new PVariable[names.length];
		for (int i = 0; i < names.length; i++) {
			vars[i] = body.getOrCreateVariableByName(names[i]);
		}
		new NegativePatternCall(body, Tuples.flatTupleOf(vars), query);
		return this;
	}
	
	// Binary transitive closure pattern call
	public EmfPQuery addBinaryTransitiveClosure(PQuery query, String sourceName, String targetName) {
		PBody body = bodies.peek();
		PVariable var_source = body.getOrCreateVariableByName(sourceName);
		PVariable var_target = body.getOrCreateVariableByName(targetName);
		new BinaryTransitiveClosure(body, Tuples.flatTupleOf(var_source, var_target), query);
		return this;
	}
	
	// Binary reflexive transitive closure pattern call
	public EmfPQuery addBinaryReflexiveTransitiveClosure(PQuery query, String sourceName, String targetName) {
		PBody body = bodies.peek();
		PVariable var_source = body.getOrCreateVariableByName(sourceName);
		PVariable var_target = body.getOrCreateVariableByName(targetName);
		new BinaryReflexiveTransitiveClosure(body, Tuples.flatTupleOf(var_source, var_target), query, query.getParameters().get(0).getDeclaredUnaryType());
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
