package com.jengine.pattern.structural.decorator;

/**
 * Created by nouuid on 2015/5/26.
 */
public class Business implements Businessable {
    @Override
    public void method() {
        System.out.println("method in Business");
    }
}
