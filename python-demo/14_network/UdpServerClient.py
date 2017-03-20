#!/usr/bin/env python
#coding=utf-8

# 网络通信就是两个进程之间的通信
# 通常用一个Socket表示“打开了一个网络链接”
import socket,time
import threading
from multiprocessing import Process

class Server(object):
	def __init__(self, port):
		self.port = port
		socket.setdefaulttimeout(20) 
		self.serverSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

	def start(self):
		self.serverSocket.bind(('127.0.0.1', 9999))
		print 'Bind UDP on 9999...'
		print 'Server waiting for receive...'
		try:
			while True:
				data, addr = self.serverSocket.recvfrom(1024)
				if ('exit'==data):
					print 'Server', data
					self.serverSocket.sendto('ok!', addr)
					break
				print 'Server received from %s:%s.' % addr
				self.serverSocket.sendto('Hello, %s!' % data, addr)
		except socket.timeout:
			pass
		except (KeyboardInterrupt,SystemExit):
			raise
		print 'Server start end.'

	def stop(self):
		print 'Server stoping'
		self.serverSocket.close()
		print 'Server stoped'

class Client(object):
	def __init__(self, serverHost, port):
		self.serverHost = serverHost
		self.port = port
		self.clientSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
		print 'Client start'

	def send(self, data):
		self.clientSocket.sendto(data, (self.serverHost, self.port))

	def recieve(self):
		return self.clientSocket.recv(1024)

	def stop(self):
		self.clientSocket.close()


server = Server(9999)
pserver = Process(target=server.start)
pserver.start()

time.sleep(3)

client = Client('127.0.0.1', 9999)
for data in ['Michael', 'Tracy', 'Sarah']:
	print 'Client is prepareing to send', data
	client.send(data)
	print client.recieve()

client.send('exit')
print client.recieve()
client.stop()

time.sleep(3)
server.stop()











