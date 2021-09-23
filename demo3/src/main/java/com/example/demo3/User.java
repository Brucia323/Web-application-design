/*create table users
(
	id int,
	name nvarchar(40) not null,
	password varchar(20) not null
);

create unique index users_id_uindex
	on users (id);

create unique index users_name_uindex
	on users (name);

alter table users
	add constraint users_pk
		primary key (id);

alter table users modify id int auto_increment;

*/
package com.example.demo3;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "user", value = "/user")
public class User extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String password = request.getParameter("password");
        PrintWriter out = response.getWriter();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("驱动程序正常！");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "root", "20010323");
            System.out.println("数据库连接正常！");
            PreparedStatement preparedStatement;
            ResultSet resultSet = null;
            if (request.getParameter("button").equals("登录")) {
                preparedStatement = connection.prepareStatement("select * from users where name=? and password=?");
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();
                out.println("<html><body>");
                if (resultSet.next()) {
                    out.println("用户登录成功！");
                } else {
                    out.println("用户登录失败！");
                }
            } else {
                preparedStatement = connection.prepareStatement("insert into users(name,password) values (" + name + "," + password + ")");
                preparedStatement.executeUpdate();
                out.println("<html><body>");
                out.println("注册成功！3s后返回登录界面");
                response.setHeader("refresh", "3;url=index.jsp");
            }
            out.println("</body></html>");
            assert resultSet != null;
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            if (request.getParameter("button").equals("注册")) {
                out.println("<html><body>");
                out.println("注册失败");
                out.println("</body></html>");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }

    public void destroy() {
    }
}