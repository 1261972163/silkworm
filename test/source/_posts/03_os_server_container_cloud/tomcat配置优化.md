---
title: tomcat配置优化
categories: server
tags: 
  - nodejs
date: 2016/6/3 17:57:25
---

# 1 tomcat/conf/server.xml配置

	<Executor name="tomcatThreadPool" namePrefix="catalina-exec-" 
		maxThreads="500" minSpareThreads="50" maxSpareThreads="100"/>   

	<Connector executor="tomcatThreadPool"   
		port="80" protocol="HTTP/1.1"     
		connectionTimeout="20000" 
		enableLookups="false"   
		redirectPort="8443" 
		URIEncoding="UTF-8" 
		acceptCount="1000" />
	
# 2 tomcat/bin/catalina.sh配置

	CATALINA_OPTS="$CATALINA_OPTS -Xms16384m -Xmx16384m -XX:PermSize=256m -XX:MaxPermSize=512m"

# 3 tomcat/conf/context.xml配置

	<Resource name="jdbc/appname"
		auth="Container" type="javax.sql.DataSource"
		maxActive="500"
		maxIdle="500"
		maxWait="60000"
		username="xingngprod"
		password="******"
		driverClassName="oracle.jdbc.OracleDriver"
		url="jdbc:oracle:thin:@(***)"
		poolPreparedStatements="true"
		removeAbandoned="true"
		removeAbandonedTimeout="300" />