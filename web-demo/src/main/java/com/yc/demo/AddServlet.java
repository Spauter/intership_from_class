package com.yc.demo;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@WebServlet("/add.s")
public class AddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        resp.setContentType("text/html;charset=utf-8");
        final PrintWriter out = resp.getWriter();

        final String a = req.getParameter("a");
        final String b = req.getParameter("b");
        if (a == null || a.matches("\\d+") == false){
            out.append("<h3 style='color:red'>")
                    .append("请提交正确的a参数")
                    .append("</h3>");
            return;
        }

        if (b == null || b.matches("\\d+") == false){
            out.append("<h3 style='color:red'>")
                    .append("请提交正确的b参数")
                    .append("</h3>");
            return;
        }

        int ai = Integer.parseInt(a);
        int bi = Integer.parseInt(b);
        int c = ai + bi;
        String s = String.format("%s +%s = %d", a, b, c);
        out.println(s);

    }
}
