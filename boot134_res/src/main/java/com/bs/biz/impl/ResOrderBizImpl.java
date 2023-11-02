package com.bs.biz.impl;

import com.bs.bean.ResOrder;
import com.bs.bean.ResOrderItem;
import com.bs.bean.ResUser;
import com.bs.mapper.ResOrderItemMapper;
import com.bs.biz.ResOrderBiz;
import com.bs.mapper.ResOrderMapper;
import com.bs.web.mode.Cartltem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    public int Order(ResOrder resOrder, Set<Cartltem> cartltemSet, ResUser resUser){
        resOrder.setUserid(resUser.getUserid());
        this.resOrderMapper.insertOrder(resOrder);
        for (Cartltem c:cartltemSet){
            ResOrderItem resOrderItem=new ResOrderItem();
            resOrderItem.setRoid(resOrder.getRoid());
            resOrderItem.setFid(c.getFood().getFid());
            resOrderItem.setDealprice(c.getFood().getRealprice());
            resOrderItem.setNum(c.getNum());
            this.resOrderItemMapper.insert(resOrderItem);
        }
        return 0;
    }

}
