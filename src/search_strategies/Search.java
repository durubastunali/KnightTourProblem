package search_strategies;

import knight_tour_problem.Main;
import objects.Node;
import objects.Tree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Search {
    private final Tree tree;
    public boolean solutionFound = false;
    private int heuristic = 0; //0 = no heuristic, 1 = h1b, 2 = h2b
    public int numberOfNodesExpanded = 1;
    public boolean timeLimitPassed = false;
    public final int[] knightMoves = {-2, -1, 1, 2};
    private int locationX, locationY, newX, newY;
    private List<Node> children ;

    public Search(Tree tree) {
        this.tree = tree;
    }

    public void depthFirstSearch(Node node, int heuristic) {
        setHeuristic(heuristic);
        depthFirstRecursive(node);
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

//    private void depthFirstRecursive(Node node) {
//        if (solutionFound) return;
//
//        if (node.depth == tree.n * tree.n) {
//            tree.solution = node;
//            tree.printSolutionPath(node);
//            solutionFound = true;
//            return;
//        }
//
//        tree.possibleMoves(node);
//
//        if (heuristic == 1) {
//            //node.children.removeIf(child -> tree.sortByNextPossibleMove(child) == 0);
//            node.children.sort(Comparator.comparingInt(tree::sortByNextPossibleMove));
//
//        } else if (heuristic == 2) {
//            //node.children.removeIf(child -> tree.sortByNextPossibleMove(child) == 0);
//            node.children.sort(
//                    Comparator.comparingInt(tree::sortByNextPossibleMove)
//                            .thenComparingInt(tree::sortByClosestToCorner)
//            );
//        }
//        for (Node child : node.children) {
//            depthFirstRecursive(child);
//        }
//    }

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

    private void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    private boolean checkTimeLimitPassed() {
        long currentTime = System.currentTimeMillis();
        return currentTime - Main.startTime >= Main.timeLimit;
    }
}