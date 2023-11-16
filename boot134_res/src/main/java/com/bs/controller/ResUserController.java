package com.bs.controller;


import com.bs.bean.ResUser;
import com.bs.biz.impl.ResUserBizImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@RestController
@RequestMapping("resuser")
@Api(tags = "用户管理")
@Slf4j
public class ResUserController {
    @Autowired
    private ResUserBizImpl resUserBizImpl;

    @RequestMapping(value = "login", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "用户登录操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true),
            @ApiImplicitParam(name = "pwd", value = "用户密码", required = true)
    })
    public Map<String, Object> login(HttpSession session, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String username = request.getParameter("username");
        String pwd = DigestUtils.md5DigestAsHex(request.getParameter("pwd").getBytes());
        String yzm = request.getParameter("yzm");
        String email = request.getParameter("email");
        String way = request.getParameter("way");
        log.info(way);
        String verify;
//        if(way==0)
        if (way.equals("register")) {
            verify = session.getAttribute("emailcode") + "";
        } else {
            verify = session.getAttribute("code") + "";

        }
        if (yzm == null) {
            map.put("code", -1);
            map.put("msg", "验证码错误");
            return map;
        }

        if (!verify.equals(yzm)) {
            map.put("code", -1);
            map.put("msg", "验证码错误");
            return map;
        }
        if (Objects.equals(username, "") || Objects.equals(pwd, "")) {
            map.put("code", -2);
            map.put("msg", "请输入用户名和密码");
        }
        ResUser resUser = new ResUser();
        try {
            if (way.equals("register")) {
                resUser.setUsername(username);
                resUser.setPwd(pwd);
                resUser.setEmail(email);
                resUserBizImpl.insert(resUser);
            }
            resUser = resUserBizImpl.findByNameAndPwd(username, pwd);
        } catch (Exception e) {
            log.error(e.getMessage());
            map.put("code", 500);
            map.put("msg", e.getCause());
            return map;
        }
        if (resUser == null) {
            map.put("code", 404);
            map.put("msg", "没有找到相关的数据");
        } else {
            map.put("code", 200);
            map.put("user", resUser);
            resUser.setPwd("");
            session.setAttribute("username",resUser.getUserid());
        }
        return map;
    }


    @ApiOperation(value = "获取已经登录的用户（除非退出）")
    @RequestMapping("isLogin")
    public Map<String, Object> isLogin(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        if (session.getAttribute("username") == null) {
            map.put("code", 404);
        } else {
            map.put("code", 200);
            Integer userid = (Integer) session.getAttribute("username");
            ResUser resUser = resUserBizImpl.findByUID(userid);
            resUser.setPwd("");
            map.put("user", resUser);

        }

        return map;
    }


    @RequestMapping("logout")
    @ApiOperation(value = "清除登录记录")
    public void logout(HttpSession session) {
        session.removeAttribute("username");
    }

}
