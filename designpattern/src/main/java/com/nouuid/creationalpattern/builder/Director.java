package com.nouuid.creationalpattern.builder;

/**
 * Created by nouuid on 2015/5/13.
 */
public class Director {
    private Builder builder = new ConcreteBuilder();

    public Product getAProduct() {
        builder.setPart("aproduct", "atype");
        return builder.getProduct();
    }

    public Product getBProduct() {
        builder.setPart("bproduct", "btype");
        return builder.getProduct();
    }
}
