package com.bs;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyApp {
    public static void main(String[] args) {
        for(String a:args){
            System.out.println(a);
        }
        SpringApplication.run(MyApp.class);
    }
}
