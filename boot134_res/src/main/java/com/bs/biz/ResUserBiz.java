package com.bs.biz;


import com.bs.bean.ResUser;
import com.bs.excption.BizException;

public interface ResUserBiz {

    ResUser findByNameAndPwd(String username,String pwd);


    ResUser findByUID(Integer userid);

    int insert(ResUser resUser) throws BizException;





}
