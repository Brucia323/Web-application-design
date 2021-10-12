<%@ page import="java.sql.*" %>
<%@ page import="java.io.RandomAccessFile" %>
<%@ page import="java.nio.charset.StandardCharsets" %><%--
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
    <script src="scripts/delete.js"></script>
</head>
<body>
<nav>
    <%
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 加载驱动程序
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo5", "root", "20010323"); // 连接数据库
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT name, manager FROM user WHERE id = '" + request.getParameter("id") + "'"); // 加载用户名和身份信息
            ResultSet resultSet = preparedStatement.executeQuery(); // 执行查询语句
            if (resultSet.next()) {
                if (resultSet.getString(2).equals("1")) {
                    out.println("<a href='New.jsp?id=" + request.getParameter("id") + "'><button>新建话题</button></a>");
                }
                out.println("<div>");
                out.println("ID:" + request.getParameter("id"));
                out.println("用户名:" + resultSet.getString(1));
                out.println("<a href='index.jsp'><button>退出</button></a>");
                out.println("</div>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    %>
</nav>
<div>
    <%
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 加载驱动程序
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo5", "root", "20010323"); // 连接数据库
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT huati.id, title, zan, huifu, top, jing, userid, time, name FROM huati,user WHERE huati.userid = user.id ORDER BY top DESC, huati.id DESC"); // 加载话题并以倒序排列
            ResultSet resultSet = preparedStatement.executeQuery(); // 执行查询语句
            while (resultSet.next()) {
                out.println("<a href='XiangXi.jsp?id=" + resultSet.getString(1) + "&userid=" + request.getParameter("id") + "' target='_blank'><div class='huati' id='" + resultSet.getString(1) + "'>"); // 详情页超链接 用于定位的标识
                out.println("<h2>" + resultSet.getString(9) + "</h2>" + resultSet.getString(8)); // 用户名 时间
                if (resultSet.getString(6).equals("1")) {
                    out.println("<h3 class='jing'>精</h3>"); // 加精标识
                }
                out.println("<h3 float=left>" + resultSet.getString(2) + "</h3>"); // 标题
                RandomAccessFile randomAccessFile = new RandomAccessFile("huati" + resultSet.getString(1) + ".txt", "r"); // 打开文件
                String huaTi = new String(randomAccessFile.readLine().getBytes(StandardCharsets.ISO_8859_1), "GBK"); // 读取文件
                randomAccessFile.close();
                out.println("<p>" + huaTi + "</p>"); // 内容
                out.println("<a href='DianZanServlet?id=" + resultSet.getString(1) + "&userid=" + request.getParameter("id") + "'><button>👍" + resultSet.getString(3) + "</button></a>"); // 点赞按钮
                out.println("<a href='XiangXi.jsp?id=" + resultSet.getString(1) + "&userid=" + request.getParameter("id") + "#huifu' target='_blank'><button>💬" + resultSet.getString(4) + "</button></a>"); // 评论按钮，直接跳转到详情页评论框
                PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT manager FROM user WHERE id = '" + request.getParameter("id") + "'");
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                if (resultSet1.next()) {
                    if (resultSet1.getString(1).equals("1")) {
                        out.println("<div class='dian'><a href=''>⚫⚫⚫</a>");
                        out.println("<ul class='more'>");
                        if (resultSet.getString(5).equals("0")) {
                            out.println("<li><a href='TopServlet?id=" + resultSet.getString(1) + "&userid=" + request.getParameter("id") + "'>置顶</a></li>");
                        } else {
                            out.println("<li><a href='TopServlet?id=" + resultSet.getString(1) + "&userid=" + request.getParameter("id") + "'>取消置顶</a></li>");
                        }
                        if (resultSet.getString(6).equals("0")) {
                            out.println("<li><a href='JingServlet?id=" + resultSet.getString(1) + "&userid=" + request.getParameter("id") + "'>加精</a></li>");
                        }else {
                            out.println("<li><a href='JingServlet?id=" + resultSet.getString(1) + "&userid=" + request.getParameter("id") + "'>取消加精</a></li>");
                        }
                        out.println("<li><a href='edit.jsp?id=" + resultSet.getString(1) + "&userid=" + request.getParameter("id") + "'>编辑</a></li>");
                        out.println("<li onclick='shanchu("+resultSet.getString(1)+","+request.getParameter("id")+")')><a href=''>删除</a></li>");
                        out.println("</ul>");
                        out.println("</div>");
                    }
                }
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
