package com.yc.demo.d0903.tomcat.impl;

import com.yc.demo.d0903.tomcat.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpServletResponseImpl implements HttpServletResponse {
    private Socket socket;
    private HttpServletRequest request;
    private int status = 200;
    private String msg = "OK";
    private Map<String, String> headerMap = new HashMap<>();

    public HttpServletResponseImpl(Socket socket, HttpServletRequest request){
        this.socket = socket;
        this.request = request;
    }


    CharArrayWriter caw = new CharArrayWriter();
    PrintWriter pw = new PrintWriter(caw);
    @Override
    public PrintWriter getWriter() {
        return pw;
    }

    @Override
    public OutputStream getOutputStream() {
        try {
            return socket.getOutputStream();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setContentType(String type) {
        setHeader("Content-Type", type);
    }

    @Override
    public void setStatus(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    @Override
    public void setHeader(String name, String value) {
        headerMap.put(name,value);
    }

    @Override
    public void flushBuffer() throws IOException {
        HttpSession session=request.getSession();
        Cookie cookie1=new CookieImpl("JSESSIONID",session.getId());
        addCookie(cookie1);
        final OutputStream out = socket.getOutputStream();
        out.write(String.format("%s %d %s\n",
                request.getProtocol(),
                this.status,
                this.msg).getBytes());
        for (Map.Entry<String, String> entry : headerMap.entrySet()){
            out.write(String.format("%s: %s\n",
                    entry.getKey(),entry.getValue()).getBytes());
        }


        for (Cookie cookie : cookies){
            out.write(cookie.toString().getBytes());
        }

        out.write("\n".getBytes());

        final String content = caw.toString();
        if (status >= 300 & status <= 399){

        }else if (content != null && content.length() > 0){
            out.write(content.getBytes());
        }else {

            String diskPath = TomcatImpl.webRoot + request.getRequestURI();

            try (FileInputStream fis = new FileInputStream(diskPath)) {
                byte[] bytes = new byte[1024];
                int count;
                while ((count = fis.read(bytes)) > 0) {
                    out.write(bytes, 0, count);
                }
            }
        }
    }

    @Override
    public void sendRedirect(String location) {
        setStatus(301,"Redirect");
        setHeader("Localtion",location);
    }


    private List<Cookie> cookies = new ArrayList<>();
    @Override
    public void addCookie(Cookie cookie) {
        cookie.add(cookie);
    }
}
