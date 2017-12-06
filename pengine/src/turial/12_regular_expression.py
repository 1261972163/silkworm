#!/usr/bin/env python
#coding=utf-8

# Python提供re模块，包含所有正则表达式的功能
import re

print 
print '-----re'
# match()方法判断是否匹配，如果匹配成功，返回一个Match对象，否则返回None。
print re.match(r'^\d{3}\-\d{3,8}$', '010-12345')
# 判断
test = '010-12345'
if re.match(r'^\d{3}\-\d{3,8}$', test):
    print 'ok'
else:
    print 'failed'

print
print '-----切分字符串'
# string的split无法识别连续的空格
print 'a b   c'.split(' ')
# 正则切分
print re.split(r'\s+', 'a b   c')
print re.split(r'[\s\,\;]+', 'a,b, c  d , e;f')

print
print '-----分组'
m = re.match(r'^(\d{3})-(\d{3,8})$', '010-12345')
print m.groups()

print
print '贪婪匹配'
# 正则匹配默认是贪婪匹配，也就是匹配尽可能多的字符
# 由于\d+采用贪婪匹配，直接把后面的0全部匹配了，结果0*只能匹配空字符串了。
print re.match(r'^(\d+)(0*)$', '102300').groups()
# 必须让\d+采用非贪婪匹配（也就是尽可能少匹配），才能把后面的0匹配出来，加个?就可以让\d+采用非贪婪匹配
print re.match(r'^(\d+?)(0*)$', '102300').groups()







