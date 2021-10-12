package com.example.demo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录
 *
 * @ZZZCNY
 */
@WebServlet(name = "login", value = "/login")
public class Login extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        if (username == null || "".equals(username.trim()) || password == null || "".equals(password.trim())) {
            out.println("<h1>用户名或密码为空</h1>");
        } else if (username.equals("admin") && password.equals("123")) {
            out.println("<h1>登录成功</h1>");
        } else {
            out.println("<h1>用户名或密码错误</h1>");
        }
        out.println("</body></html>");
    }
}