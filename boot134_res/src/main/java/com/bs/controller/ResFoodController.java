package com.bs.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.bean.ResFood;
import com.bs.biz.FastDFSBiz;
import com.bs.biz.ResFoodBiz;
import com.bs.biz.impl.ResFoodBizImpl;
import com.bs.config.RedisKeys;
import com.bs.mapper.ResFoodMapper;
import com.bs.web.mode.MypageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apiguardian.api.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("resfood")
@Slf4j
@Api(tags = "菜谱管理")
public class ResFoodController {
    @Autowired
    private ResFoodBiz resFoodBiz;
    @Autowired
    private RedisTemplate redisTemplate;


    @GetMapping("findById/{fid}")
    @ApiOperation(value = "根据菜谱编号进行操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid", required = true, value = "菜谱号")
    })
    public Map<String, Object> findById(@PathVariable Integer fid) {
        Map<String, Object> map = new HashMap<>();
        ResFood resFood;
        try {
            resFood = this.resFoodBiz.findById(fid);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("msg", e.getCause());
            return map;
        }
        resFood.setFphoto("localhost:8888/"+resFood.getFphoto());
        map.put("code", 200);
        map.put("msg", resFood);
        return map;
    }



    @RequestMapping(value = "findByPage",method = {RequestMethod.GET})
    @ApiOperation(value = "分页查询操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageno", value = "页号", required = true),
            @ApiImplicitParam(name = "pagesize", value = "总页数", required = true),
            @ApiImplicitParam(name = "sort", value = "排序方式", required = true),
            @ApiImplicitParam(name = "sortby", value = "被排序列", required = true)
    })
    public Map<String, Object> findByPAge(@RequestParam int pageno, @RequestParam int pagesize, @RequestParam String sortby, @RequestParam String sort) {
        Map<String, Object> map = new HashMap<>();
        Page<ResFood> page;
        try {
            page = this.resFoodBiz.findByPage(pageno, pagesize, sortby, sort);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("msg", e.getCause());
            return map;
        }
        page.getRecords();
        MypageBean mypageBean = new MypageBean();
        mypageBean.setPageno(pageno);
        mypageBean.setPagesize(pagesize);
        mypageBean.setSort(sort);
        mypageBean.setSortby(sortby);
        mypageBean.setDataset(page.getRecords());
        mypageBean.setTotal(page.getTotal());
        long totalPAges = page.getTotal() % mypageBean.getPagesize() == 0 ? page.getTotal() / page.getSize() : page.getTotal() / page.getSize() + 1;
        mypageBean.setTotalpages((int) totalPAges);
        if (mypageBean.getPageno() < 1) {
            mypageBean.setPre(1);
        } else {
            mypageBean.setPre(mypageBean.getPageno() - 1);
        }
        if (mypageBean.getPageno() == totalPAges) {
            mypageBean.setNext((int) totalPAges);
        } else {
            mypageBean.setNext(mypageBean.getPageno() + 1);
            map.put("code",200);
            map.put("data", mypageBean);
            return map;
        }
        return null;
    }
    @ApiOperation(value = "查看点击人数")
    @GetMapping("detailCountAdd")
    public Map<String, Object> detialCountAdd(Integer fid){
        Map<String, Object>map=new HashMap<>();
        Long count=1L;
        if(redisTemplate.hasKey(RedisKeys.RESFOOD_DETAIL_COUNT_FID+fid)==false){
            redisTemplate.opsForValue().set(RedisKeys.RESFOOD_DETAIL_COUNT_FID+fid,1);
        }else {
            count = redisTemplate.opsForValue().increment(RedisKeys.RESFOOD_DETAIL_COUNT_FID + fid);
        }
        map.put("code",200);
        map.put("data",count);
        return map;
    }






}
