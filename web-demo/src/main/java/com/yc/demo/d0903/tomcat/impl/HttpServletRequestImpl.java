package com.yc.demo.d0903.tomcat.impl;

import com.yc.demo.d0903.tomcat.Cookie;
import com.yc.demo.d0903.tomcat.HttpServletRequest;
import com.yc.demo.d0903.tomcat.HttpServletResponse;
import com.yc.demo.d0903.tomcat.HttpSession;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class HttpServletRequestImpl implements HttpServletRequest {
    private String protocol;
    private String requestURI;
    private String method;
    private Map<String, String> headerMap = new HashMap<>();
    private Socket socket;
    private Map<String, String> parameterMap = new HashMap<>();

    List<Map<String, String>> parameterMaps = new ArrayList<>();

    private String characterEncoding = "utf-8";

    public HttpServletRequestImpl(Socket socket) {
        this.socket = socket;
        final InputStream in;
        try {
            in = socket.getInputStream();
            String line;
            int k = 0;
            while (true) {
                line = readLine(in);
                System.out.println("-----------" + line);
                if (k == 0) {
                    final String[] items = line.split(" ");
                    if (items.length != 3) {
                        break;
                    }
                    this.method = items[0];
                    this.requestURI = items[1];
                    this.protocol = items[2];
                } else if (line.isEmpty()) {
                    break;
                } else {
                    final String[] items = line.split(": ");
                    headerMap.put(items[0], items[1]);
                }
                k++;
            }

            if ("POST".equals(method)) {
                final String s = headerMap.get("Content-Length");
                final int length = Integer.parseInt(s);
                StringBuilder sb = new StringBuilder();
                for (int i1 = 0; i1 < length; i1++) {
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

            if (requestURI == null) {
                requestURI="error.s";
            }
            if (requestURI.contains("?")) {
                final int i = requestURI.indexOf("?");
                final int j = requestURI.indexOf("#");
                String search;
                if (j > 0) {
                    search = requestURI.substring(i + 1, j);
                } else {
                    search = requestURI.substring(i + 1);
                }
                buildParameter(search);
                requestURI = requestURI.substring(0, i);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    String readLine(InputStream in) throws IOException {
        StringBuilder line = new StringBuilder();
        char c;
        while (true) {
            c = (char) in.read();
            if (c > 255) {
                break;
            }
            if (c == '\r') {
                in.read();
                break;
            } else {
                line.append(c);
            }
        }
        return line.toString();
    }

    private void buildParameter(String search) {
        final String[] paramArr = search.split("&");
        for (String paramStr : paramArr) {
//            重复的关键字会覆盖
//            如果new 一个 parameterMaps 会重置该值，并影响 getParameter()
//            就重新定义一个map,并加入到list里面
            Map<String, String> map = new HashMap<>();
            final String[] items = paramStr.split("=");
            if (items.length > 1) {
                parameterMap.put(items[0], items[1]);
                map.put(items[0], items[1]);
            }
            parameterMaps.add(map);
        }
    }

    public String toString() {
        final String[] ret = {"HttpServletRequestImpl{" +
                "protocol='" + protocol + '\'' +
                ", requestURI='" + requestURI + '\'' +
                ", method='" + method + '\'' +
                "}\r\n"};
        headerMap.forEach((name, value) -> {
            ret[0] += name + ": " + value + "\r\n";
        });
        return ret[0];
    }

    public InputStream getInputStream() {
        try {
            return socket.getInputStream();
        } catch (IOException e) {
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
        if (s != null) {
            List<Cookie> cookies = new ArrayList<>();
            final String[] strings = s.split("; ");
            for (String string : strings) {
                final String[] items = string.split("=");
                Cookie c = new CookieImpl(items[0], items[1]);
                cookies.add(c);
            }
            return cookies.toArray(new Cookie[0]);
        }
        return null;
    }

    @Override
    public String getRequestedSessionId() {
        return null;
    }

    @Override
    public HttpSession getSession() {
        final Cookie[] cookies = getCookies();
        HttpSession ret = null;
        String sessionId = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("JSESSIONID")) {
                    sessionId = c.getValue();
                    break;
                }
            }
            if (sessionId != null) {
                ret = sessionMap.get(sessionId);
            }
        }
        if (ret == null) {
            ret = new HttpSessionImpl();
            sessionMap.put(ret.getId(), ret);
        }
        ret.setLastAccessedTime(System.currentTimeMillis());
        return ret;
    }


    @Override
    public String getParameter(String name) {
        String value = parameterMap.get(name);
        if (characterEncoding != null) {
            try {
                value = URLDecoder.decode(value, characterEncoding);
            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = new String[parameterMaps.size()];
        for (int index = 0; index < parameterMaps.size(); index++) {
            String value = parameterMaps.get(index).get(name);
            if (characterEncoding != null) {
                try {
                    value = URLDecoder.decode(value, characterEncoding);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException("参数解码错误!", e);
                } finally {
                    values[index] = value;
                }
            }
        }
        return values;
    }

    @Override
    public String getQueryString() {
        return null;
    }


    private static Map<String, HttpSession> sessionMap = new ConcurrentHashMap<>();

    static {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Iterator<Map.Entry<String, HttpSession>> iterator = sessionMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    final Map.Entry<String, HttpSession> next = iterator.next();
                    final HttpSession session = next.getValue();
                    long time = System.currentTimeMillis() - session.getLastAccessedTime();
                    if (time > session.getMaxInactiveInterval() * 1000 * 60) {
                        System.out.println("会话自动失效：" + next.getKey());
                        iterator.remove();
                    }
                }
            }
        }, 0, 1000);
    }


    @Override
    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }


    //内部会话实现类
    class HttpSessionImpl implements HttpSession {

        private long createTime = System.currentTimeMillis();
        private String id = UUID.randomUUID().toString().replaceAll("_", "");

        public void setLastAccessedTime(long lastAccessedTime) {
            this.lastAccessedTime = lastAccessedTime;
        }

        private long lastAccessedTime;
        private int maxInacationTIme = 30;
        private Map<String, Object> attributeMap = new HashMap<>();

        @Override
        public long getCreationTime() {
            return createTime;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public long getLastAccessedTime() {
            return 0;
        }

        @Override
        public int getMaxInactiveInterval() {
            return 0;
        }

        @Override
        public void invalidate() {
            sessionMap.remove(id);
        }

        @Override
        public void setAttribute(String name, Object value) {
            attributeMap.put(name, value);
        }

        @Override
        public Object getAttribute(String name) {
            return attributeMap.get(name);
        }

        @Override
        public void removeAttribute(String name) {
            attributeMap.remove(name);
        }
    }


}
