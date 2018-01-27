package com.jengine.common.pattern.behavioral.mediator;

/**
 * Created by nouuid on 2015/5/15.
 */
public abstract class AbstractColleague {
    protected int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public abstract void setNumber(int number, AbstractMediator am);
}
