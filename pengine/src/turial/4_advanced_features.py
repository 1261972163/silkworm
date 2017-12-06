#!/usr/bin/env python
#coding=utf-8

print
print '----------切片----------'
scores = [1, 3, 5, 7, 9]
print scores[0], scores[1], scores[2]
print scores[0:3]
print scores[:3]
print scores[0:]
print scores[-3:-1]
print scores[-3:]
print scores[:5:2] # 前5个数，每2个取一个

scores = (1, 3, 5, 7, 9)
print scores[0:3]

print
print '----------迭代----------'
# 只要作用于一个可迭代对象，for循环就可以正常运行，而我们不太关心该对象究竟是list还是其他数据类型。
nums=range(10)
sum = 0
for i in nums:
    sum += nums[i]
print sum

for ch in 'ABC':
	print ch

print
d = {'a': 1, 'b': 2, 'c': 3}
for key in d:
	print key,'->',d[key]

# 下标循环
# Python内置的enumerate函数可以把一个list变成"索引-元素"对，索引是顺序序号，元素值为输入的值
print 'enumerate(nums)', enumerate(nums)
for i, value in enumerate(nums):
	print i, value
	
for i, value in enumerate('ABC'):
	print i, value

print
print '----------列表生成式----------'
print range(2,10)

print [x * x for x in range(1, 11)]
print [x * x for x in range(1, 11) if x % 2 == 0]
print [m + n for m in 'ABC' for n in 'XYZ']

d = {'x': 'A', 'y': 'B', 'z': 'C' }
print [k + '=' + v for k, v in d.iteritems()]

L = ['Hello', 'World', 'IBM', 'Apple']
print [s.lower() for s in L]

L = ['Hello', 'World', 'IBM', 18, 'Apple']
print [s.lower() for s in L if isinstance(s, str)]

print
print '----------生成器----------'
# 方法一：把一个列表生成式的[]改成()，就创建了一个generator
g = [x * x for x in range(10)]
print g
g = (x * x for x in range(10))
print g
# generator保存的是算法，每次调用next()，就计算出下一个元素的值，直到计算到最后一个元素，没有更多的元素时，抛出StopIteration的错误。
print g.next()
print g.next()
for n in g:
	print n

# 方法二：使用yield
# yield实现的generator函数，在每次调用next()的时候执行，遇到yield语句返回，再次执行时从上次返回的yield语句处继续执行。
def odd():
	print 'step 1'
	yield 1
	print 'step 2'
	yield 3
	print 'step 3'
	yield 5

for n in odd():
	print n

