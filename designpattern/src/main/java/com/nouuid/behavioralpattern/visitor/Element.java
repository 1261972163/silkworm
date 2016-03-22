package com.nouuid.behavioralpattern.visitor;

/**
 * Created by nouuid on 2015/5/22.
 */
public abstract class Element {
    public abstract void accept(Visitor visitor);

    public abstract void doSomething();
}
