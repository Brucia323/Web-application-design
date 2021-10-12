<%@ page import="java.io.RandomAccessFile" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.nio.charset.StandardCharsets" %><%--
  Created by IntelliJ IDEA.
  User: ZZZCNY
  Date: 2021/9/30
  Time: 8:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title><%
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 加载驱动程序
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo5", "root", "20010323"); // 连接数据库
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT title FROM huati WHERE id = '" + request.getParameter("id") + "'"); // 加载话题标题
            ResultSet resultSet = preparedStatement.executeQuery(); // 执行查询语句
            if (resultSet.next()) {
                out.println(resultSet.getString(1));
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    %></title>
    <link rel="stylesheet" href="styles/huati.css">
</head>
<body>
<%
    String huaTiId = request.getParameter("id"); // 话题ID
    try {
        Class.forName("com.mysql.cj.jdbc.Driver"); // 加载驱动程序
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    try {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo5", "root", "20010323"); // 连接数据库
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT huati.id, title, zan, huifu, top, jing, userid, time, name FROM huati, user WHERE huati.userid = user.id AND huati.id = '" + huaTiId + "'"); // 加载话题
        ResultSet resultSet = preparedStatement.executeQuery(); // 执行查询语句
        if (resultSet.next()) {
            out.println("<div class='huati'>");
            out.println("<h2>" + resultSet.getString(9) + "</h2>" + resultSet.getString(8)); // 用户名 时间
            if (resultSet.getString(6).equals("1")) {
                out.println("<h3 color='red' border=1 float=left>精</h3>"); // 加精标识
            }
            out.println("<h3 float=left>" + resultSet.getString(2) + "</h3>"); // 标题
            RandomAccessFile randomAccessFile = new RandomAccessFile("huati" + resultSet.getString(1) + ".txt", "r"); // 打开文件
            String huaTi = new String(randomAccessFile.readLine().getBytes(StandardCharsets.ISO_8859_1), "gbk"); // 读取文件
            randomAccessFile.close();
            out.println("<p>" + huaTi + "</p>"); // 话题
            out.println("<a href='DianZanServlet?id=" + resultSet.getString(1) + "&userid=" + request.getParameter("userid") + "&huifuid=0'><button>👍" + resultSet.getString(3) + "</button></a>"); // 点赞
            out.println("<a href='XiangXi.jsp?id=" + resultSet.getString(1) + "&userid=" + request.getParameter("userid") + "#huifu'><button>💬" + resultSet.getString(4) + "</button></a>"); // 评论 跳转
            out.println("</div>");
        }
        preparedStatement = connection.prepareStatement("SELECT huifu.id, zan, huatiid, zan, huifu, top, userid, time, huifuid, name FROM huifu, user WHERE huifu.userid = user.id AND huatiid = '" + huaTiId + "'"); // 加载评论
        resultSet = preparedStatement.executeQuery(); // 执行查询语句
        while (resultSet.next()) {
            out.println("<div class='huati' id='" + resultSet.getString(1) + "'>"); // ID用于跳转
            if (resultSet.getString(9).equals("0")) {
                // 回复话题
                out.println("<h2>" + resultSet.getString(10) + "</h2>" + resultSet.getString(8)); // 用户名 时间
            } else {
                // 回复话题下的其他回复
                PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT name FROM huifu, user WHERE huifu.userid = user.id AND huifu.id = '" + resultSet.getString(9) + "'"); // 查询被回复用户名
                ResultSet resultSet1 = preparedStatement1.executeQuery(); // 执行查询语句
                if (resultSet1.next()) {
                    out.println("<h2>" + resultSet.getString(10) + "回复" + resultSet1.getString(1) + "</h2>" + resultSet.getString(8)); // 用户B回复用户A 时间
                }
                resultSet1.close();
            }
            RandomAccessFile randomAccessFile = new RandomAccessFile("huifu" + resultSet.getString(1) + ".txt", "r"); // 打开文件
            String huiFu = new String(randomAccessFile.readLine().getBytes("ISO_8859_1"), "GBK"); // 读取内容
            randomAccessFile.close();
            out.println("<p>" + huiFu + "</p>"); // 内容
            out.println("<a href='DianZanServlet?id=" + huaTiId + "&userid=" + request.getParameter("userid") + "&huifuid=" + resultSet.getString(1) + "'><button>👍" + resultSet.getString(2) + "</button></a><br><br>"); // 点赞
            out.println("<form action='HuiFuServlet' method='get'>");
            out.println("<input type='text' name='userid' readonly hidden value='" + request.getParameter("userid") + "'>"); // 用户ID
            out.println("<input type='text' name='id' readonly hidden value='" + request.getParameter("id") + "'>"); // 话题ID
            out.println("<input type='text' name='huifuid' readonly hidden value='" + resultSet.getString(1) + "'>"); // 回复ID
            out.println("回复：<input type='text' name='huifupinglun'>");
            out.println("<button type='submit'>提交</button");
            out.println("</form></div>");
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
%>
<div class="huati">
    <form id="huifu" action="HuiFuServlet" method="get">
        <%
            out.println("<input type='text' name='userid' readonly hidden value='" + request.getParameter("userid") + "'>"); // 用户ID
            out.println("<input type='text' name='id' readonly hidden value='" + request.getParameter("id") + "'>"); // 话题ID
        %>
        回复：<input type="text" name="huifuhuati">
        <button type="submit">提交</button>
    </form>
</div>
</body>
</html>
