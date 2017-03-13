#!/usr/bin/env python
#coding=utf-8

print
print '----------高阶函数----------'
# 函数本身也可以赋值给变量，即：变量可以指向函数。
print abs
f=abs
print f

# 函数名也是变量
abs='123'
print abs
abs=f

# 高阶函数：一个函数就可以接收另一个函数作为参数
def add(x, y, f):
    return f(x) + f(y)
print add(-5, 6, abs)

# 内置map()
def f(x):
	return x*x
print map(f, range(1,10))
# map()和以下功能相同
print [x*x for x in range(1,10)]

# 内置reduce()
def fn(x, y):
	return x*10 + y
print reduce(fn, range(1,10))

# 运用map-reduce实现str转int
def fn(x, y):
	return x * 10 + y
def char2num(s):
	return {'0': 0, '1': 1, '2': 2, '3': 3, '4': 4, '5': 5, '6': 6, '7': 7, '8': 8, '9': 9}[s]
print reduce(fn, map(char2num, '13579'))

def str2int(s):
    def fn(x, y):
        return x * 10 + y
    def char2num(s):
        return {'0': 0, '1': 1, '2': 2, '3': 3, '4': 4, '5': 5, '6': 6, '7': 7, '8': 8, '9': 9}[s]
    return reduce(fn, map(char2num, s))
print str2int('13579')

# 运用lambda简化上述代码
def char2num(s):
    return {'0': 0, '1': 1, '2': 2, '3': 3, '4': 4, '5': 5, '6': 6, '7': 7, '8': 8, '9': 9}[s]
def str2int(s):
    return reduce(lambda x,y: x*10+y, map(char2num, s))
print str2int('13579')