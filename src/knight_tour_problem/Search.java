package knight_tour_problem;

import java.util.*;

public class Search {
    public Node solution;
    public int n;
    public boolean solutionFound = false;
    public int numberOfNodesExpanded = 1;
    public boolean timeLimitPassed = false;
    public final int[] knightMoves = {-2, -1, 1, 2};
    private int locationX, locationY, newX, newY;


    public Search(int n) {
        this.n = n;
    }

    public void depthFirstSearch(Node node, int heuristic) {
        if (heuristic == 0) {
            depthFirstRecursive(node);
        } else if (heuristic == 1) {
            depthFirstRecursiveH1B(node);
        } else if (heuristic == 2) {
            depthFirstRecursiveH2(node);
        }
    }

    private void depthFirstRecursive(Node node) {
        if (solutionFound) return;

        if (node.depth == n * n) {
            solution = node;
            solutionFound = true;
            return;
        }

        if (checkTimeLimitPassed()) {
            timeLimitPassed = true;
            return;
        }

        locationX = node.locationX;
        locationY = node.locationY;
        for (int moveHorizontal : knightMoves) {
            for (int moveVertical : knightMoves) {
                if (Math.abs(moveHorizontal) == Math.abs(moveVertical)) {
                  continue;
                }
                newX = locationX + moveHorizontal;
                newY = locationY + moveVertical;
                if (checkInBorders(newX, newY) && checkUnvisited(node, newX, newY)) {
                    numberOfNodesExpanded++;
                    depthFirstRecursive(new Node(node, newX, newY, node.depth + 1));
                }
            }
        }
    }

    private void depthFirstRecursiveH1B(Node node) {
        if (solutionFound) return;

        if (node.depth == n * n) {
            solution = node;
            solutionFound = true;
            return;
        }

        if (checkTimeLimitPassed()) {
            timeLimitPassed = true;
            return;
        }

        locationX = node.locationX;
        locationY = node.locationY;

        Node bestNode = null;
        int bestScore = 8;

        for (int moveHorizontal : knightMoves) {
            for (int moveVertical : knightMoves) {
                if (Math.abs(moveHorizontal) == Math.abs(moveVertical)) {
                    continue;
                }
                newX = locationX + moveHorizontal;
                newY = locationY + moveVertical;
                if (checkInBorders(newX, newY) && checkUnvisited(node, newX, newY)) {
                    int score = sortByNextPossibleMove(new Node(node, newX, newY, node.depth + 1));
                    if (score < bestScore) {
                        bestScore = score;
                        bestNode = new Node(node, newX, newY, node.depth + 1);
                    }
                }
            }
        }
        if (bestNode != null) {
            numberOfNodesExpanded++;
            depthFirstRecursiveH1B(bestNode);
        }
    }

    private void depthFirstRecursiveH2(Node node) {
        if (solutionFound) return;

        if (node.depth == n * n) {
            solution = node;
            solutionFound = true;
            return;
        }

        if (checkTimeLimitPassed()) {
            timeLimitPassed = true;
            return;
        }

        locationX = node.locationX;
        locationY = node.locationY;

        Node bestNode = null;
        int bestScore = 8;

        for (int moveHorizontal : knightMoves) {
            for (int moveVertical : knightMoves) {
                if (Math.abs(moveHorizontal) == Math.abs(moveVertical)) {
                    continue;
                }
                newX = locationX + moveHorizontal;
                newY = locationY + moveVertical;
                if (checkInBorders(newX, newY) && checkUnvisited(node, newX, newY)) {
                    int score = sortByNextPossibleMove(new Node(node, newX, newY, node.depth + 1));
                    if (score < bestScore) {
                        bestScore = score;
                        bestNode = new Node(node, newX, newY, node.depth + 1);
                    } else if (score == bestScore) {
                        int currentDistance = sortByClosestToCorner(bestNode);
                        int newNodeDistance = sortByClosestToCorner(new Node(node, newX, newY, node.depth + 1));
                        if (newNodeDistance < currentDistance) {
                            bestNode = new Node(node, newX, newY, node.depth + 1);
                        }
                    }
                }
            }
        }

        if (bestNode != null) {
            numberOfNodesExpanded++;
            depthFirstRecursiveH2(bestNode);
        }
    }



    public void breadthFirstSearch(Node root) {
        Queue<Node> frontier = new LinkedList<>();
        frontier.add(root);
        Node currentNode;
        while (!frontier.isEmpty() && !solutionFound) {
            currentNode = frontier.poll();
            if (currentNode.depth == n * n) {
                solution = currentNode;
                solutionFound = true;
                break;
            }

            locationX = currentNode.locationX;
            locationY = currentNode.locationY;
            for (int moveHorizontal : knightMoves) {
                for (int moveVertical : knightMoves) {
                    if (Math.abs(moveHorizontal) == Math.abs(moveVertical)) {
                        continue;
                    }
                    newX = locationX + moveHorizontal;
                    newY = locationY + moveVertical;
                    if (checkInBorders(newX, newY) && checkUnvisited(currentNode, newX, newY)) {
                        frontier.add(new Node(currentNode, newX, newY, currentNode.depth + 1));
                    }
                }
            }
            numberOfNodesExpanded++;
        }
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

    private boolean checkTimeLimitPassed() {
        long currentTime = System.currentTimeMillis();
        return currentTime - Main.startTime >= Main.timeLimit;
    }
}