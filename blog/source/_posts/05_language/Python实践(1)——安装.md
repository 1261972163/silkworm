---
title: Python实践(1)——安装
categories: 编程语言
tags: 
  - python
date: 2016/6/3 17:37:25
---

# linux 安装 python2.7

	网上查了最简单的安装步骤：

	1. 下载python2.7.5，保存到 /data/qtongmon/software
	http://www.Python.org/ftp/python/

	2. 解压文件
	tar xvf Python-2.7.5.tar.bz2

	3. 创建安装目录
	mkdir /usr/local/python27

	4. 安装python
	./configure --prefix=/usr/local/python27
	make
	make install

	5. 修改老版本的ln指向（注意：这里修改后，可能会影响yum的使用）
	mv /usr/bin/python /usr/bin/python2.4.3
	ln -s /usr/local/python27/bin/python /usr/bin/python

	安装成功！

	继续安装其他模块的时候，悲剧发生了：
	... relocation R_X86_64_32 against `a local symbol' can not be used when making a shared object; recompile with -fPIC

	... could not read symbols: Bad value

	解决办法编译器已经提示了：recompile with -fPIC

	该怎么带这个参数编译呢，原来是要重新编译python。
	./configure --prefix=/usr/local/  –enable-shared CFLAGS=-fPIC  
	make  
	make install

	重新编译成功了！

	但是新的问题又出现了：
	libpython2.7.so.1.0: cannot open shared object file: No such file or directory  发火
	解决办法：
	 1. cp /usr/local/python27/lib/libpython2.7.so.1.0 /usr/local/lib
	     cd /usr/local/lib
	     ln -s libpython2.7.so.1.0 libpython2.7.so

	  2. 使用命令whereis libpython2.7.so.1.0得到如下结果就说明
	    libpython2.7.so.1: /usr/local/lib/libpython2.7.so.1.0


# mac下安装python

	## 1 官网下载Python3.3

	这里面有windows和mac os x下的安装程序，下载那个64位的安装程序

	## 2 安装下载的img文件，安装完后的目录如下：

		/Library/Frameworks/Python.framework/Versions/3.3

	## 3 移动python的安装目录

	原来的安装目录见第2步，不过所有的python都在

		/System/Library/Frameworks/Python.framework/Versions

	目录中，所以最好使用下面的命令移动一下，当然不移动也可以。但后面步骤中的某些路径需要修改下。

		sudo mv /Library/Frameworks/Python.framework/Versions/3.3 /System/Library/Frameworks/Python.framework/Versions

	## 4 改变Python安装目录的用户组为wheel

		sudo chown -R root:wheel /System/Library/Frameworks/Python.framework/Versions/3.3

	python2.7的用户组就是wheel，3.3也照葫芦画瓢吧！

	## 5 修改Python当前安装目录的符号链接

	在 /System/Library/Frameworks/Python.framework/Versions/目录下有一个Current，这是一个目 录符号链接，指向当前的Python版本。原来指向2.7的，现在指向3.3。所以应先删除Current。然后重新建立Current符号链接，命令如下：

		sudo rm /System/Library/Frameworks/Python.framework/Versions/Current
		sudo ln -s /System/Library/Frameworks/Python.framework/Versions/3.3 /System/Library/Frameworks/Python.framework/Versions/Current

	## 6 删除旧的命令符号链接

	在/usr/bin目录下有4个python命令的符号链接，使用下面的命令先删除

		sudo rm /usr/bin/pydoc
		sudo rm /usr/bin/python
		sudo rm /usr/bin/pythonw
		sudo rm /usr/bin/python-config

	## 7 重新建立新的命令符号链接

	将第6步删除的符号链接重新使用下面命令建立，它们都指向Python3.3了。

		sudo ln -s /System/Library/Frameworks/Python.framework/Versions/3.3/bin/pydoc3.3 /usr/bin/pydoc
		sudo ln -s /System/Library/Frameworks/Python.framework/Versions/3.3/bin/python3.3 /usr/bin/python
		sudo ln -s /System/Library/Frameworks/Python.framework/Versions/3.3/bin/pythonw3.3 /usr/bin/pythonw
		sudo ln -s /System/Library/Frameworks/Python.framework/Versions/3.3/bin/python3.3m-config /usr/bin/python-config

	## 8 更新/root/.bash_profile文件中的路径

		cd ~
		vim .bash_profile 

	在.bash_profile插入下面的内容即可

		# Setting PATH for Python 3.3
		# The orginal version is saved in .bash_profile.pysave
		PATH="/System/Library/Frameworks/Python.framework/Versions/3.3/bin:${PATH}"
		export PATH

	ok，现在重新启动一下Console，然后执行python --version，得到的就是Python 3.3.3。如果在程序中，需要使用下面代码获取python版本

		import platform
		print(platform.python_version())

# win7下安装python

	1。 python下载并安装
	下载地址：https://www.python.org/downloads/windows/

	2. 配置python环境变量
	path下加python安装目录，如D:\opt\Python34

	3。 检查是否正确安装
	控制台下输入python，测试是否安装成功。

	4. setuptools 下载并安装
	下载地址：http://pypi.python.org/pypi/setuptools  
	安装方法：win7 64位必须使用ez_setup.py进行安装。在cmd下执行 python ez_setup.py，即可自动安装setuptools。目前没有直接的exe安装版本。

	5. easy_install下载并安装
	（1）安装  
	win32位：在D:\opt\Python34\Scripts下双击easy_install.exe  
	win64位：easy_install virtualenv  
	（2）检查是否正确安装

		easy_install --version

	6. 配置easy_install环境变量
	path下加python\Scripts安装目录，如D:\opt\Python34\Scripts


# 模块安装

	模块安装：pip install module_name
	模块卸载：pip uninstall module_name