package com.bloducspauter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

@SpringBootApplication
@EnableSpringHttpSession
public class MyApp {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(MyApp.class);
        springApplication.setAllowBeanDefinitionOverriding(true);
        springApplication.run(args);
    }
}
