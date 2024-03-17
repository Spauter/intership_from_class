package com.bloducspauter.aop;

import com.bloducspauter.config.IocConfig2;
import com.bloducspauter.config.MyAnnotationConfigApplicationContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AopApplicationContext extends MyAnnotationConfigApplicationContext {
    Map<String, Object> aspectMap = new HashMap<>();
    Map<String, List<Method>> pointCutMap = new HashMap<>();
//    为什么编译器强制要求我们定义该够着函数
    public AopApplicationContext(Class<?> configClass) {
        super(configClass);
        this.singletonObjects.forEach((id, bean) -> {
            if (bean.getClass().getAnnotation(Aspect.class) != null) {
                aspectMap.put(id,bean);
            }
        });
        for (Object a : aspectMap.values()) {
            for (Method method : a.getClass().getDeclaredMethods()) {
                Before before = method.getAnnotation(Before.class);
                if (before != null) {
                    String value=before.value();
                    String pointCut;
                    if (!value.matches("\\w+\\(\\)")) {
                        try {
                            String $1 = value.replaceAll("\\w+\\(\\)", "$1");
                            a.getClass().getDeclaredMethod("$1");
                            Method pointCutMethod = a.getClass().getDeclaredMethod($1);
                            Pointcut pointcutAnno = pointCutMethod.getAnnotation(Pointcut.class);
                            pointCut = pointcutAnno.value();
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        pointCut=value;
                    }
                    List<Method> list = pointCutMap.computeIfAbsent(pointCut, k -> new ArrayList<>());
                    list.add(method);
                }
            }
        }
    }

    public static void main(String[] args) {
        AopApplicationContext context = new AopApplicationContext(IocConfig2.class);
        context.pointCutMap.forEach((pointCut, list) -> {
            System.out.println(pointCut);
            System.out.println(list);
        });
    }
}
