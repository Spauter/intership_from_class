package com.blo;


import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
//Lite模式：false->
@Configuration(proxyBeanMethods = true)//配置被spring托管
@RestController
public class MyConfig {
    @Value("${uname}")
    //使用username会返回"C:\\users" 下的系统用户名 (? _ ?)
    private String name;

    @Value("${driverName}")
    private String driverName;


    @Value("${blo.driverClassName}")
    private String diverName1;

    @Value("${blo.url}")
    private String url1;

    @Value("${blo.username}")
    private String uname;

    @Value("${blo.password}")
    private String password;

//    第三方的类，采用bean 注解托管

    @Bean
    @Profile("test") //只有普spring.profiles.active ="test" 这个bean 才会被spring托管
    public DataSource myDataSourse(){
        DriverManagerDataSource dataSource=new DriverManagerDataSource();
        dataSource.setDriverClassName(diverName1);
        dataSource.setUsername(name);
        dataSource.setPassword(password);
        dataSource.setUrl(url1);
        return  dataSource;
    }

    @Bean
    @Profile("prod")
    public DataSource myDataSourse2(){
        DruidDataSource dataSource=new DruidDataSource();
        dataSource.setDriverClassName(driverName);
        dataSource.setUsername(uname);
        dataSource.setPassword(password);
        dataSource.setUrl(url1);
        return dataSource;
    }
}
