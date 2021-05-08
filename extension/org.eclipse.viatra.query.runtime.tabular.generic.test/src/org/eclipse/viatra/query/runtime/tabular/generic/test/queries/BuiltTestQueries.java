package org.eclipse.viatra.query.runtime.tabular.generic.test.queries;

import org.eclipse.viatra.query.runtime.api.generic.LambdaEmfPQuery;

import hu.bme.mit.inf.friends.Person;

public class BuiltTestQueries {

	private static final BuiltTestQueries INSTANCE = new BuiltTestQueries();

	static LambdaEmfPQuery typeConstraintTest;
	static LambdaEmfPQuery relationConstraintTest;
	static LambdaEmfPQuery findTest;
	static LambdaEmfPQuery negFindTest;
	static LambdaEmfPQuery equalityTest;
	static LambdaEmfPQuery inequalityTest;
	static LambdaEmfPQuery binaryTransitiveClosureTest;
	static LambdaEmfPQuery binaryReflexiveTransitiveClosureTest;
	
	private BuiltTestQueries() {
		initTypeConstraintTest();
		initRelationConstraintTest();
		initFindTest();
		initNegFindTest();
		initEqualityTest();
		initInequalityTest();
		initBinaryTransitiveClosureTest();
		initBinaryReflexiveTransitiveClosureTest();
	}
	
	public static BuiltTestQueries instance() {
		return INSTANCE;
	}
	
	public LambdaEmfPQuery getTypeConstraintTest() {
		return typeConstraintTest;
	}
	
	public LambdaEmfPQuery getRelationConstraintTest() {
		return relationConstraintTest;
	}
	
	public LambdaEmfPQuery getFindTest() {
		return findTest;
	}
	
	public LambdaEmfPQuery getNegFindTest() {
		return negFindTest;
	}
	
	public LambdaEmfPQuery getEqualityTest() {
		return equalityTest;
	}
	
	public LambdaEmfPQuery getInequalityTest() {
		return inequalityTest;
	}
	
	public LambdaEmfPQuery getBinaryTransitiveClosureTest() {
		return binaryTransitiveClosureTest;
	}
	
	public LambdaEmfPQuery getBinaryReflexiveTransitiveClosureTest() {
		return binaryReflexiveTransitiveClosureTest;
	}

	private void initTypeConstraintTest() {
		typeConstraintTest = new LambdaEmfPQuery("TypeConstraint");
		typeConstraintTest.pattern((p1) -> typeConstraintTest
				.constraint(Person.class.getTypeName(), "http://www.inf.mit.bme.hu/friends", "Person", p1));
	}

	private void initRelationConstraintTest() {
		relationConstraintTest = new LambdaEmfPQuery("RelationConstraint");
		relationConstraintTest.pattern((p1, p2) -> relationConstraintTest
				.constraint(Person.class.getTypeName(), "http://www.inf.mit.bme.hu/friends", "Person", p1)
				.constraint(Person.class.getTypeName(),"http://www.inf.mit.bme.hu/friends", "Person", p2)
				.constraint(Person.class.getTypeName(),"http://www.inf.mit.bme.hu/friends", "Person", "friend", p1, p2));
	}

	private void initFindTest() {
		findTest = new LambdaEmfPQuery("Find");
		findTest.pattern((p1, p2) -> findTest
				.constraint(Person.class.getTypeName(), "http://www.inf.mit.bme.hu/friends", "Person", p1)
				.constraint(Person.class.getTypeName(), "http://www.inf.mit.bme.hu/friends", "Person", p2)
				.patternCall(getRelationConstraintTest(), p1, p2));
	}

	private void initNegFindTest() {
		negFindTest = new LambdaEmfPQuery("NegFind");
		negFindTest.pattern((p1, p2) -> negFindTest
				.exportParameters(p1)
				.constraint(Person.class.getTypeName(), "http://www.inf.mit.bme.hu/friends", "Person", p1)
				.negativePatternCall(getRelationConstraintTest(), p1, p2));
	}

	private void initEqualityTest() {
		equalityTest = new LambdaEmfPQuery("Equality");
		equalityTest.pattern((p1, p2) -> equalityTest
				.constraint(Person.class.getTypeName(), "http://www.inf.mit.bme.hu/friends", "Person", p1)
				.constraint(Person.class.getTypeName(), "http://www.inf.mit.bme.hu/friends", "Person", p2)
				.equality(p1,  p2));
	}

	private void initInequalityTest() {
		inequalityTest = new LambdaEmfPQuery("Inequality");
		inequalityTest.pattern((p1, p2, p3) -> inequalityTest
				.exportParameters(p1, p2)
				.constraint(Person.class.getTypeName(), "http://www.inf.mit.bme.hu/friends", "Person", p1)
				.constraint(Person.class.getTypeName(), "http://www.inf.mit.bme.hu/friends", "Person", p2)
				.patternCall(getRelationConstraintTest(), p1, p3)
				.patternCall(getRelationConstraintTest(), p2, p3)
				.inequality(p1,  p2));
	}

	private void initBinaryTransitiveClosureTest() {
		binaryTransitiveClosureTest = new LambdaEmfPQuery("BinaryTransitiveClosure");
		binaryTransitiveClosureTest.pattern((p1, p2) -> binaryTransitiveClosureTest
				.constraint(Person.class.getTypeName(), "http://www.inf.mit.bme.hu/friends", "Person", p1)
				.constraint(Person.class.getTypeName(), "http://www.inf.mit.bme.hu/friends", "Person", p2)
				.binaryTransitiveClosure(getRelationConstraintTest(), p1, p2));
	}

	private void initBinaryReflexiveTransitiveClosureTest() {
		binaryReflexiveTransitiveClosureTest = new LambdaEmfPQuery("BinaryReflexiveTransitiveClosure");
		binaryReflexiveTransitiveClosureTest.pattern((p1, p2) -> binaryReflexiveTransitiveClosureTest
				.constraint(Person.class.getTypeName(), "http://www.inf.mit.bme.hu/friends", "Person", p1)
				.constraint(Person.class.getTypeName(), "http://www.inf.mit.bme.hu/friends", "Person", p2)
				.binaryReflexiveTransitiveClosure(getRelationConstraintTest(), p1, p2));
	}
	
	

}
