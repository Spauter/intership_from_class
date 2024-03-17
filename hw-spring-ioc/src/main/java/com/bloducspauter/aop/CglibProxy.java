package com.bloducspauter.aop;

import com.yc.spring.bbs.biz.UserBiz;
import com.yc.spring.bbs.dao.UserDao;
import com.yc.spring.bbs.dao.UserDaoImpl;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

// CGLIB 动态字节码 基于继承方式 不要求被代理类实现接口
public class CglibProxy {
    public Object proxy(Object target) {
        Enhancer enhancer = new Enhancer();
//        设置父类
        enhancer.setSuperclass(target.getClass());
//        设置方法拦截器
        enhancer.setCallback(new MethodInterceptor() {
            /**
             *
             * @param o 代理对象
             * @param method 方法
             * @param objects 参数
             * @param methodProxy 代理方法
             */
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                Object object = methodProxy.invoke(target, objects);
                System.out.println("后置拦截：" + object);
                return object;
            }
        });

//        创建代理对象
        return enhancer.create();
    }

    public static void main(String[] args) throws InterruptedException {
        CglibProxy cglibProxy = new CglibProxy();
        UserDao userDao = new UserDaoImpl();
        cglibProxy.proxy(userDao);
        userDao.update(null);
        UserBiz userBiz = new UserBiz();
        UserBiz userBizProxy = (UserBiz) cglibProxy.proxy(userBiz);
//        Throwing NullPointerException
        userBizProxy.modify(null);
    }
}
