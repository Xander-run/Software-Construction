/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * Partition for writtenBy(List<Tweet> tweets, String username) -> List<Tweet>:
     *     tweets.size() == 0, > 0
     *     the size of List<Tweet> > 0 or == 0
     *
     * Partition for inTimespan(List<Tweet> tweets, Timespan timespan) -> List<Tweet>:
     *     timespan == 0, > 0
     *     the size of List<Tweet> > 0 or == 0
     *
     * Partition for containing(List<Tweet> tweets, List<String> words) -> List<Tweet>:
     *     words.size() == 0, > 0
     *     words.size() == 0, = 1, > 1
     *     the size of List<Tweet> > 0 or == 0
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "bbitdiddle", "rivest talk about in 30 minutes #hype", d2);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    // This test covers the size of List<Tweet> is > 0
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }

    // This test covers the size of List<Tweet> is == 0
    @Test
    public void testWrittenByMultipleTweetsNoResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "bob");

        assertEquals("expected empty list", 0, writtenBy.size());
    }

    // This test covers tweets.size() == 0
    @Test
    public void testWrittenByZeroTweetsInput() {
        List<Tweet> writtenBy = Filter.writtenBy(new ArrayList<>(), "bob");

        assertEquals("expected empty list", 0, writtenBy.size());
    }


    // This test the size of List<Tweet> > 0
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }

    // This test the size of List<Tweet> == 0
    @Test
    public void testInTimespanMultipleTweetsNoResults() {
        Instant testStart = Instant.parse("2016-02-18T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-18T12:00:00Z");

        List<Tweet> inTimespan = Filter.inTimespan(new ArrayList<>(), new Timespan(testStart, testEnd));

        assertTrue("expected empty list", inTimespan.isEmpty());
    }

    // This test timespan == 0
    @Test
    public void testInTimespanZeroTweetsNoResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T09:00:00Z");

        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));

        assertTrue("expected empty list", inTimespan.isEmpty());
    }


    // This test covers List<Tweet> size > 0
    @Test
    public void testContainingMultipleTweetsSingleWord() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }

    // This test covers List<Tweet> size == 0
    @Test
    public void testContainingEmptyOutput() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("asdfs"));

        assertTrue("expected empty list", containing.isEmpty());
    }

    // This test covers tweets size == 0
    @Test
    public void testContainingEmptyInput() {
        List<Tweet> containing = Filter.containing(new ArrayList<>(), Arrays.asList("talk"));

        assertTrue("expected empty list", containing.isEmpty());
    }

    // This test covers tweets.size() > 0 && words.size() > 0
    @Test
    public void testContainingMultipleTweetsMultipleWords() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3), Arrays.asList("talk", "about"));

        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet3)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
        assertEquals("expected same order", 1, containing.indexOf(tweet3));
    }

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
