package knighttourproblem;
import java.util.Scanner;
import objects.Tree;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the n value: ");
        int n = scanner.nextInt();

        scanner.nextLine();

        Tree tree = new Tree(n);
        String initialPosition;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                initialPosition = (char)(96 + i) + "" + j;
                tree.generalSearch(initialPosition);
                System.out.println(initialPosition);
            }
        }
    }
}