package com.test;


import com.bloducspauter.config.IocConfig2;
import com.bloducspauter.event.UserRegisteredEvent;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.yc.spring.bbs.bean.User;
import java.io.IOException;

public class TestForClass {
    @Test
    public void test1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        User user1 = (User) context.getBean("user1");
        User user2 = (User) context.getBean("user2");
        User user3 = (User) context.getBean("user3");
        User user4 = (User) context.getBean("user3");
        System.out.println("user:" + user1.toString());
        System.out.println("u12:" + (user1 == user2));
        System.out.println("u13:" + (user1 == user3));
        System.out.println("u43:" + (user4 == user3));
    }

    @Test
    public void test2() {
        ApplicationContext context=new AnnotationConfigApplicationContext(IocConfig2.class);
        User user1 = (User) context.getBean("user1");
        User user2 = (User) context.getBean("user2");
        System.out.println("user:" + user1.toString());
        System.out.println("user:" + (user2.toString()));
        context.publishEvent(new UserRegisteredEvent(context));
    }
    @Test
    public void test3() throws IOException {
        ApplicationContext context= new AnnotationConfigApplicationContext(IocConfig2.class);
        User myUser= (User) context.getBean("myUser");
        Assert.assertNull(myUser);
    }


}
