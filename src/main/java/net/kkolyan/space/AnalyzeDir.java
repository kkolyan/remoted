package net.kkolyan.space;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @author nplekhanov
 */
public class AnalyzeDir {
    public static void main(String[] args) throws IOException {
        String[] lines = IOUtils.toString(new URL(args[0])).trim().split("\\n");
        List<Node> nodes = new ArrayList<Node>();
        for (String line: lines) {
            Node node = new Node();
            node.size = Long.parseLong(line.split("\\s")[0]);
            node.path = line.split("\\s")[1];
            nodes.add(node);
        }
        Collections.sort(nodes, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.path.compareTo(o2.path);
            }
        });
        for (Node node: nodes) {
//            System.out.println(node.path);
        }
        Node root = new Node();
        root.path = "";
        Node last = root;
        for (Node node: nodes) {
            last.isYourChildren(node);
            last = node;
        }

        for (Node node: root.children) {
            node.render();
        }
    }

    private static class Node {
        long size;
        String path;
        List<Node> children = new ArrayList<Node>();
        Node parent;

        void isYourChildren(Node other) {
            if (other.path.startsWith(path + "/")) {
                attach(other);
            }
            else {
                if (parent == null) {
                    throw new NullPointerException();
                }
                parent.isYourChildren(other);
            }
        }

        void attach(Node child) {
            children.add(child);
            child.parent = this;
        }

        void render() {
            System.out.println(parent.path.replaceAll(".", " ")+path.substring(parent.path.length()) + " "+size);
            Collections.sort(children, new Comparator<Node>() {
                @Override
                public int compare(Node o1, Node o2) {
                    return -Long.compare(o1.size, o2.size);
                }
            });
            for (Node node: children) {
                node.render();
            }
        }

        @Override
        public String toString() {
            return "Node{" +
                    "path='" + path + '\'' +
                    '}';
        }
    }
}
