package com.yc.demo.d0903;

import com.yc.demo.d0903.tomcat.HttpServletRequest;
import com.yc.demo.d0903.tomcat.HttpServletResponse;
import com.yc.demo.d0903.tomcat.ServletException;
import com.yc.demo.d0903.tomcat.impl.HttpServletImpl;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet定义步骤
 * 1.继承HTTPServlet;
 * 2.实现：doXXX
 * 3.配置：注解
 */
@WebServlet("/helloWorld.s")
public class HelloServlet extends HttpServletImpl {

 @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        super.doGet(request, response);
        response.setContentType("text/html,charset=utf-8");
        final PrintWriter printWriter=response.getWriter();
        printWriter.println("<h1>Hello World</h1>");

    }
}
