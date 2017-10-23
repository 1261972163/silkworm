#!/usr/bin/env python
# -*- coding: UTF-8 -*-

import pandas as pd
import numpy as np
import matplotlib.pyplot as plt



print("start...")

print("-----------------------------------------------------------------------")
# 用dict建立Dataframe
# DataFrame by dict
df2 = pd.DataFrame({'A': 1.,
                    'B': pd.Timestamp('20130102'),
                    'C': pd.Series(1, index=list(range(4)), dtype='float32'),
                    'D': np.array([3]*4, dtype='int32'),
                    'E': pd.Categorical(["test", "train", "test", "train"]),
                    'F': 'foo'})
print(df2)

print("-----------------------------------------------------------------------")
# 生成随机数的矩阵 np.random.randn(6,4)
dates = [pd.Timestamp('20170801'), pd.Timestamp('20170802')]
df = pd.DataFrame(np.random.randn(2,4), index = dates, columns = list('ABCD'))
print(df)

print("-----------------------------------------------------------------------")
# 对某一列降序排列
df.sort_values(by=['B'], ascending=False)
print(df)

print("-----------------------------------------------------------------------")
print("end.")