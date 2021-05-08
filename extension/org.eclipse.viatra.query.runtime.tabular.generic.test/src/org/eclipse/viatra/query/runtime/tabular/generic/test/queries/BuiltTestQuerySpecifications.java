package org.eclipse.viatra.query.runtime.tabular.generic.test.queries;

import org.eclipse.viatra.query.runtime.api.GenericQueryGroup;
import org.eclipse.viatra.query.runtime.api.IQueryGroup;
import org.eclipse.viatra.query.runtime.api.generic.EmfQuerySpecification;;

public class BuiltTestQuerySpecifications {
	
	private static final BuiltTestQuerySpecifications INSTANCE = new BuiltTestQuerySpecifications();
	
	BuiltTestQueries testQueries;
	
	private static EmfQuerySpecification typeConstraintTest;
	private static EmfQuerySpecification relationConstraintTest;
	private static EmfQuerySpecification findTest;
	private static EmfQuerySpecification negFindTest;
	private static EmfQuerySpecification equalityTest;
	private static EmfQuerySpecification inequalityTest;
	private static EmfQuerySpecification binaryTransitiveClosureTest;
	private static EmfQuerySpecification binaryReflexiveTransitiveClosureTest;
	
	private static IQueryGroup queryGroup;
	
	private BuiltTestQuerySpecifications() {
		testQueries = BuiltTestQueries.instance();
		initTypeConstraintTest();
		initRelationConstraintTest();
		initFindTest();
		initNegFindTest();
		initEqualityTest();
		initInequalityTest();
		initBinaryTransitiveClosureTest();
		initBinaryReflexiveTransitiveClosureTest();
		
		initQueryGroup();
	}
	
	public IQueryGroup getQueryGroup() {
		return queryGroup;
	}
	
	private void initQueryGroup() {
		queryGroup = GenericQueryGroup.of(
				typeConstraintTest,
				relationConstraintTest,
				findTest,
				negFindTest,
				equalityTest,
				inequalityTest,
				binaryTransitiveClosureTest,
				binaryReflexiveTransitiveClosureTest
		);
	}
	
	public EmfQuerySpecification getTypeConstraintTest() {
		return typeConstraintTest;
	}
	
	public EmfQuerySpecification getRelationConstraintTest() {
		return relationConstraintTest;
	}
	
	public EmfQuerySpecification getFindTest() {
		return findTest;
	}
	
	public EmfQuerySpecification getNegFindTest() {
		return negFindTest;
	}
	
	public EmfQuerySpecification getEqualityTest() {
		return equalityTest;
	}
	
	public EmfQuerySpecification getInequalityTest() {
		return inequalityTest;
	}
	
	public EmfQuerySpecification getBinaryTransitiveClosureTest() {
		return binaryTransitiveClosureTest;
	}
	
	public EmfQuerySpecification getBinaryReflexiveTransitiveClosureTest() {
		return binaryReflexiveTransitiveClosureTest;
	}
	
	public static BuiltTestQuerySpecifications instance() {
		return INSTANCE;
	}

	private void initTypeConstraintTest() {
		typeConstraintTest = new EmfQuerySpecification(testQueries.getTypeConstraintTest());
	}

	private void initRelationConstraintTest() {
		relationConstraintTest = new EmfQuerySpecification(testQueries.getRelationConstraintTest());
	}

	private void initFindTest() {
		findTest = new EmfQuerySpecification(testQueries.getFindTest());
	}

	private void initNegFindTest() {
		negFindTest = new EmfQuerySpecification(testQueries.getNegFindTest());
	}

	private void initEqualityTest() {
		equalityTest = new EmfQuerySpecification(testQueries.getEqualityTest());
	}

	private void initInequalityTest() {
		inequalityTest = new EmfQuerySpecification(testQueries.getInequalityTest());
	}

	private void initBinaryTransitiveClosureTest() {
		binaryTransitiveClosureTest = new EmfQuerySpecification(testQueries.getBinaryTransitiveClosureTest());
	}

	private void initBinaryReflexiveTransitiveClosureTest() {
		binaryReflexiveTransitiveClosureTest = new EmfQuerySpecification(testQueries.getBinaryReflexiveTransitiveClosureTest());
	}
	
}
