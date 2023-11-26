# cse-464-2023-sshishod

## GraphHandler

### Instructions to Run
- Download the ```cse-464-sshishod.zip``` file from this repository
- Run ```mvn package```
- This should run all tests for the project
- This command will build the project in the ```target``` folder as well
- Alternatively, unzip ```cse-464-sshishod.zip``` and then open the GraphHandler folder in IntelliJ

### APIs
- ```void graphImporter(String filePath)``` - import a directed graph in a dot file
- ```String toString()``` - Graph information like number of nodes, edges and their directions
- ```void saveGraphToFile(String filePath)``` - Write the graph information to a file
- ```void addOneNode(String label)``` - Adds a new node to the graph with the given label if it does not exist
- ```void addMultipleNodes(ArrayList<String> labels)``` - Add multiple nodes to the graph
- ```boolean removeNode(String label)``` - Returns false if node does not exist or returns true if node is removed
- ```boolean removeNodes(String[] labels)``` - Returns true if all nodes are removed successfully otherwise returns false if even one node exists
- ```boolean addEdge(String initialNode, String targetNode)``` - Returns true if edge is added otherwise returns false if edge exists
- ```boolean removeEdge(String srcLabel, String dstLabel)``` - Returns true if edge is removed successfully otherwise returns false if edge does not exist in the graph
- ```void saveGraphDOT(String filePath)``` - Outputs the modified graph in DOT format to the specified file
- ```void saveGraphPNG(String filePath)``` - Output the modified graph to a PNG file (Graph Visualization)
- ```Path GraphSearch(String src, String dst, Algorithm algo)``` - Find a path from ```src``` to ```dst``` node using BFS or DFS or Random Walk algorithm depending on enum specified. Possible values of the enum can be ```Algorithm.BFS```, ```Algorithm.DFS``` or ```Algorithm.RANDOMWALK```

### Example Code
- Creating a new GraphHandler object
```java
GraphHandler gh = new GraphHandler();
```
- Parsing the graph and printing information
```java
gh.graphImporterFromDot("*PROVIDE DOT FILE PATH*");
System.out.println(gh.toString());
gh.saveGraphToFile("*PROVIDE PATH FOR SAVING FILE*");
```
- Adding and Removing nodes
```java
gh.addOneNode("NASA");
gh.removeOneNode("NASA");
List<String> labels = new ArrayList<>();
labels.add("NASA");
labels.add("Citadel");
labels.add("Microsoft");
gh.addMultipleNodes((ArrayList<String>) labels);
List<String> removeLabels = new ArrayList<>();
removeLabels.add("Google");
removeLabels.add("Citadel");
assertTrue(gh.removeMultipleNodes((ArrayList<String>) removeLabels));
```
- Adding and removing edges
```java
gh.addOneEdge("Google", "Ford");
gh.removeOneEdge("Meta", "Ford");
```
- Output graph as DOT and PNG
```java
gh.saveGraphToDOT("*PROVIDE PATH TO SAVE AS DOT FILE*");
gh.saveGraphToPNG("*PROVIDE PATH TO SAVE AS PNG FILE*");
```

- Find a path from one node to another using BFS, DFS or Random Walk algorithm
```java
Path bfs = gh.graphSearchWithAlgo("Google", "Meta", Algorithm.BFS);
Path dfs = gh.graphSearchWithAlgo("Google", "Tesla", Algorithm.DFS);
Path rws = gh.graphSearchWithAlgo("a","c", Algorithm.RANDOMWALK);
```

- The ```buildPath()``` method in ```Path``` class will print the path in the format ```a -> b -> c```
- If no path if found, the ```graphSearchWithAlgo``` API returns ```null```, so we need to check if the returned path is not null
- This is done using Template design pattern in GraphSearchTemplate
```java
boolean found = search(graph, src, dst, visited);
if (found) {
    System.out.println(path.buildPath(dst));
    return path;
} else {
    System.out.println("No Path found");
    return null;
}
```
