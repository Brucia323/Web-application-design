package com.zcy.controller;

import com.zcy.service.Shop;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CreateServlet", value = "/CreateServlet")
public class CreateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        PrintWriter out=response.getWriter();
        out.print("success");
        String wareIdList = request.getParameter("wareIdList");
        String wareNumList = request.getParameter("wareNumList");
        Shop shop=new Shop();
        shop.order(wareIdList,wareNumList);
    }
}
