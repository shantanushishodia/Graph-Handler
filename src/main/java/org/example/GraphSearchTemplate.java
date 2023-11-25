package org.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.HashSet;
import java.util.Set;

public abstract class GraphSearchTemplate {

    protected Path path = new Path();

    public Path findPath(Graph<String, DefaultEdge> graph, String src, String dst) throws Exception {

        validateSourceAndDestination(graph, src, dst);
        Set<String> visited = new HashSet<>();
        boolean found = search(graph, src, dst, visited);

        if (found) {
            System.out.println(path.buildPath(dst));
            return path;
        } else {
            System.out.println("No Path found");
            return null;
        }
    }

    protected abstract boolean search(Graph<String, DefaultEdge> graph, String current, String destination, Set<String> visited);

    protected void validateSourceAndDestination(Graph<String, DefaultEdge> graph, String src, String dst) throws Exception {
        if (src.equals(dst)) {
            throw new Exception("Source and destination cannot be the same node");
        }
        if(!graph.containsVertex(dst) || !graph.containsVertex(src)) {
            throw new Exception("Node not present in the graph");
        }
    }
}
