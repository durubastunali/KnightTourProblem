// The Knight's Tour Problem: 150120042, 150120053, 150120075
package knight_tour_problem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static long startTime;
    public static long timeLimit;
    public static FileWriter writer;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Size of the board (n): "); // Take the size of the board as input from the user
        int n = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Search method (a-d): "); // Take the search method as input from the user
        char searchMethod = scanner.nextLine().charAt(0);

        System.out.print("Time limit (t): "); // Take the time limit as input from the user
        int t = scanner.nextInt();

        Search search = new Search(n);

        timeLimit = (long) t * 60 * 1000; // Set the time limit in milliseconds
        startTime = System.currentTimeMillis(); // Set the starting time of the program

        try { // Only search for a solution starting from the 1-1 tile
            search.treeSearch(new Node(null, 1, 1, 1), searchMethod);
            if (search.solution != null) { // A solution node is set, therefore a solution is found
                printSolution(search.solution);
            } else if (search.timeLimitPassed) { //
                System.out.println("Timeout.");
            } else { // A solution node is not set, remaining null. Therefore, no solution found
                System.out.println("No solution exists.");
            }
        } catch (OutOfMemoryError e) { //Catch possible errors
            System.out.println("Out of memory.");
        } catch (StackOverflowError e) {
            System.out.println("Stack overflow.");
        }

        long endTime = System.currentTimeMillis(); // Set the ending time of the program
        long runTime = endTime - startTime; // Calculate the runtime of the program in milliseconds

        //Format the runtime into mm:ss:SSS
        long minutes = runTime / (60 * 1000);
        long seconds = (runTime % (60 * 1000)) / 1000;
        long milliseconds = runTime % 1000;

        System.out.printf("Finished in %02d:%02d:%03d%n", minutes, seconds, milliseconds);
        System.out.println("Number of nodes expanded: " + search.numberOfNodesExpanded);
    }

    private static void printSolution(Node node)  { //Print the solution path
        System.out.println("A solution found.");
        try {
            // Output is written to a txt file.
            // This file is used as an input for the Python program to visualize the solution path.
            File file = new File("src\\knight_tour_problem\\output.txt");
            writer = new FileWriter(file, false);
            findPath(node);
            writer.close();
        } catch (IOException e) {
            System.out.println("Solution couldn't be written to the output file.");
        }
    }

    //Printing is done in recursive, so the path can be written from up to down, starting from the tile 1-1.
    private static void findPath(Node node) throws IOException {
        if (node.parent != null) {
            findPath(node.parent);
        }
        writer.write(node.locationX + "-" + node.locationY + "\n");
        System.out.println(node.locationX + "-" + node.locationY);
    }
}