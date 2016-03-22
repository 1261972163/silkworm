package com.nouuid.structuralpattern.adapter.objectadapter;

/**
 * 提供符合需求的接口
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
