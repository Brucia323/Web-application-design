package com.example.demo8;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SearchSuggest", value = "/searchsuggest")
public class SearchSuggest extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        HashMap map = new HashMap();
        map.put("a", "<words><word>a</word><word>ab</word><word>abc</word><word>abcd</word><word>abcde</word></words>");
        map.put("ab", "<words><word>ab</word><word>abc</word><word>abcd</word><word>abcde</word></words>");
        map.put("abc", "<words><word>abc</word><word>abcd</word><word>abcde</word></words>");
        map.put("abcd", "<words><word>abcd</word><word>abcde</word></words>");
        map.put("abcde", "<words><word>abcde</word></words>");
        String inputWord = req.getParameter("inputWord");
        if (!map.containsKey(inputWord)) {
            out.println("<words></words>");
        } else {
            out.println(map.get(inputWord).toString());
        }
    }
}