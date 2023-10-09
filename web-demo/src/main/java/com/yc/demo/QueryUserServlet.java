package com.yc.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@WebServlet(name = "QueryUserServlet",value = "/queryUser.s",
            loadOnStartup = 1)
public class QueryUserServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("============== QUeryUserServlet init ==============");
    }

    @Override
    public void destroy() {
        System.out.println("============== QUeryUserServlet destroy ==============");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("============== QUeryUserServlet service ==============");
        super.service(req, resp);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final List<Map<String, Object>> list = JdbcTemplate.select("select * from user");
        response.setContentType("text/html;charset=utf-8");
        final PrintWriter out = response.getWriter();
        out.append("<table>")
                .append("<tr><td>账号</d><td>密码</d><td>电话</td><tr>");
        for (Map<String, Object> user : list){
            out.append("<tr><td>" +
                    user.get("name") + "</td><td>" +
                    user.get("pwd") + "</td><td>" +
                    user.get("phone") + "</td><tr");
        }
        out.append("</table>");
        
    }
}
