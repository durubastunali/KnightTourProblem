package objects;

public class Tree {
    public Node root;
    public Node solution;
    public int n;
    private boolean solutionFound = false;

    public Tree(int n) {
        this.n = n;
    }

    public void createTree(String initialPosition) {
        Node root = createRoot(initialPosition);
        setRoot(root);
        buildTree(root);

        System.out.println("Tree created.");
    }

    private Node createRoot(String initialPosition) {
        char locationX = initialPosition.charAt(0);
        int locationY = Integer.parseInt(initialPosition.substring(1));
        return new Node(null, locationX, locationY, 1);
    }

    private void buildTree(Node node) {
        possibleMoves(node);
        if (!solutionFound) {
            for (Node child : node.children) {
                buildTree(child);
            }
        }

    }

    private void possibleMoves(Node node) {
        if (node.depth == n * n) {
            solution = node;
            printSolutionPath(node);
            solutionFound = true;
            return; // Stop further exploration if max depth is reached
        }

        char locationX = node.locationX;
        int locationY = node.locationY;
        move(locationX, locationY, -2, 1, node); // TWO LEFT, ONE UP
        move(locationX, locationY, -2, -1, node); // TWO LEFT, ONE DOWN
        move(locationX, locationY, -1, 2, node); // ONE LEFT, TWO UP
        move(locationX, locationY, 1, 2, node); // ONE RIGHT, TWO UP
        move(locationX, locationY, 2, 1, node); // TWO RIGHT, ONE UP
        move(locationX, locationY, 2, -1, node); // TWO RIGHT, ONE DOWN
        move(locationX, locationY, 1, -2, node); // ONE RIGHT, TWO DOWN
        move(locationX, locationY, -1, -2, node); // ONE LEFT, TWO DOWN
    }

    private void move(char locationX, int locationY, int moveX, int moveY, Node node) {
        char newX = (char) (locationX + moveX);
        int newY = locationY + moveY;
        if (checkBorders(newX, newY) && checkVisited(node, newX, newY)) {
            Node child = new Node(node, newX, newY, node.depth + 1); // Increment depth
            node.addChild(child);
        }
    }


    private boolean checkVisited(Node node, char newX, int newY) {
        Node currentNode = node.parent;
        while (currentNode != null) {
            if ((currentNode.locationX + "" + currentNode.locationY).equals(newX + "" + newY)) {
                return false;
            }
            currentNode = currentNode.parent;
        }
        return true;
    }

    private boolean checkBorders(char x, int y) {
        return x >= 97 && x <= 96 + n && y >= 1 && y <= n;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    private void printSolutionPath(Node node) {
        Node currentNode = node;
        while (currentNode != null) {
            System.out.println(currentNode.locationX + "" + currentNode.locationY);
            currentNode = currentNode.parent;
        }
    }
}
