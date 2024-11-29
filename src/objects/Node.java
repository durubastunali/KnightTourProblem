package objects;

import java.util.ArrayList;
import java.util.List;

public class Node {
    int locationY;
    char locationX;
    Node parent;
    List<Node> children;
    List<String> visited;

    public Node(Node parent, char locationX, int locationY) {
        this.parent = parent;
        this.locationX = locationX;
        this.locationY = locationY;
        this.children = new ArrayList<>();
        this.visited = (parent != null) ? new ArrayList<>(parent.visited) : new ArrayList<>();
        this.visited.add(locationX + "" + locationY);
    }

    public void addChild(Node child) {
        children.add(child);
    }
}
