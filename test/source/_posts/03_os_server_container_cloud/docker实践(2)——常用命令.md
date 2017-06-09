---
title: docker实践(2)——常用命令
categories: container
tags:
    - docker
date: 2016/6/1 20:46:25
---

[dock所有命令](https://docs.docker.com/engine/reference/commandline/cli/)可以分为以下几类：

    1. 容器生命周期  ：  docker [ run | start | stop | restart | kill | rm | pause | unpause ]
    2. 容器操作运维  ：  docker [ ps | inspect | top | attach | events | exec | logs | wait | export | port | checkpoint | container | create | network | node | plugin | rename | secret | service | stats | deploy | stack | swarm | system | update | volume | ]
    3. 容器rootfs命令：  docker [ commit | cp | diff ]
    4. 镜像仓库      ：  docker [ login | logout | pull | push | search ]
    5. 本地镜像管理  ：  docker [ images | rmi | tag | build | history | save | import | load ]
    6. 环境信息相关  ：  docker [ info | version ]

# 1 容器生命周期管理

    docker [ run | start | stop | restart | kill | rm | pause | unpause ]

**run**

> docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
> * 使用image创建container并执行相应命令

    # 使用image创建container并执行相应命令，然后停止
    docker run ubuntu echo "hello world"
    
    # 使用image创建container并进入交互模式, login shell是/bin/bash，--name参数可以指定启动后的容器名字
    docker run -i -t --name mytest ubuntu /bin/bash 
    
    # 运行出一个container放到后台运行
    # 如果-d启动但后面的命令执行完就结束了，如/bin/bash、echo test，则container做完该做的时候依然会终止。
    # 而且-d不能与--rm同时使用，
    # 可以通过这种方式来运行memcached、apache等。
    docker run -d ubuntu /bin/sh -c "while true; do echo hello world; sleep 2; done"
    
    # 映射host到container的端口
    # -p <host_port:contain_port>
    -p 11211:11211      # 默认情况，绑定主机所有网卡（0.0.0.0）的11211端口到容器的11211端口上
    -p 127.0.0.1:11211:11211   # 只绑定localhost这个接口的11211端口
    -p 127.0.0.1::5000
    -p 127.0.0.1:80:8080
    
    # 映射host的路径到container的目录
    # -v <host_path:container_path>
    -v /tmp/docker:/tmp/docker
    
    # 在两个container之间建立联系

**start/stop**

> * 容器可以通过run新建一个来运行，也可以重新start已经停止的container。
> * 但start不能够再指定容器启动时运行的指令，因为docker只能有一个前台进程。
> * 容器stop（或Ctrl+D）时，会在保存当前容器的状态之后退出，下次start时保有上次关闭时更改。而且每次进入attach进去的界面是一样的，与第一次run启动或commit提交的时刻相同。

    docker start <containner_id>
    docker stop <containner_id>
    docker restart <containner_id>

**restart**

    # kill容器之前等待一段时间（默认10s），然后启动容器
    docker restart <containner>
    docker restart -t 10 <containner>

**kill**

    # kill一个或多个容器
    docker kill <containner ...>

**rm**

> * 删除一个或多个container、image
> * 同一个IMAGE ID可能会有多个TAG（可能还在不同的仓库），首先你要根据这些 image names 来删除标签，当删除最后一个tag的时候就会自动删除镜像；
> * 承上，如果要删除的多个IMAGE NAME在同一个REPOSITORY，可以通过docker rmi <image_id>来同时删除剩下的TAG；若在不同Repo则还是需要手动逐个删除TAG；
> * 删除镜像，必须先删除由这个镜像启动的容器（即使已经停止）。

    # 删除一个或多个容器
    docker rm <container ...>
    # 删除所有停止的容器
    docker rm $(docker ps -a -q)
    
    # 删除镜像
    docker rmi <image ...>
    
    # 删除test仓库中docker-whale的latest tag
    docker rmi test/docker-whale:latest

**pause/unpause**

    # 暂停一个或多个容器
    docker pause <container ...>
    # 解除暂停一个或多个容器
    docker unpause <container ...>

# 2. 容器操作运维

    docker [ ps | inspect | top | attach | events | logs | wait | export | port ]

**ps**

> * 查看容器信息，包括CONTAINER ID、IMAGE、COMMAND、CREATED、STATUS、PORTS和NAMES
    
    docker ps # 只显示运行中的容器
    docker ps -a # 显示所有容器，包括已停止的

**inspect**

> * 查看image或container的底层信息
> * inspect的对象可以是image、运行中的container和停止的container。

    # 查看容器的内部IP
    docker inspect --format='{{.NetworkSettings.IPAddress}}' $CONTAINER_ID


**top**

    # 显示CONTAINER ID（如5caf5b5890ff）容器中正在运行的线程
    docker top 5caf5b5890ff

**attach**

> * attach连接到正在运行的容器终端
> * 使用Ctrl+C，退出容器，同时container停止运行。
> * （亲测无效）按ctrl-p ctrl-q可以退出到宿主机，同时保持container运行。
> * 在attach是可以带上--sig-proxy=false来确保CTRL-D或CTRL-C不会关闭容器。

    docker attach 5caf5b5890ff

**logs**

    # 可在container外面查看指定CONTAINER ID（如5caf5b5890ff）的日志
    docker logs ae60c4b64205
    
**export**

    # 导出容器快照到本地x.tar
    docker export <containter> > x.tar

# 3. 容器rootfs命令

    docker [ commit | cp | diff ]

**commit**

    docker commit <container> [repo:tag]

    # 将一个container固化为一个新的image
    # 只能提交正在运行的container，即通过docker ps可以看见的容器
    docker commit -m "some tools installed" fcbd0a5348ca seanlook/ubuntu:14.10_tutorial

# 4. 镜像仓库

docker [ login | pull | push | search ]

**login**

**pull**

    # 从官服的centos仓库下载tag为latest的镜像                  
    docker pull centos
    
    # 从官服的centos仓库下载tag为centos6的镜像                                
    docker pull centos:centos6         
    
    # 从官服的个人username的公共仓库centos下载tag为centos6的镜像               
    docker pull username/centos:centos6
    
    # 从私服mongo仓库下载tag为latest的镜像
    docker pull dl.dockerpool.com:5000/mongo:latest   

**push**

    # push与上面的pull对应，可以推送到Docker Hub的Public、Private以及私服，但不能推送到Top Level Repository。
    docker push username/mongo
    docker push registry.tp-link.net:5000/mongo:2014-10-27

**search**

    # 在docker index中搜索hello-world，搜索的范围是官方镜像和所有个人公共镜像
    docker search hello-world

# 5. 本地镜像管理

docker [ images | rmi | tag | build | history | save | import ]

**images**

    # 列出机器上的镜像
    docker images

**build**
> * docker build [OPTIONS] PATH | URL | -
> * build命令可以从Dockerfile和上下文来创建镜像

**import**

    # 从容器快照文件x.tar导入为镜像
    cat x.tar | docker import - <server>/<registry>/<repository>:<version>

**load**

    # 导入镜像存储文件到本地镜像，和import类似。
    # 区别在于容器快照文件将丢弃所有的历史记录和元数据信息（即仅保存容器当时的快照状态），
    # 而镜像存储文件将保存完整记录，体积也要大。
    cat x.tar | docker load - <server>/<registry>/<repository>:<version>

# 6. 环境信息相关

    docker [ info | version ]


    docker info
    docker version
