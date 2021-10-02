package com.example.demo5;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        User user = new User(req.getParameter("username"), req.getParameter("password")); // 传递用户名和密码
        if (user.login()) {
            out.println("登录成功！3s后跳转...");
            resp.setHeader("refresh", "3;url=main.jsp?id=" + user.getId());
        } else {
            out.println("登录失败！5s后返回.....");
            resp.setHeader("refresh", "5;url=Login.jsp");
        }
    }
}