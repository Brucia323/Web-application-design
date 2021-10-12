package com.example.demo2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 添加到购物车
 *
 * @ZZZCNY
 */
@WebServlet(name = "addcart", value = "/addcart")
public class AddCart extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        HttpSession session = request.getSession();
        Map<String, Integer> list = (HashMap<String, Integer>) session.getAttribute("cart");
        if (list == null) {
            list = new HashMap<>();
        }
        if (list.containsKey(id)) {
            int count = list.get(id);
            list.put(id, count + 1);
        } else {
            list.put(id, 1);
        }
        session.setAttribute("cart", list);
        PrintWriter out = response.getWriter();
        out.print(
                "<html><head><title>首页</title><link rel='stylesheet' href='styles/index.css'></head><body><div id='head'><h2>全部机型，</h2><span>随你挑。</span><span id='cart'>"
                        + DBHelper.getMobileById(id).getType()
                        + "已加入购物袋</span><a href='cart'><button type='submit' name='proceed' value='proceed' class='button'>查看购物袋</button></a></div><div><a class='link' href='addcart?id=1'><div class='commodity'><div class='name'><h3>iPhone 13 mini</h3></div><div><img src='images/iphone-13-mini-select-2021.jpg' alt=''></div><div class='price'><h3>RMB 5199</h3></div></div></a><a class='link' href='addcart?id=2'><div class='commodity'><div class='name'><h3>iPhone 13</h3></div><div><img src='images/iphone-13-select-2021.jpg' alt=''></div><div class='price'><h3>RMB 5999</h3></div></div></a><a class='link' href='addcart?id=3'><div class='commodity'><div class='name'><h3>iPhone 13 Pro</h3></div><div><img src='images/iphone-13-pro-family-select.jpg' alt=''></div><div class='price'><h3>RMB 7999</h3></div></div></a><a class='link' href='addcart?id=4'><div class='commodity'><div class='name'><h3>iPhone 13 Pro Max</h3></div><div><img src='images/iphone-13-pro-max-family-select.jpg' alt=''></div><div class='price'><h3>RMB 8999</h3></div></div></a></div></body></html>");
    }
}