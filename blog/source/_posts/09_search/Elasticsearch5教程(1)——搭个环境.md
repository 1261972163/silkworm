---
title: Elasticsearch5教程(1)——搭个环境
categories: search
tags: 
	- data
	- search
	- elasticsearch
---

基于Elasticsearch-5.2.2。

注：

* 只支持jdk1.8.0_121以上
* 非root用户启动

# 1 安装Elasticsearch5

下载

	wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-5.2.2.tar.gz
	tar zxvf elasticsearch-5.2.2.tar.gz
	cd elasticsearch-5.2.2

下面给出一些重要的配置项，具体含义不多说了，自行google。

配置elasticsearch.yml：

	vim elasticsearch-5.2.2/conf/elasticsearch.yml
	    # 示例
	    # -----Cluster-----
	    cluster.name: es5test
	    # -----Node-----
	    node.name: es5test-1
	    node.master: true
	    node.data: true
	    node.max_local_storage_nodes: 1
	    # -----Paths-----
	    path.data: /opt/es/es5/data
	    path.logs: /opt/es/es5/logs
	    # -----Network-----
	    network.host: 10.45.11.85
	    http.port: 9201
	    transport.tcp.port: 9301
	    # -----Discovery-----
	    discovery.zen.ping.unicast.hosts: ["10.45.11.85"]
	    discovery.zen.minimum_master_nodes: 1

设置jvm：

	vim elasticsearch-5.2.2/conf/jvm.options
	    -Xms2g
	    -Xmx2g
	    -XX:+UseG1GC
	    -XX:MaxGCPauseMillis=200
	    -XX:+DisableExplicitGC
	    -XX:+AlwaysPreTouch
	    -server
	    -Xss1m
	    -Djava.awt.headless=true
	    -Dfile.encoding=UTF-8
	    -Djna.nosys=true
	    -Djdk.io.permissionsUseCanonicalPath=true
	    -Dio.netty.noUnsafe=true
	    -Dio.netty.noKeySetOptimization=true
	    -Dio.netty.recycler.maxCapacityPerThread=0
	    -Dlog4j.shutdownHookEnabled=false
	    -Dlog4j2.disable.jmx=true
	    -Dlog4j.skipJansi=true
	    -XX:+HeapDumpOnOutOfMemoryError
	    -XX:+PrintGCDetails
	    -XX:+PrintGCTimeStamps
	    -XX:+PrintGCDateStamps
	    -XX:+PrintClassHistogram
	    -XX:+PrintTenuringDistribution
	    -XX:+PrintGCApplicationStoppedTime
	    -Xloggc:/opt/xingng-es5-test/n1/logs/gc.log 

启动：

	$ES_HOME/bin/elasticsearch -d

# 2 安装IK分词器

IK分词器时中文分词器，不涉及中文分词的，可以不用安装。或者你有更好的分词器，可以把IK替换掉。

下载IK：
	
	wget https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v5.2.2/elasticsearch-analysis-ik-5.2.2.zip
	unzip elasticsearch-analysis-ik-5.2.2.zip -d ik
	cp -r ik $ES_HOME/plugins/ 

重新启动ES即可。

# 3 安装Cerebro

Cerebro是ES5的一个运维界面。之前在es2的时候，我用的是head、kopf，但ES5不在支持运维界面作为插件存在，但可以单独通过nodejs启动

因为有了cerebro，所以我没有再使用kopf，其实cerebro是kopf的升级版。

下载cerebro：

	wget https://github.com/lmenezes/cerebro/releases/download/v0.6.4/cerebro-0.6.4.tgz
	tar zxvf cerebro-0.6.4.tgz

配置：

	vim ./cerebro-0.6.4/conf/application.conf
	    hosts = [
	       {
	          host = "http://-.-.-.-:9201"
	          name = "es5-demo"
	       }
	    ] 

启动：

	./cerebro-0.6.4/bin/cerebro 

访问：

	http://localhost:9000 

# 4 安装kibana-5.2.2

kibana是一个数据可视化应用，如果不需要数据可视化，可以不用安装。

	wget https://artifacts.elastic.co/downloads/kibana/kibana-5.2.2-linux-x86_64.tar.gz
	tar -xzf kibana-5.2.2-linux-x86_64.tar.gz
	cd kibana/ 

默认情况下，远程的机器是无法访问Kibana的，只能本机访问。你需要修改Kibana 配置：

	vim config/kibana.yml
	    elasticsearch.url: "http://192.168.1.12:9200"  # 默认是访问本机ES，访问远程ES需要改为这样
	    server.host: "0.0.0.0"                         # 默认只能本机访问，远程修改需要改为这样 

启动

	nohup ./bin/kibana &

访问

	http://127.0.0.1:5601