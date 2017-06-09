
一些建议
=========

consumer配置的值大小关系：

	request.timeout.ms / group.max.session.timeout.ms > session.timeout.ms > heartbeat.interval.ms

集群优化


	partition数目

	broker的message.max.bytes
	topic的max.message.bytes
	consumer的fetch.message.max.bytes



kafka常见错误及处理
=================


Marking the coordinator 2147483647 dead

	错误表现：

	[INFO]12-09 09:41:00 org.apache.kafka.clients.consumer.internals.ConsumerCoordinator$OffsetCommitResponseHandler.handle(ConsumerCoordinator.java:542)  Offset commit for group test1 failed due to NOT_COORDINATOR_FOR_GROUP, will find new coordinator and retry
	[INFO]12-09 09:41:00 org.apache.kafka.clients.consumer.internals.AbstractCoordinator.coordinatorDead(AbstractCoordinator.java:529)  Marking the coordinator 2147483647 dead.

	分析和解决方案：

	查阅相关资料，貌似有两种原因：

	1. 如果手动指定了consumer消费的partition，这种情况下，动态partition分配和consumer组协调就会失效。
	2. consumer client和coordinator之间的网络通信出错，例如coordinator宕机或者组需要再平衡。

	关于kafka coordinator的设计，在[Kafka Client-side Assignment Proposal](https://cwiki.apache.org/confluence/display/KAFKA/Kafka+Client-side+Assignment+Proposal)中有详细的描述。


	从当前个人使用来看，错误原因在于：

	1. broker从zookeeper掉线然后重新注册；
	2. 这个过程会导致consumer的coordinator失效；
	3. consumer寻找新的coordinator，并报错。
	4. 引发coordinator的rebalance。


消费者自动分配partition和指定分配partition是否可以同时存在


	1. 先自动分配模式下消费，然后指定分片模式的消费可以进行，但提交会出现错误
	2. 先指定分片模式下消费，然后自动分配模式下消费，自动分配的消费没有问题，指定分片的消费出现错误

		org.apache.kafka.clients.consumer.CommitFailedException: Commit cannot be completed due to group rebalance
		at org.apache.kafka.clients.consumer.internals.ConsumerCoordinator$OffsetCommitResponseHandler.handle(ConsumerCoordinator.java:552)
		at org.apache.kafka.clients.consumer.internals.ConsumerCoordinator$OffsetCommitResponseHandler.handle(ConsumerCoordinator.java:493)
		at org.apache.kafka.clients.consumer.internals.AbstractCoordinator$CoordinatorResponseHandler.onSuccess(AbstractCoordinator.java:665)
		at org.apache.kafka.clients.consumer.internals.AbstractCoordinator$CoordinatorResponseHandler.onSuccess(AbstractCoordinator.java:644)
		at org.apache.kafka.clients.consumer.internals.RequestFuture$1.onSuccess(RequestFuture.java:167)
		at org.apache.kafka.clients.consumer.internals.RequestFuture.fireSuccess(RequestFuture.java:133)
		at org.apache.kafka.clients.consumer.internals.RequestFuture.complete(RequestFuture.java:107)
		at org.apache.kafka.clients.consumer.internals.ConsumerNetworkClient$RequestFutureCompletionHandler.onComplete(ConsumerNetworkClient.java:380)
		at org.apache.kafka.clients.NetworkClient.poll(NetworkClient.java:274)
		at org.apache.kafka.clients.consumer.internals.ConsumerNetworkClient.clientPoll(ConsumerNetworkClient.java:320)
		at org.apache.kafka.clients.consumer.internals.ConsumerNetworkClient.poll(ConsumerNetworkClient.java:213)
		at org.apache.kafka.clients.consumer.internals.ConsumerNetworkClient.poll(ConsumerNetworkClient.java:193)
		at org.apache.kafka.clients.consumer.internals.ConsumerNetworkClient.poll(ConsumerNetworkClient.java:163)
		at org.apache.kafka.clients.consumer.internals.ConsumerCoordinator.commitOffsetsSync(ConsumerCoordinator.java:358)
		at org.apache.kafka.clients.consumer.KafkaConsumer.commitSync(KafkaConsumer.java:968)

	2. 