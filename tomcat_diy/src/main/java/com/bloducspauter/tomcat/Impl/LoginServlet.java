package com.bloducspauter.tomcat.Impl;




import com.bloducspauter.tomcat.Cookie;
import com.bloducspauter.tomcat.HttpServletRequest;
import com.bloducspauter.tomcat.HttpServletResponse;
import com.bloducspauter.tomcat.ServletException;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login.s")
public class LoginServlet extends HTTPServletImpl{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        final String name = request.getParameter("username");
        final String pwd = request.getParameter("password");
        response.setContentType("text/html;charset=utf-8");
        final PrintWriter out = response.getWriter();
        if ("yc".equals(name) && "123".equals(pwd)){
            out.println("登录成功! <a href='tologin.s>重新登录</a>");
            Cookie cookie = new CookieImpl("myname", name);
            cookie.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(cookie);
        }else {
            out.println("用户名或密码错误! <a href='tologin.s'>重新登录</a>");
        }
    }
}
