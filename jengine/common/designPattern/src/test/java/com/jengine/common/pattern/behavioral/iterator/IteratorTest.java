package com.jengine.common.pattern.behavioral.iterator;

import org.junit.Test;

/**
 * Created by nouuid on 2015/5/15.
 */
public class IteratorTest {
    @Test
    public void test() {
        Aggregate ag = new ConcreteAggregate();
        ag.add("testname1");
        ag.add("testname2");
        ag.add("testname3");
        Iterator it = ag.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            System.out.println(str);
        }
    }
}
