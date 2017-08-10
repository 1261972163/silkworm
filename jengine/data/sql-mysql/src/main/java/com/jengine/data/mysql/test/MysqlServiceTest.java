package com.jengine.data.mysql.test;


import com.jengine.data.mysql.MysqlService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * @author nouuid
 * @date 6/30/2016
 * @description
 */
public class MysqlServiceTest {

    MysqlService mysqlService = new MysqlService();
    String       database     = "test";
    int          tableNum     = 10;

    @org.junit.Before
    public void initMysqlService() {
        String url = "jdbc:mysql:loadbalance://10.45.11.84:3306/test?loadBalanceConnectionGroup=first&loadBalanceEnableJMX=true&useUnicode=true&characterEncoding=UTF8";
//        String url = "jdbc:mysql:loadbalance://10.45.11.85:3306/test?loadBalanceConnectionGroup=first&loadBalanceEnableJMX=true&useUnicode=true&characterEncoding=UTF8";
        try {
            mysqlService.init(url, "root", "MyNewPass4!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.After
    public void close() {
        mysqlService.close();
    }

    @org.junit.Test
    public void test() {

        int tableSize = 10*10000;

        create(tableNum);
        insert(tableSize);
    }

    @org.junit.Test
    public void testCreate() {
        int tNum = 10;
        create(10);
    }

    private void create(int num) {
        for (int i=1; i<=num; i++) {
            String sql = "create table abc" + i + " (\n" +
                    "id int not null auto_increment,\n" +
                    "title varchar(100) not null,\n" +
                    "author varchar(40) not null,\n" +
                    "date date,\n" +
                    "primary key (id)\n" +
                    ");";
            mysqlService.doUpdate(sql);
            System.out.println("create table " + database + "" + i + ", finished.");
            try {
                Thread.sleep(1*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @org.junit.Test
    public void testInsert() {
        int num = 1000*1000; //1M
        insert(num);
    }


    private void insert(int tableSize) {
        for (int i=1; i<=tableNum; i++) {
            for (int j=0; j<tableSize; j++) {
                String title = "\"" + UUID.randomUUID() + "\"";
                String author = "\"" + UUID.randomUUID() + "\"";
                String sql = "insert into " + database + "" + i + " (title, author, date) values (" + title + "," + author + ",NOW());";
                mysqlService.doUpdate(sql);
            }
            System.out.println("insert table " + database + "" + i + ", finished.");
        }
    }

    @org.junit.Test
    public void testQuery() {
        String sql2 = "select * from abc;";
        ResultSet rs = mysqlService.doQuery(sql2);
        if (rs != null) {
            try {
                while (rs.next()) {
                    System.out.println(rs.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @org.junit.Test
    public void testDrop() {
        int tNum = 10;

    }

    private void cleanup() {
        for (int i=1; i<=tableNum; i++) {
            String sql = "drop table " + database + "" + i + ";";
            mysqlService.doUpdate(sql);
            System.out.println("drop table " + database + "" + i + ", finished.");
            try {
                Thread.sleep(1*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
