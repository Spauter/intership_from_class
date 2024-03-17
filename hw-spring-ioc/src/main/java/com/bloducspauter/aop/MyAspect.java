package com.bloducspauter.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
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

    @AfterReturning(value = "myPoint()",returning ="res" )
    public void afterOfRunning(JoinPoint joinPoint,Object res) {
        System.out.println("返回拦截\t" + joinPoint.getSignature()+"\tres:"+res);
    }

    //    切点方法
    @Pointcut("execution(* com.yc..dao.UserDaoImpl.*(..))")
    public void myPoint() {
    }

    @AfterThrowing(value = "myPoint()",throwing = "e")
    public void afterThrowing(Throwable e) {
        System.out.println("异常拦截");
        System.out.println(e.getMessage());
    }
//    环绕增强
//    返回值：是业务方法的返回值
//    连接到对象负责执行方法
    @Around("myPoint()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        System.out.println("环绕增强=>前置增强");
        Object ret = new Object();
        try {
            long time=System.currentTimeMillis();
            ret= proceedingJoinPoint.proceed();
            time=System.currentTimeMillis()-time;
            System.out.println("环绕增强=>返回增强,业务花费事件为:"+time);
            return ret;
        } catch (Throwable e) {
            System.out.println("环绕增强=>异常增强");
        }finally {
            System.out.println("环绕增强=>后置增强");
        }
        return null;
    }
}

