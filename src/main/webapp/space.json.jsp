<%@ page import="org.codehaus.jackson.map.ObjectMapper"%>
<%@ page import="org.codehaus.jackson.map.annotate.JsonSerialize"%>
<%@ page import="net.kkolyan.space.PathNode"%>
<%@ page import="net.kkolyan.space.PathTraversing"%>
<%@ page import="java.io.File"%><%@ page import="java.util.UUID"%>
        <%@ page contentType="application/json" language="java" trimDirectiveWhitespaces="true" %>
<%
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationConfig(mapper.getSerializationConfig()
//                .with(SerializationConfig.Feature.INDENT_OUTPUT)
                .withSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT)
        );
        PathNode node = PathTraversing.getTree(new File(request.getParameter("path")));
        mapper.writeValue(response.getOutputStream(), node);
%>