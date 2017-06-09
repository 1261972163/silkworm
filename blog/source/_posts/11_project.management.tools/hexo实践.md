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

	# 下载
	git clone https://github.com/iissnan/hexo-theme-next.git themes/next

	# 启用主题
	vim _config.yml
		theme: next

	# 选择 Scheme
	vim themes/next/_config.yml
		#scheme: Muse
		scheme: Mist
		#scheme: Pisces

	# 设置站点名称、语言
	vim themes/next/_config.yml
		title: 白水不开
		subtitle: 思考你的思考当你在思考
		description: 思考你的思考当你在思考
		author: 白水不开
		language: zh-Hans
		timezone:

	# 菜单
	vim themes/next/_config.yml
		menu:
		  home: /
		  categories: /categories
		  archives: /archives
		  tags: /tags
		  #about: /about
		  skills: /skills
		  commonweal: /404.html
		  search: /search

	# 菜单语言
	vim themes/next/languages/zh-Hans.yml

		menu:
		  home: 首页
		  categories: 分类
		  archives: 归档
		  tags: 标签
		  about: 关于
		  search: 搜索
		  commonweal: 公益404
		  something: 有料
		  skills: 技能

	# 设置菜单图标
	vim themes/next/_config.yml
		menu_icons:
		  enable: true
		  # Icon Mapping.
		  home: home
		  about: user
		  categories: th
		  schedule: calendar
		  tags: tags
		  archives: archive
		  sitemap: sitemap
		  commonweal: heartbeat
		  skills: thumbs-up
		  search: search

	# 设置侧栏
	vim themes/next/_config.yml
		sidebar:
		  position: right
		  display: always
	# 侧边栏社交链接
	vim themes/next/_config.yml
		# Social links
		social:
		  GitHub: https://github.com/your-user-name
		  Twitter: https://twitter.com/your-user-name
		  微博: http://weibo.com/your-user-name
		  豆瓣: http://douban.com/people/your-user-name
		  知乎: http://www.zhihu.com/people/your-user-name
		  # 等等

	# 友情链接
	vim themes/next/_config.yml
		# title
		links_title: Links
		links:
		  MacTalk: http://macshuo.com/
		  Title: http://example.com/

	# 腾讯公益404页面
	腾讯公益404页面，寻找丢失儿童，让大家一起关注此项公益事业！效果如下 http://www.ixirong.com/404.html
	使用方法，新建 404.html 页面，放到主题的 source 目录下，内容如下：

		<!DOCTYPE HTML>
		<html>
		<head>
		  <meta http-equiv="content-type" content="text/html;charset=utf-8;"/>
		  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		  <meta name="robots" content="all" />
		  <meta name="robots" content="index,follow"/>
		  <link rel="stylesheet" type="text/css" href="https://qzone.qq.com/gy/404/style/404style.css">
		</head>
		<body>
		  <script type="text/plain" src="http://www.qq.com/404/search_children.js"
		          charset="utf-8" homePageUrl="/"
		          homePageName="回到我的主页">
		  </script>
		  <script src="https://qzone.qq.com/gy/404/data.js" charset="utf-8"></script>
		  <script src="https://qzone.qq.com/gy/404/page.js" charset="utf-8"></script>
		</body>
		</html>

	# 本地搜索
  	npm install hexo-generator-searchdb --save
  	vim _config.yml
  		search:
		  path: search.xml
		  field: post
		  format: html
		  limit: 10000
	vim themes/next/_config.yml
		# Local search
		local_search:
			enable: true


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