---
title: docker实践
categories: container
tags:
    - docker
date: 2016/6/1 20:46:25
---


# 1 Docker解决什么问题？

1.[深入分析 Docker 镜像原理](http://www.open-open.com/lib/view/open1440397640404.html)
2.[深入浅出Docker（一）：Docker核心技术预览](http://www.infoq.com/cn/articles/docker-core-technology-preview/)
3.[10张图带你深入理解Docker容器和镜像](http://www.open-open.com/lib/view/open1446695630904.html)
4.[为何现在流行OpenStack和Docker结合？](https://www.zhihu.com/question/35425470/answer/62993113)

# 2 安装和配置

安装就不说了。这里主要说一下Docker库加速的配置（国内速度太慢）。

**国内加速器**


    国内加速器（个人）：

    1. 阿里云加速

    [阿里云](https://cr.console.aliyun.com/?spm=5176.100239.blogcont29941.11.autsa2#/imageList)
    w**t*y@aliyun.com/A*5*****
    加速器：https://***.mirror.aliyuncs.com
    配置：
    使用如下的脚本将mirror的配置添加到docker daemon的启动参数中

        sudo mkdir -p /etc/systemd/system/docker.service.d
        sudo tee /etc/systemd/system/docker.service.d/mirror.conf <<-'EOF'
        [Service]
        ExecStart=
        ExecStart=/usr/bin/docker daemon -H fd:// --registry-mirror=https://***.mirror.aliyuncs.com
        EOF
        sudo systemctl daemon-reload
        sudo systemctl restart docker

    2. daocloud加速

    [DaoCloud](https://www.daocloud.io)
    n*****@163.com/8******c
    加速器：http://***.m.daocloud.io
    配置：
    （1）mac版，右键点击桌面顶栏的 docker 图标，选择 Preferences ，在 Advanced 标签下的 Registry mirrors 列表中加入下面的镜像地址:
        http://***.m.daocloud.io
    点击 Apply & Restart 按钮使设置生效。
    （2）ubuntu版，修改配置文件：
    vim /etc/default/docker
    > 
        DOCKER_OPTS="--registry-mirror=http://http://***.m.daocloud.io"
    
    service docker restart
