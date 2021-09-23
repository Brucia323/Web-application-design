package com.example.demo2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "Cart", value = "/Cart")
public class Cart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        Map<String, Integer> map = (HashMap<String, Integer>) session.getAttribute("cart");
        int totalPrice = 0;
        for (String key : map.keySet()) {
            totalPrice += DBHelper.getMobileById(key).getPrice() * map.get(key);
        }
        out.println("<html>\n" +
                "<head>\n" +
                "<title>购物袋</title>\n" +
                "<link rel=\"stylesheet\" href=\"styles/cart.css\">\n" +
                "<body>\n" +
                "<div id=\"header\">\n" +
                "<h1>你的购物袋总计 RMB " +
                totalPrice +
                "。</h1>\n" +
                "<button class=\"button\">结账</button>\n" +
                "</div>");
        for (String key : map.keySet()) {
            Mobile mobile = DBHelper.getMobileById(key);
            out.println("<div id=\"list\">\n" +
                    "<div id=\"type\">\n" +
                    "<h2>" +
                    mobile.getType() +
                    "</h2>\n" +
                    "</div>\n" +
                    "<div id=\"price\">\n" +
                    "<p>" +
                    mobile.getPrice() +
                    "</p>\n" +
                    "</div>" +
                    "<div id=\"number\">" +
                    map.get(key) +
                    "</div>\n");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
