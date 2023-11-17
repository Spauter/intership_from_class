package com.bs.controller;

import com.bs.bean.ResOrder;
import com.bs.biz.ResOrderBiz;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("backorder")
@Slf4j
@Api(tags = "订单管理")
public class BackResOrderController {
    @Autowired
    private ResOrderBiz resOrderBiz;


    @RequestMapping("allOrder")
    @ApiOperation(value = "全部订单")
    public Map<String,Object>allOrder(){
        Map<String,Object>map=new HashMap<>();
        try{
            List<ResOrder> resOrders = resOrderBiz.allOrders();
            map.put("code",200);
            map.put("data",resOrders);
            return map;

        }catch (Exception e){
            e.printStackTrace();
            map.put("code",500);
            map.put("msg",e.getCause());
            return map;
        }
    }
}
