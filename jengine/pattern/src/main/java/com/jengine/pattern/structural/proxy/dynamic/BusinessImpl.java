package com.jengine.pattern.structural.proxy.dynamic;

/**
 * Created by nouuid on 2015/5/12.
 */
public class BusinessImpl implements Business {
    @Override
    public void foo() {
        System.out.println("do BusinessImpl.foo()");
    }

    @Override
    public String bar(String message) {
        System.out.println("do BusinessImpl.bar()");
        return message;
    }
}
