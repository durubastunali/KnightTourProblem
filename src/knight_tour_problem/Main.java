package knight_tour_problem;
import java.util.Scanner;

import objects.Node;
import objects.Tree;
import search_strategies.Search;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the n value: ");
        int n = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Select search method (a - d): ");
        char searchMethod = scanner.nextLine().charAt(0);

        Tree tree = new Tree(n);
        Search search = new Search(tree);
        Node root;

        outerloop:
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                root = tree.createRoot(i, j);
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
                }

                if (search.solutionFound) {
                    break outerloop;
                }

                if (i == n && j == n) {
                    System.out.println("No solution exists.");
                }
            }
        }
    }
}