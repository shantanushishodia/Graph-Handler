package org.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;

/**
 * Class for finding a path using BFS
 *
 */
public class BFS extends GraphSearchTemplate {

    @Override
    protected boolean search(Graph<String, DefaultEdge> graph, String src, String dst, Set<String> visited) {
        Queue<String> queue = new LinkedList<>();
        initializeQueueAndVisited(src, queue, visited);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(dst)) {
                return true;
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

        return false;
    }

    private void initializeQueueAndVisited(String src, Queue<String> queue, Set<String> visited) {
        queue.add(src);
        visited.add(src);
        path.getPathMap().put(src, null);
    }
}