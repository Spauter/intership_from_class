package com.bloducspauter.config;

import lombok.Data;

import java.lang.reflect.Method;
@Data
public class BeanDefinition {
    private String className;
    private boolean primary;
    private boolean lazyInit;
    private String scope;
    private Method beanMethodName;
    private Object beanFactory;
}
