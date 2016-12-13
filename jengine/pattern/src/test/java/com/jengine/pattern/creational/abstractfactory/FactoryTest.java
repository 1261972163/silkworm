package com.jengine.pattern.creational.abstractfactory;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by nouuid on 2015/5/13.
 */
public class FactoryTest {

    @Test
    public void test() {
        Factory factory = new FactoryImpl();
        Assert.assertEquals("product1", factory.createProduct1().show());
        Assert.assertEquals("product2", factory.createProduct2().show());
    }
}
