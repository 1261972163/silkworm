#!/usr/bin/env python
#coding=utf-8

print
print '----------模块----------'
# 在Python中，一个.py文件就称之为一个模块（Module）。
# 注意，尽量不要与内置函数名字冲突。[Python的所有内置函数](https://docs.python.org/2/library/functions.html)。

print
print '----------模块搜索路径----------'
# 当我们试图加载一个模块时，Python会在指定的路径下搜索对应的.py文件，如果找不到，就会报错
# 默认情况下，Python解释器会搜索当前目录、所有已安装的内置模块和第三方模块，搜索路径存放在sys模块的path变量
import sys
print 'sys.path', sys.path

# 添加自己的搜索目录
# 方法一：运行时修改，运行结束后失效。
import sys
sys.path.append('/opt/nouuid/my_py_scripts')
# 方法二：设置环境变量PYTHONPATH，该环境变量的内容会被自动添加到模块搜索路径中。

print
print '----------引入自定义模块----------'
# 将自定义模块目录添加到搜索路径
current=sys.path[0]
print type(current)
current=current+'/6_module'
print current
sys.path.append(current)
print 'sys.path', sys.path
# 引入自定义模块
import hello
hello.test()

print
print '----------import ... as ..----------'
try:
	import cStringIO as StringIO
except ImportError:
	import StringIO

print
print '----------作用域----------'
# 在Python中，作用域是通过_前缀来实现的。
# 公开的：正常的函数和变量名是公开的（public），可以被直接引用
# 特殊的：类似__xxx__这样的变量是特殊变量，可以被直接引用，但是有特殊用途
# 私有的：类似_xxx和__xxx这样的函数或变量就是非公开的（private），不应该被直接引用

print
print '----------安装第三方模块----------'
# Python有两个封装了setuptools的包管理工具：easy_install和pip。目前官方推荐使用pip。
# 一般来说，第三方库都会在Python官方的pypi.python.org网站注册。
# 安装一个第三方库，必须先知道该库的名称，可以在官网或者pypi上搜索，
# 比如Python Imaging Library的名称叫PIL，因此，安装Python Imaging Library的命令就是：
# 	pip install PIL
# 个人安装的是Pillow
# 	pip install pillow
# 常用的第三方库：
# 	MySQL-python -> MySQL的驱动
# 	numpy        -> 科学计算
# 	Jinja2       -> 生成文本的模板工具
from PIL import Image
im = Image.open('./6_module/icon.ico') 
print im.format, im.size

print
print '----------—__future__----------'
# __future__模块，把下一个新版本的特性导入到当前版本
# 可以在当前版本中测试一些新版本的特性。
print isinstance('xxx', unicode)
print isinstance(b'xxx', unicode)
print isinstance(u'xxx', unicode)
print isinstance('xxx', str)
print isinstance(b'xxx', str)
print isinstance(u'xxx', str)

print '----------—unicode_literals3'
import unicode_literals3
unicode_literals3.test()

print '----------—division3'
import division3
division3.test()