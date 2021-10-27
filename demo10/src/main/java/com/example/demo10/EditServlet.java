package com.example.demo10;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EditServlet", value = "/EditServlet")
public class EditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        int topicid = Integer.parseInt(request.getParameter("topicid"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        Topics topics = new Topics();
        topics.edit(topicid, title, content);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    
    }
}
