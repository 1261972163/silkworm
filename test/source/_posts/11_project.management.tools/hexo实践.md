---
title: hexo实践
categories: default
tags: 
  - hexo
date: 2017/6/3 11:59:00
---

[hexo-theme-next](https://github.com/iissnan/hexo-theme-next)

[hexo使用指南](https://hexo.io/zh-cn/docs/index.html)


# 安装Hexo


	$ cd ~/workspace/hexo
	$ npm install hexo-cli -g
	$ hexo init blog
	$ cd blog
	$ npm install
	$ hexo g # 或者hexo generate
	$ hexo s # 或者hexo server，可以在http://localhost:4000/ 查看

这里有必要提下Hexo常用的几个命令：

	hexo generate (hexo g) 生成静态文件，会在当前目录下生成一个新的叫做public的文件夹
	hexo server (hexo s) 启动本地web服务，用于博客的预览
	hexo deploy (hexo d) 部署播客到远端（比如github, heroku等平台）

另外还有其他几个常用命令：

	$ hexo new "postName" #新建文章
	$ hexo new page "pageName" #新建页面

# 安装主题

	git clone https://github.com/iissnan/hexo-theme-next.git themes/next


# 部署到Github Pages

先使用下面的命令对Git进行初始配置。

	$ git config --global user.name "your name"
	$ git config --global user.email "email@email.com"

	ssh-keygen -t rsa -b 4096 -C "jerryweimail@gmail.com"

然后打开Hexo主文件夹下的_config.yml，设置其中的deploy 参数，详细请查看Hexo官方文档中部署部分。

我的设置如下所示：

	deploy:
	  type: git 
	  repo: ssh://git@github.com/nouuid/nouuid.github.io.git
	  branch: master

安装 hexo-deployer-git 插件，只有安装了插件之后才可以部署到Github Pages。

	npm install hexo-deployer-git --save


# 图床

	1. 确认 _config.yml 中有 post_asset_folder:true 
	2. hexo 目录执行
		npm install https://github.com/CodeFalling/hexo-asset-image --save