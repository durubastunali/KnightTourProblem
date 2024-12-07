package knight_tour_problem;

public class Node {
    public int locationY;
    public int locationX;
    public Node parent;
    public int depth;

    public Node(Node parent, int locationX, int locationY, int depth) {
        this.parent = parent;
        this.locationX = locationX;
        this.locationY = locationY;
        this.depth = depth;
    }
}
