<%--
  Created by IntelliJ IDEA.
  User: ZZZCNY
  Date: 2021/9/22
  Time: 14:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>注册</title>
</head>
<body>
<h1><%= "注册" %>
</h1>
<br/>
<form action="user" method="post">
    <label>
        用户名：
        <input type="text" name="name">
    </label>
    <br>
    <label>
        密码：
        <input type="password" name="password">
    </label>
    <br>
    <input type="submit" value="注册" name="button">
</form>
</body>
</html>
