#!/usr/bin/env python
# -*- coding: UTF-8 -*-

# 拉取百度首页源码
import urllib2

# urllib2构建Request，传入一个 URL，这个网址是百度首页，协议是 HTTP 协议，当然你也可以把 HTTP 换做 FTP,FILE,HTTPS 等等
request = urllib2.Request("http://www.baidu.com")
# 发送请求，执行 urlopen 方法之后，返回一个 response 对象，返回信息便保存在这里面。
response = urllib2.urlopen(request)
print response.read()