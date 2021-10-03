<%--
  Created by IntelliJ IDEA.
  User: ZZZCNY
  Date: 2021/9/29
  Time: 13:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>新建话题</title>
</head>
<body>
<form action="NewServlet" method="get">
    <%
        out.println("<input name=\"id\" type=\"text\" hidden readonly value=\"" + request.getParameter("id") + "\">");
    %>
    标题：<input type="text" name="title" required maxlength="255"><br>
    内容：<input type="text" name="content" required><br>
    <button type="submit">提交</button>
</form>
</body>
</html>
