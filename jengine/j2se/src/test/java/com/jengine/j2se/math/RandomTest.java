package com.jengine.j2se.math;

/**
 * @author nouuid
 * @date 7/13/2016
 * @description
 */
public class RandomTest {

    @org.junit.Test
    public void random() {
        int min = 0;
        int max = 10;
        int num = min + (int)(Math.random() * (max-min+1));
        System.out.println(num);
    }
}