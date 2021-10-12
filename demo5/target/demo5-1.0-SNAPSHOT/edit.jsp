<%@ page import="com.mysql.cj.jdbc.Driver" %>
    <%@ page import="java.sql.*" %>
        <%@ page import="java.io.RandomAccessFile" %>
            <%@ page import="java.nio.charset.StandardCharsets" %>
                <%-- Created by IntelliJ IDEA. User: ZZZCNY Date: 2021/10/4 Time: 11:15 To change this template use File
                    | Settings | File Templates. --%>
                    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
                        <html>

                        <head>
                            <title>编辑</title>
                        </head>

                        <body>
                            <form method="get" action="EditServlet">
                                <% out.println("<input name='userid' type='text' hidden readonly
                                    value='" + request.getParameter("userid") + "'>");
                                    out.println("<input name='id' type='text' hidden readonly
                                        value='" + request.getParameter("id") + "'>");
                                    %>
                                    <% try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch (ClassNotFoundException
                                        e) { e.printStackTrace(); } try { Connection
                                        connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/HuaTi", "root"
                                        , "20010323" ); PreparedStatement
                                        preparedStatement=connection.prepareStatement("SELECT title FROM huati WHERE
                                        id='" + request.getParameter("id") + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                out.println(" 标题：<input type='text' name='title' value='" + resultSet.getString(1) + "' required
                                        maxlength='255'><br>");
                                        }
                                        resultSet.close();
                                        preparedStatement.close();
                                        connection.close();
                                        } catch (SQLException e) {
                                        e.printStackTrace();
                                        }
                                        %>
                                        <% RandomAccessFile randomAccessFile=new RandomAccessFile("huati" +
                                            request.getParameter("id") + ".txt" , "r" ); String content=new
                                            String(randomAccessFile.readLine().getBytes(StandardCharsets.ISO_8859_1), "GBK"
                                            ); randomAccessFile.close(); out.println("内容：<input type='text'
                                            name='content' value='" + content + "' required><br>");
                                            %>
                                            <button type="submit">提交</button>
                            </form>
                        </body>

                        </html>