package com.example.demo4;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 商品管理
 *
 * @ZZZCNY
 */
@WebServlet(name = "shangpin", value = "/shangpin")
public class ShangPin extends HttpServlet {
    @Override
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
                        Connection connection = DriverManager
                                .getConnection("jdbc:mysql://localhost:3306/ShangPinGuanLi", "root", "20010323");
                        PreparedStatement preparedStatement = connection.prepareStatement(
                                "INSERT INTO ShangPin(name,price) VALUES ('" + name + "','" + price + "')");
                        int result = preparedStatement.executeUpdate();
                        PrintWriter out = response.getWriter();
                        if (result > 0) {
                            out.println("上架成功！3s后返回");
                            response.setHeader("refresh", "3;url=shangjia.html");
                        } else {
                            out.println("上架失败！5s后返回");
                            response.setHeader("refresh", "5;url=shangjia.html");
                        }
                        preparedStatement.close();
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (request.getParameter("button").equals("修改")) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    try {
                        Connection connection = DriverManager
                                .getConnection("jdbc:mysql://localhost:3306/ShangPinGuanLi", "root", "20010323");
                        PreparedStatement preparedStatement = connection.prepareStatement(
                                "SELECT * FROM ShangPin WHERE id ='" + request.getParameter("id") + "'");
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            PrintWriter out = response.getWriter();
                            out.println("<form action='shangpin?id=" + request.getParameter("id")
                                    + "&' method='get'>编号：<input readonly name='id' type='text' value='"
                                    + resultSet.getString(1) + "'>商品名称：<input name='name' type='text' value='"
                                    + resultSet.getString(2) + "'>商品价格：<input name='price' type='text' value='"
                                    + resultSet.getString(3)
                                    + "'><button type='submit' name='button' value='修改完成'>完成</button></form>");
                        }
                        resultSet.close();
                        preparedStatement.close();
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (request.getParameter("button").equals("修改完成")) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    try {
                        Connection connection = DriverManager
                                .getConnection("jdbc:mysql://localhost:3306/ShangPinGuanLi", "root", "20010323");
                        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE ShangPin SET name ='"
                                + request.getParameter("name") + "',price='" + request.getParameter("price")
                                + "' WHERE id ='" + request.getParameter("id") + "'");
                        int result = preparedStatement.executeUpdate();
                        PrintWriter out = response.getWriter();
                        if (result > 0) {
                            out.println("修改成功！3s后返回");
                            response.setHeader("refresh", "3;url=shangpin");
                        } else {
                            out.println("修改失败！5s后返回");
                            response.setHeader("refresh", "5;url=shangpin");
                        }
                        preparedStatement.close();
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (request.getParameter("button").equals("删除")) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    try {
                        Connection connection = DriverManager
                                .getConnection("jdbc:mysql://localhost:3306/ShangPinGuanLi", "root", "20010323");
                        PreparedStatement preparedStatement = connection.prepareStatement(
                                "DELETE FROM ShangPin WHERE id ='" + request.getParameter("id") + "'");
                        int result = preparedStatement.executeUpdate();
                        PrintWriter out = response.getWriter();
                        if (result > 0) {
                            out.println("删除成功！3s后返回");
                            response.setHeader("refresh", "3;url=shangpin");
                        } else {
                            out.println("删除失败！5s后返回");
                            response.setHeader("refresh", "5;url=shangpin");
                        }
                        preparedStatement.close();
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ShangPinGuanLi",
                            "root", "20010323");
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ShangPin");
                    ResultSet resultSet = preparedStatement.executeQuery();
                    PrintWriter out = response.getWriter();
                    out.println(
                            "<a href='shangjia.html'><button>上架商品</button></a><table border='1'><tr><td>编号</td><td>商品名称</td><td>商品价格</td><td>操作</td><td>操作</td></tr>");
                    while (resultSet.next()) {
                        out.println("<tr><td>" + resultSet.getString(1) + "</td><td>" + resultSet.getString(2)
                                + "</td><td>" + resultSet.getString(3) + "</td><td><a href='shangpin?id="
                                + resultSet.getString(1)
                                + "&button=修改'><button>修改</button></a></td><td><a href='shangpin?id="
                                + resultSet.getString(1) + "&button=删除'><button>删除</button></a></td></tr>");
                    }
                    out.println("</table>");
                    resultSet.close();
                    preparedStatement.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}