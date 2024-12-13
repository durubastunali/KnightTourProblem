// The Knight's Tour Problem: 150120042, 150120053, 150120075
package knight_tour_problem;

public class Node {
    public int locationX; // X location of the Node
    public int locationY; // Y location of the Node
    public int depth; // Depth of the Node
    public Node parent; // Parent of the Node

    public Node(Node parent, int locationX, int locationY, int depth) {
        this.parent = parent;
        this.locationX = locationX;
        this.locationY = locationY;
        this.depth = depth;
    }
}
