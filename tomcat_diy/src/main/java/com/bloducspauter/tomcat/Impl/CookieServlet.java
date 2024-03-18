package com.bloducspauter.tomcat.Impl;


import com.yc.demo.d0903.tomcat.Cookie;
import com.yc.demo.d0903.tomcat.HttpServletRequest;
import com.yc.demo.d0903.tomcat.HttpServletResponse;
import com.yc.demo.d0903.tomcat.ServletException;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/cookie.s")
public class CookieServlet extends HTTPServletImpl{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        final Cookie[] cookies = request.getCookies();

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        final PrintWriter out = response.getWriter();
        out.println("<h1>Cookie数据</h1>");
        if (cookies != null){
            out.println("<ol>");
            for (Cookie cookie : cookies){
                out.println(String.format("<li>%s : %s</li>",cookie.getName(), cookie.getValue()));
            }
            out.println("</ol>");
        }

        final String cname = request.getParameter("cname");
        final String cvalue = request.getParameter("cvalue");
        Cookie cookie = new CookieImpl(cname, cvalue);

        response.addCookie(cookie);
    }
}
