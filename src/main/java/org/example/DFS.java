package org.example;

import org.jgrapht.*;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;

/**
 * Class for dfs path
 *
 */
public class DFS {
    public static String findPath(Graph<String, DefaultEdge> graph, String src, String dst) {
        Set<String> visited = new HashSet<>();
        Map<String, String> parentMap = new HashMap<>();
        boolean found = dfs(graph, src, dst, visited, parentMap);

        if (found) {
            return Path.buildPath(parentMap, dst);
        } else {
            return null;
        }
    }

    private static boolean dfs(Graph<String, DefaultEdge> graph, String current, String destination, Set<String> visited, Map<String, String> parentMap) {
        visited.add(current);

        if (current.equals(destination)) {
            return true;
        }

        for (DefaultEdge edge : graph.outgoingEdgesOf(current)) {
            String neighbor = graph.getEdgeTarget(edge);
            if (!visited.contains(neighbor)) {
                parentMap.put(neighbor, current);
                if (dfs(graph, neighbor, destination, visited, parentMap)) {
                    return true;
                }
            }
        }

        return false;
    }
}
