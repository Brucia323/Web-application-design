package com.example.demo10;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "ReplyServlet", value = "/ReplyServlet")
public class ReplyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        int topicid = Integer.parseInt(request.getParameter("topicid"));
        int userid = Integer.parseInt(request.getParameter("userid"));
        String reply =request.getParameter("reply");
        int replyid= Integer.parseInt(request.getParameter("replyid"));
        Reply reply1=new Reply(userid,topicid,reply,replyid);
        try {
            out.print(reply1.reply());
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
