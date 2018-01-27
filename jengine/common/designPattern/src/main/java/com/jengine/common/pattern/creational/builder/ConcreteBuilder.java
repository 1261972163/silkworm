package com.jengine.common.pattern.creational.builder;

/**
 * Created by nouuid on 2015/5/13.
 */
public class ConcreteBuilder extends Builder {
    private Product product = new Product();

    public Product getProduct() {
        return product;
    }

    public void setPart(String arg1, String arg2) {
        product.setName(arg1);
        product.setType(arg2);
    }
}
