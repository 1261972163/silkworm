---
title: vagrant实践
categories: 虚拟化
tags:
	- vagrant
date: 2014/6/18 20:46:25
---

[官方文档](http://docs-v1.vagrantup.com/v1/docs/getting-started/index.html)

# 1 准备工作

* [VirtualBox](https://www.virtualbox.org/)，Vagrant依赖VirtualBox来创建各种虚拟化环境。 
* [Vagrant](http://www.vagrantup.com/)，命令系统。
* [各种box地址](http://www.vagrantbox.es/)，该地址提供各种操作系统box的下载地址。 

# 2 安装

 （1）安装VirtualBox
 例：安装到D:\Program Files\VirtualBox

 （2）安装Vagrant
 例：安装到D:\Program Files\Vagrant

# 3 配置

## 3.1 环境变量

将VirtualBox、Vagrant所对应的exe路径加入到系统环境变量PATH中，以便后续在cmd.exe中使用。

## 3.2 虚拟环境配置

### 3.2.1 添加官方box镜像

	$ vagrant box add lucid32 http://files.vagrantup.com/lucid32.box

### 3.2.2 为当前目录指定运行镜像（之前添加的lucid32镜像）

	$ vagrant init lucid32

## 3.3 网络配置（提供三种模式）

### 3.3.1 端口映射【默认】。

将虚拟机中的端口映射到宿主机对应的端口直接使用 ，在Vagrantfile中配置：

	config.vm.network :forwarded_port, guest: 80, host: 8080

### 3.3.2 private_network。

为虚拟机设置IP，自己自由的访问虚拟机，但是别人不能访问虚拟机，在Vagrantfile中配置：

	config.vm.network :private_network, ip: "192.168.1.111"

### 3.3.3 局域网DHCP。

将虚拟机作为当前局域网中的一台计算机，在Vagrantfile中配置：

	config.vm.network :public_network

## 3.4 目录映射

 默认情况下，本地的工作目录，会被映射到虚拟机的 /vagrant 目录，本地目录下的件可以直接在 /vagrant 下进行访问，在Vagrantfile中配置：

	config.vm.synced_folder "localroot/", "/var/virtualroot"

 第一个参数“localroot/”表示本地路径，该路径是本地工作目录的相对路径，也可以使用绝对路径。
 第二个参数“/var/virtualroot”表示所映射的虚拟机中的目录。


# 4 使用

## 4.1 虚拟机管理命令

	vagrant up （启动虚拟机）
	vagrant halt （关闭虚拟机——对应就是关机）
	vagrant suspend （暂停虚拟机——只是暂停，虚拟机内存等信息将以状态件的方式保存在本地，可以执行恢复操作后继续使用）
	vagrant resume （恢复虚拟机 —— 与前面的暂停相对应）
	vagrant destroy （删除虚拟机，删除后在当前虚拟机所做进行的除开Vagrantfile中的配置都不会保留）

## 4.2 登录虚拟机（建议使用第三方ssh软件secureCRT或XShell）

* 启动虚拟机：vagrant up
* 使用ssh：vagrant ssh
* 登录虚拟机：账号密码均为vagrant，若需要su root，密码也是vagrant

# 5 注意事项

## 5.1 

若在vagrant up过程中，由于网络等问题，出现镜像下载中断，开发者可使用Vagrantfile中config.vm.box\_url指定的地址自行下载镜像；完成后修改配置项config.vm.box_url为已下载的存放地址，如
 
	config.vm.box_url = "file://D:/path/to/vm.box" (windows) 

或者 

	config.vm.box_url = "file:///root/path/to/vm.box" (*nix)

## 5.2 

确保你的环境中系统盘或根目录有2G空闲，若virtualbox使用的虚拟介质也存储在系统盘或根目录，则至少需要4G空闲。

## 5.3

执行"vagrant up"后在提示Waiting for VM to boot. This can take a few minutes.处卡住的情况，可参看 https://github.com/mitchellh/vagrant/issues/455 ，强力处理方法为在virtualbox中手动重启虚拟机。


