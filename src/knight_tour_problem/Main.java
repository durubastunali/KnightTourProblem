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

        System.out.print("Size of the board (n): ");
        int n = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Search method (a-d): ");
        char searchMethod = scanner.nextLine().charAt(0);

        System.out.print("Time limit (t): ");
        int t = scanner.nextInt();

        Search search = new Search(n);

        timeLimit = (long) t * 60 * 1000;
        startTime = System.currentTimeMillis();

        try {
            search.treeSearch(new Node(null, 1, 1, 1), searchMethod);
            if (search.solution != null) {
                printSolution(search.solution);
            } else if (search.timeLimitPassed) {
                System.out.println("Timeout.");
            } else {
                System.out.println("No solution exists.");
            }
        } catch (OutOfMemoryError e) {
            System.out.println("Out of memory.");
        } catch (StackOverflowError e) {
            System.out.println("Stack overflow.");
        }

        long endTime = System.currentTimeMillis();
        long runTime = endTime - startTime;

        long minutes = runTime / (60 * 1000);
        long seconds = (runTime % (60 * 1000)) / 1000;
        long milliseconds = runTime % 1000;

        System.out.printf("Finished in %02d:%02d:%03d%n", minutes, seconds, milliseconds);
        System.out.println("Number of nodes expanded: " + search.numberOfNodesExpanded);
    }

    private static void printSolution(Node node)  {
        System.out.println("A solution found.");
        try {
            File file = new File("src\\knight_tour_problem\\output.txt");
            writer = new FileWriter(file, false);
            findPath(node);
            writer.close();
        } catch (IOException e) {
            System.out.println("Solution couldn't be written to the output file.");
        }
    }

    private static void findPath(Node node) throws IOException {
        if (node.parent != null) {
            findPath(node.parent);
        }
        writer.write(node.locationX + "-" + node.locationY + "\n");
        System.out.println(node.locationX + "-" + node.locationY);
    }
}