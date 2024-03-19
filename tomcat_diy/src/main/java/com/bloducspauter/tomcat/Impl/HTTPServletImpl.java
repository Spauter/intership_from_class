package com.bloducspauter.tomcat.Impl;



import com.bloducspauter.tomcat.HttpServlet;
import com.bloducspauter.tomcat.HttpServletRequest;
import com.bloducspauter.tomcat.HttpServletResponse;
import com.bloducspauter.tomcat.ServletException;

import java.io.IOException;

public class HTTPServletImpl implements HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if ("GET".equals(request.getMethod())){
            doGet(request,response);
        }else if ("POST".equals(request.getMethod())){
            doPost(request,response);
        }else if ("OPTIONS".equals(request.getMethod())){
            doOptions(request,response);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        throw new ServletException("405 错误, 该Servlet不支持该类型请求:GET");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        throw new ServletException("405 错误, 该Servlet不支持该类型请求:POST");
    }

    @Override
    public void doHead(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }

    @Override
    public void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        throw new ServletException("405 错误, 该Servlet不支持该类型请求:OPTIONS");
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }

    @Override
    public void doTrace(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }
}
