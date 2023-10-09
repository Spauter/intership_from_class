package com.yc.demo.d0730;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/page/cart.html","/page/myinfo.html",
        "/page/myorder.html","/user/test/a.html"})
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest requst, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) requst;
        HttpServletResponse resp = (HttpServletResponse) response;
        final HttpSession session = req.getSession();
        if (session.getAttribute("loginedUser") == null){
            final String requsetURI = req.getRequestURI();
            session.setAttribute("requestURI", requsetURI);
            final String contextPath = req.getContextPath();
            resp.sendRedirect(contextPath +"login.html?code=1");
            return;
        }
        chain.doFilter(requst, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
