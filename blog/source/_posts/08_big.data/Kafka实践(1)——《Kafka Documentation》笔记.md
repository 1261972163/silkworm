---
title: Kafka实践(1)——《Kafka Documentation》笔记
categories: 大数据
tags: 
	- 大数据
	- 消息队列
	- kafka
date: 2016/11/4 09:37:25
---


以下内容，仅为档[Kafka Documentation](http://kafka.apache.org/documentation.html#uses)的阅读笔记。阅读时的版本为0.9.0.x

* [1. 开始](#1)
	* [1.1 引言](#1-1)
	* [1.2 使用场景](#1-2)
	* [1.3 快速开始](#1-3)
	* [1.4 相关技术生态](#1-4)
	* [1.5 升级](#1-5)
* [2. API](#2)
	* [2.1 Producer API](#2-1)
	* [2.2 Consumer API](#2-2)
		* [2.2.1 高级消费者API（旧）](#2-3)
		* [2.2.2 低级消费者API（旧）](#2-4)
		* [2.2.3 消费者API（新）](#2-5)
* [3. 配置](#3)
	* [3.1 Broker Configs](#3-1)
	* [3.2 Producer Configs](#3-2)
	* [3.3 Consumer Configs](#3-3)
		* [3.3.1 Old Consumer Configs](#3-3-1)
		* [3.3.2 New Consumer Configs](#3-3-2)
	* [3.4 Kafka Connect Configs](#3-4)
* [4 设计](#4)
	* [4.1 动机](#4-1)
	* [4.2 持久化](#4-2)
	* [4.3 效率](#4-3)
	* [4.4 producer](#4-4)
	* [4.5 consumer](#4-5)
	* [4.6 消息传递](#4-6)
	* [4.7 Replication](#4-7)
	* [4.8 日志压缩](#4-8)
	* [4.9 Quotas](#4-9)
* [5 设计](#5)
* [6 操作](#6)
	* [6.1 常用操作](#6-1)
		* [6.1.1 topic操作](#6-1-1)
		* [6.1.2 关闭集群](#6-1-2)
		* [6.1.3 平衡leader](#6-1-3)
		* [6.1.4 集群间镜像数据](#6-1-4)
		* [6.1.5 查看consumer位置](#6-1-5)
		* [6.1.6 集群扩展](#6-1-6)
		* [6.1.7 退役中的broker](#6-1-7)
		* [6.1.8 增加replication因子](#6-1-8)
		* [6.1.9 设置阈值](#6-1-9)
	* [6.2 数据中心](#6-2)
	* [6.3 kafka配置](#6-3)
	* [6.4 jdk](#6-4)
	* [6.5 硬盘和操作系统](#6-5)
	* [6.6 监控](#6-6)
	* [6.7 zookeeper](#6-7)
* [7 安全](#7)
	* [7.1 安全概述](#7-1)
	* [7.2 使用SSL进行加密和认证](#7-1)
	* [7.3 使用SASL进行认证](#7-2)
	* [7.4 认证和控制列表ACLs](#7-3)
	* [7.5 zookeeper认证](#7-4)
* [8 kafka连接](#8)

# <a name="1">1 开始</a>

## <a name="1-1">1.1 引言 </a>

kafka提供提交日志的服务，具有分布式、分块、可复制的特点。

## <a name="1-2">1.2 使用场景 </a>

* 传送消息。解构数据生产和数据消费。
* 网站活动追踪。用作管道，不同类别信息使用不同topic。
* 度量。分布式数据聚合的中心点。
* 日志累积。持久化store的中转站。
* 流处理。流处理框架的中转站。
* 事件源。
* 提交日志。

## <a name="1-3">1.3 快速开始 </a>

## <a name="1-4">1.4 相关技术生态 </a>

## <a name="1-5">1.5 升级 </a>

# <a name="2">2 API</a>

## <a name="2-1">2.1 Producer</a>

依赖：

	<dependency>
	    <groupId>org.apache.kafka</groupId>
	    <artifactId>kafka-clients</artifactId>
	    <version>0.9.0.0</version>
	</dependency>

## <a name="2-2">2.2 Consumer</a>

### <a name="2-2-1">2.2.1 高级消费者API（旧）</a>

### <a name="2-2-2">2.2.2 低级消费者API（旧）</a>

### <a name="2-2-3">2.2.3 消费者API（新） </a>

新的消费者API不再区分高级/低级。

依赖：

	<dependency>
	    <groupId>org.apache.kafka</groupId>
	    <artifactId>kafka-clients</artifactId>
	    <version>0.9.0.0</version>
	</dependency>

# <a name="3">3 配置</a>

# <a name="4">4 设计</a>

## <a name="4-1">4.1 动机</a>

高吞吐量，支持大容量事件流；

高抗压，支持离线系统周期性的数据压力；

低延迟；

动态分布式分区；

容错；

## <a name="4-2">4.2 持久化</a>

磁盘线性读写要比随机读写快很多；

常量时间。基于文件的读和追加来构建持久化队列；

## <a name="4-3">4.3 效率</a>

打包发送消息。

producer、broker和consumer同时使用标准二进制消息格式。

zero-copy。

数据压缩

## <a name="4-4">4.4 producer</a>

producer只向leader partition发送消息

通过load balance，将消息发送到不同的partition。

批量发送

## <a name="4-5">4.5 consumer</a>

kafka采用pull模式。

* push VS pull

push是一种推送行为，及时性好，对consumer要求较高，consumer的消费速率能够跟得上producer的生产速率。

pull是一种拉取行为，可以在consumer端控制消费速率，可使消费速率最大化。但若producer没有数据生产时，consumer会陷入轮询。

* 消费点

通过offset指示消费的位置。

## <a name="4-6">4.6 消息传递</a>

消息传递保证措施一般有以下几种：

* 最多一次。 消息不成功则不重传，可能丢消息。
* 最少一次。消息不成功则重传。
* 只传一次。消息只传递一次且成功。

kafka的做法：

## <a name="4-7">4.7 Replication</a>

### 哪些follower会备份

和大部分分布式系统一样，Kafka处理失败需要明确定义一个Broker是否“活着”。对于Kafka而言，节点存活满足两个条件：

* 节点必须维持它和zookeeper的session。
* Follower必须能够及时将Leader的消息复制过来，且不能落后太多。

Leader会跟踪与其保持同步的follower列表，该列表称为ISR（即in-sync Replica）。只有ISR中的follower执行复制。一旦leader发现follower未存活，则将其从ISR中删除，follower又存活，则将其添加到ISR中。Kafka只解决这种fail/recover，不处理“Byzantine”（“拜占庭”）问题。

一条消息只有被ISR里的所有replica都从Leader复制过去才会被认为“committed”。这种机制确保了只要ISR有一个或以上的replica，被“committed”的消息就一定不会丢失。

### 如何选举leader

Kafka在ZooKeeper中动态维护了一个ISR（in-sync replicas），这个ISR里的所有Replica都跟上了leader，只有ISR里的成员才有被选为Leader的可能。在这种模式下，对于f+1个Replica，一个Partition能在保证不丢失已经commit的消息的前提下容忍f个Replica的失败。

在ISR中至少有一个follower时，Kafka可以确保已经commit的数据不丢失，但如果某个Partition的所有Replica都宕机了，就无法保证数据不丢失了。这种情况下有两种可行的方案：

* 等待ISR中的任一个Replica“活”过来，并且选它作为Leader
* 选择第一个“活”过来的Replica（不一定是ISR中的）作为Leader

这就需要在可用性和一致性当中作出一个简单的折衷。如果一定要等待ISR中的Replica“活”过来，那不可用的时间就可能会相对较长。而且如果ISR中的所有Replica都无法“活”过来了，或者数据都丢失了，这个Partition将永远不可用。选择第一个“活”过来的Replica作为Leader，而这个Replica不是ISR中的Replica，那即使它并不保证已经包含了所有已commit的消息，它也会成为Leader而作为consumer的数据源（前文有说明，所有读写都由Leader完成）。Kafka0.9.*使用了第二种方式。

### 可用性和耐用性保证

两种方案：

* 禁用leader选举
* 控制最小ISR大小。ISR的大小达到一定的数量后，才可以进行写入。提高了耐用性，但这样降低了可用性。

### replica管理

分配Replica的算法如下：

* (1) 将n个Broker和待分配的Partition排序
* (2) 第i个Partition分配到第i%n个Broker
* (3) 第i个Partition的第j个Replica分配到第(i + j) %n个Broker

Leader Election方案：

在所有broker中选出一个controller，所有Partition的Leader选举都由controller决定。controller会将Leader的改变直接通过RPC的方式通知给需要为此作出响应的Broker。同时controller也负责增删Topic以及Replica的重新分配。

controller不可用时，剩余的可用broker之一会成为新的controller。

## <a name="4-8">4.8 日志压缩</a>

## <a name="4-9">4.9 Quotas</a>

0.9版本提供。

Quotas（限额）以client-id为单位。

Quotas的出现是为了防止不良client独占broker资源，造成网络饱和、拒绝服务。

quota.producer.default, quota.consumer.default默认值可认为是无穷大。

违反quotas的client会被降速。

# <a name="5">5 实现</a>

# <a name="6">6 操作</a>

## <a name="6-1">6.1 常用操作</a>

### 6.1.1 topic操作

#### 新增topic

	bin/kafka-topics.sh --zookeeper zk_host:port/chroot --create --topic my_topic_name --partitions 20 --replication-factor 3 --config x=y

#### 修改topic

* 修改partitions

		bin/kafka-topics.sh --zookeeper zk_host:port/chroot --alter --topic my_topic_name --partitions 40

* 增加configs

		bin/kafka-topics.sh --zookeeper zk_host:port/chroot --alter --topic my_topic_name --config x=y

* 删除configs

		bin/kafka-topics.sh --zookeeper zk_host:port/chroot --alter --topic my_topic_name --deleteConfig x

#### 删除topic

		bin/kafka-topics.sh --zookeeper zk_host:port/chroot --delete --topic my_topic_name

默认情况下topic是不能删除的，删除操作需要delete.topic.enable=true支持。

### 6.1.2 关闭集群

优雅的stop需要满足两个条件：

* 同步所有的log
* leader关闭前需要将数据移动replica

### 6.1.3 平衡leader

手动：

	bin/kafka-preferred-replica-election.sh --zookeeper zk_host:port/chroot

自动：

	auto.leader.rebalance.enable=true

### 6.1.4 集群间镜像数据

示例：

	bin/kafka-run-class.sh kafka.tools.MirrorMaker --consumer.config consumer-1.properties --consumer.config consumer-2.properties --producer.config producer.properties --whitelist my-topic

从2个集群（consumer-1.properties和consumer-2.properties）镜像my-topic的数据到新集群（producer.properties）

需要以下配置配合：

	auto.create.topics.enable=true

### 6.1.5 查看consumer位置

示例：

	bin/kafka-run-class.sh kafka.tools.ConsumerOffsetChecker --zkconnect localhost:2181 --group test

### 6.1.6 集群扩展

集群扩展涉及到数据重分配。

#### 自动移动数据

示例移动foo1和foo2所有的分区到5，6上

topics-to-move.json：

	{"topics": [{"topic": "foo1"},
	            {"topic": "foo2"}],
	 "version":1
	}

生成移动方案。：

	bin/kafka-reassign-partitions.sh --zookeeper localhost:2181 --topics-to-move-json-file topics-to-move.json --broker-list "5,6" --generate

执行：

	bin/kafka-reassign-partitions.sh --zookeeper localhost:2181 --reassignment-json-file expand-cluster-reassignment.json --execute

检查移动结果：

	bin/kafka-reassign-partitions.sh --zookeeper localhost:2181 --reassignment-json-file expand-cluster-reassignment.json --verify

#### 自定义移动数据

定义分配计划custom-reassignment.json：

	{"version":1,"partitions":[{"topic":"foo1","partition":0,"replicas":[5,6]},{"topic":"foo2","partition":1,"replicas":[2,3]}]}

执行：

	bin/kafka-reassign-partitions.sh --zookeeper localhost:2181 --reassignment-json-file custom-reassignment.json --execute

检查：

	bin/kafka-reassign-partitions.sh --zookeeper localhost:2181 --reassignment-json-file custom-reassignment.json --verify

### 6.1.7 退役中的broker

### 6.1.8 增加replication因子

increase-replication-factor.json：

	{"version":1, "partitions":[{"topic":"foo","partition":0,"replicas":[5,6,7]}]}

执行：

	bin/kafka-reassign-partitions.sh --zookeeper localhost:2181 --reassignment-json-file increase-replication-factor.json --execute

验证：

	bin/kafka-reassign-partitions.sh --zookeeper localhost:2181 --reassignment-json-file increase-replication-factor.json --verify

查看topic配置：

	bin/kafka-topics.sh --zookeeper localhost:2181 --topic foo --describe

### 6.1.9 设置阈值

设置默认阈值：

	quota.producer.default=10485760
	quota.consumer.default=10485760

设置特定用户的阈值：

	bin/kafka-configs.sh  --zookeeper localhost:2181 --alter --add-config 'producer_byte_rate=1024,consumer_byte_rate=2048' --entity-name clientA --entity-type clients

查看：

	./kafka-configs.sh  --zookeeper localhost:2181 --describe --entity-name clientA --entity-type clients

## <a name="6-2">6.2 数据中心</a>

## <a name="6-3">6.3 kafka配置</a>

producer相关的比较重要的配置：

* 压缩率
* 同步或异步
* 批量大小

## <a name="6-4">6.4 jdk</a>

建议jdk版本1.8

## <a name="6-5">6.5 硬盘和操作系统</a>

* memory：吞吐率*时间
* os：推荐unix系统
* disk：建议使用多块磁盘，可提高吞吐率。

### linux清除策略

file -> pagecache -> disk

Pdflush控制pagecache -> disk的flush，如果flush速率跟不上写入pagecache的速率，会阻塞写入过程。

### Ext4文件系统

## <a name="6-5">6.4 硬盘和操作系统</a>

## <a name="6-6">6.6 监控</a>

## <a name="6-7">6.7 zookeeper</a>

# <a name="7">7 安全</a>

## <a name="7-1">7.1 安全概述</a>

## <a name="7-2">7.2 使用SSL进行加密和认证</a>

示例：

	#!/bin/bash
    #Step 1
    keytool -keystore server.keystore.jks -alias localhost -validity 365 -genkey
    #Step 2
    openssl req -new -x509 -keyout ca-key -out ca-cert -days 365
    keytool -keystore server.truststore.jks -alias CARoot -import -file ca-cert
    keytool -keystore client.truststore.jks -alias CARoot -import -file ca-cert
    #Step 3
    keytool -keystore server.keystore.jks -alias localhost -certreq -file cert-file
    openssl x509 -req -CA ca-cert -CAkey ca-key -in cert-file -out cert-signed -days 365 -CAcreateserial -passin pass:test1234
    keytool -keystore server.keystore.jks -alias CARoot -import -file ca-cert
    keytool -keystore server.keystore.jks -alias localhost -import -file cert-signed

broker配置：

	listeners=PLAINTEXT://host.name:port,SSL://host.name:port

	ssl.keystore.location=/var/private/ssl/kafka.server.keystore.jks
    ssl.keystore.password=test1234
    ssl.key.password=test1234
    ssl.truststore.location=/var/private/ssl/kafka.server.truststore.jks
    ssl.truststore.password=test1234
    security.inter.broker.protocol=SSL

检查：

	openssl s_client -debug -connect localhost:9093 -tls1

配置client：

	# 只支持0.9
	security.protocol=SSL
    ssl.truststore.location=/var/private/ssl/kafka.client.truststore.jks
    ssl.truststore.password=test1234

    ssl.keystore.location=/var/private/ssl/kafka.client.keystore.jks
    ssl.keystore.password=test1234
    ssl.key.password=test1234

    # Examples using console-producer and console-consumer:
    kafka-console-producer.sh --broker-list localhost:9093 --topic test --producer.config client-ssl.properties
    kafka-console-consumer.sh --bootstrap-server localhost:9093 --topic test --new-consumer --consumer.config client-ssl.properties

## <a name="7-3">7.3 使用SASL进行认证</a>

## <a name="7-4">7.4 认证和控制列表ACLs</a>

## <a name="7-5">7.5 zookeeper认证</a>

# <a name="8">8 kafka连接</a>