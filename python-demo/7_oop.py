#!/usr/bin/env python
#coding=utf-8

print
print '----------类和实例-----------'                     
class Student(object):                              # 通过class关键字定义类，类名通常是大写开头的单词，紧接着是(object)，表示该类是从哪个类继承下来的 
    pass
bart = Student()                                    # 类实例                                     
print Student
print bart
bart.name = 'Bart Simpson'                          # 可以自由地给一个实例变量绑定属性
print bart.name

print
print '----------__init__-----------'
class Student(object):                                                

    def __init__(self, name, score):                # 初始化方法
        self.name = name                            # 把name，score等属性绑定到类实例
        self.score = score

    def print_score(self):                          # 定义一个方法，除了第一个参数是self外，其他和普通函数一样。要调用一个方法，只需要在实例变量上直接调用，除了self不用传递，其他参数正常传入
        print '%s: %s' % (self.name, self.score)

bart = Student('Bart Simpson', 59)                  # 有了__init__方法，在创建实例的时候，就不能传入空的参数了，必须传入与__init__方法匹配的参数，但self不需要传，Python解释器自己会把实例变量传进去
lisa = Student('Lisa Simpson', 87)
bart.print_score()
lisa.print_score()

print
print '----------访问限制-----------'
# 如果要让内部属性不被外部访问，可以把属性的名称前加上两个下划线__
class Student(object):

    def __init__(self, name, score):
        self.__name = name
        self.__score = score

    def print_score(self):
        print '%s: %s' % (self.__name, self.__score)

bart = Student('Bart Simpson', 98)
print bart
try:
	print bart.name
except AttributeError, e:
	print 'AttributeError:', e
try:
	print bart.__name
except AttributeError, e:
	print 'AttributeError:', e

# get/set
class Student(object):

    def __init__(self, name, score):
        self.__name = name
        self.__score = score

    def get_name(self):
        return self.__name

    def set_name(self, name):
        self.__name = name

    def get_score(self):
        return self.__score

    def set_score(self, score):
        self.__score = score

    def print_score(self):
        print '%s: %s' % (self.__name, self.__score)

bart = Student('Bart Simpson', 98)
print bart
try:
	print bart.get_name()
except AttributeError, e:
	print 'AttributeError:', e
bart.set_name('Adam')
print bart.get_name()

# 双下划线开头的实例变量是不是一定不能从外部访问呢？其实也不是。不能直接访问__name是因为Python解释器对外把__name变量改成了_Student__name，所以，仍然可以通过_Student__name来访问__name变量
# 强烈建议不要这样使用
print bart._Student__name

# 私有方法
class Student(object):
    def smile(self):
        print 'smile'
    def __help(self):
        print 'help'
    def help(self):
        return self.__help()
student = Student()
student.smile()
try:
    student.__help()
except AttributeError, e:
    print 'AttributeError:', e
student.help()

print
print '----------继承和多态-----------'
class Animal(object):
    def run(self):
        print 'Animal is running...'

class Dog(Animal):
	def run(self):
		print 'Dog is running...'
	def eat(self):
		print 'Eating meat...'

class Cat(Animal):
    def run(self):
        print 'Cat is running...'

dog = Dog()
dog.run()
dog.eat()

cat = Cat()
cat.run()

print isinstance(dog, Dog)
print isinstance(cat, Cat)
print isinstance(dog, Animal)
print isinstance(cat, Animal)
print isinstance(dog, Cat)
print isinstance(cat, Dog)

def run(animal):
	animal.run()
run(Dog())
run(Cat())

print
print '----------获取对象信息----------'
# type
print type(123)
print type(abs)
print type(run)
print type(dog)

# types
import types
print types
print type('abc')==types.StringType

# isinstance()
print isinstance(1, int)
print isinstance(1, types.IntType)

# dir
# 获得一个对象的所有属性和方法，
print dir('ABC')

# getattr()、setattr()以及hasattr()
print hasattr(dog, 'x')
class Dog(Animal):
	def __init__(self, x):
		self.x=x
	def run(self):
		print 'Dog is running...'
	def eat(self):
		print 'Eating meat...'
dog = Dog(1)
print hasattr(dog, 'x')
print getattr(dog, 'x')
setattr(dog, 'x', 2)
print getattr(dog, 'x')
setattr(dog, 'y', 3)
print getattr(dog, 'y')

print hasattr(dog, 'run')
print getattr(dog, 'run')
f = getattr(dog, 'run')
f()

def run2():
    print "22222"
print setattr(dog, 'run2', run2)
dog.run2()