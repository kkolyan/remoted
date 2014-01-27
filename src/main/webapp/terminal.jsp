<%@ page import="net.kkolyan.remoted.Request"%><%@ page import="net.kkolyan.remoted.TerminalManager"%><%@ page import="net.kkolyan.remoted.Terminal"%><%@ page import="net.kkolyan.remoted.Block"%><%@ page import="java.util.Deque"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%
    Request req = new Request(request);

    String terminalId = req.getTextParameter("terminal", request.getSession().getId());

    TerminalManager terminalManager = (TerminalManager) application.getAttribute(TerminalManager.class.getName());

    if (request.getMethod().equals("POST")) {
        String command = request.getParameter("command");
        Terminal terminal = terminalManager.getTerminal(terminalId);
        terminal.execute(command);

        response.sendRedirect(request.getHeader("referer"));
        return;
    }
%>
<html>
<head>
    <link href="remoted.css" rel="stylesheet"/>
    <script>
        function init() {
            document.getElementsByName("command")[0].focus();
        }
    </script>
</head>
<body onload="init()">
<p>
    <a href="terminals.jsp">Terminals</a>
</p>
<%

    int width = req.getIntParameter("width", 80);
    int height = req.getIntParameter("height", 24);
    int offset = req.getIntParameter("offset", 0);


    Terminal terminal = terminalManager.getTerminal(terminalId);

    if (terminal == null) {
        response.sendError(404);
        return;
    }

//    response.setContentType("text/html;charset="+terminal.getEncoding());

    Deque<Block> window = terminal.getWindow(width, height, offset);
    for (Block block: window) {
%>
        <pre class="<%=block.getStream().name().toLowerCase()%>"><%=block.getContent(terminal.getEncoding())
                .replace("<","&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
        %></pre>
<%
    }

%>
<form method="post">
    <input name="command" type="text" value=""/>
</form>
</body>
</html>