zookeeper学习笔记（1）——简介
============================

ZooKeeper是一个分布式应用程序协调服务，通过暴露一个简单的操作原语集，为分布式应用程序提供数据同步服务。

# 1. 设计目标

1. 最终一致性：client不论连接到哪个Server，展示给它都是同一个视图，这是zookeeper最重要的性能。
2. 可靠性：具有简单、健壮、良好的性能，如果消息m被到一台服务器接受，那么它将被所有的服务器接受。
3. 实时性：Zookeeper保证客户端将在一个时间间隔范围内获得服务器的更新信息，或者服务器失效的信息。但由于网络延时等原因，Zookeeper不能保证两个客户端能同时得到刚更新的数据，如果需要最新数据，应该在读数据之前调用sync()接口。
4. 等待无关（wait-free）：慢的或者失效的client不得干预快速的client的请求，使得每个client都能有效的等待。
5. 原子性：更新只能成功或者失败，没有中间状态。
6. 顺序性：包括全局有序和偏序两种：全局有序是指如果在一台服务器上消息a在消息b前发布，则在所有Server上消息a都将在消息b前被发布；偏序是指如果一个消息b在消息a后被同一个发送者发布，a必将排在b前面。

# 2. 数据模型

ZK会维护一个层次关系的数据结构，它非常类似于一个标准的文件系统，其形式如下：

![这里写图片描述](http://img.blog.csdn.net/20161026174427499)

# 3. 命名空间

- zk命名空间内的每个子项被称作为znode，znode是它所在的路径唯一标识。
- 每个znode都可以存储数据，同时还可以有子节点，注意EPHEMERAL（临时的）类型的目录节点不能有子节点目录。
- zk被设计用来存储协调数据，如状态信息、配置信息、位置信息等等，这类数据量比较小。
- zk数据存储在内存中，因此具有高吞吐量、低延迟的特性。
- 每个znode都维护一份状态，包括数据更改的版本号（version）、ACL更改、时间戳，用于缓存验证和协调更新。znode数据发生变化，版本号增加。获取该节点数据的同时也获取该节点的状态。
- znode上数据的读写是原子的。读操作读取单个znode上的所有数据，写操作替换单个znode上的所有数据。
- znode有两种类型：临时节点（EPHEMERAL）和持久节点（PERSISTENT）。如果znode是临时节点，一旦创建该znode的客户端与服务器失去联系，session失效，该znode也将被删除。
- znode的目录名可以自动编号，如App1已经存在，再创建的话，将会自动命名为App2。

# 4. 状态更新和监听

每次对Zookeeper的状态的改变都会产生一个zxid（ZooKeeper Transaction Id），zxid是全局有序的，如果zxid1小于zxid2，则zxid1在zxid2之前发生。

ZK支持watch监听通知机制。Clients可监听某一个znode，当znode发生变化时（包括这个目录节点中存储的数据的修改，子节点目录的变化等），watch被触发或移除，这个是Zookeeper的核心特性。

Zookeeper的客户端和服务器通信采用长连接方式，每个客户端和服务器通过心跳来保持连接，这个连接状态称为session。整个session状态变化如图所示：

![这里写图片描述](http://img.blog.csdn.net/20161026174444124)

当client和server的连接中断（如timeout或者其他原因），client会收到一个本地通知，client处在CONNECTING状态，会自动尝试再去连接Server，如果在session有效期内再次成功连接到某个Server，则回到CONNECTED状态。
注意：如果因为网络状态不好，client和Server失去联系，client会停留在当前状态，会尝试主动再次连接Zookeeper Server。client不能宣称自己的session expired，session expired是由Zookeeper Server来决定的，client可以选择自己主动关闭session。

Zookeeper 中的监空是轻量级的，因此容易设置、维护和分发。当客户端与 Zookeeper 服务器失去联系时，客户端并不会收到监视事件的通知，只有当客户端重新连接后，若在必要的情况下，以前注册的监视会重新被注册并触发，对于开发人员来说这通常是透明的。只有一种情况会导致监视事件的丢失，即：通过exists()设置了某个znode节点的监视，但是如果某个客户端在此znode节点被创建和删除的时间间隔内与zookeeper服务器失去了联系，该客户端即使稍后重新连接 zookeeper服务器后也得不到事件通知。

# 5.一致性保证

Zookeeper是一个高效的、可扩展的服务，read和write操作都被设计为快速的，read比write操作更快。

- 顺序一致性（Sequential Consistency）：从一个客户端来的更新请求会被顺序执行。
- 原子性（Atomicity）：更新要么成功要么失败，没有部分成功的情况。
- 唯一的系统镜像（Single System Image）：无论客户端连接到哪个Server，看到系统镜像是一致的。
- 可靠性（Reliability）：更新一旦有效，持续有效，直到被覆盖。
- 时间线（Timeliness）：保证在一定的时间内各个客户端看到的系统信息是一致的。

# 6. 操作原语集

znode上的操作只包括：

- create：创建节点
- delete：删除节点
- exists：测试节点是否存在
- get data：读数据
- set data：写数据
- get children：获取节点的子节点列表
- sync：等待数据传输结束


# 参考文献

http://zookeeper.apache.org/doc/trunk/zookeeperOver.html
http://www.cnblogs.com/luxiaoxun/p/4887452.html
