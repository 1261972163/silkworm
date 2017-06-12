---
title: HBase实践
categories: 大数据
tags: 
	- 大数据
	- hadoop
	- hbase
date: 2016/6/3 17:37:25
---

# 1 背景

HBase为查询而生的，分布式的基于列存储的非关系型数据库，通过组织起节点內所有机器的內存，提供一個超大的內存Hash表。

# 2 学习

收集了一些前人的学习资料和总结内容：

* [深入 HBase 架构解析（1）](http://blog.jobbole.com/91913/)（介绍了HBase的架构设计）
* [HBase介绍、搭建、环境、安装部署](http://www.cnblogs.com/oraclestudy/articles/5665780.html)
* [HBase shell commands](https://learnhbase.wordpress.com/2013/03/02/hbase-shell-commands/)（介绍HBase shell指令）

看完最上面2篇文章，基本可以对HBase的用途和架构设计有一个感性的认识，第3篇文章是HBase shell指令的参考文档，可以作为参考实际操作练习下HBase的操作。

# 3 部署

官方文档[Apache HBase ™ Reference Guide](https://hbase.apache.org/book.html)对安装和配置有很详细的介绍。

下面是个人安装HBase集群的记录：

	机器：
         ip       机器名      用途 
    10.45.11.1  localhost1  hadoop namenode
	10.45.11.2  localhost2  hadoop datanode
	10.45.11.3  localhost3  hadoop datanode     
	10.45.11.4  localhost4  zk1
	10.45.11.5  localhost5  zk2
	10.45.11.6  localhost6  zk3
	10.45.11.7  localhost7  HMasterServer
	10.45.11.8  localhost8  HRegionServer
	10.45.11.9  localhost9  HRegionServer

准备工作：
	
	1. 安装hadoop，并启动
	2. 安装zookeeper，并启动

安装hbase（每台hbase机器上均执行）：

	1. tar -zxvf hbase-0.94.2.tar.gz  
	2. 配置hbase-env.sh
		cd $HBASE_HOME/conf
		vi hbase-env.sh
			export JAVA_HOME=/opt/jdk/jdk1.8.0_25
			export HBASE_MANAGES_ZK=false
	3. 配置hbase-site.xml(详细配置见https://hbase.apache.org/book.html)
		vi hbase-site.xml
			<!-- hdfs --> 
			<property>  
				<name>hbase.rootdir</name>  
				<value>hdfs://localhost7:9000/hbase</value>  
			</property>
			<!-- hbase cluster -->  
			<property>  
				<name>hbase.cluster.distributed</name>  
				<value>true</value>  
			</property>
			<!-- zk --> 
			<property>  
				<name>hbase.zookeeper.quorum</name>  
				<value>localhost4,localhost5,localhost6</value>  
			</property>
			<property>
	                <name>hbase.zoopkeeper.property.dataDir</name>
	                <value>/opt/zookeeper/data</value>
	        </property>
	        <!-- web ui -->
	        <property>
	                <name>hbase.master.info.port</name>
	                <value>60010</value>
	        </property>
	4. 配置regionservers
		vi regionservers
			localhost8
			localhost9
	5. 在master上启动（只在master上启动即可）
		./$HBASE_HOME/bin/start-hbase.sh
	6. 验证
		 $HBASE_HOME/bin/hbase shell
		 hbase(main):001:0> status
		 1 active master, 0 backup masters, 2 servers, 0 dead, 1.5000 average load
	7. 界面http://localhost7:60010/

## 4 原理

HBase（Hadoop Database）是一个高可靠性、高性能、面向列、可伸缩的分布式存储系统，利用Hbase技术可在廉价PC Server上搭建大规模结构化存储集群。使用Hadoop HDFS作为其文件存储系统，利用Hadoop MapReduce来处理HBase中的海量数据，利用Zookeeper作为协调工具。

逻辑模型

* 表
* 行
* 行键
* 列簇
* 列
* 时间戳

物理模型

* 数据按照行键存储。
* 列簇是单独存储一系列列的文件。

存储模型

* 表划分为多个region，region存放在region server集群上。

体系结构

* master-slave

参考文献

1. [Apache HBase ™ Reference Guide](http://hbase.apache.org/book.html#datamodel)
