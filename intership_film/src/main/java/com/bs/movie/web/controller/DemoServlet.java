package com.bs.movie.web.controller;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Company 源辰信息
 * @author navy
 * @date 2024/3/4
 * @Email haijunzhou@hnit.edu.cn
 */
@WebServlet("/demo/*")
public class DemoServlet extends BaseController{
    public void upload(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println(request.getParameter("account"));
        System.out.println(request.getParameter("pwd"));
        System.out.println(request.getParameter("code"));
        System.out.println(request.getParameter("photo"));
        this.send(response, 200, "成功");
    }
}
