package com.bs.attrbutesAutoConfiguration.demo;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BsAttrbutesFunction {
    @Autowired
    private AttributesProperties attributesProperties;

    public Map<String,Object> setSession(HttpServletRequest request,String attributes){
        request.getSession().setAttribute("attributes",attributes);
        Map<String,Object> map = new HashMap<>();
        map.put("SessionID:",request.getSession().getId());
        return map;
    }
    public String getSession(HttpServletRequest request){
        String attributes = (String) request.getSession().getAttribute("attributes");
        attributesProperties.setAttributes(attributes);
        return attributesProperties.getAttributes();
    }
}
