

import org.example.BFS;
import org.example.DFS;
import org.example.GraphHandler;
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
        graphHandler.graphImporter("src/test/test1.dot");
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
            graphHandler.graphImporter("src/fakeFile.dot");
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
        graphHandler.removeNode("Google");
        System.out.println(graphHandler.toString());

        Exception exception = assertThrows(Exception.class, () -> {
            graphHandler.removeNode("Google");
        });
        String expectedMessage = "Node not found";
        String actualMessage = exception.getMessage();

        assertEquals(5, graphHandler.getGraph().vertexSet().size());
        assertFalse(graphHandler.getGraph().containsVertex("Google"));
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Function to test  api for removing multiple nodes (with invalid node check)
     *
     * @throws Exception
     */
    @Test
    public void testRemoveNodes() throws Exception {
        graphHandler.addOneNode("Citadel");
        graphHandler.addEdge("Citadel","NASA");
        List<String> labels = new ArrayList<>();
        labels.add("Google");
        labels.add("Citadel");
        labels.add("NASA");
        assertTrue(graphHandler.removeNodes((ArrayList<String>) labels));

        Exception exception = assertThrows(Exception.class, () -> {
            graphHandler.removeNodes((ArrayList<String>) labels);
        });
        String expectedMessage = "Node not found";
        String actualMessage = exception.getMessage();

        assertFalse(graphHandler.getGraph().containsVertex("NASA"));
        assertFalse(graphHandler.getGraph().containsVertex("Citadel"));
        assertEquals(5, graphHandler.getGraph().vertexSet().size());
        assertEquals(4, graphHandler.getGraph().edgeSet().size());
        assertFalse(graphHandler.getGraph().containsEdge("Citadel","NASA"));
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Function to test addEdge (with duplicate edge check) Functionality
     *
     * @throws Exception
     */
    @Test
    public void testAddEdge() throws Exception {
        graphHandler.addEdge("Google", "Ford");
        System.out.println(graphHandler.toString());

        Exception exception = assertThrows(Exception.class, () -> {
            graphHandler.addEdge("Google", "Meta");
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

        graphHandler.removeEdge("Meta", "Ford");
        System.out.println(graphHandler.toString());

        Exception exception = assertThrows(Exception.class, () -> {
            graphHandler.removeEdge("Meta", "Ford");
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
        graphHandler.saveGraphDOT(outputDOTFile);

        String output = Files.readString(Paths.get(outputDOTFile));
        System.out.println("output: " + outputDOTFile);
        String expected = Files.readString(Paths.get("src/test/expectedGraphDOT.txt"));
        assertEquals(expected, output);
    }

    /**
     * Function to test DFS (with edge cases)
     *
     * @throws Exception
     */
    @Test
    public void testDFS() throws Exception {
        GraphHandler gh = new GraphHandler();
        gh.graphImporter("src/test/test1.dot");
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Google");
        expected.add("Meta");
        expected.add("Ford");
        expected.add("Tesla");
        String expectedString = "Google -> Meta -> Ford -> Tesla";

        Graph<String, DefaultEdge> currGraph = gh.getGraph();
        DFS dfs = new DFS();
        String result = dfs.findPath(currGraph, "Google", "Tesla");
        System.out.println(result);
        assertNotNull(result);
        assertEquals(expected, Arrays.asList(result.split(" -> ")));
        assertEquals(expectedString, result);

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
     * Function to test BFS (with edge cases)
     *
     * @throws Exception
     */
    @Test
    public void testDFS() throws Exception {
        GraphHandler gh = new GraphHandler();
        gh.graphImporter("src/test/test1.dot");
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Google");
        expected.add("Meta");
        expected.add("Ford");
        expected.add("Tesla");
        String expectedString = "Google -> Meta -> Ford -> Tesla";

        Graph<String, DefaultEdge> currGraph = gh.getGraph();
        BFS bfs = new BFS();
        String result = bfs.findPath(currGraph, "Google", "Tesla");
        System.out.println(result);
        assertNotNull(result);
        assertEquals(expected, Arrays.asList(result.split(" -> ")));
        assertEquals(expectedString, result);

        result = bfs.findPath(currGraph, "Tesla", "Google");
        assertNull(result);

        Exception exception = assertThrows(Exception.class, () -> {
            bfs.findPath(currGraph,"Google", "Google");
        });
        String expectedMessage = "Source and destination cannot be the same node";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));


    }



}
