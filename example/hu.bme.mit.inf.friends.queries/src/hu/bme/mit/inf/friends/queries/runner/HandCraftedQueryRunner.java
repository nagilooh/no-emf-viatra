package hu.bme.mit.inf.friends.queries.runner;

import org.eclipse.viatra.query.runtime.api.ViatraQueryEngine;
import org.eclipse.viatra.query.runtime.api.scope.QueryScope;
import org.eclipse.viatra.query.runtime.matchers.scopes.SimpleLocalStorageBackend;
import org.eclipse.viatra.query.runtime.tabular.TabularIndexHost.TabularIndexScope;
import org.eclipse.viatra.query.runtime.tabular.generic.GenricIndexHost;

import hu.bme.mit.inf.friends.queries.HandCraftedFriend;
import hu.bme.mit.inf.friends.queries.HandCraftedFriend2;
import hu.bme.mit.inf.friends.queries.HandCraftedFriendCircle;
import hu.bme.mit.inf.friends.queries.HandCraftedQueries;

public class HandCraftedQueryRunner {
	static GenricIndexHost stringifiedndexHost;

	public static void main(String[] args) {
		QueryScope scope = initializeStringifiedScope();
		ViatraQueryEngine viatraQueryEngine = prepareQueryEngine(scope);
		printAllMatches(viatraQueryEngine);
		modifyModel();
		printAllMatches(viatraQueryEngine);
	}
	
	private static TabularIndexScope initializeStringifiedScope() {
		SimpleLocalStorageBackend storage = new SimpleLocalStorageBackend();
		stringifiedndexHost = new GenricIndexHost(storage);
		
		// Initialize table for Person instances
		stringifiedndexHost.initDirectInstances("Person");

		// Initialize table for Friend references
		stringifiedndexHost.initFeatures("Friend");

		String[] people = {"0", "1", "2", "3", "4"};
		for (String person : people) {
			stringifiedndexHost.addInstance("Person", person);
		}
		
		stringifiedndexHost.addFeature("Friend", people[0], people[1]);
		stringifiedndexHost.addFeature("Friend", people[1], people[4]);
		stringifiedndexHost.addFeature("Friend", people[4], people[0]);
		stringifiedndexHost.addFeature("Friend", people[0], people[2]);
		stringifiedndexHost.addFeature("Friend", people[3], people[2]);
		stringifiedndexHost.addFeature("Friend", people[3], people[0]);
				
		return stringifiedndexHost.getScope();
	}
	
	private static void modifyModel() {
		stringifiedndexHost.addInstance("Person", "Pali");
		stringifiedndexHost.removeInstance("Person", "2");
		stringifiedndexHost.addFeature("Friend", "0", "Pali");
		stringifiedndexHost.addFeature("Friend", "Pali", "4");
		
		
	}
	
	private static ViatraQueryEngine prepareQueryEngine(QueryScope scope) {
		// Access managed query engine
	    ViatraQueryEngine engine = ViatraQueryEngine.on(scope);

	    // Initialize all queries on engine
		HandCraftedQueries.instance().prepare(engine);

		return engine;
	}
	
	private static void printAllMatches(ViatraQueryEngine engine) {
		// Access pattern matcher
//		Friend.Matcher matcher = Friend.Matcher.on(engine);
		System.out.println("Friends:");
		HandCraftedFriend.Matcher matcher = HandCraftedFriend.Matcher.on(engine);
		// Get and iterate over all matches
		for (HandCraftedFriend.Match match : matcher.getAllMatches()) {
			// Print all the matches to the standard output
			System.out.println(match.getP1() + ", " + match.getP2());
		}

		System.out.println("Friends2:");
		HandCraftedFriend2.Matcher matcher3 = HandCraftedFriend2.Matcher.on(engine);
		// Get and iterate over all matches
		for (HandCraftedFriend2.Match match3 : matcher3.getAllMatches()) {
			// Print all the matches to the standard output
			System.out.println(match3.getP1() + ", " + match3.getP2());
		}
		
		System.out.println("In friend circles:");
		HandCraftedFriendCircle.Matcher matcher2 = HandCraftedFriendCircle.Matcher.on(engine);
		// Get and iterate over all matches
		for (HandCraftedFriendCircle.Match match : matcher2.getAllMatches()) {
			// Print all the matches to the standard output
			System.out.println(match.getP());
		}
	}

}
