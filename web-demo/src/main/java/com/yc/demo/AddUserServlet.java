package com.yc.demo;

import com.google.gson.Gson;
import com.yc.demo.vo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AddUserServlet", value = "/addUser.s")
public class AddUserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String username = request.getParameter("username");
        final String password = request.getParameter("password");
        final String phone = request.getParameter("phone");

        response.setContentType("application/json;charset=utf-8");
        final PrintWriter out = response.getWriter();
        Gson gson = new Gson();

        Result result = null;
        if (username == null || username.trim().isEmpty()){
            result = new Result(0,"请求输入用户名",null);
            out.println(gson.toJson(result));
            return;
        }
        if (password == null || password.trim().isEmpty()){
            result = new Result(0,"请求输入密码",null);
            out.println(gson.toJson(result));
            return;
        }
        final List<Map<String, Object>> list = JdbcTemplate.select(
                "select count(*) cnt from user where name=?", username);
        final int cnt = Integer.parseInt(list.get(0).get("cnt") + "");
        if (cnt>0){
            result = new Result(0,"该用户名已经被注册!",null);
            out.println(gson.toJson(result));
            return;
        }

        JdbcTemplate.update("insert into user (name,pwd,phone) values(?,?,?)",
                username,password,phone);
        result = new Result(1,"用户注册成功!",null);
        out.println(gson.toJson(result));
        return;
    }
}
