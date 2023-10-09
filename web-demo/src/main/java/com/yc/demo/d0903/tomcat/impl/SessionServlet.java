package com.yc.demo.d0903.tomcat.impl;

import com.yc.demo.d0903.tomcat.HttpServletRequest;
import com.yc.demo.d0903.tomcat.HttpServletResponse;
import com.yc.demo.d0903.tomcat.HttpSession;

import javax.servlet.annotation.WebServlet;
import java.io.PrintWriter;

@WebServlet("/session.s")
public class SessionServlet extends HttpServletImpl {

    public void doGet(HttpServletRequest request, HttpServletResponse response){
        HttpSession session=request.getSession();
        String name=request.getParameter("name");
        response.setContentType("text/html;charset=utf-8");
        final PrintWriter printWriter=response.getWriter();
        if(name==null){
            name=(String) session.getAttribute("name");
            printWriter.println("会话中的name="+name);
        }else {
            session.setAttribute("name",name);
            printWriter.println(name+"已经保存到会话中");
        }
    }
}
