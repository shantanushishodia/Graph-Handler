package org.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

public class GraphSearchContext {
    private GraphSearchStrategy graphSearchStrategy;

    public void setAlgo(GraphSearchStrategy graphSearchStrategy) {
        this.graphSearchStrategy = graphSearchStrategy;
    }

    public Path executeAlgo(Graph<String, DefaultEdge> graph, String src, String dst) throws Exception {
        return this.graphSearchStrategy.findPath(graph, src, dst);
    }

}
