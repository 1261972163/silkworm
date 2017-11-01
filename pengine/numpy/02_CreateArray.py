# —*- encoding: UTF-8 -*-

import numpy as np

print("1. 创建。创建 array 有很多形式")
a = np.array([1, 2, 3])
print(a)
# [1 2 3]

print("2. 指定数据 dtype")
a = np.array([2,23,4],dtype=np.int)
print(a.dtype)
a = np.array([2,23,4],dtype=np.int32)
print(a.dtype)

print("3. 创建特定数据")
a = np.array([[2,23,4],[2,32,4]])  # 2d 矩阵 2行3列
print(a)
print "创建全零数组:"
a = np.zeros((3,4)) # 数据全为0，3行4列
print(a)
print("创建全一数组:")
a = np.ones((3,4),dtype = np.int)   # 数据为1，3行4列
print(a)
print "创建全空数组, 其实每个值都是接近于零的数:"
a = np.empty((3,4)) # 数据为empty，3行4列
print(a)
print "用 arange 创建连续数组:"
a = np.arange(1, 10, 3)
print(a)
print "使用 reshape 改变数据的形状:"
a = np.arange(12).reshape((3,4))
print a
print "用 linspace 创建线段型数据:"
a = np.linspace(1, 10, 4)
print a
print "随机数组（0-1）："
a = np.random.rand(10).astype(np.float32)
print a



