package com.jengine.pattern.creational.singleton;

import org.junit.Assert;

/**
 * Created by nouuid on 2015/5/12.
 */
public class SingletonDemo {

    public static void main(String[] args) {
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

class Task implements Runnable {

    private String name;
    private Singleton singleton;
    private boolean flag = true;

    public Task(String name) {
        this.name = name;
        singleton = Singleton.getInstance();
    }

    public Singleton getSingleton() {
        return singleton;
    }

    @Override
    public void run() {
        int i = 0;
        while(flag) {
            try {
                Thread.sleep(1000);
                singleton.getInfo().put(name+"-"+i, name+"-"+i);
            } catch(Exception e) {
                e.printStackTrace();
            }
            i++;
        }
    }

    public void stop() throws Exception {
        flag = false;
    }
}
