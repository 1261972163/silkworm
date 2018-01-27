/**
 * Copyright (C) 2017 The BEST Authors
 */

package com.jengine.apps.apm.skywalking;

import org.apache.skywalking.apm.toolkit.trace.Trace;

/**
 * @author bl07637
 */
public class Demo {
  public static void main(String[] args) throws InterruptedException {
    Demo demo = new Demo();
    int i = 0;
    while (i<100) {
      demo.test1("hello" + i);
      i++;
      Thread.sleep(1000);
    }
  }

  @Trace
  public String test1(String s) {
    System.out.println("test1-" + s);
    return test2(s);
  }

  public String test2(String s) {
    System.out.println("test2-" + s);
    return s;
  }
}
