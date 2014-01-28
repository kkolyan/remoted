<%@ page import="java.io.File" %>
<%@ page import="net.kkolyan.space.PathTraversing" %>
<%@ page import="net.kkolyan.space.PathNode" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title></title>
    <style>
        div {
            padding-left: 0pt;
        }

    </style>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.js"></script>
    <script>
        $(function() {
            $(".toggler").click(function(){
                $(this).siblings(".togglable").toggle();
            });
        });
    </script>
</head>
<body>
<%
    String path = request.getParameter("path");
    if (path != null) {
        PathNode file = PathTraversing.getTree(new File(path));
        %>
        <tags:path-node file="<%=file%>"/>
        <%
    }
    else {
        %>
        <form>
            <input name="path"/>
        </form>
        <%
    }
%>
</body>
</html>