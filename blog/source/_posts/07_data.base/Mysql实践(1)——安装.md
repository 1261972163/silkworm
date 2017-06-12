---
title: Mysql实践(1)——安装部署
categories: 数据库
date: 2016/11/1 20:46:25
tags:
	- mysql
---

# 1 Linux下安装

## 1.1 下载mysql

    下载mysql-5.7.13-1.el6.x86_64.rpm-bundle.tar（http://dev.mysql.com/downloads/mysql/）

## 1.2 解压tar包

    tar xvf mysql-5.7.13-1.el6.x86_64.rpm-bundle.tar

    得到

	mysql-community-client-5.7.13-1.el6.x86_64.rpm
	mysql-community-common-5.7.13-1.el6.x86_64.rpm
	mysql-community-devel-5.7.13-1.el6.x86_64.rpm
	mysql-community-embedded-5.7.13-1.el6.x86_64.rpm
	mysql-community-embedded-devel-5.7.13-1.el6.x86_64.rpm
	mysql-community-libs-5.7.13-1.el6.x86_64.rpm
	mysql-community-libs-compat-5.7.13-1.el6.x86_64.rpm
	mysql-community-server-5.7.13-1.el6.x86_64.rpm
	mysql-community-test-5.7.13-1.el6.x86_64.rpm

## 1.3 yum方式安装

    sudo yum install mysql-community-{server,client,common,devel,libs,embedded,test}-* mysql-5.7 

	（指定安装目录加--installroot=/usr/local/mysql ）

	如果出现错误：
	    Requires: libssl.so.10(libssl.so.10)(64bit)
	则卸载
	    rpm --nodeps -e openssl
	下载并安装openssl10-libs-1.0.1e-1.ius.el6.x86_64.rpm
	    rpm -ivh openssl10-libs-1.0.1e-1.ius.el6.x86_64.rpm
	重新yum

安装完成后

MySQL Installation Layout for Linux RPM Packages from the MySQL Developer Zone

![](/resources/mysql/MySQL Installation Layout for Linux RPM Packages from the MySQL Developer Zone.png)

默认情况下，会生成一个名为mysql的用户和名为mysq的组。

参考：

http://dev.mysql.com/doc/refman/5.7/en/linux-installation-rpm.html

# 2 Linux下卸载MySQL

[Linux平台卸载MySQL总结——潇湘隐者](http://www.cnblogs.com/kerrycode/p/4364465.html)

# 3 MAC下卸载MySQL

mac下mysql的DMG格式安装内有安装文件，却没有卸载文件……很郁闷的事。

网上搜了一下，发现给的方法原来得手动去删。

很多文章记述要删的文件不完整，后来在stackoverflow这里发现了一个遗漏的地方，所以将完整版记述在这里，以供查阅。

先停止所有mysql有关进程。



	sudo rm /usr/local/mysql
	sudo rm -rf /usr/local/mysql*
	sudo rm -rf /Library/StartupItems/MySQLCOM
	sudo rm -rf /Library/PreferencePanes/My*
	vim /etc/hostconfig  (and removed the line MYSQLCOM=-YES-)
	rm -rf ~/Library/PreferencePanes/My*
	sudo rm -rf /Library/Receipts/mysql*
	sudo rm -rf /Library/Receipts/MySQL*
	sudo rm -rf /var/db/receipts/com.mysql.*

最后这条很多文章都丢了，切记切记。
