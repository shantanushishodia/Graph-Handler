package org.example;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * main class of the program
 *
 */
public class Main {

    private static final String DOT_FILE_PATH = "src/companies.dot";
    private static final String DOT_FILE_PATH_RWS = "src/main/java/graphrws.dot";
    private static final String EXPECTED_GRAPH_FILE_PATH = "src/expectedGraphFile.txt";
    private static final String EXPECTED_GRAPH_DOT_PATH = "src/expectedGraphDOT.txt";
    private static final String OUTPUT_GRAPH_PNG_PATH = "src/OutputGraphPNG.png";


    /**
     * Enum for algo decision
     *
     */
    public enum Algorithm {
        BFS,
        DFS,
        RANDOMWALK
    }

    /**
     * main function
     *
     * @param args
     */
    public static void main(String[] args) {
        int userSelection=991;
        Scanner input = new Scanner(System.in);
        GraphHandler graphHandler= new GraphHandler();
        try {
            /**
             * provided the user with the choice of functionalities
             *
             */
            while (userSelection != 0) {
                System.out.println("Input your choice for operation:");
                System.out.println("\t1. Initialize graph from DOT file");
                System.out.println("\t2. Get graph details");
                System.out.println("\t3. Save graph details to a file");
                System.out.println("\t4. Add single node");
                System.out.println("\t5. Add multiple nodes");
                System.out.println("\t6. Add one edge");
                System.out.println("\t7. Save graph details in DOT format");
                System.out.println("\t8. Save graph details in PNG format");
                System.out.println("\t9. Remove a node");
                System.out.println("\t10. Remove multiples nodes");
                System.out.println("\t11. Remove an edge");
                System.out.println("\t12. Find a path between two nodes");
                System.out.println("\t0. Exit");
                userSelection=input.nextInt();
                switch (userSelection) {
                    case 1:
                        graphHandler.graphImporterFromDot(DOT_FILE_PATH);
                        break;
                    case 2:
                        System.out.println(graphHandler.toString());
                        break;
                    case 3:
                        graphHandler.saveGraphToFile(EXPECTED_GRAPH_FILE_PATH);
                        break;
                    case 4:
                        System.out.println("\tInput the name for the node:");
                        graphHandler.addOneNode(input.next());
                        break;
                    case 5:
                        System.out.println("\tEnter the number of nodes you want to add:");
                        int n= input.nextInt();
                        ArrayList<String> listOfNodes = new ArrayList<>();
                        for(int i=0;i<n;i++)
                            listOfNodes.add(input.next());
                        graphHandler.addMultipleNodes(listOfNodes);
                        break;
                    case 6:
                        System.out.println("\tInput source node for the edge");
                        String initialNode = input.next();
                        System.out.println("\tInput target node for the edge");
                        String targetNode = input.next();
                        graphHandler.addOneEdge(initialNode, targetNode);
                        break;
                    case 7:
                        graphHandler.saveGraphToDOT(EXPECTED_GRAPH_DOT_PATH);
                        break;
                    case 8:
                        graphHandler.saveGraphToPNG(OUTPUT_GRAPH_PNG_PATH);
                        break;
                    case 9:
                        System.out.println("\tInput the name for the node you wish to remove:");
                        graphHandler.removeOneNode(input.next());
                    case 10:
                        System.out.println("\tEnter the number of nodes you want to remove:");
                        int rn= input.nextInt();
                        ArrayList<String> listOfNodesToBeRemoved = new ArrayList<>();
                        for(int i=0;i<rn;i++)
                            listOfNodesToBeRemoved.add(input.next());
                        graphHandler.removeMultipleNodes(listOfNodesToBeRemoved);
                        break;
                    case 11:
                        System.out.println("\tInput source node for the edge");
                        String initialNodeToRemove = input.next();
                        System.out.println("\tInput target node for the edge");
                        String targetNodeToRemove = input.next();
                        graphHandler.removeOneEdge(initialNodeToRemove, targetNodeToRemove);
                        break;
                    case 12:
                        Algorithm algo;
                        try {
                            System.out.println("\tChoose Algo BFS/DFS/RandomWalk:");
                            algo = Algorithm.valueOf(input.next().toUpperCase());
                        } catch (Exception e) {
                            System.out.println("\tProvide the correct algo: BFS/DFS/RandomWalk");
                            break;
                        }
                        if(algo.name() == Algorithm.RANDOMWALK.name())
                            graphHandler.graphImporterFromDot(DOT_FILE_PATH_RWS);
                        System.out.println("\tInput source node");
                        String srcNode = input.next();
                        System.out.println("\tInput destination node");
                        String dstNode = input.next();
                        graphHandler.graphSearchWithAlgo(srcNode, dstNode, algo);
                        break;
                    case 0:
                        break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}