package com.yc.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
//切面类=切点(方法+注解属性)+增强(方法)
public class MyAspect {
//    前置增强
    @Before("execution(* com.yc..dao.UserDaoImpl.*(..))")
    public void before() {
        System.out.println("前置拦截");
    }

}
