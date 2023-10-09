package com.yc.demo.d0730;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebServlet({"*.s","*.do"})
public class EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("========== EncodingFilter init ==========");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("========== doFilter before ==========");
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getHeader("Accept").startsWith("text/html")){
            response.setContentType("text/html;charset=utf-8");
        }else if (req.getHeader("Accept").startsWith("application/json")
                || req.getHeader("Accept").startsWith("*/*")){
            response.setContentType("appllication/json;charset=utf-8");
        }
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        chain.doFilter(request, response);
        System.out.println("========== doFilter after ==========");
    }

    @Override
    public void destroy() {
        System.out.println("========== EncodingFilter destroy ==========");
    }
}
