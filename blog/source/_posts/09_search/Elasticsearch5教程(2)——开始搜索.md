---
title: Elasticsearch5教程(2)——开始搜索
date: 2017-06-14 15:10:54
categories: search
tags: 
	- data
	- search
	- elasticsearch
---

在《Elasticsearch5教程(1)——搭个环境》已经搭建了一个环境，只有一个节点，但五脏俱全：

* 分词。不仅可以进行默认分词，还可以使用IK进行中文分词。
* 运维。在Cerebro上可以可视化index，还可以进行很多操作，动手去挖掘吧。
* 数据可视化。kibana提供了数据可视化的组件，可以进行一些简单统计报表。

# 1 索引操作

Index是ES暴露给用户的最小数据空间，数据的索引都存储在Index中。要创建数据的索引，必须先创建Index：

	curl -XPUT 'http://127.0.0.1:9200/contact'

# 2 写点数据

现在ES集群中还没有数据，现在开始使用Elasticsearch的RESTful接口写些数据：

	curl -XPUT 'http://127.0.0.1:9200/contact/person/1' -d '
	{
		"name": "jerry",
		"sex": "m"
	}'

上面在名为contact的index，名为person的type下写入了一个Document，document id为1，Document包含两个域name和sex。


# 2 读取数据

如果知道document的id，可以GET出这条数据：

	curl -XGET 'http://127.0.0.1:9200/contact/persion/1'

如果不知道document的id，只知道name，可以通[Search API](https://www.elastic.co/guide/en/elasticsearch/reference/current/search.html)进行查询：

	curl -XGET 'http://127.0.0.1:9200/contact/persion/_search?q=name:jerry'

或者：

	curl -XGET 'http://127.0.0.1:9200/contact/persion/_search' -d '
	{
	    "query" : {
	        "term" : { "name" : "jerry" }
	    }
	}'