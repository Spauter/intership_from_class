package com.bs.controller;

import com.bs.bean.ResFood;
import com.bs.biz.FastDFSBiz;
import com.bs.biz.ResFoodBiz;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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

    @ApiOperation(value = "查看所有菜品")
    @GetMapping("findAll")
    public Map<String, Object> findAll() {
        Map<String, Object> map = new HashMap<>();
        List<ResFood> list;
        try {
            list = resFoodBiz.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("msg", e.getCause());
            return map;
        }
        for (ResFood resFood : list) {
            resFood.setFphoto("http://localhost:8888/" + resFood.getFphoto());
        }
        map.put("code", 200);
        map.put("msg", list);
        return map;
    }

    @RequestMapping(value = "addNewFood")
    @ApiOperation(value = "菜谱上架")
    public Map<String,Object> addNewFood(@RequestParam String fname,
                                         @RequestParam Double normprice,
                                         @RequestParam Double realprice,
                                         @RequestParam String detail,
                                         @RequestParam MultipartFile fphoto){
        Map<String,Object>map=new HashMap<>();
        ResFood resFood=new ResFood();
        try{
            String path=this.fastDFSBiz.upload(fphoto);
            log.info("fname:"+fname);
            resFood.setFname(fname);
            log.info("real:"+realprice);
            resFood.setRealprice(realprice);
            log.info("nor:"+normprice);
            resFood.setMormprice(normprice);
            log.info("detail:"+detail);
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

    @RequestMapping("del")
    @ApiOperation(value = "删除菜谱")
    public Map<String,Object>delFood(Integer fid){
        Map<String,Object>map=new HashMap<>();
        try{
            resFoodBiz.delete(fid);
            map.put("code",200);
            map.put("msg","删除成功");
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            map.put("code",500);
            map.put("msg","删除失败");
        }
        return map;
    }
}
