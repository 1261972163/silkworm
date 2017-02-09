sqlitebench
===========

sqlite性能测试


# 准备可执行jar

生成可执行jar：

	mvn assembly:assembly
	
得到

	target/sqlitebench-0.1.0-SNAPSHOT.jar
	
将sqlitebench-0.1.0-SNAPSHOT.jar拷贝到安装有sqlite的机器上

# 执行

格式：

	java {PARAS} -jar ./sqlitebench-0.1.0-SNAPSHOT.jar

参数：	

|参数|含义|取值|默认值|
|:--|:--|:--|:--}
|command|执行哪种操作|select/insert|-|
|db|sqlite数据文件|数据文件路径|:memory:，表示使用内存|
|table|表|String|test1|
|report,size|指定条数记录后打印统计日志|int|100|
|thread.number|线程数|int|10|
|insert.column.size|插入的数据表字段数，插入时有效|int|1|
|insert.max|插入的最大记录数|int|1|
|select.range|查询时的id范围|int|5000|
|select.request.number.per.thread|查询时每个线程的请求数|int|10000|

# 示例

插入测试：

	java -Dcommand=insert -Dinsert.max=10000 -Dthread.number=10 -jar ./sqlitebench-0.1.0-SNAPSHOT.jar

查询测试：

	java -Dcommand=select -Dinsert.max=10000 -Dthread.number=10 -jar ./sqlitebench-0.1.0-SNAPSHOT.jar

# 测试结果

样本5000:1000000，查询100000次

**id查询**

|线程数|qps|
|:--|:--|
|1  |47664, 51046, 49776|
|2  |25581, 25310, 26574|
|4  |28352, 32258, 28743|
|6  |33914, 34410, 35011|
|8  |35423, 36563, 35310|
|10 |36088, 35211, 33579|
|20 |36436, 32041, 33783|