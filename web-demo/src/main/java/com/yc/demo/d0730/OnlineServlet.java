package com.yc.demo.d0730;

import com.yc.demo.util.Utils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "OnlineServlet", value = "/online.s")
public class OnlineServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getServletContext();
        request.getSession().getServletContext();
        final ServletContext servletContext = this.getServletContext();
        final Object onlinemap = servletContext.getAttribute("onlineMap");
        Utils.result(response, onlinemap);
    }
}
