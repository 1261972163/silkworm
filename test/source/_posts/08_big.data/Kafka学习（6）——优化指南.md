Kafka学习（6）——优化指南
=========================


Kafka设计的初衷是迅速处理短小的消息，一般10K大小的消息吞吐性能最好（可参见LinkedIn的kafka性能测试）。但有时候，我们需要处理更大的消息，比如XML文档或JSON内容，一个消息差不多有10-100M，这种情况下，Kakfa应该如何处理？

针对这个问题，有以下几个建议：

*  最好的方法是不直接传送这些大的数据。如果有共享存储，如NAS, HDFS, S3等，可以把这些大的文件存放到共享存储，然后使用Kafka来传送文件的位置信息。
*  第二个方法是，将大的消息数据切片或切块，在生产端将数据切片为10K大小，使用分区主键确保一个大消息的所有部分会被发送到同一个kafka分区（这样每一部分的拆分顺序得以保留），如此以来，当消费端使用时会将这些部分重新还原为原始的消息。
*  第三，Kafka的生产端可以压缩消息，如果原始消息是XML，当通过压缩之后，消息可能会变得不那么大。在生产端的配置参数中使用compression.codec和commpressed.topics可以开启压缩功能，压缩算法可以使用GZip或Snappy。

# 1. Broker配置

* message.max.bytes

    默认:1000000。broker能接收消息的最大字节数，这个值应该比消费端的fetch.message.max.bytes更小才对，否则broker就会因为消费端无法使用这个消息而挂起。

* log.segment.bytes

    默认: 1GB。kafka数据文件的大小，确保这个数值大于一个消息的长度。一般说来使用默认值即可（一般一个消息很难大于1G，因为这是一个消息系统，而不是文件系统）。

* replica.fetch.max.bytes

    默认: 1MB。broker可复制的消息的最大字节数。这个值应该比message.max.bytes大，否则broker会接收此消息，但无法将此消息复制出去，从而造成数据丢失。

# 2. Producer配置

producer方面没有什么太多需要优化的，最需要注意的一点是：**不要是用0.8版本的Producer**。

0.8版本的Producer在面对大量数据的写入时，会导致producer端使用的直接内存无法释放，最终导致应用被操作系统中断掉。

# 3. Consumer配置


* fetch.message.max.bytes

    默认 1MB。消费者能读取的最大消息。这个值应该大于或等于message.max.bytes。 所以，如果你一定要选择kafka来传送大的消息，还有些事项需要考虑。要传送大的消息，不是当出现问题之后再来考虑如何解决，而是在一开始设计的时候，就要考虑到大消息对集群和主题的影响。

* 性能

    根据前面提到的性能测试，kafka在消息为10K时吞吐量达到最大，更大的消息会降低吞吐量，在设计集群的容量时，尤其要考虑这点。

* 可用的内存和分区数

    Brokers会为每个分区分配replica.fetch.max.bytes参数指定的内存空间，假设replica.fetch.max.bytes=1M，且有1000个分区，则需要差不多1G的内存，确保 分区数最大的消息不会超过服务器的内存，否则会报OOM错误。同样地，消费端的fetch.message.max.bytes指定了最大消息需要的内存空间，同样，分区数最大需要内存空间 不能超过服务器的内存。所以，如果你有大的消息要传送，则在内存一定的情况下，只能使用较少的分区数或者使用更大内存的服务器。

* 垃圾回收

    到现在为止，我在kafka的使用中还没发现过此问题，但这应该是一个需要考虑的潜在问题。更大的消息会让GC的时间更长（因为broker需要分配更大的块），随时关注GC的日志和服务器的日志信息。如果长时间的GC导致kafka丢失了zookeeper的会话，则需要配置zookeeper.session.timeout.ms参数为更大的超时时间。


# 4. 常见错误

## 4.1 kafka.common.MessageSizeTooLargeException

这个异常的原因比较明显，单个消息太大了，到官网找找配置就可以解决。

如果是生产者报错，修改Kafka Broker的配置，在server.properties中配置message.max.bytes，默认是1M（约）。

如果是消费者报错，修改消费者中增加fetch.message.max.bytes的配置，这个配置的值要大于Broker的message.max.bytes配置。

修改了Broker的message.max.bytes，同时需要修改replica.fetch.max.bytes，并且replica.fetch.max.bytes要大于message.max.bytes。

## 4.2 Ierator is in failed state

这真的是一个神一般存在的错误提示。Consumer消费时出现Iterator is in failed state的错误提示，错误量很多，并且consumer不再消费kafka了，造成消息堆积。具体报错如下：

    [ERROR]06-17 10:19:09 com.best.xingng.monitorlogservice.processor.LogProcessor.getNext(LogProcessor.java:149)  
    java.lang.IllegalStateException: Iterator is in failed state
            at kafka.utils.IteratorTemplate.hasNext(IteratorTemplate.scala:54)
            at com.best.xingng.monitorlogservice.processor.LogProcessor.getNext(LogProcessor.java:144)
            at com.best.xingng.monitorlogservice.processor.LogProcessor.access$200(LogProcessor.java:29)
            at com.best.xingng.monitorlogservice.processor.LogProcessor$1.run(LogProcessor.java:89)
            at java.lang.Thread.run(Thread.java:744)
    ...

但这个错误并不是真正的错误，是因为MessageSizeTooLargeException导致的，发生MessageSizeTooLargeException异常会导致Iterator is in failed state错误的发生，但是MessageSizeTooLargeException只会打印一次，而Iterator is in failed state错误会随着读取方法的调用不停的打，完全能将错误分析方向带偏了。具体如下：

    kafka.common.MessageSizeTooLargeException: Found a message larger than the maximum fetch size of this consumer on topic xingngLogProd1 partition 27 at fetch offset 114. Increase the fetch size, or decrease the maximum message size the br
    oker will allow.
            at kafka.consumer.ConsumerIterator.makeNext(ConsumerIterator.scala:90)
            at kafka.consumer.ConsumerIterator.makeNext(ConsumerIterator.scala:33)
            at kafka.utils.IteratorTemplate.maybeComputeNext(IteratorTemplate.scala:66)
            at kafka.utils.IteratorTemplate.hasNext(IteratorTemplate.scala:58)
            at com.best.xingng.monitorlogservice.processor.LogProcessor.getNext(LogProcessor.java:144)
            at com.best.xingng.monitorlogservice.processor.LogProcessor.access$200(LogProcessor.java:29)
            at com.best.xingng.monitorlogservice.processor.LogProcessor$1.run(LogProcessor.java:89)
            at java.lang.Thread.run(Thread.java:744)
    [ERROR]06-17 10:19:09 com.best.xingng.monitorlogservice.processor.LogProcessor.getNext(LogProcessor.java:149)  
    java.lang.IllegalStateException: Iterator is in failed state
            at kafka.utils.IteratorTemplate.hasNext(IteratorTemplate.scala:54)
            at com.best.xingng.monitorlogservice.processor.LogProcessor.getNext(LogProcessor.java:144)
            at com.best.xingng.monitorlogservice.processor.LogProcessor.access$200(LogProcessor.java:29)
            at com.best.xingng.monitorlogservice.processor.LogProcessor$1.run(LogProcessor.java:89)
            at java.lang.Thread.run(Thread.java:744)
    [ERROR]06-17 10:19:09 com.best.xingng.monitorlogservice.processor.LogProcessor.getNext(LogProcessor.java:149)  
    java.lang.IllegalStateException: Iterator is in failed state
            at kafka.utils.IteratorTemplate.hasNext(IteratorTemplate.scala:54)
            at com.best.xingng.monitorlogservice.processor.LogProcessor.getNext(LogProcessor.java:144)
            at com.best.xingng.monitorlogservice.processor.LogProcessor.access$200(LogProcessor.java:29)
            at com.best.xingng.monitorlogservice.processor.LogProcessor$1.run(LogProcessor.java:89)
            at java.lang.Thread.run(Thread.java:744)
    [ERROR]06-17 10:19:09 com.best.xingng.monitorlogservice.processor.LogProcessor.getNext(LogProcessor.java:149)  
    java.lang.IllegalStateException: Iterator is in failed state
            at kafka.utils.IteratorTemplate.hasNext(IteratorTemplate.scala:54)
            at com.best.xingng.monitorlogservice.processor.LogProcessor.getNext(LogProcessor.java:144)
            at com.best.xingng.monitorlogservice.processor.LogProcessor.access$200(LogProcessor.java:29)
            at com.best.xingng.monitorlogservice.processor.LogProcessor$1.run(LogProcessor.java:89)
            at java.lang.Thread.run(Thread.java:744)
    ...
    
解决MessageSizeTooLargeException即可。

## 4.3 kafka.consumer.ConsumerTimeoutException

这个错误提示也比较直白，消费者消费数据时超时了。

默认情况下是阻塞式消费数据，不会报这个错误。

如果consumer设置consumer.timeout.ms，则消费者消费数据时超时就会报错。

消费者消费数据超时的一种情况就是所有数据均被消费完了。

## 4.4 java.io.IOException: Broken pipe

## 4.5 java.io.IOException: Connection reset by peer

## 4.6 org.apache.kafka.clients.consumer.CommitFailedException: Commit cannot be completed due to group rebalance

这个错误提示比较直白，意思是消费者消费了数据，但在规定时间内没有commit，所以kafka认为这个consumer挂掉了，这时对consumer的group进行再平衡。

解决方法有三种：

1. 增加消费超时时间。消费超时时间通过heartbeat.interval.ms设置，heartbeat.interval.ms的大小不能超过session.timeout.ms，session.timeout.ms必须在[group.min.session.timeout.ms, group.max.session.timeout.ms]范围内。
2. 减少消息处理时间；由后端处理决定。
3. 减少一次消费的消息量。max.partition.fetch.bytes决定容量，max.poll.records决定数量。max.partition.fetch.bytes规定了一个partition一次pull获取的获取的数据大小。max.poll.records规定一次pull获取的消息数量。