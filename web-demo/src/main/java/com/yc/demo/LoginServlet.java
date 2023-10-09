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
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String username = req.getParameter("username");
        final String password = req.getParameter("password");
        final String vcode1 = req.getParameter("vcode");
        final String vcode2 = (String) req.getSession().getAttribute("vcode");
        Result result;
        if (vcode1 == null || vcode1.isEmpty()){
            result = new Result(0,"请输入验证码!",username);
        }else if ( vcode1.equalsIgnoreCase(vcode2 ) == false) {
            result = new Result(0,"验证码错误!",username);
        }else if ("yc".equals(username) || "zs".equals(username) || "ls".equals(username) || "ww".equals(username)
              && "123".equals(password)){
            final HttpSession session = req.getSession();
            Map<String, Object> data = new HashMap<>();
            data.put("username",username);
            data.put("requestURI",session.getAttribute("requestURI"));
            session.setAttribute("requestURI",null);
            result = new Result(1,"登录成功",username);
            session.setAttribute("loginedUser",username);
            session.removeAttribute("vcode");
        }else{
            result = new Result(0,"登录失败",null);
        }
        Utils.result(resp, result);
    }
}
