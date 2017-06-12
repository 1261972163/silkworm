---
title: Gitpages使用笔记
categories: 工程管理
tags: 
	- 工具
	- 个人博客
	- gitpages
date: 2012/3/13 20:03:00
---


# git命令

	1. 本地配置

	查看git配置
	git config --list

	配置适用范围
	1) git config 针对一个git仓库
	2) git config --global    针对一个用户
	3) sudo git config --system    针对一个系统，因为是针对整个系统的，所以必须使用sudo

	修改提交用户
	git config --global user.email "youremail@google.com"
	git config --global user.name "your name"

	2. 缓存区

	回退add
	git reset HEAD <file>

	3. 工作区

	4. 仓库

# 自定义git库

	./gitblit.sh
	http://localhost:18080/

# 构建gitpages

	mkdir username.github.io
	cd username.github.io
	git init
	git checkout --orphan gh-pages

	touch _config.yml
	mkdir _layouts
	cd _layouts
	touch default.html
	cd ..
	mkdir _posts
	touch index.html
	vim _config.yml

		markdown: rdiscount
		baseurl:/helloworld

	vim _default.html

		<!DOCTYPE html>
		<html>
		    <head>
		        <meta charset="utf-8" />
		        <title>{% if page.title %}{{ page.title }} | {% endif %}{{ site.title }}</title>
		    </head>

		    <body>
		        {{ content }}
		    </body>
		</html>

	vim index.html

		---
		layout: default
		title: Hello World
		---
		<h2>{{ page.title }}</h2>
		<p>Content</p>
		<ul>
		    {% for post in site.posts %}
		    <li>
		        {{ post.date | date_to_string }} 
		        <a href="{{ site.baseurl }}{{ post.url }}">{{ post.title }}</a>
		    </li>
		　　{% endfor %}
		</ul>

	# write your pages

	cd _posts
	touch 2014-11-05-hello-world.md
	vim 2014-11-05-hello-world.md
	
		---
		layout: default
		title: hello world
		---
		#hello world

	# 当前目录结构为：

		/helloworld
		    |--　_config.yml
		    |--　_layouts
		        |--　default.html 
		    |--　_posts
		        |--　2014-11-05-hello-world.html
		    |--　index.html

	# 发布

	git add .
	git commit -m "init"
	git remote add origin https://github.com/username/username.gitbub.io.git
	git push origin gh-pages
	
	# 访问

	http://username.github.io

	Jekyll创始人的[示例库](https://github.com/mojombo/tpw)及其搭建的[blog](https://github.com/jekyll/jekyll/wiki/Sites)。