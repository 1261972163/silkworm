---
title: ActiveMQ实践
categories: 大数据
tags: 
	- 消息队列
	- activemq
date: 2014/7/3 17:37:25
---

# 1. apache-activemq安装

备注：apache-activemq安装时JDK必须在1.5以，否则不能访问。

	1. 从官网下载Activemq Linux包http://activemq.apache.org/download.html.这儿我下载的是 apache-activemq-5.4.3-bin.tar.gz
	2. 解压包

		tar zxvf apache-activemq-5.4.3-bin.tar.gz

	3. 进入解压后的文件夹apache-activemq-5.4.3-bin中的bin目录

		cd  apache-activemq-5.4.3-bin/bin目录

	4. 在启动前先配置activemq。在bin目录下执行

		./activemq setup /root/.activemqrc

	5. 提高activemq的权限

		chmod 600 /root/.activemqrc

	6. 启动activemq

		./activemq  start

	7. 最后http访问出现以下代表成功

	    http://IP:61616/若出现下边内容则说明安装成功(备注在启动前主要要开启端口61616)

	8. 如果同一台机器上还有rabbitmq在运行，注释掉$ACTIVE_HOME/conf/activemq.xml中

		<!--
            <transportConnector name="amqp" uri="amqp://0.0.0.0:5672?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
            <transportConnector name="stomp" uri="stomp://0.0.0.0:61613?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
            <transportConnector name="mqtt" uri="mqtt://0.0.0.0:1883?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
            <transportConnector name="ws" uri="ws://0.0.0.0:61614?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
		-->


# 2. apache-activemq自启动设置

修改启动脚本/opt/activemq/bin/activemq，将开头处改为：



