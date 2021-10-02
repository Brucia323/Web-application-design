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
    用户名：<input type="text" name="username" required maxlength="40"><br>
    密码：<input type="password" name="password" required maxlength="20"><br>
    管理员身份代码：<input type="password" name="manager"><br>
    <button type="submit">注册</button>
</form>
<a href="index.jsp">
    <button>返回</button>
</a>
</body>
</html>
