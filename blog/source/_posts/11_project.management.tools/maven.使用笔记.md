---
title: Maven使用笔记
categories: 工程管理
tags: 
	- 工具
	- 代码管理
	- maven
date: 2013/10/13 20:03:00
---


* maven快速生成项目
* 下载源码包
* 下载指定包
* 下载指定包并加入到本地库
* 单元测试

# maven快速生成项目

	创建普通的j2se项目

		1、
		mvn archetype:create -DgroupId=weitry.demo.mvn（项目组） -DartifactId=mvndemo(项目ID) -DarchetypeArtifactId=maven-archetype-quickstart（项目类型）

		2、进入mvndemo目录，
		mvn clean install -Dmaven.test.skip=true

		3、mvn eclipse:eclipse
		4、把test项目导入eclipse即可

	创建web项目

		把maven-archetype-quickstart替换为maven-archetype-webapp即可
		若eclipse没有WTP插件，第三步需要改为 mvn eclipse:eclipse -Dwtpversion=2.0，指示生成的工程为WTP工程。


	示例：

		mvn archetype:create -DgroupId=com.nouuid.wy -DartifactId=demo -DarchetypeArtifactId=maven-archetype-quickstart  
		cd clientdemo  
		mvn clean install -Dmaven.test.skip=true  
		mvn eclipse:eclipse  

# 下载源码包

	mvn dependency:sources

# 下载指定包

	mvn org.apache.maven.plugins:maven-dependency-plugin:2.8:get -Dartifact=javax.mail:mail:1.1:jar:sources

# 将指定包加入到本地库

	不需要关联
	
		mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc14 -Dversion=10.2.0.3.0 -Dpackaging=jar -Dfile=ojdbc14-10.2.0.3.0.jar -DgeneratePom=true

	需要关联（方法一）：

		mvn install:install-file -DgroupId=org.jtester -DartifactId=jtester -Dversion=1.1.8 -Dpackaging=jar -DpomFile=jtester-1.1.8.pom -Dfile=jtester-1.1.8.jar -Dsources=jtester-1.1.8-sources.jar
		mvn install:install-file -DgroupId=mockit -DartifactId=jmockit -Dversion=0.999.10 -Dpackaging=jar -DpomFile=jmockit-0.999.10.pom -Dfile=jmockit-0.999.10.jar -Dsources=jmockit-0.999.10-sources.jar

	需要关联（方法二）：

		1. 创建pom.xml

			<?xml version="1.0"?>
			<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
				<modelVersion>4.0.0</modelVersion>
				<groupId>temp.download</groupId>
				<artifactId>temp-download</artifactId>
				<version>1.0-SNAPSHOT</version> 
				<dependencies>
					<dependency>
						<groupId>org.apache.lucene</groupId>
						<artifactId>lucene-queryparser</artifactId>
						<version>4.6.1</version>
					</dependency>
				</dependencies>
			</project>

		2. 执行

				mvn -f pom.xml dependency:copy-dependencies

		3. 运行完成后新增target文件夹，其下有一个dependency文件夹，里面就是下载的jar包。


# 单元测试

	mvn test > /tmp/test.log