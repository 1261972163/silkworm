package com.jengine.common.pattern.creational.prototype;


import org.junit.Test;

/**
 * Created by nouuid on 2015/5/13.
 */
public class PrototypeTest {

    @Test
    public void test() {
        System.out.println("ǳ����:");
        ConcretePrototype cp1 = new ConcretePrototype("defaultname", new Component());
        ConcretePrototype copy1 = (ConcretePrototype)cp1.clone();
        System.out.println("cp1��copy1�Ƿ���ȣ�" + cp1.equals(copy1));
        System.out.println("cp1��copy1�Ƿ�ͬһ������" + (cp1==copy1));
        System.out.println("cp1��copy1��component�Ƿ�ͬһ������" + (cp1.getComponent()==copy1.getComponent()));

        System.out.println("���:");
        ConcretePrototype cp2 = new ConcretePrototype("defaultname", new Component());
        ConcretePrototype copy2 = (ConcretePrototype)cp2.deepClone();
        System.out.println("cp2��copy2�Ƿ���ȣ�" + cp2.equals(copy2));
        System.out.println("cp2��copy2�Ƿ�ͬһ������" + (cp2==copy2));
        System.out.println("cp2��copy2��component�Ƿ�ͬһ������" + (cp2.getComponent()==copy2.getComponent()));

        System.out.println("new:");
        ConcretePrototype cp3 = new ConcretePrototype("defaultname", new Component());
        ConcretePrototype cp4 = new ConcretePrototype("defaultname", new Component());
        System.out.println("cp3��cp4�Ƿ���ȣ�" + cp3.equals(cp4));
        System.out.println("cp3��cp4�Ƿ�ͬһ������" + (cp3==cp4));
        System.out.println("cp3��cp4��component�Ƿ�ͬһ������" + (cp3.getComponent()==cp4.getComponent()));


    }

}
