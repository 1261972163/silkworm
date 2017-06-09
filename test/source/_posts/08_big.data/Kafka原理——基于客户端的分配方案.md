Kafka原理——基于客户端的partition分配方案（0.9）
====================================

![](https://raw.githubusercontent.com/nouuid/mind-manager/master/resources/db/kafka/Kafka_Client-side_Assignment_Proposal.png)

# 1 协议

分两阶段：

* 构建组关系。设置组内激活的成员，选举组中的leader，组员订阅leader。
* 同步组状态。组leader同步组员状态，进行partition分配。

##  1.1 构建组关系

> 每一个Broker将被选举为某些Consumer Group的Coordinator。某个Cosnumer Group的Coordinator负责在该Consumer Group的成员变化或者所订阅的Topic的Partititon变化时协调Rebalance操作。

寻找组的coordinator，组员发送JoinGroup请求（包含组员的特征信息），所有组员的请求都到达coordinator后，coordinator从组中随机选择一个组员为leader，然后响应所有的JoinGroup请求。

对leader的响应，包含了组员信息，leader在同步组员状态阶段会用到这些信息。

对于非leader的组员，会订阅leader，leader会收集所有组员的订阅。


## 1.2 同步组状态

构建组关系完成后，leader将状态传递给组内其他成员，分配partition。

组员向coordinator发送SyncGroup请求，coordinator在对每个组员的SyncGroup响应中携带该组员的状态。

如果coordinator收到leader的组状态，将在SyncGroup响应用leader的组状态替换每个成员的状态。

## 1.3 协调者状态机

coordinator维护着每个group的状态机，group有如下几种状态：

* Down。没有可用成员，group状态被清除。
* Initialize。协调者从zookeeper（或其他存储）读取group数据，从失效的协调者转变为
* Stable。协调者，等待首次JoinGroup.


# 参考文献

1. [Kafka Client-side Assignment Proposal](https://cwiki.apache.org/confluence/display/KAFKA/Kafka+Client-side+Assignment+Proposal)
2. [Kafka 0.9 Consumer Rewrite Design](https://cwiki.apache.org/confluence/display/KAFKA/Kafka+0.9+Consumer+Rewrite+Design)