package com.example.demo5;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "new", value = "/new")
public class NewServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        New newHuaTi = new New();
        newHuaTi.setUserId(Integer.parseInt(request.getParameter("id"))); // 传递用户ID
        newHuaTi.setTitle(request.getParameter("title")); // 传递标题
        newHuaTi.setContent(request.getParameter("content")); // 传递内容
        if (newHuaTi.write()) {
            PrintWriter out = response.getWriter();
            out.println("提交成功！3s后返回...");
            response.setHeader("refresh", "3;url=main.jsp?id=" + request.getParameter("id")); // 跳转回主页
        } else {
            PrintWriter out = response.getWriter();
            out.println("提交失败！5s后返回...");
            response.setHeader("refresh", "5;url=new.jsp?id=" + request.getParameter("id")); // 跳转回新增话题页
        }
    }
}
