/**
 * Generated from platform:/resource/hu.bme.mit.inf.Friends.queries/src/hu/bme/mit/inf/Friends/queries/queries.vql
 */
package hu.bme.mit.inf.friends.queries;

import org.eclipse.viatra.query.runtime.api.ViatraQueryEngine;
import org.eclipse.viatra.query.runtime.api.impl.BaseGeneratedPatternGroup;

/**
 * A pattern group formed of all public patterns defined in queries.vql.
 * 
 * <p>Use the static instance as any {@link interface org.eclipse.viatra.query.runtime.api.IQueryGroup}, to conveniently prepare
 * a VIATRA Query engine for matching all patterns originally defined in file queries.vql,
 * in order to achieve better performance than one-by-one on-demand matcher initialization.
 * 
 * <p> From package hu.bme.mit.inf.Friends.queries, the group contains the definition of the following patterns: <ul>
 * <li>Friend</li>
 * <li>FriendCircle</li>
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
    querySpecifications.add(HandCraftedFriend2.instance());
    querySpecifications.add(HandCraftedFriendCircle.instance());
  }
  
  public HandCraftedFriend getFriend() {
    return HandCraftedFriend.instance();
  }
  
  public HandCraftedFriend.Matcher getFriend(final ViatraQueryEngine engine) {
    return HandCraftedFriend.Matcher.on(engine);
  }
  
  public HandCraftedFriend2 getFriend2() {
    return HandCraftedFriend2.instance();
  }
  
  public HandCraftedFriend2.Matcher getFriend2(final ViatraQueryEngine engine) {
    return HandCraftedFriend2.Matcher.on(engine);
  }
  
  public HandCraftedFriendCircle getFriendCircle() {
    return HandCraftedFriendCircle.instance();
  }
  
  public HandCraftedFriendCircle.Matcher getFriendCircle(final ViatraQueryEngine engine) {
    return HandCraftedFriendCircle.Matcher.on(engine);
  }
}
