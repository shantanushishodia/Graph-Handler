

import org.example.*;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class GraphHandlerTest {

    GraphHandler graphHandler;

    /**
     * Function for initializing GraphHandler object before each test
     *
     * @throws Exception
     */
    @BeforeEach
    public void initialize() throws Exception {
        graphHandler = new GraphHandler();
        graphHandler.graphImporterFromDot("src/test/test1.dot");
    }

    /**
     * Function  for testing graphImporter functionality (with invalid input check)
     *
     */
    @Test
    public void graphImporter() {
        assertEquals(5, graphHandler.getGraph().edgeSet().size());
        assertTrue(graphHandler.getGraph().containsEdge("Google", "Meta"));
        assertTrue(graphHandler.getGraph().containsEdge("Meta", "Ford"));
        assertTrue(graphHandler.getGraph().containsEdge("Tesla", "NXP"));
        assertTrue(graphHandler.getGraph().containsEdge("NXP", "Asus"));
        assertTrue(graphHandler.getGraph().containsEdge("Ford", "Tesla"));
        assertEquals(6, graphHandler.getGraph().vertexSet().size());

        System.out.println(graphHandler.toString());

        Exception exception = assertThrows(Exception.class, () -> {
            graphHandler.graphImporterFromDot("src/fakeFile.dot");
        });

        String expectedMessage = "Unable to read file, encountered error";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Function for testing ToString Functionality
     *
     * @throws IOException
     */
    @Test
    public void testToString() throws Exception {
        String expected = Files.readString(Paths.get("src/test/expectedGraphFile.txt"));
        String output = graphHandler.toString();
        assertEquals(output, expected);
    }

    /**
     * Function for testing saveGraphToFile functionality
     *
     * @throws Exception
     */
    @Test
    public void testSaveGraphToFile() throws Exception {
        graphHandler.saveGraphToFile("src/testSaveGraphFile.txt");
        String output = Files.readString(Paths.get("src/testSaveGraphFile.txt"));
        String expected = Files.readString(Paths.get("src/test/expectedGraphFile.txt"));
        assertEquals(output, expected);
    }

    /**
     * Function for testing addOneNode (with duplication check) functionality
     *
     * @throws Exception
     */
    @Test
    public void testAddOneNode() throws Exception {
        graphHandler.addOneNode("NASA");
        System.out.println(graphHandler.toString());

        Exception exception = assertThrows(Exception.class, () -> {
            graphHandler.addOneNode("Google");
        });

        String expectedMessage = "Node already present";
        String actualMessage = exception.getMessage();

        assertEquals(7, graphHandler.getGraph().vertexSet().size());
        assertTrue(graphHandler.getGraph().containsVertex("NASA"));
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Function for testing addMultipleNodes (with duplication check) functionality
     *
     * @throws Exception
     */
    @Test
    public void testAddNodes() throws Exception {
        List<String> labels = new ArrayList<>();
        labels.add("NASA");
        labels.add("Citadel");
        labels.add("Microsoft");
        graphHandler.addMultipleNodes((ArrayList<String>) labels);

        Exception exception = assertThrows(Exception.class, () -> {
            graphHandler.addMultipleNodes((ArrayList<String>) labels);
        });
        String expectedMessage = "Node already present";
        String actualMessage = exception.getMessage();

        assertEquals(9, graphHandler.getGraph().vertexSet().size());
        assertTrue(graphHandler.getGraph().containsVertex("Microsoft"));
        assertTrue(graphHandler.getGraph().containsVertex("Citadel"));
        assertTrue(graphHandler.getGraph().containsVertex("NASA"));
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Function to test api for removing a node (with invalid node check)
     *
     */
    @Test
    public void testRemoveNode() throws Exception {
        graphHandler.removeOneNode("Google");
        System.out.println(graphHandler.toString());

        assertEquals(5, graphHandler.getGraph().vertexSet().size());
        assertFalse(graphHandler.getGraph().containsVertex("Google"));
        assertFalse(graphHandler.getGraph().containsVertex("ABCS"));
    }

    /**
     * Function to test  api for removing multiple nodes (with invalid node check)
     *
     * @throws Exception
     */
    @Test
    public void testRemoveNodes() throws Exception {
        graphHandler.addOneNode("Citadel");
        graphHandler.addOneEdge("Citadel","NASA");
        List<String> labels = new ArrayList<>();
        labels.add("Google");
        labels.add("Citadel");
        labels.add("NASA");
        assertTrue(graphHandler.removeMultipleNodes((ArrayList<String>) labels));

        assertFalse(graphHandler.getGraph().containsVertex("NASA"));
        assertFalse(graphHandler.getGraph().containsVertex("Citadel"));
        assertEquals(5, graphHandler.getGraph().vertexSet().size());
        assertEquals(4, graphHandler.getGraph().edgeSet().size());
        assertFalse(graphHandler.getGraph().containsEdge("Citadel","NASA"));
    }

    /**
     * Function to test addEdge (with duplicate edge check) Functionality
     *
     * @throws Exception
     */
    @Test
    public void testAddEdge() throws Exception {
        graphHandler.addOneEdge("Google", "Ford");
        System.out.println(graphHandler.toString());

        Exception exception = assertThrows(Exception.class, () -> {
            graphHandler.addOneEdge("Google", "Meta");
        });
        String expectedMessage = "Edge already present in the graph";
        String actualMessage = exception.getMessage();

        assertEquals(6, graphHandler.getGraph().vertexSet().size());
        assertTrue(graphHandler.getGraph().containsVertex("Google"));
        assertTrue(graphHandler.getGraph().containsEdge("Google", "Ford"));
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Function to test api for removing an edge (with invalid edge check)
     *
     * @throws Exception
     */
    @Test
    public void testRemoveEdge() throws Exception {

        graphHandler.removeOneEdge("Meta", "Ford");
        System.out.println(graphHandler.toString());

        Exception exception = assertThrows(Exception.class, () -> {
            graphHandler.removeOneEdge("Meta", "Ford");
        });
        String expectedMessage = "Edge not present in the graph";
        String actualMessage = exception.getMessage();

        assertEquals(6, graphHandler.getGraph().vertexSet().size());
        assertEquals(4, graphHandler.getGraph().edgeSet().size());
        assertTrue(graphHandler.getGraph().containsEdge("Google", "Meta"));
        assertTrue(graphHandler.getGraph().containsEdge("Ford", "Tesla"));
        assertTrue(graphHandler.getGraph().containsEdge("Tesla", "NXP"));
        assertTrue(graphHandler.getGraph().containsEdge("NXP", "Asus"));
        assertTrue(actualMessage.contains(expectedMessage));
    }


    /**
     * Function to test output DOT graph file
     *
     * @throws Exception
     */
    @Test
    public void testOutputDOTGraph() throws Exception {
        String outputDOTFile = "src/outputDOTFile.dot";
        graphHandler.saveGraphToDOT(outputDOTFile);

        String output = Files.readString(Paths.get(outputDOTFile));
        System.out.println("output: " + outputDOTFile);
        String expected = Files.readString(Paths.get("src/test/expectedGraphDOT.txt"));
        assertEquals(expected, output);
    }

    /**
     * Function to test DFS Using Template(with edge cases)
     *
     * @throws Exception
     */
    @Test
    public void testDFSUsingTemplate() throws Exception {
        GraphHandler gh = new GraphHandler();
        gh.graphImporterFromDot("src/test/test1.dot");
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Google");
        expected.add("Meta");
        expected.add("Ford");
        expected.add("Tesla");
        String expectedString = "Google -> Meta -> Ford -> Tesla";

        Graph<String, DefaultEdge> currGraph = gh.getGraph();
        DFS dfs = new DFS();
        Path result = dfs.findPath(currGraph, "Google", "Tesla");

        assertNotNull(result);
        assertEquals(expected, Arrays.asList(result.buildPath("Tesla").split(" -> ")));
        assertEquals(expectedString, result.buildPath("Tesla"));

        result = dfs.findPath(currGraph, "Tesla", "Google");
        assertNull(result);

        Exception exception = assertThrows(Exception.class, () -> {
            dfs.findPath(currGraph,"Google", "Google");
        });
        String expectedMessage = "Source and destination cannot be the same node";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));


    }

    /**
     * Function to test DFS Using Strategy  (with edge cases)
     *
     * @throws Exception
     */
    @Test
    public void testDFSUsingStrategy() throws Exception {
        GraphHandler gh = new GraphHandler();
        gh.graphImporterFromDot("src/test/test1.dot");
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Google");
        expected.add("Meta");
        expected.add("Ford");
        expected.add("Tesla");
        String expectedString = "Google -> Meta -> Ford -> Tesla";

        Path result = gh.graphSearchWithAlgo("Google", "Tesla", Main.Algorithm.DFS);

        assertNotNull(result);
        assertEquals(expected, Arrays.asList(result.buildPath("Tesla").split(" -> ")));
        assertEquals(expectedString, result.buildPath("Tesla"));

        result = gh.graphSearchWithAlgo("Tesla", "Google", Main.Algorithm.DFS);
        assertNull(result);

        Exception exception = assertThrows(Exception.class, () -> {
            gh.graphSearchWithAlgo("Google", "Google", Main.Algorithm.DFS);
        });
        String expectedMessage = "Source and destination cannot be the same node";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Function to test BFS using Template (with edge cases)
     *
     * @throws Exception
     */
    @Test
    public void testBFSUsingTemplate() throws Exception {
        GraphHandler gh = new GraphHandler();
        gh.graphImporterFromDot("src/test/test1.dot");
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Google");
        expected.add("Meta");
        expected.add("Ford");
        expected.add("Tesla");
        String expectedString = "Google -> Meta -> Ford -> Tesla";

        Graph<String, DefaultEdge> currGraph = gh.getGraph();
        BFS bfs = new BFS();
        Path result = bfs.findPath(currGraph, "Google", "Tesla");

        assertNotNull(result);
        assertEquals(expected, Arrays.asList(result.buildPath("Tesla").split(" -> ")));
        assertEquals(expectedString, result.buildPath("Tesla"));

        result = bfs.findPath(currGraph, "Tesla", "Google");
        assertNull(result);

        Exception exception = assertThrows(Exception.class, () -> {
            bfs.findPath(currGraph,"Google", "Google");
        });
        String expectedMessage = "Source and destination cannot be the same node";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));


    }

    /**
     * Function to test BFS Using Strategy(with edge cases)
     *
     * @throws Exception
     */
    @Test
    public void testBFSUsingStrategy() throws Exception {
        GraphHandler gh = new GraphHandler();
        gh.graphImporterFromDot("src/test/test1.dot");
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Google");
        expected.add("Meta");
        expected.add("Ford");
        expected.add("Tesla");
        String expectedString = "Google -> Meta -> Ford -> Tesla";

        Path result = gh.graphSearchWithAlgo("Google", "Tesla", Main.Algorithm.BFS);

        assertNotNull(result);
        assertEquals(expected, Arrays.asList(result.buildPath("Tesla").split(" -> ")));
        assertEquals(expectedString, result.buildPath("Tesla"));

        result = gh.graphSearchWithAlgo("Tesla", "Google", Main.Algorithm.BFS);
        assertNull(result);

        Exception exception = assertThrows(Exception.class, () -> {
            gh.graphSearchWithAlgo("Google", "Google", Main.Algorithm.BFS);
        });
        String expectedMessage = "Source and destination cannot be the same node";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));


    }





}
