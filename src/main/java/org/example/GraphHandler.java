package org.example;


import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.nio.dot.DOTImporter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
     * Getter for primaryGraph
     *
     * @return
     */
    public Graph<String, DefaultEdge> getGraph() {
        return primaryGraph;
    }


    /**
     * Function for importing a DOT graph file into the system
     *
     * @param filePath
     * @throws Exception
     */
    public void graphImporterFromDot(String filePath) throws Exception {
        String fileData =null;
        try {
            fileData = Files.readString(Paths.get(filePath));
        }
        catch (IOException e){
            throw new Exception("Unable to read file, encountered error",e);
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
            throw new RuntimeException("Error getting graph details");
        }
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

    /**
     * function for adding a single node to the graph
     *
     * @param label
     * @throws Exception
     */
    public void addOneNode(String label) throws Exception {
        if (!primaryGraph.containsVertex(label)) {
            try {
                primaryGraph.addVertex(label);
            } catch (Exception e) {
                throw new Exception("Node not added, encountered error", e);
            }
        }
        else {
            throw new Exception("Node already present");
        }
    }

    /**
     * Function for adding multiple nodes to the graph
     * using the addOneNode function repeatedly
     *
     * @param labels
     * @throws Exception
     */
    public void addMultipleNodes(ArrayList<String> labels) throws Exception {
        for (String label : labels) {
            addOneNode(label);
        }
    }

    /**
     * Function for removing a node
     *
     */
    public boolean removeOneNode(String label) throws Exception {
        if (primaryGraph.containsVertex(label)) {
            try {
                primaryGraph.removeVertex(label);
                return true;
            } catch (Exception e) {
                throw new Exception("Node not removed, encountered error", e);
            }
        } else {
            System.out.println("Node not found");
            return false;
        }
    }

    /**
     *
     * Function for removing multiple nodes
     *
     * @param labels
     * @return
     * @throws Exception
     */
    public boolean removeMultipleNodes(ArrayList<String> labels) throws Exception {
        ArrayList<String> nodesFailed = new ArrayList<>();
        for (String label : labels) {
            if (!removeOneNode(label)) {
                nodesFailed.add(label);
            }
        }
        if (!nodesFailed.isEmpty()) {
            System.out.println("Encountered error, these nodes are not removed: " + nodesFailed);
            throw new Exception("Not all nodes removed");
        }

        return true;
    }

    /**
     * Function for adding an edge in the graph
     *
     * @param initialNode
     * @param targetNode
     * @return
     * @throws Exception
     */
    public boolean addOneEdge(String initialNode, String targetNode) throws Exception {
        try {
            if (primaryGraph.containsEdge(initialNode, targetNode)) {
                System.out.println("\tEdge already present in the graph");
                throw new Exception();
            } else {

                if (!primaryGraph.containsVertex(initialNode))
                    primaryGraph.addVertex(initialNode);
                if (!primaryGraph.containsVertex(targetNode))
                    primaryGraph.addVertex(targetNode);

                primaryGraph.addEdge(initialNode, targetNode);
                return true;
            }
        } catch (Exception e) {
            throw new Exception("\tEdge already present in the graph", e);
        }

    }

    /**
     * Function for removing an edge from the graph
     *
     * @param srcLabel
     * @param dstLabel
     * @return
     * @throws Exception
     */
    public boolean removeOneEdge(String srcLabel, String dstLabel) throws Exception {
        try {
            if (primaryGraph.containsEdge(srcLabel, dstLabel)) {
                primaryGraph.removeEdge(srcLabel, dstLabel);
                return true;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("Edge not present in the graph", e);
        }
    }

    /**
     * Function for saving the graph in DOT Format
     *
     * @param filePath
     * @throws Exception
     */
    public void saveGraphToDOT(String filePath) throws Exception {
        DOTExporter<String, DefaultEdge> graphExporter = new DOTExporter<>();
        StringWriter writer = new StringWriter();
        String stringDOT;
        try {

            graphExporter.setVertexIdProvider(v -> v);
            graphExporter.exportGraph(primaryGraph, writer);
            stringDOT = writer.toString();
        } catch (Exception e) {
            throw new Exception("DOTString not generated, encountered error", e);
        }
        try {
            Files.write(Paths.get(filePath), stringDOT.getBytes());
        } catch (IOException e) {
            throw new IOException("DOT graph not generated, encountered error", e);
        }

    }


    /**
     * Function for saving the graph in PNG format
     *
     * @param filePath
     * @throws Exception
     */
    public void saveGraphToPNG(String filePath) throws Exception {
        JGraphXAdapter<String, DefaultEdge> adapterForGraph = new JGraphXAdapter<String, DefaultEdge>(primaryGraph);
        mxIGraphLayout layout = new mxCircleLayout(adapterForGraph);
        try {
            layout.execute(adapterForGraph.getDefaultParent());
        } catch (Exception e) {
            throw new Exception("Graph to image conversion failed, encountered error", e);
        }

        BufferedImage graphImage = mxCellRenderer.createBufferedImage(adapterForGraph, null, 2, Color.WHITE, true, null);
        File imgFile = new File(filePath);
        try {
            ImageIO.write(graphImage, "PNG", imgFile);
        } catch (IOException e) {
            throw new Exception("Image to file write failed, encountered error", e);
        }
    }

    /**
     * Function to perform path search
     *
     * @param src
     * @param dst
     * @param algo
     * @return
     */
    public Path graphSearchWithAlgo(String src, String dst, Main.Algorithm algo) throws Exception {

            if (algo.name() == "BFS") {
                BFS bfs = new BFS();
                Path path = bfs.findPath(primaryGraph.iterables().getGraph(), src, dst);
                System.out.println(path.buildPath(dst));
                return path;
            }
            else if (algo.name() == "DFS") {
                DFS dfs = new DFS();
                Path path = dfs.findPath(primaryGraph.iterables().getGraph(), src, dst);
                System.out.println(path.buildPath(dst));
                return path;
            }
        return null;
    }

}
