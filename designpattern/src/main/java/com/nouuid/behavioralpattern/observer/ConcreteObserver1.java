package com.nouuid.behavioralpattern.observer;

/**
 * Created by nouuid on 2015/5/22.
 */
public class ConcreteObserver1 implements Observer {
    public void update() {
        System.out.println("观察者1收到信息，并进行处理。");
    }
}
