package com.jengine.pattern.behavioral.observer;

import java.util.Vector;

/**
 * Created by nouuid on 2015/5/22.
 */
public abstract class Subject {
    private Vector<Observer> obs = new Vector<Observer>();

    public void addObserver(Observer obs) {
        this.obs.add(obs);
    }

    public void delObserver(Observer obs) {
        this.obs.remove(obs);
    }

    protected void notifyObserver() {
        for (Observer o : obs) {
            o.update();
        }
    }

    public abstract void doSomething();
}
