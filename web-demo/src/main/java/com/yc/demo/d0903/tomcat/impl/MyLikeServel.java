package com.yc.demo.d0903.tomcat.impl;

import com.yc.demo.d0903.tomcat.HttpServletRequest;
import com.yc.demo.d0903.tomcat.HttpServletResponse;
import com.yc.demo.d0903.tomcat.ServletException;
import com.yc.demo.d0903.tomcat.impl.HttpServletImpl;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

//地址栏输入: http://127.0.0.1/mylike.s?like=篮球&like=电影&like=手游 浏览器显示爱好列表, 将输入的爱好全部显示在 ol 列表中 提示: 实现 请求对象的 public String[] getParameterValues(String name) 方法
@WebServlet("/mylike.s")
public class MyLikeServel extends HttpServletImpl {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        super.doGet(request, response);
        String[] likes=request.getParameterValues("like");
        response.setContentType("text/html;charset=utf-8");
        final PrintWriter printWriter=response.getWriter();
        for (int i=0;i<likes.length;i++){
            printWriter.print(likes[i]+"\t");
        }


    }
}
