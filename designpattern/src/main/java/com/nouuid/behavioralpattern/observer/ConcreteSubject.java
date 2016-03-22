package com.nouuid.behavioralpattern.observer;

/**
 * Created by nouuid on 2015/5/22.
 */
public class ConcreteSubject extends Subject {
    public void doSomething() {
        System.out.println("被观察者事件发生");
        this.notifyObserver();
    }
}