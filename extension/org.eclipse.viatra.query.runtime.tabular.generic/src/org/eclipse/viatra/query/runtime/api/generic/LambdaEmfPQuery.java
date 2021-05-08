package org.eclipse.viatra.query.runtime.api.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
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
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.BasePQuery;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PParameter;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PParameterDirection;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PQuery;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PVisibility;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.QueryInitializationException;
import org.eclipse.viatra.query.runtime.matchers.tuple.Tuples;

public class LambdaEmfPQuery extends BasePQuery {

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
	private String[] exportedParameterNames = null;
	private String fullyQualifiedName;
	protected LinkedList<PBody> bodies;
	private Map<String, String[]> variables;
	private GenericPBodyNormalizer normalizer;

	public LambdaEmfPQuery(String fullyQualifiedName) {
		super(PVisibility.PUBLIC);
		this.fullyQualifiedName = fullyQualifiedName;
		embeddedParameters = new ArrayList<>();
		bodies = new LinkedList<PBody>();
		variables = new HashMap<String, String[]>();
		normalizer = new GenericPBodyNormalizer();
	}

	@Override
	public String getFullyQualifiedName() {
		return fullyQualifiedName;
	}

	public PQuery pattern(PatternOneParam p) {
		String[] valirableNames = {"p1"};
		bodies.add(new PBody(this));
		p.build(valirableNames[0]);
		
		if (exportedParameterNames == null) {
			exportedParameterNames = valirableNames;
		}
		
		for (PBody body : bodies) {
			ArrayList<ExportedParameter> exportedParameters = new ArrayList<ExportedParameter>();
			for (String exportedParameterName : exportedParameterNames) {
				String[] identifier = variables.get(exportedParameterName);
				IInputKey declaredUnaryType = new EClassTransitiveInstancesKey((EClass)getClassifierLiteralSafe(identifier[1], identifier[2]));
				PParameter parameter = new PParameter(exportedParameterName, identifier[0], declaredUnaryType, PParameterDirection.INOUT);
				embeddedParameters.add(parameter);
				exportedParameters.add(new ExportedParameter(body, body.getOrCreateVariableByName(exportedParameterName), parameter));
			}
			body.setSymbolicParameters(exportedParameters);
		}
		return normalizer.rewrite(this).getQuery();
	}

	public PQuery pattern(PatternTwoParams p) {
		String[] valirableNames = {"p1", "p2"};
		bodies.add(new PBody(this));
		p.build(valirableNames[0], valirableNames[1]);
		
		if (exportedParameterNames == null) {
			exportedParameterNames = valirableNames;
		}
		
		for (PBody body : bodies) {
			ArrayList<ExportedParameter> exportedParameters = new ArrayList<ExportedParameter>();
			for (String exportedParameterName : exportedParameterNames) {
				String[] identifier = variables.get(exportedParameterName);
				IInputKey declaredUnaryType = new EClassTransitiveInstancesKey((EClass)getClassifierLiteralSafe(identifier[1], identifier[2]));
				PParameter parameter = new PParameter(exportedParameterName, identifier[0], declaredUnaryType, PParameterDirection.INOUT);
				embeddedParameters.add(parameter);
				exportedParameters.add(new ExportedParameter(body, body.getOrCreateVariableByName(exportedParameterName), parameter));
			}
			body.setSymbolicParameters(exportedParameters);
		}
		return normalizer.rewrite(this).getQuery();
	}

	public PQuery pattern(PatternThreeParams p) {
		String[] valirableNames = {"p1", "p2", "p3"};
		bodies.add(new PBody(this));
		p.build(valirableNames[0], valirableNames[1], valirableNames[2]);
		
		if (exportedParameterNames == null) {
			exportedParameterNames = valirableNames;
		}
		
		for (PBody body : bodies) {
			ArrayList<ExportedParameter> exportedParameters = new ArrayList<ExportedParameter>();
			for (String exportedParameterName : exportedParameterNames) {
				String[] identifier = variables.get(exportedParameterName);
				IInputKey declaredUnaryType = new EClassTransitiveInstancesKey((EClass)getClassifierLiteralSafe(identifier[1], identifier[2]));
				PParameter parameter = new PParameter(exportedParameterName, identifier[0], declaredUnaryType, PParameterDirection.INOUT);
				embeddedParameters.add(parameter);
				exportedParameters.add(new ExportedParameter(body, body.getOrCreateVariableByName(exportedParameterName), parameter));
			}
			body.setSymbolicParameters(exportedParameters);
		}
		return normalizer.rewrite(this).getQuery();
	}

	public PQuery pattern(PatternFourParams p) {
		String[] valirableNames = {"p1", "p2", "p3", "p4"};
		bodies.add(new PBody(this));
		p.build(valirableNames[0], valirableNames[1], valirableNames[2], valirableNames[3]);
		
		if (exportedParameterNames == null) {
			exportedParameterNames = valirableNames;
		}
		
		for (PBody body : bodies) {
			ArrayList<ExportedParameter> exportedParameters = new ArrayList<ExportedParameter>();
			for (String exportedParameterName : exportedParameterNames) {
				String[] identifier = variables.get(exportedParameterName);
				IInputKey declaredUnaryType = new EClassTransitiveInstancesKey((EClass)getClassifierLiteralSafe(identifier[1], identifier[2]));
				PParameter parameter = new PParameter(exportedParameterName, identifier[0], declaredUnaryType, PParameterDirection.INOUT);
				embeddedParameters.add(parameter);
				exportedParameters.add(new ExportedParameter(body, body.getOrCreateVariableByName(exportedParameterName), parameter));
			}
			body.setSymbolicParameters(exportedParameters);
		}
		return normalizer.rewrite(this).getQuery();
	}
	
	// Chooses which variables are exported as parameters
	// If not called, all variables are treated as parameters
	public LambdaEmfPQuery exportParameters(String... names) {
		exportedParameterNames = names;
		return this;
	}

	// Create new Body
	public LambdaEmfPQuery or() {
		PBody body = new PBody(this);
		bodies.add(body);
		return this;
	}

	// Type constraint
	public LambdaEmfPQuery constraint(String typeName, String packageUri, String classifierName, String name) {
		PBody body = bodies.peekLast();
		PVariable var = body.getOrCreateVariableByName(name);
		variables.put(name, new String[] {typeName, packageUri, classifierName});
		new TypeConstraint(body, Tuples.flatTupleOf(var),
				new EClassTransitiveInstancesKey((EClass) getClassifierLiteral(packageUri, classifierName)));
		return this;
	}

	// Relation constraint
	public LambdaEmfPQuery constraint(String typeName, String packageUri, String className, String featureName, String sourceName,
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
	
    /**
     * For parameter type retrieval only.
     * 
     * <p>If parameter type declaration is erroneous, we still get a working parameter list (without the type declaration); 
     *  the exception will be thrown again later when the body is processed.
     */
    protected EClassifier getClassifierLiteralSafe(String packageURI, String classifierName) {
        try {
            return getClassifierLiteral(packageURI, classifierName);
        } catch (QueryInitializationException e) {
            return null;
        }
    }
	
    protected EClassifier getClassifierLiteral(String packageUri, String classifierName) {
        EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(packageUri);
        if (ePackage == null) 
            throw new QueryInitializationException(
                    "Query refers to EPackage {1} not found in EPackage Registry.", 
                    new String[]{packageUri}, 
                    "Query refers to missing EPackage.", this);
        EClassifier literal = ePackage.getEClassifier(classifierName);
        if (literal == null) 
            throw new QueryInitializationException(
                    "Query refers to classifier {1} not found in EPackage {2}.", 
                    new String[]{classifierName, packageUri}, 
                    "Query refers to missing type in EPackage.", this);
        return literal;
    }

    protected EStructuralFeature getFeatureLiteral(String packageUri, String className, String featureName) {
        EClassifier container = getClassifierLiteral(packageUri, className);
        if (! (container instanceof EClass)) 
            throw new QueryInitializationException(
                    "Query refers to EClass {1} in EPackage {2} which turned out not be an EClass.", 
                    new String[]{className, packageUri}, 
                    "Query refers to missing EClass.", this);
        EStructuralFeature feature = ((EClass)container).getEStructuralFeature(featureName);
        if (feature == null) 
            throw new QueryInitializationException(
                    "Query refers to feature {1} not found in EClass {2}.", 
                    new String[]{featureName, className}, 
                    "Query refers to missing feature.", this);
        return feature;
    }
}
