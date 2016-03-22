package com.jengine.java.lang;

import org.junit.Test;

/**
 * Created by nouuid on 2015/5/13.
 */
public class StringServiceTest {

    @Test
    public void test() {
        StringService ss = new StringServiceImpl();
        String inputStr = "this.is.a.test";
        System.out.println(ss.replace(inputStr));
        System.out.println(inputStr);
    }
}
