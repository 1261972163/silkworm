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

print '----------format----------'
print 'Hello, %s. This is %d -> %05d. Price %f -> %.2f, OX=%x' % ('admin', 2017, 2017, 10.5, 10.5, 100)

print '----------list----------'
classmates = ['Michael', 'Bob', 'Tracy']
print classmates,'len=',len(classmates)
print classmates[0],classmates[1],classmates[2],classmates[-1],classmates[-2],classmates[-3]
classmates.append('Adam')
print classmates,'len=',len(classmates)
classmates.insert(1, 'Jack')
print classmates,'len=',len(classmates)
classmates.pop() # delete from tail
print classmates,'len=',len(classmates)
classmates.pop(1)
print classmates,'len=',len(classmates)
classmates[1] = 'Sarah'
print classmates,'len=',len(classmates)
# list里面的元素的数据类型也可以不同
mix = ['Apple', 123, True]
print mix,'len=',len(mix)
mix2 = ['python', 'java', ['asp', 'php'], 'scheme']
print mix2,'len=',len(mix2)

# tuple一旦初始化就不能修改
classmates = ('Michael', 'Bob', 'Tracy')
print classmates,'len=',len(classmates)
classmates = ()
print classmates,'len=',len(classmates)
classmates = (1) # 只有一个元素时，不表示tuple，而是该元素本身的含义
print classmates
classmates = (1,) # 只有一个元素的tuple
print classmates,'len=',len(classmates)
# 一个“可变的”tuple
t = ('a', 'b', ['A', 'B'])
t[2][0] = 'X'
t[2][1] = 'Y'
print t,'len=',len(t)

print
print '----------if-else & for & while----------'
# if-else
age = 3
if age >= 18:
    print 'adult'
elif age >= 6:
    print 'teenager'
else:
    print 'kid'

# for
names = ['Michael', 'Bob', 'Tracy']
print names
for name in names:
    print name

sum = 0
for x in [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]:
    sum = sum + x
print sum

print range(10)
print range(1,11)

sum = 0
for x in range(10):
    sum = sum + x
print sum

# while
sum = 0
n = 10
while n > 0:
    sum = sum + n
    n = n - 1
print sum

print
print '----------raw_input----------'

# raw_input获取的内容是字符串
# res = []
# num = raw_input("number:")
# res.append(num)
# res.append(int(num))
# print res

print
print '----------dict----------'
scores = {'Michael': 95, 'Bob': 75, 'Tracy': 85}
print scores
print scores['Michael']
scores['Jack'] = 100
print scores
scores['Jack'] = 88
print scores
# 判断key是否存在
res = 'Thomas' in scores
print res
print scores.get('Thomas')
print scores.get('Thomas', -1) # 不存在则返回指定值

scores.pop('Jack')
print scores

print
print '----------set----------'
nums = set([1, 2, 3])
print nums
nums = set([1, 1, 2, 2, 3, 3]) # 自动过滤重复元素
print nums
nums.add(4)
print nums
nums.remove(4)
print nums
# 集合
s1 = set([1, 2, 3])
s2 = set([2, 3, 4])
print s1 & s2 

#str是不变对象，而list是可变对象。
a = ['c', 'b', 'a']
a.sort();
print a

a='abc'
b=a.replace('a', 'A');
print a
print b