# —*- encoding: UTF-8 -*-

import numpy as np #为了方便使用numpy 采用np简写

array = np.array([[1,2,3],[2,3,4]])  #列表转化为矩阵
print(array)

print("属性：\n")

print('number of dim:',array.ndim)  # 维度
# number of dim: 2

print('shape :',array.shape)    # 行数和列数
# shape : (2, 3)

print('size:',array.size)   # 元素个数
# size: 6