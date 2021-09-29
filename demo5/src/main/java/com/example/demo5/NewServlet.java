package com.example.demo5;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "NewServlet", value = "/NewServlet")
public class NewServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        New newHuaTi = new New();
        newHuaTi.setUserId(Integer.parseInt(request.getParameter("id")));
        newHuaTi.setTitle(request.getParameter("title"));
        newHuaTi.setContent(request.getParameter("content"));
        if (newHuaTi.write()) {
            PrintWriter out = response.getWriter();
            out.println("提交成功！3s后返回...");
            response.setHeader("refresh", "3;url=main.jsp?id=" + request.getParameter("id"));
        } else {
            PrintWriter out = response.getWriter();
            out.println("提交失败！5s后返回...");
            response.setHeader("refresh", "5;url=New.jsp?id=" + request.getParameter("id"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
