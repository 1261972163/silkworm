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

1. ActiveMQ官网：http://activemq.apache.org，下载apache-activemq-5.12.1-bin.tar.gz
2. 安装jdk-7u67-linux-x64.rpm，并配置环境变量
3. 上传apache-activemq-5.12.1-bin.tar.gz到linux服务器并解压，其中部分目录的说明如下：

	    bin目录：(windows下面的bat和unix/linux下面的sh) 启动ActiveMQ的启动服务就在这里
	    conf目录： activeMQ配置目录，包含最基本的activeMQ配置文件
	    data目录：activeMQ的日志文件目录
	    webapps目录：系统管理员web控制界面文件

4. 启动ActiveMQ
	进入apache-activemq-5.12.1/bin目录，执行如下命令即可

		./activemq start &            //其实新版的ActiveMQ也可以不加&后台符号，关闭ActiveMQ，使用./activemq stop

5. 验证启动是否成功

	启动成功就可以通过 http://ip地址:8161 方式访问管理界面，默认用户名和密码admin/admin

6. ActiveMQ启动后，默认会启用8161和61616两个端口

    其中8161端口为ActiveMQ的web管理控制端口， 61616为ActiveMQ的通讯端口


*关于ActiveMQ的部分配置文件说明*

（1）web管理端口的修改，activemq使用了jetty服务器来进行管理，我们可以在conf/jetty.xml文件中对其配置，web管理端口默认为8161，定义在jetty.xml文件的如下位置

	<bean id="jettyPort" class="org.apache.activemq.web.WebConsolePort" init-method="start">
		<!-- the default port number for the web console -->
		<property name="host" value="0.0.0.0"/>
		<property name="port" value="8161"/>
	</bean>

（2）通信端口的定义在ActiveMQ的主配置文件 ./conf/activemq.xml

	<transportConnectors>
		<!-- DOS protection, limit concurrent connections to 1000 and frame size to 100MB -->
		<transportConnector name="openwire" uri="tcp://0.0.0.0:61616?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
		<transportConnector name="amqp" uri="amqp://0.0.0.0:5672?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
		<transportConnector name="stomp" uri="stomp://0.0.0.0:61613?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
		<transportConnector name="mqtt" uri="mqtt://0.0.0.0:1883?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
		<transportConnector name="ws" uri="ws://0.0.0.0:61614?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
	</transportConnectors>

（3）web管理界面默认的用户名为admin/admin，其配置文件位于./conf/jetty-realm.properties，其格式如下：

	# 用户名: 密码, 角色名
	admin: admin, admin
	user: user, user

注：

如果同一台机器上还有rabbitmq在运行，注释掉$ACTIVE_HOME/conf/activemq.xml中

	<!--
        <transportConnector name="amqp" uri="amqp://0.0.0.0:5672?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
        <transportConnector name="stomp" uri="stomp://0.0.0.0:61613?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
        <transportConnector name="mqtt" uri="mqtt://0.0.0.0:1883?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
        <transportConnector name="ws" uri="ws://0.0.0.0:61614?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
	-->


# 2. apache-activemq自启动设置

修改启动脚本/opt/activemq/bin/activemq，将开头处改为：



