package com.nouuid.mysqlbench.local;

import com.nouuid.mysqlbench.common.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nouuid
 * @date 7/28/2016
 * @description
 */
public class LocalMain {
    /**
     *
     * @param args
     *  --server                mysql host and port
     *  --user                  mysql user name
     *  --password              mysql user password
     *  --database              mysql database
     *  --table-row-size        row size of table
     *  --table-col-size        column size of table
     *  --insert-mode           insert string or uuid
     *  --thread-num            thread number of processor
     *  --exe-duration          max time of executing
     *  --report-duration       report interval
     *  --test-mode             use transaction? trx : nontrx
     *  --nontrx-mode           operation: select
     *  --select-mode           select string or id
     * @throws Exception
     * @example --server=10.45.11.85:3306 --user=root --password=MyNewPass4! --database=aaa --table-row-size=10 --table-col-size=10 --insert-mode=string --thread-num=10 --exe-duration=120 --report-duration=10 --test-mode=nontrx --nontrx-mode=select --select-mode=string
     */
    public static void main(String[] args) throws Exception {

        Map<String, String> exeMap = new HashMap<String, String>();
        for (int i = 0; i < args.length; i++) {
            String[] pair = args[i].split("=");
            if (pair.length != 2 || StringUtils.isBlank(pair[0]) || StringUtils.isBlank(pair[1])) {
                throw new Exception("error parameter pair.");
            }
            exeMap.put(pair[0].trim(), pair[1].trim());
        }

        if (exeMap.isEmpty()) {
            throw new Exception("no executable parameters.");
        }

        BenchConfig benchConfig = new BenchConfig();
        for (String key : exeMap.keySet()) {
            switch (key) {
                case "--server":
                    benchConfig.setServer(exeMap.get(key));
                    break;
                case "--user":
                    benchConfig.setUsername(exeMap.get(key));
                    break;
                case "--password":
                    benchConfig.setPwd(exeMap.get(key));
                    break;

                case "--database":
                    benchConfig.setDatabase(exeMap.get(key));
                    break;
                case "--table-name":
                    benchConfig.setTableName(exeMap.get(key));
                    break;
                case "--table-row-size":
                    benchConfig.setTableRowSize(Long.parseLong(exeMap.get(key)));
                    break;
                case "--table-col-size":
                    benchConfig.setColNum(Integer.parseInt(exeMap.get(key)));
                    break;
                case "--table-num":
                    benchConfig.setTableNum(Integer.parseInt(exeMap.get(key)));
                    break;

                case "--insert-mode":
                    benchConfig.setInsertMode(exeMap.get(key));
                    break;

                case "--thread-num":
                    benchConfig.setThreadNum(Integer.parseInt(exeMap.get(key)));
                    break;
                case "--exe-duration":
                    benchConfig.setDuration(Long.parseLong(exeMap.get(key)));
                    break;
                case "--report-duration":
                    benchConfig.setReportDuration(Long.parseLong(exeMap.get(key)));
                    break;
                case "--test-mode":
                    benchConfig.setTestMode(exeMap.get(key));
                    break;
                case "--nontrx-mode":
                    benchConfig.setNontrxMode(exeMap.get(key));
                    break;
                case "--select-mode":
                    benchConfig.setSelectMode(exeMap.get(key));
                    break;
                default:
                    break;
            }
        }

        MysqlBench mysqlBench = new MysqlBench(benchConfig);
        if ("prepare".equals(exeMap.get("--exe"))) {
            mysqlBench.prepare();
        } else if ("run".equals(exeMap.get("--exe"))) {
            mysqlBench.run();
        } else if ("clean".equals(exeMap.get("--exe"))) {
            mysqlBench.cleanup();
        } else {
            throw new Exception("no executable command.");
        }
    }
}
