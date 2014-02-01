package net.kkolyan.space;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author nplekhanov
 */
public class PathTraversing {
    public static PathNode getTree(File file) throws IOException {
        PathNode tree = getTree(file.getCanonicalFile(), null);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationConfig(mapper.getSerializationConfig()
//                .with(SerializationConfig.Feature.INDENT_OUTPUT)
                .withSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT)
        );
        mapper.writeValue(new File(UUID.randomUUID().toString()), tree);
        return tree;
    }

    private static PathNode getTree(File file, PathNode parent) {
        long size = 0;

        PathNode node = new PathNode();
        if (parent == null) {
            node.setName(file.getPath());
        }
        else {
            node.setName(file.getName());
        }

        if (file.isFile()) {
            size = file.length();
        }
        else {
            ArrayList<PathNode> children = new ArrayList<PathNode>();
            File[] subFiles = file.listFiles();
            if (subFiles != null) {
                for (File subFile: subFiles) {
                    PathNode child = getTree(subFile, node);
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
            children.trimToSize();
            node.setChildren(children);
        }
        node.setSize(size);
        return node;
    }
}
