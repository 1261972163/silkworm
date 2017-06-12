--
title: Zookeeper学习笔记（2）——工作原理
categories: 大数据
tags: 
	- 大数据
	- 分布式
	- 协调
	- zookeeper
date: 2017/4/17 20:42:00
---


# 1. 集群节点

ZK集群中的节点有3种角色和4种状态。

3种角色：

* leader：领导者
* follower：随从，参与选举和投票，可被选举为领导者。
* observer：接受者，不参与选举和投票

4种状态：

* LOOKING：当前Server不知道leader是谁，正在搜寻。
* LEADING：当前Server即为选举出来的leader。
* FOLLOWING：leader已经选举出来，当前Server与之同步。
* OBSERVING：observer的行为在大多数情况下与follower完全一致，但是他们不参加选举和投票，而仅仅接受(observing)选举和投票的结果。


# 2. 原子广播协议

原子消息系统是ZK的核心，用于保证同步所有服务器：

* 可靠性传输。如果消息m被到一台服务器接受，那么它将被所有的服务器接受。
* 全局有序。如果在一台服务器上消息a在消息b前发布，则在所有Server上消息a都将在消息b前被发布。
* 偏序。如果一个消息b在消息a后被同一个发送者发布，a必将排在b前面。

Zab（Zookeeper Atomic Broadcast）协议保证了原子消息系统的高效性、可靠性、易实现性和易维护性。Zab协议假设服务器间可以构建一个点对点的FIFO通道，FIFO通道依赖TCP进行通信：

* 有序传输。数据传输有序，消息m之前的消息传输完成才可传输m，消息m传输完成后，消息m之后的消息才可传输。
* 关闭后无消息。FIFO通道关闭后，从通道无法获取消息。
* Packet。FIFO通道中的比特流。
* Proposal。协议单元。指定数量的ZK服务器获取packets后，proposal被接受。
* Message。原子广播到所有ZK服务器的比特流。message封装成一个proposal。

ZK不仅保证message的顺序，也保证proposal的顺序。通过zxid（ZooKeeper Transaction Id）来展示全局顺序。每次对Zookeeper的状态的改变都会产生一个zxid，zxid是全局有序的，如果zxid1小于zxid2，则zxid1在zxid2之前发生。

# 3. 工作过程

Zookeeper整个工作分两个阶段：

* **Leader激活**。选举leader，并建立系统的状态，准备执行提议。当服务启动或者在Leader崩溃后，Zab就进入了Leader激活阶段，当领导者被选举出来，且大多数Server完成了和leader的状态同步以后，Leader激活结束。状态同步保证了leader和Server具有相同的系统状态。
* **激活消息**。leader获取消息后，请求和协调消息传输。

## Leader Election

当leader崩溃或者leader失去大多数的follower，这时候zk进入Leader激活，需要重新选举出一个新的leader，让所有的Server都恢复到一个正确的状态。Zk的选举算法有两种：一种是基于basic paxos实现的，另外一种是基于fast paxos算法实现的。系统默认的选举算法为fast paxos。

先介绍basic paxos流程：

1. 选举线程由当前Server发起选举的线程担任，其主要功能是对投票结果进行统计，并选出推荐的Server；
2. 选举线程首先向所有Server发起一次询问（包括自己）；
3. 选举线程收到回复后，验证是否是自己发起的询问（验证zxid是否一致），然后获取对方的id（myid），并存储到当前询问对象列表中，最后获取对方提议的leader相关信息（id,zxid），并将这些信息存储到当次选举的投票记录表中；
4. 收到所有Server回复以后，就计算出zxid最大的那个Server，并将这个Server相关信息设置成下一次要投票的Server；
5. 线程将当前zxid最大的Server设置为当前Server要推荐的Leader，如果此时获胜的Server获得n/2 + 1的Server票数，设置当前推荐的leader为获胜的Server，将根据获胜的Server相关信息设置自己的状态，否则，继续这个过程，直到leader被选举出来。

通过流程分析我们可以得出：要使Leader获得多数Server的支持，则Server总数必须是奇数2n+1，且存活的Server的数目不得少于n+1。

每个Server启动后都会重复以上流程。在恢复模式下，如果是刚从崩溃状态恢复的或者刚启动的server还会从磁盘快照中恢复数据和会话信息，zk会记录事务日志并定期进行快照，方便在恢复时进行状态恢复。

fast paxos流程是在选举过程中，某Server首先向所有Server提议自己要成为leader，当其它Server收到提议以后，解决epoch和zxid的冲突，并接受对方的提议，然后向对方发送接受提议完成的消息，重复这个流程，最后一定能选举出Leader。

## Leader Election

Zookeeper Server接收到一次request，如果是follower，会转发给leader，Leader执行请求并通过Transaction的形式广播这次执行。Zookeeper集群如何决定一个Transaction是否被commit执行？通过“两段提交协议”（a two-phase commit）：

1. Leader给所有的follower发送一个PROPOSAL消息。
2. 一个follower接收到这次PROPOSAL消息，写到磁盘，发送给leader一个ACK消息，告知已经收到。
3. 当Leader收到法定人数（quorum）的follower的ACK时候，发送commit消息执行。

Zab协议保证：

1. 如果leader以T1和T2的顺序广播，那么所有的Server必须先执行T1，再执行T2。
2. 如果任意一个Server以T1、T2的顺序commit执行，其他所有的Server也必须以T1、T2的顺序执行。

“两段提交协议”最大的问题是：

如果Leader发送了PROPOSAL消息后crash或暂时失去连接，会导致整个集群处在一种不确定的状态（follower不知道该放弃这次提交还是执行提交）。Zookeeper这时会选出新的leader，请求处理也会移到新的leader上，不同的leader由不同的epoch标识。

切换Leader时，需要解决下面两个问题：

* Never forget delivered messages
Leader在COMMIT投递到任何一台follower之前crash，只有它自己commit了。新Leader必须保证这个事务也必须commit。
* Let go of messages that are skipped
Leader产生某个proposal，但是在crash之前，没有follower看到这个proposal。该server恢复时，必须丢弃这个proposal。

Zookeeper会尽量保证不会同时有2个活动的Leader，因为2个不同的Leader会导致集群处在一种不一致的状态，所以Zab协议同时保证：

1. 在新的leader广播Transaction之前，先前Leader commit的Transaction都会先执行。
2. 在任意时刻，都不会有2个Server同时有法定人数（quorum）的支持者。
这里的quorum是一半以上的Server数目，确切的说是有投票权力的Server（不包括Observer）。


# 参考文献

http://zookeeper.apache.org/doc/trunk/zookeeperOver.html 
http://www.cnblogs.com/luxiaoxun/p/4887452.html