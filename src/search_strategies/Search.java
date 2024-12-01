package search_strategies;

import objects.Node;
import objects.Tree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class Search {
    private final Tree tree;
    private boolean solutionFound = false;


    public Search(Tree tree) {
        this.tree = tree;
    }

    public void depthFirstSearch(Node node) {
        depthFirstRecursive(node);
        if (!solutionFound) {
            System.out.println("No solution found.");
        }
    }

    private void depthFirstRecursive(Node node) {
        if (solutionFound) return;
        if (node.depth == tree.n * tree.n) {
            tree.solution = node;
            tree.printSolutionPath(node);
            solutionFound = true;
            return;
        }
        tree.possibleMoves(node);
        for (Node child : node.children) {
            depthFirstRecursive(child);
        }
    }

    public void breadthFirstSearch(Node root) {
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

    public void depthFirstSearchH1B(Node node) {
        depthFirstRecursiveH1B(node);
        if (!solutionFound) {
            System.out.println("No solution found.");
        }
    }

    private void depthFirstRecursiveH1B(Node node) {
        if (solutionFound) return;
        if (node.depth == tree.n * tree.n) { // Goal condition
            tree.solution = node;
            tree.printSolutionPath(node);
            solutionFound = true;
            return;
        }
        tree.possibleMoves(node);
        node.children.sort(Comparator.comparingInt(child -> tree.calculateH1B(child)));

        for (Node child : node.children) {
            depthFirstRecursive(child);
        }
    }
}
