package search_strategies;

import objects.Node;
import objects.Tree;

import java.util.LinkedList;
import java.util.Queue;

public class GeneralSearch {
    private final Tree tree;
    private boolean solutionFound = false;


    public GeneralSearch(Tree tree) {
        this.tree = tree;
    }

    public void depthFirst(Node node) {
        knightPositionDFS(node);
        if (!solutionFound) {
            System.out.println("No solution found.");
        }
    }

    private void knightPositionDFS(Node node) {
        if (solutionFound) return;
        if (node.depth == tree.n * tree.n) {
            tree.solution = node;
            tree.printSolutionPath(node);
            solutionFound = true;
            return;
        }
        tree.possibleMoves(node);
        for (Node child : node.children) {
            knightPositionDFS(child);
        }
    }

    public void breadthFirst(Node root) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty() && !solutionFound) {
            Node currentNode = queue.poll();
            if (currentNode.depth == tree.n * tree.n) {
                tree.solution = currentNode;
                tree.printSolutionPath(currentNode);
                solutionFound = true;
                break;
            }
            tree.possibleMoves(currentNode);
            queue.addAll(currentNode.children);
        }

        if (!solutionFound) {
            System.out.println("No solution found.");
        }
    }
}
