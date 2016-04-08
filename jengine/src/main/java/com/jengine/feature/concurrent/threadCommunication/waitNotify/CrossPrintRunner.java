package com.jengine.feature.concurrent.threadCommunication.waitNotify;

/**
 * @author bl07637
 * @date 4/5/2016
 * @description
 */
public class CrossPrintRunner {

    public void test() throws InterruptedException {
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