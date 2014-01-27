package net.kkolyan.remoted;

import org.apache.commons.lang3.SystemUtils;

import javax.script.ScriptEngineManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author nplekhanov
 */
public class Terminal {
    private final Process process;
    private final Deque<Block> log = new ArrayDeque<Block>();
    private final Thread outReader;
    private final Thread errReader;

    public Terminal() {
        try {
            String shell;
            if (SystemUtils.IS_OS_WINDOWS) {
                shell = "cmd";
            }
            else if (SystemUtils.IS_OS_UNIX) {
                shell = "bash";
            }
            else throw new IllegalStateException("can't find shell for current OS");
            process = new ProcessBuilder(shell).start();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        outReader = startStreamReading(Stream.OUT, process.getInputStream());
        errReader = startStreamReading(Stream.ERR, process.getErrorStream());
    }

    public Deque<Block> getWindow(int width, int height, int offset) {
        synchronized (log) {
            return WindowMaker.getWindow(width, height, offset, log);
        }
    }

    public void execute(String line) {
        try {
            byte[] bytes = (line+ SystemUtils.LINE_SEPARATOR).getBytes(getEncoding());
            synchronized (log) {
                if (log.isEmpty() || log.getLast().getStream() != Stream.IN) {
                    log.addLast(new Block(Stream.IN));
                }
                log.getLast().write(bytes, 0, bytes.length);
            }
            synchronized (process) {
                process.getOutputStream().write(bytes);
                process.getOutputStream().flush();
            }
            
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Thread startStreamReading(Stream streamType, InputStream stream) {
        Thread thread = new Thread(new StreamReading(streamType, stream));
        thread.setDaemon(true);
        thread.start();
        return thread;
    }

    public String getEncoding() {
        return "cp866";
    }

    public void close() {
        process.destroy();
    }

    public class StreamReading implements Runnable {
        private final Stream streamType;
        private final InputStream stream;

        public StreamReading(Stream streamType, InputStream stream) {
            this.streamType = streamType;
            this.stream = stream;
        }

        @Override
        public void run() {
            try {
                byte[] buf = new byte[64*1024];
                while (true) {
                    int n = stream.read(buf);
                    if (n == -1) {
                        break;
                    }
                    synchronized (log) {
                        if (log.isEmpty() || log.getLast().getStream() != streamType) {
                            log.addLast(new Block(streamType));
                        }
                        log.getLast().write(buf, 0, n);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
