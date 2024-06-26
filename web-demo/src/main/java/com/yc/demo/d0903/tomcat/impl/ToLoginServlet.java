package com.yc.demo.d0903.tomcat.impl;


import com.yc.demo.d0903.tomcat.Cookie;
import com.yc.demo.d0903.tomcat.HttpServletRequest;
import com.yc.demo.d0903.tomcat.HttpServletResponse;
import com.yc.demo.d0903.tomcat.ServletException;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/tologin.s")
public class ToLoginServlet extends HttpServletImpl {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String myname ="";
        final Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies){
                if ("myname".equals(cookie.getName())){
                    myname = cookie.getValue();
                }
            }
        }

        final  Object loginedUser =request.getSession().getAttribute("loginedUser");
        if(loginedUser!=null){
            response.getWriter().println("<h1>欢迎</h1>");
        }
        String html = "<form action=\"login.s\">\n" +
                "    账号:<input type=\"text\" name=\"username\" autocapitalize=\"on\" value='"+myname+"'><br>\n" +
                "    密码:<input type=\"text\" name=\"password\" autocomplete=\"on\"><br>\n" +
                "    有效期:<select name=\"age\"\n" +
                "             <option value=\"1\">1天</option>\n" +
                "             <option value=\"2\">1周</option>\n" +
                "             <option value=\"3\">1月</option>\n" +
                "             <option value=\"4\">1年</option>\n" +
                "             <option value=\"5\">立即删除(Max-Age=0)</option>\n" +
                "          </select><br>" +
                "       <button>登录</button>\n" +
                "</form>";
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(html);
    }
}
