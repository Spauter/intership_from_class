package com.blo.handler;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/session")
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
     * 获取session
     * @param request
     * @return
     */
    @RequestMapping("/get")
    public String getSession(HttpServletRequest request){
        String attributes = (String) request.getSession().getAttribute("attributes");
        return attributes;
    }
}
