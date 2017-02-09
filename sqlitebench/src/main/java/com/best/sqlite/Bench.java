package com.best.sqlite;

/**
 * content
 *
 * @author nouuid
 * @date 2/7/2017
 * @since 0.1.0
 */
public class Bench {
//    private String db         = "/opt/sqlitebench/testDB.db";
    private String db         = ":memory:"; // default in-memory database
    private String table      = "test1";
    private int    reportSize = 100;
    private int    threadNum  = 10;
    private String command    = null;

    /**
     * java -Dsearch.range=5000 -Drequest.number.per.thread=1000 -Dreport.size=100 -Dthread.number=10 -Dtable=test1 -jar ./target/sqlitebench-1.0-SNAPSHOT.jar
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Bench demo = new Bench();
        demo.parseArgs();
        demo.on();
        ConnectionHelper.close();
    }

    private void parseArgs() {
        if (StringUtils.isNotBlank(System.getProperty("command"))) {
            command = System.getProperty("command");
        }
        if (StringUtils.isNotBlank(System.getProperty("db"))) {
            db = System.getProperty("db");
        }
        if (StringUtils.isNotBlank(System.getProperty("table"))) {
            table = System.getProperty("table");
        }
        if (StringUtils.isNotBlank(System.getProperty("report.size"))) {
            reportSize = Integer.parseInt(System.getProperty("report.size"));
        }
        if (StringUtils.isNotBlank(System.getProperty("thread.number"))) {
            threadNum = Integer.parseInt(System.getProperty("thread.number"));
        }
    }

    public void on() {
        if ("insert".equals(command)) {
            int columnSize = 1;
            int max = 1;
            if (StringUtils.isNotBlank(System.getProperty("insert.column.size"))) {
                columnSize = Integer.parseInt(System.getProperty("insert.column.size"));
            }
            if (StringUtils.isNotBlank(System.getProperty("insert.max"))) {
                max = Integer.parseInt(System.getProperty("insert.max"));
            }
            if (":memory:".equals(db)) {
                CreateTable createTable = new CreateTable(db, table, columnSize);
                createTable.on();
            }
            Insert insert = new Insert(max, threadNum, reportSize, db, table, columnSize);
            try {
                insert.on();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if ("select".equals(command)) {
            int searchRange = 5000;
            int requestNumPerThread = 10000;
            if (StringUtils.isNotBlank(System.getProperty("select.range"))) {
                searchRange = Integer.parseInt(System.getProperty("select.range"));
            }
            if (StringUtils.isNotBlank(System.getProperty("select.request.number.per.thread"))) {
                requestNumPerThread = Integer.parseInt(System.getProperty("select.request.number.per.thread"));
            }

            if (":memory:".equals(db)) {
                int columnSize = 1;
                if (StringUtils.isNotBlank(System.getProperty("insert.column.size"))) {
                    columnSize = Integer.parseInt(System.getProperty("insert.column.size"));
                }
                CreateTable createTable = new CreateTable(db, table, columnSize);
                createTable.on();
                int max = 1;
                if (StringUtils.isNotBlank(System.getProperty("insert.max"))) {
                    max = Integer.parseInt(System.getProperty("insert.max"));
                }
                Insert insert = new Insert(max, 5, reportSize, db, table, columnSize);
                try {
                    insert.on();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Select select = new Select(db, table, searchRange, requestNumPerThread, reportSize, threadNum);
            try {
                select.on();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
