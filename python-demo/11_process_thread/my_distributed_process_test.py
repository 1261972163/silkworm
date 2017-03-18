#!/usr/bin/env python
#coding=utf-8
from multiprocessing import Process
from multiprocessing.managers import BaseManager
import time, Queue

class ServerQueueManager(BaseManager):
	pass

class ClientQueueManager(BaseManager):
	pass

class Server(object):
	def __init__(self, port):
		self.port = port
	
	def start(self):
		print 'server start'
		queue = Queue.Queue()
		ServerQueueManager.register('get_task_queue', callable=lambda: queue)
		m = ServerQueueManager(address=('', self.port), authkey='abc')
		m.start()
		task_queue = m.get_task_queue()
		print 'server put 1'
		task_queue.put(1)
		print 'server waiting...'
		time.sleep(5)
		m.shutdown()
		print 'server end'
    	

class Client(object):

	def __init__(self, serverHost, port):
		self.serverHost = serverHost
		self.port = port

	def start(self):
		print 'client start'
		ClientQueueManager.register('get_task_queue')
		print '-', type(self.serverHost)
		print '-', type(self.port)
		m = ClientQueueManager(address=(self.serverHost, self.port), authkey='abc')
		m.connect()
		task_queue = m.get_task_queue()
		data = task_queue.get(timeout=1)
		print 'client get', data
		print 'client end'

server = Server(5000)
client = Client('127.0.0.1', 5000)

p1 = Process(target=server.start)
p2 = Process(target=client.start)
p1.start()
time.sleep(3)
p2.start()
p1.join()
p2.join()

print 'end'