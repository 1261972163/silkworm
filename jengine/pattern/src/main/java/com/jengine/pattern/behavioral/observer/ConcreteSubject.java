package com.jengine.pattern.behavioral.observer;

/**
 * Created by nouuid on 2015/5/22.
 */
public class ConcreteSubject extends Subject {
    public void doSomething() {
        System.out.println("���۲����¼�����");
        this.notifyObserver();
    }
}