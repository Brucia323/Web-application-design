<%--
  Created by IntelliJ IDEA.
  User: ZZZCNY
  Date: 2021/9/29
  Time: 9:55
  To change this template use File | Settings | File Templates.
--%>
<%@page pageEncoding="UTF-8" %>
<html lang="zh-CN">
<head>
    <title>注册</title>
</head>
<body>
<h1>注册</h1>
<form method="post" action="EnrollServlet">
    用户名：<input type="text" name="username">
    密码：<input type="password" name="password">
    身份：<input type="checkbox" name="manager" value="true">管理员
    <button type="submit">注册</button>
</form>
<a href="index.jsp">
    <button>返回</button>
</a>
</body>
</html>
