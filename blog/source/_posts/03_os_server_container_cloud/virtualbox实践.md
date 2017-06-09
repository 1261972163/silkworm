---
title: virtualbox实践
categories: container
tags:
	- virtualbox
date: 2016/6/1 20:46:25
---

# 1 virtualbox安装

1. 下载
2. 安装

# 2 系统安装

1. 下载系统镜像
2. 安装系统
	1. 挂载镜像：Settings/Storage/Controller:IDE/Empty/点击光盘选择
	2. 安装系统
3. 网络配置（Settings/Network/attached to/Bridged Adapter）

# 3 virtualbox功能增强工具包安装

1. 安装编译环境

		sudo apt-get install build-essential

2. 映射设备

	1. VirtualBox/Settings/Storage/Controller:IDE/Empty
	2. 点击挂载VBoxGuestAdditions.iso（VirtualBox安装目录下）镜像
	3. 启动系统，root登陆

3. 将虚拟光盘挂在到/media/cdrom

		sudo mount /dev/cdrom /media/cdrom

4. 执行VBoxLinuxAdditions.run

		cp /media/cdrom/VBoxLinuxAdditions.run /tmp
		chmod a+x /tmp/VBoxLinuxAdditions.run
		./VBoxLinuxAdditions.run

5. 重启系统

		shutdown -r now
		