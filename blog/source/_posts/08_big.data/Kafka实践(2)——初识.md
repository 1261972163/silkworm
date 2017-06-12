---
title: Kafka实践(2)——初识
categories: 大数据
tags: 
	- 大数据
	- 消息队列
	- kafka
date: 2016/11/5 09:51:25
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


# 常用集群操作

	# 启动kafka

    bin/kafka-server-start.sh config/server.properties
    bin/kafka-server-start.sh -daemon ./config/server.properties

	# 关闭kafka
    bin/kafka-server-stop.sh

	# topic列表
    bin/kafka-topics.sh --list --zookeeper silkworm-test-zookeeper:2181/kafka

	# 创建topic
    bin/kafka-topics.sh --create --zookeeper silkworm-test-zookeeper:2181/kafka --replication-factor 1 --partitions 1 --topic test

    # 删除topic
    bin/kafka-topics.sh  --delete --zookeeper 【zookeeper server】  --topic 【topic name】

# 运行测试

生产者和消费者

    bin/kafka-console-producer.sh --broker-list silkworm-test-kafka01:9092 --topic test
    bin/kafka-console-consumer.sh --zookeeper silkworm-test-zookeeper:2181 --topic test --from-beginning

这里有个[Kafka的测试](http://blog.csdn.net/weitry/article/details/53007507)

# purge删除数据

（1）保存当前过期时间。

（2）临时修改topic的过期时间：

    kafka-topics.sh --zookeeper localhost:13003 --alter --topic MyTopic --config retention.ms=1000

（3）等待一段时间，大约1min。

（4）purge完成后，恢复到原来的过期时间。



# 参考文献

1. [Kafka Documentation](http://kafka.apache.org/documentation.html#uses)
