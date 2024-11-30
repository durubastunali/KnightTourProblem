package objects;

import java.util.ArrayList;
import java.util.List;

public class Node {
    int locationY;
    char locationX;
    Node parent;
    List<Node> children;
    int depth;

    public Node(Node parent, char locationX, int locationY, int depth) {
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
