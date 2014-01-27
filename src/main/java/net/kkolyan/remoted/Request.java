package net.kkolyan.remoted;

import javax.servlet.http.HttpServletRequest;

/**
 * @author nplekhanov
 */
public class Request {
    private HttpServletRequest request;

    public Request(HttpServletRequest request) {
        this.request = request;
    }

    public String getTextParameter(String name, String defaultValue) {
        String value = request.getParameter(name);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public int getIntParameter(String name, int defaultValue) {
        return Integer.parseInt(getTextParameter(name, defaultValue+""));
    }
}
