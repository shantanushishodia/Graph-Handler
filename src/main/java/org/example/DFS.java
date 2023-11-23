package org.example;

import org.jgrapht.*;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;

/**
 * Class for dfs path
 *
 */
public class DFS extends GraphSearchTemplate {

    @Override
    protected boolean search(Graph<String, DefaultEdge> graph, String current, String destination, Set<String> visited) {
        visited.add(current);

        if (current.equals(destination)) {
            return true;
        }

        for (DefaultEdge edge : graph.outgoingEdgesOf(current)) {
            String neighbor = graph.getEdgeTarget(edge);
            if (!visited.contains(neighbor)) {
                path.getPathMap().put(neighbor, current);
                if (search(graph, neighbor, destination, visited)) {
                    return true;
                }
            }
        }

        return false;
    }
}

