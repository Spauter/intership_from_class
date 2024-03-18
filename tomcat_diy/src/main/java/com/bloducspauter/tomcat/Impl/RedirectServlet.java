package com.bloducspauter.tomcat.Impl;


import com.yc.demo.d0903.tomcat.HttpServletRequest;
import com.yc.demo.d0903.tomcat.HttpServletResponse;
import com.yc.demo.d0903.tomcat.ServletException;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/toWebapp.s")
public class RedirectServlet  extends HTTPServletImpl{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.sendRedirect("webapp/login.html");
    }
}
