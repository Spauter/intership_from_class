package com.yc.demo;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@WebServlet(name = "FindUserServlet",value = "/findUser.s",
             loadOnStartup = 1)
public class FindUserServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final List<Map<String, Object>> list = JdbcTemplate.select("select * from user");
        response.setContentType("text/html;charset=utf-8");
        final PrintWriter out = response.getWriter();

        Gson gson = new Gson();
        final String json = gson.toJson(list);
        out.append(json);
    }
}
