package com.bloducspauter.config;

import com.yc.spring.bbs.bean.User;
import org.springframework.context.annotation.*;

//配置类 类似与bean.xml
@Configuration
@ComponentScan({"com.bloducspauter","com.yc"})
public class IocConfig2 {
    @Bean("myUser")
//    相当于primary="true"
    @Primary
    User user1() {
        User user=new User();
        user.setUname("张三");
        user.setUid(1);
        return  user;
    }

    @Bean("myUser")
    @Primary
//    懒加载
    @Lazy
//    scope="prototype"
    @Scope("prototype")
    User user2(){
        User user=new User();
        user.setUid(2);
        user.setUname("李四");
        return user;
    }

    @Bean("user1")
    @Primary
//    懒加载
    @Lazy
//    scope="prototype"
    @Scope("prototype")
    User user3(){
        User user=new User();
        user.setUid(3);
        user.setUname("王五");
        return user;
    }

    @Bean("user2")
    User user4(){
        User user=new User();
        user.setUid(4);
        user.setUname("赵六");
        return user;
    }
}
