/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.*;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices;
    private List<Edge<L>> edges;
    
    // Abstraction function:
    //   AF(vertices, edges) = the graph that has and only has all the vertices and edges
    // Representation invariant:
    //   All the vertices of the edges belongs to vertices set
    // Safety from rep exposure:
    //   All fields are made private
    //   vertices and edges can be mutated by their reference, so the get method returns a defensive copy
    
    public ConcreteEdgesGraph() {
        vertices = new HashSet<>();
        edges = new ArrayList<>();
        checkRep();
    }
    
    private void checkRep() {
        for (Edge<L> edge : edges) {
            assert vertices.contains(edge.getSource()) && vertices.contains(edge.getTarget());
        }
    }
    
    @Override public boolean add(L vertex) {
        if (vertices.contains(vertex)) {
            return false;
        } else {
            vertices.add(vertex);
            checkRep();
            return true;
        }
    }
    
    @Override public int set(L source, L target, int weight) {
        boolean found = false;
        int weightBefore = -1;   // non-negative if found
        for (Edge<L> edge : edges) {
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                found = true;
                weightBefore = edge.getWeight();
                if (weight != 0) {
                    edge.setWeight(weight);
                } else {
                    edges.remove(edge);
                }
                break;
            }
        }
        checkRep();
        if (!found) {
            edges.add(new Edge<>(source, target, weight));
            checkRep();
            return 0;
        } else {
            return weightBefore;
        }
    }
    
    @Override public boolean remove(L vertex) {
        boolean ret = vertices.remove(vertex);
        LinkedList<Edge<L>> newEdges = new LinkedList<>();
        for (Edge<L> edge : edges) {
            if (!edge.getSource().equals(vertex) && !edge.getTarget().equals(vertex)) {
                newEdges.add(edge);
            }
        }
        edges = newEdges;
        checkRep();
        return ret;
    }
    
    @Override public Set<L> vertices() {
        return new HashSet<>(vertices);
    }
    
    @Override public Map<L, Integer> sources(L target) {
        Map<L, Integer> sourceMap = new HashMap<>();
        for (Edge<L> edge : edges) {
            if (edge.getTarget().equals(target)) {
                sourceMap.put(edge.getSource(), edge.getWeight());
            }
        }
        checkRep();
        return sourceMap;
    }
    
    @Override public Map<L, Integer> targets(L source) {
        Map<L, Integer> targetMap = new HashMap<>();
        for (Edge<L> edge : edges) {
            if (edge.getSource().equals(source)) {
                targetMap.put(edge.getTarget(), edge.getWeight());
            }
        }
        checkRep();
        return targetMap;
    }

    @Override
    public String toString() {
        return "ConcreteEdgesGraph{" +
                "vertices=" + vertices +
                ", edges=" + edges +
                '}';
    }
}

/**
 * TODO specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L> {
    
    private final L source;
    private final L target;
    private int weight;
    
    // Abstraction function:
    //   AF(source, target, weight) = a directed edge from the "source" to "target" with weight "weight"
    // Representation invariant:
    //   The Edge exist
    // Safety from rep exposure:
    //   The source and target are private and final String, so the reference can't be reassigned or mutated.
    //   Weight can be reassigned by setWeight(int) method
    
    public Edge(L source, L target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }
    
    private void checkRep() {}

    public L getSource() {
        return source;
    }

    public L getTarget() {
        return target;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", weight=" + weight +
                '}';
    }

}
