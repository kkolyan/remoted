package net.kkolyan.remoted;

import java.io.ByteArrayOutputStream;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;

/**
 * @author nplekhanov
 */
public class SplittableBuffer extends ByteArrayOutputStream {

    private void rewind(int bytes) {
        if (bytes > count) {
            throw new IllegalStateException();
        }
        count -= bytes;
    }
    public synchronized Deque<SplittableBuffer> split(int rowLength) {
        if (rowLength >= count) {
            return new ArrayDeque<SplittableBuffer>(Collections.singletonList(this));
        }
        Deque<SplittableBuffer> chunks = new ArrayDeque<SplittableBuffer>();
        chunks.addLast(new SplittableBuffer());

        for (int i = 0; i < count; i ++) {
            if (buf[i] == '\b') {
                if (chunks.getLast().size() > 0) {
                    chunks.getLast().rewind(1);
                }
                continue;
            }
            if (buf[i] == '\n') {
                chunks.addLast(new SplittableBuffer());
                continue;
            }

            chunks.getLast().write(buf[i]);

            if (chunks.getLast().size() == rowLength) {
                chunks.addLast(new SplittableBuffer());
            }
        }
        if (chunks.getLast().size() == 0) {
            chunks.removeLast();
        }
        return chunks;
    }

    public SplittableBuffer getRowsRange(int offset, int limit) {
        int row = 0;
        int minI = count;
        int maxI = 0;
        for (int i = count - 1; i >= 0; i --) {
            if (buf[i] == '\n') {
                row ++;
            }
            if (row < offset) {
                continue;
            }
            if (row > offset + limit) {
                break;
            }
            if (buf[i] == '\n') {
                minI = Math.min(minI, i);
                maxI = Math.max(maxI, i);
            }
        }
        SplittableBuffer slice = new SplittableBuffer();
        slice.write(buf, minI+1, maxI - minI);
        return slice;
    }

    public synchronized void stripTailingReturnCarriage() {
        if (buf[count - 1] == '\r') {
            rewind(1);
        }
    }
}
