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
        Node root;

        timeLimit = (long) t * 60 * 1000;
        startTime = System.currentTimeMillis();

        outerloop:
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {

                root = new Node(null, i, j, 1);
                try {
                    if (searchMethod == 'a') { //BREADTH FIRST SEARCH
                        search.breadthFirstSearch(root);
                    } else if (searchMethod == 'b') { //DEPTH FIRST SEARCH
                        search.depthFirstSearch(root, 0);
                    } else if (searchMethod == 'c') { //DFS WITH H1B
                        search.depthFirstSearch(root, 1);
                    } else if (searchMethod == 'd') { //DFS WITH H2
                        search.depthFirstSearch(root, 2);
                    } else {
                        System.out.println("Invalid search method.");
                        break outerloop;
                    }
                } catch (OutOfMemoryError | StackOverflowError e) {
                    System.out.println("Out of memory.");
                    break outerloop;
                }

                if (search.solutionFound) {
                    printSolution(search.solution);
                    break outerloop;
                }

                if (search.timeLimitPassed) {
                    System.out.println("Timeout.");
                    break outerloop;
                }

                if (i == n && j == n) {
                    System.out.println("No solution exists.");
                    break outerloop;
                }
            }
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        long minutes = elapsedTime / (60 * 1000);
        long seconds = (elapsedTime % (60 * 1000)) / 1000;
        long milliseconds = elapsedTime % 1000;

        System.out.printf("Finished in %02d:%02d:%03d%n", minutes, seconds, milliseconds);
        System.out.println("Number of nodes expanded: " + search.numberOfNodesExpanded);
    }

    public static void printSolution(Node node)  {
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