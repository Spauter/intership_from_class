package com.yc.demo.d0903.tomcat.impl;

import com.yc.demo.d0903.tomcat.HttpServletRequest;
import com.yc.demo.d0903.tomcat.HttpServletResponse;

import javax.servlet.annotation.WebServlet;

@WebServlet("/loginout.s")
public class LoginoutServlet extends  HttpServletImpl{
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        request.getSession().invalidate();

        response.sendRedirect("tologin.s");
    }
}
