package objects;

public class Tree {
    public Node root;
    public int n;

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
        return new Node(null, locationX, locationY);
    }

    private void buildTree(Node node) {
        possibleMoves(node);
        for (Node child : node.children) {
            buildTree(child);
        }
    }

    private void possibleMoves(Node node) {
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
        String newPosition = newX + "" + newY;
        if (checkBorders(newX, newY) && !node.visited.contains(newPosition)) {
            Node child = new Node(node, newX, newY);
            node.addChild(child);
        }
    }

    private boolean checkBorders(char x, int y) {
        return x >= 97 && x <= 96 + n && y >= 1 && y <= n;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void printTree() {
        if (root == null) {
            System.out.println("Tree is empty.");
            return;
        }
        printNode(root, 0);
    }

    private void printNode(Node node, int level) {
        String indent = "  ".repeat(level);
        System.out.println(indent + "Node: (" + node.locationX + ", " + node.locationY + ") - Visited: " + node.visited);
        for (Node child : node.children) {
            printNode(child, level + 1);
        }
    }
}
