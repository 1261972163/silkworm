package com.jengine.j2se.concurrent.threadCommunication.waitNotifyPractice;

import org.junit.Test;

/**
 * @author nouuid
 * @date 4/5/2016
 * @description
 */
public class CrossPrintRunner {

    @Test
    public void crossPrintTest() throws Exception {
        CrossPrinter crossPrinter = new CrossPrinter();
        for (int i=0; i<10; i++) {
            Thread aPrinter = new Thread(new Runnable() {
                @Override
                public void run() {
                    crossPrinter.printA();
                }
            });
            aPrinter.start();
        }

        for (int i=0; i<100; i++) {
            Thread bPrinter = new Thread(new Runnable() {
                @Override
                public void run() {
                    crossPrinter.printB();
                }
            });
            bPrinter.start();
        }
        Thread.sleep(10*1000);
    }
}


class CrossPrinter {

    volatile private boolean prevIsA = false;

    synchronized public void printA() {
        try {
            while (prevIsA) {
                wait();
            }
            System.out.println("AAAAA");
            prevIsA = true;
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized public void printB() {
        try {
            while (!prevIsA) {
                wait();
            }
            System.out.println("BBBBB");
            prevIsA = false;
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}