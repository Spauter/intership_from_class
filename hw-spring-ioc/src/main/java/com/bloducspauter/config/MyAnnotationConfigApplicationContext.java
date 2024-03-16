package com.bloducspauter.config;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAnnotationConfigApplicationContext {
    Map<String,BeanDefinition> beanDefinitionMap = new HashMap<>();
    Map<String,Object> singletonObjects = new HashMap<>();
    public MyAnnotationConfigApplicationContext(Class<?> configClass) throws UnsupportedEncodingException, ClassNotFoundException {
        //读取配置
        Annotation annotation = configClass.getAnnotation(Configuration.class);
        if (annotation == null) {
            throw new NullPointerException();
        }

        //Bean方法 =>BeanDefinition
        for (Method method : configClass.getMethods()) {
            if (method.getAnnotation(Bean.class) != null) {
                BeanDefinition definition = new BeanDefinition();
//                bean类型
                definition.setClassName(method.getReturnType().getName());
                definition.setBeanMethodName(method);
                definition.setBeanFactory(this);
                definition.setLazyInit(method.getAnnotation(Lazy.class) != null);
                if (method.getAnnotation(Scope.class) != null) {
                    Scope scope = method.getAnnotation(Scope.class);
                    definition.setScope(scope.value());
                }
            //其余省略
            }
        }
        //扫描组件 => BeaDefinition
        ComponentScan componentScan = configClass.getAnnotation(ComponentScan.class);
        if (componentScan != null) {
            String[] packagePaths = componentScan.value();
            URL rootURL = configClass.getClassLoader().getResource("");
            File projectRootPath = new File(URLDecoder.decode(rootURL.getPath(), "utf-8"));
            for (String p : packagePaths) {
                //使用类加载器获取类路径
                ClassLoader classLoader = configClass.getClassLoader();
                URL url = classLoader.getResource(p.replaceAll("\\.", "/"));
                String rootPackagePath = url.getPath();
                rootPackagePath = URLDecoder.decode(rootPackagePath, "utf-8");
                //包扫描根包的文件目录对象
                File rootPath = new File(rootPackagePath);
                List<File> fileList = new ArrayList<>();
                scanComponent(rootPath, fileList, projectRootPath);
                fileList.forEach(System.out::println);
            }
        }
//        创建bean
//        加入容器
//        执行后置处理器
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

    public <T> T getBean(Class<?> c) {
        //TODO
        return null;
    }

    public static void main(String[] args) throws UnsupportedEncodingException, ClassNotFoundException {
        new MyAnnotationConfigApplicationContext(IocConfig2.class);
    }

    //递归扫描133,75,72
    void scanComponent(File file, List<File> fileList, File projectFilePath) throws ClassNotFoundException {
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                scanComponent(f, fileList, projectFilePath);
            } else if (f.isFile() && f.getName().endsWith(".class")) {
                String classPath = f.getPath().replace(projectFilePath.getPath(), "");
//                字节码文件=>类文件
//                判断文件是否是组件 =>用文件路径还原成一个类 com. ... User
                classPath = classPath.replace(".class", "");
                classPath = classPath.replaceAll("\\\\", ".");
                classPath = classPath.substring(1);
                try {
                    Class<?> beanClass = Class.forName(classPath);
                    if(beanClass.getAnnotation(Component.class)!=null
                            ||beanClass.getAnnotation(Service.class)!=null
                            ||beanClass.getAnnotation(Repository.class)!=null
                            ||beanClass.getAnnotation(Controller.class)!=null){
                        // 是 讀取 bean 定義信息
                        BeanDefinition definaition = new BeanDefinition();
                        definaition.setClassName(beanClass.getName());
                        definaition.setLazyInit(beanClass.getAnnotation(Lazy.class)!=null);
                        if (beanClass.getAnnotation(Scope.class)!=null) {
                            Scope scope = beanClass.getAnnotation(Scope.class);
                            definaition.setScope(scope.value());
                        }
                        // 组件命名 => 没有设置 => 名子是类名 + 首字母小写
                        //         => 有设置  => 使用该值
                        Component component = beanClass.getAnnotation(Component.class);
                        if(component!=null){
                            if (!component.value().isEmpty()) {
                                definaition.setId(component.value());
                            } else {
                                String name = beanClass.getSimpleName();
                                name = name.substring(0,1).toLowerCase() + name.substring(1);
                                definaition.setId(name);
                            }
                        }
                        beanDefinitionMap.put(definaition.getId(), definaition);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        beanDefinitionMap.forEach((k,v)->System.out.println(k + "  " + v));

        // 创建 bean
        beanDefinitionMap.forEach((id,beanDefinition)->{
            if(beanDefinition.getBeanMethodName()==null){
                // 组件扫描
                if ( beanDefinition.isLazyInit()
                        || "prototype".equals(beanDefinition.getScope())){
                    // 延迟加载 和 原型模式 ==> 现在不创建 bean
                } else {
                    try {
                        Class<?> beanClass = Class.forName(beanDefinition.getClassName());
                        Object bean = beanClass.newInstance();
                        // 加入容器
                        singletonObjects.put(id,bean);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException("类路径错误!",e);
                    } catch (InstantiationException e) {
                        throw new RuntimeException("创建 bean 失败!",e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("类访问错误!",e);
                    }
                }
            } else {
                // bean 方法
                if ( beanDefinition.isLazyInit()
                        || "prototype".equals(beanDefinition.getScope())){
                    // 延迟加载 和 原型模式 ==> 现在不创建 bean
                } else {
                    try {
                        Object bean = beanDefinition.getBeanMethodName().invoke(beanDefinition.getBeanFactory());
                        // 加入容器
                        singletonObjects.put(id,bean);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException("方法调用错误!",e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("方法访问错误!",e);
                    }
                }
            }
        });
    }
}
