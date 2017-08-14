#!/usr/bin/env python
#coding=utf-8

print
print '----------try...except...finally...----------'

def test(a, b):

	try:
		r=a/int(b)
	except ZeroDivisionError, e:
		return 'ZeroDivisionError', e
	except ValueError, e:
		return 'ValueError', e
	finally:
		print 'finally'
	print 'end'
print 'test(10, 0):', test(10,0)
print 'test(10, \'a\'):', test(10, 'a')

# 使用try...except捕获错误还有一个巨大的好处，就是可以跨越多层调用
print '跨越多层调用'
def foo(s):
    return 10 / int(s)

def bar(s):
    return foo(s) * 2

def main():
    try:
        bar('0')
    except StandardError, e:
        print 'Error!'
    finally:
        print 'finally...'

main()

# logging模块记录日志
print
print '----------logging----------'
# 默认情况，WARNING及以上日志级别将日志打印到屏幕上(stdout)
# 日志级别：DEBUG < INFO < WARNING < ERROR < CRITICAL
# 日志格式：日志级别:Logger实例名称:日志消息内容

import logging

# [配置](http://www.jianshu.com/p/feb86c06c4f4)
# logging.basicConfig(filename='logger.log', level=logging.INFO)

def foo(s):
    return 10 / int(s)

def bar(s):
    return foo(s) * 2

def main():
    try:
        bar('0')
    except StandardError, e:
    	print 'StandardError', e
        logging.error(e)
main()
print 'END'

# 抛出错误
print
print '----------raise抛出错误----------'
class FooError(StandardError):
    pass

def foo(s):
    n = int(s)
    if n==0:
        raise FooError('invalid value: %s' % s)
    return 10 / n
try:
	foo(0)
except FooError, e:
	print 'FooError', e

print
print '----------断言----------'
# 启动Python解释器时可以用-O参数来关闭assert
def foo(s):
    n = int(s)
    assert n != 0, 'n is zero!'    # assert的意思是，表达式n != 0应该是True，否则，抛出后面的错误。
    return 10 / n
try:
	foo('0')
except AssertionError, e:
	print 'AssertionError', e

print
print '----------pdb----------'
# 启动Python的调试器pdb，让程序以单步方式运行，可以随时查看运行状态。
# 使用方法：
#      python -m pdb x.py
# 以参数-m pdb启动
# 输入命令l来查看代码
# 输入命令n可以单步执行代码
# 任何时候都可以输入命令p 变量名来查看变量
# 输入命令q结束调试，退出程序
# pdb在命令行调试的方法理论上是万能的，但实在是太麻烦了

print
print '----------pdb.set_trace()----------'
# 断点时，输入c继续运行
import pdb
s = '0'
n = int(s)
pdb.set_trace() # 运行到这里会自动暂停
try:
	print 10 / n
except StandardError, e:
    print 'StandardError', e

print
print '----------使用unittest模块进行单元测试----------'

print
print '----------运行单元测试----------'
# 方法1：
# 最简单的运行方式是在mydict_test.py的最后加上两行代码：
# if __name__ == '__main__':
#    unittest.main()
# 这样就可以把mydict_test.py当做正常的python脚本运行。
# 
# 方法2（更常见）：
# 在命令行通过参数-m unittest直接运行单元测试。

print
print '----------setUp与tearDown----------'
# 这两个方法会分别在每调用一个测试方法的前后分别被执行。

print
print '----------文档测试----------'
# Python内置的“文档测试”（doctest）模块可以直接提取注释中的代码并执行测试。