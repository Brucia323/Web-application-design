package com.zcy.servlet;

import com.zcy.entity.PageBean;
import com.zcy.service.SearchService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "SearchServlet", value = "/SearchServlet")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String search = request.getParameter("search");
        int page = 1;
        int pageSize = 20;
        String currentPage = request.getParameter("currentPage");
        if (currentPage != null) {
            page = Integer.parseInt(currentPage);
        }
        SearchService searchService = new SearchService();
        try {
            PageBean pageBean= searchService.search(search, page, pageSize);
            request.setAttribute("pageBean", pageBean);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/search.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    
    }
}
