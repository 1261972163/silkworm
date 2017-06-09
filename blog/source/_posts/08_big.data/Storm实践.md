---
title: Storm实践
categories: bigdata
tags: 
	- bigdata
	- storm
date: 2016/6/3 17:37:25
---


# 安装

	启动
	Nimbus：bin/storm nimbus >/dev/null 2>&1 &
	Supervisor：bin/storm supervisor >/dev/null 2>&1 &
	UI：bin/storm ui >/dev/null 2>&1 &

	启动后通过http://{nimbus host}:8080观察集群的worker资源使用情况、Topologies的运行状态等信息。

	注意事项：
	Storm后台进程被启动后，将在Storm安装部署目录下的logs/子目录下生成各个进程的日志文件。
	经测试，Storm UI必须和Storm Nimbus部署在同一台机器上，否则UI无法正常工作，因为UI进程会检查本机是否存在Nimbus链接。
	为了方便使用，可以将bin/storm加入到系统环境变量中。


	storm jar  apache-storm-1.0.1/examples/storm-starter/storm-starter-topologies-1.0.1.jar  org.apache.storm.starter.WordCountTopology