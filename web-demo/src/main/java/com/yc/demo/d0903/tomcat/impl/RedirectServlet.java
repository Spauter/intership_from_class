package com.yc.demo.d0903.tomcat.impl;


import com.yc.demo.d0903.tomcat.HttpServletRequest;
import com.yc.demo.d0903.tomcat.HttpServletResponse;
import com.yc.demo.d0903.tomcat.ServletException;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/toWebapp.s")
public class RedirectServlet  extends HttpServletImpl {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.sendRedirect("webapp/login.html");
    }
}
