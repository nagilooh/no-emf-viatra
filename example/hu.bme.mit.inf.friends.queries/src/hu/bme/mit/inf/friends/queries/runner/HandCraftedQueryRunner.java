package hu.bme.mit.inf.friends.queries.runner;

import org.eclipse.viatra.query.runtime.api.ViatraQueryEngine;
import org.eclipse.viatra.query.runtime.api.scope.QueryScope;
import org.eclipse.viatra.query.runtime.matchers.scopes.SimpleLocalStorageBackend;
import org.eclipse.viatra.query.runtime.matchers.scopes.tables.ITableWriterBinary;
import org.eclipse.viatra.query.runtime.matchers.scopes.tables.ITableWriterUnary;
import org.eclipse.viatra.query.runtime.matchers.util.Direction;
import org.eclipse.viatra.query.runtime.tabular.StringifiedndexHost;
import org.eclipse.viatra.query.runtime.tabular.TabularIndexHost.TabularIndexScope;

import hu.bme.mit.inf.friends.queries.HandCraftedFriend;
import hu.bme.mit.inf.friends.queries.HandCraftedQueries;

public class HandCraftedQueryRunner {

	public static void main(String[] args) {
		QueryScope scope = initializeStringifiedScope();
		ViatraQueryEngine viatraQueryEngine = prepareQueryEngine(scope);
		printAllMatches(viatraQueryEngine);
	}
	
	private static TabularIndexScope initializeStringifiedScope() {
		SimpleLocalStorageBackend storage = new SimpleLocalStorageBackend();
		StringifiedndexHost stringifiedndexHost = new StringifiedndexHost(storage);
		stringifiedndexHost.initDirectInstances("Person");
		stringifiedndexHost.initFeatures("Friend");

		ITableWriterUnary.Table<Object> tablePerson = stringifiedndexHost.getTableDirectInstances("Person");
		String person1 = "James";	
		String person2 = "Robert";
		tablePerson.write(Direction.INSERT, person1);
		tablePerson.write(Direction.INSERT, person2);
		
		ITableWriterBinary.Table<Object, Object> tablePerson_Friend = stringifiedndexHost.getTableFeatureSlots("Friend");
		tablePerson_Friend.write(Direction.INSERT, person1, person2);
		tablePerson_Friend.write(Direction.INSERT, person2, person1);
				
		return stringifiedndexHost.getScope();
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
		HandCraftedFriend.Matcher matcher = HandCraftedFriend.Matcher.on(engine);
		// Get and iterate over all matches
		for (HandCraftedFriend.Match match : matcher.getAllMatches()) {
			// Print all the matches to the standard output
			System.out.println(match.getP1() + ", " + match.getP2());
		}
	}

}
