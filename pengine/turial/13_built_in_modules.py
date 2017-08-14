#!/usr/bin/env python
#coding=utf-8


print
print '-----collections-----'

print
print '-----namedtuple'

from collections import namedtuple

Point = namedtuple('Point', ['x', 'y'])
p = Point(1, 2)
print p.x

print 
print '-----deque'

from collections import deque

q = deque(['a', 'b', 'c'])
q.append('x')
q.appendleft('y')
print q

print
print '-----defaultdict'

from collections import defaultdict

dd = defaultdict(lambda: 'N/A')
dd['key1'] = 'abc'
print dd['key1']
print dd['key2']

print
print '-----OrderedDict'

from collections import OrderedDict

d = dict([('a', 1), ('b', 2), ('c', 3)])
print d
od = OrderedDict([('a', 1), ('b', 2), ('c', 3)])
print od
# OrderedDict的Key会按照插入的顺序排列，不是Key本身排序
od = OrderedDict([('a', 1), ('c', 2), ('b', 3)])
print od

print
print '-----Counter'

from collections import Counter

c = Counter()
for ch in 'programming':
	c[ch] = c[ch] + 1
print c

print
print '-----base64-----'

import base64
# 如果要编码的二进制数据不是3的倍数，最后会剩下1个或2个字节怎么办？
# Base64用\x00字节在末尾补足后，再在编码的末尾加上1个或2个=号，表示补了多少字节，解码的时候，会自动去掉。
print base64.b64encode('binary\\x00string')
print base64.b64decode('YmluYXJ5XHgwMHN0cmluZw==')

print
print '-----struct-----'
# 在网络通信当中，大多传递的数据是以二进制流（binary data）存在的。
# 需要有一种机制将某些特定的结构体类型打包成二进制流的字符串然后再网络传输，
# 而接收端也应该可以通过某种机制进行解包还原出原始的结构体数据。
# struct模块定义的数据类型可以参考Python官方文档：
# https://docs.python.org/2/library/struct.html#format-characters
import struct
import binascii

x = 10240099
print bin(x)
print hex(x)
x2=struct.pack('>I', x)
print x2
x3=struct.unpack('>I',x2)
print x3

values = (1, 'abc', 2.7)
s = struct.Struct('I3sf')
packed_data = s.pack(*values)
unpacked_data = s.unpack(packed_data)

print 'Original values:', values
print 'Format string :', s.format
print 'Uses :', s.size, 'bytes'
print 'Packed Value :', binascii.hexlify(packed_data)
print 'Unpacked Type :', type(unpacked_data), ' Value:', unpacked_data

print
print '-----hashlib-----'
# Python的hashlib提供了常见的摘要算法，如MD5，SHA1等等。

print '-----md5'
import hashlib

md5 = hashlib.md5()
md5.update('how to use md5 in python hashlib?')
print md5.hexdigest()

print '-----sha1'
import hashlib

sha1 = hashlib.sha1()
sha1.update('how to use sha1 in ')
sha1.update('python hashlib?')
print sha1.hexdigest()

print '-----itertools'
print '-----count'
# 无限循环器
import itertools,time

natuals = itertools.count(1)
for n in natuals:
	if (n==5):
		break
	print n

print '-----cycle'
x = 1
cs = itertools.cycle('ABC')
for c in cs:
	if (x==10):
		break
	print c
	x = x + 1

print '-----repeat'
ns = itertools.repeat('A', 10)
for n in ns:
	print n

print '-----takewhile'
natuals = itertools.count(1)
ns = itertools.takewhile(lambda x: x <= 10, natuals)
for n in ns:
	print n

print '-----chain'
for c in itertools.chain('ABC', 'XYZ'):
    print c

print '-----groupby'
for key, group in itertools.groupby('AaaBBbcCAAa'):
	print key, list(group)

print '-----'
for key, group in itertools.groupby('AaaBBbcCAAa', lambda c: c.upper()):
	print key, list(group)

for n in itertools.imap(lambda x, y: x * y, [10, 20, 30], itertools.count(1)):
	print n

r = map(lambda x: x*x, [1, 2, 3])
print r










