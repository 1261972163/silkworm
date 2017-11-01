# -*- encoding: UTF-8 -*-
import numpy as np

print "= 的赋值方式会带有关联性"

a=np.arange(1,5)
b=a
print a
print b
a[0]=2
print a
print b