package com.yc;
import com.bloducspauter.config.IocConfig2;
import com.yc.spring.bbs.bean.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestForClass2 {
    @Test
    public void test3() {
        ApplicationContext context= new AnnotationConfigApplicationContext(IocConfig2.class);
        User myUser= (User) context.getBean("myUser");
        int uid= myUser.getUid();
        Assert.assertEquals(1000,uid);
    }


}
