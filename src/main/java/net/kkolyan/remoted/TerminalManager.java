package net.kkolyan.remoted;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nplekhanov
 */
public class TerminalManager {

    private Map<String,Terminal> terminalMap = new HashMap<String, Terminal>();
    private boolean shutdown;

    private void checkShutdown() {
        if (shutdown) {
            throw new IllegalStateException("already shutdown");
        }
    }

    public synchronized Terminal getTerminal(String terminalId) {
        checkShutdown();

        Terminal terminal = terminalMap.get(terminalId);
        if (terminal == null) {
            terminal = new Terminal();
            terminalMap.put(terminalId, terminal);
        }
        return terminal;
    }

    public synchronized Map<String,? extends Terminal> getTerminals() {
        checkShutdown();

        return new HashMap<String, Terminal>(terminalMap);
    }

    public void closeTerminal(String terminalId) {
        Terminal terminal;
        synchronized (this) {
            checkShutdown();

            terminal = terminalMap.remove(terminalId);
        }
        if (terminal != null) {
            terminal.close();
        }
    }

    public synchronized void shutdown() {
        for (Terminal terminal: terminalMap.values()) {
            terminal.close();
        }

    }
}
