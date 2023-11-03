package com.bs.biz;

import com.bs.bean.ResOrder;
import com.bs.bean.ResUser;
import com.bs.web.mode.Cartltem;

import java.util.Set;

public interface ResOrderBiz {
     /**
      * 添加订单
      * @param resOrder
      * @param cartltemSet
      * @param resUser
      * @return
      */
     int Order(ResOrder resOrder, Set<Cartltem> cartltemSet, ResUser resUser);
}












































