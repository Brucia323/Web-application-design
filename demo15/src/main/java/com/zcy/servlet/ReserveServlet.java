package com.zcy.servlet;

import com.zcy.dao.MySQL;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet(name = "ReserveServlet", value = "/ReserveServlet")
public class ReserveServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        MySQL mySQL = new MySQL();
        try {
            mySQL.reserve(bookId);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        response.setHeader("refresh","0;url=PageServlet?currentPage="+request.getParameter("currentPage"));
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    
    }
}
