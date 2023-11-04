package com.bs.controller;

import com.bs.bean.CartItem;
import com.bs.bean.ResFood;
import com.bs.biz.ResFoodBiz;
import com.bs.biz.ResOrderBiz;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("resOrder")
@Slf4j
@Api(tags = "购物车管理")
public class ResOrderController {
    @Autowired
    private ResOrderBiz resOrderBiz;
    @Autowired
    private ResFoodBiz resFoodBiz;

    @RequestMapping("clearAll")
    @ApiOperation("清空购物车")
    public Map<String, Object> clearAll(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        session.removeAttribute("cart");
        map.put("code", 1);
        return map;
    }

    @RequestMapping("addCart")
    @ApiOperation("添加购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "菜谱id", value = "fid", required = true),
            @ApiImplicitParam(name = "购物车数量", value = "num", required = true)
    })
    public Map<String, Object> addCart(@RequestParam Integer fid, @RequestParam Integer num, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        ResFood resFood = resFoodBiz.findById(fid);
        if (resFood == null) {
            map.put("code", 404);
            map.put("msg", "查无数据");
        }
        Map<Integer, CartItem> cartItemMap=new HashMap<>();
        if(session.getAttribute("cart")!=null) {
            cartItemMap=(Map<Integer, CartItem>) session.getAttribute("cart");
        }
        CartItem cartltem = new CartItem();
        cartltem.setResFood(resFood);
        cartltem.setNum(num);
        assert resFood != null;
        cartltem.setSmallCount(resFood.getRealprice() * num);
        if (cartItemMap.containsKey(fid)) {
            cartltem = cartItemMap.get(fid);
            cartltem.setNum(cartltem.getNum() + num);
            cartItemMap.put(fid, cartltem);
        } else {
            cartltem.setNum(num);
            cartItemMap.put(fid, cartltem);
        }
        if (cartltem.getNum() <= 0) {
            cartItemMap.remove(fid);
        }
        session.setAttribute("cart", cartItemMap);
        map.put("code", 1);
        map.put("obj", cartItemMap);
        return map;
    }

    @RequestMapping("getCartInfo")
    @ApiOperation("获取购物车")
    public Map<String, Object> getCartInfo(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        Map<Integer, CartItem> cartItemMap = (Map<Integer, CartItem>) session.getAttribute("cart");
        if (session.getAttribute("cart") == null || cartItemMap.size() <= 0) {
            map.put("code", 0);
            map.put("obj", null);
            return map;
        } else {
            map.put("code", 1);
            map.put("obj", cartItemMap.values());
            return map;
        }
    }
}
