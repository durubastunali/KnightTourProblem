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
        String initialPosition;
        Node root;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                initialPosition = (char)(96 + i) + "" + j;
                root = tree.createRoot(initialPosition);
                if (searchMethod == 'a') { //BREADTH FIRST SEARCH
                    search.breadthFirstSearch(root);
                } else if (searchMethod == 'b') { //DEPTH FIRST SEARCH
                    search.depthFirstSearch(root);
                } else if (searchMethod == 'c') { //DFS WITH H1B
                    search.depthFirstSearchH1B(root);
                } else if (searchMethod == 'd') { //DFS WITH H2

                } else {
                    System.out.println("Invalid search method.");
                }
            }
        }
    }
}