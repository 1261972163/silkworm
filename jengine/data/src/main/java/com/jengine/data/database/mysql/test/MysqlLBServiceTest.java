package com.jengine.data.database.mysql.test;

import com.jengine.data.database.mysql.MysqlLBService;

/**
 * @author nouuid
 * @date 6/30/2016
 * @description
 */
public class MysqlLBServiceTest {

    MysqlLBService mysqlLBService = new MysqlLBService();

    @org.junit.Before
    public void initMysqlService() {
        String url = "jdbc:mysql:loadbalance://10.45.11.84:3306,10.45.11.85:3306/test?loadBalanceConnectionGroup=first&loadBalanceEnableJMX=true&useUnicode=true&characterEncoding=UTF8";
//        String url = "jdbc:mysql:loadbalance://10.45.11.84:3306/test?loadBalanceConnectionGroup=first&loadBalanceEnableJMX=true&useUnicode=true&characterEncoding=UTF8";
//        String url = "jdbc:mysql:loadbalance://10.45.11.85:3306/test?loadBalanceConnectionGroup=first&loadBalanceEnableJMX=true&useUnicode=true&characterEncoding=UTF8";
        try {
            mysqlLBService.init(url, "root", "MyNewPass4!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void testCreate() {
        int tNum = 10;
        for (int i=1; i<=tNum; i++) {
            String sql = "create table abc" + i + " (\n" +
                    "id int not null auto_increment,\n" +
                    "title varchar(100) not null,\n" +
                    "author varchar(40) not null,\n" +
                    "date date,\n" +
                    "primary key (id)\n" +
                    ");";
            System.out.println(sql);
            mysqlLBService.executeUpdate(sql);
            try {
                Thread.sleep(1*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @org.junit.Test
    public void testInsert1() {
        int num = 4;
        String sql1 = "insert into abc () values (" + num + ");";
        mysqlLBService.executeUpdate(sql1);
    }

    @org.junit.Test
    public void testInsert2() {
//        int num = 1000*1000; //1M
        int num = 1; //1M
        String title = "\"" + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + "\"";
        String author = "\"" + "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" + "\"";

        for (int i=0; i<num; i++) {
            String sql1 = "insert into abc2 (title, author, date) values (" + title + "," + author + ",NOW());";
            mysqlLBService.executeUpdate(sql1);
            System.out.println(i);
        }
    }

    @org.junit.Test
    public void testQuery() {
        String sql2 = "select * from abc;";
        mysqlLBService.executeQuery(sql2);
    }

    @org.junit.Test
    public void testDrop() {
        int tNum = 10;
        for (int i=1; i<=tNum; i++) {
            String sql = "drop table abc" + i + ";";
            System.out.println(sql);
            mysqlLBService.executeUpdate(sql);
            try {
                Thread.sleep(1*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
