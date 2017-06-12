---
title: Nodejs实践
categories: 服务器
tags: 
	- nodejs
date: 2017/4/3 17:57:25
---

# 1. 安装（Linux）

	wget https://nodejs.org/dist/v6.10.1/node-v6.10.1-linux-x64.tar.xz
	tar -xvf node-v6.10.1-linux-x64.tar.xz
	mv node-v6.10.1-linux-x64 /opt/
	cd /opt/node-v6.10.1-linux-x64
	./bin/node -v （检查是否显示版本）
	vim ~/.bash_profile
		export NODEJS_HOME=/opt/node-v6.10.1-linux-x64
		export PATH=$NODEJS_HOME/bin:$PATH
	source ~/.bash_profile

# 2. 查看配置：

	npm config ls
	npm config list

# 3. 配置npm源：

	# 临时使用
	npm --registry https://registry.npm.taobao.org install express 

	# 持久使用
	npm config set registry https://registry.npm.taobao.org 

	# 验证
	npm config get registry 或者 npm info express 

# 4. 配置node_modules路径：

	mkdir $nodejs_home/node_modules/node_global
	mkdir $nodejs_home/node_modules/node_cache
	npm config set prefix "$nodejs_home/node_modules/node_global"
	npm config set cache "$nodejs_home/node_modules/node_cache"
	“win+R”-->“sysdm.cpl”-->“高级”-->“环境变量”打开设置对话框
	添加NODE_PATH=$nodejs_home/node_modules/node_global
	在path中追加;$nodejs_home

