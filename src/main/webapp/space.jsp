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
        function formatNumber(n) {
            if (!n) {
                return '0';
            }
            var s = n.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, "$1 ");
            return s.substring(0, s.length - 3);
        }
        $(function() {

            <%
                String path = request.getParameter("path");

                if (path != null) {
            %>
            document.body.innerHTML = '';
            $.get('space.json.jsp?path=<%=path.replace("\\","\\\\")%>', function(data, textStatus, jqXHR) {
                var s = renderNode(data, null);
                document.body.innerHTML = s;

                $(".toggler").click(function(){
                    $(this).siblings(".togglable").toggle();
                });
            }, 'json').fail(function(jqXHR, textStatus, errorThrown ) {
                alert(errorThrown);
            });
            <%
                }
            %>
        });

        var SEPARATOR = '<%=File.separatorChar == '\\' ? "\\\\" : "/"%>';

        function renderNode(node, pathTo) {
            var path;
            if (pathTo != null) {
                path = pathTo + SEPARATOR + node.name;
            } else {
                path = node.name;
            }
            var s = '<div>';
            if (node.children) {
                if (pathTo) {
                    s += '<span style="color: #CCC;">'+(pathTo + SEPARATOR) +'</span>';
                }
                s += '<a href="javascript:" class="toggler">'+node.name+'</a>';
            } else {
                s += '<span style="color: #CCC;">'+path+'</span>'
            }
            s += ' <b>'+formatNumber(node.size)+'</b>';
            if (node.children) {
                s += '<div class="togglable" style="display: none;">';
                s += node.children.map(function(child) {
                    return renderNode(child, path);
                }).join('');
                s += '</div>';
            }
            s += '</div>';
            return s;
        }
    </script>
</head>
<body>
<div id="rootPanel">
</div>
<form>
    <input name="path"/>
</form>
</body>
</html>