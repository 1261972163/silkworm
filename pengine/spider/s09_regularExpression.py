#!/usr/bin/env python
#coding=utf-8

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

print "-----re.search(pattern, string[, flags])"

searchres1 = re.search(pattern,'hello world!')
searchres2 = re.search(pattern,'Hi, hello world!')
searchres3 = re.search(pattern,'Hi, helo world!')

printres(searchres1, 1)
printres(searchres2, 2)
printres(searchres3, 3)

'''
re.split(pattern, string[, maxsplit])
'''

print "-----re.split(pattern, string[, maxsplit])"

pattern2 = re.compile(r'\d+')
print re.split(pattern2,'one1two2three3four4')

### 输出 ###
# ['one', 'two', 'three', 'four', '']