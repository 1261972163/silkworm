package com.jengine.pattern.behavioral.mediator;

import org.junit.Test;

/**
 * Created by nouuid on 2015/5/15.
 */
public class MediatorTest {
    @Test
    public void test() {
        AbstractColleague collA = new ColleagueA();
        AbstractColleague collB = new ColleagueB();

        AbstractMediator am = new Mediator(collA, collB);

        System.out.println("==========ͨ������AӰ��B==========");
        collA.setNumber(1000, am);
        System.out.println("collA��numberֵΪ��" + collA.getNumber());
        System.out.println("collB��numberֵΪA��10����" + collB.getNumber());

        System.out.println("==========ͨ������BӰ��A==========");
        collB.setNumber(1000, am);
        System.out.println("collB��numberֵΪ��" + collB.getNumber());
        System.out.println("collA��numberֵΪB��0.1����" + collA.getNumber());
    }
}
