package org.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;

/**
 * Class for finding a path using BFS
 *
 */
public class BFS {
    public Path findPath(Graph<String, DefaultEdge> graph, String src, String dst) throws Exception {
        try {
            if(src.equals(dst)){
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("Source and destination cannot be the same node", e);
        }
        Path path = new Path();
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(src);
        visited.add(src);
        path.getPathMap().put(src, null);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(dst)) {
                return path;
            }

            for (DefaultEdge edge : graph.outgoingEdgesOf(current)) {
                String neighbor = graph.getEdgeTarget(edge);
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    path.getPathMap().put(neighbor, current);
                }
            }
        }

        return null;
    }

}
