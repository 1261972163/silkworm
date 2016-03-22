package com.nouuid.creationalpattern.prototype;


import org.junit.Assert;
import org.junit.Test;

/**
 * Created by nouuid on 2015/5/13.
 */
public class PrototypeTest {

    @Test
    public void test() {
        System.out.println("浅复制:");
        ConcretePrototype cp1 = new ConcretePrototype("defaultname", new Component());
        ConcretePrototype copy1 = (ConcretePrototype)cp1.clone();
        System.out.println("cp1和copy1是否相等：" + cp1.equals(copy1));
        System.out.println("cp1和copy1是否同一个对象" + (cp1==copy1));
        System.out.println("cp1和copy1的component是否同一个对象：" + (cp1.getComponent()==copy1.getComponent()));

        System.out.println("深复制:");
        ConcretePrototype cp2 = new ConcretePrototype("defaultname", new Component());
        ConcretePrototype copy2 = (ConcretePrototype)cp2.deepClone();
        System.out.println("cp2和copy2是否相等：" + cp2.equals(copy2));
        System.out.println("cp2和copy2是否同一个对象：" + (cp2==copy2));
        System.out.println("cp2和copy2的component是否同一个对象：" + (cp2.getComponent()==copy2.getComponent()));

        System.out.println("new:");
        ConcretePrototype cp3 = new ConcretePrototype("defaultname", new Component());
        ConcretePrototype cp4 = new ConcretePrototype("defaultname", new Component());
        System.out.println("cp3和cp4是否相等：" + cp3.equals(cp4));
        System.out.println("cp3和cp4是否同一个对象：" + (cp3==cp4));
        System.out.println("cp3和cp4的component是否同一个对象：" + (cp3.getComponent()==cp4.getComponent()));


    }

}
