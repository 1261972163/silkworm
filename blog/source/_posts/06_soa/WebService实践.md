---
title: Web Service实践
categories: 服务化
tags: 
	- Web Service
date: 2015/6/3 17:37:25
---

# 1. Web Service定义

引用W3C定义：

> [Definition: A Web service is a software system designed to support interoperable machine-to-machine interaction over a network. It has an interface described in a machine-processable format (specifically WSDL). Other systems interact with the Web service in a manner prescribed by its description using SOAP messages, typically conveyed using HTTP with an XML serialization in conjunction with other Web-related standards.]

这段话表达了3个意思：

1. web服务是用来支持机器对机器之间通过网络进行互操作的软件系统
2. web服务的接口描述是有机器能够读懂的格式（WSDL：web service description language）
3. 在其他系统跟web服务交互时，这些系统要遵循web服务的接口描述来组织SOAP消息。通常情况下使用HTTP来传递XML格式的内容与其他的web相关标准相关联。

# 2. 运行过程

Web Service提供的服务是基于web容器的，底层使用http协议，类似一个远程的服务提供者，比如天气预报服务，对各地客户端提供天气预报，是一种请求应答的机制，是跨系统跨平台的。就是通过一个servlet，提供服务出去。

运行过程：

（1）客户端从服务器得到WebService的WSDL，同时在客户端声称一个代理类(Proxy Class) 。这个代理类负责与WebService服务器进行Request 和Response。
（2）当一个数据（XML格式的）被封装成SOAP格式的数据流发送到服务器端的时候，就会生成一个进程对象并且把接收到这个Request的SOAP包进行解析；
（3）然后对事物进行处理，处理结束以后再对这个计算结果进行SOAP包装，然后把这个包作为一个Response发送给客户端的代理类(Proxy Class)；
（4）代理类也对这个SOAP包进行解析处理，继而进行后续操作。

# 3. 逻辑分层

![](/resources/webservice/logical.png)
 

# 4. 模型

![](/resources/webservice/model.png)


服务提供者，也就是我们构建的web服务，可以对外提供某种服务，它首先要通过发布（可选）到服务代理，然后被服务请求者查找到，服务请求者从服务代理得到服务提供者的信息之后，和服务提供者绑定，从服务提供者那里得到需要的服务。

# 5. 概念性协议栈

![](/resources/webservice/protocal.png)

* 发现服务层：UDDI是一种目录服务，企业可以使用它对Webservices进行注册和搜索。
* 描述服务层：为客户端提供服务端的服务描述，例如web服务能做什么，位置在哪，如何调用等。
* 消息格式层：保证客户端与服务端格式设置上保持一致，一般通过SOAP来实现。SOAP定义了C与S之间消息传输规范。SOAP用XML来格式化消息，HTTP来承载。SOAP包括3个部分，SOAP信封（定义了描述信息和如何处理消息的框架的封装）、表达应用程序定义的数据类型实例的编码规则（SOAP编码规则）以及RPC协议
* 编码格式层：为C和S之间提供标准的，独立于平台的数据交换编码格式，一般为XML格式。
* 传输协议层：为C与S之间提供交互的网络通信协议。