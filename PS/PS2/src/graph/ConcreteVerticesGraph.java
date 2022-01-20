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
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices;
    
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
    
    @Override public boolean add(L vertex) {
        for (Vertex<L> containedVertex : vertices) {
            if (containedVertex.getValue().equals(vertex)) {
                return false;
            }
        }
        vertices.add(new Vertex<>(vertex));
        return true;
    }
    
    @Override public int set(L source, L target, int weight) {
        int weightBefore = -1;
        boolean found = false;

        // set the source vertex
        for (Vertex<L> theVertex : vertices) {
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
        for (Vertex<L> theVertex : vertices) {
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
    
    @Override public boolean remove(L vertex) {
        boolean ret = false;
        for (Vertex<L> theVertex : vertices) {
            if (theVertex.getValue().equals(vertex)) {
                vertices.remove(theVertex);
                ret = true;
                break;
            }
        }
        return ret;
    }
    
    @Override public Set<L> vertices() {
        Set<L> ret = new HashSet<>();
        for (Vertex<L> vertex : vertices) {
            ret.add(vertex.getValue());
        }
        return ret;
    }
    
    @Override public Map<L, Integer> sources(L target) {
        Set<L> validVertices = vertices();
        for (Vertex<L> vertex : vertices) {
            if (vertex.getValue().equals(target)) {
                for (L source : vertex.getSourceWeightMap().keySet()) {
                    if (!validVertices.contains(source)) {
                        vertex.getSourceWeightMap().remove(source);
                    }
                }
                return new HashMap<>(vertex.getSourceWeightMap());
            }
        }
        return null;
    }
    
    @Override public Map<L, Integer> targets(L source) {
        Set<L> validVertices = vertices();
        for (Vertex<L> vertex : vertices) {
            if (vertex.getValue().equals(source)) {
                for (L target : vertex.getTargetWeightMap().keySet()) {
                    if (!validVertices.contains(target)) {
                        vertex.getTargetWeightMap().remove(target);
                    }
                }
                return new HashMap<>(vertex.getTargetWeightMap());
            }
        }
        // Avoid return null without specification. Avoid return null.
        return new HashMap<>();
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
class Vertex<L> {
    
    private final Map<L, Integer> sourceWeightMap;
    private final Map<L, Integer> targetWeightMap;
    private final L theL;
    
    // Abstraction function:
    //   AF(sourceWeightMap, targetWeightMap, theL) = a legal vertex within the graph
    // Representation invariant:
    //   the vertex exists
    // Safety from rep exposure:
    //   All fields are made private and final
    
    public Vertex(L theL) {
        sourceWeightMap = new HashMap<>();
        targetWeightMap = new HashMap<>();
        this.theL = theL;
        checkRep();
    }

    private void checkRep() {}

    public Map<L, Integer> getSourceWeightMap() {
        return sourceWeightMap;
    }

    public Map<L, Integer> getTargetWeightMap() {
        return targetWeightMap;
    }

    public L getValue() {
        return theL;
    }

    public void addSource(L theL, int weight) {
        sourceWeightMap.put(theL, weight);
    }

    public void addTarget(L theL, int weight) {
        targetWeightMap.put(theL, weight);
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "sourceWeightMap=" + sourceWeightMap +
                ", targetWeightMap=" + targetWeightMap +
                ", theL='" + theL + '\'' +
                '}';
    }
}
