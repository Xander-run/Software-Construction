/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import static org.junit.Assert.*;

import graph.Graph;
import org.junit.Test;

import java.util.Map;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {

    // Testing strategy
    //   TODO

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }


    // Should I write unit test on private method?
    // Can see here: https://softwareengineering.stackexchange.com/questions/100959/how-do-you-unit-test-private-methods
    // In short: You Shouldn't.
    @Test
    public void testExpendGraphFromString() {
        GraphPoet graphPoet = new GraphPoet("");
        assertEquals(0, graphPoet.getGraph().vertices().size());
        graphPoet.expendGraphFromString("   ");
        assertEquals(0, graphPoet.getGraph().vertices().size());
        graphPoet.expendGraphFromString("Hello, HELLO, hello, goodbye!");
        assertEquals(2, graphPoet.getGraph().vertices().size());
        assertEquals(0, graphPoet.getGraph().targets("random").size());
        assertEquals(Integer.valueOf(1), graphPoet.getGraph().targets("hello,").get("goodbye!"));
        assertEquals(Integer.valueOf(2), graphPoet.getGraph().targets("hello,").get("hello,"));

    }

    @Test
    public void testPoem() {
        GraphPoet graphPoet = new GraphPoet("This is a test of the Mugar Omni Theater sound system.");
        assertEquals("Test of the system.", graphPoet.poem("Test the system."));
    }

}
