package com.nouuid.behavioralpattern.observer;

/**
 * Created by nouuid on 2015/5/22.
 */
public class ConcreteObserver2 implements Observer {
    public void update() {
        System.out.println("观察者2收到信息，并进行处理。");
    }
}