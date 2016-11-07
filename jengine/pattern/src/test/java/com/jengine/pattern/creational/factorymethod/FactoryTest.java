package com.jengine.pattern.creational.factorymethod;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by nouuid on 2015/5/13.
 */
public class FactoryTest {
    @Test
    public void test() {
        Factory factory = new FactoryImpl();
        Product product = factory.createProduct();

        Assert.assertEquals("product", product.show());
    }
}
