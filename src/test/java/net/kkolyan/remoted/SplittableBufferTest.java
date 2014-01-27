package net.kkolyan.remoted;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Deque;

import static org.junit.Assert.*;

/**
 * @author nplekhanov
 */
public class SplittableBufferTest {

    @Test
    public void testCommon() throws IOException {
        assertSplit(
                "Before the JSP 2.1 v\n" +
                        "ersion you were saw \n" +
                        "the unwanted white s\n" +
                        "paces in the respons\n" +
                        "e page these white s\n" +
                        "paces makes the html\n" +
                        " source",
                "Before the JSP 2.1 v" +
                        "ersion you were saw " +
                        "the unwanted white s" +
                        "paces in the respons" +
                        "e page these white s" +
                        "paces makes the html" +
                        " source",
                20
        );
    }

    @Test
    public void testBackspace() throws IOException {
        assertSplit(
                "Before the JSP 2.1 v\n" +
                        "ersion you were saw \n" +
                        "the unwanted white s\n" +
                        "paces in the respons\n" +
                        "e page these white s\n" +
                        "paces makes the html\n" +
                        " source",
                "Before the JSP 2.1 v" +
                        "ersion you were saw " +
                        "the unwanted white s" +
                        "pa123\b\b\bces in the respons" +
                        "e page these white s" +
                        "paces makes the html" +
                        " source",
                20
        );
    }

    @Test
    public void testMultiBlock() throws IOException {
        assertSplit(
                "Before the JSP 2.1 v\n" +
                        "ersion\n" +
                        "the unwanted white s\n" +
                        "paces in the respons\n" +
                        "e page these white s\n" +
                        "paces makes the html\n" +
                        " source",
                "Before the JSP 2.1 v" +
                        "ersion\n" +
                        "the unwanted white s" +
                        "paces in the respons" +
                        "e page these white s" +
                        "paces makes the html" +
                        " source",
                20
        );
    }

    @Test
    public void testMultiBlock2() throws IOException {
        assertSplit(
                "Before the JSP 2.1 v\n" +
                        "ersion you were saw \n" +
                        "\n" +
                        "the unwanted white s\n" +
                        "paces in the respons\n" +
                        "e page these white s\n" +
                        "paces makes the html\n" +
                        " source",
                "Before the JSP 2.1 v" +
                        "ersion you were saw \n" +
                        "the unwanted white s" +
                        "paces in the respons" +
                        "e page these white s" +
                        "paces makes the html" +
                        " source",
                20
        );
    }

    @Test
    public void testPrecise() throws IOException {
        assertSplit(
                "Before the JSP 2.1 v\n" +
                        "ersion you were saw \n" +
                        "the unwanted white s\n" +
                        "paces in the respons\n" +
                        "e page these white s\n" +
                        "paces makes the html",
                "Before the JSP 2.1 v" +
                        "ersion you were saw " +
                        "the unwanted white s" +
                        "paces in the respons" +
                        "e page these white s" +
                        "paces makes the html",
                20
        );
    }

    @Test
    public void testRange() throws IOException {
        assertEquals(
                "ersion you were saw \n" +
                        "the unwanted white s\n" +
                        "paces in the respons\n", create(
                "Before the JSP 2.1 v\n" +
                        "ersion you were saw \n" +
                        "the unwanted white s\n" +
                        "paces in the respons\n" +
                        "e page these white s\n" +
                        "paces makes the html").getRowsRange(2, 3).toString());
    }

    private SplittableBuffer create(String data) throws IOException {
        SplittableBuffer buffer = new SplittableBuffer();
        buffer.write(data.getBytes("utf8"));
        return buffer;
    }

    private void assertSplit(String expectedOutput, String input, int rowLength) throws IOException {
        SplittableBuffer buffer = new SplittableBuffer();
        buffer.write(input.getBytes("utf8"));
        Deque<SplittableBuffer> output = buffer.split(rowLength);
        assertEquals(expectedOutput, StringUtils.join(output, "\n"));
    }
}
