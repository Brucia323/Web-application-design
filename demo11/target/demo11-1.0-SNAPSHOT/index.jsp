<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<table>
    <%
        for (int i = 1; i <= 9; i++) {
            out.print("<tr>");
            for (int j = 1; j <= i; j++) {
                out.print("<td style=\"border: 3px solid white\">" + i + "*" + j + "=" + i * j + "</td>");
            }
            out.print("</tr>");
        }
    %>
</table>
</body>
</html>
