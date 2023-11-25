package org.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.Set;

public interface GraphSearchStrategy {
    Path findPath(Graph<String, DefaultEdge> graph, String src, String dst) throws Exception;
}