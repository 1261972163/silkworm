package com.jengine.pattern.behavioral.observer;

import org.junit.Test;

/**
 * Created by nouuid on 2015/5/22.
 */
public class ObserverTest {

    @Test
    public void test() {
        Subject sub = new ConcreteSubject();
        sub.addObserver(new ConcreteObserver1()); //��ӹ۲���1
        sub.addObserver(new ConcreteObserver2()); //��ӹ۲���2
        sub.doSomething();
    }
}
