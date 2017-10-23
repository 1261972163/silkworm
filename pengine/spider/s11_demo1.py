#!/usr/bin/env python
# -*- coding: UTF-8 -*-

import re
import urllib
import urllib2
import sys


'''
爬取糗事百科段子，目标：
1、抓取糗事百科热门段子
2、过滤带有图片的段子
3、实现每按一次回车显示一个段子的发布时间，发布人，段子内容，点赞数。
'''


reload(sys)
sys.setdefaultencoding('utf-8')

page = 1
url = 'http://www.qiushibaike.com/hot/page/' + str(page)
user_agent = 'Mozilla/4.0 (compatible; MSIE 5.5; Windows NT)'
headers = { 'User-Agent' : user_agent }
try:
    request = urllib2.Request(url, headers=headers)
    response = urllib2.urlopen(request)
    # print response.read()
except urllib2.URLError, e:
    if hasattr(e,"code"):
        print e.code
    if hasattr(e,"reason"):
        print e.reason

# 解析网页
#print '%-10s %-20s %-10s' % ('userid','username','likes')
print('{:<10}{:<20}{:<10}'.format('userid','username','likes'))
content = response.read().decode('utf-8')
pattern = re.compile('<div.*?class="author.*?>.*?<a.*?href="/users/(.*?)/".*?target.*?'+
                     '<img.*?alt="(.*?)">.*?</a>.*?'+
                     '<a.*?<h2>.*?</a>.*?<div.*?</div>.*?<a.*?'+
                     '<div.*?class="content".*?<span.*?</span>.*?</div>.*?'+
                     '<div.*?class="stats">.*?'+
                     '<span.*?class="stats-vote">.*?<i.*?class="number">(.*?)</i>.*?</span>.*?'
                     ,re.S)
items = re.findall(pattern,content)
for item in items:
    #print '%-10s %-20s %-10s' % (item[0],item[1],item[2])
    print('{:<10}{:<20}{:<10}'.format(item[0],item[1],item[2]))