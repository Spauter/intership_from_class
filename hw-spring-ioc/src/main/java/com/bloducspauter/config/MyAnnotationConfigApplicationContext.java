package com.bloducspauter.config;

import org.springframework.context.annotation.*;
//import org.springframework.stereotype.Component;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class MyAnnotationConfigApplicationContext {
    public MyAnnotationConfigApplicationContext(Class<?> configClass) throws UnsupportedEncodingException {
//    读取配置
        Annotation annotation = configClass.getAnnotation(Configuration.class);
        if (annotation == null) {
            throw new NullPointerException();
        }

//        Bean方法 =>BeanDefinition
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
//                其余省略
            }
        }
//        扫描组件 => BeaDefinition
        ComponentScan componentScan = configClass.getAnnotation(ComponentScan.class);
        if (componentScan != null) {
            String[] packagePaths = componentScan.value();
            URL rootURL = configClass.getClassLoader().getResource("");
            File projectRootPath = new File(URLDecoder.decode(rootURL.getPath(), "utf-8"));
            for (String p : packagePaths) {
//                使用类加载器获取类路径
                ClassLoader classLoader = configClass.getClassLoader();
                URL url = classLoader.getResource(p.replaceAll("\\.", "/"));
                String rootPackagePath = url.getPath();
                rootPackagePath = URLDecoder.decode(rootPackagePath, "utf-8");
//                包扫描根包的文件目录对象
                File rootPath = new File(rootPackagePath);
                List<File> fileList = new ArrayList<>();
                scanComponent(rootPath, fileList, projectRootPath);
            }
        }
//        创建bean
//        加入容器
//        执行后置处理器
    }

//    public Object getBean(String myUser) {
//        return null;
//    }
//
//    public <T> T getBean(Class<?> c) {
//        return null;
//    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        new MyAnnotationConfigApplicationContext(IocConfig.class);
    }

    //递归扫描133,75,72
    void scanComponent(File file, List<File> fileList, File projectFilePath) {
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                scanComponent(f, fileList, projectFilePath);
            } else if (f.isFile() && f.getName().endsWith(".class")) {
                String classPath = f.getPath().replace(projectFilePath.getPath(), "");
                System.out.println(classPath);
//                字节码文件=>类文件
//                判断文件是否是组件 =>用文件路径还原成一个类 com. ... User
                fileList.add(f);
            }
        }
    }
}
