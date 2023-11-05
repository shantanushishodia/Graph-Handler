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
- ```void GraphSearch(String src, String dst, Algorithm algo)``` - Find a path from ```src``` to ```dst``` node using BFS or DFS algorithm depending on enum specified. Possible values of the enum can be ```BFS```, ```DFS```
