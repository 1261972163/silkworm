# -*- encoding: UTF-8 -*-
import pandas as pd
import numpy as np

print "Pandas是一个数据结构集。有两种数据结构Series和DataFrame。"

print "1、Series是带标签的序列，包括数据和行标签两部分。在不指定指定键的情况下，行标签从0自增。"
a=pd.Series([11,32,23,np.nan,9], index=[5,4,5,3,1])
print "pd.Series([11,32,23,np.nan,9], index=[5,4,5,3,1])=\n",a
print "通过行标签选择数据。"
print "a[5]=\n",a[5]
print "pd.Series([11,32,23,np.nan,9])=\n",pd.Series([11,32,23,np.nan,9])

print "================"
print "2、DataFrame是表格。表格包括数据、行标签和列标签"
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
print "列名称排序df.sort_index(axis=1,ascending=False)=\n",df.sort_index(axis=1,ascending=False)
print "行名称排序df.sort_index(axis=0,ascending=False)=\n",df.sort_index(axis=0,ascending=False)
print df.sort_index(axis=1,ascending=True)
print df.sort_index(axis=0,ascending=True)


df = pd.DataFrame({'A' : 1.,
                    'B' : pd.Timestamp('20130102'),
                    'C' : pd.Series(1,index=list(range(4)),dtype='float32'),
                    'D' : np.array([3] * 4,dtype='int32'),
                    'E' : pd.Categorical(["test","train","test","train"]),
                    'F' : 'foo'})
print "df=\n",df
print "列排序df.sort_values(by='E')=\n",df.sort_values(by='E')