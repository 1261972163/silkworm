---
title: RabbitMQ实践
categories: bigdata
tags: 
	- 消息队列
	- rabbitmq
date: 2016/6/3 17:37:25
---

# 1 环境搭建

需要安装Erlang、python环境。

## 1.1 gcc安装

	yum install gcc

## 1.2 Erlang安装

* 下载otp_src_17.0.tar.gz
* 解压

		tar -zvxf otp_src_17.0.tar.gz

* 安装ncurses-devel

		yum list|grep ncurses
		yum -y install ncurses-devel.x86_64

* 进入otp_src_17.0目录
		
		./configure && make && make install

* 添加环境变量
		
		vim /etc/profile
		export ERLANG_HOME=/usr/share/otp_src_17.0
		export PATH=$ERLANG_HOME/bin:$PATH	
		source /etc/profile

* 验证安装是否正确

		erl -version
		Erlang (ASYNC_THREADS,HIPE) (BEAM) emulator version 6.0
	
## 1.3 安装python

建议大于等于2.7，小于3.0的版本。

* 验证是否安装

		yum list installed python 或 python -V

* 安装略


## 1.4 安装simplejson

下载simplejson-3.3.0.tar.gz，解压后进入目录

	python setup.py install

## 1.5 RabbitMQ-Server安装

	tar xvzf rabbitmq-server-2.6.1.tar.gz
    cd rabbitmq-server-2.6.1
	make
	yum install xmlto.x86_64
	TARGET_DIR=/usr/local/rabbitmq SBIN_DIR=/usr/local/rabbitmq/sbin MAN_DIR=/usr/local/rabbitmq/man DOC_INSTALL_DIR=/usr/local/rabbitmq/doc make install

在sbin/目录下出现了三个命令：

	rabbitmqctl  rabbitmq-env  rabbitmq-server

安装成功！

# 2 RabbitMQ配置

## 2.1 设置日志与消息持久化目录：
 
	[root@localhost ~]# mkdir /var/log/rabbitmq
	[root@localhost ~]# mkdir /var/lib/rabbitmq
		
## 2.2 软链接

	[root@localhost ~]# ln -s /usr/local/rabbitmq/sbin/rabbitmq-server /usr/bin/rabbitmq-server
	[root@localhost ~]# ln -s /usr/local/rabbitmq/sbin/rabbitmq-env /usr/bin/rabbitmq-env

## 2.3 启动web管理工具

	[root@localhost sbin]# ./rabbitmq-plugins enable rabbitmq_management
	Error: {cannot_write_enabled_plugins_file,”/etc/rabbitmq/enabled_plugins”,
	enoent}
	[root@localhost sbin]# mkdir /etc/rabbitmq
	[root@localhost sbin]# ./rabbitmq-plugins enable rabbitmq_management
	The following plugins have been enabled:
	mochiweb
	webmachine
	rabbitmq_web_dispatch
	amqp_client
	rabbitmq_management_agent
	rabbitmq_management
	Plugin configuration has changed. Restart RabbitMQ for changes to take effect.

配置用户权限后，RabbitMQ启动后，通过访问http://server-host-name:15672/访问即可。

# 3 RabbitMQ基础操作

## 3.1 用户管理

添加用户：

	./rabbitmqctl add_user admin abc123

其他常用操作：
	
	删除用户： rabbitmqctl  delete_user  Username

	修改密码： rabbitmqctl  change_password  Username  Newpassword

	查看用户： rabbitmqctl  list_users

## 3.2 角色管理

设置administrator角色：

	./rabbitmqctl set_user_tags admin administrator

其他角色：

	monitoring，policymaker，management，或其他自定义名称

## 3.3 权限管理

设置权限：

	./rabbitmqctl set_permissions -p / admin ".*" ".*" ".*"

其他常用操作：

	查看用户权限： rabbitmqctl  list_user_permissions [-p  VHostPath] User
	清除用户权限： rabbitmqctl  clear_permissions  [-p VHostPath]  User


## 3.4 RabbitMQ开闭操作

进入SBIN_DIR目录。


* 一步启动Erlang node和Rabbit应用: 

		./rabbitmq-server start
	
* 一步关闭Erlang node和Rabbit应用：

		./rabbitmq-server stop

* 节点启用：
	
		./rabbitmqctl start
	
* 节点关闭：

		./rabbitmqctl stop

* 查看服务状态

		[root@localhost ~]# lsof -i:5672
		COMMAND PID USER FD TYPE DEVICE SIZE/OFF NODE NAME
		beam 25276 root 13u IPv6 73960 0t0 TCP *:amqp (LISTEN)

