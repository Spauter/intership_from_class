package com.blo.handler;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SessionController {

    /**
     * 设置session
     * @param request
     * @param attributes
     * @return
     */
    @RequestMapping("/set")
    public Map<String,Object> setSession(HttpServletRequest request, @RequestParam("attributes")String attributes){
        request.getSession().setAttribute("attributes",attributes);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("SessionID:",request.getSession().getId());
        return map;
    }
    /**
     * 设置session的另一种
     * @return
     */
    @RequestMapping("/set1")
    public Map<String,Object> setSession(HttpServletRequest request,HttpSession session){
        String attributes=request.getParameter("attributes");
        Map<String,Object> map = new HashMap<String,Object>();
        session.setAttribute("attributes",attributes);
        map.put("SessionID:",session.getId());
        return map;
    }

    /**
     * 获取session
     * @param request
     * @return
     */
    @RequestMapping("/get")
    public String getSession(HttpServletRequest request){
        String attributes = (String) request.getSession().getAttribute("attributes");
        return attributes;
    }

    /**
     * 另一种
     * @param session
     * @return
     */
    @RequestMapping("/get1")
    public String getSession(HttpSession session) {
        return (String) session.getAttribute("attributes");
    }
}
