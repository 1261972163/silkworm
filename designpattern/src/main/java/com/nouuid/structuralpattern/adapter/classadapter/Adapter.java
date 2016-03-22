package com.nouuid.structuralpattern.adapter.classadapter;

/**
 * 提供符合需求的接口
 * Created by nouuid on 2015/5/25.
 */
public class Adapter extends Adaptee implements Target {
    @Override
    public void request() {
        System.out.println("符合需求的接口");
        super.specificRequest();
    }

}
