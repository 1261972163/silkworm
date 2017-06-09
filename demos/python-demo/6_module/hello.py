#!/usr/bin/env python
#coding=utf-8

' a test module '                        # 一个字符串，表示模块的文档注释，任何模块代码的第一个字符串都被视为模块的文档注释。

__author__ = 'nouuid'                    # 使用__author__变量把作者写进去

import sys                               # 引入模块

def test():
    args = sys.argv                      # sys模块有一个argv变量，用list存储了命令行的所有参数。argv至少有一个元素，因为第一个参数永远是该.py文件的名称
    if len(args)==1:
        print 'Hello World!'
    elif len(args)==2:
        print 'Hello, %s!' % args[1]
    else:
        print 'Too many arguments!'

if __name__=='__main__':                 # Python解释器把一个特殊变量__name__置为__main__，而如果在其他地方导入该hello模块时，if判断将失败，因此，这种if测试可以让一个模块通过命令行运行时执行一些额外的代码，最常见的就是运行测试。
    test()