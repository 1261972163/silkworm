#!/usr/bin/env python
#coding=utf-8

print '----------number----------'

num = -1
if num >= 0:
    print num
else:
    print -num

num2 = 0xff00
print num2

num3 = 1.2e-5
print num3

print '----------string----------'
str1='I\'m \"OK\"!'
print str1

print '\\n','\n'
print 'a\\t','\t','a'
print 'a',r'\n','a'

print '''
line1
line2
line3
'''

print '<r>'
print r'''
line1 \n
line2
line3
'''
print '</r>'

print 'len(\'abc\')=',len('abc')

print '----------bool----------'
print True
print True and False
print True or False
print not True

print '----------None----------'
print None

print '----------variable----------'
a = 1
print a
a = 'a'
print a
a = True
print a

print '----------constant----------'
PI = 3.1415927
PI = 1
print PI

print '----------arithmetic operation----------'
print 10/3
print 10.0/3

print '----------coding----------'
print 'ASCII: ord(\'A\')=',ord('A')
print 'ASCII: chr(65)=',chr(65)
print '中文'





