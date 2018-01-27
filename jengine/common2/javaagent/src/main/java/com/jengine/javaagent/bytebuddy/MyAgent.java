/**
 * Copyright (C) 2017 The BEST Authors
 */

package com.jengine.javaagent.bytebuddy;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

/**
 * @author nouuid
 */
public class MyAgent {
  public static void premain(String agentArgs, Instrumentation inst) {
    System.out.println("this is an perform monitor agent.");

    AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {
      @Override
      public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
                                              TypeDescription typeDescription,
                                              ClassLoader classLoader,
                                              JavaModule javaModule) {
        return builder
            .method(ElementMatchers.<MethodDescription>any()) // 拦截任意方法
            .intercept(MethodDelegation.to(TimeInterceptor.class)); // 委托
      }

    };

    AgentBuilder.Listener listener = new AgentBuilder.Listener() {
      @Override
      public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

      }

      @Override
      public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader,
                                   JavaModule javaModule, boolean b, DynamicType dynamicType) {

      }

      @Override
      public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader,
                            JavaModule javaModule, boolean b) {

      }

      @Override
      public void onError(String s, ClassLoader classLoader, JavaModule javaModule,
                          boolean b, Throwable throwable) {

      }

      @Override
      public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

      }
    };

    new AgentBuilder
        .Default()
        .type(ElementMatchers.nameStartsWith("com.jengine.javaagent.bytebuddy")) // 指定需要拦截的类
        .transform(transformer)
        .with(listener)
        .installOn(inst);
  }
}
