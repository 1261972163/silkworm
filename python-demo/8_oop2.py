#!/usr/bin/env python
#coding=utf-8

print
print '----------__slots__-----------'
# __slots__限制class的属性
class Student(object):
	pass
s = Student()
s2 = Student()
s.name = 'Jerry'
print s.name

print
print '----------MethodType实例绑定方法-----------'
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

print
print '----------多重继承-----------'

class Animal(object):
    pass

class Runnable(object):
    def run(self):
        print('Running...')

class Flyable(object):
    def fly(self):
        print('Flying...')

class Dog(Animal, Runnable):
    pass

class Bat(Animal, Flyable):
    pass

dog = Dog()
bat = Bat()
dog.run()
bat.fly()

print
print '----------定制类-----------'
# 形如__xxx__的变量或者函数名就要注意，这些在Python中是有特殊用途的
# 这样有特殊用途的函数，可以帮助我们定制类。
print dir(dog)
print dog
print dir('A')
# __str__()返回用户看到的字符串
# __repr__()返回程序开发者看到的字符串

print
print '----------使用元类-----------'
# 之前我们使用type检查类型
# type()函数既可以返回一个对象的类型
print type(1)
# 又可以创建出新的类型
# 通过type()函数创建的类和直接写class是完全一样的。
# 因为Python解释器遇到class定义时，仅仅是扫描一下class定义的语法，然后调用type()函数创建出class。
def fn(self, name="World"):
	print('Hello, %s!' % name) 
Hello = type('Hello', (object,), dict(say=fn))
hello = Hello()
hello.say()

print
print '----------元类-----------' 
# metaclass，直译为元类，简单的解释就是：类的创建类
# metaclass允许你创建类或者修改类
# 换句话说，你可以把类看成是metaclass创建出来的“实例”。
# 用法：
# 	先定义metaclass，就可以创建一个类，最后创建实例。
class ListMetaclass(type):                                      # metaclass是创建类，所以必须从`type`类型派生：
    def __new__(cls, name, bases, attrs):                       # 参数：当前准备创建的类的对象、类的名字、类继承的父类集合、类的方法集合
        attrs['add'] = lambda self, value: self.append(value)
        return type.__new__(cls, name, bases, attrs)

class MyList(list):
    __metaclass__ = ListMetaclass # 指示使用ListMetaclass来定制类

mylist = MyList()
mylist.add(1)
mylist.add(2)
print mylist

l = list()
try:
	l.add(1)
except AttributeError, e:
	print 'AttributeError:', e
print l



