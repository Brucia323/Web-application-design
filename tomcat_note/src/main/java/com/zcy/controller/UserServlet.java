package com.zcy.controller;

import com.zcy.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserServlet", value = "/UserServlet")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String flag = request.getParameter("flag");
        if (flag.equals("login")) {
            out.print(login(request));
        } else if (flag.equals("logup")) {
            out.print(logup(request));
        } else if (flag.equals("checkUsername")) {
            out.print(checkUsername(request));
        }
    }
    
    /**
     * 登录
     *
     * @param request 请求
     * @return 用户id
     */
    private int login(HttpServletRequest request) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserService user = new UserService();
        int userId = user.login(username, password);
        if (userId != 0) {
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("userId", userId);
        }
        return userId;
    }
    
    /**
     * 注册
     *
     * @param request 请求
     * @return 用户id
     */
    private int logup(HttpServletRequest request) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserService user = new UserService();
        int userId = user.logup(username, password);
        if (userId != 0) {
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("userId", userId);
        }
        return userId;
    }
    
    /**
     * 检查账号是否已被注册
     *
     * @param request 请求
     * @return true=1/false=0
     */
    private int checkUsername(HttpServletRequest request) throws IOException {
        String username = request.getParameter("username");
        UserService user = new UserService();
        return user.checkUsername(username);
    }
}
