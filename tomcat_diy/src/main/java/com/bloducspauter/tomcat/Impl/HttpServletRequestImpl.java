package com.bloducspauter.tomcat.Impl;

import com.yc.demo.d0903.tomcat.Cookie;
import com.yc.demo.d0903.tomcat.HttpServletRequest;
import com.yc.demo.d0903.tomcat.HttpSession;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpServletRequestImpl implements HttpServletRequest {
    private String protocol;
    private String requestURI;
    private String method;
    private Map<String, String> headerMap = new HashMap<>();
    private Socket socket;
    private Map<String, String> parameterMap = new HashMap<>();

    public HttpServletRequestImpl(Socket socket){
        this.socket = socket;
        final InputStream in;
        try {
            in = socket.getInputStream();
            String line;
            int k = 0;
            while (true){
                line = readLine(in);
                System.out.println("-----------"+line);
                if (k == 0){
                    final String[] items = line.split(" ");
                    this.method = items[0];
                    this.requestURI = items[1];
                    this.protocol = items[2];
                }else if (line.isEmpty()){
                    break;
                }else {
                    final String[] items = line.split(": ");
                    headerMap.put(items[0], items[1]);
                }
                k ++;
            }

            if ("POST".equals(method)){
                final String s = headerMap.get("Content-Length");
                final int length = Integer.parseInt(s);
                StringBuilder sb = new StringBuilder();
                for (int i1 = 0; i1 < length; i1++){
                    char c = (char) in.read();
                    sb.append(c);
                }
                System.out.println("响应体内容: ");
                System.out.println(sb);
            }
            {
//                byte[] bytes = new byte[4024];
//                int count = in.read(bytes);
//                String content = new String(bytes, 0, count);
//                System.out.println(content);
//
//                final String[] lines = content.split("\\r\\n");
//                for (int i = 0; i < lines.length; i++) {
//                    if (i == 0) {
//                        final String[] items = lines[i].split(" ");
//                        this.method = items[0];
//                        this.requestURI = items[1];
//                        this.protocol = items[2];
//                    } else if (lines[i].isEmpty()) {
//                        break;
//                    } else {
//                        final String[] items = lines[i].split(": ");
//                        headerMap.put(items[0], items[1]);
//                    }
//                }
            }
            if (requestURI.contains("?")){
                final int i = requestURI.indexOf("?");
                final int j = requestURI.indexOf("#");
                String search;
                if (j > 0){
                    search = requestURI.substring(i + 1, j);
                }else {
                    search = requestURI.substring(i + 1);
                }
                buildParameter(search);
                requestURI = requestURI.substring(0, i);
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

     String readLine(InputStream in) throws IOException {
        StringBuilder line = new StringBuilder();
        char c;
        while (true){
            c = (char) in.read();
            if (c == '\r'){
                in.read();
                break;
            }else {
                line.append(c);
            }
        }
        return line.toString();
    }

    private void buildParameter(String search) {
        final String[] paramArr = search.split("&");
        for (String paramStr : paramArr){
            final String[] items = paramStr.split("=");
            if (items.length == 1){
                parameterMap.put(items[0], "");
            }else if (items.length > 1){
                parameterMap.put(items[0], items[1]);
            }
        }
    }

    public String toString(){
        final String[] ret = {"HttpServletRequestImpl{" +
                "protocol='" + protocol + '\'' +
                ", requestURI='" + requestURI + '\'' +
                ", method='" + method + '\'' +
                "}\r\n"};
        headerMap.forEach((name, value) ->{
            ret[0] +=name + ": " + value + "\r\n";
        });
        return ret[0];
    }

    public InputStream getInputStream(){
        try {
            return socket.getInputStream();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getRequestURI() {
        return requestURI;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public String getHeader(String name) {
        return null;
    }

    @Override
    public Cookie[] getCookies() {
        final String s = this.headerMap.get("Cookie");
        if (s != null){
            List<Cookie> cookies = new ArrayList<>();
            final String[] strings = s.split("; ");
            for (String string : strings){
                final String[] items = string.split("=");
                Cookie c = new CookieImpl(items[0], items[1]);
                cookies.add(c);
            }
            return cookies.toArray(new Cookie[0]);
        }
        return null;
    }

    @Override
    public HttpSession getSession() {
        return null;
    }

    @Override
    public String getParameter(String name) {
        String value = parameterMap.get(name);
        if (characterEncoding!=null){
            try {
                value = URLDecoder.decode(value,characterEncoding);
            }catch (UnsupportedEncodingException e){
                throw new RuntimeException("参数解码错误!", e);
            }
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        return new String[0];
    }

    @Override
    public String getQueryString() {
        return null;
    }


    private String characterEncoding;
    @Override
    public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
        this.characterEncoding = characterEncoding;
    }
}
