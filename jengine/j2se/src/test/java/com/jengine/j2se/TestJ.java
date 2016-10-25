package com.jengine.j2se;

import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * content
 *
 * @author bl07637
 * @date 9/26/2016
 * @since 0.1.0
 */
public class TestJ {
    @Test
    public void test1() {
        int[] x = new int[3];
        x[0] = 1;
        x[1] = 2;
        x[2] = 3;
        List list = Arrays.asList(x);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        for (int id : x) {
            stringBuilder.append(id).append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        stringBuilder.append(")");
        System.out.println(stringBuilder.toString());
    }

    @Test
    public void test2() {
        System.out.println(new Date().getTime());
        System.out.println(System.currentTimeMillis());
    }

    @Test
    public void test3() {
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        readWriteLock.readLock().lock();
        readWriteLock.readLock().unlock();
        System.out.println("end.");
    }
}
