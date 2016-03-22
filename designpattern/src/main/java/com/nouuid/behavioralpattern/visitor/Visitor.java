package com.nouuid.behavioralpattern.visitor;

/**
 * Created by nouuid on 2015/5/22.
 */
public interface Visitor {
    public void visit(ConcreteElement1 el1);
    public void visit(ConcreteElement2 el2);
}
