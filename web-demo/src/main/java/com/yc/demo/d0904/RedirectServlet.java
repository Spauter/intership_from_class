package com.yc.demo.d0904;

import com.yc.demo.d0903.tomcat.HttpServletRequest;
import com.yc.demo.d0903.tomcat.HttpServletResponse;
import com.yc.demo.d0903.tomcat.ServletException;
import com.yc.demo.d0903.tomcat.impl.HttpServletImpl;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
@WebServlet("/toXXX.s")
public class RedirectServlet extends HttpServletImpl {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        super.doGet(request, response);
        response.sendRedirect("index.html");
    }
    //    响应重定向

}
