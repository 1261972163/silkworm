---
title: java基础总结(1)——预览
categories: 编程语言
tags: 
  - java
  - j2se
date: 2016/6/3 17:37:25
---


# J2SE

* 安装JDK
	* [弄清楚各个目录的用途](http://www.cnblogs.com/cynthiahuo/archive/2013/06/03/3115921.html)。
	* 使用命令行工具javac编译，java命令运行程序。
	* Java的包（package）对.class文件所在的路径的影响。
	* [JDK,JRE,JVM区别与联系](http://www.hollischuang.com/archives/78)
* 面向对象编程
	* 基本数据结构、表达式、语句、控制流、函数调用。
	* 类、引用类型和值类型的区别、成员、方法、访问控制、继承、多态、接口、接口实现。
	* 面向对象的基本思想，即对象、消息、封装、继承、多态等，这些通用的内容不是Java特有的。
* Java的异常处理
	* [什么是异常？]()异常时为了在异常情况下使用而设计的，一定要注意设计初衷是用于不正常的情形，而不是为了其他用途，永远不应该用于正常的控制流。
	* [为什么Java中要使用 Checked Exceptions](http://www.iteye.com/topic/2038)。**Checked Exception**是编译时会检查的异常，程序期望调用者能适当地从异常条件中恢复，用于可恢复的情形，要求调用者必须catch住或者将异常传播出去，包括ClassNotFoundException、NoSuchMetodException、IOException等。**RuntimeException**是一种编程错误，只有在运行时才知道是否违反了某种API约定，往往是不可恢复的情形，执行下去有害无益，包括ArithmeticException、ArrayStoreExcetpion、ClassCastException、IndexOutOfBoundsException、NullPointerException等。**Error**一般是与JVM相关的问题，如系统崩溃，虚拟机错误，内存空间不足，方法调用栈溢出等，包括StackOverFlowError、OutOfMemoryError、AssertionError等。
	* [什么时候用特殊返回值而不使用异常，什么时候应该抛出异常，什么时候不抛异常而是处理异常？]()传统的异常处理方法是函数返回特殊值来表示异常，异常与返回值最大的区别是程序执行序的不同。在一个方法执行体内部，返回值是表示一个方法按照既定的逻辑执行完毕，退出当前层次的调用；而异常更像是一种longjump，使得方法跳出既定的逻辑，返回到上层调用者。异常和返回值的执行开销不同。java是一个基于栈的语言，程序的执行是通过调用栈来完成的。在进入一个方法体的时候，会往当前线程调用栈push一个栈帧，该栈帧相当于构建了该方法执行的一个上下文(包含入参，局部变量等)。多个嵌套调用对应就会有多次push栈操作。返回值表示当前层次调用完毕，调用栈只需简单执行pop操作即可。如果是异常，则需要维持整个线程调用层次的栈信息，并依次上溯，判断调用链上的某个上层调用是否能catch该异常。从这点来说，异常的开销要大于返回值的执行开销，所以在编码的时候，不要随便抛出异常，确实是异常情况才考虑抛出异常。
	* [什么时候用特殊返回值而不使用异常，什么时候应该抛出异常？]()如果要捕获异常，应在离异常源近的地方捕获它。捕获了异常就一定要处理异常。如果不能处理异常，不要捕获该异常，应将该异常继续向上抛出，交由更上层的作用域来处理。
	* [什么是pokemon catch及其危害](http://stackoverflow.com/questions/2308979/exception-handling-question)
	* 学习Java1.7的[try-with-resource语句和AutoCloseable接口](http://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html)。
	* [10 Exception handling Best Practices in Java Programming](http://javarevisited.blogspot.com/2013/03/0-exception-handling-best-practices-in-Java-Programming.html)
* 常用的数据结构。
	* 数组。初始化后所用内存空间固定，不能变动。
	* [String、Stringbuffer、StringBulider的区别？](http://blog.csdn.net/weitry/article/details/53292723)
	* 泛型容器（java.util.*）。尤其是java.util.List接口和java.util.ArrayList实现；以及java.util.Map接口和java.util.HashMap实现。
	* [泛型](http://blog.csdn.net/weitry/article/details/52964599)
	* 基础类型和装箱类型的区别，如何进行自动转换的。
* 标准库里的各种工具
	* [反射](http://blog.csdn.net/weitry/article/details/52936835)、Annotation的用法、java.lang.reflect.Proxy的用法（动态绑定）、第三方库CGLib
	* [序列化](http://blog.csdn.net/weitry/article/details/53292825)
	* 包括日期时间、字符串格式化、IO、NIO等。
* JVM
	* [内存模型](https://www.cs.umd.edu/~pugh/java/memoryModel/jsr133.pdf)，看两篇文章[1](http://tutorials.jenkov.com/java-concurrency/java-memory-model.html)、[深入理解Java内存模型（一）——基础](http://ifeve.com/java-memory-model-1/)、自己[总结一下](http://blog.csdn.net/weitry/article/details/53264262)，熟悉顺序一致性、重排序、happens-before法则、JMM、可见性、原子性、lock、volatile等。
	* 内存管理
	* 编译机制
	* [类加载机制](http://www.importnew.com/23742.html)、[双亲委派模型与自定义类加载器](http://www.importnew.com/24036.html)、[字节码](http://www.importnew.com/24088.html)。
	* 垃圾收集算法。要留意即使有垃圾回收的情况下也会发生的内存泄露（如自己设计数组容器，元素是引用，逻辑上删除了元素，但并没有清成null）。注意垃圾回收只能回收内存中的对象，除了内存以外，其它资源不能依靠垃圾回收来关闭。比如，文件、管道、Socket、数据库连接等，垃圾回收是不会帮你关闭的。
	* GC调优
* 多线程
	* Thread和Runnable的用法，以及自带的Executer等基本多线程工具。
	* [java.util.Collections]()和[并发容器](http://blog.csdn.net/weitry/article/details/52964509)
	* [memory model（内存一致性模型）]()和[无锁同步（见java memory model和java.util.concurrent.atomic）]()。
	* 除了“共享内存多线程编程”以外的模型：多进程multi-processing、消息传递message passing等。
	* 并发容器。JDK 5在java.util.concurrent里引入了ConcurrentHashMap，在需要支持高并发的场景，我们可以使用它代替HashMap。但是为什么没有ArrayList的并发实现呢？难道在多线程场景下我们只有Vector这一种线程安全的数组实现可以选择么？为什么在java.util.concurrent 没有一个类可以代替Vector呢？
	* [为什么java.util.concurrent 包里没有并发的ArrayList实现？](http://ifeve.com/why-is-there-not-concurrent-arraylist-in-java-util-concurrent-package/)在java.util.concurrent包中没有加入并发的ArrayList实现的主要原因是：很难去开发一个通用并且没有并发瓶颈的线程安全的List。像ConcurrentHashMap这样的类的真正价值（The real point / value of classes）并不是它们保证了线程安全。而在于它们在保证线程安全的同时不存在并发瓶颈。举个例子，ConcurrentHashMap采用了锁分段技术和弱一致性的Map迭代器去规避并发瓶颈。所以问题在于，像“Array List”这样的数据结构，你不知道如何去规避并发的瓶颈。拿contains() 这样一个操作来说，当你进行搜索的时候如何避免锁住整个list？另一方面，Queue 和Deque (基于Linked List)有并发的实现是因为他们的接口相比List的接口有更多的限制，这些限制使得实现并发成为可能。CopyOnWriteArrayList是一个有趣的例子，它规避了只读操作（如get/contains）并发的瓶颈，但是它为了做到这点，在修改操作中做了很多工作和修改可见性规则。此外，修改操作还会锁住整个List，因此这也是一个并发瓶颈。所以从理论上来说，CopyOnWriteArrayList并不算是一个通用的并发List。
	* 什么是协程？一个第三方协程库Quasar。
* 网络编程
	* TCP/IP协议
	* Socket编程
	* 了解[c10k问题](http://www.kegel.com/c10k.html)
	* 熟练使用NIO，学习单线程轮询式IO复用（Selector），试着用java.nio写一个文件服务器。
	* 了解一下操作系统（包括C语言）提供的select, poll, epoll, kqueue等接口。
	* 了解其他的通信库，如netty等。熟练使用netty。
	* HTTP协议，用Java进行HTTP的客户端编程。