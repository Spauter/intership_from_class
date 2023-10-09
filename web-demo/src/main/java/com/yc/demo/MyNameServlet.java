package com.yc.demo;

import com.yc.demo.util.Utils;
import com.yc.demo.vo.Result;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "MyNameServlet",value = "/myName.s")
public class MyNameServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final HttpSession session = request.getSession();
        final Object loginedUSer = session.getAttribute("loginedUser");
        Result result;
        if (loginedUSer == null){
            result = new Result(0,"未登录",null);
        }else {
            result = new Result(1,"已登录",loginedUSer);
        }
        Utils.result(response,result);
    }
}
