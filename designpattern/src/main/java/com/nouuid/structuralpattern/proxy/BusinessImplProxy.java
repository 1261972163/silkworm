package com.nouuid.structuralpattern.proxy;

/**
 * Created by nouuid on 2015/5/12.
 */
public class BusinessImplProxy implements Business {

    private BusinessImpl bi;

    @Override
    public void doAction() {
        if (bi == null) {
            bi = new BusinessImpl();
        }
        doBefore();
        bi.doAction();
        doAfter();
    }

    public void doBefore() {
        System.out.println("before doAction()");
    }

    public void doAfter() {
        System.out.println("after doAction()");
    }
}
