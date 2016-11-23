package com.jengine.pattern.structural;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * content
 *
 * @author bl07637
 * @date 11/14/2016
 * @since 0.1.0
 */
public class ProxyDynamicDemo {
    public static void main(String[] args) {
        DynamicProxyDemoInterface dynamicProxyDemoInterface = (DynamicProxyDemoInterface) DynamicProxyInstance.create(new DynamicProxyDemoService());
        String result = dynamicProxyDemoInterface.say("hello");
        System.out.println(result);
    }
}

/**
 * creator
 */
class DynamicProxyInstance {
    public static Object create(Object objx) {
        Class cls = objx.getClass();
        return Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), new DynamicProxyHandler(objx));
    }
}

/**
 * InvocationHandler
 */
class DynamicProxyHandler implements InvocationHandler {
    private Object obj;

    DynamicProxyHandler(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        doBefore(method);
        Object result = method.invoke(obj, args);
        doAfter(method);
        return result;
    }

    private void doBefore(Method method) {
        System.out.println("before " + obj.getClass().getSimpleName() + "." + method.getName() + "()");
    }

    private void doAfter(Method method) {
        System.out.println("after " + obj.getClass().getSimpleName() + "." + method.getName() + "()");
    }
}

/**
 * interface
 */
interface DynamicProxyDemoInterface {
    String say(String s);
}

/**
 * implement
 */
class DynamicProxyDemoService implements DynamicProxyDemoInterface {

    public String say(String s) {
        return "say " + s;
    }
}
