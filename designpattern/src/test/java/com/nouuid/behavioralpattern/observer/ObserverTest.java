package com.nouuid.behavioralpattern.observer;

import org.junit.Test;

/**
 * Created by nouuid on 2015/5/22.
 */
public class ObserverTest {

    @Test
    public void test() {
        Subject sub = new ConcreteSubject();
        sub.addObserver(new ConcreteObserver1()); //添加观察者1
        sub.addObserver(new ConcreteObserver2()); //添加观察者2
        sub.doSomething();
    }
}
