package com.test;

import com.bloducspauter.config.IocConfig2;
import com.bloducspauter.event.UserRegisteredEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Locale;

public class SpringAppTest {
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(IocConfig2.class);
        //获取资源 在冒号里面加*（classPath*:)就会从jar包里面找
        Resource[] resources=context.getResources("classpath*:META-INF/spring.factories");
        for (Resource resource : resources) {
            System.out.println(resource);
        }
        //获取系统环境变量
        System.out.println(context.getEnvironment().getProperty("JAVA_HOME"));
        System.out.println(context.getEnvironment().getProperty("GRADLE_HOME"));
        System.out.println(context.getEnvironment().getProperty("os.name"));
        //不同语言翻译结果
//        System.out.println(context.getMessage("hi", null, Locale.CHINA));
//        System.out.println(context.getMessage("hi", null, Locale.ENGLISH));

        //发事件
        context.publishEvent(new UserRegisteredEvent(context));

    }
}
