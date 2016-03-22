package com.nouuid.creationalpattern.builder;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by nouuid on 2015/5/13.
 */
public class BuilderTest {

    @Test
    public void test() {
        Director director = new Director();

        Product aProduct = director.getAProduct();
        Assert.assertEquals("aproduct", aProduct.getName());
        Assert.assertEquals("atype", aProduct.getType());

        Product bProduct = director.getBProduct();
        Assert.assertEquals("bproduct", bProduct.getName());
        Assert.assertEquals("btype", bProduct.getType());
    }
}
