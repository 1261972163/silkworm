#!/usr/bin/env python
#coding=utf-8

print
print '----------__slots__-----------'
class Student(object):
	pass
s = Student()
s2 = Student()
s.name = 'Jerry'
print s.name

print
print '----------实例绑定方法-----------'
def set_age(self, age):
	self.age = age

from types import MethodType
s.set_age = MethodType(set_age, s, Student) # 给实例绑定一个方法
s.set_age(1)
print s.age

# 但是，给一个实例绑定的方法，对另一个实例是不起作用的
try:
	print s2.age
except AttributeError, e:
	print 'AttributeError', e

print
print '----------class绑定方法-----------'
# 为了给所有实例都绑定方法，可以给class绑定方法
Student.set_age = MethodType(set_age, None, Student) 
s.set_age(1)
s2.set_age(2)
print s.age
print s2.age

print
print '----------__slots__限制class的属性-----------'
# 为了达到限制class的属性的目的，Python允许在定义class的时候，定义一个特殊的__slots__变量，来限制该class能添加的属性
class Student(object):
	__slots__ = ('name', 'age') # 用tuple定义允许绑定的属性名称
s = Student()
s.name = 'Jerry'
s.age = 27
try:
	s.score = 90
except AttributeError, e:
	print 'AttributeError', e

# 但不限制方法
def set_age(self, age):
	self.age = age
Student.set_age = MethodType(set_age, None, Student)
s.set_age(1)
print s.age

print
print '----------@property-----------'
# Python内置的@property装饰器就是负责把一个方法变成属性调用的
class Student(object):

    @property
    def score(self):                                        # 把一个getter方法变成属性，只需要加上@property就可以了
        return self._score

    @score.setter
    def score(self, value):                                 # @score.setter负责把一个setter方法变成属性赋值
        if not isinstance(value, int):
            raise ValueError('score must be an integer!')
        if value < 0 or value > 100:
            raise ValueError('score must between 0 ~ 100!')
        self._score = value

s = Student()
s.score = 60
try:
	s.score = 110
except ValueError, e:
	print 'ValueError', e