package com.bs.biz.impl;

import com.bs.bean.ResOrder;
import com.bs.biz.ResOrderItemBiz;
import com.bs.mapper.ResOrderItemMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ResOrderItemBizImpl implements ResOrderItemBiz{
    @Autowired
    ResOrderItemMapper orderItemMapper;
    @Override
    public int insert(ResOrder order) {
        return 0;
    }
}
