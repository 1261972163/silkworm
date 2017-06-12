---
title: JVM总结(1)——自动内存管理机制
categories: 编程语言
tags: 
  - java
  - jvm
date: 2016/6/3 17:37:25
---

# 1. 内存区域

JVM内存包括以下运行时数据区域：

* **程序计数器**。使用的内存很小，线程私有，可看作是当前线程所执行字节码的行号指示器。JVM规范中唯一一个没有规定OutOfMemoryError的区域。
* **栈**。线程私有，生命周期和线程相同。每个java方法执行时对应一个栈帧，用于存储局部变量表、操作数栈、动态链接、方法出口等信息，进入方法时创建栈帧，出方法时销毁栈帧。线程请求的栈深度超过JVM允许的深度，抛出StackOverflowError。如果栈可以动态扩展，扩展时无法申请到足够内存，抛出OutOfMemoryError异常。
* **堆**。所有线程共享的内存区域。几乎所有的对象实例以及数组都在堆上分配。如果堆中没有足够内存完成实例分配，同时堆无又法扩展，抛出OutOfMemoryError异常。
* **方法区**。所有线程共享的内存区域。存储已被JVM加载的类信息、常量、静态变量、即时编译器编译后的代码等数据。方法区无法满足内存分配需求时，抛出OutOfMemoryError异常。
* **本地方法栈**。和JVM栈作用类似，区别在于本地方法栈为JVM使用到的Native方法服务。和JVM栈一样会出现StackOverflowError和OutOfMemoryError异常。

另外，需要注意直接内存（Direct Memory），直接内存不是JVM规范定义的内存区域。但这部分内存也经常使用（如NIO使用Native函数库直接分配堆外内存），可能导致OutOfMemoryError异常。

# 2. 垃圾回收

堆为新对象分配存储空间，如果分配了空间，而又不回收空间，空间迟早会被用完，然后OutOfMemoryError。Java并不在代码层提供内存释放的API，而是由JVM去自主清理内存，将**不可达的对象**清理掉，这个过程就叫GC。

## 如何判断对象不可达？

如何判断对象不可达？最经典的是**引用计数算法**，给对象中添加一个引用计数器，有地方引用它时，计数器值加1，引用失效时，计数器值减1，计数器值为0表示对象不再被使用，即不可达。当计数器在一段时间内保持为0时，该对象就认为是可以被回收了。但是，这个算法有明显的缺陷：当两个对象相互引用，但是二者已经没有作用时，按照常规，应该对其进行垃圾回收，但是其相互引用，又不符合垃圾回收的条件，因此无法完美处理这块内存清理。因此Sun的JVM并没有采用引用计数算法来进行垃圾回收。因此在java中，单纯使用引用计数法实现垃圾回收是不可行的。

目前主流的商用程序语言都是使用**可达性分析（也叫根搜索算法） **来判定对象是否存活。基本思想是，设立若干种根对象（GC Roots）作为起始点，从这些节点开始向下搜索，搜索路径成为**引用链**。对于一个对象，当任何一个GC Roots均没有引用链到该对象时，判定该对象不可达，可以被回收。JAVA中可以当做GC Roots的对象有以下几种：

	1. 栈（栈帧中的本地变量表）中引用的对象。
	2. 方法区中的静态成员。
	3. 方法区中的常量引用的对象（全局变量）
	4. 本地方法栈中JNI（一般说的Native方法）引用的对象。
	注：第一和第四种都是指方法的本地变量表，第二种表达的意思比较清晰，第三种主要指的是声明为final的常量值。

## 什么是引用？

JDK1.2之前，如果reference类型的数据中存储的数值代表的是另一块内存的起始地址，就称这块内存代表着一个引用，在这种引用定义下，对象只有被引用和未被引用两种状态，不能完整的描述不同用途的对象，如一些缓存对象：当内存空间足够时，保留在内存；如果垃圾回收之后，内存空间紧张，就抛弃这些对象。JDK1.2之后，引用概念得到扩展（按引用强度依次减弱）：

* **强引用**。普遍存在，类似“Object obj = new Object();”的引用。强引用对象，GC不回收。
* **软引用**。SoftReference。系统内存溢出之前，GC回收软引用对象，回收后仍然没有足够内存，抛OutOfMemoryError异常。
* **弱引用**。WeakReference。弱引用对象只能生存到下一次GC之前。
* **虚引用**。PhantomReference。虚引用不对对象生存时间产生影响，也无法通过虚引用来取得一个对象实例。其唯一目的是：虚引用对象在被GC时收到一个系统通知。

什么是**逃逸回收**？被判定不可达的对象，只是处于“缓刑”阶段，真正宣告对象死亡，至少需要经历两次标记过程：

可达性分析出的不可达对象会被第一次标记并且进行一次筛选，筛选条件是该对象是否有必要执行finalize()方法。当对象没有覆盖finalize()方法，或者finalize()方法以及被JVM调用过，JVM将这两种情况视为“没有必要执行”。

如果被判定为有必要执行finalize()方法，对象将被加入F-Queue队列中，然后由JVM自动建立的低优先级Finalizer线程去依次触发对象的finalize()方法，但不保证它运行结束。然后GC将对F-Queue中的对象进行二次小规模标记，如果在finalize()方法执行时，对象重新与引用链上的任何一个对象建立关联，二次标记时它将被移除出“即将回收”的集合，这就是“逃逸回收”。如果没有出现逃逸回收，那么对象就真被回收了。

## JVM是如何回收的？

JVM自主GC是在**弱分代假设**的前提下设计的：

	1. 大部分新对象立即不可达；
	2. 只存在少量old对象对young对象的引用。

为了保证这两个假设的有效性，HotSpot VM 将Heap分为两类区域：

* **Young Generation**。绝大多数新对象会被分配到这里，由于大部分对象在创建后会立即不可达，所以很多对象被创建在新生代，然后消失。对象从该区消失的过程叫“minor GC”或“young GC”。

	Young Generation又被分为3个区Eden、From和To。执行如下：

		1. 绝大多数New Object会存放在Eden；
		2. Eden满，或者新对象大小 > Eden所剩空间，Eden执行GC，GC后存活的对象被移动到From；
		3. 此后Eden执行GC后幸存的对象会被堆积在From；
		4. 当From饱和，From执行GC，GC幸存的对象会被移动到To，然后清空From，并将To置为From，From置为To；
		5. 在以上步骤中重复几次依然存活的对象，就会被移动到Old Generation。

* **Old Generation**。从Young Generation幸存下来的对象移动到该区。对象从该区消失的过程叫“major GC”或“full GC”通常该区比Young Generation要大，发生GC的频率要比Young Generation小。Old Generation的GC事件基本上是在空间已满时发生，执行的过程根据GC类型不同而不同。

方法区如何进行回收？JVM规范不要求对方法区进行GC，而且方法区GC性价比很低。HotSpot JVM的永久代是有GC的
，**永久代GC**主要回收废弃常量和无用类。回收废弃常量和回收堆对象类似，无引用时回收。JVM可以对无用类进行回收，判定无用类需要满足三个条件：

	1. 该类所有实例均已被回收。
	2. 加载该类的ClassLoader已被回收。
	3. 该类对应的java.lang.Class对象没有在任何地方被引用，无法在任何地方通过反射访问该类的方法。

## 有哪些回收策略？

GC可以有多种不同的实现，这里简单介绍下主要的GC算法和核心思想。

1. **标记-清除算法（Mark-Sweep）**。是现代垃圾回收算法的基础。分两个阶段：标记和清除。标记阶段，通过根节点，标记所有从根节点开始的可达对象，未被标记的对象就是垃圾对象。清除节点，清除所有垃圾对象。这个算法效率不高，而且在清理完成后会产生内存碎片，这样，如果有大对象需要连续的内存空间时，还需要进行碎片整理，所以，此算法需要改进。
2. **复制算法（Copying）**。将原有内存分为两块，每次只使用一块，垃圾回收时，将正使用内存中存活的对象复制到未使用的内存块中，之后，清除正使用内存块中所有对象，交换两个内存的角色，完成垃圾回收。存活对象少、垃圾对象多的前提下，复制算法效率高。又由于对象是在垃圾回收过程中统一被复制到新内存空间中，可保证回收后的内存空间无碎片。优点很明显，但缺点是内存会折半，单纯的复制算法也让人很难接受。
3. **标记-压缩算法（Mark-Compact）**。和标记-清除算法前半段一样，标记阶段，通过根节点，标记所有从根节点开始的可达对象，未被标记的对象就是垃圾对象。然后将所有存活对象压缩到内存的一端，使得内存连续，之后清理边界外所有内存空间。避免了内存碎片，又不需要两块相同大小的内存，性价比较高。
4. **增量算法（Incremental Colllecting）**。对于大部分垃圾回收算法，垃圾回收时应用将stop-the-world。stop-the-world状态下，应用所有线程挂起，暂停一切正常工作，等待垃圾会回收完成。垃圾回收的时间长，应用被挂起的时间就长，严重影响用户体验和系统性能。增量算法，每次圾收集线程只收集一小片区域的内存空间，接着切换到应用线程，以此反复，直到垃圾收集完成。减少了系统停顿时间，但频繁的线程切换和上下文转换，会使垃圾收集的总成本上升，系统吞吐量下降。
5. **分代算法（Generational Collecting）**。上面的算法都有自己的优缺点，分代就是根据对象特点将内存划分成几块，根据每块内存区域的特点，选择合适的算法回收，以提高垃圾回收效率。Young Generation的对象特点是朝生夕灭，大约90%的新对象会很快被回收，因此适合使用复制算法。Old Generation的对象特点是存活时间长，存活率几乎达到100%，不适合复制算法，可选择标记-压缩算法。

## 有哪些垃圾收集器？

JVM对垃圾回收器如何实现并没有任何规定，因此不同厂商、不同版本的JVM根据自己应用特点和要求组合出各个年代所使用的收集器。主要关注的是：

* **停顿时间**。可达性分析必须在一个能确保一致性的快照中进行，这就要求停顿所有Java执行线程，这种现象为“Stop the world”，这段时间就是停顿时间。停顿时间越短越适合需要与用户交互的程序，良好的响应速度能提升用户体验。
* **吞吐量**。吞吐量 = 运行用户代码时间 / （运行用户代码时间 + 垃圾收集时间）。高吞吐量则可以高效率地利用CPU时间，尽快完成程序的运算任务，主要适合在后台运算而不需要太多交互的任务。

JDK8 HotSpot 有7种GC收集器：

* **年轻代GC**：Serial、ParNew、Parallel Scavenge
* **老年代GC**：Serial Old(MSC)、CMS、Parallel Old
* **G1**。

1. **Serial收集器**。新生代收集器，单线程、独占式的垃圾回收。使用复制算法，实现简单，处理逻辑高效，无线程切换开销。硬件不是特别优越的场合，性能表现好。串行收集器运行时，应用所有线程停止工作，进入等待，这种现象为“Stop the world”。

![serial gc](http://img.blog.csdn.net/20161021125335259)

2. **ParNew收集器**。新生代收集器，使用复制算法，原理是将串行收集器多线程化，实现多线程、独占式的垃圾回收。关注吞吐量，在并发能力强的CPU上效果比串行收集器好。若多线程压力大，则并行收集器可能还没有串行收集器好。并行收集器使用在对吞吐量有要求的应用上。

![parallel gc](http://img.blog.csdn.net/20161021125518869)

3. **Parallel Scavenge收集器**。新生代收集器，使用复制算法，并发多线程、独占式垃圾回收，目标是达到一个可控制的吞吐量。提供了两个参数：控制最大垃圾集停顿时间的-XX:MaxGCPauseMillis，设置吞吐量大小的-XX:GCTimeRatio。

4. **Serial Old收集器**。老年代收集器，使用标记-压缩算法，单线程、独占式垃圾回收。但老年代对象较多，回收比较耗时，造成的停顿时间更长，所以一般不建议在老年代使用串行收集器。

5. **Parallel Old**。老年代收集器。Parallel Scavenge收集器的老年代版本，实现标记-压缩算法。GC时经历标记-整理-压缩的过程，和标记-清除-压缩算法过程略有区别。

6. **CMS收集器**。老年代收集器。CMS（并发标记清除收集器）使用标记-清除算法，是并发、非独占式的垃圾回收。主要工作步骤为：

		（1）初始标记。独占系统资源。标记出需要回收的对象。只标记出GC ROOTS（如classloader）能**直接关联**到的对象，速度快。
		（2）并发标记。非独占式。标记出需要回收的对象。进行GC ROOTS根搜索算法，判定对象是否存活。
		（3）重新标记。独占系统资源。标记出需要回收的对象。为了修正并发标记期间，因用户程序继续运行而导致标记产生变动的那一部分对象的标记记录，这个阶段的停顿时间会被初始标记阶段稍长，但比并发标记阶段要短。
		（4）并发清除。非独占式。
		（5）并发重置。非独占式。垃圾回收完成后，重新初始化CMS数据结构和数据，为下一次垃圾回收做准备。

CMS关注系统停顿时间，被称为并发低停顿收集器，但它并非完美。CMS比其他GC类型使用更多的内存和CPU，对CPU资源非常敏感，默认启动的回收线程数是 (CPU数量 + 3)/4。CPU在4个以上时，并发回收时垃圾收集线程不少于25%的CPU资源，并随CPU数量增加而下降。CPU数量少于4个时，本来用户程序CPU负载就很大，还要分出一半运算能力去执行收集器线程，总吞吐量下降，让人无法接受啊。

CMS是在老年代空间使用达到一定的阈值（jdk6默认92%）之后启动回收的。CMS并发清理阶段用户线程仍然运行，可能会产生新的垃圾，这就是“**浮动垃圾**”。但这部分垃圾是在标记之后出现的，只能等到下次GC回收，如果老年代对象增长过快导致剩余8%的空间消耗过快，内存空间不足，就会出现“**Concurrent Mode Failure**”失败，JVM启动后备预案，临时启用Serial Old收集器重新进行老年代垃圾回收，这样停顿时间就很长了。这个阈值可以通过-XX:CMSInitiatingOccupancyFraction设置。

CMS使用“标记-清除”算法，会产生大量碎片。默认不进行压缩，需要手动设置+UseCMSCompactAtFullCollection指定执行多少次不压缩的Full GC后来一次带压缩GC。

![cms gc](http://img.blog.csdn.net/20161021125551461)

7. **G1收集器**。G1收集器（Garbage First (G1) GC），不同于其他的分代回收算法、G1将堆空间划分成了互相独立的区块。每块区域既有可能属于O区、也有可能是Y区，且每类区域空间可以是不连续的（对比CMS的O区和Y区都必须是连续的）。G1基于标记-压缩算法。包含以下阶段（其中有些阶段是属于Young GC的）：

		（1）初始并行阶段（Initial Marking Phase）。属于Young GC范畴，是stop-the-world活动。对持有老年代对象引用的Survivor区（Root区）进行标记。
		（2）Root区扫描（Root Region Scanning）。扫描Survivor区中的老年代对象引用，该阶段发生在应用运行时，必须在Young GC前完成。
		（3）并行标记（Concurrent Marking）。找出整个堆中存活的对象，对于空区标记为“X”。该阶段发生在应用运行时，同时该阶段活动会被Young GC打断。
		（4）重标记（Remark）。清除空区，重计算所有区的存活状态（liveness），是stop-the-world活动。
		（5）清除（Cleanup）。选择出存活状态低的区进行收集。计算存活对象和空区，是stop-the-world活动。更新记录表，是stop-the-world活动。重置空区，将其加入空闲列表，是并行活动
		（6）复制（Copying）。该阶段是stop-the-world活动，负责将存活对象复制到新的未使用的区。可以发生在年轻代区，日志记录为[GC pause (young)]。也可以同时发生在年轻代区和老年代区，日志记录为[GC Pause (mixed)]。

虽然在清理这些区块时G1仍然需要暂停应用线程、但可以用相对较少的时间优先回收包含垃圾最多区块。这也是为什么G1命名为Garbage First的原因：第一时间处理垃圾最多的区块。在以下场景下G1更适合：

	* 服务端多核CPU、JVM内存占用较大的应用（至少大于4G）
	* 应用在运行过程中会产生大量内存碎片、需要经常压缩空间
	* 想要更可控、可预期的GC停顿周期；防止高并发下应用雪崩现象

# 3. 性能监控和优化

前面建立了一套比较完整的理论基础，把理论应用到生产实践时往往会出现各种问题，给一个系统定位问题时，知识经验是关键基础，数据是依据，工具是运用知识处理数据的手段。这里的数据包括：运行日志、异常堆栈、GC日志、线程快照（threaddump/javacore文件）、堆转储快照（heapdump/hprof文件）等。jdk提供了一系列工具用于监视JVM的工具：

1. JVM进程状况和配置信息

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

2. 运行状态统计

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

3. GC日志

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

4. 内存分析

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

5. 堆栈跟踪

	[jstack](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/jstack.html#BABGJDIF)可以显示线程快照。

		jstack [ option ] vmid

	线程快照时当前JVM内每一条线程正在执行的方法堆栈的集合。生成线程快照的目的是，定位线程出现长时间停顿的原因，如线程间死锁、死循环、请求外部资源导致的长时间等待等。

6. 可视化监控

	[jconsole](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/jconsole.html)是GUI监视工具，以图表化的形式显示各种数据。可通过远程连接监视远程的服务器VM。用java写的GUI程序，用来监控VM，并可监控远程的VM，非常易用，而且功能非常强。命令行里打 jconsole，选择进程就可以了。

	[jvisualvm](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/jvisualvm.html)同jconsole都是一个基于图形化界面的、可以查看本地及远程的JAVA GUI监控工具，jvisualvm同jconsole的使用方式一样，直接在命令行打入jvisualvm即可启动，不过Jvisualvm界面更美观一些，数据更实时。

# 4. GC调优目标

一个实际运行的java项目需要有以下特征：

	* 通过-Xms和-Xmx选项指定了内存大小
	* 使用了-server选项
	* 系统未产生太多超时日志

如果不具有以上特征，这样的java项目需要进行GC调优。**GC调优目标**是：降低移动到老年代的对象数量，缩短Full GC执行时间（stop-the-world持续的时间）。具体调优措施如下（按照重要性由高到低排序）：

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

# 5. GC调优具体过程

调优的具体过程为：

1. 监控GC状态；
2. 分析监控结果，确定是否需要调优；
3. 选择GC类型；
4. 设置内存大小；
5. 分析调优结果（至少24小时）；
6. 如果调优结果理想，将调优配置扩展到该应用其他服务器上。否则，继续调优并分析。

**1.监控GC状态**

	$ jstat -gcutil 21719 1s
	S0    S1    E    O    P    YGC    YGCT    FGC    FGCT GCT
	48.66 0.00 48.10 49.70 77.45 3428 172.623 3 59.050 231.673
	48.66 0.00 48.10 49.70 77.45 3428 172.623 3 59.050 231.673

**2. 分析监控结果**

YGCT/YGC=0.05s，执行一次young gc，平均需要50ms，可接受。

FGCT/FGC=19.68s，执行一次full gc，平均需要19.68s，需要调优。

当然，不能只看平均执行时间，还需要看执行次数等指标。

**3. 选择GC类型**

通常，CMS GC要比其他GC更快。但发生并发模式错误（CONCURRENT MODE FAILURE）时，CMS GC要比Parallel GC慢。

一般来说，除G1外的GC只适用于10G范围的gc，内存超过10G，这些GC性能会下降很快，建议使用G1。

**4. 设置内存大小**

以一次full gc后剩余的内存为基础，向上增加单位内存（如500M）。若full gc后剩余300M，则设置内存大小300M（默认使用）+500M（老年代最小值）+200M（空余浮动）。

**5. 分析调优结果**

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