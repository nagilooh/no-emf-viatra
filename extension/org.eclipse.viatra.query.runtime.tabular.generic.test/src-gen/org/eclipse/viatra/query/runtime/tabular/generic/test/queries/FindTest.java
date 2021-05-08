/**
 * Generated from platform:/resource/org.eclipse.viatra.query.runtime.tabular.generic.test/src/org/eclipse/viatra/query/runtime/tabular/generic/test/queries/test_queries.vql
 */
package org.eclipse.viatra.query.runtime.tabular.generic.test.queries;

import hu.bme.mit.inf.friends.Person;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.viatra.query.runtime.api.IPatternMatch;
import org.eclipse.viatra.query.runtime.api.IQuerySpecification;
import org.eclipse.viatra.query.runtime.api.ViatraQueryEngine;
import org.eclipse.viatra.query.runtime.api.impl.BaseGeneratedEMFPQuery;
import org.eclipse.viatra.query.runtime.api.impl.BaseGeneratedEMFQuerySpecification;
import org.eclipse.viatra.query.runtime.api.impl.BaseMatcher;
import org.eclipse.viatra.query.runtime.api.impl.BasePatternMatch;
import org.eclipse.viatra.query.runtime.emf.types.EClassTransitiveInstancesKey;
import org.eclipse.viatra.query.runtime.matchers.backend.QueryEvaluationHint;
import org.eclipse.viatra.query.runtime.matchers.psystem.PBody;
import org.eclipse.viatra.query.runtime.matchers.psystem.PVariable;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicdeferred.ExportedParameter;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicenumerables.PositivePatternCall;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicenumerables.TypeConstraint;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PParameter;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PParameterDirection;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PVisibility;
import org.eclipse.viatra.query.runtime.matchers.tuple.Tuple;
import org.eclipse.viatra.query.runtime.matchers.tuple.Tuples;
import org.eclipse.viatra.query.runtime.tabular.generic.test.queries.RelationConstraintTest;
import org.eclipse.viatra.query.runtime.util.ViatraQueryLoggingUtil;

/**
 * A pattern-specific query specification that can instantiate Matcher in a type-safe way.
 * 
 * <p>Original source:
 *         <code><pre>
 *         pattern findTest(p1 : Person, p2 : Person) {
 *         	find relationConstraintTest(p1, p2);
 *         }
 * </pre></code>
 * 
 * @see Matcher
 * @see Match
 * 
 */
@SuppressWarnings("all")
public final class FindTest extends BaseGeneratedEMFQuerySpecification<FindTest.Matcher> {
  /**
   * Pattern-specific match representation of the org.eclipse.viatra.query.runtime.tabular.generic.test.queries.findTest pattern,
   * to be used in conjunction with {@link Matcher}.
   * 
   * <p>Class fields correspond to parameters of the pattern. Fields with value null are considered unassigned.
   * Each instance is a (possibly partial) substitution of pattern parameters,
   * usable to represent a match of the pattern in the result of a query,
   * or to specify the bound (fixed) input parameters when issuing a query.
   * 
   * @see Matcher
   * 
   */
  public static abstract class Match extends BasePatternMatch {
    private Person fP1;
    
    private Person fP2;
    
    private static List<String> parameterNames = makeImmutableList("p1", "p2");
    
    private Match(final Person pP1, final Person pP2) {
      this.fP1 = pP1;
      this.fP2 = pP2;
    }
    
    @Override
    public Object get(final String parameterName) {
      switch(parameterName) {
          case "p1": return this.fP1;
          case "p2": return this.fP2;
          default: return null;
      }
    }
    
    @Override
    public Object get(final int index) {
      switch(index) {
          case 0: return this.fP1;
          case 1: return this.fP2;
          default: return null;
      }
    }
    
    public Person getP1() {
      return this.fP1;
    }
    
    public Person getP2() {
      return this.fP2;
    }
    
    @Override
    public boolean set(final String parameterName, final Object newValue) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      if ("p1".equals(parameterName) ) {
          this.fP1 = (Person) newValue;
          return true;
      }
      if ("p2".equals(parameterName) ) {
          this.fP2 = (Person) newValue;
          return true;
      }
      return false;
    }
    
    public void setP1(final Person pP1) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      this.fP1 = pP1;
    }
    
    public void setP2(final Person pP2) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      this.fP2 = pP2;
    }
    
    @Override
    public String patternName() {
      return "org.eclipse.viatra.query.runtime.tabular.generic.test.queries.findTest";
    }
    
    @Override
    public List<String> parameterNames() {
      return FindTest.Match.parameterNames;
    }
    
    @Override
    public Object[] toArray() {
      return new Object[]{fP1, fP2};
    }
    
    @Override
    public FindTest.Match toImmutable() {
      return isMutable() ? newMatch(fP1, fP2) : this;
    }
    
    @Override
    public String prettyPrint() {
      StringBuilder result = new StringBuilder();
      result.append("\"p1\"=" + prettyPrintValue(fP1) + ", ");
      result.append("\"p2\"=" + prettyPrintValue(fP2));
      return result.toString();
    }
    
    @Override
    public int hashCode() {
      return Objects.hash(fP1, fP2);
    }
    
    @Override
    public boolean equals(final Object obj) {
      if (this == obj)
          return true;
      if (obj == null) {
          return false;
      }
      if ((obj instanceof FindTest.Match)) {
          FindTest.Match other = (FindTest.Match) obj;
          return Objects.equals(fP1, other.fP1) && Objects.equals(fP2, other.fP2);
      } else {
          // this should be infrequent
          if (!(obj instanceof IPatternMatch)) {
              return false;
          }
          IPatternMatch otherSig  = (IPatternMatch) obj;
          return Objects.equals(specification(), otherSig.specification()) && Arrays.deepEquals(toArray(), otherSig.toArray());
      }
    }
    
    @Override
    public FindTest specification() {
      return FindTest.instance();
    }
    
    /**
     * Returns an empty, mutable match.
     * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
     * 
     * @return the empty match.
     * 
     */
    public static FindTest.Match newEmptyMatch() {
      return new Mutable(null, null);
    }
    
    /**
     * Returns a mutable (partial) match.
     * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
     * 
     * @param pP1 the fixed value of pattern parameter p1, or null if not bound.
     * @param pP2 the fixed value of pattern parameter p2, or null if not bound.
     * @return the new, mutable (partial) match object.
     * 
     */
    public static FindTest.Match newMutableMatch(final Person pP1, final Person pP2) {
      return new Mutable(pP1, pP2);
    }
    
    /**
     * Returns a new (partial) match.
     * This can be used e.g. to call the matcher with a partial match.
     * <p>The returned match will be immutable. Use {@link #newEmptyMatch()} to obtain a mutable match object.
     * @param pP1 the fixed value of pattern parameter p1, or null if not bound.
     * @param pP2 the fixed value of pattern parameter p2, or null if not bound.
     * @return the (partial) match object.
     * 
     */
    public static FindTest.Match newMatch(final Person pP1, final Person pP2) {
      return new Immutable(pP1, pP2);
    }
    
    private static final class Mutable extends FindTest.Match {
      Mutable(final Person pP1, final Person pP2) {
        super(pP1, pP2);
      }
      
      @Override
      public boolean isMutable() {
        return true;
      }
    }
    
    private static final class Immutable extends FindTest.Match {
      Immutable(final Person pP1, final Person pP2) {
        super(pP1, pP2);
      }
      
      @Override
      public boolean isMutable() {
        return false;
      }
    }
  }
  
  /**
   * Generated pattern matcher API of the org.eclipse.viatra.query.runtime.tabular.generic.test.queries.findTest pattern,
   * providing pattern-specific query methods.
   * 
   * <p>Use the pattern matcher on a given model via {@link #on(ViatraQueryEngine)},
   * e.g. in conjunction with {@link ViatraQueryEngine#on(QueryScope)}.
   * 
   * <p>Matches of the pattern will be represented as {@link Match}.
   * 
   * <p>Original source:
   * <code><pre>
   * pattern findTest(p1 : Person, p2 : Person) {
   * 	find relationConstraintTest(p1, p2);
   * }
   * </pre></code>
   * 
   * @see Match
   * @see FindTest
   * 
   */
  public static class Matcher extends BaseMatcher<FindTest.Match> {
    /**
     * Initializes the pattern matcher within an existing VIATRA Query engine.
     * If the pattern matcher is already constructed in the engine, only a light-weight reference is returned.
     * 
     * @param engine the existing VIATRA Query engine in which this matcher will be created.
     * @throws ViatraQueryRuntimeException if an error occurs during pattern matcher creation
     * 
     */
    public static FindTest.Matcher on(final ViatraQueryEngine engine) {
      // check if matcher already exists
      Matcher matcher = engine.getExistingMatcher(querySpecification());
      if (matcher == null) {
          matcher = (Matcher)engine.getMatcher(querySpecification());
      }
      return matcher;
    }
    
    /**
     * @throws ViatraQueryRuntimeException if an error occurs during pattern matcher creation
     * @return an initialized matcher
     * @noreference This method is for internal matcher initialization by the framework, do not call it manually.
     * 
     */
    public static FindTest.Matcher create() {
      return new Matcher();
    }
    
    private static final int POSITION_P1 = 0;
    
    private static final int POSITION_P2 = 1;
    
    private static final Logger LOGGER = ViatraQueryLoggingUtil.getLogger(FindTest.Matcher.class);
    
    /**
     * Initializes the pattern matcher within an existing VIATRA Query engine.
     * If the pattern matcher is already constructed in the engine, only a light-weight reference is returned.
     * 
     * @param engine the existing VIATRA Query engine in which this matcher will be created.
     * @throws ViatraQueryRuntimeException if an error occurs during pattern matcher creation
     * 
     */
    private Matcher() {
      super(querySpecification());
    }
    
    /**
     * Returns the set of all matches of the pattern that conform to the given fixed values of some parameters.
     * @param pP1 the fixed value of pattern parameter p1, or null if not bound.
     * @param pP2 the fixed value of pattern parameter p2, or null if not bound.
     * @return matches represented as a Match object.
     * 
     */
    public Collection<FindTest.Match> getAllMatches(final Person pP1, final Person pP2) {
      return rawStreamAllMatches(new Object[]{pP1, pP2}).collect(Collectors.toSet());
    }
    
    /**
     * Returns a stream of all matches of the pattern that conform to the given fixed values of some parameters.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     * @param pP1 the fixed value of pattern parameter p1, or null if not bound.
     * @param pP2 the fixed value of pattern parameter p2, or null if not bound.
     * @return a stream of matches represented as a Match object.
     * 
     */
    public Stream<FindTest.Match> streamAllMatches(final Person pP1, final Person pP2) {
      return rawStreamAllMatches(new Object[]{pP1, pP2});
    }
    
    /**
     * Returns an arbitrarily chosen match of the pattern that conforms to the given fixed values of some parameters.
     * Neither determinism nor randomness of selection is guaranteed.
     * @param pP1 the fixed value of pattern parameter p1, or null if not bound.
     * @param pP2 the fixed value of pattern parameter p2, or null if not bound.
     * @return a match represented as a Match object, or null if no match is found.
     * 
     */
    public Optional<FindTest.Match> getOneArbitraryMatch(final Person pP1, final Person pP2) {
      return rawGetOneArbitraryMatch(new Object[]{pP1, pP2});
    }
    
    /**
     * Indicates whether the given combination of specified pattern parameters constitute a valid pattern match,
     * under any possible substitution of the unspecified parameters (if any).
     * @param pP1 the fixed value of pattern parameter p1, or null if not bound.
     * @param pP2 the fixed value of pattern parameter p2, or null if not bound.
     * @return true if the input is a valid (partial) match of the pattern.
     * 
     */
    public boolean hasMatch(final Person pP1, final Person pP2) {
      return rawHasMatch(new Object[]{pP1, pP2});
    }
    
    /**
     * Returns the number of all matches of the pattern that conform to the given fixed values of some parameters.
     * @param pP1 the fixed value of pattern parameter p1, or null if not bound.
     * @param pP2 the fixed value of pattern parameter p2, or null if not bound.
     * @return the number of pattern matches found.
     * 
     */
    public int countMatches(final Person pP1, final Person pP2) {
      return rawCountMatches(new Object[]{pP1, pP2});
    }
    
    /**
     * Executes the given processor on an arbitrarily chosen match of the pattern that conforms to the given fixed values of some parameters.
     * Neither determinism nor randomness of selection is guaranteed.
     * @param pP1 the fixed value of pattern parameter p1, or null if not bound.
     * @param pP2 the fixed value of pattern parameter p2, or null if not bound.
     * @param processor the action that will process the selected match.
     * @return true if the pattern has at least one match with the given parameter values, false if the processor was not invoked
     * 
     */
    public boolean forOneArbitraryMatch(final Person pP1, final Person pP2, final Consumer<? super FindTest.Match> processor) {
      return rawForOneArbitraryMatch(new Object[]{pP1, pP2}, processor);
    }
    
    /**
     * Returns a new (partial) match.
     * This can be used e.g. to call the matcher with a partial match.
     * <p>The returned match will be immutable. Use {@link #newEmptyMatch()} to obtain a mutable match object.
     * @param pP1 the fixed value of pattern parameter p1, or null if not bound.
     * @param pP2 the fixed value of pattern parameter p2, or null if not bound.
     * @return the (partial) match object.
     * 
     */
    public FindTest.Match newMatch(final Person pP1, final Person pP2) {
      return FindTest.Match.newMatch(pP1, pP2);
    }
    
    /**
     * Retrieve the set of values that occur in matches for p1.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    protected Stream<Person> rawStreamAllValuesOfp1(final Object[] parameters) {
      return rawStreamAllValues(POSITION_P1, parameters).map(Person.class::cast);
    }
    
    /**
     * Retrieve the set of values that occur in matches for p1.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Person> getAllValuesOfp1() {
      return rawStreamAllValuesOfp1(emptyArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for p1.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Stream<Person> streamAllValuesOfp1() {
      return rawStreamAllValuesOfp1(emptyArray());
    }
    
    /**
     * Retrieve the set of values that occur in matches for p1.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     *      
     * @return the Stream of all values or empty set if there are no matches
     * 
     */
    public Stream<Person> streamAllValuesOfp1(final FindTest.Match partialMatch) {
      return rawStreamAllValuesOfp1(partialMatch.toArray());
    }
    
    /**
     * Retrieve the set of values that occur in matches for p1.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     *      
     * @return the Stream of all values or empty set if there are no matches
     * 
     */
    public Stream<Person> streamAllValuesOfp1(final Person pP2) {
      return rawStreamAllValuesOfp1(new Object[]{null, pP2});
    }
    
    /**
     * Retrieve the set of values that occur in matches for p1.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Person> getAllValuesOfp1(final FindTest.Match partialMatch) {
      return rawStreamAllValuesOfp1(partialMatch.toArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for p1.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Person> getAllValuesOfp1(final Person pP2) {
      return rawStreamAllValuesOfp1(new Object[]{null, pP2}).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for p2.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    protected Stream<Person> rawStreamAllValuesOfp2(final Object[] parameters) {
      return rawStreamAllValues(POSITION_P2, parameters).map(Person.class::cast);
    }
    
    /**
     * Retrieve the set of values that occur in matches for p2.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Person> getAllValuesOfp2() {
      return rawStreamAllValuesOfp2(emptyArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for p2.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Stream<Person> streamAllValuesOfp2() {
      return rawStreamAllValuesOfp2(emptyArray());
    }
    
    /**
     * Retrieve the set of values that occur in matches for p2.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     *      
     * @return the Stream of all values or empty set if there are no matches
     * 
     */
    public Stream<Person> streamAllValuesOfp2(final FindTest.Match partialMatch) {
      return rawStreamAllValuesOfp2(partialMatch.toArray());
    }
    
    /**
     * Retrieve the set of values that occur in matches for p2.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     *      
     * @return the Stream of all values or empty set if there are no matches
     * 
     */
    public Stream<Person> streamAllValuesOfp2(final Person pP1) {
      return rawStreamAllValuesOfp2(new Object[]{pP1, null});
    }
    
    /**
     * Retrieve the set of values that occur in matches for p2.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Person> getAllValuesOfp2(final FindTest.Match partialMatch) {
      return rawStreamAllValuesOfp2(partialMatch.toArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for p2.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Person> getAllValuesOfp2(final Person pP1) {
      return rawStreamAllValuesOfp2(new Object[]{pP1, null}).collect(Collectors.toSet());
    }
    
    @Override
    protected FindTest.Match tupleToMatch(final Tuple t) {
      try {
          return FindTest.Match.newMatch((Person) t.get(POSITION_P1), (Person) t.get(POSITION_P2));
      } catch(ClassCastException e) {
          LOGGER.error("Element(s) in tuple not properly typed!",e);
          return null;
      }
    }
    
    @Override
    protected FindTest.Match arrayToMatch(final Object[] match) {
      try {
          return FindTest.Match.newMatch((Person) match[POSITION_P1], (Person) match[POSITION_P2]);
      } catch(ClassCastException e) {
          LOGGER.error("Element(s) in array not properly typed!",e);
          return null;
      }
    }
    
    @Override
    protected FindTest.Match arrayToMatchMutable(final Object[] match) {
      try {
          return FindTest.Match.newMutableMatch((Person) match[POSITION_P1], (Person) match[POSITION_P2]);
      } catch(ClassCastException e) {
          LOGGER.error("Element(s) in array not properly typed!",e);
          return null;
      }
    }
    
    /**
     * @return the singleton instance of the query specification of this pattern
     * @throws ViatraQueryRuntimeException if the pattern definition could not be loaded
     * 
     */
    public static IQuerySpecification<FindTest.Matcher> querySpecification() {
      return FindTest.instance();
    }
  }
  
  private FindTest() {
    super(GeneratedPQuery.INSTANCE);
  }
  
  /**
   * @return the singleton instance of the query specification
   * @throws ViatraQueryRuntimeException if the pattern definition could not be loaded
   * 
   */
  public static FindTest instance() {
    try{
        return LazyHolder.INSTANCE;
    } catch (ExceptionInInitializerError err) {
        throw processInitializerError(err);
    }
  }
  
  @Override
  protected FindTest.Matcher instantiate(final ViatraQueryEngine engine) {
    return FindTest.Matcher.on(engine);
  }
  
  @Override
  public FindTest.Matcher instantiate() {
    return FindTest.Matcher.create();
  }
  
  @Override
  public FindTest.Match newEmptyMatch() {
    return FindTest.Match.newEmptyMatch();
  }
  
  @Override
  public FindTest.Match newMatch(final Object... parameters) {
    return FindTest.Match.newMatch((hu.bme.mit.inf.friends.Person) parameters[0], (hu.bme.mit.inf.friends.Person) parameters[1]);
  }
  
  /**
   * Inner class allowing the singleton instance of {@link FindTest} to be created 
   *     <b>not</b> at the class load time of the outer class, 
   *     but rather at the first call to {@link FindTest#instance()}.
   * 
   * <p> This workaround is required e.g. to support recursion.
   * 
   */
  private static class LazyHolder {
    private static final FindTest INSTANCE = new FindTest();
    
    /**
     * Statically initializes the query specification <b>after</b> the field {@link #INSTANCE} is assigned.
     * This initialization order is required to support indirect recursion.
     * 
     * <p> The static initializer is defined using a helper field to work around limitations of the code generator.
     * 
     */
    private static final Object STATIC_INITIALIZER = ensureInitialized();
    
    public static Object ensureInitialized() {
      INSTANCE.ensureInitializedInternal();
      return null;
    }
  }
  
  private static class GeneratedPQuery extends BaseGeneratedEMFPQuery {
    private static final FindTest.GeneratedPQuery INSTANCE = new GeneratedPQuery();
    
    private final PParameter parameter_p1 = new PParameter("p1", "hu.bme.mit.inf.friends.Person", new EClassTransitiveInstancesKey((EClass)getClassifierLiteralSafe("http://www.inf.mit.bme.hu/friends", "Person")), PParameterDirection.INOUT);
    
    private final PParameter parameter_p2 = new PParameter("p2", "hu.bme.mit.inf.friends.Person", new EClassTransitiveInstancesKey((EClass)getClassifierLiteralSafe("http://www.inf.mit.bme.hu/friends", "Person")), PParameterDirection.INOUT);
    
    private final List<PParameter> parameters = Arrays.asList(parameter_p1, parameter_p2);
    
    private GeneratedPQuery() {
      super(PVisibility.PUBLIC);
    }
    
    @Override
    public String getFullyQualifiedName() {
      return "org.eclipse.viatra.query.runtime.tabular.generic.test.queries.findTest";
    }
    
    @Override
    public List<String> getParameterNames() {
      return Arrays.asList("p1","p2");
    }
    
    @Override
    public List<PParameter> getParameters() {
      return parameters;
    }
    
    @Override
    public Set<PBody> doGetContainedBodies() {
      setEvaluationHints(new QueryEvaluationHint(null, QueryEvaluationHint.BackendRequirement.UNSPECIFIED));
      Set<PBody> bodies = new LinkedHashSet<>();
      {
          PBody body = new PBody(this);
          PVariable var_p1 = body.getOrCreateVariableByName("p1");
          PVariable var_p2 = body.getOrCreateVariableByName("p2");
          new TypeConstraint(body, Tuples.flatTupleOf(var_p1), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("http://www.inf.mit.bme.hu/friends", "Person")));
          new TypeConstraint(body, Tuples.flatTupleOf(var_p2), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("http://www.inf.mit.bme.hu/friends", "Person")));
          body.setSymbolicParameters(Arrays.<ExportedParameter>asList(
             new ExportedParameter(body, var_p1, parameter_p1),
             new ExportedParameter(body, var_p2, parameter_p2)
          ));
          // 	find relationConstraintTest(p1, p2)
          new PositivePatternCall(body, Tuples.flatTupleOf(var_p1, var_p2), RelationConstraintTest.instance().getInternalQueryRepresentation());
          bodies.add(body);
      }
      return bodies;
    }
  }
}
