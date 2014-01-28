package net.kkolyan.space;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nplekhanov
 */
public class PathNode {
    private long size;
    private List<PathNode> children;
    private File file;

    public PathNode(File file, List<PathNode> children, long size) {
        this.file = file;
        this.children = children;
        this.size = size;
    }

    public long getSize() {
        return size;
    }

    public List<PathNode> getChildren() {
        return children;
    }

    public File getFile() {
        return file;
    }
}
