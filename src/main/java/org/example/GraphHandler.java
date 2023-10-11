package org.example;


import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.nio.dot.DOTImporter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

/**
 * Class for handling different functionalities of the program
 *
 */
public class GraphHandler {

    /**
     * Primary graph for loading from DOT and making changes to
     *
     */
    private Graph<String, DefaultEdge> primaryGraph = new SimpleDirectedGraph<>(DefaultEdge.class);

    /**
     * Function for importing a DOT graph file into the system
     *
     * @param filePath
     * @throws Exception
     */
    public void graphImporter(String filePath) throws Exception {
        String fileData =null;
        try {
            fileData = Files.readString(Paths.get(filePath));
        }
        catch (IOException e){
            throw new Exception("Error while reading input file",e);
        }

        try {
            DOTImporter<String, DefaultEdge> dotImporter = new DOTImporter<>();
            dotImporter.setVertexFactory(label -> label);
            dotImporter.importGraph(primaryGraph, new StringReader(fileData));
            System.out.println("Graph Parsing Successful");
        }
        catch (Exception e){
            throw new Exception("Graph parsing unsuccessful",e);
        }

    }

    /**
     * Function for getting the parsed graph details
     *
     */
    @Override
    public String toString() {
        try {
            String graphDetails = "";

            Set<String> setOfVertex = primaryGraph.vertexSet();
            graphDetails += "Nodes Count: " + setOfVertex.size() + "\n";
            graphDetails += "Label of nodes: \n";
            for(String values : setOfVertex) {
                graphDetails += values + "\n";
            }

            Set<DefaultEdge> setOfEdges = primaryGraph.edgeSet();
            graphDetails += "Edges count: " + setOfEdges.size() + "\n";
            graphDetails += "Directional edges with nodes: \n";
            for (DefaultEdge edge : setOfEdges) {
                String initialEdge = primaryGraph.getEdgeSource(edge);
                String targetEdge = primaryGraph.getEdgeTarget(edge);
                graphDetails += initialEdge + " -> " + targetEdge + "\n";
            }

            return graphDetails;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Function for saving the graph details into a file
     *
     * @param filePath
     * @throws Exception
     */
    public void saveGraphToFile(String filePath) throws Exception {
        String graphDetails = toString();
        try {
            Files.write(Paths.get(filePath), graphDetails.getBytes());
            System.out.println("File save is a success " + filePath);
        } catch (IOException e) {
            throw new Exception("File save failed", e);
        }
    }




}
