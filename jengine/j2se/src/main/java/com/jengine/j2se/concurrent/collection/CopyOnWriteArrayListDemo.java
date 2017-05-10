package com.jengine.j2se.concurrent.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * 1. CopyOnWriteArrayList源自jdk1.5，通常被认为是ArrayList的线程安全变体。
 * 2. 内部由可变数组实现，和ArrayList的区别在于CopyOnWriteArrayList的数组内部均为有效数据。
 * 3. 可变性操作在添加或删除数据的时候，会对数组进行扩容或减容。扩容或减容的过程是：产生新数组，然后将有效数据复制到新数组，这也是“CopyOnWrite”的语义。
 * 但复制操作的效率比较低。
 * 4. 每次获取数组都是final类型的，数组引用不可变。同时在add、set、remove、clear、subList、sort等可变性操作内部加锁，保证了数组操作的线程安全性。get操作不加锁。
 * 5. 使用COWIterator进行遍历，内部为CopyOnWriteArrayList的数据数组的final快照，保证了遍历时数据的不变性。不支持remove操作。
 * 6. 综合上述特性，CopyOnWriteArrayList多线程安全，写操作复制和加锁导致效率较低，读操作序号读取效率高，
 * 适合使用在多线程、读操作远远大于写操作的场景里，比如缓存。
 *
 * @author bl07637
 * @date 12/7/2016
 * @since 0.1.0
 */
public class CopyOnWriteArrayListDemo {

    // 1. CopyOnWriteArrayList源自jdk1.5，通常被认为是ArrayList的线程安全变体。
    @Test
    public void threadSafe() throws InterruptedException {
        CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<String>();
        ArrayList<String> arrayList = new ArrayList<String>();
        CountDownLatch countDownLatch = new CountDownLatch(1); // 保证下面10个线程同时运行
        ThreadGroup group = new ThreadGroup("mygroup");
        for (int i=1; i<=100; i++) {
            Thread thread = new Thread(group, new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int j=1; j<=1000; j++) {
                        copyOnWriteArrayList.add(Thread.currentThread().getName());
//                        // 使用ArrayList会抛错
//                        arrayList.add(Thread.currentThread().getName());
                    }
                }
            }, "t"+i);
            thread.start();
        }
        countDownLatch.countDown();
        while (group.activeCount() > 0) {
            Thread.sleep(10);
        }
        System.out.println(copyOnWriteArrayList.size());
        System.out.println(arrayList.size());
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
