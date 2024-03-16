package com.yc.spring;

import com.yc.spring.bbs.bean.User;
import org.springframework.context.annotation.*;

//配置类 类似与bean.xml
@Configuration
@ComponentScan("com.yc")
//开启Aspect自动代理
@EnableAspectJAutoProxy
public class IocConfig {
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
}
