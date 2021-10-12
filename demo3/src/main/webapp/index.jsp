<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>登录</title>
</head>
<body>
<h1><%= "登录" %>
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
    <input type="submit" value="登录" name="button">
</form>
<a href="enroll.jsp">注册</a>
</body>
</html>