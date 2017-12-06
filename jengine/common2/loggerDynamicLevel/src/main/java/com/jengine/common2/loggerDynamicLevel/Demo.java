package com.jengine.common2.loggerDynamicLevel;

import java.util.concurrent.CountDownLatch;

/**
 * 动态修改log4j日志级别
 * @author nouuid
 */
public class Demo {

  public static void main(String[] args) throws Exception {
    final DemoServer demoServer = new DemoServer();
    CountDownLatch end = new CountDownLatch(1);
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          demoServer.start();
        } catch (Exception e) {
          e.printStackTrace();
        }
        end.countDown();
      }
    });
    thread.start();
    end.await();
    System.out.println("### end.");
  }
}
