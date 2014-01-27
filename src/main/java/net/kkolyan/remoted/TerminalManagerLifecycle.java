package net.kkolyan.remoted;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author nplekhanov
 */
public class TerminalManagerLifecycle implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        TerminalManager terminalManager = new TerminalManager();
        sce.getServletContext().setAttribute(TerminalManager.class.getName(), terminalManager);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        TerminalManager terminalManager = (TerminalManager) sce.getServletContext()
                .getAttribute(TerminalManager.class.getName());

        terminalManager.shutdown();
    }
}
