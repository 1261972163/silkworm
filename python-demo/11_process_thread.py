#!/usr/bin/env python
#coding=utf-8

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




