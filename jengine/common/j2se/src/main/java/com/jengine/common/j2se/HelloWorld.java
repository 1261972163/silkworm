/**
 * Copyright (C) 2017 The BEST Authors
 */

package com.jengine.common.j2se;

/**
 * javac HelloWorld.java
 * java HelloWorld
 * @author nouuid
 */
public class HelloWorld {

  public static void main(String[] args) throws InterruptedException {
    int i = 0;
    while (true) {
      System.out.println("round" + i);
      Thread.sleep(1000);
      i++;
    }
  }
}
