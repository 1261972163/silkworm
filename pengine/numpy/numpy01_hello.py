# —*- encoding: UTF-8 -*-
import numpy as np

a = 'hello world'
print a

# 一维数组
a = np.array([1,2,3])
print a


# 二维矩阵
a = np.array([[1,  2],  [3,  4]])
print a

# ndimin 指定返回数组的最小维数。
a = np.array([1,  2,  3, 4, 5], ndmin =  2)
print a

# dtype 数组的所需数据类型，可选。
a = np.array([1,  2,  3], dtype = complex)
print a

# NumPy 支持比 Python 更多种类的数值类型。
# NumPy 数字类型是dtype（数据类型）对象的实例。

# 自定义dtype
import numpy as np
dt = np.dtype('>i4')
print dt

dt = np.dtype([('age',np.int8)])
a = np.array([(10,),(20,),(30,)], dtype = dt)
print a

# 定义名为 student 的结构化数据类型
student = np.dtype([('name','S20'),  ('age',  'i1'),  ('marks',  'f4')])
print student
