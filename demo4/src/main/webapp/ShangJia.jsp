<%--
  Created by IntelliJ IDEA.
  User: ZZZCNY
  Date: 2021/9/27
  Time: 12:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上架商品</title>
</head>
<body>
<h1>上架商品</h1>
<form action="ShangPin" method="get">
    商品名称：<input name="name" type="text">
    商品价格：<input name="price" type="text">
    <button type="submit" name="button" value="上架">上架</button>
</form>
<a href="index.jsp"><button>返回</button></a>
</body>
</html>
