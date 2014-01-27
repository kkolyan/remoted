package net.kkolyan.remoted;

import org.apache.commons.lang3.SystemUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author nplekhanov
 */
public class BlockTest {

    @Test
    public void testWithoutTail() throws IOException {
        assertEquals(
                "Using username \"kkolyan\"." + SystemUtils.LINE_SEPARATOR +
                        "kkolyan@kkolyan.fvds.ru's password:" + SystemUtils.LINE_SEPARATOR, create(
                "Using username \"kkolyan\".\n" +
                        "kkolyan@kkolyan.fvds.ru's password:\n" +
                        "Last login: Tue Dec 17 19:59:50 2013 from 5.19.250.71\n" +
                        "-bash-4.1#"
        ).getWithoutTail(2).getContent("utf8"));
    }


    @Test
    public void testTail() throws IOException {
        assertEquals(
                "" +
                        "9:59:50 2013" + SystemUtils.LINE_SEPARATOR +
                        " from 5.19.2" + SystemUtils.LINE_SEPARATOR +
                        "50.71" + SystemUtils.LINE_SEPARATOR +
                        "-bash-4.1#" + SystemUtils.LINE_SEPARATOR, create(
                "" +
                        "Using username \"kkolyan\".\n" +
                        "kkolyan@kkolyan.fvds.ru's password:\n" +
                        "Last login: Tue Dec 17 19:59:50 2013 from 5.19.250.71\n" +
                        "-bash-4.1#"
        ).getTail(4, 12).getContent("utf8"));
    }


    private Block create(String data) throws IOException {
        Block block = new Block(Stream.OUT);
        byte[] bytes = data.getBytes("utf8");
        block.write(bytes, 0, bytes.length);
        return block;
    }
}
