package com.jengine.javaagent;

import java.lang.instrument.Instrumentation;

/**
 * content
 *
 * @author nouuid
 * @date 6/29/2017
 * @since 0.1.0
 */
public class MyAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("MyAgent-1");
        inst.addTransformer(new Logger());
        System.out.println("MyAgent-2");
    }
}
