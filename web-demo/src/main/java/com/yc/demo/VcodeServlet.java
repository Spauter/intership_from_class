package com.yc.demo;

import com.yc.demo.util.VerifyCodeUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "VcodeServlet",value = "/vcode.s")
public class VcodeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final HttpSession session = request.getSession();
        final String vcode = VerifyCodeUtils.outputImage(response);
        session.setAttribute("vcode",vcode);
    }
}