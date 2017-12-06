# -*- encoding: UTF-8 -*-
import pandas as pd
import numpy as np

df=pd.DataFrame(np.arange(24).reshape(6,4),index=pd.date_range('20171103', periods=6),columns=['A','B','C','D'])
print "df=",df

print "选择某一列df['A']=\n",df['A']
print "选择某一列df.A=\n",df.A
print "选择多行df[1:3]=\n",df[1:3]
print "选择多行df['2017-11-06':'2017-11-08']=\n",df['2017-11-06':'2017-11-08']
print "选择多列df[[0,1]]=\n",df[[0,1]]
print "选择多列df[['A','D']]=\n",df[['A','D']]

