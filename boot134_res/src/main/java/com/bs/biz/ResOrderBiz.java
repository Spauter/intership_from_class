package com.bs.biz;

import com.bs.bean.ResOrder;
import com.bs.bean.ResUser;
import com.bs.bean.CartItem;
import java.util.Set;

public interface ResOrderBiz {
     /**
      * 添加订单
      * @param resOrder
      * @param cartltemSet
      * @param resUser
      * @return
      */
     int Order(ResOrder resOrder, Set<CartItem> cartltemSet, ResUser resUser);
}












































