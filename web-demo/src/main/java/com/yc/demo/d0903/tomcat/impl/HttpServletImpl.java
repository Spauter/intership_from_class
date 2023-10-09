package com.yc.demo.d0903.tomcat.impl;

import com.yc.demo.d0903.tomcat.HttpServlet;
import com.yc.demo.d0903.tomcat.HttpServletRequest;
import com.yc.demo.d0903.tomcat.HttpServletResponse;
import com.yc.demo.d0903.tomcat.ServletException;

import java.io.IOException;

public class HttpServletImpl implements HttpServlet {
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
