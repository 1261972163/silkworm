package com.nouuid.creationalpattern.singleton;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by nouuid on 2015/5/12.
 */
public class SingletonTest {

    @Test
    public void test() {
        Task task1 = new Task("task1");
        Thread t1 = new Thread(task1);
        t1.start();
        Task task2 = new Task("task2");
        Thread t2 = new Thread(task2);
        t2.start();

        try {
            Thread.sleep(1 * 10 * 1000);
//            System.out.println(task1.getSingleton().getInfo());
//            System.out.println(task2.getSingleton().getInfo());
            Assert.assertEquals(task1.getSingleton().getInfo(), task2.getSingleton().getInfo());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                task1.stop();
                task2.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }
}
