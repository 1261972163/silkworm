---
title: 技能清单
categories: default
date: 2017-06-03 09:56:00
---


# 通用技能

* 职业观。双赢思考、责任心、及时沟通、反馈、团队意识、换位思考。
* 知识观。保持对知识的好奇和热情，不局限在自己的圈子。
* 方法论。批判性思考、任务拆分、SMART原则。
* 价值观。研发武器，拒绝玩具。实践是检验真理的唯一标准。
* 时间管理。四象限、专注力。
* Office能力。Word、PPT、Excel、yEd/Visio、markdown、MindManager/Freemind/Xmind、VIM。
* 完成的定义。弄懂原理、解决了问题、拥有足够的测试、及时反馈进度、更新相关文档、沉淀。
* 熟练的定义。
	* 可以脱离文档操作、重要特性了如指掌。
	* 弄懂原理、操作的顺其自然、读过源码甚至很多遍、能够修改源码。
	* 实战过无数回，遇到很多奇葩环境，有足够信心绕过。
	* 具备创造性，不仅仅跟在大牛后面，研究出几个不错的技巧、有paper有分享，甚至写出了自己的工具。
* 学习的原则。至少通读一遍官方文档、至少看完和练习好一本书。

# 专业基础

* 算法。熟练**快排**和**二分**。
* 网络。
	* TCP/IP协议栈、OSI参考模型、TCP的三次握手与四次分手。
	* HTTP抓包和调试。
	* 什么是跳转。服务端跳转、客户端跳转。
* Linux。
	* 文件操作、scp、sz、rz、ssl、netstat、top、iostat、crontab、ps、nslookup等。
* 正则表达式。[正则表达式30分钟入门教程](http://deerchao.net/tutorials/regex/regex.htm)。工具：RegexBuddy、Kodos、[正则图解](http://www.regexper.com/)
* 数据库
	* 熟悉关系数据库（Oracle、MySQL）和SQL语句。
	* 熟悉JDBC，知道SQL注入安全漏洞，[掌握如何用PreparedStatement防止注入](http://bobby-tables.com/)。
	* 什么是事务
* 瀑布模型。需求->需求分析->设计->开发->测试->上线->运维/运营
* 需求分析能力
* 调试能力。
	* 只要定位出，就没有解决不了的Bugs
	* 肉眼看到的都是假象，一定要专业的工具与经验配合
	* Bugs在哪出现，最终就在哪进行真实模拟调试
	* 缩小范围。构建自己的测试样例（排除网络复杂未知情况），关联模块一个个排除，单步调试
* 敏捷思想。快速迭代、任务拆细、v1原则、记录-沉淀-分享。
* 科学上网。
	* shadowsocks + vps + Chrome(SwitchyOmega)/Firefox(AutoProxy)
	* [SSH隧道](https://www.ibm.com/developerworks/cn/linux/l-cn-sshforward/index.html)。本地转发、远程转发、动态转发。

# Web编程

* [java]()。集合、泛型、IO/NIO、并发、反射、序列化、内存模型、GC调优、JVM调优工具。
* 前端。目标：写一个基于DOM、XPath或者CSS Selector的网页解析器（爬网页）。
	* DOM。搞好前端的必要基础。
	* 《JavaScript DOM编程艺术》
	* jQuery。官方文档得过一遍。优秀的插件应该体验一遍，并做些尝试。
	* Bootstrap。应该使用一遍。
	* ReactJS。Facebook出品的颠覆性前端框架。
	* 验证码破解。pytesser
* 前后端通信框架
	* [Servlet接口]()
	* 标准的Servlet容器怎么用，包括web.xml的用法以及listener、filter等概念。以及某个Servlet容器（如Jetty或者Tomcat）的具体用法。
	* 模板语言，如haml, velocity, freemarker。
	* Spring框架中的Web框架。
	* 熟悉Spring Bean Container以及里面各种乱七八糟的工具。
	* 什么是RESTful Web Service。
* 对象管理。Spring的IOC和AOP，对象生命周期。
* 数据库
	* 了解java transaction api(JTA)。
	* 对象关系转换，如MyBatis、Hibernate。
* 序列化
	* JSON
	* cPickle
	* protobuf
	* avro
* 调度
	* crontab是最原生的定时调度
	* 基于redis实现的分布式调度
	* 基于rpyc实现的分布式调度
	* celery/gearman等调度框架
* 并发
	* 线程池。进程内优美的并发方案
	* 协程。进程内另一种优美的并发方案
* 日志
	* 熟悉一下slf4j和logback的用法。
* 持续集成
	* jenkins
* 项目管理。Git、SVN、Maven、Gradle

# 云平台

* Docker

# 架构

* 分布式。什么是分布式一致性，两阶段提交，paxos，[微信开源：生产级paxos类库PhxPaxos实现原理介绍](http://www.infoq.com/cn/articles/weinxin-open-source-paxos-phxpaxos)
* RPC。
* SOA。
* 微服务。

# 数据存储

* MySQL
* Redis
* Sqlite
* Hadoop体系。HDFS、MapReduce、HBase
* bsddb
* ElasticSearch
* kafka

# 数据处理

* Hive
* Spark
* ELK。ElasticSearch + Logstash + Kibana。分词原理、[IK分词原理](http://boke.io/ikfen-ci-yuan-li/)

	

# 软件工程


# 机器学习

* python基础必会
	* [Python 2.7教程](http://www.liaoxuefeng.com/wiki/001374738125095c955c1e6d8bb493182103fac9270762a000)
	* [Python编码规范](http://blog.knownsec.com/Knownsec_RD_Checklist/PythonCodingRule.pdf)
	* [The Python Standard Library](https://docs.python.org/2.7/library/index.html)
* 贝叶斯算法
	* [《贝叶斯推断及其互联网应用1》](http://www.ruanyifeng.com/blog/2011/08/bayesian_inference_part_one.html)
	* [《贝叶斯推断及其互联网应用2》](http://www.ruanyifeng.com/blog/2011/08/bayesian_inference_part_two.html)
	* [算法杂货铺——分类算法之朴素贝叶斯分类(Naive Bayesian classification)](http://www.cnblogs.com/leoo2sk/archive/2010/09/17/naive-bayesian-classifier.html)
	* [朴素贝叶斯（NB,Naive Bayes)简介](http://home.cnblogs.com/group/topic/40112.html)	
	* [数学之美番外篇：平凡而又神奇的贝叶斯方法](http://mindhacks.cn/2008/09/21/the-magical-bayesian-method/)
	* nouuid说：

			简单来说，贝叶斯算法是全概率公式的运用
				后验概率　＝　先验概率 ｘ 调整因子
			一般是先预估一个"先验概率"，然后加入实验结果，看这个实验到底是增强还是削弱了"先验概率"，由此得到更接近事实的"后验概率"。
* 神经元
* 遗传算法
* 聚类、分类







# links

* [IBM developerworks](https://www.ibm.com/developerworks/cn/)
* [阿里中间件团队](http://jm.taobao.org/)
* [美团点评技术博客](http://tech.meituan.com/)
* [腾讯ISUX](https://isux.tencent.com/)
* [华为企业互动社区](http://support.huawei.com/huaweiconnect/enterprise/index.html)
* [奇虎360技术博客](http://blogs.360.cn/)

* [并发编程网](http://ifeve.com/)
* [知道创宇技能表](http://blog.knownsec.com/Knownsec_RD_Checklist/v3.0.html)
* [酷壳](http://coolshell.cn/)

* [阮一峰](http://www.ruanyifeng.com/home.html)
* [廖雪峰](http://www.liaoxuefeng.com/)
* [agapple](http://agapple.iteye.com/) 
* [geekplux](http://www.geekplux.com/)

* [刘未鹏](http://mindhacks.cn/)
* [李笑来](http://xiaolai.li/)
* [阳志平](http://www.yangzhiping.com/) 

* [Java学习路线图](http://www.importnew.com/24103.html)
