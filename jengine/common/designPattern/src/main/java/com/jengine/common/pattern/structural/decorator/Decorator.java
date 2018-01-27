package com.jengine.common.pattern.structural.decorator;

/**
 * Created by nouuid on 2015/5/26.
 */
public class Decorator implements Businessable {

    private Businessable business;

    public Decorator(Businessable business) {
        super();
        this.business = business;
    }

    @Override
    public void method() {
        System.out.println("before method");
        business.method();
        System.out.println("after method");
    }
}
