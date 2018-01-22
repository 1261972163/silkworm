#!/usr/bin/env python
#coding=utf-8
# 在2.7版本的代码中，可以通过unicode_literals来使用Python 3.x的新的语法
from __future__ import unicode_literals

def test():
	print isinstance('xxx', unicode)
	print isinstance(b'xxx', unicode)
	print isinstance(u'xxx', unicode)
	print isinstance('xxx', str)
	print isinstance(b'xxx', str)
	print isinstance(u'xxx', str)