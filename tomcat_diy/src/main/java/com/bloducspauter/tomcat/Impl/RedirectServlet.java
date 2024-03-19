package com.bloducspauter.tomcat.Impl;




import com.bloducspauter.tomcat.HttpServletRequest;
import com.bloducspauter.tomcat.HttpServletResponse;
import com.bloducspauter.tomcat.ServletException;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/toWebapp.s")
public class RedirectServlet  extends HTTPServletImpl{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.sendRedirect("webapp/login.html");
    }
}
