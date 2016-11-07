package com.jengine.pattern.behavioral.strategy;

import org.junit.Test;

/**
 * Created by nouuid on 2015/5/22.
 */
public class StrategyTest {

    @Test
    public void test() {
        Context context;
        System.out.println("-----ִ�в���1-----");
        context = new Context(new ConcreteStrategy1());
        context.execute();

        System.out.println("-----ִ�в���2-----");
        context = new Context(new ConcreteStrategy2());
        context.execute();
    }
}
