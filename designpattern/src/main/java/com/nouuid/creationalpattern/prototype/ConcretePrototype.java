package com.nouuid.creationalpattern.prototype;

/**
 * 实现一个克隆自身的操作
 * Created by nouuid on 2015/5/13.
 */
public class ConcretePrototype extends Prototype {
    public ConcretePrototype(String name, Component component) {
        setName(name);
        setComponent(component);
    }
}
