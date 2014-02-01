<%@ tag import="net.kkolyan.space.PathNode" %>
<%@ tag import="java.text.DecimalFormat" %>
<%@ tag import="java.io.File" %>
<%@ tag language="java"  trimDirectiveWhitespaces="true"%>
<%@ attribute name="file" type="net.kkolyan.space.PathNode" %>
<%@ attribute name="pathTo" type="java.lang.String" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<div>
    <%

        String path;
        if (pathTo != null) {
            path = pathTo + File.separator + file.getName();
        } else {
            path = file.getName();
        }

        if (!file.getChildren().isEmpty()) {
            %>
            <span style="color: #CCC;"><%=pathTo == null ? "" : pathTo + File.separator %></span><a href="javascript:" class="toggler"><%=file.getName()%></a>
            <%
        } else {
            %>
            <span style="color: #CCC;"><%=path%></span>
            <%
        }

    %>
    <b><%=DecimalFormat.getNumberInstance().format(file.getSize())%></b>
    <div class="togglable" style="display: none;">
        <%
        for (PathNode child: file.getChildren()) {
            %>
            <tags:path-node file="<%=child%>" pathTo="<%=path%>"/>
            <%
        }
        %>
    </div>
</div>