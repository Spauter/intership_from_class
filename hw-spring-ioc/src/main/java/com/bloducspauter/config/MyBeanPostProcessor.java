package com.bloducspauter.config;

import com.yc.spring.bbs.bean.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
// 对象创建后   初始化对象之前
    public Object postProcessBeforeInitialization(Object o, String id) throws BeansException {
//        当前方法不做处理，直接返回
        return o;
    }
    @Override
//    对象创建后 初始化对象后
    public Object postProcessAfterInitialization(Object bean, String id) throws BeansException {
        if (bean instanceof User) {
            ((User) bean).setUid(1000);
        }
        return bean;
    }
}
