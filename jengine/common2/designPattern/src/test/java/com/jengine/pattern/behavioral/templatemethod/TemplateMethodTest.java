package com.jengine.pattern.behavioral.templatemethod;

import org.junit.Test;

/**
 * Created by nouuid on 2015/5/22.
 */
public class TemplateMethodTest {

    @Test
    public void test() {
        int[] a = {10, 32, 1, 9, 5, 7, 12, 0, 4, 3}; // Ԥ����������
        AbstractSort s = new ConcreteSort();
        s.showSortResult(a);
    }
}
