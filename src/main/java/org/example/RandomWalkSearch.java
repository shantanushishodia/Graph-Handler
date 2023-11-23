package org.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;

/**
 * Class for Random Walk Search
 *
 */
public class RandomWalkSearch extends GraphSearchTemplate implements GraphSearchStrategy {

    @Override
    protected boolean search(Graph<String, DefaultEdge> graph, String current, String destination, Set<String> visited) {
        visited.add(current);

        if (current.equals(destination)) {
            return true;
        }

        List<String> neighbors = getNeighbors(graph, current);
        while (!neighbors.isEmpty()) {
            int randomIndex = new Random().nextInt(neighbors.size());

            String randomNeighbor = neighbors.get(randomIndex);
            System.out.println(current+ "->"+randomNeighbor);

            path.getPathMap().put(randomNeighbor, current);

            boolean temp =search(graph, randomNeighbor, destination, visited);
            if(temp)
                return true;
            neighbors.remove(randomIndex);
        }

        return false;
    }

    private List<String> getNeighbors(Graph<String, DefaultEdge> graph, String src) {
        List<String> neighbors = new ArrayList<>();
        neighbors.addAll(graph.outgoingEdgesOf(src).stream().map(graph::getEdgeTarget).toList());
        return neighbors;
    }
}
