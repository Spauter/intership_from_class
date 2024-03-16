package com.yc.demo.d0903.tomcat.impl;

import com.yc.demo.d0903.tomcat.HttpServletRequest;
import com.yc.demo.d0903.tomcat.HttpServletResponse;
import com.yc.demo.d0903.tomcat.ServletException;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/error.s")
public class ErrorServlet  extends HttpServletImpl{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        super.doGet(request, response);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("出错了");
    }


}
