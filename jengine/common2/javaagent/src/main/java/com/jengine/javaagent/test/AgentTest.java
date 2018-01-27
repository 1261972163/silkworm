/**
 * Copyright (C) 2017 The BEST Authors
 */

package com.jengine.javaagent.test;

/**
 * @author nouuid
 */
public class AgentTest {
  private void fun1() throws Exception {
    System.out.println("this is fun 1.");
    Thread.sleep(500);
  }

  private void fun2() throws Exception {
    System.out.println("this is fun 2.");
    Thread.sleep(500);
  }

  /**
   * -javaagent:D:\Workspace\github\silkworm\jengine\common2\javaagent\target\jengine-javaagent-0.1.0-SNAPSHOT.jar
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    AgentTest test = new AgentTest();
    test.fun1();
    test.fun2();
  }
}
