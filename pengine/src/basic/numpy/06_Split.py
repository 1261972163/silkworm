# -*- encoding: UTF-8 -*-

import numpy as np

a=np.arange(1, 13).reshape(3,4)
print "a=",a

print "split只能等量分割"
print "np.split(a,2,axis=1)=",np.split(a,2,axis=1)
print "np.split(a,1,axis=0)=",np.split(a,3,axis=0)

print "array_split可以不等量分割"
print "np.array_split(a,2,axis=0)=",np.array_split(a,2,axis=0)

print "hsplit等同axis=1,vsplit等同axis=0"
print "np.hsplit(a,2)=",np.hsplit(a,2)
print "np.vsplit(a,2)=",np.vsplit(a,3)


