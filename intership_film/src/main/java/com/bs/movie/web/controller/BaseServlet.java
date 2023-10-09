package com.bs.movie.web.controller;

import com.bs.movie.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ly.common.bean.Result;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 基本的servlet类，定义的Servlet都继承他
 */
public class BaseServlet extends HttpServlet {

    private static final long serialVersionUID = -8486226834811243648L;
    protected Gson gson = new Gson();


    public <T> T parseRequest(HttpServletRequest req, Class<T> cls) {
        T t = null;
        //根据反射获取所有的方法
        Method[] methods = cls.getDeclaredMethods();
        //创建对象
        try {
            // t = cls.newInstance();
            Constructor constructor = cls.getDeclaredConstructor();
            t = (T) constructor.newInstance();
            //获取所有的参数name名称
            Enumeration<String> enums = req.getParameterNames();
            while (enums.hasMoreElements()) {
                String name = enums.nextElement();
                for (Method method : methods) {
                    String methodName = method.getName();
                    if (("set" + name).equalsIgnoreCase(methodName)) {
                        String value = req.getParameter(name);
                        //获取setXxx方法形参类型
                        String typeName = method.getParameterTypes()[0].getName();
                        if ("java.lang.Integer".equals(typeName) || "int".equals(typeName)) {
                            method.invoke(t, Integer.parseInt(value));
                        } else if ("java.lang.Long".equals(typeName) || "long".equals(typeName)) {
                            method.invoke(t, Long.parseLong(value));
                        } else if ("java.lang.Float".equals(typeName) || "float".equals(typeName)) {
                            method.invoke(t, Float.parseFloat(value));
                        } else if ("java.lang.Double".equals(typeName) || "double".equals(typeName)) {
                            method.invoke(t, Double.parseDouble(value));
                        } else {
                            method.invoke(t, value);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return t;
    }

    /**
     * 请求的参数封装到map集合中
     *
     * @param req
     * @return
     */
    public Map<String, Object> parseRequest(HttpServletRequest req) {
        Map<String, Object> map = new HashMap<>();
        //获取到所有请求参数  的name名称
        Enumeration<String> enums = req.getParameterNames();
        while (enums.hasMoreElements()) {
            String name = enums.nextElement();
            String value = req.getParameter(name);
            if (!StringUtil.checkNull(value)) {
                map.put(name, req.getParameter(name));
            }
        }
        System.out.println(map);
        return map;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURL().toString();
        path = path.replaceAll(".+?/(\\w*)(\\.\\w+)?$", "$1");

        try {
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            Method m = this.getClass().getDeclaredMethod(path, HttpServletRequest.class, HttpServletResponse.class);
            System.out.println("执行：" + m);

            try {
                m.setAccessible(false);
                Object ret = m.invoke(this, request, response);
                if (ret != null) {
                    response.getWriter().append(this.gson.toJson(ret));
                }
            } catch (InvocationTargetException var6) {
                var6.printStackTrace();
                response.getWriter().append(this.gson.toJson(Result.failure("系统异常, 请联系管理员", (Object)null)));
            }

        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException var7) {
            throw new RuntimeException("方法名错误：" + path, var7);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    protected void send(HttpServletResponse resp, int result) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.print(result);
        out.flush();
        out.close();
    }

    protected void send(HttpServletResponse resp, String result) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.print(result);
        out.flush();
        out.close();
    }

    protected void send(HttpServletResponse resp, Object obj) throws  IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        Gson gson = new GsonBuilder().serializeNulls().create();
        out.print(gson.toJson(obj));
        out.flush();
        out.close();
    }

    protected void send(HttpServletResponse resp, int code, Object obj) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("data", obj);
        Gson gson = new GsonBuilder().serializeNulls().create();
        out.print(gson.toJson(map));
        out.flush();
        out.close();
    }
}
