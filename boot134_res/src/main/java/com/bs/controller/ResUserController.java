package com.bs.controller;


import com.bs.Util.StringUtil;
import com.bs.bean.ResUser;
import com.bs.biz.impl.ResUserBizImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("resuser")
@Api(tags = "用户管理")
@Slf4j
public class ResUserController {
    @Autowired
    private ResUserBizImpl resUserBizImpl;

    @RequestMapping(value = "login",method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "用户登录操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名",required = true),
            @ApiImplicitParam(name = "pwd",value = "用户密码",required = true)
    })
    public Map<String,Object>login(ResUser resUser, HttpSession session){
        Map<String,Object> map=new HashMap<>();
        String yzm= (String) session.getAttribute("yzm");
        if(!resUser.getYzm().equals(yzm)){
            map.put("code",-1);
            map.put("msg","验证码错误");
            return map;
        }
        if(new StringUtil().isEmpty(resUser.getUsername())||new StringUtil().isEmpty(resUser.getPwd())){
            map.put("code",-2);
            map.put("msg","请输入用户名和密码");
        }
        try{
            String username= (String) session.getAttribute("username");
            String pwd=(String) session.getAttribute("pwd");
            log.info(username);
            resUser=resUserBizImpl.findByNameAndPwd(username,pwd);
        }catch (Exception e){
            log.error(e.getMessage());
            map.put("code",500);
            map.put("msg",e.getCause());
            return map;
        }
        if(resUser==null){
            map.put("code",404);
            map.put("msg","没有找到相关的数据");
        }else {
            map.put("code",200);
            map.put("msg",resUser);
        }
        return map;
    }







}
