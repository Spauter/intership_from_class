package com.bs.biz.impl;

import com.bs.bean.ResOrder;
import com.bs.biz.ResOrderItemBiz;
import com.bs.mapper.ResOrderItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResOrderItemBizImpl implements ResOrderItemBiz{
    @Autowired
    ResOrderItemMapper orderItemMapper;
    @Override
    public int insert(ResOrder order) {
        return 0;
    }
}
