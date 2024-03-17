package com.bloducspauter.config;

import com.yc.spring.IocConfig;
import com.yc.spring.bbs.bean.User;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAnnotationConfigApplicationContext {

    Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    protected Map<String, Object> singletonObjects = new HashMap<>();


    public static void main(String[] args) {
        MyAnnotationConfigApplicationContext cxt =
                new MyAnnotationConfigApplicationContext(IocConfig2.class);
//        User myUser = (User) cxt.getBean("myUser");
//        System.out.println("myUser = " + myUser);

        cxt.singletonObjects.forEach((id, bean) -> {
            System.out.println("{id:"+id+",bean:"+bean+"}");
        });
    }

    public MyAnnotationConfigApplicationContext(Class<?> configClass) {
        // 读取配置
        Annotation configClassAnnotation = configClass.getAnnotation(Configuration.class);
        if (configClassAnnotation == null) {
            throw new RuntimeException("不是配置类:" + configClass);
        }
        Object config = null;
        try {
            config = configClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        // 1. Bean方法 => BeanDefinition
        for (Method method : configClass.getDeclaredMethods()) {
            if (method.getAnnotation(Bean.class) != null) {
                // bean 方法
                BeanDefinition definition = getBeanDefinition(method, config);
                beanDefinitionMap.put(definition.getId(), definition);
                // 其他属性-->省略
            }
        }
        // 2. 扫描组件 => BeanDefinition
        ComponentScan componentScan = configClass.getAnnotation(ComponentScan.class);
        if (componentScan != null) {
            String[] packagePaths = componentScan.value();
            // 获取工程类根目录
            URL url1 = configClass.getClassLoader().getResource("");
            File projectRoot = new File(URLDecoder.decode(url1.getPath()));
            // 組件類掃描結果集合
            List<File> list = new ArrayList<>();
            for (String packagePath : packagePaths) {
                // 使用类加载器获取类路径结构
                ClassLoader classLoader = configClass.getClassLoader();
                URL url = classLoader.getResource(packagePath.replaceAll("\\.", "/"));
                String rootPackagePath = url.getPath();
                rootPackagePath = URLDecoder.decode(rootPackagePath, StandardCharsets.UTF_8);
                // 扫描根包文件目录对象
                File rootPath = new File(rootPackagePath);
                // 递归扫描
                scanComponent(rootPath, list, projectRoot);
                list.forEach(System.out::println);
            }
            for (File file : list) {
                String classPath = file.getPath().replace(projectRoot.getPath(), "");
                classPath = classPath.replace(".class", "");
                classPath = classPath.replaceAll("\\\\", ".");
                classPath = classPath.substring(1);
                try {
                    Class<?> beanClass = Class.forName(classPath);
                    if (beanClass.getAnnotation(Component.class) != null
                            || beanClass.getAnnotation(Service.class) != null
                            || beanClass.getAnnotation(Repository.class) != null
                            || beanClass.getAnnotation(Controller.class) != null) {
                        // 是 讀取 bean 定義信息
                        BeanDefinition definition = getBeanDefinition(beanClass);
                        beanDefinitionMap.put(definition.getId(), definition);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        // 测试打印
        beanDefinitionMap.forEach((k, v) -> System.out.println(k + "  " + v));

        // 创建 bean
        beanDefinitionMap.forEach((id, beanDefinition) -> {
            if (beanDefinition.getBeanMethodName() == null) {
                // 组件扫描
                if (beanDefinition.isLazyInit()
                        || "prototype".equals(beanDefinition.getScope())) {
                    // 延迟加载 和 原型模式 ==> 现在不创建 bean
                } else {
                    try {
                        Class<?> beanClass = Class.forName(beanDefinition.getClassName());
                        Object bean = beanClass.newInstance();
                        // 加入容器
                        singletonObjects.put(id, bean);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException("类路径错误!", e);
                    } catch (InstantiationException e) {
                        throw new RuntimeException("创建 bean 失败!", e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("类访问错误!", e);
                    }
                }
            } else {
                // bean 方法
                if (beanDefinition.isLazyInit()
                        || "prototype".equals(beanDefinition.getScope())) {
                    // 延迟加载 和 原型模式 ==> 现在不创建 bean
                } else {
                    try {
                        Object bean = beanDefinition.getBeanMethodName().invoke(beanDefinition.getBeanFactory());
                        // 加入容器
                        singletonObjects.put(id, bean);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException("方法调用错误!", e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("方法访问错误!", e);
                    }
                }
            }
        });

        // 执行后置处理器

    }

    private static BeanDefinition getBeanDefinition(Class<?> beanClass) {
        BeanDefinition definition = new BeanDefinition();
        definition.setClassName(beanClass.getName());
        definition.setLazyInit(beanClass.getAnnotation(Lazy.class) != null);
        if (beanClass.getAnnotation(Scope.class) != null) {
            Scope scope = beanClass.getAnnotation(Scope.class);
            definition.setScope(scope.value());
        }
        // 组件命名 => 没有设置 => 名子是类名 + 首字母小写
        //         => 有设置  => 使用该值
        Component component = beanClass.getAnnotation(Component.class);
        String name = beanClass.getSimpleName();
        name = name.substring(0, 1).toLowerCase() + name.substring(1);
        definition.setId(component != null && !component.value().isEmpty() ? component.value() : name);
        return definition;
    }

    private static BeanDefinition getBeanDefinition(Method method, Object config) {
        BeanDefinition definition = new BeanDefinition();
        // bean 类型
        definition.setClassName(method.getReturnType().getName());
        definition.setBeanMethodName(method);
        // 传入 config 对象
        definition.setBeanFactory(config);
        definition.setLazyInit(method.getAnnotation(Lazy.class) != null);
        if (method.getAnnotation(Scope.class) != null) {
            Scope scope = method.getAnnotation(Scope.class);
            definition.setScope(scope.value());
        }
        Bean beanAnno = method.getAnnotation(Bean.class);
        String[] ids = beanAnno.value();
        if (ids.length == 0) {
            // 没有设置 @Bean 的 id 那么使用方法名作为id
            definition.setId(method.getName());
        } else {
            definition.setId(ids[0]);
        }
        return definition;
    }

    // 递归扫描方法
    void scanComponent(File path, List<File> list, File projectRoot) {
        File[] files = path.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    scanComponent(file, list, projectRoot);
                } else if (file.isFile() && file.getName().endsWith(".class")) {
                    // 字节码文件 => 类文件
                    // 判断是否是组件 => 用文件路径还原成一个类路径 => com.yc....User
                    // /com/yc  =>  com.yc.bbs.User
                    String classPath = file.getPath().replace(projectRoot.getPath(), "");
                    classPath = classPath.replace(".class", "");
                    classPath = classPath.replaceAll("\\\\", ".");
                    classPath = classPath.substring(1);
                    try {
                        Class<?> beanClass = Class.forName(classPath);
                        if (beanClass.getAnnotation(Component.class) != null
                                || beanClass.getAnnotation(Service.class) != null
                                || beanClass.getAnnotation(Repository.class) != null
                                || beanClass.getAnnotation(Controller.class) != null) {
                            // 是 組件
                            list.add(file);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Object getBean(String id) {
        Object bean = singletonObjects.get(id);
        if(bean == null){
            BeanDefinition beanDefinition = beanDefinitionMap.get(id);
            if(beanDefinition==null){
                throw new RuntimeException("没有该bean:" + id);
            } else {
                if("prototype".equals(beanDefinition.getScope())){
                    // TODO 原型模式=>每次 getBean 都要创建新的 bean
                } else if(beanDefinition.isLazyInit()){
                    // TODO 现在创建对象
                }
            }
        }
        return bean;
    }

    public <T> T getBean() {
        // TODO 实现该方法
        return null;
    }
}
