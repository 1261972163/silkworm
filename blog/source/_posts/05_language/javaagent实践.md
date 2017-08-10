---
title: javaagent实践
categories: 编程语言
tags:
  - java
  - j2se
date: 2016/6/30 15:14:25
---



# 1 agent

代理 (agent) 是在你的main方法前的一个拦截器 (interceptor)，也就是在main方法执行之前，执行agent的代码。agent的代码与你的main方法在同一个JVM中运行，并被同一个system classloader装载，被同一的安全策略 (security policy) 和上下文 (context) 所管理。叫代理（agent）这个名字有点误导的成分，它与我们一般理解的代理不大一样。java agent使用起来比较简单。

## 怎样写一个java agent?

只需要实现premain这个方法

    public static void premain(String agentArgs, Instrumentation inst)

JDK 6 中如果找不到上面的这种premain的定义，还会尝试调用下面的这种premain定义：

    public static void premain(String agentArgs)

## MANIFEST.MF

Agent 类必须打成jar包，然后里面的 META-INF/MAINIFEST.MF 必须包含 Premain-Class这个属性。

下面是一个MANIFEST.MF的例子：

    Manifest-Version: 1.0 Premain-Class:MyAgent1 Created-By:1.6.0_06

然后把MANIFEST.MF 加入到你的jar包中。

## -javaagent

**javaagent顺序**

 一个java程序中-javaagent这个参数的个数是没有限制的，所以可以添加任意多个java agent。所有的java agent会按照你定义的顺序执行。例如：

    java -javaagent:MyAgent1.jar -javaagent:MyAgent2.jar -jar MyProgram.jar

假设MyProgram.jar里面的main函数在MyProgram中。MyAgent1.jar和MyAgent2.jar这2个jar包中实现了premain的类分别是MyAgent1, MyAgent2。程序执行的顺序将会是

    MyAgent1.premain -> MyAgent2.premain -> MyProgram.main

**agentArgs参数**

每一个java agent 都可以接收一个字符串类型的参数，也就是premain中的agentArgs，这个agentArgs是通过java option中定义的。如：

    java -javaagent:MyAgent2.jar=thisIsAgentArgs -jar MyProgram.jar

MyAgent2中premain接收到的agentArgs的值将是”thisIsAgentArgs”

**Instrumentation参数**

通过参数中的Instrumentation inst，添加自己定义的ClassFileTransformer，来改变class文件。
这里自定义的Transformer实现了transform方法，在该方法中提供了对实际要执行的类的字节码的修改，甚至可以达到执行另外的类方法的地步。通过java agent就可以不用修改原有的java程序代码，通过agent的形式来修改或者增强程序了，或者做热启动等等。























xxx
