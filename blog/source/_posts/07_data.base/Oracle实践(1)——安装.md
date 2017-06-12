---
title: Oracle实践(1)——安装
categories: 数据库
date: 2014/6/18 20:46:25
tags:
	- oracle
---

# Win7下安装oracle client和PlSql Developer

## 1 准备工作

1. oracle_win32_11gR2_client
2. PLSQL

## 2 安装步骤

1. 下载安装oracle_win32_11gR2_client；
2. 配置TNS_ADMIN环境变量；
3. 安装PLSQL；
4. 配置PLSQL下的oracle home和oci library。

具体步骤见《4 具体步骤》，见http://yefenme.blog.163.com/blog/static/1306977042013530111554972/

## 3 注意事项

1. PLSQL不支持64位，请安装32位oracle client；
2. oracle client安装路径不要带空格。

## 4 具体步骤

见http://yefenme.blog.163.com/blog/static/1306977042013530111554972/

---------
-------------------- 华丽的分隔线 --------------------
---------


# 实战练习

[命令参考](http://www.w3school.com.cn/sql/sql_select.asp)



**表复制（SELECT INTO 和 INSERT INTO SELECT）**

> 将一个table1的数据的部分字段复制到table2中，或者将整个table1复制到table2中

	方法一：INSERT INTO SELECT
	-- 1.创建测试表
	create TABLE Table1(a varchar(10), b varchar(10), c varchar(10) )
	create TABLE Table2(a varchar(10), c varchar(10), d int)
	-- 2.创建测试数据
	Insert into Table1 values('赵','asds','90')  
    Insert into Table1 values('钱','asds','100')  
    Insert into Table1 values('孙','asds','80')  
    Insert into Table1 values('李','asds',null) 
    -- 3.INSERT INTO SELECT语句复制表数据
	insert into Table2(a, c, d) select a,c,5 from Table1
	-- 4.显示更新后的结果  
    select * from Table2
    -- 5.删除测试表  
    drop TABLE Table1  
    drop TABLE Table2
	
	方法二：SELECT INTO
	-- 1.创建测试表
	create TABLE Table1(a varchar(10), b varchar(10), c varchar(10) )
	-- 2.创建测试数据
	Insert into Table1 values('赵','asds','90')  
    Insert into Table1 values('钱','asds','100')  
    Insert into Table1 values('孙','asds','80')  
    Insert into Table1 values('李','asds',null) 
	-- 3.SELECT INTO FROM语句创建表Table2并复制数据  
    select a,c INTO Table2 from Table1 
	-- 4.显示更新后的结果  
    select * from Table2
    -- 5.删除测试表  
    drop TABLE Table1  
    drop TABLE Table2

> 注意：
如果在sql/plus或者PL/SQL执行这条语句，会报"ORA-00905:缺失关键字"错误，原因是PL/Sql与T-SQL的区别。

> T-SQL中该句正常，但PL/SQL中解释是:select..into is part of PL/SQL language which means you have to use it inside a PL/SQL block. You can not use it in a SQL statement outside of PL/SQL.
即不能单独作为一条sql语句执行，一般在PL/SQL程序块(block)中使用。

> 如果想在PL/SQL中实现该功能，可使用Create table newTable as select * from ...：
如： create table NewTable as select * from ATable;
NewTable 除了没有键，其他的和ATable一样

	-- 1.创建测试表
	create TABLE Table1(a varchar(10), b varchar(10), c varchar(10) )
	-- 2.创建测试数据
	Insert into Table1 values('赵','asds','90')  
    Insert into Table1 values('钱','asds','100')  
    Insert into Table1 values('孙','asds','80')  
    Insert into Table1 values('李','asds',null) 
	-- 3.Create table newTable as select * from ...语句创建表Table2并复制数据  
    create Table2 as select a,c from Table1 
	-- 4.显示更新后的结果  
    select * from Table2
    -- 5.删除测试表  
    drop TABLE Table1
    drop TABLE Table2

---------
-------------------- 华丽的分隔线 --------------------
---------