package com.bloducspauter.aop;

import com.bloducspauter.config.IocConfig2;
import com.bloducspauter.config.MyAnnotationConfigApplicationContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AopApplicationContext extends MyAnnotationConfigApplicationContext {
    Map<String, Object> aspectMap = new HashMap<>();
    Map<String, List<Method>> pointCutMap = new HashMap<>();
    Map<String, Object> proxyMap = new HashMap<>();

    //    为什么编译器强制要求我们定义该够着函数
    public AopApplicationContext(Class<?> configClass) {
        super(configClass);
        this.singletonObjects.forEach((id, bean) -> {
            if (bean.getClass().getAnnotation(Aspect.class) != null) {
                aspectMap.put(id, bean);
            }
        });
        for (Object a : aspectMap.values()) {
            for (Method method : a.getClass().getDeclaredMethods()) {
                Before before = method.getAnnotation(Before.class);
                if (before != null) {
                    String value = before.value();
                    String pointCut;
                    if (value.matches("\\w+\\(\\)")) {
                        try {
                            String replacedValue = value.replaceAll("(\\w+)\\(\\)", "$1");
                            a.getClass().getDeclaredMethod(replacedValue);
                            Method pointCutMethod = a.getClass().getDeclaredMethod(replacedValue);
                            Pointcut pointcutAnno = pointCutMethod.getAnnotation(Pointcut.class);
                            pointCut = pointcutAnno.value();
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        pointCut = value;
                    }
                    List<Method> list = pointCutMap.computeIfAbsent(pointCut, k -> new ArrayList<>());
                    list.add(method);
                }
            }
        }//扫描所有组件,查看是否保护被切点拦截的方法
        this.singletonObjects.forEach((id, bean) -> {
            a:
            for (Method m : bean.getClass().getDeclaredMethods()) {
                for (String point : pointCutMap.keySet()) {
                    if (isPointCut(point, m)) {
                        proxyMap.put(id, bean);
//                        只要有一个方法被切到,那么就要代理，即可中止循环
//                        break a;
                    }
                }
            }
        });
        proxyMap.forEach((id, bean) -> {
            Class<?>[] interfaces = bean.getClass().getInterfaces();
            if (interfaces.length == 0) {
//                CGLib动态代理
                CglibProxy cglibProxy=new CglibProxy();
                cglibProxy.proxy(bean);
            } else {
//                jdk 动态代理
                JdkProxy jdkProxy = new JdkProxy();
                jdkProxy.proxy(bean);
            }
        });
    }

    public boolean isPointCut(String point, Method method) {
//        只处理execution表达式
        point = point.replaceAll("execution\\((.+)\\)", "$1");
        String methodString = method.toString();
        point = point.replaceAll("\\*|\\.\\.", ".*");
        point = point.replaceAll("\\(", "\\\\(");
        point = point.replaceAll("\\)", "\\\\)");
        return methodString.matches(point);
    }

    private void jdkProxy(String id, Object bean) {
        Object proxyBean = Proxy.newProxyInstance(
                bean.getClass().getClassLoader(),
                bean.getClass().getInterfaces(),
                (proxy, method, args) -> {
//                    JDK Stream编程
                    //                        前置增强
                    pointCutMap
//                                双列集合转单列集合,Set<Entry>
                            .entrySet()
//                                集合转成 流
                            .stream()
//                                过滤切点
                            .filter(entry -> isPointCut(entry.getKey(), method))
//                            将Map里面的List元素提取出来
                            .flatMap(entry -> entry.getValue().stream())
//                            过滤拦截方法的集合
                            .filter(adviceMethod -> adviceMethod.getAnnotation(Before.class) != null)
//                            执行所有前置争抢方法
                            .forEach(beforeMethod -> {
                                try {
                                    beforeMethod.invoke(bean);
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                    ;

                    return method.invoke(bean, args);
                }
        );
    }

    public static void main(String[] args) {
        AopApplicationContext context = new AopApplicationContext(IocConfig2.class);
//        context.pointCutMap.forEach((pointCut, list) -> {
//            System.out.println(pointCut);
//            System.out.println(list);
//        });
        context.proxyMap.forEach((id, bean) -> {
            System.out.println(id);
            System.out.println(bean);
        });
    }

}
