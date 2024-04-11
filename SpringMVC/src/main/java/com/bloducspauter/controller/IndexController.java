package com.bloducspauter.controller;

import com.bloducspauter.bean.Person;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class IndexController {
    @RequestMapping("hello")
    public String hello(String name) {
        return "Hello "+name;
    }

    @RequestMapping("toPage")
    ModelAndView toPage(String page){
        page = page == null ? "A":page;
        ModelAndView mav = new ModelAndView();
        //  redirect 响应重定向,  forward 请求转发
        mav.setViewName("redirect:" + page + ".html");
        return mav;
    }

    @RequestMapping(path = {"test","testA","testB"})
    String test(){
        return "test ";
    }

    @RequestMapping(path="method",  // 405
            method = {RequestMethod.POST,RequestMethod.HEAD})
    String method(){
        return "method ";
    }

    @PostMapping("post")
    String methodPost(){
        return "method ";
    }

    @GetMapping(path = "myname" ,
            params = "name!=john",        // 400
            headers = "Host=localhost")   // 404
    String myname(String name){
        return "myname " + name;
    }

    @PostMapping(path = "upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, // 415
            produces = MediaType.TEXT_HTML_VALUE+";charset=utf-8")
    String uplaod(){
        return "文件上传";
    }

    //////////////////////////////////////////////////
    @RequestMapping("add")
    int add(@RequestParam("x") int a,
            @RequestParam(name="y",
                    defaultValue = "100",
                    required = false) int b){
        return a + b;
    }

    @RequestMapping("person1")
    Person person1(Person p){
        return p;
    }

    @RequestMapping("person2")
    Person person2(@RequestBody Person p){
        return p;
    }

    @RequestMapping("servlet")
    String servlet(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session){
        return request + "  =  " +
                response + "  =  " +
                session + "  =  ";
    }
}
