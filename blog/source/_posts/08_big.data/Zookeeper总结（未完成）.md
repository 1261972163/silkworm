---
title: Zookeeper总结（未完成）
categories: 大数据
tags: 
	- 大数据
	- 分布式
	- 协调
	- zookeeper
date: 2017/5/17 20:42:00
---


zookeeper
==================

# zookeeper的总体结构和使用参见：

1. [分布式服务框架 Zookeeper -- 管理分布式环境中的数据](http://www.ibm.com/developerworks/cn/opensource/os-cn-zookeeper/)（介绍了安装以及应用场景，应用场景包括：统一命名服务、配置管理、集群管理、共享锁、队列管理。）
2. [zookeeper官方文档](http://zookeeper.apache.org/doc/trunk/zookeeperOver.html ) (官方文档，大致介绍zookeeper)
3. [zookeeper学习记录](http://agapple.iteye.com/blog/1111377)（简单介绍，提供了分布式的Barrier、Queue、Lock同步的实现代码。）
4. [如何浅显易懂地解说 Paxos 的算法？](https://www.zhihu.com/question/19787937)


# zookeeper设计实现原理参见：

1. [zookeeper学习记录(二)](http://agapple.iteye.com/blog/1112032)



# 常见问题

[Zookeeper常见问题整理](http://blog.csdn.net/jiewuyou/article/details/45675201)


# zookeeper常用操作

转换日志格式

	java -Djava.ext.dirs=/home/appadmin/zookeeper:/home/appadmin/zookeeper/lib org.apache.zookeeper.server.LogFormatter /home/appadmin/log/version-2/log.a004d1b2e > x.log


# 安装

	# 1 准备工作

	  * [官方文档](http://zookeeper.apache.org/doc/r3.4.6/zookeeperStarted.html)
	  * [zookeeper-3.4.6.tar.gz](http://zookeeper.apache.org/releases.html)

	# 2 集群配置

	以下操作为**伪集群模式**，即部署在一台机器上。

	## 2.1 解压ZK

	解压三个zookeeper-3.4.6.tar.gz，如下：
	    
	    
	    /opt/zookeeper/zookeeper-3.4.6-1
	    /opt/zookeeper/zookeeper-3.4.6-2
	    /opt/zookeeper/zookeeper-3.4.6-3
	    

	注：部署的越多，可靠性就越高，最好部署**奇数**个，偶数个不是不可以，但是zookeeper集群是以宕机个数过半才会让整个集群宕机的，所以奇数个集群更佳。

	## 2.2 配置zoo.cfg

	  * 将三个zookeeper根目录下的conf下的zoo_sample.cfg文件重命名为“zoo.cfg”；
	  * 分别在三个zookeeper根目录下创建data文件夹；
	  * 分别在三个data下创建二进制文件myid，内容分别为1,2,3；
	  * 修改zoo.cfg

	| 元素	| 含义|
	|:--|:--|
	|tickTime	| Zookeeper服务器之间或客户端与服务器之间维持心跳的时间间隔|
	|initLimit	| Zookeeper服务器集群中Follower服务器初始化连接时最长能忍受的心跳时间间隔数。心跳时间超过该间隔数，表明Follower 服务器连接Leader服务器失败。|
	|syncLimit	| Leader与Follower之间发送消息时的最大请求/应答时间长度（以心跳数计算）
	|dataDir	| Zookeeper保存数据的目录|
	|dataLogDir	| Zookeeper保存日志文件的目录，默认为dataDir|
	|clientPort	| 这个端口就是客户端连接Zookeeper服务器的端口，Zookeeper会监听这个端口，接受客户端的访问请求 |


	示例（zookeeper-3.4.6-1/conf/zoo.cfg）：
	    
	    
	        # The number of milliseconds of each tick
	        tickTime=2000
	        # The number of ticks that the initial 
	        # synchronization phase can take
	        initLimit=10
	        # The number of ticks that can pass between 
	        # sending a request and getting an acknowledgement
	        syncLimit=5
	        # the directory where the snapshot is stored.
	        # do not use /tmp for storage, /tmp here is just 
	        # example sakes.
	        dataDir=D:/opt/zookeeper/zookeeper-3.4.6-1/data
	        # the port at which the clients will connect
	        clientPort=2181
	        # the maximum number of client connections.
	        # increase this if you need to handle more clients
	        #maxClientCnxns=60
	        #
	        # Be sure to read the maintenance section of the 
	        # administrator guide before turning on autopurge.
	        #
	        # http://zookeeper.apache.org/doc/current/zookeeperAdmin.html#sc_maintenance
	        #
	        # The number of snapshots to retain in dataDir
	        #autopurge.snapRetainCount=3
	        # Purge task interval in hours
	        # Set to "0" to disable auto purge feature
	        #autopurge.purgeInterval=1
	        server.1=localhost:1888:1889
	        server.2=localhost:2888:2889
	        server.3=localhost:3888:3889
	    

	示例（zookeeper-3.4.6-2/conf/zoo.cfg）：
	    
	    
	        # The number of milliseconds of each tick
	        tickTime=2000
	        # The number of ticks that the initial 
	        # synchronization phase can take
	        initLimit=10
	        # The number of ticks that can pass between 
	        # sending a request and getting an acknowledgement
	        syncLimit=5
	        # the directory where the snapshot is stored.
	        # do not use /tmp for storage, /tmp here is just 
	        # example sakes.
	        dataDir=D:/opt/zookeeper/zookeeper-3.4.6-2/data
	        # the port at which the clients will connect
	        clientPort=2182
	        # the maximum number of client connections.
	        # increase this if you need to handle more clients
	        #maxClientCnxns=60
	        #
	        # Be sure to read the maintenance section of the 
	        # administrator guide before turning on autopurge.
	        #
	        # http://zookeeper.apache.org/doc/current/zookeeperAdmin.html#sc_maintenance
	        #
	        # The number of snapshots to retain in dataDir
	        #autopurge.snapRetainCount=3
	        # Purge task interval in hours
	        # Set to "0" to disable auto purge feature
	        #autopurge.purgeInterval=1
	        server.1=localhost:1888:1889
	        server.2=localhost:2888:2889
	        server.3=localhost:3888:3889
	    

	示例（zookeeper-3.4.6-3/conf/zoo.cfg）：
	    
	    
	        # The number of milliseconds of each tick
	        tickTime=2000
	        # The number of ticks that the initial 
	        # synchronization phase can take
	        initLimit=10
	        # The number of ticks that can pass between 
	        # sending a request and getting an acknowledgement
	        syncLimit=5
	        # the directory where the snapshot is stored.
	        # do not use /tmp for storage, /tmp here is just 
	        # example sakes.
	        dataDir=D:/opt/zookeeper/zookeeper-3.4.6-2/data
	        # the port at which the clients will connect
	        clientPort=2183
	        # the maximum number of client connections.
	        # increase this if you need to handle more clients
	        #maxClientCnxns=60
	        #
	        # Be sure to read the maintenance section of the 
	        # administrator guide before turning on autopurge.
	        #
	        # http://zookeeper.apache.org/doc/current/zookeeperAdmin.html#sc_maintenance
	        #
	        # The number of snapshots to retain in dataDir
	        #autopurge.snapRetainCount=3
	        # Purge task interval in hours
	        # Set to "0" to disable auto purge feature
	        #autopurge.purgeInterval=1
	        server.1=localhost:1888:1889
	        server.2=localhost:2888:2889
	        server.3=localhost:3888:3889
	    

	注：server.A=B：C：D

	|符号|含义|
	|:--|:--|
	|A	|一个数字，表示这个是第几号服务器；|
	|B	|这个服务器的 ip 地址；|
	|C	|这个服务器与集群中的 Leader 服务器交换信息的端口；|
	|D	|表示的是万一集群中的 Leader 服务器挂了，需要一个端口来重新进行选举，选出一个新的 Leader，而这个端口就是用来执行选举时服务器相互通信的端口。|


	如果是伪集群的配置方式，由于 B 都是一样，所以不同的 Zookeeper 实例通信端口号不能一样，所以要给它们分配不同的端口号。

	## 2.3 命令使用

	### 2.3.1 启动

	执行所有zookeeper根目录下
	    
	    
	    ./bin/zkServer.cmd start
	    

	### 2.3.2 验证启动是否成功

	命令行执行：zookeeper根目录
	    
	    
	    ./bin/zkCli.cmd -server 127.0.0.1:2181
	    

	### 3.3 帮助命令help
	    
	    
	    [zk: 127.0.0.1:2181(CONNECTED) 0] help
	    ZooKeeper host:port cmd args
	            get path [watch]
	            ls path [watch]
	            set path data [version]
	            delquota [-n|-b] path
	            quit
	            printwatches on|off
	            createpath data acl
	            stat path [watch]
	            listquota path
	            history
	            setAcl path acl
	            getAcl path
	            sync path
	            redo cmdno
	            addauth scheme auth
	            delete path [version]
	            setquota -n|-b val path