package com.example.demo2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 购物车
 *
 * @ZZZCNY
 */
@WebServlet(name = "cart", value = "/cart")
public class Cart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        Map<String, Integer> map = (HashMap<String, Integer>) session.getAttribute("cart");
        int totalPrice = 0;
        for (String key : map.keySet()) {
            totalPrice += DBHelper.getMobileById(key).getPrice() * map.get(key);
        }
        out.println(
                "<html><head><title>购物袋</title><link rel='stylesheet' href='styles/cart.css'><body><div id='header'><h1>你的购物袋总计 RMB "
                        + totalPrice + "。</h1><button class='button'>结账</button></div>");
        for (String key : map.keySet()) {
            Mobile mobile = DBHelper.getMobileById(key);
            out.println("<div id='list'><div id='type'><h2>" + mobile.getType() + "</h2></div><div id='price'><p>"
                    + mobile.getPrice() + "</p></div><div id='number'>" + map.get(key) + "</div>");
        }
    }
}
