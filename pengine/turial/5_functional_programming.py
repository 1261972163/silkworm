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

print
print '----------map-reduce----------'
# 内置map()
# 传入的函数依次作用到序列的每个元素，并把结果作为新的list返回
print 'map'
def f(x):
	return x*x
print map(f, range(1,10))
# map()和以下功能相同
print [x*x for x in range(1,10)]

# 内置reduce()
# 把一个函数作用在一个序列上，这个函数必须接收两个参数，reduce把结果继续和序列的下一个元素做累积计算
print 'reduce'
def fn(x, y):
	return x*10 + y
print reduce(fn, range(1,10))

# 运用map-reduce实现str转int
print 'str->int'
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
print 'lambda'
def str2int(s):
    def char2num(s2):
        return {'0': 0, '1': 1, '2': 2, '3': 3, '4': 4, '5': 5, '6': 6, '7': 7, '8': 8, '9': 9}[s2]
    return reduce(lambda x,y: x*10+y, map(char2num, s))
print str2int('13579')

print
print '----------filter----------'
# 把传入的函数依次作用于每个元素，然后根据返回值是True还是False决定保留还是丢弃该元素。
def is_odd(n):
    return n%2==0
print filter(is_odd, range(1,10))

print
print '----------sorted----------'
print sorted([4, 2, 5, 7, 9, 1, 50, 3])
def reversed_cmp(x, y):
    if (x>y):
        return 1
    elif (x<y):
        return -1
    else:
        return 0
print sorted([4, 2, 5, 7, 9, 1, 50, 3], reversed_cmp)

print sorted(['jerry', 'Jerry', 'Adam', 'Morty'])
def cmp_ignore_case(s1, s2):
    u1 = s1.upper()
    u2 = s2.upper()
    if u1 < u2:
        return -1
    if u1 > u2:
        return 1
    return 0
print sorted(['jerry', 'Jerry', 'Adam', 'Morty'], cmp_ignore_case)

print
print '----------返回函数----------'
# 高阶函数除了可以接受函数作为参数外，还可以把函数作为结果值返回。
def lazy_sum(*args):
    def sum():
        ax = 0
        for n in args:
            ax = ax + n
        return ax
    return sum
f=lazy_sum(1, 2, 3)
print f
print f()

print '闭包'
# 相关参数和变量都保存在返回的函数中，这种称为“闭包（Closure）”
# 注意：当一个函数返回了一个函数后，其内部的局部变量还被新函数引用，所以，闭包用起来简单，实现起来可不容易。
# 下面是错误示例：
def count():
    fs = []
    for i in range(1, 4):
        def f():
             return i*i
        fs.append(f)
    return fs
f1, f2, f3 = count()
print f1(),f2(),f3()

# 返回函数不要引用任何循环变量，或者后续会发生变化的变量。
def count():
    fs = []
    for i in range(1, 4):
        def f(j):
            def g():
                return j*j
            return g
        fs.append(f(i))
    return fs
f1, f2, f3 = count()
print f1(),f2(),f3()

print
print '----------匿名函数----------'
# 关键字lambda表示匿名函数
f = lambda x: x * x
print f
print f(2)

print
print '----------装饰器----------'
# 函数对象有一个__name__属性，可以拿到函数的名字
def f(x):
    return x*x
f2 = f
print f2.__name__

# 在函数调用前后自动打印日志，但又不希望修改函数的定义，这种在代码运行期间动态增加功能的方式，称之为“装饰器”（Decorator）。
def log(func):
    def wrapper(*args, **kw):
        print 'before call %s():' % func.__name__
        func(*args, **kw)
        print 'after call %s():' % func.__name__
        return func(*args, **kw)
    return wrapper

# 借助Python的@语法，把decorator置于函数的定义处
@log
def now():
    print '2013-12-25'
now()

# 如果decorator本身需要传入参数，那就需要编写一个返回decorator的高阶函数，写出来会更复杂
def log(text):
    def decorator(func):
        def wrapper(*args, **kw):
            print '%s %s():' % (text, func.__name__)
            return func(*args, **kw)
        return wrapper
    return decorator

# 传递参数    
@log('myexecute')
def now():
    print '2013-12-25'
now()

# functools.wraps
# 以上两种decorator的定义都没有问题，但还差最后一步。因为我们讲了函数也是对象，它有__name__等属性，但你去看经过decorator装饰之后的函数，它们的__name__已经从原来的'now'变成了'wrapper'：

import functools

def log(func):
    @functools.wraps(func)
    def wrapper(*args, **kw):
        print 'call %s():' % func.__name__
        return func(*args, **kw)
    return wrapper

print
print '----------偏函数----------'
print int('12')
print int('12', base=8)
print int('12', 16)

def int2(x, base=2):
    return int(x, base)
print int2('1000000')

# functools.partial创建偏函数
# 简单总结functools.partial的作用就是，把一个函数的某些参数给固定住（也就是设置默认值），返回一个新的函数
# 需要注意的是，只能从后往前固定，固定参数后面不能跟未固定的参数
import functools
int2 = functools.partial(int, base=2)
print int2('1000000')

def f(x, y, z):
    return x*y*z;
f2 = functools.partial(f, z=2)
print f2(3, 3)
f3 = functools.partial(f, x=3, y=4, z=2)
print f3()

def f2(x, y, z=2):
    return f(x, y, z)
print f2(3, 3)