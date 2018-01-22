# -*- encoding: UTF-8 -*-

import numpy as np

a = np.arange(3, 15)
print "a=", a
print "a[0]=", a[0]
print "a[0:3]=", a[0:3]

b = a.reshape(3, 4)
print "b=", b
print "b[0][3]=", b[0][3]
print "b[0][0:3]=", b[0][0:3]

print "\nfor逐行打印"
for row in b:
    print row
print "for逐列打印"
for col in b.T:
    print col

print "\nflatten将多维的矩阵进行展开成1行的数列"
print "b.flatten()=", b.flatten()
print "flat是迭代器"
for ele in b.flat:
    print ele