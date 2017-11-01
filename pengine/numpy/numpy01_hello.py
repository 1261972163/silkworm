# —*- encoding: UTF-8 -*-
import numpy as np

# numpy是一个操作多维数组对象的包
# 使用numpy需要引入包，import numpy as np

'''
数组对象 ndarray
'''

# numpy中的数组对象是ndarray，ndarray中的每个元素在内存中使用相同大小的块。
# 生成ndarry的方法：
# numpy.array(object, dtype = None, copy = True, order = None, subok = False, ndmin = 0)
# （1）object 任何暴露数组接口方法的对象都会返回一个数组或任何（嵌套）序列。
a = np.array([1,2,3])
print a
a = np.array([[1,  2],  [3,  4]])
print a
# （2）dtype  数组元素数据类型，可选。
a = np.array([1,  2,  3], dtype = complex)
print a
# （3）copy   对象是否被复制，可选，默认为true。
# （4）order  C（按行）、F（按列）或A（任意，默认）。
# （5）subok  默认情况下，返回的数组被强制为基类数组。 如果为true，则返回子类。
# （6）ndimin 指定返回数组的最小维数。
a = np.array([1,  2,  3, 4, 5], ndmin = 2)
print a

# 数值类型是dtype（数据类型）对象的实例。NumPy 支持比 Python 更多种类的数值类型。
a = np.array([1,  2, 0], dtype = np.bool_)
print a

'''
数据类型 dtype
'''

# dtype可由以下语法构造：
# numpy.dtype(object, align, copy)
# （1）Object 被转换为数据类型的对象。
# （2）Align  如果为true，则向字段添加间隔，使其类似 C 的结构体。
# （3）Copy   生成dtype对象的新副本，如果为flase，结果是内建数据类型对象的引用。
import numpy as np
dt = np.dtype('>i4')
print dt

dt = np.dtype([('age',np.int8)])
a = np.array([(10,),(20,),(30,)], dtype = dt)
print a

dt = np.dtype(('age',np.int8))
print dt

# 定义名为 student 的结构化数据类型
student = np.dtype([('name','S20'),  ('age',  'i1'),  ('marks',  'f4')])
print student
