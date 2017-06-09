---
title: Kafka实践（2）——原理
categories: 
tags: 
	- bigdata
	- kafka
date: 2017/6/4 09:51:25
---


# Kafka是什么

Kafka是最初由Linkedin公司开发，是一个分布式、分区的、多副本的、多订阅者，基于zookeeper协调的分布式日志系统(也可以当做MQ系统)，常见可以用于web/nginx日志、访问日志，消息服务等等，Linkedin于2010年贡献给了Apache基金会并成为顶级开源项目。

主要设计目标如下：

* 以时间复杂度为O(1)的方式提供消息持久化能力，即使对TB级以上数据也能保证常数时间复杂度的访问性能。
* 高吞吐率。即使在非常廉价的商用机器上也能做到单机支持每秒100K条以上消息的传输。
* 支持Kafka Server间的消息分区，及分布式消费，同时保证每个Partition内的消息顺序传输。
* 同时支持离线数据处理和实时数据处理。
* Scale out：支持在线水平扩展。

# 组件框架

kafka的核心组件是Broker、Producer和consumer。

![](/resources/kafka/kafka_architecture.png)

* Producer。向Kafka服务器发送/生产消息的组件。
* Consumer。从Kafka服务器取出/消费消息的组件。
* Broker。kafka服务器。接收Producer和Consumer的请求，并把Message持久化到本地磁盘。Cluster会选举出一个Broker来担任Controller，负责处理Partition的Leader选举，协调Partition迁移等工作。

以上组件在分布式环境下均可以是多个，支持故障转移。同时ZK仅和broker和consumer相关。值得注意的是broker的设计是无状态的，消费的状态信息依靠消费者自己维护，通过一个offset偏移量。

# 文件存储机制

Broker是Kafka消息中间件处理结点，一个Kafka节点就是一个broker，多个broker可以组成一个Kafka集群。内部维护着：

* Topic：划分Message的逻辑概念，一个Topic可以分布在多个Broker上。
* Partition：topic物理上的分组，每个Topic都至少有1个Partition，每个Partition是有序队列，消息按照序号顺序存储。
* Segment：partition物理上由多个segment组成。
* offset：每个partition都由一系列有序的、不可变的消息组成，这些消息被连续的追加到partition中。partition中的每个消息都有一个连续的序列号叫做offset,用于partition唯一标识一条消息.

## topic中partition存储分布

假设实验环境中Kafka集群只有一个broker，xxx/message-folder为数据文件存储根目录，在Kafka broker中server.properties文件配置(参数log.dirs=xxx/message-folder)，例如创建2个topic名称分别为report_push、launch_info, partitions数量都为partitions=4。存储路径和目录规则为：

	xxx/message-folder
	      |--report_push-0
	      |--report_push-1
	      |--report_push-2
	      |--report_push-3
	      |--launch_info-0
	      |--launch_info-1
	      |--launch_info-2
	      |--launch_info-3

在Kafka文件存储中，同一个topic下有多个不同partition，每个partition为一个目录，partiton命名规则为topic名称+有序序号，第一个partiton序号从0开始，序号最大值为partitions数量减1。

如果是多broker分布情况，请参考[kafka集群partition分布原理分析](http://blog.csdn.net/lizhitao/article/details/41778193)

## partiton中文件存储方式

partition中文件存储方式如下:

	|-- report_push-0
		|-- segment file1
		|-- segment file2
		|-- segment file3
		|-- ...
		|-- segment filen

* 每个partion(目录)相当于一个巨型文件被平均分配到多个大小相等segment(段)数据文件中。
* 每个段segment file消息数量不一定相等，这种特性方便old segment file快速被删除。
* 每个partiton只需要支持顺序读写就行了，segment文件生命周期由服务端配置参数决定。
* 这样做的好处就是能快速删除无用文件，有效提高磁盘利用率。

## partiton中segment文件存储结构

上面说的segment file其实是由索引文件（.index）和数据文件（.log）组成，并且成对出现。segment file的名称是19位数字，没有数字用0填充。partion全局的第一个segment从0开始，后续每个segment文件名为上一个segment文件最后一条消息的offset值。

	|-- report_push-0
			|-- 0000000000000000000.index
			|-- 0000000000000000000.log
			|-- 0000000000000368769.index
			|-- 0000000000000368769.log
			|-- 0000000000000737377.index
			|-- 0000000000000737377.log

以0000000000000368769.{segment file}文件为例，说明segment中index<—->data file对应关系物理结构如下：

![index和数据映射关系](/resources/kafka/0000000000000368769_segment_file.png)

上述索引文件存储大量元数据，数据文件存储大量消息，索引文件中元数据指向对应数据文件中message的物理偏移地址。

其中以索引文件中元数据3,497为例，依次在数据文件中表示第3个message(在全局partiton表示第368772个message)、以及该消息的物理偏移地址为497。

从上述图3了解到segment data file由许多message组成，下面详细说明message物理结构如下：

![](/resources/kafka/kafka_message_payload.png)

参数说明：

|关键字|解释说明|
|:--|:--|
|8 byte offset	|在parition(分区)内的每条消息都有一个有序的id号，这个id号被称为偏移(offset),它可以唯一确定每条消息在parition(分区)内的位置。即offset表示partiion的第多少message|
|4 byte message size	|message大小|
|4 byte CRC32	|用crc32校验message|
|1 byte “magic"	|表示本次发布Kafka服务程序协议版本号|
|1 byte “attributes"	|表示为独立版本、或标识压缩类型、或编码类型。|
|4 byte key length	|表示key的长度,当key为-1时，K byte key字段不填|
|K byte key	|可选|
|value bytes payload	|表示实际消息数据。|

## 在partition中如何通过offset查找message

例如读取offset=368776的message，需要通过下面2个步骤查找。

1. 第一步查找segment file

	其中00000000000000000000.index表示最开始的文件，起始偏移量(offset)为0.第二个文件00000000000000368769.index的消息量起始偏移量为368770 = 368769 + 1.同样，第三个文件00000000000000737337.index的起始偏移量为737338=737337 + 1，其他后续文件依次类推，以起始偏移量命名并排序这些文件，只要根据offset **二分查找**文件列表，就可以快速定位到具体文件。

	当offset=368776时定位到00000000000000368769.index|log

2. 第二步通过segment file查找message

	通过第一步定位到segment file，当offset=368776时，依次定位到00000000000000368769.index的元数据物理位置和00000000000000368769.log的物理偏移地址，然后再通过00000000000000368769.log顺序查找直到offset=368776为止。

从index和数据映射关系图可知，这样做的优点，segment index file采取稀疏索引存储方式，它减少索引文件大小，通过mmap可以直接内存操作，稀疏索引为数据文件的每个对应message设置一个元数据指针,它比稠密索引节省了更多的存储空间，但查找起来需要消耗更多的时间。

# 消息交付保证

消息交付是否可靠，有以下几种保证：

* At least once。至少一次。消息绝不会丢失，但有可能重新发送。（request.required.acks=1或-1）。Kafka的默认保证。
* At most once。最多一次。消息可能丢失，但永远不会重发。通过设置Producer异步提交可以实现。（request.required.acks=0）。
* Exactly once。传递一次且仅一次。要求利用外部存储系统配合Kafka的offset来保证。

# 高吞吐量

高吞吐量依赖于OS文件系统的页缓存、sendfile技术和线性读写磁盘：

* PageCache

依赖OS的页缓存能大量减少IO，高效利用内存来作为缓存。当上层有写操作时，操作系统只是将数据写入OS的PageCache，同时标记Page属性为Dirty。当读操作发生时，先从PageCache中查找，如果发生缺页才进行磁盘调度，最终返回需要的数据。实际上PageCache是把尽可能多的空闲内存都当做了磁盘缓存来使用。同时如果有其他进程申请内存，回收PageCache的代价又很小。

* zero-copy

传统网络IO，OS 从硬盘把数据读到内核区的PageCache，用户进程把数据从内核区Copy到用户区。然后用户进程再把数据写入到Socket，数据流入内核区的Socket Buffer上。OS 再把数据从Buffer中Copy到网卡的Buffer上，这样完成一次发送。整个过程共经历两次Context Switch，四次System Call。

同一份数据在内核Buffer与用户Buffer之间重复拷贝，效率低下。其中2、3两步没有必要，完全可以直接在内核区完成数据拷贝。这也正是Sendfile所解决的问题，经过Sendfile优化后，整个I/O过程就变成了下面这个样子。
  
* 磁盘顺序写

磁盘线性读写要比随机读写快很多。顺序IO不仅可以利用RAID技术带来很高的吞吐量，同时可以基于文件的读和追加来构建持久化队列，利用队列来提供常量时间O(1)时间复杂度的put和get。

* 消息压缩

Producer支持End-to-End的压缩。数据在本地压缩后放到网络上传输，在Broker一般不解压(除非指定要Deep-Iteration)，直至消息被Consume之后在客户端解压。

当然用户也可以选择自己在应用层上做压缩和解压的工作(毕竟Kafka目前支持的压缩算法有限，只有GZIP和Snappy)，不过这样可能造成效率的意外降低！

 Kafka的End-to-End压缩与MessageSet配合在一起工作效果最佳，上面的做法直接割裂了两者间联系。至于道理其实很简单，压缩算法中一条基本的原理“重复的数据量越多，压缩比越高”。大多数情况下输入数据量大一些会取得更好的压缩比。

# 消息生产者Producer

producer只向leader partition发送消息。通过load balance，将消息发送到不同的partition。

producer采用push模式，任性地不需要考虑consumer是否能处理。如果要保证低延迟，就需要consumer快速处理，一般是在consumer端进行缓存。

为了保证低延迟，producer一次只发送一条数据，比较浪费，可采用批量发送。

# 消息消费者Consumer

kafka采用pull模式。push模式和pull模式相比，push模式很难适应消费速率不同的消费者，因为消息发送速率是由broker决定的。push模式的目标是尽可能以最快速度传递消息，但是这样很容易造成consumer来不及处理消息，典型的表现就是拒绝服务以及网络拥塞。而pull模式则可以根据consumer的消费能力以适当的速率消费消息，可使消费速率最大化。

基于pull模式的另一个优点是，它有助于积极的批处理的数据发送到消费者。基于push模式必须选择要么立即发送请求或者积累更多的数据，稍后发送它，无论消费者是否能立刻处理它，如果是低延迟，这将导致短时间只发送一条消息，不用缓存，这是实在是一种浪费，基于pull的设计解决这个问题，消费者总是pull在日志的当前位置之后pull所有可用的消息（或配置一些大size），所以消费者可设置消费多大的量，也不会引入不必要的等待时间。

# 高可用

kafka引入replication和leader机制来实现高可用。

将Topic的Partition的副本分配到集群中的其他Broker上，允许故障时请求自动转到副本上。分配Replica的算法如下：

    (1) 将n个Broker和待分配的Partition排序
    (2) 第i个Partition分配到第i%n个Broker
    (3) 第i个Partition的第j个Replica分配到第(i + j) %n个Broker

Leader负责数据读写，Follower只向Leader顺序Fetch数据（N条通路）。Leader会跟踪与其保持同步的Replica列表，该列表称为ISR（即in-sync Replica）。Consumer读消息也是从Leader读取，只有被commit过的消息才会暴露给Consumer。

当Producer发布消息到某个Partition时：

    (1) 先通过ZooKeeper找到该Partition的Leader，然后无论该Topic的Replication Factor为多少（也即该Partition有多少个Replica），Producer只将该消息发送到该Partition的Leader。Leader会将该消息写入其本地Log。
    (2) 每个Follower都从Leader pull数据。这种方式上，Follower存储的数据顺序与Leader保持一致。Follower在收到该消息并写入其Log后，向Leader发送ACK。
    (3) 一旦Leader收到了ISR中的所有Replica的ACK，该消息就被认为已经commit了，Leader将增加HW并且向Producer发送ACK。
    (4) 为了提高性能，每个Follower在接收到数据后就立马向Leader发送ACK，而非等到数据写入Log中。因此，对于已经commit的消息，Kafka只能保证它被存于多个Replica的内存中，而不能保证它们被持久化到磁盘中，也就不能完全保证异常发生后该条消息一定能被Consumer消费。

当leader宕机时，通过leader election从replica中选举leader。只有ISR里的成员才有被选为Leader的可能。如果所有Replica都不工作，那么kafka将选择第一个“活”过来的Replica（不一定是ISR中的）作为Leader。

所有Partition的Leader选举Leader选举都由controller决定。在所有broker中选出一个controller，controller会将Leader的改变直接通过RPC的方式（比ZooKeeper Queue的方式更高效）通知需为为此作为响应的Broker。同时controller也负责增删Topic以及Replica的重新分配。

broker failover过程如下：

# 参考文献

1. [Kafka Documentation](http://kafka.apache.org/documentation.html#uses)
2. [Kafka文件存储机制那些事](http://tech.meituan.com/kafka-fs-design-theory.html)