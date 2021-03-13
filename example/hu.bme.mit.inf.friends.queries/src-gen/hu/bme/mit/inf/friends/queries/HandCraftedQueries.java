/**
 * Generated from platform:/resource/hu.bme.mit.inf.Friend2s.queries/src/hu/bme/mit/inf/Friend2s/queries/queries.vql
 */
package hu.bme.mit.inf.friends.queries;

import hu.bme.mit.inf.friends.queries.HandCraftedFriend;
//import hu.bme.mit.inf.Friend2s.queries.Friend2Circle;
import org.eclipse.viatra.query.runtime.api.ViatraQueryEngine;
import org.eclipse.viatra.query.runtime.api.impl.BaseGeneratedPatternGroup;

/**
 * A pattern group formed of all public patterns defined in queries.vql.
 * 
 * <p>Use the static instance as any {@link interface org.eclipse.viatra.query.runtime.api.IQueryGroup}, to conveniently prepare
 * a VIATRA Query engine for matching all patterns originally defined in file queries.vql,
 * in order to achieve better performance than one-by-one on-demand matcher initialization.
 * 
 * <p> From package hu.bme.mit.inf.Friend2s.queries, the group contains the definition of the following patterns: <ul>
 * <li>Friend2</li>
 * <li>Friend2Circle</li>
 * </ul>
 * 
 * @see IQueryGroup
 * 
 */
@SuppressWarnings("all")
public final class HandCraftedQueries extends BaseGeneratedPatternGroup {
  /**
   * Access the pattern group.
   * 
   * @return the singleton instance of the group
   * @throws ViatraQueryRuntimeException if there was an error loading the generated code of pattern specifications
   * 
   */
  public static HandCraftedQueries instance() {
    if (INSTANCE == null) {
        INSTANCE = new HandCraftedQueries();
    }
    return INSTANCE;
  }
  
  private static HandCraftedQueries INSTANCE;
  
  private HandCraftedQueries() {
    querySpecifications.add(HandCraftedFriend.instance());
  }
  
  public HandCraftedFriend getFriend2() {
    return HandCraftedFriend.instance();
  }
  
  public HandCraftedFriend.Matcher getFriend2(final ViatraQueryEngine engine) {
    return HandCraftedFriend.Matcher.on(engine);
  }
}
