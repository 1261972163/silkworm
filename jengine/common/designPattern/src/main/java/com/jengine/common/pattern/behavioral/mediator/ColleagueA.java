package com.jengine.common.pattern.behavioral.mediator;

/**
 * Created by nouuid on 2015/5/15.
 */
public class ColleagueA extends AbstractColleague {
    @Override
    public void setNumber(int number, AbstractMediator am) {
        this.number = number;
        am.AaffectB();
    }
}
