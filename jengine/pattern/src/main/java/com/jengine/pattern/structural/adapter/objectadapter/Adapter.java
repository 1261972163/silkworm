package com.jengine.pattern.structural.adapter.objectadapter;

/**
 * �ṩ��������Ľӿ�
 * Created by nouuid on 2015/5/25.
 */
public class Adapter implements Target {
    private Adaptee adaptee;

    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    public void request() {
        this.adaptee.specificRequest();
    }

}
