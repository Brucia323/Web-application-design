<%@ page import="java.io.RandomAccessFile" %>
<%@ page import="java.sql.*" %><%--
  Created by IntelliJ IDEA.
  User: ZZZCNY
  Date: 2021/9/30
  Time: 8:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HuaTi", "root", "20010323");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT title FROM huati WHERE id='"+request.getParameter("id")+"'");
            ResultSet resultSet = preparedStatement.executeQuery();
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
</head>
<body>

</body>
</html>
