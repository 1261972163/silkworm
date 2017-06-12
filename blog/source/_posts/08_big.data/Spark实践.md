---
title: Spark实践
categories: 大数据
tags: 
	- 大数据
	- spark
date: 2016/6/10 17:37:25
---


一个demo

	wy@mac:~/Workspace/spark$ vim input.txt
	people are not as beautiful as they look,
	as they walk or as they talk.
	they are only as beautiful  as they love,
	as they care as they share.

	wy@mac:~/Workspace/spark$ hadoop fs -mkdir -p /user/wy
	wy@mac:~/Workspace/spark$ hadoop fs -put input.txt  /user/wy/

	wy@mac:~/Workspace/spark$ spark-shell
	scala> sc.version
	res3: String = 2.0.0
	scala> val inputfile = sc.textFile("input.txt")
	scala> val counts = inputfile.flatMap(line => line.split(" ")).map(word => (word, 1)).reduceByKey(_+_)
	scala> counts.toDebugString
	scala> counts.cache()
	scala> counts.saveAsTextFile("output")

	wy@mac:~/Workspace/spark$ hadoop fs -get output
	wy@mac:~/Workspace/spark$ cd ouput
	wy@mac:~/Workspace/spark/output$ cat part-00000
	(talk.,1)
	(are,2)
	(only,1)
	(as,8)
	(,1)
	(they,7)
	(love,,1)
	wy@mac:~/Workspace/spark/output$ cat part-00001
	(not,1)
	(people,1)
	(share.,1)
	(or,1)
	(care,1)
	(beautiful,2)
	(walk,1)
	(look,,1)

	wy@mac:~$ nc -lk 9999
	wy@mac:~$ run-example streaming.NetworkWordCount localhost 9999



