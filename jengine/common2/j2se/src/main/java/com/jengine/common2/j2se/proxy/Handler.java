package com.jengine.common2.j2se.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author nouuid
 * @description
 * @date 9/11/16
 */
public class Handler implements InvocationHandler {
    private Object target;

    public Handler(Object target) {
        this.target = target;
    }

    public Object newProxyInstance() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        method.invoke(target, args);
        after();
        return null;
    }

    public void before() {
        System.out.println("before method");
    }

    public void after() {
        System.out.println("after method");
    }
}
