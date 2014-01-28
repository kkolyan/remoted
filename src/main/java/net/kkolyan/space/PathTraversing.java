package net.kkolyan.space;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author nplekhanov
 */
public class PathTraversing {
    public static PathNode getTree(File file) {
        List<PathNode> children = new ArrayList<PathNode>();
        long size = 0;
        if (file.isFile()) {
            size = file.length();
        }
        else {
            File[] subFiles = file.listFiles();
            if (subFiles != null) {
                for (File subFile: subFiles) {
                    PathNode child = getTree(subFile);
                    size += child.getSize();
                    children.add(child);
                }
            }
            Collections.sort(children, new Comparator<PathNode>() {
                @Override
                public int compare(PathNode o1, PathNode o2) {
                    return -Long.compare(o1.getSize(), o2.getSize());
                }
            });
        }
        return new PathNode(file, children, size);
    }
}
