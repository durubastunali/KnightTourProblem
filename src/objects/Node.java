package objects;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public int locationY;
    public int locationX;
    public Node parent;
    public List<Node> children;
    public int depth;

    public Node(Node parent, int locationX, int locationY, int depth) {
        this.parent = parent;
        this.locationX = locationX;
        this.locationY = locationY;
        this.children = new ArrayList<>();
        this.depth = depth;
    }

    public void addChild(Node child) {
        children.add(child);
    }
}
