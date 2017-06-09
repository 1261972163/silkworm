---
title: docker实践(3)——使用笔记
categories: container
tags:
    - docker
date: 2016/5/1 20:31:25
---

# 编辑Dockerfile
    
    vim Dockerfile
    >
        FROM ubuntu
        
        MAINTAINER dkuuid nouuid@163.com
        
        RUN echo "hello, docker!"
        
        CMD echo "hello, docker!"
        CMD mkdir /opt/tmp
        CMD touch /opt/tmp/1.log

# 根据Dockerfile构建镜像
    
    # /Users/weiyang/Workspace/docker/为镜像所在目录
    docker build -t dkuuid/test1 /Users/weiyang/Workspace/docker/

	# 挂载主机目录作为数据卷
    docker run -v /Users/weiyang/Workspace/docker/test:/opt/tmp dkuuid/test1
    
	# 挂载一个本地主机文件作为数据卷

    # 记录在容器输入过的命令
    docker run --rm -it -v ~/.bash_history:/.bash_history ubuntu /bin/bash
    
# 数据卷容器创建和使用

    # 创建
    docker run -d -v /dbdata --name dbdata training/postgres echo Data-only container for postgres
    # 挂载
    docker run -d --volumes-from dbdata --name db1 training/postgres
    # 备份
    # 将本地/opt/docker/dbdata挂载在dbdata容器的/backup目录，同时将容器的/dbdata打包到/backup/backup.tar
    docker run --volumes-from dbdata -v /opt/docker/dbdata:/backup ubuntu tar cvf /backup/backup.tar /dbdata
    # 恢复
    # 如果要恢复数据到一个容器，首先创建一个带有数据卷的容器 dbdata2。
    # 然后创建另一个容器，挂载 dbdata2 的容器，并使用 untar 解压备份文件到挂载的容器卷中。
    docker run -v /dbdata --name dbdata2 ubuntu /bin/bash
    docker run --volumes-from dbdata2 -v $(pwd):/backup ubuntu tar xvf /backup/backup.tar
    
# 使用网络

    # 映射本地5000端口到容器的5000端口
    docker run -d -p 5000:5000 training/webapp python app.py
    # 映射到指定地址的指定端口
    docker run -d -p 127.0.0.1:5000:5000 training/webapp python app.py
    # 映射到指定地址的任意端口
    docker run -d -p 127.0.0.1::5000 training/webapp python app.py
    # 使用 udp 标记来指定 udp 端口
    docker run -d -p 127.0.0.1:5000:5000/udp training/webapp python app.py
    # 查看映射端口配置
    docker port <container> 5000
    # 自定义容器名
    docker run -d -P --name web training/webapp python app.py
    # db 容器和 web 容器建立互联关系。
    docker run -d --name db training/postgres
    docker run -d -P --name web --link db:db training/webapp python app.py
    
    docker run -t -i --rm --link db:db training/webapp /bin/bash