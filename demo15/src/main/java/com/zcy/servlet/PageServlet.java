package com.zcy.servlet;

import com.zcy.entity.PageBean;
import com.zcy.service.BookService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "PageServlet", value = "/PageServlet")
public class PageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int pageSize = 20;
        String currentPage = request.getParameter("currentPage");
        if (currentPage != null) {
            page = Integer.parseInt(currentPage);
        }
        BookService bookService = new BookService();
        try {
            PageBean pageBean = bookService.findByPage(page, pageSize);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("/book_list.jsp").forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    
    }
}
