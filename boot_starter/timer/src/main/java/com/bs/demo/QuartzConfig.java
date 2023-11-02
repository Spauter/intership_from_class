package com.bs.demo;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class QuartzConfig {
    @Scheduled(cron = "0/5 * * * * ?")
    public void  test(){
        System.out.println("Hello world");
    }
}
