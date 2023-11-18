package com.bs.biz.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bs.bean.ResOrder;
import com.bs.bean.ResOrderItem;
import com.bs.bean.ResUser;
import com.bs.mapper.ResOrderItemMapper;
import com.bs.biz.ResOrderBiz;
import com.bs.mapper.ResOrderMapper;
import com.bs.bean.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional(
        propagation = Propagation.REQUIRED,
        isolation = Isolation.DEFAULT,
        timeout = 5000,
        rollbackFor = RuntimeException.class
)
@Slf4j
public class ResOrderBizImpl implements ResOrderBiz {
    @Autowired
    private ResOrderMapper resOrderMapper;
    @Autowired
    private ResOrderItemMapper resOrderItemMapper;

    @Override
    public int Order(ResOrder resOrder, Set<CartItem> cartltemSet, ResUser resUser){
        resOrder.setUserid(resUser.getUserid());
        this.resOrderMapper.insert(resOrder);
        for (CartItem c:cartltemSet){
            ResOrderItem resOrderItem=new ResOrderItem();
            resOrderItem.setRoid(resOrder.getRoid());
            resOrderItem.setFid(c.getResFood().getFid());
            resOrderItem.setDealprice(c.getResFood().getRealprice()*c.getNum());
            resOrderItem.setNum(c.getNum());
            this.resOrderItemMapper.insert(resOrderItem);
        }
        return 0;
    }

    @Override
    public List<ResOrder> allOrders() {
        QueryWrapper<ResOrder>queryWrapper=new QueryWrapper<>();
        return resOrderMapper.selectList(queryWrapper);
    }

}
