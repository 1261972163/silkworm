package com.nouuid.creationalpattern.factorymethod;

/**
 * Created by nouuid on 2015/5/13.
 */
public class FactoryImpl implements Factory {
    @Override
    public Product createProduct() {
        return new ProductImpl();
    }
}
