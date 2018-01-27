package com.jengine.common.pattern.creational.prototype;

/**
 * ʵ��һ����¡����Ĳ���
 * Created by nouuid on 2015/5/13.
 */
public class ConcretePrototype extends Prototype {
    public ConcretePrototype(String name, Component component) {
        setName(name);
        setComponent(component);
    }
}
