---
title: Cat实践
categories: 运维
tags:
  - 监控
date: 2017/6/28 16:52:25
---

# 安装

* jdk7
* maven3
* MySQL

步骤：

  git clone https://github.com/dianping/cat.git
  cd cat
  mvn clean install -DskipTests
  mvn cat:install
    Please input jdbc url:[jdbc:mysql://127.0.0.1:3306]jdbc:mysql://10.45.11.84:3306
    Please input username:root
    Please input password:[]MyNewPass4!
    # 这个地方要特别说明一下，如有cat的源文件在E盘，则相关配置文件就应该放在e:/data/appdatas/cat/下面
  cd cat-home
  mvn jetty:run
  http://localhost:2281/cat

最最最后再说一下，为啥要指定创建/data/appdatas/cat和/data/applogs/cat这两个目录呢，因为cat客户端要上报给服务端，cat-client.jar包里写死的从/data/appdatas/cat读取配置，向/data/applogs/cat输出日志。
