<%@ tag import="net.kkolyan.space.PathNode" %>
<%@ tag import="java.text.DecimalFormat" %>
<%@ tag language="java"  trimDirectiveWhitespaces="true"%>
<%@ attribute name="file" type="net.kkolyan.space.PathNode" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<div>
    <%
        String path = file.getFile().getAbsolutePath();
        path = path.substring(0, path.length() - file.getFile().getName().length());

        if (!file.getChildren().isEmpty()) {
            %>
            <span style="color: #CCC;"><%=path%></span><a href="javascript:" class="toggler"><%=file.getFile().getName()%></a>
            <%
        } else {
            %>
            <span style="color: #CCC;"><%=path+file.getFile().getName()%></span>
            <%
        }

    %>
    <b><%=DecimalFormat.getNumberInstance().format(file.getSize())%></b>
    <div class="togglable" style="display: none;">
        <%
        for (PathNode child: file.getChildren()) {
            %>
            <tags:path-node file="<%=child%>"/>
            <%
        }
        %>
    </div>
</div>