// The Knight's Tour Problem: 150120042, 150120053, 150120075
package knight_tour_problem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Search {
    public int n;
    public Node solution = null; // Last node of the solution path, initially null: indicating no solution found yet
    public boolean timeLimitPassed = false;

    public final int[] knightMoves = {-2, -1, 1, 2}; // One dimensional legal moves
    private final ArrayList<Node> frontier = new ArrayList<>();
    private final ArrayList<Node> children = new ArrayList<>();
    public int numberOfNodesExpanded = 0;

    public Search(int n) { // Set the size of the board for the problem
        this.n = n;
    }

    public void treeSearch(Node root, char searchStrategy) {
        frontier.add(root); // Initially, add the root to the frontier
        Node node = root;
        while (true) {
            if (node.depth == n * n) { // A solution is found if a node at depth n * n is found, return
                solution = node;
                return;
            }
            if (frontier.isEmpty()) { // No solution is found if the frontier is empty, return
                return;
            }
            if (checkTimeLimitPassed()) { // Check whether the time limit is passed or not. If passed, return
                timeLimitPassed = true;
                return;
            }

            if (searchStrategy == 'a') {
                node = frontier.removeFirst(); // For BFS, make the frontier act like a queue
            } else if (searchStrategy == 'b' || searchStrategy == 'c' || searchStrategy == 'd') {
                node = frontier.removeLast(); // For DFS, make the frontier act like a stack
            } else { // If any other input than "a, b, c or d" is given as search strategy, invalid, return
                System.out.println("Invalid search method.");
                return;
            }

            // Add the children of the expanded node into frontier
            getChildren(node, searchStrategy); // Details of how to add is in the method below
            numberOfNodesExpanded++;
        }
    }

    private void getChildren(Node node, char searchStrategy) {
        children.clear();
        for (int moveHorizontal : knightMoves) {
            for (int moveVertical : knightMoves) {
                if (Math.abs(moveHorizontal) == Math.abs(moveVertical)) { // Eliminate the illegal moves
                    continue;
                }
                // Calculate the new location after moving the knight
                int newX = node.locationX + moveHorizontal;
                int newY = node.locationY + moveVertical;

                // Check whether the tile to be moved is unvisited and remain in borders
                if (checkInBorders(newX, newY) && checkUnvisited(node, newX, newY)) {
                    children.add(new Node(node, newX, newY, node.depth + 1));
                }
            }
        }
        if (searchStrategy == 'c') { // If h1b; sort the list by number of possible moves
            children.sort(Comparator.comparingInt(this::getNumberOfPossibleMoves));
        } else if (searchStrategy == 'd') { // If h2; additionally sort the list by closest to corners
            children.sort(Comparator.comparingInt(this::getNumberOfPossibleMoves)
                    .thenComparingInt(this::getDistanceToCorner));
        }

        if (searchStrategy != 'a') { // If DFS, add children to frontier reversed for stack structure
            Collections.reverse(children);
        }

        frontier.addAll(children); // Add the children of the expanded node after proper structure is formed
    }

    // Through the path, check whether any node has the same location with the new location
    private boolean checkUnvisited(Node node, int newX, int newY) {
        Node currentNode = node.parent;
        while (currentNode != null) {
            if (currentNode.locationX == newX && currentNode.locationY == newY) {
                return false;
            }
            currentNode = currentNode.parent;
        }
        return true;
    }

    // Check whether the new location remains in border
    private boolean checkInBorders(int x, int y) {
        return (x >= 0) && (x < n) && (y >= 0) && (y < n);
    }

    private int getNumberOfPossibleMoves(Node node) {
        int nodeX = node.locationX;
        int nodeY = node.locationY;
        int newX, newY, h1b = 0; // Initially, number of possible moves from that node is 0

        for (int moveHorizontal : knightMoves) {
            for (int moveVertical : knightMoves) {
                if (Math.abs(moveHorizontal) != Math.abs(moveVertical)) {
                    newX = nodeX + moveHorizontal;
                    newY = nodeY + moveVertical;
                    if (checkUnvisited(node, newX, newY) && checkInBorders(newX, newY)) {
                        h1b++; // As a legal move can be made, the number of possible moves is incremented
                    }
                }
            }
        }
        return h1b;
    }

    private int getDistanceToCorner(Node node) {
        int x = node.locationX;
        int y = node.locationY;
        int distanceX = Math.min(x, n - 1 - x); // Check the closest distance to edges horizontally
        int distanceY = Math.min(y, n - 1 - y); // Check the closest distance to edges vertically
        return distanceX + distanceY; // Return the manhattan distance to the closest corner
    }

    private boolean checkTimeLimitPassed() { // Check whether the time limit is exceeded
        long currentTime = System.currentTimeMillis();
        return currentTime - Main.startTime >= Main.timeLimit;
    }
}