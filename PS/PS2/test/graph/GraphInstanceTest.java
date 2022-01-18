/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    /* Testing Strategy:
     * FIXME: I don't think this is necessary. The test is not a partition but a sequence of operation rather.
     *  also, the code will illustrate the sequence, and no need to write redundant comments.
     */

    private static final String string1 = "aaa";
    private static final String string2 = "bbb";

    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }

    @Test
    public void testAdd() {
        Graph<String> graph = emptyInstance();
        assertTrue(graph.add(string1));
        assertFalse(graph.add(string1));
        assertTrue(graph.add(string2));
        assertFalse(graph.add(string2));
    }

    @Test
    public void testRemove() {
        Graph<String> graph = emptyInstance();
        assertTrue(graph.add(string1));
        assertTrue(graph.add(string2));
        assertEquals(0, graph.set(string1, string2, 1));
        assertEquals(1, graph.sources(string2).size());
        assertTrue(graph.remove(string1));
        assertEquals(0, graph.sources(string2).size());
        assertTrue(graph.remove(string2));
        assertFalse(graph.remove(string1));
        assertFalse(graph.remove(string2));
    }

    @Test
    public void testSet() {
        Graph<String> graph = emptyInstance();
        assertTrue(graph.add(string1));
        assertTrue(graph.add(string2));
        assertEquals(0, graph.set(string1, string2, 1));
        assertEquals(1, graph.set(string1, string2, 2));
        assertEquals(0, graph.set(string2, string1, 3));
        assertEquals(3, graph.set(string2, string1, 4));
    }

    @Test
    public void testVertices() {
        Graph<String> graph = emptyInstance();
        assertTrue(graph.add(string1));
        assertTrue(graph.add(string2));
        assertEquals(2, graph.vertices().size());
        assertTrue(graph.vertices().contains(string1));
        assertTrue(graph.vertices().contains(string2));
        assertTrue(graph.remove(string2));
        assertEquals(1, graph.vertices().size());
        assertTrue(graph.vertices().contains(string1));
        assertFalse(graph.vertices().contains(string2));
    }

    @Test
    public void testSources() {
        Graph<String> graph = emptyInstance();
        assertTrue(graph.add(string1));
        assertTrue(graph.add(string2));
        assertEquals(0, graph.sources(string1).keySet().size());
        assertEquals(0, graph.sources(string2).keySet().size());
        assertEquals(0, graph.set(string1, string2, 3));
        assertEquals(0, graph.set(string2, string1, 4));
        assertEquals(1, graph.sources(string1).keySet().size());
        assertEquals(1, graph.sources(string2).keySet().size());
        assertEquals(Integer.valueOf(3), graph.sources(string2).get(string1));
        assertEquals(Integer.valueOf(4), graph.sources(string1).get(string2));
    }

    @Test
    public void testTargets() {
        Graph<String> graph = emptyInstance();
        assertTrue(graph.add(string1));
        assertTrue(graph.add(string2));
        assertEquals(0, graph.targets(string1).keySet().size());
        assertEquals(0, graph.targets(string2).keySet().size());
        assertEquals(0, graph.set(string1, string2, 3));
        assertEquals(0, graph.set(string2, string1, 4));
        assertEquals(1, graph.targets(string1).keySet().size());
        assertEquals(1, graph.targets(string2).keySet().size());
        assertEquals(Integer.valueOf(3), graph.targets(string1).get(string2));
        assertEquals(Integer.valueOf(4), graph.targets(string2).get(string1));
    }
}
