package com.yc.demo.d0730;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.LinkedHashMap;
import java.util.Map;

@WebListener
public class OnlineListener implements
        ServletContextListener,
        HttpSessionListener,
        HttpSessionAttributeListener
{

    public OnlineListener(){

    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("============ contextInitialized ============");
        Map<String, Long> onlineMap = new LinkedHashMap<>();
        final ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("onlineMap",onlineMap);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("============ contextInitialized ============");
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("============ sessionCreated ============");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("============ sessionDestroyed ============");
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        System.out.println("============ attributeAdded ============");
        if ("loginedUser".equals(sbe.getName())){
            final ServletContext servletContext = sbe.getSession().getServletContext();
            Map<String, Long> onlineMap = (Map<String, Long>) servletContext.getAttribute("onlineMap");
            onlineMap.put((String) sbe.getValue(), System.currentTimeMillis());
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        System.out.println("============ attributeRemoved ============");
        if ("loginedUser".equals(sbe.getName())){
            final ServletContext servletContext = sbe.getSession().getServletContext();
            Map<String, Long> onlineMap = (Map<String, Long>) servletContext.getAttribute("onlineMap");
            onlineMap.remove((String) sbe.getValue());
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("============ attributeReplaced ============");
    }
}
