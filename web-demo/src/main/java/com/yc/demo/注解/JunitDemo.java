package com.yc.demo.注解;

import org.junit.BeforeClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class JunitDemo {
    public static void main(String[] args) {
        Class<?>[] objects = new Class[]{A.class, B.class, C.class};
        Class<?>[] tests = filterTest(objects);
        Method beforeClassMethod = null;
        Method afterclassMethod = null;
        Method beforeMethod = null;
        Method afterMethod = null;
        for (Class<?> test : tests) {
            System.out.println("******************************");
//            获取beforeClass和afterClass 两个注解的方法
            for (Method method : test.getMethods()) {
                if (method.getAnnotation(BeforeClass.class) != null) {
                    beforeClassMethod = method;
                }
                if (method.getAnnotation(AfterClass.class) != null) {
                    afterclassMethod = method;
                }
                if (method.getAnnotation(Before.class) != null) {
                    beforeMethod = method;
                }
                if (method.getAnnotation(After.class) != null) {
                    afterMethod = method;
                }
            }
            System.out.println("beforeclassMethod = " + beforeClassMethod);
            System.out.println("afterclassMethod = " + afterclassMethod);
            System.out.println("beforeMethod =" + beforeMethod);
            System.out.println("afterMethod =" + afterMethod);

        }
        if (beforeClassMethod!=null){
//            beforeClassMethod.invoke (test);

        }
    }


    public static Class<?>[] filterTest(Class<?>[] objects) {
        List<Class> tests = new ArrayList<>();
        for (Class<?> object : objects) {
            final Method[] methods = object.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getAnnotation(Test.class) != null) {
                    tests.add(object);
                }
            }
        }
        return tests.toArray(new Class[0]);
    }
}

class A {
    @Before
    public void before() {
        System.out.println("A.before()");
    }

    @BeforeClass
    public static void afterClass() {
        System.out.println("A.afterClass");
    }

    @Test
    public void test1() {
        System.out.println("A.test1");
        int i = 1 / 0;
    }

    @Test
    public void test2() {
        System.out.println("A.test2()");
    }
}

class B {
    @Test
    public void test1() {
        System.out.println("B.test1()");
    }
}

class C {
    public void test1() {
        System.out.println("C.test1()");
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Test {

}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Before {

}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface After {

}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface AfterClass {

}
