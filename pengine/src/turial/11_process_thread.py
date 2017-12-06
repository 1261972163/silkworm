#!/usr/bin/env python
#coding=utf-8

print
print '-----多进程-----'

print
print '-----fork()'
'''
# fork()调用一次，返回两次，因为操作系统自动把当前进程（称为父进程）复制了一份（称为子进程），然后，分别在父进程和子进程内返回。
# 子进程永远返回0，而父进程返回子进程的ID。
# 只适用于Unix/Linux系统
import os

if ('posix' == os.name):
	print 'Process (%s) start...' % os.getpid()
	pid = os.fork()
	if pid==0:
	    print 'I am child process (%s) and my parent is %s.' % (os.getpid(), os.getppid())
	else:
	    print 'I (%s) just created a child process (%s).' % (os.getpid(), pid)
'''
print
print '-----multiprocessing'
# multiprocessing模块就是跨平台版本的多进程模块。
from multiprocessing import Process
import os
# 子进程要执行的代码
def run_proc(name):
    print 'Run child process %s (%s)...' % (name, os.getpid())

print 'Parent process %s.' % os.getpid()
p = Process(target=run_proc, args=('test',))
print 'Process will start.'
p.start()
p.join()
print 'Process end.'

# my test
print 'my test'
def mytest(x):
	print 'x*x=', x*x

p1 = Process(target=mytest, args=(2,) )
p1.start()
p1.join()
print 'mytest end'

def mytest2(x):
	def mytest3(y):
		return y*y
	z = mytest3(x)
	return z+1
print mytest2(2)

def mytest3():
	def mytest4():
		print 'mytest4'

	p2 = Process(target=mytest4, args=())
	p2.start()
	p2.join()
	print 'mytest3'
mytest3()

print
print '-----进程池'
# 对Pool对象调用join()方法会等待所有子进程执行完毕，调用join()之前必须先调用close()，调用close()之后就不能继续添加新的Process了。
from multiprocessing import Pool
import os, time, random

def long_time_task(name):
    print 'Run task %s (%s)...' % (name, os.getpid())
    start = time.time()
    time.sleep(random.random() * 3)
    end = time.time()
    print 'Task %s runs %0.2f seconds.' % (name, (end - start))

print 'Parent process %s.' % os.getpid()
p = Pool()
for i in range(5):
    p.apply_async(long_time_task, args=(i,))
print 'Waiting for all subprocesses done...'
p.close()
p.join()
print 'All subprocesses done.'

# mypooltest
print 'mypooltest'
p = Pool()
p.apply_async(long_time_task, args=('xxxx',))
p.close()
p.join()

print
print '-----进程间通信'
# Python的multiprocessing模块包装了底层的机制，提供了Queue、Pipes等多种方式来交换数据。
from multiprocessing import Process, Queue
import os, time, random

def write(q):
	for value in ['A', 'B', 'C']:
		print 'print %s in queue' % value
		q.put(value)
		time.sleep(random.random())

def read(q):
    while True:
        value = q.get(True)
        print 'Get %s from queue.' % value

# 父进程创建Queue，并传给各个子进程：
q = Queue()
pw = Process(target=write, args=(q,))
pr = Process(target=read, args=(q,))
# 启动子进程pw，写入:
pw.start()
# 启动子进程pr，读取:
pr.start()
# 等待pw结束:
pw.join()
# pr进程里是死循环，无法等待其结束，只能强行终止:
pr.terminate()

print
print '-----多线程-----'
# Python的标准库提供了两个模块：thread和threading，
# thread是低级模块，threading是高级模块，对thread进行了封装。
# 绝大多数情况下，我们只需要使用threading这个高级模块。
import threading

def test1():
	print 'current_thread.name=', threading.current_thread().name
t = threading.Thread(target=test1, name='name1')
t.start()
t.join()

balance = 0

def change_it(n):
	# print 'start-', threading.current_thread().name
	global balance
	balance = balance + n
	balance = balance - n
	# print 'end-', threading.current_thread().name
def run_task(n):
	for i in range(100000):
		change_it(n)
t1 = threading.Thread(target=run_task, args=(5,))
t2 = threading.Thread(target=run_task, args=(8,))
t1.start()
t2.start()
t1.join()
t2.join()
# time.sleep(30)
print 'balance=', balance


print
print '-----加锁'
balance = 0
lock = threading.Lock()

def run_task2(n):
    for i in range(10):
        # 先要获取锁:
        lock.acquire()
        try:
            change_it(n)
        finally:
            # 改完了一定要释放锁:
            lock.release()
t1 = threading.Thread(target=run_task2, args=(5,))
t2 = threading.Thread(target=run_task2, args=(8,))
t1.start()
t2.start()
t1.join()
t2.join()
print 'balance=', balance

print
print '-----ThreadLocal'
import threading

# 创建全局ThreadLocal对象:
local_school = threading.local()

def process_student():
    print 'Hello, %s (in %s)' % (local_school.student, threading.current_thread().name)

def process_thread(name):
    # 绑定ThreadLocal的student:
    local_school.student = name
    process_student()

t1 = threading.Thread(target= process_thread, args=('Alice',), name='Thread-A')
t2 = threading.Thread(target= process_thread, args=('Bob',), name='Thread-B')
t1.start()
t2.start()
t1.join()
t2.join()
print 'ThreaLocal test end'

print 
print '-----进程 vs. 线程'
# 多进程模式最大的优点就是稳定性高，因为一个子进程崩溃了，不会影响主进程和其他子进程。缺点是创建进程的代价大.
# 多线程模式通常比多进程快一点，但是也快不到哪去，而且，多线程模式致命的缺点就是任何一个线程挂掉都可能直接造成整个进程崩溃，因为所有线程共享进程的内存。

print 
print '-----线程切换'
# 无论是多进程还是多线程，只要数量一多，效率肯定上不去。
# 多任务一旦多到一个限度，就会消耗掉系统所有的资源，结果效率急剧下降，所有任务都做不好。

print
print '-----计算密集型 vs. IO密集型'
# 计算密集型任务由于主要消耗CPU资源，因此，代码运行效率至关重要。
# Python这样的脚本语言运行效率很低，完全不适合计算密集型任务。对于计算密集型任务，最好用C语言编写。
#
# IO密集型任务执行期间，99%的时间都花在IO上，花在CPU上的时间很少。
# 因此，用运行速度极快的C语言替换用Python这样运行速度极低的脚本语言，完全无法提升运行效率。
# 对于IO密集型任务，最合适的语言就是开发效率最高（代码量最少）的语言，脚本语言是首选，C语言最差。

print
print '-----分布式进程-----'
# 在Thread和Process中，应当优选Process，因为Process更稳定，而且，Process可以分布到多台机器上，而Thread最多只能分布到同一台机器的多个CPU上。
# Python的multiprocessing模块不但支持多进程，其中managers子模块还支持把多进程分布到多台机器上。一个服务进程可以作为调度者，将任务分布到其他多个进程中，依靠网络通信。
# 由于managers模块封装很好，不必了解网络通信的细节，就可以很容易地编写分布式多进程程序。
print '用法：'
print '见 distributed_process_server.py 和 distributed_process_client.py'


