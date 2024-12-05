package objects;

public class Tree {
    public Node root;
    public Node solution;
    public int n;
    public final int[] knightMoves = {-2, -1, 1, 2};

    public Tree(int n) {
        this.n = n;
    }

    public Node createRoot(int initialX, int initialY) {
        Node rootNode = new Node(null, initialX, initialY, 1);
        setRoot(rootNode);
        return rootNode;
    }

    public void possibleMoves(Node node) {
        int locationX = node.locationX;
        int locationY = node.locationY;

        for (int moveHorizontal : knightMoves) {
            for (int moveVertical : knightMoves) {
                if (Math.abs(moveHorizontal) != Math.abs(moveVertical)) {
                    move(locationX, locationY, moveHorizontal, moveVertical, node);
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

    private void move(int locationX, int locationY, int moveX, int moveY, Node node) {
        int newX = locationX + moveX;
        int newY = locationY + moveY;
        if (checkBorders(newX, newY) && checkVisited(node, newX, newY)) {
            Node child = new Node(node, newX, newY, node.depth + 1);
            node.addChild(child);
        }
    }

    private boolean checkVisited(Node node, int newX, int newY) {
        Node currentNode = node.parent;
        while (currentNode != null) {
            if (currentNode.locationX == newX && currentNode.locationY == newY) {
                return false;
            }
            currentNode = currentNode.parent;
        }
        return true;
    }

    private boolean checkBorders(int x, int y) {
        return (x >= 1) && (x <= n) && (y >= 1) && (y <= n);
    }

    public int sortByNextPossibleMove(Node node) {
        int nodeX = node.locationX;
        int nodeY = node.locationY;

        int newX;
        int newY;

        int h1b = 0;

        for (int moveHorizontal : knightMoves) {
            for (int moveVertical : knightMoves) {
                if (Math.abs(moveHorizontal) != Math.abs(moveVertical)) {
                    newX = nodeX + moveHorizontal;
                    newY = nodeY + moveVertical;
                    if (checkVisited(node, newX, newY) && checkBorders(newX, newY)) {
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


    private void setRoot(Node root) {
        this.root = root;
    }

    public void printSolutionPath(Node node) {
        Node currentNode = node;
        System.out.println("Solution Path:");
        while (currentNode != null) {
            System.out.println(currentNode.locationX + "-" + currentNode.locationY);
            currentNode = currentNode.parent;
        }
    }

    public void checkSolutionCorrect() {
        //CHECK CORRECT PATH
        Node node = solution;
        while (node.parent != null) {
            int parentX = node.parent.locationX;
            int parentY = node.parent.locationY;
            int childX = node.locationX;
            int childY = node.locationY;
            if (!((Math.abs(parentX - childX) == 2 && Math.abs(parentY - childY) == 1) ||
                (Math.abs(parentX - childX) == 1 && Math.abs(parentY - childY) == 2))) {
                System.out.println("Wrong Solution");
                return;
            }
            node = node.parent;
        }

        node = solution;
        while (node != null) {
            int nodeX = node.locationX;
            int nodeY = node.locationY;

            Node currentNode = node.parent; // Start checking from the parent of the current node
            while (currentNode != null) {
                int curNodeX = currentNode.locationX;
                int curNodeY = currentNode.locationY;

                // Check for duplicate coordinates
                if (nodeX == curNodeX && nodeY == curNodeY) {
                    System.out.println("Wrong Solution2");
                    return;
                }

                currentNode = currentNode.parent; // Move to the next parent
            }

            node = node.parent; // Move to the next node in the chain
        }


        System.out.println("Correct Solution");
    }
}