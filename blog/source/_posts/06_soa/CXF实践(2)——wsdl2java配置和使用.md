---
title: CXF实践(2)——wsdl2java配置和使用
categories: 服务化
tags: 
	- cxf
date: 2016/7/3 17:37:25
---


# 1 下载apache-cxf-2.7.13-SNAPSHOT

下载地址：http://repository.apache.org/snapshots/org/apache/cxf/apache-cxf/2.7.13-SNAPSHOT/

# 2 环境设置

1. 新建CXF_HOME，D:\Program Files\cxf\apache-cxf-2.7.13-SNAPSHOT
2. 在CLASSPATH中添加，;%CXF_HOME%\lib
3. PATH中添加，;%CXF_HOME%\bin
4. 验证：cmd下，wsdl2java -v，显示wsdl2java - Apache CXF 2.7.13-SNAPSHOT...，配置成功

# 3 wsdl2java工具使用

wsdl2java CustomerService.wsdl
