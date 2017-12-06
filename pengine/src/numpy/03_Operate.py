# -*- encoding: UTF-8 -*-

import numpy as np

print "1. 元素运算"
a=np.array([10,20,30,40])   # array([10, 20, 30, 40])
b=np.arange(4)              # array([0, 1, 2, 3])
print "a=",a
print "b=",b

print "a-b=", a-b

print "a*b=", a*b

print "a^2=", a**2

print "sin(a)=",np.sin(a)

print "a<31=", (a<31)

print "\n"
print "2. 矩阵运算"
c=np.array([[1,1], [1,0]])
d=np.arange(4).reshape(2,2)
print "c=", c
print "d=", d

print "c.dot(d)=", c.dot(d)

print "\n"
print "3. 统计"
print "sum(d)=", np.sum(d)
print "min(d)=", np.min(d)
print "max(d)=", np.max(d)

print("axis=1，行统计；axis=0，列统计。")
print "sum(d,axis=1)=", np.sum(d,axis=1)
print "min(d,axis=0)=", np.min(d,axis=0)
print "max(d,axis=1)=", np.max(d,axis=1)

print "mean(c)=",np.mean(c)
print "average(c)=",np.average(c)
print "median(c)=",np.median(c)

print "累加函数cumsum(c)=",np.cumsum(c)
print "累差函数diff(c)=",np.diff(c)

print "sort(c)=", np.sort(c)
print "转置transpose(d)=", np.transpose(d)
print "clip中最小值最大值则用于让函数判断矩阵中元素是否有比最小值小的或者比最大值大的元素，并将这些指定的元素转换为最小值或者最大值"
print "clip(d, 1, 2)=", np.clip(d, 1, 2)

print "nonzero显示非0元素的坐标，行和列坐标分开显示"
print "nonzero(d)=", np.nonzero(d)
