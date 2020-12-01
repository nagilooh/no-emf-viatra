package hu.bme.mit.inf.friends.queries.runner;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.viatra.query.runtime.api.ViatraQueryEngine;
import org.eclipse.viatra.query.runtime.api.scope.QueryScope;
import org.eclipse.viatra.query.runtime.emf.EMFScope;
import org.eclipse.viatra.query.runtime.matchers.scopes.SimpleLocalStorageBackend;
import org.eclipse.viatra.query.runtime.matchers.scopes.tables.ITableWriterBinary;
import org.eclipse.viatra.query.runtime.matchers.scopes.tables.ITableWriterUnary;
import org.eclipse.viatra.query.runtime.matchers.util.Direction;
import org.eclipse.viatra.query.runtime.tabular.EcoreIndexHost;
import org.eclipse.viatra.query.runtime.tabular.TabularIndexHost.TabularIndexScope;

import hu.bme.mit.inf.friends.FriendsFactory;
import hu.bme.mit.inf.friends.FriendsPackage;
import hu.bme.mit.inf.friends.People;
import hu.bme.mit.inf.friends.Person;
import hu.bme.mit.inf.friends.queries.Friend;
import hu.bme.mit.inf.friends.queries.Queries;

public class QueryRunner implements IApplication {

	@Override
	public Object start(IApplicationContext context) throws Exception {
        // Return value 0 is considered as a successful execution on Unix systems
		QueryScope scope = initializeTabularScope();
		ViatraQueryEngine viatraQueryEngine = prepareQueryEngine(scope);
		printAllMatches(viatraQueryEngine);
		return 0;
	}

	@Override
	public void stop() {
        // Headless applications do not require specific stop steps
	}
	
	private EMFScope initializeEMFScope() {
		ResourceSet rs = new ResourceSetImpl();
		rs.getResource(URI.createPlatformPluginURI("hu.bme.mit.inf.friends.queries/People.xmi", true), true);

		return new EMFScope(rs);
	}
	
	private TabularIndexScope initializeTabularScope() {
		FriendsFactory friendsFactory = FriendsFactory.eINSTANCE;
		FriendsPackage friendsPackage = FriendsPackage.eINSTANCE;
		EcoreIndexHost ecoreIndexHost = new EcoreIndexHost(new SimpleLocalStorageBackend(), friendsPackage);

		EClass peopleClass = friendsPackage.getPeople();
		EClass personClass = friendsPackage.getPerson();
		EReference people_Person = friendsPackage.getPeople_Person();
		EReference person_Friend = friendsPackage.getPerson_Friend();

		ITableWriterUnary.Table<Object> tablePeople = ecoreIndexHost.getTableDirectInstances(peopleClass);
		People people = friendsFactory.createPeople();
		tablePeople.write(Direction.INSERT, people);

		ITableWriterUnary.Table<Object> tablePerson = ecoreIndexHost.getTableDirectInstances(personClass);
		Person person1 = friendsFactory.createPerson();
		person1.setName("James");
		Person person2 = friendsFactory.createPerson();
		person2.setName("Robert");		
		tablePerson.write(Direction.INSERT, person1);
		tablePerson.write(Direction.INSERT, person2);
		
		ITableWriterBinary.Table<Object, Object> tablePeople_Person = ecoreIndexHost.getTableFeatureSlots(people_Person);
		tablePeople_Person.write(Direction.INSERT, people, person1);
		tablePeople_Person.write(Direction.INSERT, people, person2);
		
		ITableWriterBinary.Table<Object, Object> tablePerson_Friend = ecoreIndexHost.getTableFeatureSlots(person_Friend);
		tablePerson_Friend.write(Direction.INSERT, person1, person2);
		tablePerson_Friend.write(Direction.INSERT, person2, person1);
				
		return ecoreIndexHost.getScope();
	}
	
	private ViatraQueryEngine prepareQueryEngine(QueryScope scope) {
		// Access managed query engine
	    ViatraQueryEngine engine = ViatraQueryEngine.on(scope);

	    // Initialize all queries on engine
		Queries.instance().prepare(engine);

		return engine;
	}
	
	private void printAllMatches(ViatraQueryEngine engine) {
		// Access pattern matcher
		Friend.Matcher matcher = Friend.Matcher.on(engine);
		// Get and iterate over all matches
		for (Friend.Match match : matcher.getAllMatches()) {
			// Print all the matches to the standard output
			System.out.println(match.getP1() + ", " + match.getP2());
		}
	}

}
