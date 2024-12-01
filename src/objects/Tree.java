package objects;

public class Tree {
    public Node root;
    public Node solution;
    public int n;
    public final int[] knightMoves = {-2, -1, 1, 2};

    public Tree(int n) {
        this.n = n;
    }

    public Node createRoot(String initialPosition) {
        char locationX = initialPosition.charAt(0);
        int locationY = Integer.parseInt(initialPosition.substring(1));
        Node rootNode = new Node(null, locationX, locationY, 1);
        setRoot(rootNode);
        return rootNode;
    }

    public void possibleMoves(Node node) {
        char locationX = node.locationX;
        int locationY = node.locationY;

        for (int i = 0; i < knightMoves.length; i++) {
            for (int j = 0; j < knightMoves.length; j++) {
                if (Math.abs(knightMoves[i]) != Math.abs(knightMoves[j])) {
                    move(locationX, locationY, knightMoves[i], knightMoves[j], node);
                }
            }
        }
/*

        move(locationX, locationY, -2, 1, node);  // TWO LEFT, ONE UP
        move(locationX, locationY, -2, -1, node); // TWO LEFT, ONE DOWN
        move(locationX, locationY, -1, 2, node);  // ONE LEFT, TWO UP
        move(locationX, locationY, 1, 2, node);   // ONE RIGHT, TWO UP
        move(locationX, locationY, 2, 1, node);   // TWO RIGHT, ONE UP
        move(locationX, locationY, 2, -1, node);  // TWO RIGHT, ONE DOWN
        move(locationX, locationY, 1, -2, node);  // ONE RIGHT, TWO DOWN
        move(locationX, locationY, -1, -2, node); // ONE LEFT, TWO DOWN

 */


    }

    private void move(char locationX, int locationY, int moveX, int moveY, Node node) {
        char newX = (char) (locationX + moveX);
        int newY = locationY + moveY;
        if (checkBorders(newX, newY) && checkVisited(node, newX, newY)) {
            Node child = new Node(node, newX, newY, node.depth + 1);
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

    public int calculateH1B(Node node) {
        char nodeX = node.locationX;
        int nodeY = node.locationY;

        char newX;
        int newY;

        int h1b = 0;

        for (int i = 0; i < knightMoves.length; i++) {
            for (int j = 0; j < knightMoves.length; j++) {
                if (Math.abs(knightMoves[i]) != Math.abs(knightMoves[j])) {
                    newX = (char)(nodeX + knightMoves[i]);
                    newY = nodeY + knightMoves[j];
                    if (checkVisited(node, newX, newY) && checkBorders(newX, newY)) {
                        h1b++;
                    }
                }
            }
        }

        return h1b;
    }

    private void setRoot(Node root) {
        this.root = root;
    }

    public void printSolutionPath(Node node) {
        Node currentNode = node;
        System.out.println("Solution Path:");
        while (currentNode != null) {
            System.out.println(currentNode.locationX + "" + currentNode.locationY);
            currentNode = currentNode.parent;
        }
    }
}