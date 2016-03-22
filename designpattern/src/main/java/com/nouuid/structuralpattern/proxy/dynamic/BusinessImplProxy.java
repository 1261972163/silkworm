package com.nouuid.structuralpattern.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by nouuid on 2015/5/12.
 */
public class BusinessImplProxy implements InvocationHandler {

    private Object obj;

    BusinessImplProxy() {

    }

    BusinessImplProxy(Object obj) {
        this.obj = obj;
    }

    public static Object factory(Object obj) {
        Class cls = obj.getClass();
        return Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), new BusinessImplProxy(obj));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        doBefore();
        result = method.invoke(obj, args);
        doAfter();
        return result;
    }

    public void doBefore() {
        System.out.println("before " + obj.getClass().getSimpleName() + ".doXXX()");
    }

    public void doAfter() {
        System.out.println("after " + obj.getClass().getSimpleName() + ".doXXX()");
    }

}
