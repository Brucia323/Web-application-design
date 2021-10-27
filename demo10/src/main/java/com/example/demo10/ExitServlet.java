package com.example.demo10;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ExitServlet", value = "/ExitServlet")
public class ExitServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    
    }
}
