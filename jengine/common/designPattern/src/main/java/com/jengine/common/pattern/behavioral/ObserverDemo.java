package com.jengine.common.pattern.behavioral;


import java.util.ArrayList;
import java.util.List;

/**
 * 观察者
 *
 * @author nouuid
 * @date 11/14/2016
 * @since 0.1.0
 */
public class ObserverDemo {
    public static void main(String[] args) {
        Subject sub = new ConcreteSubject();
        sub.addObserver(new ConcreteObserver1());
        sub.addObserver(new ConcreteObserver2());
        sub.doSomething();
    }
}

abstract class Subject {
    //用来保存注册的观察者对象
    private List<Observer> obs = new ArrayList<Observer>();

    //注册观察者对象
    public void addObserver(Observer obs) {
        this.obs.add(obs);
    }

    //删除观察者对象
    public void delObserver(Observer obs) {
        this.obs.remove(obs);
    }

    //通知所有注册的观察者对象
    protected void notifyObserver() {
        for (Observer o : obs) {
            o.update();
        }
    }

    public abstract void doSomething();
}

interface Observer {
    void update();
}

class ConcreteSubject extends Subject {
    public void doSomething() {
        System.out.println("ConcreteSubject doSomething");
        this.notifyObserver();
    }
}

class ConcreteObserver1 implements Observer {
    public void update() {
        System.out.println("ConcreteObserver1");
    }
}

class ConcreteObserver2 implements Observer {
    public void update() {
        System.out.println("ConcreteObserver2");
    }
}
