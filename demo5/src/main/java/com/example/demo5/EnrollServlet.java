package com.example.demo5;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "EnrollServlet", value = "/EnrollServlet")
public class EnrollServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        int isManager;
        if (request.getParameter("manager").equals("123")) {
            isManager = 1; // 是管理员
        } else {
            isManager = 0; // 不是管理员
        }
        User user = new User(request.getParameter("username"), request.getParameter("password")); // 传递用户名和密码
        user.setManager(isManager); // 传递管理员身份标识
        if (user.enroll()) {
            out.println("注册成功！3s后跳转...");
            response.setHeader("refresh", "3;url=Login.jsp");
        } else {
            out.println("注册失败！5s后返回.....");
            response.setHeader("refresh", "5;url=Enroll.jsp");
        }
    }
}
