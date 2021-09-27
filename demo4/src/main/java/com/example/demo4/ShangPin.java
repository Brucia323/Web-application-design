/*create table ShangPin
(
	id int,
	name nvarchar(40) not null,
	price int not null
);

create unique index ShangPin_id_uindex
	on ShangPin (id);

alter table ShangPin
	add constraint ShangPin_pk
		primary key (id);

alter table ShangPin modify id int auto_increment;

*/
package com.example.demo4;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "商品管理", value = "/ShangPin")
public class ShangPin extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        if (request.getParameter("button") != null) {
            if (request.getParameter("button").equals("上架")) {
                String name = request.getParameter("name");
                String price = request.getParameter("price");
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ShangPinGuanLi", "root", "20010323");
                        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ShangPin(name,price) VALUES (" + name + "," + price + ")");
                        int result = preparedStatement.executeUpdate();
                        PrintWriter out = response.getWriter();
                        if (result > 0) {
                            out.println("上架成功！3s后返回");
                            response.setHeader("refresh", "3;url=ShangJia.jsp");
                        } else {
                            out.println("上架失败！5s后返回");
                            response.setHeader("refresh", "5;url=ShangJia.jsp");
                        }
                        preparedStatement.close();
                        connection.close();
                    } catch (SQLException e) {
                        System.out.println("数据库连接失败！");
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println("驱动程序加载失败！");
                }
            } else if (request.getParameter("button").equals("修改")) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ShangPinGuanLi", "root", "20010323");
                        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ShangPin WHERE id ='" + request.getParameter("id") + "'");
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            PrintWriter out = response.getWriter();
                            out.println("<form action=\"ShangPin?id=" + request.getParameter("id") + "&\" method=\"get\">\n" +
                                    "    编号：<input readonly name='id' type='text' value='" + resultSet.getString(1) + "'>\n" +
                                    "    商品名称：<input name=\"name\" type=\"text\" value='" + resultSet.getString(2) + "'>\n" +
                                    "    商品价格：<input name=\"price\" type=\"text\" value='" + resultSet.getString(3) + "'>\n" +
                                    "    <button type=\"submit\" name=\"button\" value=\"修改完成\">完成</button>\n" +
                                    "</form>");
                        }
                        resultSet.close();
                        preparedStatement.close();
                        connection.close();
                    } catch (SQLException e) {
                        System.out.println("数据库连接失败");
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println("驱动程序加载失败！");
                }
            } else if (request.getParameter("button").equals("修改完成")) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ShangPinGuanLi", "root", "20010323");
                        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE ShangPin SET name ='" + request.getParameter("name") + "',price='" + request.getParameter("price") + "' WHERE id ='" + request.getParameter("id") + "'");
                        int result = preparedStatement.executeUpdate();
                        PrintWriter out = response.getWriter();
                        if (result > 0) {
                            out.println("修改成功！3s后返回");
                            response.setHeader("refresh", "3;url=ShangPin");
                        } else {
                            out.println("修改失败！5s后返回");
                            response.setHeader("refresh", "5;url=ShangPin");
                        }
                        preparedStatement.close();
                        connection.close();
                    } catch (SQLException e) {
                        System.out.println("数据库连接失败！");
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println("驱动程序加载失败！");
                }
            } else if (request.getParameter("button").equals("删除")) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ShangPinGuanLi", "root", "20010323");
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM ShangPin WHERE id ='" + request.getParameter("id") + "'");
                        int result = preparedStatement.executeUpdate();
                        PrintWriter out = response.getWriter();
                        if (result > 0) {
                            out.println("删除成功！3s后返回");
                            response.setHeader("refresh", "3;url=ShangPin");
                        } else {
                            out.println("删除失败！5s后返回");
                            response.setHeader("refresh", "5;url=ShangPin");
                        }
                        preparedStatement.close();
                        connection.close();
                    } catch (SQLException e) {
                        System.out.println("数据库连接失败！");
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println("驱动程序加载失败！");
                }
            }
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ShangPinGuanLi", "root", "20010323");
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ShangPin");
                    ResultSet resultSet = preparedStatement.executeQuery();
                    PrintWriter out = response.getWriter();
                    out.println("<a href=\"ShangJia.jsp\"><button>上架商品</button></a>");
                    out.println("<table border=\"1\">");
                    out.println("    <tr>\n" +
                            "        <td>编号</td>\n" +
                            "        <td>商品名称</td>\n" +
                            "        <td>商品价格</td>\n" +
                            "        <td>操作</td>\n" +
                            "        <td>操作</td>\n" +
                            "    </tr>\n");
                    while (resultSet.next()) {
                        out.println("    <tr>\n" +
                                "        <td>" + resultSet.getString(1) + "</td>\n" +
                                "        <td>" + resultSet.getString(2) + "</td>\n" +
                                "        <td>" + resultSet.getString(3) + "</td>\n" +
                                "        <td><a href='ShangPin?id=" + resultSet.getString(1) + "&button=修改'><button>修改</button></a></td>\n" +
                                "        <td><a href='ShangPin?id=" + resultSet.getString(1) + "&button=删除'><button>删除</button></a></td>\n" +
                                "    </tr>\n");
                    }
                    out.println("</table>");
                    resultSet.close();
                    preparedStatement.close();
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("数据库连接失败！");
                    System.out.println(e);
                }
            } catch (ClassNotFoundException e) {
                System.out.println("驱动程序加载失败！");
            }
        }
    }

    public void destroy() {
    }
}