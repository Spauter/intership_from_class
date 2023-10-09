package com.yc.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CookieServlet",value = "/cookie.s")
public class CookieServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html:charset=utf-8");
        final PrintWriter out = resp.getWriter();
        final Cookie[] cookies = req.getCookies();
        if (cookies != null){
            out.append("<ol>");
            for (Cookie cookie : cookies){
                out.append("<li>")
                        .append(cookie.getName())
                        .append(" = ")
                        .append(cookie.getValue())
                        .append("</li>");
            }
            out.append("</ol>");
        }

        final String name = req.getParameter("name");
        final String value = req.getParameter("value");
        final String maxAge = req.getParameter("maxAge");
        if (name != null){
            Cookie cookie = new Cookie(name,value);
            if (maxAge!=null){
                cookie.setMaxAge( Integer.parseInt(maxAge));
            }
            resp.addCookie(cookie);
        }
    }
}
