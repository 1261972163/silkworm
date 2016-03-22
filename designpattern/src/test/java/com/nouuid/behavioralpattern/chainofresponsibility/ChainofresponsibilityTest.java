package com.nouuid.behavioralpattern.chainofresponsibility;

import org.junit.Test;

/**
 * Created by nouuid on 2015/5/14.
 */
public class ChainofresponsibilityTest {
    @Test
    public void test() {
        Handler handler1 = new ConcreteHandler1();
        Handler handler2 = new ConcreteHandler2();
        Handler handler3 = new ConcreteHandler3();

        handler1.setNextHandler(handler2);
        handler2.setNextHandler(handler3);

        Response response = handler1.handleRequest(new Request(new Level(4)));
    }
}
