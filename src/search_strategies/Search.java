package search_strategies;

import objects.Node;
import objects.Tree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class Search {
    private final Tree tree;
    private boolean solutionFound = false;
    private int heuristic = 0; //0 = no heuristic, 1 = h1b, 2 = h2b


    public Search(Tree tree) {
        this.tree = tree;
    }

    public void depthFirstSearch(Node node, int heuristic) {
        setHeuristic(heuristic);
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

        if (heuristic == 1) {
            node.children.sort(Comparator.comparingInt(child -> tree.calculateH1B(child)));
        } else if (heuristic == 2) {
            //H2B
        }

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

    private void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }
}