package com.bloducspauter.aop;

import com.yc.spring.bbs.dao.UserDao;
import com.yc.spring.bbs.dao.UserDaoImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//java提供的代理方式,基于接口方法实现，被代理类必须实现接口
public class JdkProxy {
    public static Object proxy(Object bean) {
        Proxy.newProxyInstance(
//                类加载器
                bean.getClass().getClassLoader(),
//                接口数组
                bean.getClass().getInterfaces(),
//               方法拦截器
                new InvocationHandler() {
                    /**
                     *
                     * @param proxy the proxy instance that the method was invoked on(代理对象）
                     *
                     * @param method the {@code Method} instance corresponding to
                     * the interface method invoked on the proxy instance.  The declaring
                     * class of the {@code Method} object will be the interface that
                     * the method was declared in, which may be a superinterface of the
                     * proxy interface that the proxy class inherits the method through.（拦截的方法）
                     *
                     * @param args an array of objects containing the values of the
                     * arguments passed in the method invocation on the proxy instance,
                     * or {@code null} if interface method takes no arguments.
                     * Arguments of primitive types are wrapped in instances of the
                     * appropriate primitive wrapper class, such as
                     * {@code java.lang.Integer} or {@code java.lang.Boolean}.（传入的方法参数）
                     *
                     * @return
                     * @throws Throwable
                     */
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("前置增强,方法签名为:"+method);
//                        执行业务方法
                        Object object = method.invoke(bean, args);
                        return object;
                    }
                }
        );

        return bean;
    }

    public static void main(String[] args) throws InterruptedException {
        UserDao userDao=new UserDaoImpl();
        UserDao proxyBean = (UserDao) proxy(userDao);
        proxyBean.count(null);
    }
}
