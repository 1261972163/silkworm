#!/usr/bin/env python
# -*- coding: UTF-8 -*-

import re

'''
re.match(pattern, string[, flags])
'''

# 将正则表达式编译成Pattern对象，注意hello前面的r的意思是“原生字符串”
pattern = re.compile(r'hello')

# 使用re.match匹配文本，获得匹配结果，无法匹配时将返回None
matchres1 = re.match(pattern,'hello')
matchres2 = re.match(pattern,'helloo CQC!')
matchres3 = re.match(pattern,'helo CQC!')
matchres4 = re.match(pattern,'hello CQC!')

def printres(res, i):
    #如果1匹配成功
    if res:
        # 使用Match获得分组信息
        print '%s' % i , res.group()
    else:
        print '%s' % i , '匹配失败'

printres(matchres1, 1)
printres(matchres2, 2)
printres(matchres3, 3)
printres(matchres4, 4)

'''
re.search(pattern, string[, flags])
'''

print "----- re.search(pattern, string[, flags])"

searchres1 = re.search(pattern,'hello world!')
searchres2 = re.search(pattern,'Hi, hello world!')
searchres3 = re.search(pattern,'Hi, helo world!')

printres(searchres1, 1)
printres(searchres2, 2)
printres(searchres3, 3)

print "----- re.split(pattern, string[, maxsplit])  "
print "----- 按照能够匹配的子串将 string 分割后返回列表。maxsplit 用于指定最大分割次数，不指定将全部分割。"
pattern = re.compile(r'\d+')
print re.split(pattern,'one1two2three3four4')


print "----- re.findall(pattern, string[, flags]) "
print "----- 搜索 string，以列表形式返回全部能匹配的子串。"
pattern = re.compile(r'\d+')
print re.findall(pattern,'one1two2three3four4')

print "----- re.finditer(pattern, string[, flags])"
print "----- 搜索 string，返回一个顺序访问每一个匹配结果（Match对象）的迭代器。"
pattern = re.compile(r'\d+')
for m in re.finditer(pattern,'one1two2three3four4'):
    print m.group(),

print "----- re.sub(pattern, repl, string[, count])"
print "----- 使用 repl 替换 string 中每一个匹配的子串后返回替换后的字符串。"
pattern = re.compile(r'(\w+) (\w+)')
s = 'i say, hello world!'
print re.sub(pattern,r'\2 \1', s)

def func(m):
    return m.group(1).title() + ' ' + m.group(2).title()
print re.sub(pattern,func, s)


print "----- re.subn(pattern, repl, string[, count])"
print "----- 返回 (sub(repl, string[, count]), 替换次数)。"
pattern = re.compile(r'(\w+) (\w+)')
s = 'i say, hello world!'
print re.subn(pattern,r'\2 \1', s)
def func(m):
    return m.group(1).title() + ' ' + m.group(2).title()
print re.subn(pattern,func, s)








