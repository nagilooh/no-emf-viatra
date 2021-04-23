package hu.bme.mit.inf.friends.queries.runner;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.viatra.query.runtime.api.ViatraQueryEngine;
import org.eclipse.viatra.query.runtime.api.scope.QueryScope;
import org.eclipse.viatra.query.runtime.emf.EMFScope;
import org.eclipse.viatra.query.runtime.matchers.scopes.SimpleLocalStorageBackend;
import org.eclipse.viatra.query.runtime.matchers.scopes.tables.ITableWriterBinary;
import org.eclipse.viatra.query.runtime.matchers.scopes.tables.ITableWriterUnary;
import org.eclipse.viatra.query.runtime.tabular.generic.GenricIndexHost;
import org.eclipse.viatra.query.runtime.tabular.TabularIndexHost.TabularIndexScope;

import hu.bme.mit.inf.friends.FriendsFactory;
import hu.bme.mit.inf.friends.People;
import hu.bme.mit.inf.friends.Person;
import hu.bme.mit.inf.friends.queries.Friend;
import hu.bme.mit.inf.friends.queries.FriendCircle;
import hu.bme.mit.inf.friends.queries.HandCraftedFriend;
import hu.bme.mit.inf.friends.queries.HandCraftedFriendCircle;
import hu.bme.mit.inf.friends.queries.HandCraftedQueries;
import hu.bme.mit.inf.friends.queries.Queries;

public class BenchmarkRunnerOld {

	static int n_people = 100000;
	static int n_friends = 80000;
	
	static Random rand = new Random();
	static URI uri;
	static ArrayList<String> people = new ArrayList<>();
	static ArrayList<Integer> friends = new ArrayList<>();
	
	static GenricIndexHost stringifiedndexHost;
	static ITableWriterUnary.Table<Object> tablePerson;
	static ITableWriterBinary.Table<Object, Object> tablePerson_Friend;
	
	static Resource resource;

	static long tabular_load = 0;
	static long tabular_load_start = 0;
	static long tabular_load_finish = 0;
	static long tabular_engine = 0;
	static long tabular_engine_start = 0;
	static long tabular_engine_finish = 0;
	static long tabular_prepare = 0;
	static long tabular_prepare_start = 0;
	static long tabular_prepare_finish = 0;
	static long tabular_friend = 0;
	static long tabular_friend_start = 0;
	static long tabular_friend_finish = 0;
	static long tabular_friend_circle = 0;
	static long tabular_friend_circle_start = 0;
	static long tabular_friend_circle_finish = 0;
	static long tabular_add_person = 0;
	static long tabular_add_person_start = 0;
	static long tabular_add_person_finish = 0;
	static long tabular_add_friend = 0;
	static long tabular_add_friend_start = 0;
	static long tabular_add_friend_finish = 0;
	static long tabular_remove_friend = 0;
	static long tabular_remove_friend_start = 0;
	static long tabular_remove_friend_finish = 0;
	static long tabular_remove_person = 0;
	static long tabular_remove_person_start = 0;
	static long tabular_remove_person_finish = 0;
	static long emf_load_start = 0;
	static long emf_load = 0;
	static long emf_load_finish = 0;
	static long emf_engine = 0;
	static long emf_engine_start = 0;
	static long emf_engine_finish = 0;
	static long emf_prepare = 0;
	static long emf_prepare_start = 0;
	static long emf_prepare_finish = 0;
	static long emf_friend = 0;
	static long emf_friend_start = 0;
	static long emf_friend_finish = 0;
	static long emf_friend_circle = 0;
	static long emf_friend_circle_start = 0;
	static long emf_friend_circle_finish = 0;
	static long emf_add_person = 0;
	static long emf_add_person_start = 0;
	static long emf_add_person_finish = 0;
	static long emf_add_friend = 0;
	static long emf_add_friend_start = 0;
	static long emf_add_friend_finish = 0;
	static long emf_remove_friend = 0;
	static long emf_remove_friend_start = 0;
	static long emf_remove_friend_finish = 0;
	static long emf_remove_person = 0;
	static long emf_remove_person_start = 0;
	static long emf_remove_person_finish = 0;

	public static void main(String[] args) {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		try {
			bf.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createSocialNetwork();
		setupAndSaveEMFInstanceResource();
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(4);
		for (int i = 0; i < 20; i++) {
			System.out.println("iter " + i);
			System.out.println("Measurement\t\t\tEMF\tTabular\t\tDifference");
			
			QueryScope tabularScope = initializeTabularScope();
			tabular_load = tabular_load_finish - tabular_load_start;
			
//			QueryScope emfScope = initializeEMFScope();
			emf_load = emf_load_finish - emf_load_start;
			System.out.printf("Load [ms]\t\t%8.4f\t%8.4f\t%8.4f\n", emf_load / 1000000.0, tabular_load / 1000000.0, (tabular_load - emf_load) / 1000000.0);
			

			System.gc();
			System.gc();
			System.gc();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				bf.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ViatraQueryEngine viatraQueryEngineOnTabular = prepareQueryEngineOnTabular(tabularScope);
			tabular_engine = tabular_engine_finish - tabular_engine_start;
			tabular_prepare = tabular_prepare_finish - tabular_prepare_start;
			
//			ViatraQueryEngine viatraQueryEngineOnEMF = prepareQueryEngineOnEMF(emfScope);
			emf_engine = emf_engine_finish - emf_engine_start;
			emf_prepare = emf_prepare_finish - emf_prepare_start;
			System.out.printf("Engine [ms]\t\t%8.4f\t%8.4f\t%8.4f\n", emf_engine / 1000000.0, tabular_engine / 1000000.0, (tabular_engine - emf_engine) / 1000000.0);
			System.out.printf("Prepare [ms]\t\t%8.4f\t%8.4f\t%8.4f\n", emf_prepare / 1000000.0, tabular_prepare / 1000000.0, (tabular_prepare - emf_prepare) / 1000000.0);
			

			try {
				bf.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.gc();
			System.gc();
			System.gc();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			printAllMatchesTabular(viatraQueryEngineOnTabular);
			tabular_friend = tabular_friend_finish - tabular_friend_start;
			tabular_friend_circle = tabular_friend_circle_finish - tabular_friend_circle_start;
			
			
//			printAllMatchesEMF(viatraQueryEngineOnEMF);
			emf_friend = emf_friend_finish - emf_friend_start;
			emf_friend_circle = emf_friend_circle_finish - emf_friend_circle_start;
			System.out.printf("Friend [ms]\t\t%8.4f\t%8.4f\t%8.4f\n", emf_friend / 1000000.0, tabular_friend / 1000000.0, (tabular_friend - emf_friend) / 1000000.0);
			System.out.printf("FriendCircle [ms]\t%8.4f\t%8.4f\t%8.4f\n", emf_friend_circle / 1000000.0, tabular_friend_circle / 1000000.0, (tabular_friend_circle - emf_friend_circle) / 1000000.0);
			
			addPersonTabular();
			tabular_add_person = tabular_add_person_finish - tabular_add_person_start;
//			addPersonEMF();
			emf_add_person = emf_add_person_finish - emf_add_person_start;
			System.out.printf("Add Person [ms]\t\t%8.4f\t%8.4f\t%8.4f\n", emf_add_person / 1000000.0, tabular_add_person / 1000000.0, (tabular_add_person - emf_add_person) / 1000000.0);
			addFriendTabular();
			tabular_add_friend = tabular_add_friend_finish - tabular_add_friend_start;
//			addFriendEMF();
			emf_add_friend = emf_add_friend_finish - emf_add_friend_start;
			System.out.printf("Add Friend [ms]\t\t%8.4f\t%8.4f\t%8.4f\n", emf_add_friend / 1000000.0, tabular_add_friend / 1000000.0, (tabular_add_friend - emf_add_friend) / 1000000.0);
			
			printAllMatchesTabular(viatraQueryEngineOnTabular);
			tabular_friend = tabular_friend_finish - tabular_friend_start;
			tabular_friend_circle = tabular_friend_circle_finish - tabular_friend_circle_start;
			
//			printAllMatchesEMF(viatraQueryEngineOnEMF);
			emf_friend = emf_friend_finish - emf_friend_start;
			emf_friend_circle = emf_friend_circle_finish - emf_friend_circle_start;
			System.out.printf("Friend_2 [ms]\t\t%8.4f\t%8.4f\t%8.4f\n", emf_friend / 1000000.0, tabular_friend / 1000000.0, (tabular_friend - emf_friend) / 1000000.0);
			System.out.printf("FriendCircle_2 [ms]\t%8.4f\t%8.4f\t%8.4f\n", emf_friend_circle / 1000000.0, tabular_friend_circle / 1000000.0, (tabular_friend_circle - emf_friend_circle) / 1000000.0);
			
			removeFriendTabular();
			tabular_remove_friend = tabular_remove_friend_finish - tabular_remove_friend_start;
//			removeFriendEMF();
			emf_remove_friend = emf_remove_friend_finish - emf_remove_friend_start;
			System.out.printf("Remove Friend [ms]\t%8.4f\t%8.4f\t%8.4f\n", emf_remove_friend / 1000000.0, tabular_remove_friend / 1000000.0, (tabular_remove_friend - emf_remove_friend) / 1000000.0);
			removePersonTabular();
			tabular_remove_person = tabular_remove_person_finish - tabular_remove_person_start;
//			removePersonEMF();
			emf_remove_person = emf_remove_person_finish - emf_remove_person_start;
			System.out.printf("Remove Person [ms]\t%8.4f\t%8.4f\t%8.4f\n", emf_remove_person / 1000000.0, tabular_remove_person / 1000000.0, (tabular_remove_person - emf_remove_person) / 1000000.0);
			
//			System.out.println("Tabular Load:\t" + tabular_load / 1000000.0);
//			System.out.println("Tabular Friend:\t" + tabular_friend / 1000000.0);
//			System.out.println("Tabular FriendCircle:\t" + tabular_friend_circle / 1000000.0);
//			System.out.println("Tabular Add Person:\t" + tabular_add_person);
//			System.out.println("Tabular Add Friend:\t" + tabular_add_friend);
//			
//			System.out.println("EMF Load:\t" + emf_load / 1000000.0);
//			System.out.println("EMF Friend:\t" + emf_friend / 1000000.0);
//			System.out.println("EMF FriendCircle:\t" + emf_friend_circle / 1000000.0);
//			System.out.println("EMF Add Person:\t" + emf_add_person);
//			System.out.println("EMF Add Friend:\t" + emf_add_friend);
			

			printAllMatchesTabular(viatraQueryEngineOnTabular);
			tabular_friend = tabular_friend_finish - tabular_friend_start;
			tabular_friend_circle = tabular_friend_circle_finish - tabular_friend_circle_start;
			
//			printAllMatchesEMF(viatraQueryEngineOnEMF);
			emf_friend = emf_friend_finish - emf_friend_start;
			emf_friend_circle = emf_friend_circle_finish - emf_friend_circle_start;
			System.out.printf("Friend_2 [ms]\t\t%8.4f\t%8.4f\t%8.4f\n", emf_friend / 1000000.0, tabular_friend / 1000000.0, (tabular_friend - emf_friend) / 1000000.0);
			System.out.printf("FriendCircle_2 [ms]\t%8.4f\t%8.4f\t%8.4f\n", emf_friend_circle / 1000000.0, tabular_friend_circle / 1000000.0, (tabular_friend_circle - emf_friend_circle) / 1000000.0);
			
//			System.out.println("Tabular Friend_2: " + tabular_friend / 1000000.0);
//			System.out.println("Tabular FriendCircle_2: " + tabular_friend_circle / 1000000.0);
//			
//			System.out.println("EMF Friend_2: " + emf_friend / 1000000.0);
//			System.out.println("EMF FriendCircle_2: " + emf_friend_circle / 1000000.0);
			
			System.out.println("=====================================================================");

			System.gc();
			System.gc();
			System.gc();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				bf.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static TabularIndexScope initializeTabularScope() {
		tabular_load_start = System.nanoTime();
		SimpleLocalStorageBackend storage = new SimpleLocalStorageBackend();
		stringifiedndexHost = new GenricIndexHost(storage);
		stringifiedndexHost.initDirectInstances("Person");
		stringifiedndexHost.initFeatures("Friend");
		setupTabularInstance();
		tabular_load_finish = System.nanoTime();
		return stringifiedndexHost.getScope();
	}

	
	private static EMFScope initializeEMFScope() {
		emf_load_start = System.nanoTime();
		ResourceSet rs = new ResourceSetImpl();
        rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMLResourceFactoryImpl());
		resource = rs.getResource(uri, true);
		emf_load_finish = System.nanoTime();
		return new EMFScope(rs);
	}
	
	private static void addPersonTabular() {
		tabular_add_person_start = System.nanoTime();
		stringifiedndexHost.addInstance("Person", String.valueOf(people.size()));
//		stringifiedndexHost.addInstance("Person", "Jani");
		tabular_add_person_finish = System.nanoTime();
	}
	
	private static void addFriendTabular() {
		String source = "0";
		String target = String.valueOf(people.size());

		tabular_add_friend_start = System.nanoTime();
		stringifiedndexHost.addFeature("Friend", source, target);
		stringifiedndexHost.addFeature("Friend", target, source);
		tabular_add_friend_finish = System.nanoTime();
	}
	
	private static void removeFriendTabular() {
		String source = "0";
		String target = String.valueOf(people.size());

		tabular_remove_friend_start = System.nanoTime();
		stringifiedndexHost.removeFeature("Friend", source, target);
		stringifiedndexHost.removeFeature("Friend", target, source);
		tabular_remove_friend_finish = System.nanoTime();
	}
	
	private static void removePersonTabular() {
		tabular_remove_person_start = System.nanoTime();
		stringifiedndexHost.removeInstance("Person", String.valueOf(people.size()));
		tabular_remove_person_finish = System.nanoTime();
	}
	
	private static void addPersonEMF() {
		People peopleInst = (People)resource.getContents().get(0);
		
		emf_add_person_start = System.nanoTime();
		Person person = FriendsFactory.eINSTANCE.createPerson();
		person.setName(String.valueOf(people.size()));
		peopleInst.getPerson().add(person);		
		emf_add_person_finish = System.nanoTime();
		
	}

	private static void addFriendEMF() {
		People peopleInst = (People)resource.getContents().get(0);
		EList<Person> peopleList = peopleInst.getPerson();
		Person source = peopleList.get(0);
		Person target = peopleList.get(people.size());
		
		emf_add_friend_start = System.nanoTime();
		source.getFriend().add(target);
		target.getFriend().add(source);
		emf_add_friend_finish = System.nanoTime();
	}

	private static void removeFriendEMF() {
		People peopleInst = (People)resource.getContents().get(0);
		EList<Person> peopleList = peopleInst.getPerson();
		Person source = peopleList.get(0);
		Person target = peopleList.get(people.size());
		
		emf_remove_friend_start = System.nanoTime();
		source.getFriend().remove(target);
		target.getFriend().remove(source);
		emf_remove_friend_finish = System.nanoTime();
	}
	private static void removePersonEMF() {
		People peopleInst = (People)resource.getContents().get(0);
		EList<Person> peopleList = peopleInst.getPerson();
		Person person = peopleList.get(people.size());
		
		emf_remove_person_start = System.nanoTime();
		peopleInst.getPerson().remove(person);		
		emf_remove_person_finish = System.nanoTime();
	}

	private static ViatraQueryEngine prepareQueryEngineOnTabular(QueryScope scope) {
		// Access managed query engine

		// LocalSearch
//		QueryEvaluationHint localSearchHint = LocalSearchHints.getDefault().build();
//		ViatraQueryEngineOptions options = ViatraQueryEngineOptions.
//				defineOptions().
//				withDefaultHint(localSearchHint).
//				withDefaultBackend(localSearchHint.getQueryBackendFactory()). // this line is needed in 1.4 due to bug 507777
//				build();

//	    ViatraQueryEngine engine = ViatraQueryEngine.on(scope, options);

		// Rete
		tabular_engine_start = System.nanoTime();
		ViatraQueryEngine engine = ViatraQueryEngine.on(scope);
		tabular_engine_finish = System.nanoTime();

		// Initialize all queries on engine
		tabular_prepare_start = System.nanoTime();
		HandCraftedQueries.instance().prepare(engine);
		tabular_prepare_finish = System.nanoTime();

		return engine;
	}

	private static ViatraQueryEngine prepareQueryEngineOnEMF(QueryScope scope) {
		// Access managed query engine
		
		// LocalSearch
//		QueryEvaluationHint localSearchHint = LocalSearchHints.getDefault().build();
//		ViatraQueryEngineOptions options = ViatraQueryEngineOptions.
//				defineOptions().
//				withDefaultHint(localSearchHint).
//				withDefaultBackend(localSearchHint.getQueryBackendFactory()). // this line is needed in 1.4 due to bug 507777
//				build();

//	    ViatraQueryEngine engine = ViatraQueryEngine.on(scope, options);
		
		// Rete
		emf_engine_start = System.nanoTime();
		ViatraQueryEngine engine = ViatraQueryEngine.on(scope);
		emf_engine_finish = System.nanoTime();

		// Initialize all queries on engine
		emf_prepare_start = System.nanoTime();
		Queries.instance().prepare(engine);
		emf_prepare_finish = System.nanoTime();

		return engine;
	}

	private static void printAllMatchesTabular(ViatraQueryEngine engine) {
		// Access pattern matcher

		// Get all matches for Friend
		tabular_friend_start = System.nanoTime();
		HandCraftedFriend.Matcher matcher = HandCraftedFriend.Matcher.on(engine);
		Collection<HandCraftedFriend.Match> matches_Friend = matcher.getAllMatches();
		tabular_friend_finish = System.nanoTime();
//		System.out.println("Got all matches for Friend");
		
		
		// Get all matches for FriendCircle
		tabular_friend_circle_start = System.nanoTime();
		HandCraftedFriendCircle.Matcher matcher2 = HandCraftedFriendCircle.Matcher.on(engine);
		Collection<HandCraftedFriendCircle.Match> matches_FriendCircle = matcher2.getAllMatches();
		tabular_friend_circle_finish = System.nanoTime();
//		System.out.println("Got all matches for FriendCircle");
		
		/*
		System.out.println("Friends:");
		// Iterate over all matches for Friend
		for (HandCraftedFriend.Match match : matches_Friend) {
			// Print all the matches to the standard output
			System.out.println(match.getP1() + ", " + match.getP2());
		}

		System.out.println("In friend circles:");
		// Iterate over all matches for FriendCircle
		for (HandCraftedFriendCircle.Match match : matches_FriendCircle) {
			// Print all the matches to the standard output
			System.out.println(match.getP());
		}
		*/
	}

	private static void printAllMatchesEMF(ViatraQueryEngine engine) {
		// Access pattern matcher

		// Get all matches for Friend
		emf_friend_start = System.nanoTime();
		Friend.Matcher matcher = Friend.Matcher.on(engine);
		Collection<Friend.Match> matches_Friend = matcher.getAllMatches();
		emf_friend_finish = System.nanoTime();
//		System.out.println("Got all matches for Friend");

		// Get all matches for Friend
		emf_friend_circle_start = System.nanoTime();
		FriendCircle.Matcher matcher2 = FriendCircle.Matcher.on(engine);
		Collection<FriendCircle.Match> matches_FriendCircle = matcher2.getAllMatches();
		emf_friend_circle_finish = System.nanoTime();
//		System.out.println("Got all matches for Friend");

		/*
		System.out.println("Friends:");
		// Iterate over all matches for Friend
		for (Friend.Match match : matches_Friend) {
			// Print all the matches to the standard output
			System.out.println(match.getP1() + ", " + match.getP2());
		}

		System.out.println("In friend circles:");
		// Get and iterate over all matches
		for (FriendCircle.Match match : matches_FriendCircle) {
			// Print all the matches to the standard output
			System.out.println(match.getP());
		}
		*/
	}
	
	public static void createSocialNetwork() {
		for (int i = 0; i < n_people; i++) {
			people.add(String.valueOf(i));
		}
		for (int i = 0; i < n_friends * 2; i++) {
			friends.add(rand.nextInt(n_people));
		}
	}
	
	public static void setupTabularInstance() {
		for (String name : people) {
			stringifiedndexHost.addInstance("Person", name);
		}
//		System.out.println("Tabular People added");
		for (int i = 0; i < friends.size(); i += 2) {
			stringifiedndexHost.addFeature("Friend", String.valueOf(friends.get(i)), String.valueOf(friends.get(i + 1)));
		}
//		System.out.println("Tabular Friends added");
	}

	public static void setupAndSaveEMFInstanceResource() {
		ResourceSet rs = new ResourceSetImpl();
		Resource peopleResource = createAndAddResource("hu.bme.mit.inf.friends.queries/benchmark.xmi", new String[] {"xmi", "xml"}, rs);
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
		uri = URI.createFileURI("D:/git/org.eclipse.viatra/example/hu.bme.mit.inf.friends.queries/benchmark.xmi");
		Resource resource = rs.createResource(uri);
		((ResourceImpl) resource).setIntrinsicIDToEObjectMap(new HashMap());
		return resource;
	}

	public static void saveResource(Resource resource) {
		Map saveOptions = ((XMLResource) resource).getDefaultSaveOptions();
		saveOptions.put(XMLResource.OPTION_CONFIGURATION_CACHE, Boolean.TRUE);
		saveOptions.put(XMLResource.OPTION_USE_CACHED_LOOKUP_TABLE, new ArrayList());
		try {
			resource.save(saveOptions);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
