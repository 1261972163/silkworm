---
title: Hadoop实践
categories: 大数据
tags: 
	- 大数据
	- hadoop
	- MapReduce
date: 2016/6/3 17:37:25
---


# HDFS

HDFS(Hadoop Distributed File System，Hadoop分布式文件系统)是Hadoop项目的文件系统。但和一般的文件系统又有区别，区别在于HDFS是**分布式的文件系统**。同时，HDFS和其他的分布式文件系统的区别也很明显。

* HDFS是一个**高度容错性**的系统，适合部署在**廉价的机器**上。
* HDFS能提供**高吞吐量**的数据访问，非常适合大规模数据集上的应用。
* HDFS放宽了一部分POSIX约束，来实现**流式读取**文件系统数据的目的。

# MapReduce

MapReduce是一种计算模型，用于大数据的并行计算。

Map和Reduce是两种操作：

	map(key1,value) -> list<key2,value2>
	reduce(key2, list<value2>) -> list<value3>

Map（映射）函数是把输入映射为一组或多组全新的数据，而不去改变原始数据；Reduce（归纳）函数是讲Map得到的一组数据经过某些方法归一成输出值。

总结起来，Map是一个“分”的过程，把海量数据分割成了若干小块以分给若干台处理器去处理，而Reduce是一个“合”的过程，它把各台处理器处理后的结果进行汇总操作以得到答案。

http://blog.csdn.net/v_july_v/article/details/6637014