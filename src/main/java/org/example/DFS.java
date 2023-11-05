package org.example;

import org.jgrapht.*;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;

/**
 * Class for dfs path
 *
 */
public class DFS {
    static Path path = new Path();
    public Path findPath(Graph<String, DefaultEdge> graph, String src, String dst) throws Exception {
        try {
            if(src.equals(dst)){
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("Source and destination cannot be the same node", e);
        }
        Set<String> visited = new HashSet<>();
        boolean found = dfs(graph, src, dst, visited);

        if (found) {
            return path;
        } else {
            return null;
        }
    }

    private static boolean dfs(Graph<String, DefaultEdge> graph, String current, String destination, Set<String> visited) {
        visited.add(current);

        if (current.equals(destination)) {
            return true;
        }

        for (DefaultEdge edge : graph.outgoingEdgesOf(current)) {
            String neighbor = graph.getEdgeTarget(edge);
            if (!visited.contains(neighbor)) {
                path.pathMap.put(neighbor, current);
                if (dfs(graph, neighbor, destination, visited)) {
                    return true;
                }
            }
        }

        return false;
    }
}
