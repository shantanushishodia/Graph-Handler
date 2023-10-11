package org.example;

import java.util.Scanner;

/**
 * main class of the program
 *
 */
public class Main {

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
                System.out.println("\t0. Exit");
                userSelection=input.nextInt();
                switch (userSelection) {
                    case 1:
                        graphHandler.graphImporter("src/companies.dot");
                        break;
                    case 2:
                        System.out.println(graphHandler.toString());
                        break;
                    case 3:
                        graphHandler.saveGraphToFile("src/OutputGraphFile.txt");
                    case 0:
                        break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}