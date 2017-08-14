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
		# 测试的时候使用socket.setdefaulttimeout()，为了退出accept()
		# This will affect all the socket operation
		socket.setdefaulttimeout(20) 
		self.serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		self.serverSocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1) 

	def start(self):
		self.serverSocket.bind(('127.0.0.1', 9999))
		# self.serverSocket.settimeout(20) # 测试的时候使用，为了退出accept()
		self.serverSocket.listen(5)
		
		print 'Server waiting for connection...'
		try:
			while True:
				print 'Server circle'
				sock, addr = self.serverSocket.accept()
				# 创建新线程来处理TCP连接:
				t = threading.Thread(target=self.tcplink, args=(sock, addr))
				t.setDaemon(True)
				t.start()
		except socket.timeout:
			pass
		except (KeyboardInterrupt,SystemExit):
			raise
		print 'Server start end.'

	def tcplink(self, sock, addr):
		print 'Server accept new connection from %s:%s...' % addr
		try:
			while True:
				data = sock.recv(1024)
				time.sleep(1)
				if data == 'exit' or not data:
					break
				sock.send('Server: Hello, %s!' % data)
		finally:
			sock.send('Connection from %s:%s closed.' % addr)
			sock.close()
			print 'Server Connection from %s:%s closed.' % addr

	def stop(self):
		print 'Server stoping'
		self.serverSocket.close()
		print 'Server stoped'

class Client(object):
	def __init__(self, serverHost, port):
		self.serverHost = serverHost
		self.port = port
		self.clientSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		self.clientSocket.connect((serverHost, port))
		print 'Client start'

	def send(self, data):
		self.clientSocket.send(data)

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