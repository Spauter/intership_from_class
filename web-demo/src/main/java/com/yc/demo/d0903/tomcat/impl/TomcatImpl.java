package com.yc.demo.d0903.tomcat.impl;

import com.yc.demo.d0903.tomcat.*;

import javax.servlet.annotation.WebServlet;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class TomcatImpl implements Tomcat {
    public static String webRoot;

    public static void main(String[] args) {
        new TomcatImpl().start(80,"D:\\spouter\\JAVA\\Github\\intership_from_class\\web-demo\\src\\main");
    }

    @Override
    public void start(int port, String webRoot) {
        TomcatImpl.webRoot = webRoot;
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("服务器启动成功:" + port);
            while (true){
                final Socket socket = server.accept();
                HttpServletRequest request = buildRequest(socket);
                final HttpServletResponse response = buildResponse(socket,request);
                if (isServletRequest(request)) {
                    processServletRequest(request, response);
                }else if (isStaticRequest(request)){
                    processServletRequest(request, response);
                }else {
                    final OutputStream out = socket.getOutputStream();
                    out.write("HTTP/1.1 404 not Found\n".getBytes());
                    out.write("Content-Type: text/html;charset=utf-8\n".getBytes());
                    out.write("\n".getBytes());
                    out.write("404错误，访问的目标资源不存在\n".getBytes());
                }
                socket.close();
            }
        }catch (IOException | ServletException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public HttpServletRequest buildRequest(Socket socket) throws IOException {
        final HttpServletRequestImpl httpServletRequest = new HttpServletRequestImpl(socket);
        System.out.println("httpServletRequest = " + httpServletRequest);
        return httpServletRequest;
    }

    @Override
    public HttpServletResponse buildResponse(Socket socket, HttpServletRequest request) throws IOException {
        return new HttpServletResponseImpl(socket,request);
    }

    @Override
    public boolean isStaticRequest(HttpServletRequest request) {
        String diskPath = webRoot + request.getRequestURI();
        return new File(diskPath).exists();
    }

    Map<String, HttpServlet> servletMap = new HashMap<>();
    TomcatImpl(){
        Class[] servlets = new Class[]{
                HelloServlet.class,
                RedirectServlet.class,
                CookieServlet.class,
                ToLoginServlet.class,
                LoginServlet.class,
                SessionServlet.class,
                LoginoutServlet.class,
                MyLikeServel.class,
                ErrorServlet.class
        };
        for (Class aClass : servlets){
            try {
                final Object servlet = aClass.newInstance();
                WebServlet webServlet = (WebServlet) aClass.getAnnotation(WebServlet.class);
                final String[] paths = webServlet.value();
                for (String path : paths){
                    servletMap.put(path, (HttpServlet) servlet);
                }
            }catch (Exception e){
                throw new RuntimeException("创建Servlet失败!",e);
            }
        }
    }

    @Override
    public boolean isServletRequest(HttpServletRequest request) {
        return servletMap.containsKey(request.getRequestURI());
    }

    @Override
    public void processStaticRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String contentType = getMimeType(request.getRequestURI());
        response.setContentType(contentType);
        response.flushBuffer();
    }

    @Override
    public void processServletRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        final HttpServlet httpServlet = servletMap.get(request.getRequestURI());
        httpServlet.service(request,response);
        response.flushBuffer();
    }

    @Override
    public String getMimeType(String path) {
        if (path.endsWith(".js")){
            return "application/javascript;charset=utf-8";
        }else if (path.endsWith(".css")){
            return "text/css;charset=utf-8";
        }else if (path.endsWith(".jpg")){
            return "image/jpg";
        }else {
            return "text/html;charset=utf-8";
        }
    }

    @Override
    public void listenShutdown() {

    }
}
