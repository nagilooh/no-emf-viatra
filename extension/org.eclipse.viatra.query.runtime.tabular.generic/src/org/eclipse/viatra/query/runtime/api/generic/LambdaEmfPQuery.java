package org.eclipse.viatra.query.runtime.api.generic;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.viatra.query.runtime.api.impl.BaseGeneratedEMFPQuery;
import org.eclipse.viatra.query.runtime.emf.types.EClassTransitiveInstancesKey;
import org.eclipse.viatra.query.runtime.emf.types.EStructuralFeatureInstancesKey;
import org.eclipse.viatra.query.runtime.matchers.backend.QueryEvaluationHint;
import org.eclipse.viatra.query.runtime.matchers.context.IInputKey;
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

public class LambdaEmfPQuery extends BaseGeneratedEMFPQuery {

	public interface PatternOneParam {
		PQuery build(String parameter);
	}

	public interface PatternTwoParams {
		PQuery build(String parameter1, String parameter2);
	}

	public interface PatternThreeParams {
		PQuery build(String parameter1, String parameter2, String parameter3);
	}

	public interface PatternFourParams {
		PQuery build(String parameter1, String parameter2, String parameter3, String parameter4);
	}

	private List<PParameter> embeddedParameters = null;
	private String fullyQualifiedName;
	private LinkedList<PBody> bodies = new LinkedList<PBody>();

	public LambdaEmfPQuery(String fullyQualifiedName) {
		super(PVisibility.PUBLIC);
		this.fullyQualifiedName = fullyQualifiedName;
	}

	@Override
	public String getFullyQualifiedName() {
		return fullyQualifiedName;
	}

	public LambdaEmfPQuery pattern(PatternOneParam p) {
		PParameter parameter1 = new PParameter("p1", "String", (IInputKey) null, PParameterDirection.INOUT);
		embeddedParameters = Arrays.asList(parameter1);
		bodies.add(new PBody(this));
		p.build("var_1");

		for (PBody body : bodies) {
			body.setSymbolicParameters(Arrays.<ExportedParameter>asList(
					new ExportedParameter(body, body.getOrCreateVariableByName("var_1"), parameter1)));
		}
		return this;
	}

	public LambdaEmfPQuery pattern(PatternTwoParams p) {
		PParameter parameter1 = new PParameter("p1", "String", (IInputKey) null, PParameterDirection.INOUT);
		PParameter parameter2 = new PParameter("p2", "String", (IInputKey) null, PParameterDirection.INOUT);
		embeddedParameters = Arrays.asList(parameter1, parameter2);
		bodies.add(new PBody(this));
		p.build("var_1", "var_2");

		for (PBody body : bodies) {
			body.setSymbolicParameters(Arrays.<ExportedParameter>asList(
					new ExportedParameter(body, body.getOrCreateVariableByName("var_1"), parameter1),
					new ExportedParameter(body, body.getOrCreateVariableByName("var_2"), parameter2)));
		}
		return this;
	}

	public LambdaEmfPQuery pattern(PatternThreeParams p) {
		PParameter parameter1 = new PParameter("p1", "String", (IInputKey) null, PParameterDirection.INOUT);
		PParameter parameter2 = new PParameter("p2", "String", (IInputKey) null, PParameterDirection.INOUT);
		PParameter parameter3 = new PParameter("p3", "String", (IInputKey) null, PParameterDirection.INOUT);
		embeddedParameters = Arrays.asList(parameter1, parameter2, parameter3);
		bodies.add(new PBody(this));
		p.build("var_1", "var_2", "var_3");

		for (PBody body : bodies) {
			body.setSymbolicParameters(Arrays.<ExportedParameter>asList(
					new ExportedParameter(body, body.getOrCreateVariableByName("var_1"), parameter1),
					new ExportedParameter(body, body.getOrCreateVariableByName("var_2"), parameter2),
					new ExportedParameter(body, body.getOrCreateVariableByName("var_3"), parameter3)));
		}
		return this;
	}

	public LambdaEmfPQuery pattern(PatternFourParams p) {
		PParameter parameter1 = new PParameter("p1", "String", (IInputKey) null, PParameterDirection.INOUT);
		PParameter parameter2 = new PParameter("p2", "String", (IInputKey) null, PParameterDirection.INOUT);
		PParameter parameter3 = new PParameter("p3", "String", (IInputKey) null, PParameterDirection.INOUT);
		PParameter parameter4 = new PParameter("p4", "String", (IInputKey) null, PParameterDirection.INOUT);
		embeddedParameters = Arrays.asList(parameter1, parameter2, parameter3, parameter4);
		bodies.add(new PBody(this));
		p.build("var_1", "var_2", "var_3", "var_4");

		for (PBody body : bodies) {
			body.setSymbolicParameters(Arrays.<ExportedParameter>asList(
					new ExportedParameter(body, body.getOrCreateVariableByName("var_1"), parameter1),
					new ExportedParameter(body, body.getOrCreateVariableByName("var_2"), parameter2),
					new ExportedParameter(body, body.getOrCreateVariableByName("var_3"), parameter3),
					new ExportedParameter(body, body.getOrCreateVariableByName("var_4"), parameter4)));
		}
		return this;
	}

	// Create new Body
	public LambdaEmfPQuery or() {
		PBody body = new PBody(this);
		bodies.add(body);
		return this;
	}

	// Type constraint
	public LambdaEmfPQuery constraint(String packageUri, String classifierName, String name) {
		PBody body = bodies.peekLast();
		PVariable var = body.getOrCreateVariableByName(name);
		new TypeConstraint(body, Tuples.flatTupleOf(var),
				new EClassTransitiveInstancesKey((EClass) getClassifierLiteral(packageUri, classifierName)));
		return this;
	}

	// Relation constraint
	public LambdaEmfPQuery constraint(String packageUri, String className, String featureName, String sourceName,
			String targetName) {
		PBody body = bodies.peekLast();
		PVariable var_source = body.getOrCreateVariableByName(sourceName);
		PVariable var_target = body.getOrCreateVariableByName(targetName);
		new TypeConstraint(body, Tuples.flatTupleOf(var_source, var_target),
				new EStructuralFeatureInstancesKey(getFeatureLiteral(packageUri, className, featureName)));
		return this;
	}

	// Equality constraint
	public LambdaEmfPQuery equality(String sourceName, String targetName) {
		PBody body = bodies.peekLast();
		PVariable var_source = body.getOrCreateVariableByName(sourceName);
		PVariable var_target = body.getOrCreateVariableByName(targetName);
		new Equality(body, var_source, var_target);
		return this;
	}

	// Inequality constraint
	public LambdaEmfPQuery inequality(String sourceName, String targetName) {
		PBody body = bodies.peekLast();
		PVariable var_source = body.getOrCreateVariableByName(sourceName);
		PVariable var_target = body.getOrCreateVariableByName(targetName);
		new Inequality(body, var_source, var_target);
		return this;
	}

	// Positive pattern call
	public LambdaEmfPQuery patternCall(PQuery query, String... names) {
		PBody body = bodies.peekLast();
		PVariable[] vars = new PVariable[names.length];
		for (int i = 0; i < names.length; i++) {
			vars[i] = body.getOrCreateVariableByName(names[i]);
		}
		new PositivePatternCall(body, Tuples.flatTupleOf(vars), query);
		return this;
	}

	// Negative pattern call
	public LambdaEmfPQuery negativePatternCall(PQuery query, String... names) {
		PBody body = bodies.peekLast();
		PVariable[] vars = new PVariable[names.length];
		for (int i = 0; i < names.length; i++) {
			vars[i] = body.getOrCreateVariableByName(names[i]);
		}
		new NegativePatternCall(body, Tuples.flatTupleOf(vars), query);
		return this;
	}

	// Binary transitive closure pattern call
	public LambdaEmfPQuery binaryTransitiveClosure(PQuery query, String sourceName, String targetName) {
		PBody body = bodies.peekLast();
		PVariable var_source = body.getOrCreateVariableByName(sourceName);
		PVariable var_target = body.getOrCreateVariableByName(targetName);
		new BinaryTransitiveClosure(body, Tuples.flatTupleOf(var_source, var_target), query);
		return this;
	}

	// Binary reflexive transitive closure pattern call
	public LambdaEmfPQuery binaryReflexiveTransitiveClosure(PQuery query, String sourceName, String targetName) {
		PBody body = bodies.peekLast();
		PVariable var_source = body.getOrCreateVariableByName(sourceName);
		PVariable var_target = body.getOrCreateVariableByName(targetName);
		new BinaryReflexiveTransitiveClosure(body, Tuples.flatTupleOf(var_source, var_target), query,
				query.getParameters().get(0).getDeclaredUnaryType());
		return this;
	}

	@Override
	public List<PParameter> getParameters() {
		return embeddedParameters;
	}

	@Override
	public Set<PBody> doGetContainedBodies() {
		setEvaluationHints(new QueryEvaluationHint(null, QueryEvaluationHint.BackendRequirement.UNSPECIFIED));
		return new LinkedHashSet<PBody>(bodies);
	}

}
