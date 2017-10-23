#!/usr/bin/env python
# -*- coding: UTF-8 -*-
import re
import urllib2

__author__ = 'nouuid'

class QSBK:
    def __init__(self, minPageIndex, maxPageIndex):
        self.minPageIndex = minPageIndex
        self.maxPageIndex = maxPageIndex
        self.user_agent = 'Mozilla/4.0 (compatible; MSIE 5.5; Windows NT)'
        # 初始化headers
        self.headers = {'User-Agent': self.user_agent}

    # 传入某一页的索引获得页面代码
    def getPage(self, pageIndex):
        try:
            url = 'http://www.qiushibaike.com/hot/page/' + str(pageIndex)
            request = urllib2.Request(url, headers=self.headers)
            response = urllib2.urlopen(request)
            pageCode = response.read().decode('utf-8')
            return pageCode
        except urllib2.URLError, e:
            if hasattr(e,"reason"):
                print u"连接糗事百科失败,错误原因",e.reason
                return None

    # 传入某一页代码，返回本页不带图片的段子列表
    def getPageItems(self, pageIndex):
        pageCode = self.getPage(pageIndex)
        if not pageCode:
            print "页面加载失败...."
            return None
        pattern = re.compile('<div.*?class="author.*?>.*?<a.*?href="/users/(.*?)/".*?target.*?' +
                             '<img.*?alt="(.*?)">.*?</a>.*?' +
                             '<a.*?<h2>.*?</a>.*?<div.*?</div>.*?<a.*?' +
                             '<div.*?class="content".*?<span.*?</span>.*?</div>.*?' +
                             '<div.*?class="stats">.*?' +
                             '<span.*?class="stats-vote">.*?<i.*?class="number">(.*?)</i>.*?</span>.*?'
                             , re.S)
        items = re.findall(pattern,pageCode)
        # pageStories = []
        for item in items:
            print '%-10s %-20s %-10s' % (item[0], item[1], item[2])
            # pageStories.append([item[0],item[1],item[2]])
        # return pageStories

    # 加载并提取页面的内容，加入到列表中
    def loadPage(self, pageIndex):
        self.getPageItems(pageIndex)

    # 调用该方法，每次敲回车打印输出一个段子
    def getOneStory(self, pageStories, page):
        # 遍历一页的段子
        for story in pageStories:
            # 等待用户输入
            input = raw_input()
            # 每当输入回车一次，判断一下是否要加载新页面
            self.loadPage()
            # 如果输入Q则程序结束
            if input == "Q":
                self.enable = False
                return
            print u"第%d页\tid:%s\t发布人:%s\t赞:%s\n" % (page, story[0], story[1], story[2])

    # 开始方法
    def start(self):
        print('{:<10}{:<20}{:<10}'.format('userid', 'username', 'likes'))
        nowPage = self.minPageIndex
        while nowPage<=self.maxPageIndex:
            print "------------------第%d页-----------------------" % nowPage
            self.loadPage(nowPage)
            nowPage +=1

print u"输入最小和最大页码数（空格分隔）："
min,max = map(int, raw_input().split())
spider = QSBK(min, max)
spider.start()