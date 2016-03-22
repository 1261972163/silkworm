package com.nouuid.structuralpattern.decorator;

import org.junit.Test;

/**
 * Created by nouuid on 2015/5/26.
 */
public class DecoratorTest {

    @Test
    public void test() {
        System.out.println("Business:");
        Businessable biz = new Business();
        biz.method();

        System.out.println("Decorator:");
        Businessable decorator = new Decorator(biz);
        decorator.method();
    }
}
