package com.bs.biz;


import com.bs.bean.ResUser;

public interface ResUserBiz {

    ResUser findByNameAndPwd(String username,String pwd);


    ResUser findByUID(Integer userid);

    int insert(ResUser resUser);





}
