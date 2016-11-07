package com.jengine.pattern.structural.proxy.statical;

/**
 * Created by nouuid on 2015/5/12.
 */
public class BusinessImpl implements Business {

    @Override
    public void doAction() {
        System.out.println("do real action");
    }
}
