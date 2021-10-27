package com.example.demo10;

import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GetSessionServlet", value = "/GetSessionServlet")
public class GetSessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession httpSession = request.getSession();
        if (httpSession.getAttribute("id") == null) {
            out.print("Not logged in");
            return;
        }
        int id = (int) httpSession.getAttribute("id");
        String username = (String) httpSession.getAttribute("username");
        boolean administrator = (boolean) httpSession.getAttribute("administrator");
        User user = new User(username, administrator, id);
        Gson gson = new Gson();
        String json = gson.toJson(user);
        out.print(json);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    
    }
}
