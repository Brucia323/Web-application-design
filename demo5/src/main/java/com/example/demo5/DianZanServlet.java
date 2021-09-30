package com.example.demo5;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "DianZanServlet", value = "/DianZanServlet")
public class DianZanServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int userid = Integer.parseInt(request.getParameter("userid"));
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HuaTi", "root", "20010323");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM dianzan WHERE huatiid='" + id + "' AND userid='" + userid + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                preparedStatement = connection.prepareStatement("DELETE FROM dianzan WHERE userid='" + userid + "' AND huatiid='" + id + "'");
                preparedStatement.executeUpdate();
                preparedStatement = connection.prepareStatement("UPDATE huati SET zan=zan-1 WHERE id='" + id + "'");
                preparedStatement.executeUpdate();
            } else {
                preparedStatement = connection.prepareStatement("INSERT INTO dianzan (userid, huatiid) VALUES ('" + userid + "','" + id + "')");
                preparedStatement.executeUpdate();
                preparedStatement = connection.prepareStatement("UPDATE huati SET zan=zan+1 WHERE id='" + id + "'");
                preparedStatement.executeUpdate();
            }
            response.setHeader("refresh", "0;url=main.jsp?id=" + request.getParameter("userid") + "#" + request.getParameter("id") + "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
