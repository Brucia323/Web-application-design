package com.example.demo6;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo6", "root",
                    "20010323");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE name = '"
                    + request.getParameter("username") + "' AND password = '" + request.getParameter("password") + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                out.println("欢迎您");
            } else {
                out.println("密码错误");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
