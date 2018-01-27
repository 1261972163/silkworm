package com.jengine.javaagent.asm;

import java.lang.instrument.Instrumentation;

/**
 * javaagent计算方法执行时间
 *
 * @author nouuid
 * @date 6/29/2017
 * @since 0.1.0
 */
public class MyAgent {
  public static void premain(String agentArgs, Instrumentation inst) {
    System.out.println("agent begin");
    //添加字节码转换器
    inst.addTransformer(new PrintTimeTransformer());
    System.out.println("agent end");
  }
}
