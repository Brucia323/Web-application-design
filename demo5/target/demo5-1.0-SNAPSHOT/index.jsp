<%@ page import="java.sql.*" %>
<%@ page import="java.io.RandomAccessFile" %>
<%--
  Created by IntelliJ IDEA.
  User: ZZZCNY
  Date: 2021/9/29
  Time: 10:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
<a href="Login.jsp">
    <button>登录</button>
</a>
<a href="Enroll.jsp">
    <button>注册</button>
</a>
<%
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    try {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HuaTi", "root", "20010323");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT huati.id,title,zan,huifu,top,jing,userid,time,name FROM huati,user WHERE huati.userid = user.id");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            out.println("<div>");
            out.println("<h2>" + resultSet.getString(9) + "</h2>" + resultSet.getString(8));
            out.println("<h3>" + resultSet.getString(2) + "</h3>");
            RandomAccessFile randomAccessFile = new RandomAccessFile("huati" + resultSet.getString(1) + ".txt", "r");
            String huaTi = new String(randomAccessFile.readLine().getBytes("ISO-8859-1"), "gbk");
            randomAccessFile.close();
            out.println("<p>" + huaTi + "</p>");
            out.println("👍" + resultSet.getString(3));
            out.println("💬" + resultSet.getString(4));
            out.println("</div>");
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
%>
</body>
</html>
