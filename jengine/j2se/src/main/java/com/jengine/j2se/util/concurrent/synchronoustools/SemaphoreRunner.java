package com.jengine.j2se.util.concurrent.synchronoustools;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * @author nouuid
 * @date 4/29/2016
 * @description
 * use semaphore to build a bounded blocking container
 */
public class SemaphoreRunner {
    public void test() {
        SemaphoreBoundedHashSet<Object> objectSemaphoreBoundedHashSet = new SemaphoreBoundedHashSet<Object>(10);

        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<100; i++) {
                    try {
                        objectSemaphoreBoundedHashSet.add(new String());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("i=" + i);
                }
            }
        });

        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    objectSemaphoreBoundedHashSet.remove();
                }
            }
        });

        producer.start();
        consumer.start();

    }
}

class SemaphoreBoundedHashSet<T> {
    private final Set<T> set;
    private final Semaphore semaphore;

    public SemaphoreBoundedHashSet(int bound) {
        set = Collections.synchronizedSet(new HashSet<T>());
        semaphore = new Semaphore(bound);
    }

    public boolean add(T o) throws InterruptedException {
        semaphore.acquire();
        boolean isAdded = false;
        try {
            isAdded = set.add(o);
            return isAdded;
        } finally {
            if(!isAdded) {
                semaphore.release();
            }
        }
    }

    public boolean remove() {
        if(set!=null && set.size()>0) { //has thread safe problem
            for(T e : set) {
                return remove(e);
            }
        }
        return true;
    }

    public boolean remove(T o) {

        boolean isRemoved = false;
        try {
            isRemoved = set.remove(o);
            return isRemoved;
        } finally {
            if(isRemoved) {
                semaphore.release();
            }
        }
    }
}
