package com.example.demo2;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AddCart", value = "/AddCart")
public class AddCart extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
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
        out.print("<html>\n" +
                "<head>\n" +
                "    <title>首页</title>\n" +
                "    <link rel=\"stylesheet\" href=\"styles/index.css\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<div id=\"head\">\n" +
                "    <h2>全部机型，</h2>\n" +
                "    <span>随你挑。</span>\n" +
                "<span id=\"cart\">" + DBHelper.getMobileById(id).getType() + "已加入购物袋</span>\n" +
                "<a href=\"Cart\">\n" +
                "<button type=\"submit\" name=\"proceed\" value=\"proceed\" class=\"button\">查看购物袋</button>" +
                "</a>" +
                "</div>\n" +
                "<div>\n" +
                "    <a class=\"link\" href=\"AddCart?id=1\">\n" +
                "        <div class=\"commodity\">\n" +
                "            <div class=\"name\">\n" +
                "                <h3>iPhone 13 mini</h3>\n" +
                "            </div>\n" +
                "            <div>\n" +
                "                <img src=\"images/iphone-13-mini-select-2021.jpg\" alt=\"\">\n" +
                "            </div>\n" +
                "            <div class=\"price\">\n" +
                "                <h3>RMB 5199</h3>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </a>\n" +
                "    <a class=\"link\" href=\"AddCart?id=2\">\n" +
                "        <div class=\"commodity\">\n" +
                "            <div class=\"name\">\n" +
                "                <h3>iPhone 13</h3>\n" +
                "            </div>\n" +
                "            <div>\n" +
                "                <img src=\"images/iphone-13-select-2021.jpg\" alt=\"\">\n" +
                "            </div>\n" +
                "            <div class=\"price\">\n" +
                "                <h3>RMB 5999</h3>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </a>\n" +
                "    <a class=\"link\" href=\"AddCart?id=3\">\n" +
                "        <div class=\"commodity\">\n" +
                "            <div class=\"name\">\n" +
                "                <h3>iPhone 13 Pro</h3>\n" +
                "            </div>\n" +
                "            <div>\n" +
                "                <img src=\"images/iphone-13-pro-family-select.jpg\" alt=\"\">\n" +
                "            </div>\n" +
                "            <div class=\"price\">\n" +
                "                <h3>RMB 7999</h3>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </a>\n" +
                "    <a class=\"link\" href=\"AddCart?id=4\">\n" +
                "        <div class=\"commodity\">\n" +
                "            <div class=\"name\">\n" +
                "                <h3>iPhone 13 Pro Max</h3>\n" +
                "            </div>\n" +
                "            <div>\n" +
                "                <img src=\"images/iphone-13-pro-max-family-select.jpg\" alt=\"\">\n" +
                "            </div>\n" +
                "            <div class=\"price\">\n" +
                "                <h3>RMB 8999</h3>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </a>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
    }

    public void destroy() {
    }
}