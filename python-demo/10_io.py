#!/usr/bin/env python
#coding=utf-8

print
print '----------io编程----------'

print '----------文件读写----------'

try:
	f = open('./1_hello.py', 'r')
	str = f.read()
	print str
except IOError, e:
	print 'IOError', e
finally:
	f.close()

print
print '-----with自动调close()'
# Python引入了with语句来自动帮我们调用close()方法
with open('./1_hello.py', 'r') as f:
	print f.read()

print '-----读取二进制文件'
# 要读取二进制文件，比如图片、视频等等，用'rb'模式打开文件
with open('./6_module/icon.ico', 'rb') as f:
	print f.read()

print '-----codecs'
# Python还提供了一个codecs模块帮我们在读文件时自动转换编码，直接读出unicode
import codecs
with codecs.open('./1_hello.py', 'r', 'gbk') as f:
    print f.read()

print
print '----------写文件'
# 写文件时，操作系统往往不会立刻把数据写入磁盘，而是放到内存缓存起来，空闲的时候再慢慢写入。
# 只有调用close()方法时，操作系统才保证把没有写入的数据全部写入磁盘。
# 忘记调用close()的后果是数据可能只写了一部分到磁盘，剩下的丢失了。
with open('./10_io/io.md', 'w') as f:
    f.write('Hello, world!')

print
print '----------操作文件和目录----------'
# Python内置的os模块也可以直接调用操作系统提供的接口函数。
import os

# 如果是posix，说明系统是Linux、Unix或Mac OS X，如果是nt，就是Windows系统。
print os.name
# 环境变量
print os.environ
print
# 获取PATH环境变量的值
print os.getenv('PATH')

print
# 查看当前目录的绝对路径:
print os.path.abspath('.')
print
# 合并路径
newpath = os.path.join('./', 'testdir') 
# 创建
os.mkdir(newpath)
# 删除
os.rmdir(newpath)
# 路径拆分，把一个路径拆分为两部分，后一部分总是最后级别的目录或文件名
print os.path.split(newpath)
# 得到文件扩展名
print os.path.splitext('./1_hello.py')

# 创建文件
fp = open('test.md', 'w')
# 重命名 windows不支持
# os.rename('test.md', 'test.py')
# 删除文件 windows不支持
# os.remove('test.md')

print '列出当前目录下的所有目录'
print [x for x in os.listdir('.') if os.path.isdir(x)]

print '列出所有的.py文件'
print [x for x in os.listdir('.') if os.path.isfile(x) and os.path.splitext(x)[1]=='.py']