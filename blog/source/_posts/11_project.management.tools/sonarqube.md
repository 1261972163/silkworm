---
title: Sonarqube指南
categories: 工程管理
tags: 
	- 工具
	- 代码质量
	- sonarqube
date: 2014/10/3 08:59:00
---

# 1 准备工作

已安装JAVA环境
已安装mysql数据库
下载sonarqube-4.5.1
下载sonar-runner-dist-2.4

# 2 数据库配置

进入数据库：

	#mysql -u root -p

执行：

	mysql> CREATE DATABASE sonar CHARACTER SET utf8 COLLATE utf8_general_ci; 
	mysql> CREATE USER 'sonar' IDENTIFIED BY 'sonar';
	mysql> GRANT ALL ON sonar.* TO 'sonar'@'%' IDENTIFIED BY 'sonar';
	mysql> GRANT ALL ON sonar.* TO 'sonar'@'localhost' IDENTIFIED BY 'sonar';
	mysql> FLUSH PRIVILEGES;

# 3 安装sonar与sonar-runner（win7环境）

## 3.1 解压sonarqube-4.5.1到

	D:\opt\sonarqube\sonarqube-4.5.1

## 3.2 解压sonar-runner-dist-2.4到

	D:\opt\sonarqube\sonar-runner-2.4

## 3.3 添加环境变量

添加SONAR_ HOME、SONAR_ RUNNER_ HOME环境变量。
将%SONAR_ RUNNER_ HOME%\bin加入PATH。

## 3.4 修改sonar配置文件

编辑D:\opt\sonarqube\sonarqube-4.5.1\conf\sonar.properties文件，配置数据库设置，默认已经提供了各类数据库的支持。这里使用mysql，因此取消mysql模块的注释：

	sonar.jdbc.username=sonar
	sonar.jdbc.password=sonar

	sonar.jdbc.url=jdbc:mysql://localhost:3306/sonar?useUnicode=true&characterEncoding=utf8&rewriteBatchedStatements=true
	sonar.jdbc.driverClassName:com.mysql.jdbc.Driver

	sonar.jdbc.maxActive=20
	sonar.jdbc.maxIdle=5
	sonar.jdbc.minIdle=2
	sonar.jdbc.maxWait=5000
	sonar.jdbc.minEvictableIdleTimeMillis=600000
	sonar.jdbc.timeBetweenEvictionRunsMillis=30000

## 3.5 修改sonar-runner的配置文件

编辑D:\opt\sonarqube\sonar-runner-2.4\conf\sonar-runner.properties：

	#Configure here general information about the environment, such as SonarQube DB details for example
	#No information about specific project should appear here

	#----- Default SonarQube server
	sonar.host.url=http://localhost:9000

	#----- PostgreSQL
	#sonar.jdbc.url=jdbc:postgresql://localhost/sonar

	#----- MySQL
	sonar.jdbc.url=jdbc:mysql://localhost:3306/sonar?useUnicode=true&amp;characterEncoding=utf8

	#----- Oracle
	#sonar.jdbc.url=jdbc:oracle:thin:@localhost/XE

	#----- Microsoft SQLServer
	#sonar.jdbc.url=jdbc:jtds:sqlserver://localhost/sonar;SelectMethod=Cursor

	#----- Global database settings
	#sonar.jdbc.username=sonar
	#sonar.jdbc.password=sonar

	#----- Default source code encoding
	#sonar.sourceEncoding=UTF-8

	#----- Security (when 'sonar.forceAuthentication' is set to 'true')
	sonar.login=admin
	sonar.password=admin

## 3.6 添加数据库驱动

除了Oracle数据库外，其它数据库驱动都默认已经提供了，且这些已添加的驱动是sonar唯一支持的，因此不需要修改。
如果是Oracle数据库，需要复制JDBC驱动至D:\opt\sonarqube\sonarqube-4.5.1\extensions\jdbc-driver\oracle目录

# 4 启动sonar

	进入
		D:\opt\sonarqube\sonarqube-4.5.1\bin\windows-x86-64
	启动服务
		StartSonar.bat
	访问
		http:\\localhost:9000

# 5 使用SonarQube Runner分析源码

前提：
已安装SonarQube Runner且环境变量已配置，即sonar-runner命令可在任意目录下执行。

## 5.1 创建sonar-project.properties配置文件

在项目源码的根目录下创建sonar-project.properties配置文件。
打开sonar-project.properties，进行参数配置，最简参数配置如下：

	# Required metadata
	sonar.projectKey=Monkey
	sonar.projectName=Monkey
	sonar.projectVersion=1.1
	# Comma-separated paths to directories with sources (required)
	sonar.sources=src
	sonar.binaries=bin/classes
	# Language
	sonar.language=java
	# Encoding of the source files
	sonar.sourceEncoding=UTF-8

说明：

	projectKey与projectName和工程名字一样即可
	sources为源码目录
	binaries为编译后的classes目录
	binaries=bin，sonar会自动遍历所有目录
	language为分析的语言
	sourceEncoding源码编码格式

## 5.2 执行分析

cmd切换到项目源码根目录，执行命令

	# sonar-runner

分析成功后访问

	http://localhost:9000/

发现多了一个刚才你设置的projectKey

点击你分析的工程，查看分析结果

不同参数的意思：

	http://docs.codehaus.org/display/SONAR/Analysis+Parameters

不同项目的源码分析示例下载： 　　 

	https://github.com/SonarSource/sonar-examples/zipball/master