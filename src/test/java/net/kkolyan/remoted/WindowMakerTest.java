package net.kkolyan.remoted;

import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

/**
 * @author nplekhanov
 */
public class WindowMakerTest {

    @Test
    public void test1() throws IOException {
        assertEquals(join(create(
                "" +
                        "Dec 17 1\n" +
                        "9:59:50 \n" +
                        "2013 fro\n" +
                        "m 5.19.2\n"
        )), join(WindowMaker.getWindow(8, 4, 3, create(
                "Using username \"kkolyan\".\n" +
                        "kkolyan@kkolyan.fvds.ru's password:\n",
                "Last login: Tue Dec 17 19:59:50 2013 from 5.19.250.71\n",
                "-bash-4.1#"
        ))));
    }

    private String join(Collection<Block> blocks) throws IOException {
        StringBuilder s = new StringBuilder();
        for (Block block: blocks) {
            s.append(block.getContent("utf8"));
        }
        return s.toString();
    }

    private Deque<Block> create(String... datas) throws IOException {
        Deque<Block> deque = new ArrayDeque<Block>();
        for (String data: datas) {
            Block block = new Block(Stream.OUT);
            byte[] bytes = data.getBytes("utf8");
            block.write(bytes, 0, bytes.length);
            deque.add(block);
        }
        return deque;
    }
}
