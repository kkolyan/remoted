import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nplekhanov
 */
public class SplitPath {
    public static void main(String[] args) throws IOException {
        List<? super String> path = new ArrayList<String>();
        getPath(new File(args[0]).getCanonicalFile(), path);
        System.out.println(path);
    }

    private static void getPath(File file, List<? super String> path) {
        File parent = file.getParentFile();
        if (parent != null) {
            getPath(parent, path);
        }
        path.add(file.getName()+"="+file.getPath());
    }
}
