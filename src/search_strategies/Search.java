package search_strategies;

import knight_tour_problem.Main;
import objects.Node;
import objects.Tree;

import java.util.*;

public class Search {
    private final Tree tree;
    public boolean solutionFound = false;
    public int numberOfNodesExpanded = 1;
    public boolean timeLimitPassed = false;
    public final int[] knightMoves = {-2, -1, 1, 2};
    private int locationX, locationY, newX, newY;


    public Search(Tree tree) {
        this.tree = tree;
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

        if (node.depth == tree.n * tree.n) {
            tree.solution = node;
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
                if (tree.checkInBorders(newX, newY) && tree.checkUnvisited(node, newX, newY)) {
                    numberOfNodesExpanded++;
                    depthFirstRecursive(new Node(node, newX, newY, node.depth + 1));
                }
            }
        }
    }

    private void depthFirstRecursiveH1B(Node node) {
        if (solutionFound) return;

        if (node.depth == tree.n * tree.n) {
            tree.solution = node;
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
                if (tree.checkInBorders(newX, newY) && tree.checkUnvisited(node, newX, newY)) {
                    int score = tree.sortByNextPossibleMove(new Node(node, newX, newY, node.depth + 1));
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

        if (node.depth == tree.n * tree.n) {
            tree.solution = node;
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
                if (tree.checkInBorders(newX, newY) && tree.checkUnvisited(node, newX, newY)) {
                    int score = tree.sortByNextPossibleMove(new Node(node, newX, newY, node.depth + 1));
                    if (score < bestScore) {
                        bestScore = score;
                        bestNode = new Node(node, newX, newY, node.depth + 1);
                    } else if (score == bestScore) {
                        int currentDistance = tree.sortByClosestToCorner(bestNode);
                        int newNodeDistance = tree.sortByClosestToCorner(new Node(node, newX, newY, node.depth + 1));
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
            if (currentNode.depth == tree.n * tree.n) {
                tree.solution = currentNode;
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
                    if (tree.checkInBorders(newX, newY) && tree.checkUnvisited(currentNode, newX, newY)) {
                        frontier.add(new Node(currentNode, newX, newY, currentNode.depth + 1));
                    }
                }
            }
            numberOfNodesExpanded++;
        }
    }

    private boolean checkTimeLimitPassed() {
        long currentTime = System.currentTimeMillis();
        return currentTime - Main.startTime >= Main.timeLimit;
    }
}