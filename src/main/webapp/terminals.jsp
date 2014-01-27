<%@ page import="net.kkolyan.remoted.TerminalManager" %>
<%@ page import="net.kkolyan.remoted.Terminal" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    TerminalManager terminalManager = (TerminalManager) application.getAttribute(TerminalManager.class.getName());

    String terminalId = request.getParameter("terminal");
    String action = request.getParameter("action");
    if ("close".equals(action)) {
        terminalManager.closeTerminal(terminalId);
        response.sendRedirect(request.getHeader("referer"));
        return;
    }
%>
<html>
<head>
    <title></title>
    <link href="remoted.css" rel="stylesheet"/>
</head>
<body>
<%
    for (Map.Entry<String, ? extends Terminal> terminal: terminalManager.getTerminals().entrySet()) {
        %>
        <p>
            <b><%=terminal.getKey()%></b>
            <%
            if (terminal.getKey().equals(session.getId())) {
                %><a href="terminal.jsp">Go to</a><%
            } else {
                %><a href="terminal.jsp?terminal=<%=terminal.getKey()%>">Go to</a><%
            }
            %>
            <a href="terminals.jsp?action=close&terminal=<%=terminal.getKey()%>">Close</a>
            <%if (terminal.getKey().equals(session.getId())) {%> (Your Default Terminal)<%}%>
        </p>
        <%
    }
%>
</body>
</html>