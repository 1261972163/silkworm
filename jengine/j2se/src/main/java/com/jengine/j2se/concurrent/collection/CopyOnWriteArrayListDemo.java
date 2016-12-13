package com.jengine.j2se.concurrent.collection;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * content
 *
 * @author bl07637
 * @date 12/7/2016
 * @since 0.1.0
 */
public class CopyOnWriteArrayListDemo {

    public static void main(String[] args) throws InterruptedException {


    }


    public void test1() throws InterruptedException {
        CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<String>();

        AddTask addTask = new AddTask(copyOnWriteArrayList, "a");
        RemoveTask removeTask = new RemoveTask(copyOnWriteArrayList);
        LoopTask loopTask = new LoopTask(copyOnWriteArrayList);


        Thread thread1 = new Thread(addTask);
        Thread thread2 = new Thread(removeTask);
        Thread thread3 = new Thread(loopTask);

        thread1.start();
        thread2.start();
        thread3.start();


        Thread.sleep(30*60*1000);
    }

    @org.junit.Test
    public void test2() {
        CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<String>();
        for (int i=1; i<20; i++) {
            copyOnWriteArrayList.add("" + i);
        }
        boolean ok = false;
        for (String s : copyOnWriteArrayList) {
            if (!ok) {
                copyOnWriteArrayList.remove(copyOnWriteArrayList.get(7));
                ok = true;
            }
            System.out.println(s);
        }
        System.out.println("end");
    }
}

class AddTask implements Runnable {
    CopyOnWriteArrayList<String> copyOnWriteArrayList;
    String name;

    public AddTask(CopyOnWriteArrayList<String> copyOnWriteArrayList, String name) {
        this.copyOnWriteArrayList = copyOnWriteArrayList;
        this.name = name;
    }

    @Override
    public void run() {
        String s;
        int i = 1;
        while (true) {
            try {
                s = name + i++;
                Thread.sleep(500);
                copyOnWriteArrayList.add(s);
                System.out.println("add:" + s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class RemoveTask implements Runnable {

    CopyOnWriteArrayList<String> copyOnWriteArrayList;

    public RemoveTask(CopyOnWriteArrayList<String> copyOnWriteArrayList) {
        this.copyOnWriteArrayList = copyOnWriteArrayList;
    }

    @Override
    public void run() {
        try {
            Random random = new Random();
            while (true) {
                Thread.sleep(1000);
                int i = random.nextInt(copyOnWriteArrayList.size());
                System.out.println("               remove:" + copyOnWriteArrayList.get(i));
                copyOnWriteArrayList.remove(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class LoopTask implements Runnable {

    CopyOnWriteArrayList<String> copyOnWriteArrayList;

    public LoopTask(CopyOnWriteArrayList<String> copyOnWriteArrayList) {
        this.copyOnWriteArrayList = copyOnWriteArrayList;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(431);
                for (String s : copyOnWriteArrayList) {
                    System.out.println("                            print:" + s);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
