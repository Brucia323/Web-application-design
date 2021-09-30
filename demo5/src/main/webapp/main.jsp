<%@ page import="java.sql.*" %>
<%@ page import="java.io.RandomAccessFile" %><%--
  Created by IntelliJ IDEA.
  User: ZZZCNY
  Date: 2021/9/29
  Time: 12:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>首页</title>
    <link rel="stylesheet" href="styles/huati.css">
</head>
<body>
<%
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    try {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HuaTi", "root", "20010323");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name,manager FROM user WHERE id='" + request.getParameter("id") + "'");
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            out.println("ID:" + request.getParameter("id"));
            out.println("用户名:" + resultSet.getString(1));
            out.println("<a href='index.jsp'><button>退出</button></a>");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
%>
<div>
    <%
        out.println("<a href='New.jsp?id=" + request.getParameter("id") + "'><button>新建话题</button></a>");
    %>
    <div>
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
                    out.println("<a href='XiangXi.jsp?id=" + resultSet.getString(1) + "&userid=" + request.getParameter("id") + "' target='_blank'><div class='huati' id='" + resultSet.getString(1) + "'>");
                    out.println("<h2>" + resultSet.getString(9) + "</h2>" + resultSet.getString(8));
                    if (resultSet.getString(6).equals("1")) {
                        out.println("<h3 color='red' border=1 float=left>精</h3>");
                    }
                    out.println("<h3 float=left>" + resultSet.getString(2) + "</h3>");
                    RandomAccessFile randomAccessFile = new RandomAccessFile("huati" + resultSet.getString(1) + ".txt", "r");
                    String huaTi = new String(randomAccessFile.readLine().getBytes("ISO-8859-1"), "gbk");
                    randomAccessFile.close();
                    out.println("<p>" + huaTi + "</p>");
                    out.println("<a href='DianZanServlet?id=" + resultSet.getString(1) + "&userid=" + request.getParameter("id") + "'><button>👍" + resultSet.getString(3) + "</button></a>");
                    out.println("<a href='XiangXi.jsp?id=" + resultSet.getString(1) + "&userid=" + request.getParameter("id") + "' target='_blank'><button>💬" + resultSet.getString(4) + "</button></a>");
                    out.println("</div></a>");
                }
                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        %>
    </div>
</div>
</body>
</html>
