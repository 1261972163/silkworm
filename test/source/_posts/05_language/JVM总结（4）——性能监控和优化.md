---
title: JVM总结(4)——性能监控和优化
categories: java
tags: 
  - java
  - jvm
date: 2016/6/3 17:37:25
---

# 1 性能监控

前面建立了一套比较完整的理论基础，把理论应用到生产实践时往往会出现各种问题，给一个系统定位问题时，知识经验是关键基础，数据是依据，工具是运用知识处理数据的手段。这里的数据包括：运行日志、异常堆栈、GC日志、线程快照（threaddump/javacore文件）、堆转储快照（heapdump/hprof文件）等。jdk提供了一系列工具用于监视JVM的工具：

## 1.1 JVM进程状况和配置信息

要列出已装载的JVM，可以使用[jps](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/jps.html)：

	jps [ -q | -m | -l | -v | -V | -Joption] [ hostid ]

使用

	jps -v

可以查看JVM启动时的显示指定的参数列表，但如果想知道未被显示指定的参数的系统默认值，就要使用[jinfo](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/jinfo.html)：

	jinfo [ option ] pid

[jcmd](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/jcmd.html)可以向指定运行中JVM的Main-class传递参数

	jcmd <pid | main class> <command ...|PerfCounter.print|-f file>

同时，还可以用于列出已装载的JVM。下面两条命令效果一致：

	jps -l -m 
	jcmd

## 1.2 运行状态统计

[jstat](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/jstat.html#BEHHGFAE)用于监视JVM各种运行状态信息。包括类加载卸载情况、内存（新生代和老年代的容量、使用情况）、垃圾收集等信息。统计维度比较多：

	-class               显示加载class的数量、所占空间、耗时等信息
	-compiler            显示JVM实时编译的数量等信息。
	-printcompilation    显示HotSpot编译方法的统计

	-gc                  显示各代容量、gc次数和时间。
	-gcnew               显示新生代gc相关信息。
	-gcold               显示老年代gc相关信息。

	-gccapacity          显示JVM内存中三代（young、old、perm）对象的使用和占用大小
	-gcnewcapacity       显示新生代容量
	-gcoldcapacity
	-gcpermcapacity

	-gcutil              显示JVM内存中三代（young、old、perm）gc的情况
	-gccause             显示最近一次GC统计和原因

## 1.3 GC日志

-verbosegc需要在启动时设置为java参数。下列参数可以和-verbosegc配合使用：

	-XX:+PrintGCDetails
	-XX:+PrintGCTimeStamps
	-XX:+PrintHeapAtGC 
	-XX:+PrintGCDateStamps       (from JDK 6 update 4)
	-Xloggc:$LOG_FILE_PATH       (注：$LOG_FILE_PATH为日志文件路径)

gc发生时，gc日志格式为：

	[GC [<collector>: <starting occupancy1> -> <ending occupancy1>, <pause time1> secs] <starting occupancy3> -> <ending occupancy3>, <pause time3> secs]

| Collector	| Name of Collector Used for minor gc |
| :------------- |:-------------|
| starting occupancy1	| GC前年轻代大小 | 
| ending occupancy1	| GC后年轻代大小 | 
| pause time1	| minor GC时，Java应用停顿的时刻| 
| starting occupancy3	| GC前堆大小| 
| ending occupancy3	| GC后对大小| 
| pause time3	| 堆GC使，Java应用停顿的时刻| 

## 1.4 内存分析

如果要查看整个JVM堆内对象情况，可以使用

	jmap -heap [pid]

打印heap的概要信息，GC使用的算法，heap的配置及wise heap的使用情况。但要注意的是，在使用CMS GC 情况下，jmap -heap的执行有可能会导致JAVA 进程挂起。

打印classload的信息，可以使用

	jmap -clstats [pid]

包含每个classloader的名字、活泼性、地址、父classloader和加载的class数量。

对class的统计，可以使用

	jmap -histo[:live] [-F] [pid]

这个命令执行，JVM会先触发gc，然后再统计信息,打印每个class的实例数目、内存占用、类全名信息。VM的内部类名字开头会加上前缀“*”。如果live子参数加上后,只统计活的对象数量。-F可选，在pid没有响应的时候强制使用-histo参数。在这个模式下，live子参数无效。

在对象被回收之前，需要执行finalize方法，而finalize方法的执行又是需要排着队由某个线程来一个个消费的。可以通过

	jmap -finalizerinfo [pid]

打印正等候回收的对象的信息。

要把当前堆的详细情况保存下来。如果设置了JVM 参数：

	-XX:+HeapDumpOnOutOfMemoryError

JVM 就会在发生内存泄露时抓拍下当时的内存状态，也就是我们想要的堆转储文件。

除此之外，还有很多的工具可以帮助我们得到一个堆转储文件（.hprof），例如：

使用jcosole中的MBean

	到MBean>com.sun.management>HotSpotDiagnostic>操作>dumpHeap中，点击 dumpHeap按钮。生成的dump文件在java应用的根目录下面。

使用jmap

	jmap -dump:[live,]format=b,file=<dumpfile> [-F] [pid]

使用hprof二进制形式,输出jvm的heap内容到文件=. live子选项是可选的，假如指定live选项,那么只输出活的对象到文件。-F可选，在pid没有响应的时候强制使用-dump参数。在这个模式下，live子参数无效。

heap如果比较大的话，jmap -dump 就会导致这个过程比较耗时，并且执行的过程中为了保证dump的信息是可靠的，所以会暂停应用。

[jhat](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/jhat.html)是JVM提供的dump文件分析工具，可以将堆中的对象以html的形式显示出来，包括对象的数量，大小等等，并支持对象查询语言OQL，分析相关的应用后，可以通过http://localhost:7000来访问分析结果。

但其分析功能相对比较简陋，有更加优秀的dump分析工具可供选择：

* [Eclipse MemoryAnalyzer](http://www.eclipse.org/mat/)是一个快速并且功能强大的Java heap分析器，能够帮助你查找内存泄漏和减少内存消耗。在File>Acquire Heap Dump>configure>HPROF jmap dump provider设置一下分析应用的JDK，点击相关应用列表来生成heap dump并分析。实践操作可以参考[使用 Eclipse Memory Analyzer 进行堆转储文件分析](https://www.ibm.com/developerworks/cn/opensource/os-cn-ecl-ma/)。

[IBM HeapAnalyzer](http://www.alphaworks.ibm.com/tech/heapanalyzer)是一款免费的JVM内存堆的图形分析工具，它可以有效的列举堆的内存使用状况，帮助分析Java内存泄漏的原因。

需要注意的是，jmap -heap、jmap -dump 、 jmap -histo:live都将对应用的执行产生影响，所以建议如果不是很有必要的话，不要去执行。

在排查问题的时候，对于保留现场信息的操作，可以用gcore [pid]直接保留，这个的执行速度会比jmap -dump快不少，之后可以再用jmap/jstack等从core dump文件里提取相应的信息。

另外，在socket，nio中的有些API中，申请的内存是直接向OS要的，在堆中分析内存是查看不到的，可以通过-XX:MaxDirectMemorySize=来设置应用向OS直接申请的最大内存数。 

## 1.5 堆栈跟踪

[jstack](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/jstack.html#BABGJDIF)可以显示线程快照。

	jstack [ option ] vmid

线程快照时当前JVM内每一条线程正在执行的方法堆栈的集合。生成线程快照的目的是，定位线程出现长时间停顿的原因，如线程间死锁、死循环、请求外部资源导致的长时间等待等。

## 1.6 可视化监控

[jconsole](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/jconsole.html)是GUI监视工具，以图表化的形式显示各种数据。可通过远程连接监视远程的服务器VM。用java写的GUI程序，用来监控VM，并可监控远程的VM，非常易用，而且功能非常强。命令行里打 jconsole，选择进程就可以了。

[jvisualvm](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/jvisualvm.html)同jconsole都是一个基于图形化界面的、可以查看本地及远程的JAVA GUI监控工具，jvisualvm同jconsole的使用方式一样，直接在命令行打入jvisualvm即可启动，不过Jvisualvm界面更美观一些，数据更实时。

# 2 GC调优目标

一个实际运行的java项目需要有以下特征：

	* 通过-Xms和-Xmx选项指定了内存大小
	* 使用了-server选项
	* 系统未产生太多超时日志

如果不具有以上特征，这样的java项目需要进行GC调优。GC调优的目标是：

	降低移动到老年代的对象数量，缩短Full GC执行时间（stop-the-world持续的时间）。

具体调优措施如下（按照重要性由高到低排序）：

1. **减少对象产生的数量**。GC产生的原因是heap内对象过多超过一定的大小导致。控制住对象的数量、大小，就控制住了GC的源头，从而保证了GC的性能。比如尽量少使用String，换用StringBuilder或StringBuffer。但更多时候我们不得不使用一些对象，我们只能换用其他措施。
2. **选择合适的GC收集器**。GC收集器时回收对象的工具和场所，每种GC收集器都有其使用的场景，其性能表现也各不相同，选择合适的GC收集器，针对GC收集器进行后续优化。
3. **调整新生代空间大小**。在Oracle JVM中除了JDK 7及最高版本中引入的G1 GC外，其他的GC都是基于分代回收的。也就是对象会在Eden区中创建，然后不断在Survivor中来回移动。之后如果该对象依然存活，就会被移到老年代中。有些对象，因为占用空间太大以致于在Eden区中创建后就直接移动到了老年代。老年代的GC较新生代会耗时更长，因此减少移动到老年代的对象数量可以降低full GC的频率。减少对象转移到老年代可能会被误解为把对象驻留在新生代，然而这是不可能的，我们只能调整新生代的空间大小，让对象尽可能的在新生代回收掉。
4. **调整老年代空间大小**。Heap主要由新生代和老年代。调整新生代大小的同时也在调整老年代大小。Full GC的单次执行与Minor GC相比，耗时有较明显的增加。如果执行Full GC占用太长时间(例如超过1秒)，在对外服务的连接中就可能会出现超时。通过缩小老年代空间的方式来降低Full GC执行时间，可能会面临OutOfMemoryError或者带来更频繁的Full GC。通过增加老年代空间来减少Full GC执行次数，单次Full GC耗时将会增加。因此，需要为老年代空间设置适当的大小。

**JVM调优**主要调整两类参数：

1. 内存分配参数

	|分类|选项|说明|
	|:-------------|:-------------|:-------------|
	|堆空间|-Xms|JVM启动时占据，JVM会将所用内存尽可能限制在-Xms内，触及-Xms时会引发Full GC。|
	||-Xmx|堆空间最大值|
	|新生代空间|-XX:NewRatio|新生代与老年代的比例|
	||-XX:NewSize|新生代大小|
	||-XX:SurvivorRatio|Eden区与Survivor区的比例|
	||-XX:TargetSurvivorRatio|survivor可使用率，当survivor空间使用率达到这个值时，将对象送入老年代|

2. GC收集器选择参数

	|分类|选项|说明|
	|:-------------|:-------------|:-------------|
	|串行GC|	-XX:+UseSerialGC|新生代、老年代均使用串行收集器|
	|并行GC|-XX:+UseParallelGC |新生代使用并行收集器，老年代使用并行压缩收集器|
	||-XX:+UseParallelGC -XX:+UseParallelOldGC|新生代使用并行收集器，老年代使用并行压缩收集器|
	||-XX:+UseParallelGC -XX:-UseParallelOldGC|新生代使用并行收集器，老年代使用串行收集器|
	||-XX:ParallelGCThreads=< value >|指定并行收集器工作时的线程数|
	|并行压缩GC|参考并行GC||
	|CMS GC	|-XX:+UseConcMarkSweepGC|新生代使用并行收集器，老年代使用“CMS+串行”收集器|
	||-XX:+CMSParallelRemarkEnabled|启用并行重标记|
	||-XX:CMSInitiatingOccupancyFraction=< value >|堆内存使用率回收阈值，默认68%|
	||-XX:+UseCMSInitiatingOccupancyOnly|只在达到阈值时进行CMS回收|
	||-XX:+UseCMSCompactAtFullCollection|CMS回收后进行一次内存压缩|
	|G1|-XX:+UseG1GC|G1收集器|

# 3 GC调优具体过程

调优的具体过程为：

	1. 监控GC状态；
	2. 分析监控结果，确定是否需要调优；
	3. 选择GC类型；
	4. 设置内存大小；
	5. 分析调优结果（至少24小时）；
	6. 如果调优结果理想，将调优配置扩展到该应用其他服务器上。否则，继续调优并分析。

## 3.1 监控GC状态

	$ jstat -gcutil 21719 1s
	S0    S1    E    O    P    YGC    YGCT    FGC    FGCT GCT
	48.66 0.00 48.10 49.70 77.45 3428 172.623 3 59.050 231.673
	48.66 0.00 48.10 49.70 77.45 3428 172.623 3 59.050 231.673

## 3.2 分析监控结果

YGCT/YGC=0.05s，执行一次young gc，平均需要50ms，可接受。

FGCT/FGC=19.68s，执行一次full gc，平均需要19.68s，需要调优。

当然，不能只看平均执行时间，还需要看执行次数等指标。

## 3.3 选择GC类型

通常，CMS GC要比其他GC更快。但发生并发模式错误（CONCURRENT MODE FAILURE）时，CMS GC要比Parallel GC慢。

一般来说，除G1外的GC只适用于10G范围的gc，内存超过10G，这些GC性能会下降很快，建议使用G1。

## 3.4 设置内存大小

以一次full gc后剩余的内存为基础，向上增加单位内存（如500M）。若full gc后剩余300M，则设置内存大小300M（默认使用）+500M（老年代最小值）+200M（空余浮动）。

## 3.5 分析调优结果

主要关注：

* Full GC 执行时间
* Minor GC 执行时间
* Full GC 执行间隔
* Minor GC 执行间隔
* Entire Full GC 执行时间
* Entire Minor GC 执行时间
* Entire GC 执行时间
* Full GC 执行时间
* Minor GC 执行时间


# 参考文献

内存区域

1. [JVM自动内存管理：内存区域基础概念（视频）](http://www.jikexueyuan.com/course/1793.html)
2. [JDK,JRE,JVM区别与联系](http://www.hollischuang.com/archives/78)
3. [Java虚拟机的内存组成以及堆内存介绍](http://www.hollischuang.com/archives/80)
4. [Java之美[从菜鸟到高手演变]之JVM内存管理及垃圾回收](http://blog.csdn.net/zhangerqing/article/details/8214365)
5. [Java堆内存与栈内存的区别](http://blog.yuantops.com/tech/Java-Heap-vs-Stack-memory)
6. [Java 堆和栈的区别）](http://my.oschina.net/leejun2005/blog/486581#OSC_h1_12)

jvm工具

1. [Java Platform, Standard Edition Tools Reference](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/)
2. [JDK内置工具使用](http://blog.csdn.net/fenglibing/article/details/6411924)

垃圾回收

1. [Garbage Collection](http://www.cubrid.org/blog/tags/Garbage%20Collection/)
2. [how-to-monitor-java-garbage-collection](http://www.cubrid.org/blog/dev-platform/how-to-monitor-java-garbage-collection/)
3. [JVM调优：选择合适的GC collector](http://blog.csdn.net/macyang/article/details/8731313)

推荐阅读文献

1. [JVM 自动内存管理：对象判定和回收算法（视频）](http://www.jikexueyuan.com/course/2098.html)
2. [Java 技术，IBM 风格: 垃圾收集策略，第 1 部分（为什么要有不同的 GC 策略？）](https://www.ibm.com/developerworks/cn/java/j-ibmjava2/)
3. [JVM 垃圾回收器工作原理及使用实例介绍（GC算法、垃圾收集器、GC参数）](https://www.ibm.com/developerworks/cn/java/j-lo-JVMGarbageCollection/)
4. [JVM内存回收理论与实现（对象存活的判定）](http://www.infoq.com/cn/articles/jvm-memory-collection)

JVM参数调优

1. [JVM实用参数系列](http://ifeve.com/useful-jvm-flags/)
2. [成为Java GC专家（5）—Java性能调优原则（调优原则）](http://www.importnew.com/13954.html)
3. [JVM 优化经验总结](http://www.ibm.com/developerworks/cn/java/j-lo-jvm-optimize-experience/index.html)
4. [JVM调优总结](http://wenku.baidu.com/view/9202d8f8941ea76e58fa04fe.html)