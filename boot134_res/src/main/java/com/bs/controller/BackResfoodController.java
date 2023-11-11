package com.bs.controller;

import com.bs.bean.ResFood;
import com.bs.biz.FastDFSBiz;
import com.bs.biz.ResFoodBiz;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("backResfood")
@Slf4j
@Api(tags = "菜品上架管理")
public class BackResfoodController {
    @Autowired
    private FastDFSBiz fastDFSBiz;
    @Autowired
    private ResFoodBiz resFoodBiz;
    @RequestMapping("addNewFood")
    @ApiOperation(value = "菜谱上架")
    public Map<String,Object> addNewFood(HttpServletRequest request, MultipartFile multipartFile){
        Map<String,Object>map=new HashMap<>();
        String fname=request.getParameter("fname");
        String normprice=request.getParameter("normprices");
        String realprice=request.getParameter("realprices");
        String detail=request.getParameter("detail");
        String path=fastDFSBiz.upload(multipartFile);
        ResFood resFood=new ResFood();
        try{
            resFood.setFname(fname);
            resFood.setRealprice(Double.parseDouble(realprice));
            resFood.setMormprice(Double.parseDouble(normprice));
            resFood.setDetail(detail);
            resFood.setFphoto(path);
            resFoodBiz.addFood(resFood);
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            map.put("code",500);
            map.put("msg",e.getCause());
            return map;
        }
        map.put("code",200);
        map.put("data",resFood);
        return map;
    }
}
