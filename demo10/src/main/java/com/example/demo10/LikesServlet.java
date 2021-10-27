package com.example.demo10;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "LikesServlet", value = "/LikesServlet")
public class LikesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        int userid = Integer.parseInt(request.getParameter("userid"));
        int topicid = Integer.parseInt(request.getParameter("topicid"));
        Topics topics = new Topics();
        try {
            out.print(topics.likes(userid, topicid));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    
    }
}
