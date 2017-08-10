package com.jengine.common2.j2se.proxy;

/**
 * @author nouuid
 * @description
 * @date 9/11/16
 */
public class ProxyMain {

    public void test() {
        Handler h = new Handler(new HelloServiceImpl());
        HelloService helloProxy = (HelloService)h.newProxyInstance();
        helloProxy.say("hellos");
    }
}
