<%@page pageEncoding="UTF-8" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>登录</title>
</head>
<body>
<h1>登录</h1>
<form method="post" action="LoginServlet">
    用户名：<input type="text" name="username" required maxlength="40"><br>
    密码：<input type="password" name="password" required maxlength="20"><br>
    <button type="submit">登录</button>
</form>
<a href="Enroll.jsp">
    <button>注册</button>
</a>
</body>
</html>