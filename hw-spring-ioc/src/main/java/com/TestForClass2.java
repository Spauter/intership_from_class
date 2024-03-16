package com;
import com.bloducspauter.config.IocConfig;
import com.yc.spring.bbs.bean.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.io.IOException;

public class TestForClass2 {
    @Test
    public void test3() {
        ApplicationContext context= new AnnotationConfigApplicationContext(IocConfig.class);
        User myUser= (User) context.getBean("myUser");
        int uid= myUser.getUid();
        Assert.assertEquals(1000,uid);
    }


}
