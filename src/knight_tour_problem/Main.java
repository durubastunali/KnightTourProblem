package knight_tour_problem;
import java.util.Scanner;

import objects.Node;
import objects.Tree;
import search_strategies.Search;

public class Main {

    public static long startTime;
    public static long timeLimit;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Size of the board (n): ");
        int n = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Search method (a-d): ");
        char searchMethod = scanner.nextLine().charAt(0);


        System.out.print("Time limit (t): ");
        int t = scanner.nextInt();

        Tree tree = new Tree(n);
        Search search = new Search(tree);
        Node root;

        timeLimit = (long) t * 60 * 1000;
        startTime = System.currentTimeMillis();

        outerloop:
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                root = tree.createRoot(i, j);
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
                    tree.printSolution(tree.solution);
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
}