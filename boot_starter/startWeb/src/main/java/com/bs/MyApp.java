package com.bs;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyApp {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(MyApp.class);
        springApplication.setAllowBeanDefinitionOverriding(true);
        springApplication.run(args);
    }
}
