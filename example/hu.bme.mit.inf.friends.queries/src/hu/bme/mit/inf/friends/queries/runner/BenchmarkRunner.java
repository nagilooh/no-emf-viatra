package hu.bme.mit.inf.friends.queries.runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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
import org.eclipse.viatra.query.runtime.tabular.TabularIndexHost.TabularIndexScope;
import org.eclipse.viatra.query.runtime.tabular.generic.GenricIndexHost;

import hu.bme.mit.inf.friends.FriendsFactory;
import hu.bme.mit.inf.friends.People;
import hu.bme.mit.inf.friends.Person;
import hu.bme.mit.inf.friends.queries.Friend;
import hu.bme.mit.inf.friends.queries.FriendCircle;
import hu.bme.mit.inf.friends.queries.HandCraftedFriend;
import hu.bme.mit.inf.friends.queries.HandCraftedFriendCircle;
import hu.bme.mit.inf.friends.queries.HandCraftedQueries;
import hu.bme.mit.inf.friends.queries.Queries;

public class BenchmarkRunner {

//	Change these variables
//	------------------------------------------------------------

	// Each "pair" of numbers refers to the number of "Person" elements and the number of "Friend" relations in a model: {"Person" elements, "Friend" relations}
	// The benchmark iterates through all of these sizes
	static int[][] model_size = {
			{50000, 30000},
			{50000, 40000},
			{100000, 40000},
			{100000, 80000},
			{120000, 40000},
			{120000, 80000},
	};
	
	// The number of iterations on each size of model
	// All iterations of a given size use the exact same model
	static int ITERS = 10;

	// Number of iterations used as warmup without printing or exporting the results
	static int WARMUP_ROUNDS = 20;
	
	// Model size used for warmup rounds
	// For every warmup round a new model is generated with this size
	static int[] warmup_size = {50000, 30000};

	// Path to the csv file used for export
	// The folder containing the file must exist
	// The contents of the files are overwritten at each run 
	// This can be changed by adding a 'true' parameter to the "writeToCsvFile" method call
	// or changing the default value in "CsvWriterSimple.java"
	static String outputPath = "c:/test/monitor.csv";

	// Path to the xmi file used for saving the EMF model
	// This should be in the project folder (e.g. "C:/git/org.eclipse.viatra/example/hu.bme.mit.inf.friends.queries/benchmark.xmi")
	static String emfModelPath = "<PATH TO PROJECT>/hu.bme.mit.inf.friends.queries/benchmark.xmi";
	
	// Set to true to run EMF benchmark
	static boolean RUN_EMF = true;
	

	// Set to true to run Tabular benchmark
	static boolean RUN_TABULAR = true;
	
//	------------------------------------------------------------
	
	static Resource resource;
	
	static GenricIndexHost stringifiedndexHost;
	
	static Random rand = new Random();
	static URI uri;
	static ArrayList<String> people;
	static ArrayList<Integer> friends;
	
	static double tabular_load = 0;
	static double tabular_engine = 0;
	static double tabular_prepare = 0;
	static double tabular_friend = 0;
	static double tabular_friend_circle = 0;
	static double tabular_add_person = 0;
	static double tabular_add_friend = 0;
	static double tabular_remove_friend = 0;
	static double tabular_remove_person = 0;
	static double emf_load = 0;
	static double emf_engine = 0;
	static double emf_prepare = 0;
	static double emf_friend = 0;
	static double emf_friend_circle = 0;
	static double emf_add_person = 0;
	static double emf_add_friend = 0;
	static double emf_remove_friend = 0;
	static double emf_remove_person = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
//		try {
//			bf.readLine();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
        CsvWriter writer = new CsvWriter();
        
        int runTypes = 0;
        for (boolean b : new boolean[] {RUN_EMF, RUN_TABULAR}) {
        	if (b) {
        		runTypes++;
        	}
        }
        int rowWidth = runTypes * 4 + 3;
        String[] header = new String[rowWidth];
        int row_index = 0;
        header[row_index++] = "Name";
        header[row_index++] = "Number of people";
        header[row_index++] = "Number of friends";
        if (RUN_EMF) {
        	header[row_index++] = "EMF_Load";
        	header[row_index++] = "EMF_Prepare";
        	header[row_index++] = "EMF_Friend";
        	header[row_index++] = "EMF_FriendCircle";
        }
        if (RUN_TABULAR) {
        	header[row_index++] = "Tabular_Load";
        	header[row_index++] = "Tabular_Prepare";
        	header[row_index++] = "Tabular_Friend";
        	header[row_index++] = "Tabular_FriendCircle";
        }
        File outputFile = new File(outputPath);
        writer.writeToCsvFile(header, outputFile);
        
        // Warm up
        for (int i = 0; i < WARMUP_ROUNDS; i++) {
        	System.out.println("Running warmup round " + (i + 1) + " out of " + WARMUP_ROUNDS);
			createSocialNetwork(warmup_size[0], warmup_size[1]);
			setupAndSaveEMFInstanceResource();
        	if (RUN_EMF) {
	        	runEMF();
			}
        	if (RUN_TABULAR) {
        		runTabular();
        	}
        }
        
		int runId = 1;
		for (int i = 0; i < model_size.length; i++) {
			createSocialNetwork(model_size[i][0], model_size[i][1]);
			setupAndSaveEMFInstanceResource();
			
			for (int j = 0; j < ITERS; j++) {
				stringifiedndexHost = null;
				resource = null;

				System.gc();
				System.gc();
				System.gc();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//				try {
//					bf.readLine();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
				if (RUN_EMF) {
					runEMF();
				}
				if (RUN_TABULAR) {
					runTabular();
				}
				
				printResult(runId, model_size[i][0], model_size[i][1]);
				
				String[] row = new String[rowWidth];
				row_index = 0;
				row[row_index++] = "run_" + (runId);
				row[row_index++] = String.valueOf(model_size[i][0]);
				row[row_index++] = String.valueOf(model_size[i][1]);
		        if (RUN_EMF) {
		        	row[row_index++] = String.valueOf(emf_load);
		        	row[row_index++] = String.valueOf(emf_prepare);
		        	row[row_index++] = String.valueOf(emf_friend);
		        	row[row_index++] = String.valueOf(emf_friend_circle);
		        }
		        if (RUN_TABULAR) {
		        	row[row_index++] = String.valueOf(tabular_load);
		        	row[row_index++] = String.valueOf(tabular_prepare);
		        	row[row_index++] = String.valueOf(tabular_friend);
		        	row[row_index++] = String.valueOf(tabular_friend_circle);
		        }
		        writer.writeToCsvFile(row, outputFile, true);
		        runId++;
				
		        
				System.gc();
				System.gc();
				System.gc();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//				try {
//					bf.readLine();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}		
		}
	}
	
	private static void printResult(int iter, int people, int friends) {
		System.out.println("iter: " + iter + "\tPeople: " + people + "\tFriends: " + friends);
		
		System.out.println("Measurement\t\t\tEMF\tTabular\t\tDifference (tabular/emf)");
		System.out.printf("Load [ms]\t\t%8.4f\t%8.4f\t%8.4f\n", emf_load, tabular_load, (tabular_load / emf_load));
		System.out.printf("Engine [ms]\t\t%8.4f\t%8.4f\t%8.4f\n", emf_engine, tabular_engine, (tabular_engine / emf_engine));
		System.out.printf("Prepare [ms]\t\t%8.4f\t%8.4f\t%8.4f\n", emf_prepare, tabular_prepare, (tabular_prepare / emf_prepare));
		System.out.printf("Friend [ms]\t\t%8.4f\t%8.4f\t%8.4f\n", emf_friend, tabular_friend, (tabular_friend / emf_friend));
		System.out.printf("FriendCircle [ms]\t%8.4f\t%8.4f\t%8.4f\n", emf_friend_circle, tabular_friend_circle, (tabular_friend_circle / emf_friend_circle));
//		System.out.printf("Add Person [ms]\t\t%8.4f\t%8.4f\t%8.4f\n", emf_add_person, tabular_add_person, (tabular_add_person - emf_add_person));
//		System.out.printf("Add Friend [ms]\t\t%8.4f\t%8.4f\t%8.4f\n", emf_add_friend, tabular_add_friend, (tabular_add_friend - emf_add_friend));
//		System.out.printf("Friend_2 [ms]\t\t%8.4f\t%8.4f\t%8.4f\n", emf_friend, tabular_friend, (tabular_friend - emf_friend));
//		System.out.printf("FriendCircle_2 [ms]\t%8.4f\t%8.4f\t%8.4f\n", emf_friend_circle, tabular_friend_circle, (tabular_friend_circle - emf_friend_circle));
//		System.out.printf("Remove Friend [ms]\t%8.4f\t%8.4f\t%8.4f\n", emf_remove_friend, tabular_remove_friend, (tabular_remove_friend - emf_remove_friend));
//		System.out.printf("Remove Person [ms]\t%8.4f\t%8.4f\t%8.4f\n", emf_remove_person, tabular_remove_person, (tabular_remove_person - emf_remove_person));
//		System.out.printf("Friend_3 [ms]\t\t%8.4f\t%8.4f\t%8.4f\n", emf_friend, tabular_friend, (tabular_friend - emf_friend));
//		System.out.printf("FriendCircle_3 [ms]\t%8.4f\t%8.4f\t%8.4f\n", emf_friend_circle, tabular_friend_circle, (tabular_friend_circle - emf_friend_circle));

		System.out.println("=====================================================================");
	}
	
	private static void runEMF() {
		QueryScope emfScope = initializeEMFScope();
		ViatraQueryEngine viatraQueryEngineOnEMF = createEngineOnEMF(emfScope); 
//		prepareEMF(viatraQueryEngineOnEMF);
		Collection<Friend.Match> matches_Friend = getFriendMatchesEMF(viatraQueryEngineOnEMF);
		Collection<FriendCircle.Match> matches_FriendCircle = getFriendCircleMatchesEMF(viatraQueryEngineOnEMF);
	}
	

	private static void runTabular() {
		QueryScope tabularScope = initializeTabularScope();				
		ViatraQueryEngine viatraQueryEngineOnTabular = createEngineOnTabular(tabularScope);
//		prepareTabular(viatraQueryEngineOnTabular);
		Collection<HandCraftedFriend.Match> matches_Friend = getFriendMatchesTabular(viatraQueryEngineOnTabular);
		Collection<HandCraftedFriendCircle.Match> matches_FriendCircle = getFriendCircleMatchesTabular(viatraQueryEngineOnTabular);
	}
	
	
	private static TabularIndexScope initializeTabularScope() {
		double tabular_load_start = System.nanoTime();
		SimpleLocalStorageBackend storage = new SimpleLocalStorageBackend();
		stringifiedndexHost = new GenricIndexHost(storage);
		stringifiedndexHost.initDirectInstances("Person");
		stringifiedndexHost.initFeatures("Friend");
		setupTabularInstance();
		double tabular_load_finish = System.nanoTime();
		tabular_load = (tabular_load_finish - tabular_load_start) / 1000000;
		return stringifiedndexHost.getScope();
	}

	
	private static EMFScope initializeEMFScope() {
		double emf_load_start = System.nanoTime();
		ResourceSet rs = new ResourceSetImpl();
        rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMLResourceFactoryImpl());
		resource = rs.getResource(uri, true);
		double emf_load_finish = System.nanoTime();
		emf_load = (emf_load_finish - emf_load_start) / 1000000;
		return new EMFScope(rs);
	}
	
	private static void addPersonTabular() {
		double tabular_add_person_start = System.nanoTime();
		stringifiedndexHost.addInstance("Person", String.valueOf(people.size()));
		double tabular_add_person_finish = System.nanoTime();
		tabular_add_person = (tabular_add_person_finish - tabular_add_person_start) / 1000000;
	}
	
	private static void addFriendTabular() {
		String source = "0";
		String target = String.valueOf(people.size());

		double tabular_add_friend_start = System.nanoTime();
		stringifiedndexHost.addFeature("Friend", source, target);
		stringifiedndexHost.addFeature("Friend", target, source);
		double tabular_add_friend_finish = System.nanoTime();
		tabular_add_friend = (tabular_add_friend_finish - tabular_add_friend_start) / 1000000;
	}
	
	private static void removeFriendTabular() {
		String source = "0";
		String target = String.valueOf(people.size());

		double tabular_remove_friend_start = System.nanoTime();
		stringifiedndexHost.removeFeature("Friend", source, target);
		stringifiedndexHost.removeFeature("Friend", target, source);
		double tabular_remove_friend_finish = System.nanoTime();
		tabular_remove_friend = (tabular_remove_friend_finish - tabular_remove_friend_start) / 1000000;
	}
	
	private static void removePersonTabular() {
		double tabular_remove_person_start = System.nanoTime();
		stringifiedndexHost.removeInstance("Person", String.valueOf(people.size()));
		double tabular_remove_person_finish = System.nanoTime();
		tabular_remove_person = (tabular_remove_person_finish - tabular_remove_person_start) / 1000000;
	}
	
	private static void addPersonEMF() {
		People peopleInst = (People)resource.getContents().get(0);
		
		double emf_add_person_start = System.nanoTime();
		Person person = FriendsFactory.eINSTANCE.createPerson();
		person.setName(String.valueOf(people.size()));
		peopleInst.getPerson().add(person);		
		double emf_add_person_finish = System.nanoTime();
		emf_add_person = (emf_add_person_finish - emf_add_person_start) / 1000000;
		
	}

	private static void addFriendEMF() {
		People peopleInst = (People)resource.getContents().get(0);
		EList<Person> peopleList = peopleInst.getPerson();
		Person source = peopleList.get(0);
		Person target = peopleList.get(people.size());
		
		double emf_add_friend_start = System.nanoTime();
		source.getFriend().add(target);
		target.getFriend().add(source);
		double emf_add_friend_finish = System.nanoTime();
		emf_add_friend = (emf_add_friend_finish - emf_add_friend_start) / 1000000;
	}

	private static void removeFriendEMF() {
		People peopleInst = (People)resource.getContents().get(0);
		EList<Person> peopleList = peopleInst.getPerson();
		Person source = peopleList.get(0);
		Person target = peopleList.get(people.size());
		
		double emf_remove_friend_start = System.nanoTime();
		source.getFriend().remove(target);
		target.getFriend().remove(source);
		double emf_remove_friend_finish = System.nanoTime();
		emf_remove_friend = (emf_remove_friend_finish - emf_remove_friend_start) / 1000000;
	}
	
	private static void removePersonEMF() {
		People peopleInst = (People)resource.getContents().get(0);
		EList<Person> peopleList = peopleInst.getPerson();
		Person person = peopleList.get(people.size());
		
		double emf_remove_person_start = System.nanoTime();
		peopleInst.getPerson().remove(person);		
		double emf_remove_person_finish = System.nanoTime();
		emf_remove_person = (emf_remove_person_finish - emf_remove_person_start) / 1000000;
	}
	
	private static ViatraQueryEngine createEngineOnTabular(QueryScope scope) {
		double tabular_engine_start = System.nanoTime();
		ViatraQueryEngine engine = ViatraQueryEngine.on(scope);
		double tabular_engine_finish = System.nanoTime();
		tabular_engine = (tabular_engine_finish - tabular_engine_start) / 1000000;
		return engine;
	}

	private static void prepareTabular(ViatraQueryEngine engine) {
		// Initialize all queries on engine
		double tabular_prepare_start = System.nanoTime();
		HandCraftedQueries.instance().prepare(engine);
		double tabular_prepare_finish = System.nanoTime();
		tabular_prepare = (tabular_prepare_finish - tabular_prepare_start) / 1000000;
	}
	
	private static ViatraQueryEngine createEngineOnEMF(QueryScope scope) {
		double emf_engine_start = System.nanoTime();
		ViatraQueryEngine engine = ViatraQueryEngine.on(scope);
		double emf_engine_finish = System.nanoTime();
		emf_engine = (emf_engine_finish - emf_engine_start) / 1000000;
		return engine;
	}

	private static void prepareEMF(ViatraQueryEngine engine) {
		// Initialize all queries on engine
		double emf_prepare_start = System.nanoTime();
		Queries.instance().prepare(engine);
		double emf_prepare_finish = System.nanoTime();
		emf_prepare = (emf_prepare_finish - emf_prepare_start) / 1000000;
	}
	
	private static Collection<HandCraftedFriend.Match> getFriendMatchesTabular(ViatraQueryEngine engine) {
		// Get all matches for Friend
		double tabular_friend_start = System.nanoTime();
		HandCraftedFriend.Matcher matcher = HandCraftedFriend.Matcher.on(engine);
		Collection<HandCraftedFriend.Match> matches_Friend = matcher.getAllMatches();
		double tabular_friend_finish = System.nanoTime();
		tabular_friend = (tabular_friend_finish - tabular_friend_start) / 1000000;
		return matches_Friend;
	}
	
	private static Collection<HandCraftedFriendCircle.Match> getFriendCircleMatchesTabular(ViatraQueryEngine engine) {
		// Get all matches for FriendCircle
		double tabular_friend_circle_start = System.nanoTime();
		HandCraftedFriendCircle.Matcher matcher2 = HandCraftedFriendCircle.Matcher.on(engine);
		Collection<HandCraftedFriendCircle.Match> matches_FriendCircle = matcher2.getAllMatches();
		double tabular_friend_circle_finish = System.nanoTime();
		tabular_friend_circle = (tabular_friend_circle_finish - tabular_friend_circle_start) / 1000000;
		return matches_FriendCircle;
	}

	private static void printAllFriendMatchesTabular(ViatraQueryEngine engine) {
		Collection<HandCraftedFriend.Match> matches_Friend = getFriendMatchesTabular(engine);
		
		System.out.println("Friends:");
		// Iterate over all matches for Friend
		for (HandCraftedFriend.Match match : matches_Friend) {
			// Print all the matches to the standard output
			System.out.println(match.getP1() + ", " + match.getP2());
		}
	}

	private static void printAllFriendCircleMatchesTabular(ViatraQueryEngine engine) {
		Collection<HandCraftedFriendCircle.Match> matches_FriendCircle = getFriendCircleMatchesTabular(engine);
		
		System.out.println("In friend circles:");
		// Iterate over all matches for FriendCircle
		for (HandCraftedFriendCircle.Match match : matches_FriendCircle) {
			// Print all the matches to the standard output
			System.out.println(match.getP());
		}
	}
	
	private static Collection<Friend.Match> getFriendMatchesEMF(ViatraQueryEngine engine) {
		// Get all matches for Friend
		double emf_friend_start = System.nanoTime();
		Friend.Matcher matcher = Friend.Matcher.on(engine);
		Collection<Friend.Match> matches_Friend = matcher.getAllMatches();
		double emf_friend_finish = System.nanoTime();
		emf_friend = (emf_friend_finish - emf_friend_start) / 1000000;
		return matches_Friend;
	}
	
	private static Collection<FriendCircle.Match> getFriendCircleMatchesEMF(ViatraQueryEngine engine) {
		// Get all matches for Friend
		double emf_friend_circle_start = System.nanoTime();
		FriendCircle.Matcher matcher2 = FriendCircle.Matcher.on(engine);
		Collection<FriendCircle.Match> matches_FriendCircle = matcher2.getAllMatches();
		double emf_friend_circle_finish = System.nanoTime();
		emf_friend_circle = (emf_friend_circle_finish - emf_friend_circle_start) / 1000000;
		return matches_FriendCircle;
	}

	private static void printAllFriendMatchesEMF(ViatraQueryEngine engine) {
		Collection<Friend.Match> matches_Friend = getFriendMatchesEMF(engine);
		
		System.out.println("Friends:");
		// Iterate over all matches for Friend
		for (Friend.Match match : matches_Friend) {
			// Print all the matches to the standard output
			System.out.println(match.getP1() + ", " + match.getP2());
		}
	}

	private static void printAllFriendCircleMatchesEMF(ViatraQueryEngine engine) {
		Collection<FriendCircle.Match> matches_FriendCircle = getFriendCircleMatchesEMF(engine);

		System.out.println("In friend circles:");
		// Get and iterate over all matches
		for (FriendCircle.Match match : matches_FriendCircle) {
			// Print all the matches to the standard output
			System.out.println(match.getP());
		}
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
	
	public static void setupTabularInstance() {
		for (String name : people) {
			stringifiedndexHost.addInstance("Person", name);
		}
		for (int i = 0; i < friends.size(); i += 2) {
			stringifiedndexHost.addFeature("Friend", String.valueOf(friends.get(i)), String.valueOf(friends.get(i + 1)));
		}
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
		uri = URI.createFileURI(emfModelPath);
		Resource resource = rs.createResource(uri);
		((ResourceImpl) resource).setIntrinsicIDToEObjectMap(new HashMap());
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
