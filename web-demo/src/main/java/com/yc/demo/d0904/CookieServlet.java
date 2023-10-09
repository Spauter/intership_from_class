package com.yc.demo.d0904;


import com.yc.demo.d0903.tomcat.Cookie;
import com.yc.demo.d0903.tomcat.HttpServletRequest;
import com.yc.demo.d0903.tomcat.HttpServletResponse;
import com.yc.demo.d0903.tomcat.ServletException;
import com.yc.demo.d0903.tomcat.impl.HttpServletImpl;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
@WebServlet("/Cookie.s")
public class CookieServlet extends HttpServletImpl {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        super.doGet(request, response);
        final Cookie[] cookies=request.getCookies();
        response.setContentType("text.html,;charset=utf-8");
        final PrintWriter printWriter=response.getWriter();
        printWriter.println("<h1>Cookie数据</h>");
        if(cookies !=null){
            printWriter.println("<ol>");
            for (Cookie c:cookies){
                printWriter.println(String.format("<li>%s : %s </li>",c.getName(),c.getValue()));
            }
            printWriter.println("</ol>");
        }

    }
}
