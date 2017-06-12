---
title: Rqlite实践
categories: 数据库
tags: 
	- rqlite
date: 2016/11/18 17:37:25
---

# 1 简介

使用SQLite作为存储引擎，使用Raft实现一致性。


# 2 安装

安装过程中的一个问题

	问题：/lib64/libc.so.6: version `GLIBC_2.14' not found
	解决方法：http://blog.csdn.net/cpplang/article/details/8462768

# 3 启动

一个简单的启动脚本

	export LD_LIBRARY_PATH=/opt/glibc-2.14/lib:$LD_LIBRARY_PATH

	RQLITE_HOME=/opt/rqlite
	RQLITE_BIN_HOME=$RQLITE_HOME/rqlited-v3.9.2-linux-amd64
	RQLITE_LOGS=$RQLITE_HOME/logs

	cd $RQLITE_BIN_HOME
	nohup ./rqlited -http 127.0.0.1:4001 -raft 127.0.0.1:4002 ../node.1 > $RQLITE_LOGS/1.log 2>&1 &
	sleep 5s
	echo "node.1 started."
	cd $RQLITE_BIN_HOME
	nohup ./rqlited -http 127.0.0.1:4003 -raft 127.0.0.1:4004 -join http://127.0.0.1:4001 ../node.2 > $RQLITE_LOGS/2.log 2>&1 &
	sleep 5s
	echo "node.2 started."
	cd $RQLITE_BIN_HOME
	nohup ./rqlited -http 127.0.0.1:4005 -raft 127.0.0.1:4006 -join http://127.0.0.1:4001 ../node.3 > $RQLITE_LOGS/3.log 2>&1 &
	sleep 5s
	echo "node.3 started."

# 4 命令

	create table:

		curl -XPOST '127.0.0.1:4001/db/execute?pretty&timings' -H "Content-Type: application/json" -d '[
		    "CREATE TABLE foo (id integer not null primary key, name text)"
		]'

	insert:

		curl -XPOST '127.0.0.1:4001/db/execute?pretty&transaction' -H "Content-Type: application/json" -d "[
		    \"INSERT INTO foo(name) VALUES('jerry')\"
		]"

	query:

		curl -G '127.0.0.1:4001/db/query?pretty&timings' --data-urlencode 'q=SELECT * FROM foo'

# 5 一些坑

	1. 高可用问题
		
		* 只有master能进行操作，操作其他节点并没有什么用，如查询其他节点会得到类似以下信息
			<a href="http://127.0.0.1:4003/db/query">Moved Permanently</a>.
		* 以./rqlited ../node.1方式启动的节点就是master
		* master宕掉后，会从其他节点中选择一个作为master


# 6 测试


写入：

|线程数|速率（条/ms）|
|:--|:--|
|1|0.13|
|10|0.58|


## id查询

	rqlite

	样本5000:500000，查询100000次

	线程数  	qps
	1           1272
	2           3017
	4           4347
	6           5802
	8           6720
	10       	7551
	12          7617
	20          8120
	30          8127


# 参考文献

1. [SQLite vs MySQL vs PostgreSQL：关系型数据库比较](http://www.infoq.com/cn/news/2014/04/sqlite-mysql-postgresql)
2. [SQLite3性能深入分析](http://blog.xcodev.com/posts/sqlite3-performance-indeep/)