/**
 * Generated from platform:/resource/org.eclipse.viatra.query.runtime.tabular.generic.test/src/org/eclipse/viatra/query/runtime/tabular/generic/test/queries/test_queries.vql
 */
package org.eclipse.viatra.query.runtime.tabular.generic.test.queries;

import org.eclipse.viatra.query.runtime.api.ViatraQueryEngine;
import org.eclipse.viatra.query.runtime.api.impl.BaseGeneratedPatternGroup;
import org.eclipse.viatra.query.runtime.tabular.generic.test.queries.BinaryReflexiveTransitiveClosureTest;
import org.eclipse.viatra.query.runtime.tabular.generic.test.queries.BinaryTransitiveClosureTest;
import org.eclipse.viatra.query.runtime.tabular.generic.test.queries.EqualityTest;
import org.eclipse.viatra.query.runtime.tabular.generic.test.queries.FindTest;
import org.eclipse.viatra.query.runtime.tabular.generic.test.queries.InequalityTest;
import org.eclipse.viatra.query.runtime.tabular.generic.test.queries.NegFindTest;
import org.eclipse.viatra.query.runtime.tabular.generic.test.queries.RelationConstraintTest;
import org.eclipse.viatra.query.runtime.tabular.generic.test.queries.TypeConstraintTest;

/**
 * A pattern group formed of all public patterns defined in test_queries.vql.
 * 
 * <p>Use the static instance as any {@link interface org.eclipse.viatra.query.runtime.api.IQueryGroup}, to conveniently prepare
 * a VIATRA Query engine for matching all patterns originally defined in file test_queries.vql,
 * in order to achieve better performance than one-by-one on-demand matcher initialization.
 * 
 * <p> From package org.eclipse.viatra.query.runtime.tabular.generic.test.queries, the group contains the definition of the following patterns: <ul>
 * <li>typeConstraintTest</li>
 * <li>relationConstraintTest</li>
 * <li>findTest</li>
 * <li>negFindTest</li>
 * <li>equalityTest</li>
 * <li>inequalityTest</li>
 * <li>binaryTransitiveClosureTest</li>
 * <li>binaryReflexiveTransitiveClosureTest</li>
 * </ul>
 * 
 * @see IQueryGroup
 * 
 */
@SuppressWarnings("all")
public final class Test_queries extends BaseGeneratedPatternGroup {
  /**
   * Access the pattern group.
   * 
   * @return the singleton instance of the group
   * @throws ViatraQueryRuntimeException if there was an error loading the generated code of pattern specifications
   * 
   */
  public static Test_queries instance() {
    if (INSTANCE == null) {
        INSTANCE = new Test_queries();
    }
    return INSTANCE;
  }
  
  private static Test_queries INSTANCE;
  
  private Test_queries() {
    querySpecifications.add(TypeConstraintTest.instance());
    querySpecifications.add(RelationConstraintTest.instance());
    querySpecifications.add(FindTest.instance());
    querySpecifications.add(NegFindTest.instance());
    querySpecifications.add(EqualityTest.instance());
    querySpecifications.add(InequalityTest.instance());
    querySpecifications.add(BinaryTransitiveClosureTest.instance());
    querySpecifications.add(BinaryReflexiveTransitiveClosureTest.instance());
  }
  
  public TypeConstraintTest getTypeConstraintTest() {
    return TypeConstraintTest.instance();
  }
  
  public TypeConstraintTest.Matcher getTypeConstraintTest(final ViatraQueryEngine engine) {
    return TypeConstraintTest.Matcher.on(engine);
  }
  
  public RelationConstraintTest getRelationConstraintTest() {
    return RelationConstraintTest.instance();
  }
  
  public RelationConstraintTest.Matcher getRelationConstraintTest(final ViatraQueryEngine engine) {
    return RelationConstraintTest.Matcher.on(engine);
  }
  
  public FindTest getFindTest() {
    return FindTest.instance();
  }
  
  public FindTest.Matcher getFindTest(final ViatraQueryEngine engine) {
    return FindTest.Matcher.on(engine);
  }
  
  public NegFindTest getNegFindTest() {
    return NegFindTest.instance();
  }
  
  public NegFindTest.Matcher getNegFindTest(final ViatraQueryEngine engine) {
    return NegFindTest.Matcher.on(engine);
  }
  
  public EqualityTest getEqualityTest() {
    return EqualityTest.instance();
  }
  
  public EqualityTest.Matcher getEqualityTest(final ViatraQueryEngine engine) {
    return EqualityTest.Matcher.on(engine);
  }
  
  public InequalityTest getInequalityTest() {
    return InequalityTest.instance();
  }
  
  public InequalityTest.Matcher getInequalityTest(final ViatraQueryEngine engine) {
    return InequalityTest.Matcher.on(engine);
  }
  
  public BinaryTransitiveClosureTest getBinaryTransitiveClosureTest() {
    return BinaryTransitiveClosureTest.instance();
  }
  
  public BinaryTransitiveClosureTest.Matcher getBinaryTransitiveClosureTest(final ViatraQueryEngine engine) {
    return BinaryTransitiveClosureTest.Matcher.on(engine);
  }
  
  public BinaryReflexiveTransitiveClosureTest getBinaryReflexiveTransitiveClosureTest() {
    return BinaryReflexiveTransitiveClosureTest.instance();
  }
  
  public BinaryReflexiveTransitiveClosureTest.Matcher getBinaryReflexiveTransitiveClosureTest(final ViatraQueryEngine engine) {
    return BinaryReflexiveTransitiveClosureTest.Matcher.on(engine);
  }
}
