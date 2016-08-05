package com.jengine.java.util;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author nouuid
 * @date 3/18/2016
 * @description
 */
public class ExchangerEngine {

    final Exchanger exchanger = new Exchanger();
    ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        ExchangerEngine ee = new ExchangerEngine();
        ee.exchange();
    }

    public void exchange() {
        instance1();
        instance2();
    }

    public void instance1() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String outData = "instance1";
                    System.out.println("Thread[" + Thread.currentThread().getName() + "] exchange out data=" + outData + ".");
                    String inData = (String)exchanger.exchange(outData);
                    System.out.println("Thread[" + Thread.currentThread().getName() + "] exchange in data=" + inData + ".");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void instance2() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String outData = "instance2";
                    System.out.println("Thread[" + Thread.currentThread().getName() + "] exchange out data=" + outData + ".");
                    String inData = (String)exchanger.exchange(outData);
                    System.out.println("Thread[" + Thread.currentThread().getName() + "] exchange in data=" + inData + ".");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

