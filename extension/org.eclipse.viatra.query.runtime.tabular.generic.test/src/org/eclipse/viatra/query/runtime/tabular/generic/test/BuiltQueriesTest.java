package org.eclipse.viatra.query.runtime.tabular.generic.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.viatra.query.runtime.api.IPatternMatch;
import org.eclipse.viatra.query.runtime.api.IQueryGroup;
import org.eclipse.viatra.query.runtime.api.ViatraQueryEngine;
import org.eclipse.viatra.query.runtime.api.ViatraQueryMatcher;
import org.eclipse.viatra.query.runtime.api.impl.BaseQuerySpecification;
import org.eclipse.viatra.query.runtime.api.scope.QueryScope;
import org.eclipse.viatra.query.runtime.emf.EMFScope;
import org.eclipse.viatra.query.runtime.tabular.generic.test.queries.BinaryReflexiveTransitiveClosureTest;
import org.eclipse.viatra.query.runtime.tabular.generic.test.queries.BinaryTransitiveClosureTest;
import org.eclipse.viatra.query.runtime.tabular.generic.test.queries.BuiltTestQuerySpecifications;
import org.eclipse.viatra.query.runtime.tabular.generic.test.queries.EqualityTest;
import org.eclipse.viatra.query.runtime.tabular.generic.test.queries.FindTest;
import org.eclipse.viatra.query.runtime.tabular.generic.test.queries.InequalityTest;
import org.eclipse.viatra.query.runtime.tabular.generic.test.queries.NegFindTest;
import org.eclipse.viatra.query.runtime.tabular.generic.test.queries.RelationConstraintTest;
import org.eclipse.viatra.query.runtime.tabular.generic.test.queries.TypeConstraintTest;

import hu.bme.mit.inf.friends.FriendsFactory;
import hu.bme.mit.inf.friends.People;
import hu.bme.mit.inf.friends.Person;

public class BuiltQueriesTest {

	static Resource resource;
	
	static Random rand = new Random();
	static URI uri;
	static ArrayList<String> people;
	static ArrayList<Integer> friends;

	// Path to the xmi file used for saving the EMF model
	// This should be in the project folder (e.g. "C:/git/org.eclipse.viatra/extension/org.eclipse.viatra.query.runtime.tabular.generic.test.queries/test-model.xmi")
	static String emfModelPath = "D:/git/org.eclipse.viatra/extension/org.eclipse.viatra.query.runtime.tabular.generic.test/test-model.xmi";

	public static void main(String[] args) throws IOException {
		createSocialNetwork(500, 300);
		setupAndSaveEMFInstanceResource();
		runTest();
	}
	
	private static void runTest() {
		QueryScope emfScope = initializeEMFScope();
		ViatraQueryEngine viatraQueryEngineOnGenearted = createEngineOnEMF(emfScope);
		ViatraQueryEngine viatraQueryEngineOnBuilt = createEngineOnEMF(emfScope);
		
		BaseQuerySpecification[][] queryPairs = {
				{TypeConstraintTest.instance(), BuiltTestQuerySpecifications.instance().getTypeConstraintTest()},
				{RelationConstraintTest.instance(), BuiltTestQuerySpecifications.instance().getRelationConstraintTest()},
				{FindTest.instance(), BuiltTestQuerySpecifications.instance().getFindTest()},
				{NegFindTest.instance(), BuiltTestQuerySpecifications.instance().getNegFindTest()},
				{EqualityTest.instance(), BuiltTestQuerySpecifications.instance().getEqualityTest()},
				{InequalityTest.instance(), BuiltTestQuerySpecifications.instance().getInequalityTest()},
				{BinaryTransitiveClosureTest.instance(), BuiltTestQuerySpecifications.instance().getBinaryTransitiveClosureTest()},
				{BinaryReflexiveTransitiveClosureTest.instance(), BuiltTestQuerySpecifications.instance().getBinaryReflexiveTransitiveClosureTest()},
		};
		
		for (BaseQuerySpecification[] pair : queryPairs) {
			compareMatches(pair[0], pair[1], viatraQueryEngineOnGenearted, viatraQueryEngineOnBuilt);
		}
	}
	

	private static boolean compareMatches(BaseQuerySpecification generated, BaseQuerySpecification built, ViatraQueryEngine viatraQueryEngineOnGenearted, ViatraQueryEngine viatraQueryEngineOnBuilt) {
		ViatraQueryMatcher<IPatternMatch> generatedMatcher = generated.getMatcher(viatraQueryEngineOnGenearted);
		ArrayList<IPatternMatch> generatedMatches = new ArrayList<>(generatedMatcher.getAllMatches());
		System.out.println(generated.getFullyQualifiedName() + ", " + built.getFullyQualifiedName());

		List<String> generatedNames = 
				generatedMatches.stream()
					.map(m->(m.prettyPrint()))
					.collect(Collectors.toList());		
		
		ViatraQueryMatcher<IPatternMatch> builtMatcher = built.getMatcher(viatraQueryEngineOnGenearted);
		ArrayList<IPatternMatch> builtMatches = new ArrayList<>(builtMatcher.getAllMatches());

		List<String> builtNames = 
				builtMatches.stream()
				.map(m->(m.prettyPrint()))
				.collect(Collectors.toList());
		
		boolean identical = listEqualsIgnoreOrder(generatedNames, builtNames);
		System.out.println(identical ? "Passed" : "Failed");
		return identical;
	}
	
	public static <T> boolean listEqualsIgnoreOrder(List<T> list1, List<T> list2) {
	    return new HashSet<>(list1).equals(new HashSet<>(list2));
	}
	
	private static EMFScope initializeEMFScope() {
		ResourceSet rs = new ResourceSetImpl();
        rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMLResourceFactoryImpl());
		resource = rs.getResource(uri, true);
		return new EMFScope(rs);
	}
	
	private static ViatraQueryEngine createEngineOnEMF(QueryScope scope) {
		ViatraQueryEngine engine = ViatraQueryEngine.on(scope);
		return engine;
	}

	private static void prepare(IQueryGroup queryGroup, ViatraQueryEngine engine) {
		// Initialize all queries on engine
		queryGroup.prepare(engine);
	}
	
	public static void createSocialNetwork(int n_people, int n_friends) {
		people = new ArrayList<>();
		friends = new ArrayList<>();
		for (int i = 0; i < n_people; i++) {
			people.add(String.valueOf(i));
		}
		for (int i = 0; i < n_friends * 2; i++) {
			friends.add(rand.nextInt(n_people));
		}
	}
	
	public static void setupAndSaveEMFInstanceResource() {
		ResourceSet rs = new ResourceSetImpl();
		Resource peopleResource = createAndAddResource("org.eclipse.viatra.query.runtime.tabular.generic.test/test-model.xmi", new String[] {"xmi", "xml"}, rs);
		People peopleInst = FriendsFactory.eINSTANCE.createPeople();
		EList<Person> personList = peopleInst.getPerson();
		for (String name : people) {
			Person person = FriendsFactory.eINSTANCE.createPerson();
			person.setName(name);
			personList.add(person);
		}
		for (int i = 0; i < friends.size(); i += 2) {
			personList.get(friends.get(i)).getFriend().add(personList.get(friends.get(i + 1)));
		}

		peopleResource.getContents().add(peopleInst);
		saveResource(peopleResource);
	}

	public static Resource createAndAddResource(String outputFile, String[] fileextensions, ResourceSet rs) {
	     for (String fileext : fileextensions) {
	         rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put(fileext, new XMLResourceFactoryImpl());
	      }		
		uri = URI.createFileURI(emfModelPath);
		Resource resource = rs.createResource(uri);
		((ResourceImpl) resource).setIntrinsicIDToEObjectMap(new HashMap<String, EObject>());
		return resource;
	}

	public static void saveResource(Resource rs) {
		Map saveOptions = ((XMLResource) rs).getDefaultSaveOptions();
		saveOptions.put(XMLResource.OPTION_CONFIGURATION_CACHE, Boolean.TRUE);
		saveOptions.put(XMLResource.OPTION_USE_CACHED_LOOKUP_TABLE, new ArrayList());
		try {
			rs.save(saveOptions);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
