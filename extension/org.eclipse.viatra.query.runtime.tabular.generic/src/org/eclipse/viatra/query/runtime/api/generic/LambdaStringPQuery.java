package org.eclipse.viatra.query.runtime.api.generic;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
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
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PQuery;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PVisibility;
import org.eclipse.viatra.query.runtime.matchers.tuple.Tuples;
import org.eclipse.viatra.query.runtime.tabular.generic.types.StringExactInstancesKey;
import org.eclipse.viatra.query.runtime.tabular.generic.types.StringStructuralFeatureInstancesKey;

public class LambdaStringPQuery extends BasePQuery {

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
	protected LinkedList<PBody> bodies = new LinkedList<PBody>();
	GenericPBodyNormalizer normalizer;

	public LambdaStringPQuery(String fullyQualifiedName) {
		super(PVisibility.PUBLIC);
		this.fullyQualifiedName = fullyQualifiedName;
		normalizer = new GenericPBodyNormalizer();
	}

	@Override
	public String getFullyQualifiedName() {
		return fullyQualifiedName;
	}

	public PQuery pattern(PatternOneParam p) {
		String[] valirableNames = {"var_1"};
		bodies.add(new PBody(this));
		p.build(valirableNames[0]);

		if (embeddedParameters == null) {
			embeddedParameters = new ArrayList<PParameter>();
			for (String var : valirableNames) {
				embeddedParameters.add(new PParameter(var));
			}
		}
		for (PBody body : bodies) {
			ArrayList<ExportedParameter> exported = new ArrayList<ExportedParameter>();
			for (int i = 0; i < embeddedParameters.size(); i++) {
				exported.add(new ExportedParameter(body, body.getOrCreateVariableByName(valirableNames[i]), embeddedParameters.get(i)));
			}
			body.setSymbolicParameters(exported);
		}
		return normalizer.rewrite(this).getQuery();
	}

	public PQuery pattern(PatternTwoParams p) {
		String[] valirableNames = {"var_1", "var_2"};
		bodies.add(new PBody(this));
		p.build(valirableNames[0], valirableNames[1]);

		if (embeddedParameters == null) {
			embeddedParameters = new ArrayList<PParameter>();
			for (String var : valirableNames) {
				embeddedParameters.add(new PParameter(var));
			}
		}
		for (PBody body : bodies) {
			ArrayList<ExportedParameter> exported = new ArrayList<ExportedParameter>();
			for (int i = 0; i < embeddedParameters.size(); i++) {
				exported.add(new ExportedParameter(body, body.getOrCreateVariableByName(valirableNames[i]), embeddedParameters.get(i)));
			}
			body.setSymbolicParameters(exported);
		}
		return normalizer.rewrite(this).getQuery();
	}

	public PQuery pattern(PatternThreeParams p) {
		String[] valirableNames = {"var_1", "var_2", "var_3"};
		bodies.add(new PBody(this));
		p.build(valirableNames[0], valirableNames[1], valirableNames[2]);

		if (embeddedParameters == null) {
			embeddedParameters = new ArrayList<PParameter>();
			for (String var : valirableNames) {
				embeddedParameters.add(new PParameter(var));
			}
		}
		for (PBody body : bodies) {
			ArrayList<ExportedParameter> exported = new ArrayList<ExportedParameter>();
			for (int i = 0; i < embeddedParameters.size(); i++) {
				exported.add(new ExportedParameter(body, body.getOrCreateVariableByName(embeddedParameters.get(i).getName()), embeddedParameters.get(i)));
			}
			body.setSymbolicParameters(exported);
		}
		return normalizer.rewrite(this).getQuery();
	}

	public PQuery pattern(PatternFourParams p) {
		String[] valirableNames = {"var_1", "var_2", "var_3", "var_4"};
		bodies.add(new PBody(this));
		p.build(valirableNames[0], valirableNames[1], valirableNames[2], valirableNames[3]);

		if (embeddedParameters == null) {
			embeddedParameters = new ArrayList<PParameter>();
			for (String var : valirableNames) {
				embeddedParameters.add(new PParameter(var));
			}
		}
		for (PBody body : bodies) {
			ArrayList<ExportedParameter> exported = new ArrayList<ExportedParameter>();
			for (int i = 0; i < embeddedParameters.size(); i++) {
				exported.add(new ExportedParameter(body, body.getOrCreateVariableByName(valirableNames[i]), embeddedParameters.get(i)));
			}
			body.setSymbolicParameters(exported);
		}
		return normalizer.rewrite(this).getQuery();
	}
	
	// Chooses which variables are exported as parameters
	// If not called, all variables are treated as parameters
	public LambdaStringPQuery exportParameters(String... names) {
		embeddedParameters = new ArrayList<PParameter>();
		for (String name : names) {
			embeddedParameters.add(new PParameter(name));
		}
		return this;
	}

	// Create new Body
	public LambdaStringPQuery or() {
		PBody body = new PBody(this);
		bodies.add(body);
		return this;
	}

	// Type constraint
	public LambdaStringPQuery constraint(String type, String name) {
		PBody body = bodies.peekLast();
		PVariable var = body.getOrCreateVariableByName(name);
		new TypeConstraint(body, Tuples.flatTupleOf(var), new StringExactInstancesKey(type));
		return this;
	}

	// Relation constraint
	public LambdaStringPQuery constraint(String type, String sourceName, String targetName) {
		PBody body = bodies.peekLast();
		PVariable var_source = body.getOrCreateVariableByName(sourceName);
		PVariable var_target = body.getOrCreateVariableByName(targetName);
		new TypeConstraint(body, Tuples.flatTupleOf(var_source, var_target),
				new StringStructuralFeatureInstancesKey(type));
		return this;
	}

	// Equality constraint
	public LambdaStringPQuery equality(String sourceName, String targetName) {
		PBody body = bodies.peekLast();
		PVariable var_source = body.getOrCreateVariableByName(sourceName);
		PVariable var_target = body.getOrCreateVariableByName(targetName);
		new Equality(body, var_source, var_target);
		return this;
	}

	// Inequality constraint
	public LambdaStringPQuery inequality(String sourceName, String targetName) {
		PBody body = bodies.peekLast();
		PVariable var_source = body.getOrCreateVariableByName(sourceName);
		PVariable var_target = body.getOrCreateVariableByName(targetName);
		new Inequality(body, var_source, var_target);
		return this;
	}

	// Positive pattern call
	public LambdaStringPQuery patternCall(PQuery query, String... names) {
		PBody body = bodies.peekLast();
		PVariable[] vars = new PVariable[names.length];
		for (int i = 0; i < names.length; i++) {
			vars[i] = body.getOrCreateVariableByName(names[i]);
		}
		new PositivePatternCall(body, Tuples.flatTupleOf(vars), query);
		return this;
	}

	// Negative pattern call
	public LambdaStringPQuery negativePatternCall(PQuery query, String... names) {
		PBody body = bodies.peekLast();
		PVariable[] vars = new PVariable[names.length];
		for (int i = 0; i < names.length; i++) {
			vars[i] = body.getOrCreateVariableByName(names[i]);
		}
		new NegativePatternCall(body, Tuples.flatTupleOf(vars), query);
		return this;
	}

	// Binary transitive closure pattern call
	public LambdaStringPQuery binaryTransitiveClosure(PQuery query, String sourceName, String targetName) {
		PBody body = bodies.peekLast();
		PVariable var_source = body.getOrCreateVariableByName(sourceName);
		PVariable var_target = body.getOrCreateVariableByName(targetName);
		new BinaryTransitiveClosure(body, Tuples.flatTupleOf(var_source, var_target), query);
		return this;
	}

	// Binary reflexive transitive closure pattern call
	public LambdaStringPQuery binaryReflexiveTransitiveClosure(PQuery query, String sourceName, String targetName) {
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
