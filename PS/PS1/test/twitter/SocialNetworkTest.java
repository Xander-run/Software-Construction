/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.*;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * Partition for guessFollowsGraph(List<Tweet> tweets) -> Map<String, Set<String>>
     *     tweets.size() == 0, > 0
     *
     * Partition for influencers(Map<String, Set<String>> followsGraph) -> List<String>
     *     followsGraph followsGraph K-V pair number == 0, > 0
     */
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "@bbitdiddle is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "@alyssa rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(2, "bob", "@alyssa rivest talk in 30 minutes #hype", d2);

    private static final String bobName = "Bob";
    private static final Set<String> bobFollowers = new HashSet<>();
    private static final String ericName = "Eric";
    private static final Set<String> ericFollowers = new HashSet<>();

    private static final Map<String, Set<String>> followsGraph1 = new HashMap<>();
    private static final List<Tweet> tweetList1 = new LinkedList<>();

    static {
        tweetList1.add(tweet1);
        tweetList1.add(tweet2);
        tweetList1.add(tweet3);
        bobFollowers.addAll(Arrays.asList("A", "B", "C"));
        ericFollowers.addAll(Arrays.asList("A", "B"));
        followsGraph1.put(bobName, bobFollowers);
        followsGraph1.put(ericName, ericFollowers);
    }

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }

    @Test
    public void testGuessFollowsGraphNonEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweetList1);
        assertTrue("Excepted non-empty Set", !followsGraph.isEmpty());
    }
    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }

    @Test
    public void testInfluencersNonEmpty() {
        List<String> influencers = SocialNetwork.influencers(followsGraph1);

        assertTrue("Expected non-empty list", !influencers.isEmpty());
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
