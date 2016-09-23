package com.jengine;

/**
 * Created by weiyang on 5/7/16.
 */
public class Test {
    @org.junit.Test
    public void test() {
        System.out.println("test");
        try {
            Thread.sleep(30 * 60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        System.out.println();
    }
}
