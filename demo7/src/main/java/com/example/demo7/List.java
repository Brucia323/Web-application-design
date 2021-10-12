package com.example.demo7;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "list", value = "/list")
public class List extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        Map<String, String> pm = new HashMap<String, String>();
        pm.put("山东", "济南,青岛,泰安,潍坊,烟台,聊城,枣庄,菏泽,莱芜,临沂");
        pm.put("江苏", "南京,苏州,无锡,徐州,南通,连云港,镇江,常州,淮安,扬州");
        pm.put("广东", "广州,深圳,珠海,汕头,佛山,东莞,湛江,江门,中山,惠州");
        PrintWriter out = resp.getWriter();
        String s1 = req.getParameter("proc");
        out.println(pm.get(s1));
    }
}