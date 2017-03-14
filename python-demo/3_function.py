#!/usr/bin/env python
#coding=utf-8

print
print '----------abs----------'
print abs(-1)

print
print '----------cmp----------'
print cmp(0, 1)
print cmp(2, 1)

print
print '----------数据类型转换----------'
print int('1')
print float('1.5')
print float('1')
print str('1.5')
print unicode(100)
print bool(1)
print bool('')

print
print '----------定义函数----------'

def my_abs(x):
    if x >= 0:
        return x
    else:
        return -x

print my_abs(-1)

print
print '----------空函数----------'
def nop():
    pass

print
print '----------参数类型检查----------'
def my_abs(x):
    if not isinstance(x, (int, float)):
        raise TypeError('bad operand type')
    if x >= 0:
        return x
    else:
        return -x

print my_abs(-1)

print
print '----------返回多个值----------'

import math

def move(x, y, step, angle=0):
    nx = x + step * math.cos(angle)
    ny = y + step * math.sin(angle)
    return nx, ny

x, y = move(0, 0, 60, math.pi/6)
print x, y
res = move(0, 0, 60, math.pi/6)
print res

print
print '----------默认参数----------'
# 必选参数在前，默认参数在后
# 当函数有多个参数时，把变化大的参数放前面，变化小的参数放后面。变化小的参数就可以作为默认参数。
def dance(x, y, step=10):
	x = x + step;
	y = y + step;
	return x, y
print dance(1, 1)
print dance(1, 1, 10)

# 有多个默认参数时
def enroll(name, gender, age=18, city='Hangzhou'):
	return name, gender, age, city
# 调用的时候，既可以按顺序提供默认参数
print enroll('admin', 'F')
print enroll('admin', 'F', 16)
# 也可以不按顺序提供部分默认参数。当不按顺序提供部分默认参数时，需要把参数名写上。
print enroll('admin', 'F', city='Beijing')

# 默认参数必须指向不变对象
def add_end(L=[]):
    L.append('END')
    return L

print add_end([1, 2, 3])
print add_end(['x'])

# Python函数在定义的时候，默认参数L的值就被计算出来了，即[]。
# 因为默认参数L也是一个变量，它指向对象[]。
# 每次调用该函数，如果改变了L的内容，则下次调用时，默认参数的内容就变了，不再是函数定义时的[]了。
print add_end()
print add_end()

# 使用None
def add_end(L=None):
    if L is None:
        L = []
    L.append('END')
    return L
print add_end()
print add_end()

print
print '----------可变参数----------'
# 用list或tuple传参数
def calc(numbers):
    sum = 0
    for n in numbers:
        sum = sum + n * n
    return sum
print calc([1, 2, 3])
print calc((1, 3, 5, 7))

# 可变参数
# 可变参数允许你传入0个或任意个参数，这些可变参数在函数调用时自动组装为一个tuple。
def calc(*numbers):
    sum = 0
    for n in numbers:
        sum = sum + n * n
    return sum
print calc(1, 2, 3)
print calc(*[1, 2, 3])
print calc(*(1, 2, 3))

# 关键字参数
# 关键字参数允许你传入0个或任意个含参数名的参数，这些关键字参数在函数内部自动组装为一个dict。
def person(name, age, **kw):
    return 'name:', name, 'age:', age, 'other:', kw
print person('admin', 1)
print person('admin', 1, city='Beijing')
print person('admin', 1, city='Beijing', job='Engineer')
kw = {'city': 'Beijing', 'job': 'Engineer'}
print person('admin', 1, **kw)

print
print '----------参数组合----------'
# 参数组合
# 必选参数、默认参数、可变参数和关键字参数，这4种参数都可以一起使用，或者只用其中某些，但是请注意，参数定义的顺序必须是：必选参数、默认参数、可变参数和关键字参数。
def func(a, b, c=0, *args, **kw):
    return a, b, c, args, kw
print func(1, 2, 3, 4, 5, **{'key1':'value1', 'key2':'value2'})

def every(*args, **kw):
	return args,kw
print every(1, 2)
print every(**{'key1':'value1', 'key2':'value2'})

print
print '----------递归函数----------'
# 使用递归函数需要注意防止栈溢出。
def fact(n):
    if n==1:
        return 1
    return n * fact(n - 1)
print fact(5)

# 解决递归调用栈溢出的方法是通过尾递归优化，事实上尾递归和循环的效果是一样的，所以，把循环看成是一种特殊的尾递归函数也是可以的。
def fact(n):
    return fact_iter(n, 1)

def fact_iter(num, product):
    if num == 1:
        return product
    return fact_iter(num - 1, num * product)
print fact(5)
