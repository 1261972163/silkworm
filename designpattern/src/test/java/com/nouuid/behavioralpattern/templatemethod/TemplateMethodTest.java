package com.nouuid.behavioralpattern.templatemethod;

import org.junit.Test;

/**
 * Created by nouuid on 2015/5/22.
 */
public class TemplateMethodTest {

    @Test
    public void test() {
        int[] a = {10, 32, 1, 9, 5, 7, 12, 0, 4, 3}; // 预设数据数组
        AbstractSort s = new ConcreteSort();
        s.showSortResult(a);
    }
}
