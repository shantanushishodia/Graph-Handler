

import org.example.GraphHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
     * Function  for testing graphImporter functionality
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
    public void testToString() throws IOException {
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
     * Function for testing addOneNode functionality
     *
     * @throws Exception
     */
    @Test
    public void testAddOneNode() throws Exception {
        graphHandler.addOneNode("e");
        System.out.println(graphHandler.toString());

        assertEquals(7, graphHandler.getGraph().vertexSet().size());
        assertTrue(graphHandler.getGraph().containsVertex("e"));
    }

    /**
     * Function for testing addMultipleNodes functionality
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

        assertEquals(9, graphHandler.getGraph().vertexSet().size());
        assertTrue(graphHandler.getGraph().containsVertex("Microsoft"));
        assertTrue(graphHandler.getGraph().containsVertex("Citadel"));
        assertTrue(graphHandler.getGraph().containsVertex("NASA"));
    }




}
