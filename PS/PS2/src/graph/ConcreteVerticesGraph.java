/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import com.sun.javafx.collections.MappingChange;

import java.util.*;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph implements Graph<String> {
    
    private final List<Vertex> vertices;
    
    // Abstraction function:
    //   AF(vertices) -> the graph that has and only has the vertices
    // Representation invariant:
    //   All the vertices are valid
    // Safety from rep exposure:
    //   Vertices are made final and private
    //   The get method of vertices returns a defensive copy
    //   and the get methods of the sourceWeightMap and targetWeightMap return defensive copy
    
    public ConcreteVerticesGraph() {
        vertices = new ArrayList<>();
    }

    // lazy change, so the checkRep() is left empty
    public void checkRep() {}
    
    @Override public boolean add(String vertex) {
        for (Vertex containedVertex : vertices) {
            if (containedVertex.getValue().equals(vertex)) {
                return false;
            }
        }
        vertices.add(new Vertex(vertex));
        return true;
    }
    
    @Override public int set(String source, String target, int weight) {
        int weightBefore = -1;
        boolean found = false;

        // set the source vertex
        for (Vertex theVertex : vertices) {
            if (theVertex.getValue().equals(source)) {
                if (theVertex.getTargetWeightMap().containsKey(target)) {
                    found = true;
                    weightBefore = theVertex.getTargetWeightMap().get(target);
                }
                if (weight == 0) {
                    theVertex.getTargetWeightMap().remove(target);
                } else {
                    theVertex.getTargetWeightMap().put(target, weight);
                }
                break;
            }
        }

        // set the target vertex
        for (Vertex theVertex : vertices) {
            if (theVertex.getValue().equals(target)) {
                if (weight == 0) {
                    theVertex.getSourceWeightMap().remove(source);
                } else {
                    theVertex.getSourceWeightMap().put(source, weight);
                }
                break;
            }
        }

        if (found) {
            return weightBefore;
        } else {
            return 0;
        }
    }
    
    @Override public boolean remove(String vertex) {
        boolean ret = false;
        for (Vertex theVertex : vertices) {
            if (theVertex.getValue().equals(vertex)) {
                vertices.remove(theVertex);
                ret = true;
                break;
            }
        }
        return ret;
    }
    
    @Override public Set<String> vertices() {
        Set<String> ret = new HashSet<>();
        for (Vertex vertex : vertices) {
            ret.add(vertex.getValue());
        }
        return ret;
    }
    
    @Override public Map<String, Integer> sources(String target) {
        Set<String> validVertices = vertices();
        for (Vertex vertex : vertices) {
            if (vertex.getValue().equals(target)) {
                for (String source : vertex.getSourceWeightMap().keySet()) {
                    if (!validVertices.contains(source)) {
                        vertex.getSourceWeightMap().remove(source);
                    }
                }
                return new HashMap<>(vertex.getSourceWeightMap());
            }
        }
        return null;
    }
    
    @Override public Map<String, Integer> targets(String source) {
        Set<String> validVertices = vertices();
        for (Vertex vertex : vertices) {
            if (vertex.getValue().equals(source)) {
                for (String target : vertex.getTargetWeightMap().keySet()) {
                    if (!validVertices.contains(target)) {
                        vertex.getTargetWeightMap().remove(target);
                    }
                }
                return new HashMap<>(vertex.getTargetWeightMap());
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "ConcreteVerticesGraph{" +
                "vertices=" + vertices +
                '}';
    }
}

/**
 * TODO specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex {
    
    private final Map<String, Integer> sourceWeightMap;
    private final Map<String, Integer> targetWeightMap;
    private final String theString;
    
    // Abstraction function:
    //   AF(sourceWeightMap, targetWeightMap, theString) = a legal vertex within the graph
    // Representation invariant:
    //   the vertex exists
    // Safety from rep exposure:
    //   All fields are made private and final
    
    public Vertex(String theString) {
        sourceWeightMap = new HashMap<>();
        targetWeightMap = new HashMap<>();
        this.theString = theString;
        checkRep();
    }

    private void checkRep() {}

    public Map<String, Integer> getSourceWeightMap() {
        return sourceWeightMap;
    }

    public Map<String, Integer> getTargetWeightMap() {
        return targetWeightMap;
    }

    public String getValue() {
        return theString;
    }

    public void addSource(String theString, int weight) {
        sourceWeightMap.put(theString, weight);
    }

    public void addTarget(String theString, int weight) {
        targetWeightMap.put(theString, weight);
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "sourceWeightMap=" + sourceWeightMap +
                ", targetWeightMap=" + targetWeightMap +
                ", theString='" + theString + '\'' +
                '}';
    }
}
