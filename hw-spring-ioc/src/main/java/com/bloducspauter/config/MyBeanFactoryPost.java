package com.bloducspauter.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

@Component
public class MyBeanFactoryPost implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
//        动态创建bean定义
        DefaultListableBeanFactory defaultListableBeanFactory= (DefaultListableBeanFactory) configurableListableBeanFactory;
        BeanDefinition definition= new RootBeanDefinition();
        definition.setBeanClassName("com.yc.spring.bbs.bean.User");
        definition.setPrimary(true);
//        设置
        definition.setScope("prototype");
//        注册bean
        defaultListableBeanFactory.registerBeanDefinition("myUser",definition);


    }
}
