
“生产者-消费者模型“在计算机领域应用很广泛，但常常面临生产者和消费者速度不匹配的问题，导致整个生产者的流程过长。

因此，就有了”生产者-存储-消费“模型，将生产者和消费者通过存储介质隔离开，缩短生产者的流程，保证生产者的低延迟。这个存储介质一般会选择内存，毕竟快嘛，如果考虑持久化的话，就得选择磁盘了，比较空间大嘛，但需要磁盘的耗时。如果数据量太大，一台机器空间无法满足时，可以扩展为分布式服务、分布式存储，然后添加replica做冗余备份，还可以实现容错高可用。消息在存储介质上存储，为了保证消息的有序性，一般选择的是队列数据结构。这时，所有的消息都还混在同一个队列中，实际上，消息往往是有类别主题的，不同主题往往是互不干扰的，因此可以在存储上按照主题进行隔离，让不同的消息进入不同的队列中。这样，就是一个**分布式的发布/订阅消息系统**，常见的包括：ActiveMQ、RabbitMQ、Redis等。

Kafka就是这样一种分布式的发布/订阅消息系统，具有吞吐率高、快速消息持久化、消息分区、分区内顺序传输、分布式消费等特点。

整个流程包括：

**Producer**负责发送消息到Broker，producer只向leader partition发送消息，通过load balance，将消息push到不同的partition。

* **Broker**是kafka服务器，承担着接收Producer和Consumer的请求，并把Message持久化到本地磁盘、担当Coordinator负责处理Partition的Leader选举，协调Partition迁移等职责。Broker内部维护着Topic和Partition，消息到达Broker后按照Topic提交到Partition上，**Topic**是划分Message的逻辑概念，一个Topic可以分布在多个Broker上。每个Topic都至少有1个Partition，**Partition**是消息最终存储的位置，在Partition内消息按照Offset**顺序存储**，**Offset**是消息在当前Partition中的编号，编号顺序不跨Partition。

**Consumer**负责从Broker读取消息。前面说到消息被顺序的存储在topic的partition中，那么consumer就是从partition上读取消息，但是从哪个topic的哪个partition消费呢，这就是**partition分配**。这里可以**手动指定**consumer消费指定topic的指定partition，也可以只指定topic，让broker中的**coordinator分配**每个consumer消费的partition，但最终**一个partition只能由一个consumer进行消费**，一个consumer可以消费多个topic的多个partition。

**Coordinator**是从cluster中选举出的一个broker，用来协调Partition消费的分配工作。

partition分配完成后，consumer只是知道了消息所在的partition，还不能确定partition中消息的读取位置。Kafka主要提供了earliest、latest和指定offset三种方式来定位读取消息的位置。

接着，consumer采用**pull模式**从指定位置读取消息，读取到的消息被处理完后，默认情况下，consumer把从partition上读取的最后一条消息的Offset提交到Broker上的指定topic。

> pull模式和push模式相比，push模式很难适应消费速率不同的消费者，因为消息发送速率是由broker决定的。push模式的目标是尽可能以最快速度传递消息，但是这样很容易造成consumer来不及处理消息，典型的表现就是拒绝服务以及网络拥塞。而pull模式则可以根据consumer的消费能力以适当的速率消费消息，可使消费速率最大化。
>
> 基于pull模式的另一个优点是，它有助于积极的批处理的数据发送到消费者。基于push模式必须选择要么立即发送请求或者积累更多的数据，稍后发送它，无论消费者是否能立刻处理它，如果是低延迟，这将导致短时间只发送一条消息，不用缓存，这是实在是一种浪费，基于pull的设计解决这个问题，消费者总是pull在日志的当前位置之后pull所有可用的消息（或配置一些大size），所以消费者可设置消费多大的量，也不会引入不必要的等待时间。

每个Consumer属于一个特定的**Consumer Group**（可为每个Consumer指定group name，若不指定group name则属于默认的group）。同一Topic的同一个消费组维护同一份offset。

上面就是Kafka的整个生产-存储-消费流程，其中还有一些关键问题没有讲述清楚。

1. 如何保证消息交付？
2. 如何实现的高吞吐量




