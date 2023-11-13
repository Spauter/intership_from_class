package com.bs.biz.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.bean.ResFood;
import com.bs.biz.ResFoodBiz;
import com.bs.config.RedisKeys;
import com.bs.mapper.ResFoodMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@Slf4j

public class ResFoodBizImpl implements ResFoodBiz {
    @Autowired
    private ResFoodMapper resFoodMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    private String nginxAddress;

    @Override
    public List<ResFood> findAll() {
        QueryWrapper<ResFood> wrapper= new QueryWrapper<>();
        return resFoodMapper.selectList(wrapper);
    }

    @Override
    public ResFood findById(Integer fid) {
        QueryWrapper<ResFood> wrapper= new QueryWrapper<>();
        wrapper.eq("fid",fid);
       return resFoodMapper.selectOne(wrapper);
    }

    @Override
    public Page<ResFood> findByPage(int pageno, int pagesize, String sortBy, String sort) {
        QueryWrapper<ResFood>wrapper= new QueryWrapper<>();
       if ("asc".equalsIgnoreCase(sort)){
           wrapper.orderByAsc(sortBy);
       }else {
            wrapper.orderByDesc(sortBy);
       }
       Page<ResFood>page=new Page<>(pageno,pagesize);
       Page<ResFood>page1=this.resFoodMapper.selectPage(page,wrapper);
       List<ResFood>list=page1.getRecords();
       List<String>keys=new ArrayList<>();
       for(ResFood resFood:list){
           keys.add(RedisKeys.RESFOOD_DETAIL_COUNT_FID+resFood.getFid());
       }
        List allFoodDetailCOuntValues=redisTemplate.opsForValue().multiGet(keys);
        for(int i=0;i<list.size();i++){
            String a=  allFoodDetailCOuntValues.get(i)+"";
            log.info("allFoodDetailCOuntValues:"+allFoodDetailCOuntValues.get(i));

            list.get(i).setDetail_count(Long.parseLong(a.equals("null") ?1+"":a));
            list.get(i).setFphoto("http://localhost:8888/"+list.get(i).getFphoto());
        }
       log.info(String.valueOf(page1.getSize()));
       log.info("总记录="+page1.getTotal());
       log.info("总页数="+page1.getPages());
       log.info("当前页面"+page1.getCurrent());
       return page1;
    }



    @Transactional(
            propagation = Propagation.SUPPORTS,
            isolation = Isolation.DEFAULT,
            timeout = 5000,
            readOnly = false,
            noRollbackFor = RuntimeException.class
    )
    @Override
    public int addFood(ResFood resFood) {
       this.resFoodMapper.insert(resFood);
       return resFood.getFid();
    }
}
