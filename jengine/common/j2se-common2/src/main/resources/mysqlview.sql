SELECT VERSION();   -- 服务器版本信息
SELECT USER();    -- 当前用户
SHOW STATUS;    -- 服务器状态
SHOW VARIABLES;   -- 服务器配置变量
show databases;   -- 数据库列表
DROP database IF EXISTS `test`;   -- 删除数据库
create database test;
use test;
show database();
show tables;

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;   -- 关闭外键约束

DROP TABLE IF EXISTS `test1`;   -- 删除数据库表

create table test1 (    -- 创建表
   id INT NOT NULL AUTO_INCREMENT,
   title VARCHAR(100) NOT NULL,
   author VARCHAR(40) NOT NULL,
   pages int not null,
   submission_date DATE,
   PRIMARY KEY ( id )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

show table status from test where name='test1';   -- 查看表信息
SHOW COLUMNS FROM test1;    -- 显示表的列

set autocommit=0;   -- 禁止自动提交
begin;    -- 事务开始
INSERT INTO test1 (title, author, pages, submission_date)   -- 插入
VALUES("Learn Mysql", "nouuid", 101, NOW()),
  ("Learn Mysql2", "nouuid", 201, NOW()),
  ("Learn Mysql2", "nouuid2", 50, NOW()),
  ("Learn Mysql3", "nouuid3", 500, NOW());
commit;   -- 事务提交
begin;
INSERT INTO test1 (title, author, pages, submission_date)
VALUES("Learn Mysql", "xxx", 11, NOW());
ROLLBACK;   -- 事务回滚
set autocommit=1;   -- 开启自动提交

SET FOREIGN_KEY_CHECKS = 1;   -- 开启外键约束

SELECT * from test1
WHERE title='Learn Mysql' and author LIKE '%uuid' or author='x';

SELECT * from test1 ORDER BY author ASC;

SELECT author, COUNT(*) as production FROM  test1 GROUP BY author;

SELECT author, COUNT(*) as production FROM  test1 GROUP BY author WITH ROLLUP;

create table test2 select * from test1; -- 表数据拷贝，注意：不会拷贝表索引等信息。

ALTER TABLE test2 ADD tmp int;    -- 加列
ALTER TABLE test2 MODIFY tmp CHAR(10);    -- 修改列类型
ALTER TABLE test2 CHANGE tmp tmp2 BIGINT NOT NULL DEFAULT 100;  -- 修改列名称和类型
ALTER TABLE test2 ALTER tmp2 SET DEFAULT 1000;  -- 设置列默认值
ALTER TABLE test2 ALTER tmp2 DROP DEFAULT;    -- 删除列默认值
ALTER TABLE test2 TYPE = MYISAM;    -- 修改表引擎
ALTER TABLE test2 RENAME TO test3;    -- 表重命名
ALTER TABLE test3 DROP tmp2;    -- 删除列

delete from test3 where id=1;   -- 删除指定行

create table test2 select * from test1;

update test2 set title="Learn Mysql of test2"   -- 更新指定记录的列数据
where id=1;

select a.title, b.title, a.author   -- 内连接
from test1 a inner join test2 b
on a.author=b.auther;

select a.title, b.title, a.author
from test1 a, test2 b
where a.author=b.auther;

select a.title, b.title, a.author   -- 左连接
from test1 a left join test2 b
on a.author=b.auther;

select a.title, b.title, a.author   -- 右连接
from test1 a right join test2 b
on a.author=b.auther;

/*
index
 */
CREATE INDEX author_index ON test3(author(40));   -- 建普通索引
ALTER test3 ADD INDEX [author_index] ON (author(40));   -- 建普通索引
DROP INDEX [author_index] ON test3;   -- 删除索引
alter table test3 add index author_index (author, pages) ;   -- 建普通索引
alter table test3 drop index index_name ;   -- 删除索引
alter table test3 add unique author_index(author, pages) ;   -- 建unique索引
alter table test3 drop index index_name ;   -- 删除索引
alter table test3 add primary key (author, pages) ;   -- 建primary索引
alter table test3 drop primary key ;   -- 删除primary索引
ALTER TABLE test3 ADD FULLTEXT author_index (author);
DROP INDEX [author_index] ON test3;   -- 删除索引

CREATE TABLE test4 (
  id INT NOT NULL,
  author VARCHAR(40) NOT NULL,
  INDEX [author_index] (author(40))   -- 建普通索引
);
SHOW INDEX FROM test4\G   -- 查看索引


CREATE TEMPORARY TABLE SalesSummary (   -- 建临时表
    product_name VARCHAR(50) NOT NULL
    , total_sales DECIMAL(12,2) NOT NULL DEFAULT 0.00
    , avg_unit_price DECIMAL(7,2) NOT NULL DEFAULT 0.00
    , total_units_sold INT UNSIGNED NOT NULL DEFAULT 0
);
DROP TABLE SalesSummary;

/*
表复制，包括表的结构，索引，默认值等。
 */
SHOW CREATE TABLE test1 \G;

create table test5 (
   id INT NOT NULL AUTO_INCREMENT,
   title VARCHAR(100) NOT NULL,
   author VARCHAR(40) NOT NULL,
   pages int not null,
   submission_date DATE,
   PRIMARY KEY ( id )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO test5 (id, title, author, submission_date)
select * from test1;

/*
序列重排：删除列时需要防止新纪录添加
 */
ALTER TABLE test5 DROP id;

ALTER TABLE test5
ADD id INT NOT NULL AUTO_INCREMENT FIRST,
ADD PRIMARY KEY (id);

drop table test1;
drop table test2;
drop table test3;
drop table test4;
drop table test5;
drop test;
