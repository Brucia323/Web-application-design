<%@ page import="java.sql.*" %>
<%@ page import="java.io.RandomAccessFile" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
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
    <link rel="stylesheet" href="styles/huati.css">
</head>
<body>
<a href="Login.jsp">
    <button>登录</button>
</a>
<a href="Enroll.html">
    <button>注册</button>
</a>
<%
    try {
        Class.forName("com.mysql.cj.jdbc.Driver"); // 加载驱动程序
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    try {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HuaTi", "root", "20010323"); // 连接数据库
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT huati.id, title, zan, huifu, top, jing, userid, time, name FROM huati, user WHERE huati.userid = user.id ORDER BY huati.id, top DESC"); // 加载话题并以倒序排列
        ResultSet resultSet = preparedStatement.executeQuery(); // 执行查询语句
        while (resultSet.next()) {
            out.println("<div class='huati'>");
            out.println("<h2>" + resultSet.getString(9) + "</h2>" + resultSet.getString(8)); // 用户名 时间
            out.println("<h3>" + resultSet.getString(2) + "</h3>"); // 标题
            RandomAccessFile randomAccessFile = new RandomAccessFile("huati" + resultSet.getString(1) + ".txt", "r"); // 打开内容所在文件
            String huaTi = new String(randomAccessFile.readLine().getBytes(StandardCharsets.ISO_8859_1), "gbk"); // 读取话题内容
            randomAccessFile.close();
            out.println("<p>" + huaTi + "</p>"); // 内容
            out.println("👍" + resultSet.getString(3)); // 点赞
            out.println("💬" + resultSet.getString(4)); // 评论
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
