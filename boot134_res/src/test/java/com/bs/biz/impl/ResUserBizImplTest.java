package com.bs.biz.impl;

import com.bs.ResApp;
import com.bs.bean.ResUser;
import com.bs.biz.ResUserBiz;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ResApp.class})
@Slf4j
public class ResUserBizImplTest {
@Autowired
private ResUserBiz resUserBiz;
    @Test
  public   void findByNameAndPwd() {
        ResUser resUser=resUserBiz.findByNameAndPwd("a","0cc175b9c0f1b6a831c399e269772661");
        Assert.assertNotNull(resUser);
    }

    @Test
    public void findByUID() {
        ResUser resUser=resUserBiz.findByUID(2);
        Assert.assertEquals("b@163.com",resUser.getEmail());
    }
}