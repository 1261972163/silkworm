---
title: Elasticsearch总结(1)——原理
categories: search
tags: 
	- data
	- search
	- elasticsearch
date: 2017/3/13 20:03:00
---


Lucene可以说是当今最先进，最高效的全功能开源搜索引擎框架。但Lucene只是一个框架，要充分利用它的功能，必须使用Java将其直接集成到应用中，更糟糕的是，Lucene非常复杂，需要深入学习检索的相关知识才能理解它。

ElasticSearch(以下简称ES)是一个基于Lucene构建的开源(open-source)，分布式(distributed)，RESTful，实时(real-time)的搜索与分析(analytics)引擎。使用Lucene作为内部引擎，通过简单的RESTful API来隐藏Lucene的复杂性，从而让全文搜索变得简单。

下面将主要解决3个问题：

1. 什么是倒排索引？
2. ES如何实现分布式文档存储？
3. ES索引是不可变的，如何实现更新索引？

# 倒排索引

Lucene包含以下接关键概念：

* **Document**是数据源，由Field组成。
* **Field**是Document的组成部分，由name和value组成。
* **Term**是不可分割的单词，搜索的最小单元。
* **Token**是一个Term的呈现方式，包含这个Term的内容，在文档中的起始位置，以及类型。

Lucene搜索的核心数据结构是[**倒排索引**](https://www.elastic.co/guide/en/elasticsearch/guide/current/inverted-index.html)，把索引中的每个Term与相应的Document映射起来，一个倒序索引由两部分组成：在文档中出现的所有不同的词；对每个词，它所出现的文档的列表。形式如下：

	Term -> （count, Document）

Lucene实现搜索的核心过程包括：

	* 索引过程，用指定的analyzer解析用户添加的Document并存储到倒排索引中。
	* 搜索过程，用查询解析器(query parser)解析查询语句，然后用指定的analyzer解析查询语句中的词，和倒排索引中的词进行匹配。  

analyzer解析又叫**分词**，由一个分词器(tokenizer)和0个或者多个过滤器(filter)组成，也可能会有0个或者多个字符映射器(character mappers)组成。Lucene中的tokenizer用来把文本拆分成一个个的Token。Token包含了比较多的信息，比如Term在文本的中的位置及Term原始文本，以及Term的长度。文本经过tokenizer处理后的结果称为token stream。token stream其实就是一个个Token的顺序排列。token stream将等待着filter来处理。filter链将用来处理Token Stream中的每一个token。这些处理方式包括删除Token，改变Token，甚至添加新的Token。

Lucene的操作方式和操作数据库有点相似。数据库操作流程为：如果要使用数据库，必须先创建数据库，然后往这个数据表中一行一行的插入数据记录，数据记录插入成功之后，就可以操作这张数据表，实现增删改查操作了。在lucene中，“数据库”是索引文件目录，“数据记录”是Document，Document由Field组成，Field由name和value组成。类似的操作流程为：

	1. 创建索引文件目录；
	2. 封装数据源：用Field封装需要检索的信息，然后将Field封装到一个Document文档对象；
	3. 存储索引：将Docement放入索引文件目录中；
	4. 删除索引：根据索引id去删除对应的索引；
	5. 更新索引：先将旧的索引删除，然后添加新的索引；
	6. 查询索引：先创建索引读取对象，然后封装Query查询对象，调用search()方法得到检索结果。

具体示例可以参考[lucene示例](http://www.lucenetutorial.com/lucene-in-5-minutes.html)

# 分布式文档存储

ES沿用了Lucene的概念，并进行了适当扩展，和关系型数据库概念对照起来类似：

	Relational DB  =>  Databases  =>  Tables  =>  Rows       =>  Columns 
	Elasticsearch  =>  Indices    =>  Types   =>  Documents  =>  Fields

**index**只是一个命名空间，逻辑概念，用来指向一个或多个实际的物理分片(shard)。**shard**是index逻辑数据范围内的一个切片，实际上是一个Lucene实例，拥有完整的搜索功能。但shard对用户来说是透明的，只需要和创建的index通信即可。

shard可分为primary shard和replica shard两类。在一个index里的每一个文档都属于一个单独的**primary shard**，在对一个文档建立索引时，文档和索引仅存储在一个primary shard上，那如何选择存储的shard呢？选择shard的过程就是**路由**，这个选择并不是无规律的，而是计算出来的：

	shard = hash(routing) % number_of_primary_shards

routing的值可以是文档的id，也可以是用户自己设置的一个值。hash将会根据routing算出一个数值然后%primaryshards的数量。所以，primary_shards的数量在索引创建时确定后不能修改。

**replica shard**是primary shard的拷贝，可以动态增加，可以在任何时候修改。对于**主从shard的分布**，集群会控制Primary shard和它的Replicas不在同一台节点上，同时集群不会初始化超过数量的replica，要产生足够数量的replica，可以添加node，然后增加replica。添加node是集群的**水平扩容**。集群可以在全局范围内自动平衡数据的分布，保证数据平均分布在集群节点中，同时主从shard不在同一节点上。

**replica的作用**主要有两点： （1）冗余容灾。（2）提供读请求服务，请求可以直接从replica中搜索或读取文档。现在主要看下**冗余容灾**，如果kill掉下面的Node1

![](https://www.elastic.co/guide/en/elasticsearch/guide/current/images/elas_0205.png)

杀掉的是master节点。一个Cluster必须要有master以保证集群的功能正常。所以集群要做的第一件事是选择一个新的master：Node2. 当我们杀掉1节点时，Primary shards 1和2丢失了。如果丢失了primary shard，index(名词)将不能正常的工作。此时P1和P2的拷贝存在Node2和Node3上。所以此时新升级的master(Node2)将做的第一件事就是将NODE2和NODE3上的replica shard1和replica shard2升级为primary shard。此时如果我们杀掉NODE2，整个集群的容灾过程同理，还是可以正常运行。

![](https://www.elastic.co/guide/en/elasticsearch/guide/current/images/elas_0206.png)

这时，如果我们重启了NODE1，cluster将会重新分配缺少的两个replica shards(现在每个primary shard只有2个replicas，配置是3个，缺少2个)。如果NODE1的数据是旧的，那么它将会继续利用它们，NODE1只会从现在的Primary Shards拷贝这期间更改的数据。

对于ES集群，我们可以向这个集群的任何一台NODE发送请求，每一个NODE都有能力处理请求。每一个NODE都知道每一个文档所在的位置所以可以直接将请求路由过去。（注：最好的实践方式是轮询所有的NODE来发送请求，以达到请求负载均衡。）

创建、索引、删除文档都是写操作，这些操作必须在primary shard完全成功后才能拷贝至其对应的replicas上。

![](https://www.elastic.co/guide/en/elasticsearch/guide/current/images/elas_0402.png)

具体逻辑是：

	1.客户端向Node1发送写操作的请求。
	2.Node1使用文档的_id来决定这个文档属于shard0，然后将请求路由至NODE3，P0所在的位置。
	3.Node3在P0上执行了请求。如果请求成功，则将请求并行的路由至NODE1 NODE2的R0上。当所有的replicas报告成功后，NODE3向请求的node(NODE1)发送成功报告，NODE1再报告至Client。
	当客户端收到执行成功后，操作已经在Primary shard和所有的replica shards上执行成功了。

一个文档可以在primary shard和所有的replica shard上读取。

![](https://www.elastic.co/guide/en/elasticsearch/guide/current/images/elas_0403.png)

具体逻辑：

	1.客户端发送Get请求到NODE1。
	2.NODE1使用文档的_id决定文档属于shard 0。shard 0的所有拷贝存在于所有3个节点上。随机选择一个，这次，它将请求路由至NODE2。
	3.NODE2将文档返回给NODE1，NODE1将文档返回给客户端。对于读请求，请求节点(NODE1)将在每次请求到来时都选择一个不同的replica。
	shard来达到负载均衡。使用轮询策略轮询所有的replica shards。

更新操作，结合了以上的两个操作：读、写。

![](https://www.elastic.co/guide/en/elasticsearch/guide/current/images/elas_0404.png)

具体逻辑：

	1.客户端发送更新操作请求至NODE1
	2.NODE1将请求路由至NODE3，Primary shard所在的位置
	3.NODE3从P0读取文档，改变source字段的JSON内容，然后试图重新对修改后的数据在P0做索引。如果此时这个文档已经被其他的进程修改了，那么它将重新执行3步骤，这个过程如果超过了retryon_conflict设置的次数，就放弃。
	4.如果NODE3成功更新了文档，它将并行的将新版本的文档同步到NODE1和NODE2的replica shards重新建立索引。一旦所有的replica
	shards报告成功，NODE3向被请求的节点(NODE1)返回成功，然后NODE1向客户端返回成功。

# 索引更新

写到磁盘的倒序索引是**不变的**：自从写到磁盘就再也不变。这会有很多好处：

	1. 不需要添加锁。如果你从来不用更新索引，那么你就不用担心多个进程在同一时间改变索引。
	2. 一旦索引被内核的文件系统做了Cache，它就会待在那因为它不会改变。只要内核有足够的缓冲空间，绝大多数的读操作会直接从内存而不需要经过磁盘。这大大提升了性能。
	3. 其他的缓存(例如fiter cache)在索引的生命周期内保持有效，它们不需要每次数据修改时重构，因为数据不变。
	4. 写一个单一的大的倒序索引可以让数据压缩，减少了磁盘I/O的消耗以及缓存索引所需的RAM。

当然，索引的不变性也有缺点。如果你想让新修改过的文档可以被搜索到，你必须重新构建整个索引。这在一个index可以容纳的数据量和一个索引可以更新的频率上都是一个限制。

如何在不丢失不变形的好处下让倒序索引可以更改，实现**动态更新索引**呢？答案是：使用不只一个索引。新添额外的索引来反映新的更改来替代重写所有倒序索引的方案。Lucene引进了**per-segment搜索**的概念。一个segment就是一个完整的倒序索引子集，所以现在index在Lucene中的含义就是：segments集合加上提交点。**提交点**(commit point)内包含所有已知的segments记录。当ES启动或者重新打开一个index时，它会利用这个提交点来决定哪些segments属于当前的shard。 

![](https://www.elastic.co/guide/en/elasticsearch/guide/current/images/elas_1102.png)

一个完成的[per-segment工作流程](https://www.elastic.co/guide/en/elasticsearch/guide/current/dynamic-indices.html)包括：

	1.新文档在in-memory buffer中进行索引组织。
	2.每隔一段时间，将in-memory buffer中的文档写入到磁盘上一个新的segement，同时写入一个包含新segment的新提交点，然后磁盘fsync，将内核文件系统缓存持久化。
	3.打开新的segment，使它包含的文档可以被索引。
	4.清理in-memory buffer，准备接收新的文档。

当一个新的请求来时，会遍历所有的segments。词条分析程序会聚合所有的segments来保障每个文档和词条相关性的准确。通过这种方式，新的文档轻量的可以被添加到对应的索引中。

segments是不变的，所以**删除文档**不能从旧的segments中删除，也不能在旧的segments中更新来映射一个新的文档版本。取之的是，每一个提交点都会包含一个**.del文件**，列举了哪一个segment的哪一个文档已经被删除了。 当一个文档被”删除”了，它仅仅是在.del文件里被标记了一下。被”删除”的文档依旧可以被索引到，但是它将会在最终结果返回时被移除掉。

**文档更新**同理：当文档更新时，旧版本的文档将会被标记为删除，新版本的文档在新的segment中建立索引。也许新旧版本的文档都会本检索到，但是旧版本的文档会在最终结果返回时被移除。

在per-segment搜索的机制下，新的文档会在分钟级内被索引，但是还不够快，无法达到**准实时搜索**。瓶颈在磁盘，将新的segment提交到磁盘需要**fsync**来保障物理写入，但是fsync是很耗时的。它不能在每次文档更新时就被调用，否则性能会很低。 

现在需要一种轻便的方式能使新的文档可以被索引，这就意味着不能使用fsync来保障。 在ES和物理磁盘之间是**内核文件系统缓存**。segment将首先写入到内核的文件系统缓存，这个过程很轻量，然后再flush到磁盘，这个过程很耗时。但是一旦一个segment文件在内核的缓存中，它可以被打开被读取。

如果在文档提交时，文本被修改了，如何保证这些修改不丢失，实现[更新持久化](https://www.elastic.co/guide/en/elasticsearch/guide/current/translog.html)。具体过程是：

	1. 当一个文档被索引时，它会被添加到in-memory buffer，并且追加到Translog日志中。
	2. shard每秒都会被refreshed，refresh操作后，在in-memory buffer中的文档被写入到一个新的segment，但没有fsync。同时，这个segment被打开，可以用于查询。然后in-memory buffer被清空，但Translog不会被清空。
	3. 继续1的操作。
	4. 当Translog变的太大时，将in-memory buffer中的文档写入一个新的segment，然后清空in-memory buffer，同时将提交点写入磁盘，内核文件系统缓存会被fsync到磁盘，然后删除旧Translog日志，然后建立新的Translog，一个完整的提交就完成了。

Translog的作用就是保证操作不丢失。问题是：Translog有多安全？默认情况下，Translog每5秒 或者 每个写请求完成后被fsync到磁盘。

太多的segment是一个问题，因为每个segment都会消耗文件句柄、内存、CPU，更重要的是每个搜索请求会遍历检查所有的segment，segment越多，搜索越慢。为了解决segment太多的问题，ES在后台进行**段合并**，这个过程是在索引和搜索时自动发生的，并不影响索引和搜索操作：

	1. 选择多个大小类似segment合并成一个大的segment。在段合并时，被删除的文档不会被添加到这个新的大segment中。
	2. 新的segment被flush到磁盘。
	3. 提交新的提交点，包含新的segment并且排除旧的segment。
	4. 打开新的segment。
	5. 删除旧的segment，被标记为删除的文档也一起从文件系统中移除。



