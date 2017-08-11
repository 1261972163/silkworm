package com.jengine.pattern.creational.abstractfactory;

/**
 * Created by nouuid on 2015/5/13.
 */
public class FactoryImpl implements Factory {

    @Override
    public Product1 createProduct1() {
        return new Product1Impl();
    }

    @Override
    public Product2 createProduct2() {
        return new Product2Impl();
    }
}
