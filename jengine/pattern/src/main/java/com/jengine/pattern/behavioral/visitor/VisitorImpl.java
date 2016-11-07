package com.jengine.pattern.behavioral.visitor;

/**
 * Created by nouuid on 2015/5/22.
 */
public class VisitorImpl implements Visitor {

    public void visit(ConcreteElement1 el1) {
        el1.doSomething();
    }

    public void visit(ConcreteElement2 el2) {
        el2.doSomething();
    }
}
