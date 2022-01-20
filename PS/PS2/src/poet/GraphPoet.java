/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {
    
    private final Graph<String> graph = Graph.empty();

    // Abstraction function:
    //   AF(graph) = The Graph poet as described above
    // Representation invariant:
    //   RI: graph is consistent with the corpus
    // Safety from rep exposure:
    //   The field graph is made private and final. The get method of graph is made package level private
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        List<String> corputStringList = Files.readAllLines(corpus.toPath());
        for (String theString : corputStringList) {
            expendGraphFromString(theString);
        }
    }

    /**
     * Create a new poet with the graph from corpus (as described above).
     *
     * @param corpus text file from which to derive the poet's affinity graph
     */
    public GraphPoet(String corpus) {
        expendGraphFromString(corpus);
    }
    
    // TODO checkRep
    
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        String[] splitInput = input.split("\\s+");
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < splitInput.length; i++) {
            sb.append(splitInput[i - 1]);
            sb.append(' ');
            String source = splitInput[i - 1].toLowerCase(Locale.ROOT);
            String target = splitInput[i].toLowerCase(Locale.ROOT);
            Set<String> candidate = graph.targets(source).keySet();
            // The intersection that is both the target of the source and source of the target is the candidate
            candidate.retainAll(graph.sources(target).keySet());
            if (candidate.size() > 0) {
                List<String> candidateList = new ArrayList<>(candidate);
                String intermediate = candidateList.get(0);
                int maxWeight = graph.sources(intermediate).get(source) + graph.targets(intermediate).get(target);
                for (int j = 1; j < candidateList.size(); j++) {
                    String temp = candidateList.get(i);
                    int currentWeight = graph.sources(temp).get(source) + graph.targets(temp).get(target);
                    if (currentWeight > maxWeight) {
                        maxWeight = currentWeight;
                        intermediate = temp;
                    }
                }
                sb.append(intermediate);
                sb.append(' ');
            }

            if (i == splitInput.length - 1) {
                sb.append(splitInput[i]);
            }
        }

        return sb.toString();
    }

    void expendGraphFromString(String theString) {
        if (theString.length() == 0) {
            return;
        }
        String[] splitString = theString.split("\\s+");

        for (int i = 0; i < splitString.length; i++) {
            splitString[i] = splitString[i].toLowerCase(Locale.ROOT);
        }

        for (String item : splitString) {
            graph.add(item);
        }

        for (int i = 1; i < splitString.length; i++) {
            int weight = graph.targets(splitString[i - 1]).getOrDefault(splitString[i], 0) + 1;
            graph.set(splitString[i - 1], splitString[i], weight);
        }
    }

    Graph<String> getGraph() {
        return graph;
    }

    @Override
    public String toString() {
        return "GraphPoet{" +
                "graph=" + graph +
                '}';
    }
}
