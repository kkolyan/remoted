package net.kkolyan.remoted;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * @author nplekhanov
 */
public class WindowMaker {
    public static Deque<Block> getWindow(int width, int height, int offset, Deque<Block> raw) {
        Deque<Block> window = new ArrayDeque<Block>();
        Iterator<Block> it = raw.descendingIterator();
        int rowCounter = 0;
        while (it.hasNext() && rowCounter < offset + height) {
            Block block = it.next().getTail(offset + height - rowCounter, width);
            if (block.getRows() + rowCounter <= offset) {
                rowCounter += block.getRows();
                continue;
            }

            Block head = block.getWithoutTail(offset - rowCounter);
            rowCounter += block.getRows();
            window.addFirst(head);
        }
        return window;
    }
}
