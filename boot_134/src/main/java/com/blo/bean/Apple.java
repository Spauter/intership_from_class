package com.blo.bean;

import com.blo.SystemCondition;
import org.springframework.context.annotation.Conditional;

@Conditional(SystemCondition.class)
public class Apple {
    public Apple(){
        System.out.println("这是一个第三方类");
    }
}
