---
title: 科学上网
categories: office
tags: 
	- 工具
	- vultr
	- shadowsocks
date: 2014/6/3 08:59:00
---

# 1 购买vultr主机

# 2 shadowsocks安装

root用户登录，运行

	wget --no-check-certificate https://raw.githubusercontent.com/teddysun/shadowsocks_install/master/shadowsocksR.sh
	chmod +x shadowsocksR.sh
	./shadowsocksR.sh 2>&1 | tee shadowsocksR.log

password: ******

相关命令：

	卸载：./shadowsocksR.sh uninstall
	启动：/etc/init.d/shadowsocks start
	停止：/etc/init.d/shadowsocks stop
	重启：/etc/init.d/shadowsocks restart
	状态：/etc/init.d/shadowsocks status

	配置文件路径：/etc/shadowsocks.json
	日志文件路径：/var/log/shadowsocks.log
	代码安装目录：/usr/local/shadowsocks

多用户配置 sample：

	{
	"server":"0.0.0.0",
	"server_ipv6": "[::]",
	"local_address":"127.0.0.1",
	"local_port":1080,
	"port_password":{
	    "8989":"password1",
	    "8990":"password2"，
	    "8991":"password3"
	},
	"timeout":300,
	"method":"aes-256-cfb",
	"protocol": "origin",
	"protocol_param": "",
	"obfs": "plain",
	"obfs_param": "",
	"redirect": "",
	"dns_ipv6": false,
	"fast_open": false,
	"workers": 1
	}

如果你想修改配置文件，请参考：

	https://github.com/breakwa11/shadowsocks-rss/wiki/Server-Setup


