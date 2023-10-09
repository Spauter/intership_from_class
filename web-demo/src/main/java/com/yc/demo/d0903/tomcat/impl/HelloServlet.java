package com.yc.demo.d0903.tomcat.impl;


import com.yc.demo.d0903.tomcat.HttpServletRequest;
import com.yc.demo.d0903.tomcat.HttpServletResponse;
import com.yc.demo.d0903.tomcat.ServletException;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello.s")
public class HelloServlet extends HttpServletImpl {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        final PrintWriter out = response.getWriter();
        final String name = request.getParameter("name");
        if (name != null){
            out.println("<h1>Hello "+name+"!</h1>");
        }else {
            out.println("<h1>Hello world!</h1>");
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
