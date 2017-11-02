# -*- encoding: UTF-8 -*-
import pandas as pd
import numpy as np

a=pd.Series([11,32,23,np.nan,9])
print a

dates = pd.date_range("20171101",periods=3)
print "dates=", dates

print "表格"
print "指定表格的index和column"
df = pd.DataFrame(np.random.rand(3,2),index=dates,columns=["a","b"])
print "df=\n",df
print "df['b']=\n",df['b']

print "默认采用从0开始index."
df = pd.DataFrame(np.random.rand(3,2))
print "df=\n",df
print "查看每一列的数据属性df.dtypes=\n",df.dtypes
print "df.index=",df.index
print "df.columns=",df.columns
print "df.values=",df.values
print "总结df.describe=\n",df.describe()
print "翻转df.transpose=\n",df.transpose()

print df
print "df.sort_index(axis=1,ascending=False)=\n",df.sort_index(axis=1,ascending=False)
print "df.sort_index(axis=0,ascending=False)=\n",df.sort_index(axis=0,ascending=False)
print df.sort_index(axis=1,ascending=True)
print df.sort_index(axis=0,ascending=True)

df = pd.DataFrame({'A' : 1.,
                    'B' : pd.Timestamp('20130102'),
                    'C' : pd.Series(1,index=list(range(4)),dtype='float32'),
                    'D' : np.array([3] * 4,dtype='int32'),
                    'E' : pd.Categorical(["test","train","test","train"]),
                    'F' : 'foo'})
print df
print (df.sort_values(by='E'))