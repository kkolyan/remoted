package net.kkolyan.remoted;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
* @author nplekhanov
*/
public class Block {
    private Deque<SplittableBuffer> lines = new ArrayDeque<SplittableBuffer>();
    private Stream stream;

    public Block(Stream stream) {
        this.stream = stream;
    }

    public void write(byte[] data, int offset, int length) {
        for (int i = offset; i < offset + length; i ++) {
            if (data[i] != '\n') {
                if (lines.isEmpty()) {
                    lines.addLast(new SplittableBuffer());
                }
                lines.getLast().write(data[i]);
            } else {
                lines.addLast(new SplittableBuffer());
            }
        }
        if (lines.getLast().size() == 0) {
            lines.removeLast();
        }
    }

    public int getRows() {
        return lines.size();
    }

    public Stream getStream() {
        return stream;
    }

    public Block getTail(int limit, int width) {
        Block sub = new Block(stream);
        Iterator<SplittableBuffer> lines = this.lines.descendingIterator();
        while (lines.hasNext() && sub.lines.size() < limit) {
            Iterator<SplittableBuffer> subLines = lines.next().split(width).descendingIterator();
            while (subLines.hasNext() && sub.lines.size() < limit) {
                SplittableBuffer line = subLines.next();
                sub.lines.addFirst(line);
            }
        }
        return sub;
    }

    public Block getWithoutTail(int cutOut) {
        if (cutOut <= 0) {
            return this;
        }
        Block sub = new Block(stream);
        Iterator<SplittableBuffer> lines = this.lines.descendingIterator();
        int counter = 0;
        while (lines.hasNext()) {
            SplittableBuffer line = lines.next();
            counter ++;
            if (counter > cutOut) {
                sub.lines.addFirst(line);
            }
        }
        return sub;
    }

    public String getContent(String encoding) {
        StringBuilder s = new StringBuilder();
        for (SplittableBuffer line: lines) {
            try {
                s.append(line.toString(encoding)).append(SystemUtils.LINE_SEPARATOR);
            } catch (UnsupportedEncodingException e) {
                throw new IllegalStateException(e);
            }
        }
        return s.toString();
    }

    @Override
    public String toString() {
        return "Block{" +
                "lines=" + lines +
                ", stream=" + stream +
                '}';
    }
}
