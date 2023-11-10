package com.bs.controller;

import com.bs.bean.ResAdmin;
import com.bs.biz.ResAdminiBiz;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("resadmin")
@Slf4j
@Api(tags = "管理员登录")
public class ResAdminController {
    @Autowired
    private ResAdminiBiz resAdminiBiz;

    @RequestMapping("login")
    @ApiOperation(value = "管理员登录")
   public Map<String,Object>login(HttpServletRequest request){
       Map<String,Object>map=new HashMap<>();
       log.info("you visited me");
       String raname=request.getParameter("raname");
       log.info(raname);
       String rapwd=request.getParameter("rapwd");
        log.info(rapwd);
        String password=DigestUtils.md5DigestAsHex(rapwd.getBytes());
       try{
            ResAdmin resadmin=resAdminiBiz.findByIDAndPwd(raname,password );
            if(resadmin==null){
                map.put("code",404);
                map.put("msg","用户名或者密码错误");
                return map;
            }
            map.put("code",200);
            resadmin.setRapwd("");
            map.put("data",resadmin);
            return map;
       }catch (Exception e){
           map.put("ocde",500);
           map.put("msg",e.getCause());
           return map;
       }
   }
}
