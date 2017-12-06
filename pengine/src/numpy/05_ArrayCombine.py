# -*- encoding: UTF-8 -*-

import numpy as np

a=np.arange(1, 5)
b=np.arange(6, 10)
print "a=",a
print "b=",b

c=np.vstack((a,b))
print "vstack=", c
print "a.shape=",a.shape
print "b.shape=",b.shape
print "c.shape=",c.shape

d=np.hstack((a,b))
print "hstack((a,b))=", d
print "d.shape=",d.shape

print "array转矩阵："
print d[np.newaxis,:]
print d[:,np.newaxis]

print "concatenate合并多个矩阵"
c=a[np.newaxis,:]
d=b[np.newaxis,:]
print "c=",c
print "d=",d
print "concatenate((c,d),axis=0)=",np.concatenate((c,d),axis=0)
print "concatenate((c,d),axis=1)=",np.concatenate((c,d),axis=1)
print "concatenate((c,d,d,c),axis=0)=",np.concatenate((c,d,d,c),axis=0)


