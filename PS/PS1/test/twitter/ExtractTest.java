/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * Partition for getTimespan(List<Tweet> tweets) -> Timespan:
     *     tweets.size() == 1, > 1
     *
     * Partition  for getMentionedUsers(List<Tweet> tweets) -> Set<String>:
     *    mentioned mentioned users number == 0, > 0
     *    contains case sensitive issue or not
     *    contains tricky case like bitdiddle@mit.edu or not
     *    tweets.size() == 1, > 1
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "alyssa", "hello @bob, this is my friend @michael", d1);
    private static final Tweet tweet4 = new Tweet(4, "alyssa", "@java @JAVA @Java, are u ok?", d1);
    private static final Tweet tweet5 = new Tweet(5, "alyssa", "Email @bob, and here is his email address: bob@somesite.com @@", d1);


    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    // This test covers tweets.size() > 1
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }

    // This test covers tweets.size() == 1
    @Test
    public void testGetTimespanOneTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));

        assertEquals("Expected start", d1, timespan.getStart());
        assertEquals("Expected end", d1, timespan.getStart());
    }

    // This test covers mentioned users number == 0
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }


    // This test covers mentioned user number > 0
    @Test
    public void testGetMentionedUsersMentionedTwice() {
        Set<String> mentionedUsers = Extract.getMentionedUsers((Arrays.asList(tweet3)));

        assertEquals("expected set with size 2", 2, mentionedUsers.size());
    }

    // This test covers sensitive issue
    @Test
    public void testGetMentionedUsersCaseSensitive() {
        Set<String> mentionedUsers = Extract.getMentionedUsers((Arrays.asList(tweet4)));

        assertEquals("expected set with size 1", 1, mentionedUsers.size());
    }

    // This test tricky case like bitdiddle@mit.edu
    @Test
    public void testGetMentionedUsersTrickyCase() {
        Set<String> mentionedUsers = Extract.getMentionedUsers((Arrays.asList(tweet5)));

        assertEquals("expected set with size 1", 1, mentionedUsers.size());
    }

    // This test tweets.size() > 1
    @Test
    public void testGetMentionedUsersWithTwoTweets() {
        Set<String> mentionedUsers = Extract.getMentionedUsers((Arrays.asList(tweet3, tweet4)));

        assertEquals("expected set with size 3", 3, mentionedUsers.size());
    }

    @Test
    public void testIsAllowedTweetUserChar() {
        assertTrue(Extract.isAllowedTweetUserChar('a'));
        assertTrue(Extract.isAllowedTweetUserChar('e'));
        assertTrue(Extract.isAllowedTweetUserChar('z'));
        assertTrue(Extract.isAllowedTweetUserChar('A'));
        assertTrue(Extract.isAllowedTweetUserChar('E'));
        assertTrue(Extract.isAllowedTweetUserChar('Z'));
        assertTrue(Extract.isAllowedTweetUserChar('_'));
        assertTrue(Extract.isAllowedTweetUserChar('-'));
        assertTrue(Extract.isAllowedTweetUserChar('-'));
        assertTrue(!Extract.isAllowedTweetUserChar('8'));
    }
    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
