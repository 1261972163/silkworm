package com.jengine.pattern.structural.adapter.objectadapter;

import com.jengine.pattern.structural.adapter.AdapterTarget;

/**
 * Created by nouuid on 2015/5/25.
 */
public class Adapter implements AdapterTarget {
    private Adaptee adaptee;

    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    public void request() {
        this.adaptee.specificRequest();
    }

}
