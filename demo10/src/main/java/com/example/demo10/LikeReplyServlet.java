package com.example.demo10;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "LikeReplyServlet", value = "/LikeReplyServlet")
public class LikeReplyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        int userid = Integer.parseInt(request.getParameter("userid"));
        int replyid = Integer.parseInt(request.getParameter("replyid"));
        int topicid = Integer.parseInt(request.getParameter("topicid"));
        Reply reply = new Reply();
        try {
            out.print(reply.likes(userid, replyid, topicid));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
