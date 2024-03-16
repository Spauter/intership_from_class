package com.yc.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
//切面类=切点(方法+注解属性)+增强(方法)
public class MyAspect {
    //    前置增强
    @Before("myPoint()")
    public void before() {
        System.out.println("前置拦截");
    }

    @Before("myPoint()")
//    spring会以接口注入的方式将连接点对象注入进来
    public void beforeOfRunning(JoinPoint joinPoint) {
//        参数
        System.out.println("前置拦截--带JoinPoint");
        System.out.println("Arrays.toString(joinPoint.getArgs():" + Arrays.toString(joinPoint.getArgs()));
        System.out.println(joinPoint.getSignature());

    }

    @After("myPoint()")
    public void after(JoinPoint joinPoint) {
        System.out.println("后置拦截\t" + joinPoint.getSignature());
    }

    @AfterReturning("myPoint()")
    public void afterOfRunning(JoinPoint joinPoint) {
        System.out.println("返回拦截\t" + joinPoint.getSignature());
    }

    //    切断方法
    @Pointcut("execution(* com.yc..dao.UserDaoImpl.*(..))")
    public void myPoint() {
    }
}

