package com.example.demo13;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ReviseServlet", value = "/ReviseServlet")
public class ReviseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int id= Integer.parseInt(request.getParameter("id"));
        String name=request.getParameter("name");
        String sex=request.getParameter("sex");
        int age= Integer.parseInt(request.getParameter("age"));
        double weight= Double.parseDouble(request.getParameter("weight"));
        double hight= Double.parseDouble(request.getParameter("hight"));
        Student student=new Student(name,sex,age,weight,hight);
        try {
            student.revise(id);
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
