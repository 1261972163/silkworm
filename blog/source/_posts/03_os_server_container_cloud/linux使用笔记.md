---
title: Linux使用笔记
categories: os
tags:
  - linux
date: 2011/2/23 17:37:25
---

* [Linux教程](http://c.biancheng.net/cpp/html/2726.html)
* [Shell教程](http://c.biancheng.net/cpp/view/6994.html)
* [crontab 定时任务](http://linuxtools-rst.readthedocs.io/zh_CN/latest/tool/crontab.html)
* [linux下远程传输文件的方式sftp.scp,rsync,rcp](http://daaoao.blog.51cto.com/2329117/668298)
* [通过IOStat命令监控IO性能](http://blog.csdn.net/lucien166/article/details/38318775)
* [netstat 的10个基本用法](https://linux.cn/article-2434-1.html)


常用命令：


# 1. 文件管理

机器间文件拷贝scp

	格式：
	本机到远程机器：scp localpath remoteuser@remotehost:remotepath
	远程机器到本机：scp remoteuser@remotehost:remotepath localpath

	示例：
	scp -r /home/wwwroot/www/charts/util root@192.168.1.65:/home/wwwroot/limesurvey_back/scp
	scp -r root@192.168.1.65:/home/wwwroot/limesurvey_back/scp /home/wwwroot/www/charts/util

	注意两点：
	1.如果远程服务器防火墙有特殊限制，scp便要走特殊端口，具体用什么端口视情况而定，命令格式如下：
	#scp -p 4588 remote@www.abc.com:/usr/local/sin.sh /home/administrator
	2.使用scp要注意所使用的用户是否具有可读取远程服务器相应文件的权限。

计算指定模式文件的大小（单位：kb）

	du -sk my-*-20160307 | awk '{sum+=$1};END{print sum}'

查找文件名中包含某字符（如"elm"）的文件

	find / -name '*elm*'

# 2. 进程管理

查看进程启动时间

    ps -A -opid,stime,etime,args | grep pid    

查看进程号为28174的占用cpu最高的线程

	top -Hp 28174 -d 1 -n 1


将 cpu 占用率高的线程找出来

	ps H -eo user,pid,ppid,tid,time,%cpu,cmd --sort=%cpu

	ps Hh -eo pid,tid,pcpu | sort -nk3 |tail

# 3. 网络通信

端口映射（80映射到8080）

	root权限下执行：
	iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-port 8080
	service iptables save
	service iptables restart

查看端口连接数

	netstat -anlt | grep 8080 | grep ESTABLISHED | wc -l

修改用户进程可打开文件数限制

	# 查看
	ulimit -n
	# 修改，假设用户为nouuid
	vim /etc/security/limits.conf
	>
		nouuid soft nofile 10240
   		nouuid hard nofile 10240


反查域名

	nslookup 10.9.13.110

修改DNS

	vim /etc/resolv.conf

查看当前机器被连接的机器

	netstat -tun|grep 8080|sort|awk '{print $5}'|cut -d : -f 1|uniq

重启网卡

	/etc/init.d/networking restart

刷新下本地域名缓存

	（linux）：sudo /etc/init.d/networking restart
	（windows）：ipconfig /flushdns

# 4. 软件安装和卸载

配置国内yum源

	1. 进入yum源配置目录
		cd /etc/yum.repos.d
	2. 备份系统自带的yum源
		mv CentOS-Base.repo CentOS-Base.repo.bk
	3. 下载更新国内yum源（网易/搜狐/中科大）
		网易：wget http://mirrors.163.com/.help/CentOS6-Base-163.repo
		搜狐：wget http://mirrors.sohu.com/help/CentOS-Base-sohu.repo
		中科大：wget http://centos.ustc.edu.cn/CentOS-Base.repo
	4. 更新yum配置，立即生效
		yum makecache

常用软件

	yum -y install vim*
	yum -y install sysstat


软件安装和卸载

	1. 检查系统是否安装prce，，如果已安装则会显示pcre的版本信息
		[root@localhost /]# rpm -qa pcre
		pcre-7.8-6.el6.i686
	2. 删除pcre包
		[root@localhost /]# rpm -e --nodeps pcre
	3. 在线安装pcre
		[root@localhost /]# yum -y install pcre
	4. 查看pcre的安装路径
		[root@localhost /]# rpm -qa pcre
		pcre-7.8-6.el6.i686

		[root@localhost /]# rpm -ql pcre-7.8-6.el6.i686
		/lib/libpcre.so.0
		/lib/libpcre.so.0.0.1
		/usr/bin/pcregrep
		/usr/bin/pcretest
		/usr/lib/libpcrecpp.so.0
		/usr/lib/libpcrecpp.so.0.0.0
		/usr/lib/libpcreposix.so.0
		/usr/lib/libpcreposix.so.0.0.0
		/usr/share/doc/pcre-7.8
		/usr/share/doc/pcre-7.8/AUTHORS
		/usr/share/doc/pcre-7.8/COPYING
		/usr/share/doc/pcre-7.8/ChangeLog
		/usr/share/doc/pcre-7.8/LICENCE
		/usr/share/doc/pcre-7.8/NEWS
		/usr/share/doc/pcre-7.8/README
		/usr/share/man/man1/pcre-config.1.gz
		/usr/share/man/man1/pcregrep.1.gz
		/usr/share/man/man1/pcretest.1.gz

# 5. 系统

修改系统文件打开数

	vim /etc/security/limits.conf

	limits.conf的格式如下：
	    <domain> <type>  <item> <value>

	<domain> :可以指定单个用户名、@组名、所有用户（\*）
	type：有 soft（指的是当前系统生效的设置值），hard（表明系统中所能设定的最大值）和 -（表明同时设置了 soft 和 hard 的值）

打印进程号为pid的进程个线程的cpu，内存等资源占用情况

    top -Hp pid -d 1 -n 1

查看CPU信息

    cat /proc/cpuinfo

修改主机名

    vi /etc/sysconfig/network
    设置HOSTNAME

关闭无操作中断ssh功能

    vim /etc/profile
    注释掉export TMOUT

查看操作系统版本

    cat /etc/issue 或 cat /etc/redhat-release

查看64/32位

    getconf LONG_BIT

查看系统约束

    cat /proc/<PID>/limits

动态修改运行中进程的 rlimit

	[参考](http://xiezhenye.com/2013/02/%E5%8A%A8%E6%80%81%E4%BF%AE%E6%94%B9%E8%BF%90%E8%A1%8C%E4%B8%AD%E8%BF%9B%E7%A8%8B%E7%9A%84-rlimit.html)

    echo -n 'Max open files=655350:655350' > /proc/<PID>/limits


# Linux服务器设置ssh超时自动注销

  vi /etc/profile

    HISTFILESIZE=5 #history command
    HISTSIZE=5             #history command
    TMOUT=300              #ssh timeout(sec)
