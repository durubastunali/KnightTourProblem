package knight_tour_problem;

import java.util.*;

public class Search {
    public Node solution;
    public int n;
    public boolean solutionFound = false;
    public int numberOfNodesExpanded = 1;
    public boolean timeLimitPassed = false;
    public final int[] knightMoves = {-2, -1, 1, 2};
    private final List<Node> frontier = new ArrayList<>();


    public Search(int n) {
        this.n = n;
    }

    public void treeSearch(Node root, char searchStrategy) {
        frontier.add(root);
        Node node = root;
        while (true) {
            if (node.depth == n * n) {
                solution = node;
                break;
            }
            if (frontier.isEmpty()) {
                break;
            }
            if (checkTimeLimitPassed()) {
                timeLimitPassed = true;
                break;
            }

            if (searchStrategy == 'a') {
                node = frontier.getFirst();
                frontier.removeFirst();

            } else if (searchStrategy == 'b' || searchStrategy == 'c' || searchStrategy == 'd') {
                node = frontier.getLast();
                frontier.removeLast();
            }

            getChildren(node, searchStrategy);
            numberOfNodesExpanded++;
        }
    }

    private void getChildren(Node node, char searchStrategy) {
        List<Node> children = new ArrayList<>();
        for (int moveHorizontal : knightMoves) {
            for (int moveVertical : knightMoves) {
                if (Math.abs(moveHorizontal) == Math.abs(moveVertical)) {
                    continue;
                }
                int newX = node.locationX + moveHorizontal;
                int newY = node.locationY + moveVertical;

                if (checkInBorders(newX, newY) && checkUnvisited(node, newX, newY)) {
                    children.add(new Node(node, newX, newY, node.depth + 1));
                }
            }
        }
        if (searchStrategy == 'c') {
            children.sort(Comparator.comparingInt(this::sortByNextPossibleMove));
        } else if (searchStrategy == 'd') {
            children.sort(Comparator.comparingInt(this::sortByNextPossibleMove)
                    .thenComparingInt(this::sortByClosestToCorner));
        }

        if (searchStrategy != 'a') {
            children.reversed();
        }

        frontier.addAll(children);
    }

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

    private boolean checkInBorders(int x, int y) {
        return (x >= 1) && (x <= n) && (y >= 1) && (y <= n);
    }

    private int sortByNextPossibleMove(Node node) {
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

    private int sortByClosestToCorner(Node node) {
        int x = node.locationX - 1;
        int y = node.locationY - 1;
        int distanceX = Math.min(x, n - 1 - x);
        int distanceY = Math.min(y, n - 1 - y);
        return distanceX + distanceY;
    }


    private boolean checkTimeLimitPassed() {
        long currentTime = System.currentTimeMillis();
        return currentTime - Main.startTime >= Main.timeLimit;
    }
}