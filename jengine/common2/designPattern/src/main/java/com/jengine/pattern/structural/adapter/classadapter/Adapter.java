package com.jengine.pattern.structural.adapter.classadapter;

import com.jengine.pattern.structural.adapter.AdapterTarget;

/**
 * Created by nouuid on 2015/5/25.
 */
public class Adapter extends Adaptee implements AdapterTarget {
    @Override
    public void request() {
        System.out.println("class adapter");
        super.specificRequest();
    }

}
