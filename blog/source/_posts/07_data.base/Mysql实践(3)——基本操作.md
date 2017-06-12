---
title: Mysql实践(3)——基本操作
categories: 数据库
date: 2016/11/1 20:46:25
tags:
	- mysql
---

基本操作

启动：sudo service mysqld start
关闭：sudo service mysqld stop
状态：sudo service mysqld status
重启：sudo service mysqld restart

root密码：

	（1）获取临时密码：sudo grep 'temporary password' /var/log/mysqld.log
	（2）使用临时密码登录：mysql -uroot -p
	（3）修改密码：ALTER USER 'root'@'localhost' IDENTIFIED BY 'MyNewPass4!';

mysql账户远程访问授权，默认只能在localhost访问。其他机器使用myuser/mypassword访问mysql服务器需要授权。

	* 授权指定机器访问：

		GRANT ALL PRIVILEGES ON *.* TO 'myuser'@'192.168.1.3' IDENTIFIED BY 'mypassword' WITH GRANT OPTION;

	* 授权任意机器访问：

		GRANT ALL PRIVILEGES ON *.* TO 'myuser'@'%' IDENTIFIED BY 'mypassword' WITH GRANT OPTION;

查看最大连接数：

	show variables like 'max_connections';

修改最大连接数：

	set GLOBAL max_connections=1000;

查看表信息：

	show table status from sbtest where name='sbtest1';

查看表创建命令（有可能出错）：
	
	show create table sbtest1;

查看innodb_buffer_pool的使用情况：

	show engine innodb status\G;

进去指定schema 数据库（存放了其他的数据库的信息） 
	
	mysql> use information_schema;
	Database changed

查询所有数据的大小 

	mysql> select concat(round(sum(DATA_LENGTH/1024/1024), 2), 'MB')
	    -> as data from TABLES;
	+-----------+
	| data      |
	+-----------+
	| 6674.48MB |
	+-----------+
	1 row inset(16.81 sec)

查看指定数据库实例的大小，比如说数据库 forexpert

	mysql> selectconcat(round(sum(DATA_LENGTH/1024/1024), 2), 'MB')
	    -> as data from TABLES where table_schema='forexpert';
	+-----------+
	| data      |
	+-----------+
	| 6542.30MB |
	+-----------+
	1 row inset(7.47 sec)

查看指定数据库的表的大小，比如说数据库 forexpert 中的 member 表 

	mysql> select concat(round(sum(DATA_LENGTH/1024/1024),2),'MB') as data
	    -> from TABLES where table_schema='forexpert'
	    -> and table_name='member';
	+--------+
	| data   |
	+--------+
	| 2.52MB |
	+--------+
	1 row inset(1.88 sec)


找回密码：

	（1）修改my.cnf，在[mysqld]的段中加上一句：skip-grant-tables
	（2）修改密码，MySQL 5.7.6 以及最新版本：
		# mysql -uroot
		# mysql> use mysql
		# mysql> update user set authentication_string=PASSWORD('MyNewPass4!') where User='root';
		# mysql> FLUSH PRIVILEGES;
		# mysql> exit
	（3）修改my.cnf，去除skip-grant-tables