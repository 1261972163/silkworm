package com.nouuid.behavioralpattern.visitor;

/**
 * Created by nouuid on 2015/5/22.
 */
public class ConcreteElement1 extends Element {
    public void doSomething() {
        System.out.println("ÕâÊÇÔªËØ1");
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
