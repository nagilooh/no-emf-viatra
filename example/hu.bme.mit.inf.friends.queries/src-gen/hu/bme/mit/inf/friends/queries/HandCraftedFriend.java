/**
 * Generated from platform:/resource/hu.bme.mit.inf.friends.queries/src/hu/bme/mit/inf/friends/queries/queries2.vql
 */
package hu.bme.mit.inf.friends.queries;

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
//import org.eclipse.emf.ecore.EClass;
import org.eclipse.viatra.query.runtime.api.IPatternMatch;
import org.eclipse.viatra.query.runtime.api.IQuerySpecification;
import org.eclipse.viatra.query.runtime.api.ViatraQueryEngine;
import org.eclipse.viatra.query.runtime.api.impl.BaseGeneratedEMFPQuery;
import org.eclipse.viatra.query.runtime.api.impl.BaseGeneratedEMFQuerySpecification;
import org.eclipse.viatra.query.runtime.api.impl.BaseMatcher;
import org.eclipse.viatra.query.runtime.api.impl.BasePatternMatch;
import org.eclipse.viatra.query.runtime.api.scope.QueryScope;
//import org.eclipse.viatra.query.runtime.emf.types.EClassTransitiveInstancesKey;
//import org.eclipse.viatra.query.runtime.emf.types.EStructuralFeatureInstancesKey;
import org.eclipse.viatra.query.runtime.tabular.types.StringExactInstancesKey;
import org.eclipse.viatra.query.runtime.tabular.types.StringStructuralFeatureInstancesKey;
import org.eclipse.viatra.query.runtime.matchers.ViatraQueryRuntimeException;
import org.eclipse.viatra.query.runtime.matchers.backend.QueryEvaluationHint;
import org.eclipse.viatra.query.runtime.matchers.psystem.PBody;
import org.eclipse.viatra.query.runtime.matchers.psystem.PVariable;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicdeferred.Equality;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicdeferred.ExportedParameter;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicenumerables.TypeConstraint;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PParameter;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PParameterDirection;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PVisibility;
import org.eclipse.viatra.query.runtime.matchers.tuple.Tuple;
import org.eclipse.viatra.query.runtime.matchers.tuple.Tuples;
import org.eclipse.viatra.query.runtime.util.ViatraQueryLoggingUtil;

//import hu.bme.mit.inf.friends.Person;

/**
 * A pattern-specific query specification that can instantiate Matcher in a type-safe way.
 * 
 * <p>Original source:
 *         <code><pre>
 *         pattern friend(p1 : Person, p2 : Person) {
 *         	Person.friend(p1, p2);
 *         }
 * </pre></code>
 * 
 * @see Matcher
 * @see Match
 * 
 */
@SuppressWarnings("all")
public final class HandCraftedFriend extends BaseGeneratedEMFQuerySpecification<HandCraftedFriend.Matcher> {
  /**
   * Pattern-specific match representation of the hu.bme.mit.inf.friends.queries.friend pattern,
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
    private String fP1;
    
    private String fP2;
    
    private static List<String> parameterNames = makeImmutableList("p1", "p2");
    
    private Match(final String pP1, final String pP2) {
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
    
    public String getP1() {
      return this.fP1;
    }
    
    public String getP2() {
      return this.fP2;
    }
    
    @Override
    public boolean set(final String parameterName, final Object newValue) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      if ("p1".equals(parameterName) ) {
          this.fP1 = (String) newValue;
          return true;
      }
      if ("p2".equals(parameterName) ) {
          this.fP2 = (String) newValue;
          return true;
      }
      return false;
    }
    
    public void setP1(final String pP1) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      this.fP1 = pP1;
    }
    
    public void setP2(final String pP2) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      this.fP2 = pP2;
    }
    
    @Override
    public String patternName() {
      return "hu.bme.mit.inf.friends.queries.friend";
    }
    
    @Override
    public List<String> parameterNames() {
      return HandCraftedFriend.Match.parameterNames;
    }
    
    @Override
    public Object[] toArray() {
      return new Object[]{fP1, fP2};
    }
    
    @Override
    public HandCraftedFriend.Match toImmutable() {
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
      if ((obj instanceof HandCraftedFriend.Match)) {
          HandCraftedFriend.Match other = (HandCraftedFriend.Match) obj;
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
    public HandCraftedFriend specification() {
      return HandCraftedFriend.instance();
    }
    
    /**
     * Returns an empty, mutable match.
     * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
     * 
     * @return the empty match.
     * 
     */
    public static HandCraftedFriend.Match newEmptyMatch() {
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
    public static HandCraftedFriend.Match newMutableMatch(final String pP1, final String pP2) {
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
    public static HandCraftedFriend.Match newMatch(final String pP1, final String pP2) {
      return new Immutable(pP1, pP2);
    }
    
    private static final class Mutable extends HandCraftedFriend.Match {
      Mutable(final String pP1, final String pP2) {
        super(pP1, pP2);
      }
      
      @Override
      public boolean isMutable() {
        return true;
      }
    }
    
    private static final class Immutable extends HandCraftedFriend.Match {
      Immutable(final String pP1, final String pP2) {
        super(pP1, pP2);
      }
      
      @Override
      public boolean isMutable() {
        return false;
      }
    }
  }
  
  /**
   * Generated pattern matcher API of the hu.bme.mit.inf.friends.queries.friend pattern,
   * providing pattern-specific query methods.
   * 
   * <p>Use the pattern matcher on a given model via {@link #on(ViatraQueryEngine)},
   * e.g. in conjunction with {@link ViatraQueryEngine#on(QueryScope)}.
   * 
   * <p>Matches of the pattern will be represented as {@link Match}.
   * 
   * <p>Original source:
   * <code><pre>
   * pattern friend(p1 : Person, p2 : Person) {
   * 	Person.friend(p1, p2);
   * }
   * </pre></code>
   * 
   * @see Match
   * @see HandCraftedFriend
   * 
   */
  public static class Matcher extends BaseMatcher<HandCraftedFriend.Match> {
    /**
     * Initializes the pattern matcher within an existing VIATRA Query engine.
     * If the pattern matcher is already constructed in the engine, only a light-weight reference is returned.
     * 
     * @param engine the existing VIATRA Query engine in which this matcher will be created.
     * @throws ViatraQueryRuntimeException if an error occurs during pattern matcher creation
     * 
     */
    public static HandCraftedFriend.Matcher on(final ViatraQueryEngine engine) {
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
    public static HandCraftedFriend.Matcher create() {
      return new Matcher();
    }
    
    private static final int POSITION_P1 = 0;
    
    private static final int POSITION_P2 = 1;
    
    private static final Logger LOGGER = ViatraQueryLoggingUtil.getLogger(HandCraftedFriend.Matcher.class);
    
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
    public Collection<HandCraftedFriend.Match> getAllMatches(final String pP1, final String pP2) {
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
    public Stream<HandCraftedFriend.Match> streamAllMatches(final String pP1, final String pP2) {
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
    public Optional<HandCraftedFriend.Match> getOneArbitraryMatch(final String pP1, final String pP2) {
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
    public boolean hasMatch(final String pP1, final String pP2) {
      return rawHasMatch(new Object[]{pP1, pP2});
    }
    
    /**
     * Returns the number of all matches of the pattern that conform to the given fixed values of some parameters.
     * @param pP1 the fixed value of pattern parameter p1, or null if not bound.
     * @param pP2 the fixed value of pattern parameter p2, or null if not bound.
     * @return the number of pattern matches found.
     * 
     */
    public int countMatches(final String pP1, final String pP2) {
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
    public boolean forOneArbitraryMatch(final String pP1, final String pP2, final Consumer<? super HandCraftedFriend.Match> processor) {
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
    public HandCraftedFriend.Match newMatch(final String pP1, final String pP2) {
      return HandCraftedFriend.Match.newMatch(pP1, pP2);
    }
    
    /**
     * Retrieve the set of values that occur in matches for p1.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    protected Stream<String> rawStreamAllValuesOfp1(final Object[] parameters) {
      return rawStreamAllValues(POSITION_P1, parameters).map(String.class::cast);
    }
    
    /**
     * Retrieve the set of values that occur in matches for p1.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<String> getAllValuesOfp1() {
      return rawStreamAllValuesOfp1(emptyArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for p1.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Stream<String> streamAllValuesOfp1() {
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
    public Stream<String> streamAllValuesOfp1(final HandCraftedFriend.Match partialMatch) {
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
    public Stream<String> streamAllValuesOfp1(final String pP2) {
      return rawStreamAllValuesOfp1(new Object[]{null, pP2});
    }
    
    /**
     * Retrieve the set of values that occur in matches for p1.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<String> getAllValuesOfp1(final HandCraftedFriend.Match partialMatch) {
      return rawStreamAllValuesOfp1(partialMatch.toArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for p1.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<String> getAllValuesOfp1(final String pP2) {
      return rawStreamAllValuesOfp1(new Object[]{null, pP2}).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for p2.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    protected Stream<String> rawStreamAllValuesOfp2(final Object[] parameters) {
      return rawStreamAllValues(POSITION_P2, parameters).map(String.class::cast);
    }
    
    /**
     * Retrieve the set of values that occur in matches for p2.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<String> getAllValuesOfp2() {
      return rawStreamAllValuesOfp2(emptyArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for p2.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Stream<String> streamAllValuesOfp2() {
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
    public Stream<String> streamAllValuesOfp2(final HandCraftedFriend.Match partialMatch) {
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
    public Stream<String> streamAllValuesOfp2(final String pP1) {
      return rawStreamAllValuesOfp2(new Object[]{pP1, null});
    }
    
    /**
     * Retrieve the set of values that occur in matches for p2.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<String> getAllValuesOfp2(final HandCraftedFriend.Match partialMatch) {
      return rawStreamAllValuesOfp2(partialMatch.toArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for p2.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<String> getAllValuesOfp2(final String pP1) {
      return rawStreamAllValuesOfp2(new Object[]{pP1, null}).collect(Collectors.toSet());
    }
    
    @Override
    protected HandCraftedFriend.Match tupleToMatch(final Tuple t) {
      try {
          return HandCraftedFriend.Match.newMatch((String) t.get(POSITION_P1), (String) t.get(POSITION_P2));
      } catch(ClassCastException e) {
          LOGGER.error("Element(s) in tuple not properly typed!",e);
          return null;
      }
    }
    
    @Override
    protected HandCraftedFriend.Match arrayToMatch(final Object[] match) {
      try {
          return HandCraftedFriend.Match.newMatch((String) match[POSITION_P1], (String) match[POSITION_P2]);
      } catch(ClassCastException e) {
          LOGGER.error("Element(s) in array not properly typed!",e);
          return null;
      }
    }
    
    @Override
    protected HandCraftedFriend.Match arrayToMatchMutable(final Object[] match) {
      try {
          return HandCraftedFriend.Match.newMutableMatch((String) match[POSITION_P1], (String) match[POSITION_P2]);
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
    public static IQuerySpecification<HandCraftedFriend.Matcher> querySpecification() {
      return HandCraftedFriend.instance();
    }
  }
  
  private HandCraftedFriend() {
    super(GeneratedPQuery.INSTANCE);
  }
  
  /**
   * @return the singleton instance of the query specification
   * @throws ViatraQueryRuntimeException if the pattern definition could not be loaded
   * 
   */
  public static HandCraftedFriend instance() {
    try{
        return LazyHolder.INSTANCE;
    } catch (ExceptionInInitializerError err) {
        throw processInitializerError(err);
    }
  }
  
  @Override
  protected HandCraftedFriend.Matcher instantiate(final ViatraQueryEngine engine) {
    return HandCraftedFriend.Matcher.on(engine);
  }
  
  @Override
  public HandCraftedFriend.Matcher instantiate() {
    return HandCraftedFriend.Matcher.create();
  }
  
  @Override
  public HandCraftedFriend.Match newEmptyMatch() {
    return HandCraftedFriend.Match.newEmptyMatch();
  }
  
  @Override
  public HandCraftedFriend.Match newMatch(final Object... parameters) {
    return HandCraftedFriend.Match.newMatch((String) parameters[0], (String) parameters[1]);
  }
  
  /**
   * Inner class allowing the singleton instance of {@link HandCraftedFriend} to be created 
   *     <b>not</b> at the class load time of the outer class, 
   *     but rather at the first call to {@link HandCraftedFriend#instance()}.
   * 
   * <p> This workaround is required e.g. to support recursion.
   * 
   */
  private static class LazyHolder {
    private static final HandCraftedFriend INSTANCE = new HandCraftedFriend();
    
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
    private static final HandCraftedFriend.GeneratedPQuery INSTANCE = new GeneratedPQuery();
    
    private final PParameter parameter_p1 = new PParameter("p1", "String", new StringExactInstancesKey("Person"), PParameterDirection.INOUT);
    
    private final PParameter parameter_p2 = new PParameter("p2", "String", new StringExactInstancesKey("Person"), PParameterDirection.INOUT);
    
    private final List<PParameter> parameters = Arrays.asList(parameter_p1, parameter_p2);
    
    private GeneratedPQuery() {
      super(PVisibility.PUBLIC);
    }
    
    @Override
    public String getFullyQualifiedName() {
      return "hu.bme.mit.inf.friends.queries.friend";
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
          new TypeConstraint(body, Tuples.flatTupleOf(var_p1), new StringExactInstancesKey("Person"));
          new TypeConstraint(body, Tuples.flatTupleOf(var_p2), new StringExactInstancesKey("Person"));
          body.setSymbolicParameters(Arrays.<ExportedParameter>asList(
             new ExportedParameter(body, var_p1, parameter_p1),
             new ExportedParameter(body, var_p2, parameter_p2)
          ));
          // 	Person.friend(p1, p2)
          new TypeConstraint(body, Tuples.flatTupleOf(var_p1), new StringExactInstancesKey("Person"));
          PVariable var__virtual_0_ = body.getOrCreateVariableByName(".virtual{0}");
          new TypeConstraint(body, Tuples.flatTupleOf(var_p1, var__virtual_0_), new StringStructuralFeatureInstancesKey("Friend"));
          new TypeConstraint(body, Tuples.flatTupleOf(var__virtual_0_), new StringExactInstancesKey("Person"));
          new Equality(body, var__virtual_0_, var_p2);
          bodies.add(body);
      }
      return bodies;
    }
  }
}
