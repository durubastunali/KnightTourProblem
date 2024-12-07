package objects;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Tree {
    public Node solution;
    public int n;
    public final int[] knightMoves = {-2, -1, 1, 2};
    private FileWriter writer = null;

    public Tree(int n) {
        this.n = n;
    }

    public Node createRoot(int initialX, int initialY) {
        return new Node(null, initialX, initialY, 1);
    }

    public boolean checkUnvisited(Node node, int newX, int newY) {
        Node currentNode = node.parent;
        while (currentNode != null) {
            if (currentNode.locationX == newX && currentNode.locationY == newY) {
                return false;
            }
            currentNode = currentNode.parent;
        }
        return true;
    }

    public boolean checkInBorders(int x, int y) {
        return (x >= 1) && (x <= n) && (y >= 1) && (y <= n);
    }

    public int sortByNextPossibleMove(Node node) {
        int nodeX = node.locationX;
        int nodeY = node.locationY;
        int newX, newY, h1b = 0;

        for (int moveHorizontal : knightMoves) {
            for (int moveVertical : knightMoves) {
                if (Math.abs(moveHorizontal) != Math.abs(moveVertical)) {
                    newX = nodeX + moveHorizontal;
                    newY = nodeY + moveVertical;
                    if (checkUnvisited(node, newX, newY) && checkInBorders(newX, newY)) {
                        h1b++;
                    }
                }
            }
        }
        return h1b;
    }

    public int sortByClosestToCorner(Node node) {
        int x = node.locationX;
        int y = node.locationY;

        int distanceToTopLeft = Math.abs(x - 1) + Math.abs(y - 1);
        int distanceToTopRight = Math.abs(x - 1) + Math.abs(y - n);
        int distanceToBottomLeft = Math.abs(x - n) + Math.abs(y - 1);
        int distanceToBottomRight = Math.abs(x - n) + Math.abs(y - n);

        return Math.min(Math.min(distanceToTopLeft, distanceToTopRight),
                Math.min(distanceToBottomLeft, distanceToBottomRight));
    }

    public void printSolution(Node node)  {
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

    private void findPath(Node node) throws IOException {
        if (node.parent != null) {
            findPath(node.parent);
        }
        writer.write(node.locationX + "-" + node.locationY + "\n");
        System.out.println(node.locationX + "-" + node.locationY);
    }
}