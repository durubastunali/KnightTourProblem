package knighttourproblem;
import java.util.Scanner;
import objects.Tree;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the n value: ");
        int n = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Enter the initial position: ");
        String initialPosition = scanner.nextLine();

        Tree tree = new Tree(n);
        tree.createTree(initialPosition);

    }
}