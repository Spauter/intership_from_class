package com.test;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

public class BeanFactoryTest {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        /**
         * bean的定义 (class,scope,初始化，销毁)
         */
        AbstractBeanDefinition abstractBeanDefinition =
                BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope("singleton").getBeanDefinition();
        beanFactory.registerBeanDefinition("config", abstractBeanDefinition);
        //由于TA缺少解析@Bean能力，所以没有完整打印出来
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println("________________________________________________________");//分割符
        //给BeanFactory 添加一些常用的后处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println("________________________________________________________");
        /*
            后处理器也是bean，然后要执行这些后处理器，才能够解析configuration的注解，然后再把注解定义的bean加入到容器中
            BeanFactoryPostProcessor提供了修改Bean定义的功能
            后面还有BeanDefinitionRegistryPostProcessor 会提供新增Bean定义的功能
        */
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().forEach(beanFactoryPostProcessor -> {
            //执行Bean工厂处理器
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        });
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }
    }

    @Configuration
    static class Config {
        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }

        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }
    }

    static class Bean1 {
        public Bean1() {
            System.out.println("This is Bean1");
        }
    }

    static class Bean2 {
        public Bean2() {
            System.out.println("This is Bean2");
        }
    }
}
