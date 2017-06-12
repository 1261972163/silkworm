---
title: 《Maven实战》笔记
categories: 工程管理
tags: 
	- 工具
	- 代码管理
	- maven
date: 2013/8/13 20:03:00
---


《Maven实战》笔记
==================

本文要点：

* maven是什么?
* 

相关资料：

* 

## 1 用途

Maven是一个构建工具，管理项目的生命周期。同时它又是一个依赖管理工具和项目信息管理工具。而我们需要做的仅仅是编写一个称为project object model (POM)的pom.xml文件。

## 2 安装

略 

## 3 使用Archetype生成项目骨架

### 3.1 创建普通的j2se项目

* mvn archetype:create -DgroupId=weitry.demo.mvn（项目组） -DartifactId=mvndemo(项目ID) -DarchetypeArtifactId=maven-archetype-quickstart（项目类型）
* 进入mvndemo目录，mvn clean install -Dmaven.test.skip=true
* mvn eclipse:eclipse
* 把test项目导入eclipse即可

示例：

	[root@test ~]# mvn archetype:create -DgroupId=com.wy -DartifactId=demo -DarchetypeArtifactId=maven-archetype-quickstart
	[root@test ~]# cd demo
	[root@test ~]# mvn clean install -Dmaven.test.skip=true
	[root@test ~]# mvn eclipse:eclipse

### 3.2 创建web项目

把maven-archetype-quickstart替换为maven-archetype-webapp即可。

若eclipse没有WTP插件，第三步需要改为 mvn eclipse:eclipse -Dwtpversion=2.0，指示生成的工程为WTP工程。

示例：

	[root@test ~]# mvn archetype:create -DgroupId=com.wy -DartifactId=webdemo -DarchetypeArtifactId=maven-archetype-webapp
	[root@test ~]# cd webdemo
	[root@test ~]# mvn clean install -Dmaven.test.skip=true
	[root@test ~]# mvn eclipse:eclipse -Dwtpversion=2.0

## 4 基本的pom.xml

一个基本的pom.xml文件：

	<?xml version="1.0" encoding="UTF-8"?>
	<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
	
		<modelVersion>4.0.0</modelVersion>
		<groupId>com.wy</groupId>
		<artifactId>demo</artifactId>
		<version>1.0-SNAPSHOT</version>
		<name>demo project</name>
		<packaging>jar</packaging>
	</project>

* 第一行是xml头；
* project是pom.xml的根元素，声明了一些pom相关的命名空间和xsd元素；
* modelVersion是当前pom模型的版本；
* groupId、artifactId和version定义了一个项目的基本坐标，分别表示项目所属组、项目id和项目版本号；
* name是更为友好的项目名称，和项目描述差不多，不是必须的。
* packaging定义项目的打包方式，默认打包jar.

## 5 依赖

### 5.1 dependencies和dependency

dependencies元素声明项目的依赖，可包含多个dependency，每个dependency声明一个依赖。

	<dependencies>
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>4.5</version>
			<type>jar</type>
			<scope>test</scope>
		</dependency>
	</dependencies>

* type是依赖的类型，对应项目的packaging，可不声明，默认为jar。
* scope为依赖范围。
* optional标记依赖是否可选。
* exclusions用来排除传递性依赖。

### 5.2 依赖范围

依赖范围，用来控制和三种classpath（complie、test和runtime）的关系。可选的依赖范围包括compile、test、provided、runtime、system和import，默认为compile。。

### 5.3 传递性依赖

第一直接依赖和第二直接依赖的范围决定传递性依赖的范围。

### 5.4 依赖调解

传递性依赖可能导致依赖的冲突（版本号不一致），必须选择一个，有以下原则：

* 路径最近者优先；
* 路径相同时，第一声明者（pom中的位置）优先。

### 5.5 可选依赖

可选依赖适用于传递性依赖。

### 5.6 排除依赖

剔除传递性依赖，改用当前项目声明的依赖。参考4.7的示例。

### 5.7 归类依赖

同一项目的不同模块，版本一起管理。例如：

	<properties>
		<spring.version>3.0.7.RELEASE</spring.version>
		<spring.security.version>2.0.5.RELEASE</spring.security.version>
		<spring.ldap.version>1.3.1.RELEASE</spring.ldap.version>
		<blazeds.version>3.2.0.3978</blazeds.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-core-tiger</artifactId>
			<version>${spring.security.version}</version>
			<exclusions>
				<exclusion>
					<artifactId>spring-core</artifactId>
					<groupId>org.springframework</groupId>
				</exclusion>
			</exclusions>
		</dependency>
	
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-core</artifactId>
			<version>${spring.version}</version>
		</dependency>
	</dependencies>

### 5.8 优化依赖

查看当前项目的已解析依赖：

	mvn dependency:list

查看当前项目的依赖树：

	mvn dependency:tree

## 6 仓库

### 6.1 本地仓库

本地设置settings.xml文件，如：

	<settings>
		<localRespository>D:\opt\maven\reposity\</localRespository>
	</settings>

### 6.2 远程仓库

### 6.3 中央仓库

Maven的安装文件自带了中央仓库的配置。

### 6.4 私服

## 7 生命周期和插件

Maven的生命周期是对所有构建过程的抽象和统一，实际任务由插件完成。

Maven拥有三套相互独立的生命周期，分别是clean、default和site。

### 7.1 clean

目的是清理项目，包含三个阶段：

* pre-clean
* clean
* post-clean

### 7.2 default

目的是构建项目，是生命周期中最核心的部分，主要阶段包括：

* validate
* initialize
* generate-sources
* process-sources
* generate-resources
* process-resources
* compile
* process-classes
* generate-test-sources
* process-test-sources
* generate-test-resources
* process-test-resources
* test-compile
* process-test-classes
* test
* prepare-package
* package
* pre-integration-test
* integration-test
* post-integration-test
* verify
* install
* deploy
* 

### 7.3 site

目的是建立项目站点和发布站点，包括如下阶段：

* pre-site
* site
* post-site
* site-deploy



## 用mvn快速搭建maven的JavaEE IDE 项目

### 创建普通的j2se项目

1、mvn archetype:create -DgroupId=weitry.demo.mvn（项目组） -DartifactId=mvndemo(项目ID) -DarchetypeArtifactId=maven-archetype-quickstart（项目类型）

2、进入mvndemo目录，mvn clean install -Dmaven.test.skip=true

3、mvn eclipse:eclipse

4、把test项目导入eclipse即可

### 创建web项目

把maven-archetype-quickstart替换为maven-archetype-webapp即可

若eclipse没有WTP插件，第三步需要改为 mvn eclipse:eclipse -Dwtpversion=2.0，指示生成的工程为WTP工程。

## maven的方式如何导入包？

（1）添加网上的jar包的方法。

首先去http://mvnrepository.com（maven的jar包网站）这个网站搜索到相应的jar包，然后找到相应的版本，获得jar包的dependency，
例如：

	<dependency>
	<groupId>org.json</groupId>
	<artifactId>json</artifactId>
	<version>20090211</version>
	</dependency>

把这份文件添加到pom.xml文件当中，工具就会自动下载其中的jar包。

（2）添加网上maven资源中不存在的jar包的方法。  

需要使用到命令：
	
	mvn install:install-file -Dfile=%_jar% -DgroupId=%_gid% -DartifactId=%_aid% -Dversion=%_ver% -Dpackaging=%_pkg%
	%_jar%：输入jar的文件位置（本地文件的地址）
	%_gid%：输入groupid（一般都是jar包的名字）
	%_aid%:输入artifactid（和上面的差不多，范围更细）
	%_ver%:输入version（版本号）
	%_pkg%:输入打包方式（默认方式是jar）

例子：
	
	mvn install:install-file -Dfile=C:\Users\liuxianglei\Desktop\linshi\jar包专区\open-api-sdk-2.0.jar -DgroupId=com.jd.open.api -DartifactId=open-api-sdk -Dversion=2.0  -Dpackaging=jar

然后打包成功。

之后jar包就会被导入到Maven Dependencies中，但是其中的jar还是在本地。



使用了这么长时间的maven，今天突然意识到一个问题：为什么我们要使用Maven?

于是，搜索一下，作出以下mark！

Maven是一个采用纯Java编写的开源项目管理工具。它采用一种被称之为project object model (POM)概念来管理项目，所有的项目配置信息都被定义在一个叫做POM.xml的文件中。通过该文件，Maven可以管理项目的整个声明周期，包括编译、构建，测试，发布、报告等等。目前Apache下绝大多数项目都已经采用Maven进行管理。同时，Maven本身还支持多种插件，可以方便更灵活的控制项目。 
说到”为什么使用Maven？“，你准会想到Ant，老想拿Maven和这个build元老级工具比一比谁牛。可Maven却不想和Ant比肩，他的目标更远大。Ant确实是一个很强大的build工具箱，而maven不想成为第二个。他的想法是想基于模式来建立一个基础架构，这个架构具有可视性、可重用性、可维护性和易于理解等特性。 

## Maven的法宝？

1.Archetype原型

利用Archetype，maven能快速帮你生成一个基本的项目，统一的目录结构，基本的配置文件。为什么这样建立目录，这就是总结前人的经验了。这就是项目目录模式吧。

2.POM 项目对象模型 

pom提供了一个项目的所有相关信息和配置细节。这样，通过一个pom.xml，你就能全面的了解这个项目的各个方面，当然除了项目代码。通过这个文件，你可以build整个项目，直至发布。

参考资料：

1.http://jackycheng2007.iteye.com/blog/923791

2.http://jackycheng2007.iteye.com/blog/966144

## Maven的优势？


1. Maven的库是由开源组织维护，不需要我们再花精力去管第三方库，即使自己维护，也比较方便；

2. Maven对jar包的版本管理有工具上的支持，比如将Release版本和Snapshot版本区分开，有利于SCM管理；

3. Maven是标准，用过的人多，不需要额外培训；

4. Maven的plugin比较多，可以有更多功能，Maven现有体系比较开放，采用的技术相对比较通用和成熟，plugin的机制也可以便于我们扩展更多功能；

5. Maven的库下载是即用即下，不需要实现全部down下来。Maven的插件也是自动升级，可以方便我们扩展新功能；

6. 可以很方便的与eclipse、IDEA这样的主流的IDE集成；

7. 仓库管理器：它的出现有两个目的：首先它的角色是一个高度可配置的介于你的组织与公开Maven仓库之间的代理，其次它为你的组织提供了一个可部署你组织内部生成的构件(第二方库)的地方；

8. 版本管理功能，这里的版本管理不是指第三方库的版本管理，而是项目的版本管理；

9. 站点功能：它的出现让我们可以对项目的状态一目了然，可以自动的把项目的状态和各种报表以站点的形式发布到内部网或者外部网，可以随时随地查看项目状态。 有很多中报表可以选择，包括，doc生成，代码规范的检查，自动bug检查，单元测试报表，单元测试的代码覆盖率报表。

参考文献：

1.http://www.360doc.com/content/09/0707/11/7147_4172255.shtml



## 其他一些有意思的看法：

1.http://juvenshun.iteye.com/blog/250855


2.http://blog.csdn.net/wangzhewang/article/details/7217853


写完这篇文章才发现一个比我介绍更详细的文档，可忽略本文章，直接移步：

《（精）为何使用manven，什么是manven》http://blog.csdn.net/it_man/article/details/7829699

