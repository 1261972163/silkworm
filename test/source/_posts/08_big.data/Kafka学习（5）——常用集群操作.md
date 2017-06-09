Kafka学习（5）——常用集群操作
=============================


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

# 5. 测试

    bin/kafka-console-producer.sh --broker-list silkworm-test-kafka01:9092 --topic test
    bin/kafka-console-consumer.sh --zookeeper silkworm-test-zookeeper:2181 --topic test --from-beginning

# 6. purge

（1）保存当前过期时间。

（2）临时修改topic的过期时间：

    kafka-topics.sh --zookeeper localhost:13003 --alter --topic MyTopic --config retention.ms=1000

（3）等待一段时间，大约1min。

（4）purge完成后，恢复到原来的过期时间。


./kafka-01/kafka_2.10-0.9.0.1/bin/kafka-server-start.sh -daemon ./kafka-01/kafka_2.10-0.9.0.1/config/server.properties
./kafka-02/kafka_2.10-0.9.0.1/bin/kafka-server-start.sh -daemon ./kafka-02/kafka_2.10-0.9.0.1/config/server.properties 
./kafka-03/kafka_2.10-0.9.0.1/bin/kafka-server-start.sh -daemon ./kafka-03/kafka_2.10-0.9.0.1/config/server.properties 
./kafka-04/kafka_2.10-0.9.0.1/bin/kafka-server-start.sh -daemon ./kafka-04/kafka_2.10-0.9.0.1/config/server.properties 