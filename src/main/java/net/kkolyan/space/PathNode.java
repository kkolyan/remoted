package net.kkolyan.space;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author nplekhanov
 */
public class PathNode {
    private String name;
    private long size;
    private List<PathNode> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PathNode> getChildren() {
        return children;
    }

    public void setChildren(List<PathNode> children) {
        this.children = children;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
