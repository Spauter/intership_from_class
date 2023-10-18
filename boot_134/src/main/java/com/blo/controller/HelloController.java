package com.blo.controller;

import com.blo.bean.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


@RestController
@ImportResource(locations = {"classpath:spring.xml"})
@Slf4j
public class HelloController {

    @Value("${uname}")
    //使用username会返回"C:\\users" 下的系统用户名 (? _ ?)

    private String name;

    @Value("${driverName}")
    private String driverName;

    @Autowired
    @Qualifier(value = "s")
    Student student;

    @Value("${blo.driverClassName}")
    private String diverName1;

    @Value("${blo.url}")
    private String url1;

    @Value("${blo.username}")
    private String uname;

    @Value("${blo.password}")
    private String password;

    @Autowired
    private Environment environment;

    @Resource
    private ConfigurableEnvironment configurableEnvironment;

    private final List<String>list=new ArrayList<>();

    @RequestMapping("/")
    String home() {
        return "Hello World!"+name+"\t"+driverName+"\n"+student+"  "+driverName+"  "+name;
    }



    @RequestMapping(value = "/services", method = {RequestMethod.POST, RequestMethod.GET})
    public String services(@RequestParam(required = false) Integer type) {
        if (type == null) {
            return "Argument  not be null!";
        }
        if (type == 1) {
            return new Date() + "";
        } else if (type == 2) {
            return new Random().nextInt(10000) + "";

        } else {
            return "No requires";
        }

    }

    @RequestMapping(value = "register", method = {RequestMethod.GET})
    public Student register(@RequestParam String sname) {
        log.info("You visited me!");
        Student s = new Student();
        s.setSid(new Random().nextInt(1000));
        s.setSname(sname);
        return s;
    }

    @RequestMapping(value = "register2", method = {RequestMethod.POST})
    public Student register2(@RequestParam String sname) {
        log.info("You visited me!");
        Student s = new Student();
        s.setSname(sname);
        s.setSid(new Random().nextInt(1000));
        return s;
    }

    @RequestMapping(value="path")
    public List<String> syspath(){

        list.add("C\\users\\"+System.getenv("USERNAME"));
        list.add(environment.getProperty("user.home"));
        list.add(environment.getProperty("JAVA_HOME"));
        /*
         * 可能是没有权限访问C:\\Windows.
         */
        list.add(System.getenv("SYSTEMROOT"));//C:\\Windows
        list.add(configurableEnvironment.getProperty("SYSTEMROOT"));//null
        list.add(environment.getProperty("SYSTEMROOT"));//null
        return list;
    }

    @RequestMapping(value = "SqlTest")
    public List<String>SqlProperties(){
        list.add(diverName1);
        list.add(url1);
        list.add(uname);
        list.add(password);
        return list;
    }

}
