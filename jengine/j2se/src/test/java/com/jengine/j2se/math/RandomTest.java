package com.jengine.j2se.math;

import org.junit.Test;

import java.util.Random;

/**
 * @author nouuid
 * @date 7/13/2016
 * @description
 */
public class RandomTest {

    @Test
    public void random() {
        int min = 0;
        int max = 10;
        int num = min + (int)(Math.random() * (max-min+1));
        System.out.println(num);
    }

    @Test
    public void random2() {
        Random random = new Random();
        System.out.println(random.nextInt(10));
        System.out.println(random.nextInt(10));
    }
}
