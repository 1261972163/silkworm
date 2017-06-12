---
title: ubuntu常用维护
categories: os
tags: 
  - ubuntu
date: 2007/6/3 17:57:25
---

设置启动进入命令行

    sudo systemctl set-default multi-user.target

设置启动进入图形界面

    sudo systemctl set-default graphical.target 
    
修改root的ssh权限
    
    vim /etc/ssh/sshd_config
    将PermitRootLogin without-passsward改为PermitRootLogin yes
    /etc/init.d/ssh restart
    
配置jdk

    vim ~/.bashrc
    export JAVA_HOME=/opt/jdk/jdk1.8.0_112
    export CLASSPATH=.:$JAVA_HOME/lib:$JAVA_HOME/jre/lib:$CLASSPATH
    export PATH=$JAVA_HOME/bin:$JAVA_HOME/jre/bin:$PATH
    source ~/.bashrc
    
安装ssh

    # 判断是否安装
    # ssh-agent表示ssh-client启动，sshd表示ssh-server启动了。
    ps -e | grep ssh
    >    2151 ?        00:00:00 ssh-agent
    >    5313 ?        00:00:00 sshd
    
    # 安装ssh-client
    sudo apt-get install openssh-client
    # 安装ssh-server
    sudo apt-get install openssh-server
    # 启动服务
    sudo /etc/init.d/ssh start
    # 修改ssh配置文件
    vim /etc/ssh/sshd_config
    > Port 22
    > PermitRootLogin yes
    # 重启
    /etc/init.d/ssh restart
    

    