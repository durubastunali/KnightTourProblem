package objects;

import java.util.ArrayList;
import java.util.List;

public class Node {
    int locationY;
    char locationX;
    Node parent;
    List<Node> children;

    public Node(Node parent, char locationX, int locationY) {
        this.parent = parent;
        this.locationX = locationX;
        this.locationY = locationY;
        this.children = new ArrayList<>();
    }

    public void addChild(Node child) {
        children.add(child);
    }
}
