---
title: Ruby实践(1)——安装
categories: ruby
tags: 
  - ruby
date: 2016/6/3 17:37:25
---

# win7下安装Ruby

## 1 安装ruby

	#1.1 下载并安装 
	下载地址：http://rubyinstaller.org/downloads/  
	安装路径：D:\opt\ruby\Ruby193

	#1.2 配置ruby环境变量
	path下加ruby执行文件安装目录，如D:\opt\ruby\Ruby193\bin

	#1.3 检查是否正确安装
	控制台下输入		
		ruby -v
	输出示例：
		ruby 1.9.3p550 (2014-10-27) [i386-mingw32]

## 2 安装DevKit

	#2.1 下载并安装
	下载地址：http://rubyinstaller.org/downloads/
	解压路径：D:\opt\ruby\DevKit

	#2.2 创建配置config.yml
	命令行窗口输入：
		cd D:\opt\ruby\DevKit
		ruby dk.rb init
	打开config.yml，末尾添加
		- D:/opt/ruby/Ruby193

	#2.3 检查是否正确安装
	控制台下输入		
		ruby dk.rb review
		ruby dk.rb install
