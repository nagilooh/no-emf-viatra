/**
 * Generated from platform:/resource/hu.bme.mit.inf.friends.queries/src/hu/bme/mit/inf/friends/queries/queries.vql
 */
package hu.bme.mit.inf.friends.queries;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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
//import org.eclipse.viatra.query.runtime.emf.types.EClassTransitiveInstancesKey;
//import org.eclipse.viatra.query.runtime.emf.types.EStructuralFeatureInstancesKey;
import org.eclipse.viatra.query.runtime.tabular.generic.types.StringExactInstancesKey;
import org.eclipse.viatra.query.runtime.tabular.generic.types.StringStructuralFeatureInstancesKey;
import org.eclipse.viatra.query.runtime.matchers.backend.QueryEvaluationHint;
import org.eclipse.viatra.query.runtime.matchers.psystem.PBody;
import org.eclipse.viatra.query.runtime.matchers.psystem.PVariable;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicdeferred.Equality;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicdeferred.ExportedParameter;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicenumerables.BinaryTransitiveClosure;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicenumerables.TypeConstraint;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PParameter;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PParameterDirection;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PVisibility;
import org.eclipse.viatra.query.runtime.matchers.tuple.Tuple;
import org.eclipse.viatra.query.runtime.matchers.tuple.Tuples;
import org.eclipse.viatra.query.runtime.util.ViatraQueryLoggingUtil;

/**
 * A pattern-specific query specification that can instantiate Matcher in a type-safe way.
 * 
 * <p>Original source:
 *         <code><pre>
 *         pattern friendCircle(p : Person) {
 *         	Person.friend+(p, p);
 *         }
 * </pre></code>
 * 
 * @see Matcher
 * @see Match
 * 
 */
@SuppressWarnings("all")
public final class HandCraftedFriendCircle extends BaseGeneratedEMFQuerySpecification<HandCraftedFriendCircle.Matcher> {
  /**
   * Pattern-specific match representation of the hu.bme.mit.inf.friends.queries.friendCircle pattern,
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
    private String fP;
    
    private static List<String> parameterNames = makeImmutableList("p");
    
    private Match(final String pP) {
      this.fP = pP;
    }
    
    @Override
    public Object get(final String parameterName) {
      switch(parameterName) {
          case "p": return this.fP;
          default: return null;
      }
    }
    
    @Override
    public Object get(final int index) {
      switch(index) {
          case 0: return this.fP;
          default: return null;
      }
    }
    
    public String getP() {
      return this.fP;
    }
    
    @Override
    public boolean set(final String parameterName, final Object newValue) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      if ("p".equals(parameterName) ) {
          this.fP = (String) newValue;
          return true;
      }
      return false;
    }
    
    public void setP(final String pP) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      this.fP = pP;
    }
    
    @Override
    public String patternName() {
      return "hu.bme.mit.inf.friends.queries.friendCircle";
    }
    
    @Override
    public List<String> parameterNames() {
      return HandCraftedFriendCircle.Match.parameterNames;
    }
    
    @Override
    public Object[] toArray() {
      return new Object[]{fP};
    }
    
    @Override
    public HandCraftedFriendCircle.Match toImmutable() {
      return isMutable() ? newMatch(fP) : this;
    }
    
    @Override
    public String prettyPrint() {
      StringBuilder result = new StringBuilder();
      result.append("\"p\"=" + prettyPrintValue(fP));
      return result.toString();
    }
    
    @Override
    public int hashCode() {
      return Objects.hash(fP);
    }
    
    @Override
    public boolean equals(final Object obj) {
      if (this == obj)
          return true;
      if (obj == null) {
          return false;
      }
      if ((obj instanceof HandCraftedFriendCircle.Match)) {
          HandCraftedFriendCircle.Match other = (HandCraftedFriendCircle.Match) obj;
          return Objects.equals(fP, other.fP);
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
    public HandCraftedFriendCircle specification() {
      return HandCraftedFriendCircle.instance();
    }
    
    /**
     * Returns an empty, mutable match.
     * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
     * 
     * @return the empty match.
     * 
     */
    public static HandCraftedFriendCircle.Match newEmptyMatch() {
      return new Mutable(null);
    }
    
    /**
     * Returns a mutable (partial) match.
     * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
     * 
     * @param pP the fixed value of pattern parameter p, or null if not bound.
     * @return the new, mutable (partial) match object.
     * 
     */
    public static HandCraftedFriendCircle.Match newMutableMatch(final String pP) {
      return new Mutable(pP);
    }
    
    /**
     * Returns a new (partial) match.
     * This can be used e.g. to call the matcher with a partial match.
     * <p>The returned match will be immutable. Use {@link #newEmptyMatch()} to obtain a mutable match object.
     * @param pP the fixed value of pattern parameter p, or null if not bound.
     * @return the (partial) match object.
     * 
     */
    public static HandCraftedFriendCircle.Match newMatch(final String pP) {
      return new Immutable(pP);
    }
    
    private static final class Mutable extends HandCraftedFriendCircle.Match {
      Mutable(final String pP) {
        super(pP);
      }
      
      @Override
      public boolean isMutable() {
        return true;
      }
    }
    
    private static final class Immutable extends HandCraftedFriendCircle.Match {
      Immutable(final String pP) {
        super(pP);
      }
      
      @Override
      public boolean isMutable() {
        return false;
      }
    }
  }
  
  /**
   * Generated pattern matcher API of the hu.bme.mit.inf.friends.queries.friendCircle pattern,
   * providing pattern-specific query methods.
   * 
   * <p>Use the pattern matcher on a given model via {@link #on(ViatraQueryEngine)},
   * e.g. in conjunction with {@link ViatraQueryEngine#on(QueryScope)}.
   * 
   * <p>Matches of the pattern will be represented as {@link Match}.
   * 
   * <p>Original source:
   * <code><pre>
   * pattern friendCircle(p : Person) {
   * 	Person.friend+(p, p);
   * }
   * </pre></code>
   * 
   * @see Match
   * @see HandCraftedFriendCircle
   * 
   */
  public static class Matcher extends BaseMatcher<HandCraftedFriendCircle.Match> {
    /**
     * Initializes the pattern matcher within an existing VIATRA Query engine.
     * If the pattern matcher is already constructed in the engine, only a light-weight reference is returned.
     * 
     * @param engine the existing VIATRA Query engine in which this matcher will be created.
     * @throws ViatraQueryRuntimeException if an error occurs during pattern matcher creation
     * 
     */
    public static HandCraftedFriendCircle.Matcher on(final ViatraQueryEngine engine) {
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
    public static HandCraftedFriendCircle.Matcher create() {
      return new Matcher();
    }
    
    private static final int POSITION_P = 0;
    
    private static final Logger LOGGER = ViatraQueryLoggingUtil.getLogger(HandCraftedFriendCircle.Matcher.class);
    
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
     * @param pP the fixed value of pattern parameter p, or null if not bound.
     * @return matches represented as a Match object.
     * 
     */
    public Collection<HandCraftedFriendCircle.Match> getAllMatches(final String pP) {
      return rawStreamAllMatches(new Object[]{pP}).collect(Collectors.toSet());
    }
    
    /**
     * Returns a stream of all matches of the pattern that conform to the given fixed values of some parameters.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     * @param pP the fixed value of pattern parameter p, or null if not bound.
     * @return a stream of matches represented as a Match object.
     * 
     */
    public Stream<HandCraftedFriendCircle.Match> streamAllMatches(final String pP) {
      return rawStreamAllMatches(new Object[]{pP});
    }
    
    /**
     * Returns an arbitrarily chosen match of the pattern that conforms to the given fixed values of some parameters.
     * Neither determinism nor randomness of selection is guaranteed.
     * @param pP the fixed value of pattern parameter p, or null if not bound.
     * @return a match represented as a Match object, or null if no match is found.
     * 
     */
    public Optional<HandCraftedFriendCircle.Match> getOneArbitraryMatch(final String pP) {
      return rawGetOneArbitraryMatch(new Object[]{pP});
    }
    
    /**
     * Indicates whether the given combination of specified pattern parameters constitute a valid pattern match,
     * under any possible substitution of the unspecified parameters (if any).
     * @param pP the fixed value of pattern parameter p, or null if not bound.
     * @return true if the input is a valid (partial) match of the pattern.
     * 
     */
    public boolean hasMatch(final String pP) {
      return rawHasMatch(new Object[]{pP});
    }
    
    /**
     * Returns the number of all matches of the pattern that conform to the given fixed values of some parameters.
     * @param pP the fixed value of pattern parameter p, or null if not bound.
     * @return the number of pattern matches found.
     * 
     */
    public int countMatches(final String pP) {
      return rawCountMatches(new Object[]{pP});
    }
    
    /**
     * Executes the given processor on an arbitrarily chosen match of the pattern that conforms to the given fixed values of some parameters.
     * Neither determinism nor randomness of selection is guaranteed.
     * @param pP the fixed value of pattern parameter p, or null if not bound.
     * @param processor the action that will process the selected match.
     * @return true if the pattern has at least one match with the given parameter values, false if the processor was not invoked
     * 
     */
    public boolean forOneArbitraryMatch(final String pP, final Consumer<? super HandCraftedFriendCircle.Match> processor) {
      return rawForOneArbitraryMatch(new Object[]{pP}, processor);
    }
    
    /**
     * Returns a new (partial) match.
     * This can be used e.g. to call the matcher with a partial match.
     * <p>The returned match will be immutable. Use {@link #newEmptyMatch()} to obtain a mutable match object.
     * @param pP the fixed value of pattern parameter p, or null if not bound.
     * @return the (partial) match object.
     * 
     */
    public HandCraftedFriendCircle.Match newMatch(final String pP) {
      return HandCraftedFriendCircle.Match.newMatch(pP);
    }
    
    /**
     * Retrieve the set of values that occur in matches for p.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    protected Stream<String> rawStreamAllValuesOfp(final Object[] parameters) {
      return rawStreamAllValues(POSITION_P, parameters).map(String.class::cast);
    }
    
    /**
     * Retrieve the set of values that occur in matches for p.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<String> getAllValuesOfp() {
      return rawStreamAllValuesOfp(emptyArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for p.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Stream<String> streamAllValuesOfp() {
      return rawStreamAllValuesOfp(emptyArray());
    }
    
    @Override
    protected HandCraftedFriendCircle.Match tupleToMatch(final Tuple t) {
      try {
          return HandCraftedFriendCircle.Match.newMatch((String) t.get(POSITION_P));
      } catch(ClassCastException e) {
          LOGGER.error("Element(s) in tuple not properly typed!",e);
          return null;
      }
    }
    
    @Override
    protected HandCraftedFriendCircle.Match arrayToMatch(final Object[] match) {
      try {
          return HandCraftedFriendCircle.Match.newMatch((String) match[POSITION_P]);
      } catch(ClassCastException e) {
          LOGGER.error("Element(s) in array not properly typed!",e);
          return null;
      }
    }
    
    @Override
    protected HandCraftedFriendCircle.Match arrayToMatchMutable(final Object[] match) {
      try {
          return HandCraftedFriendCircle.Match.newMutableMatch((String) match[POSITION_P]);
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
    public static IQuerySpecification<HandCraftedFriendCircle.Matcher> querySpecification() {
      return HandCraftedFriendCircle.instance();
    }
  }
  
  private HandCraftedFriendCircle() {
    super(GeneratedPQuery.INSTANCE);
  }
  
  /**
   * @return the singleton instance of the query specification
   * @throws ViatraQueryRuntimeException if the pattern definition could not be loaded
   * 
   */
  public static HandCraftedFriendCircle instance() {
    try{
        return LazyHolder.INSTANCE;
    } catch (ExceptionInInitializerError err) {
        throw processInitializerError(err);
    }
  }
  
  @Override
  protected HandCraftedFriendCircle.Matcher instantiate(final ViatraQueryEngine engine) {
    return HandCraftedFriendCircle.Matcher.on(engine);
  }
  
  @Override
  public HandCraftedFriendCircle.Matcher instantiate() {
    return HandCraftedFriendCircle.Matcher.create();
  }
  
  @Override
  public HandCraftedFriendCircle.Match newEmptyMatch() {
    return HandCraftedFriendCircle.Match.newEmptyMatch();
  }
  
  @Override
  public HandCraftedFriendCircle.Match newMatch(final Object... parameters) {
    return HandCraftedFriendCircle.Match.newMatch((String) parameters[0]);
  }
  
  /**
   * Inner class allowing the singleton instance of {@link HandCraftedFriendCircle} to be created 
   *     <b>not</b> at the class load time of the outer class, 
   *     but rather at the first call to {@link HandCraftedFriendCircle#instance()}.
   * 
   * <p> This workaround is required e.g. to support recursion.
   * 
   */
  private static class LazyHolder {
    private static final HandCraftedFriendCircle INSTANCE = new HandCraftedFriendCircle();
    
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
    private static final HandCraftedFriendCircle.GeneratedPQuery INSTANCE = new GeneratedPQuery();
    
    private final PParameter parameter_p = new PParameter("p", "String", new StringExactInstancesKey("Person"), PParameterDirection.INOUT);
    
    private final List<PParameter> parameters = Arrays.asList(parameter_p);
    
    private class Embedded_1_Person_friend extends BaseGeneratedEMFPQuery {
      private final PParameter parameter_p0 = new PParameter("p0", "String", new StringExactInstancesKey("Person"), PParameterDirection.INOUT);
      
      private final PParameter parameter_p1 = new PParameter("p1", "String", new StringExactInstancesKey("Person"), PParameterDirection.INOUT);
      
      private final List<PParameter> embeddedParameters = Arrays.asList(parameter_p0, parameter_p1);
      
      public Embedded_1_Person_friend() {
        super(PVisibility.EMBEDDED);
      }
      
      @Override
      public String getFullyQualifiedName() {
        return GeneratedPQuery.this.getFullyQualifiedName() + "$Embedded_1_Person_friend";
      }
      
      @Override
      public List<PParameter> getParameters() {
        return embeddedParameters;
      }
      
      @Override
      public Set<PBody> doGetContainedBodies() {
        PBody body = new PBody(this);
        PVariable var_p0 = body.getOrCreateVariableByName("p0");
        PVariable var_p1 = body.getOrCreateVariableByName("p1");
        body.setSymbolicParameters(Arrays.<ExportedParameter>asList(
           new ExportedParameter(body, var_p0, parameter_p0),
           new ExportedParameter(body, var_p1, parameter_p1)
        ));
        // 	Person.friend+(p, p)
        new TypeConstraint(body, Tuples.flatTupleOf(var_p0), new StringExactInstancesKey("Person"));
        PVariable var__virtual_0_ = body.getOrCreateVariableByName(".virtual{0}");
        new TypeConstraint(body, Tuples.flatTupleOf(var_p0, var__virtual_0_), new StringStructuralFeatureInstancesKey("Friend"));
        new TypeConstraint(body, Tuples.flatTupleOf(var__virtual_0_), new StringExactInstancesKey("Person"));
        new Equality(body, var__virtual_0_, var_p1);
        return Collections.singleton(body);
      }
    }
    
    private GeneratedPQuery() {
      super(PVisibility.PUBLIC);
    }
    
    @Override
    public String getFullyQualifiedName() {
      return "hu.bme.mit.inf.friends.queries.friendCircle";
    }
    
    @Override
    public List<String> getParameterNames() {
      return Arrays.asList("p");
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
          PVariable var_p = body.getOrCreateVariableByName("p");
          new TypeConstraint(body, Tuples.flatTupleOf(var_p), new StringExactInstancesKey("Person"));
          body.setSymbolicParameters(Arrays.<ExportedParameter>asList(
             new ExportedParameter(body, var_p, parameter_p)
          ));
          // 	Person.friend+(p, p)
          new BinaryTransitiveClosure(body, Tuples.flatTupleOf(var_p, var_p), new HandCraftedFriendCircle.GeneratedPQuery.Embedded_1_Person_friend());
          bodies.add(body);
      }
      return bodies;
    }
  }
}
