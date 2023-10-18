package com.blo;

import com.blo.bean.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = {"classpath:db.properties"})
@EnableConfigurationProperties(Product.class)
public class Myapp {
    public static void main(String[] args) {
        for (String s:args){
            System.out.println(s);
        }
        SpringApplication.run(Myapp.class);
    }



}
