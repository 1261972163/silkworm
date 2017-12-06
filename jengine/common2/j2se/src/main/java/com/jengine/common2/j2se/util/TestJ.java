package com.jengine.common2.j2se.util;

import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * content
 *
 * @author nouuid
 * @date 9/26/2016
 * @since 0.1.0
 */
public class TestJ {
    @Test
    public void test1() {
        int[] x = new int[3];
        x[0] = 1;
        x[1] = 2;
        x[2] = 3;
        List list = Arrays.asList(x);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        for (int id : x) {
            stringBuilder.append(id).append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        stringBuilder.append(")");
        System.out.println(stringBuilder.toString());
    }

    @Test
    public void test2() {
        System.out.println(new Date().getTime());
        System.out.println(System.currentTimeMillis());
    }

    @Test
    public void test3() {
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        readWriteLock.readLock().lock();
        readWriteLock.readLock().unlock();
        System.out.println("end.");
    }

    @Test
    public void test4() {
        System.out.println(UUID.randomUUID());
    }



    @Test
    public void test5() {
        String fileName = "E:\\nouuid_test\\8x.txt";
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            String[] tos = new String[6];
            tos[0] = "nouuid-es-prod-node-2";
            tos[1] = "nouuid-es-prod-node-3";
            tos[2] = "nouuid-es-prod-node-4";
            tos[3] = "nouuid-es-prod-node-5";
            tos[4] = "nouuid-es-prod-node-6";
            tos[5] = "nouuid-es-prod-node-7";
            List<String> tos2 = Arrays.asList(tos);
            while ((tempString = reader.readLine()) != null) {
                String[] splits = tempString.split("\\t");
                if (splits.length!=4 || tos2.contains(splits[3])) {
                    continue;
                }
                url(splits[0], splits[1], splits[3], tos[line%6]);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }



    private void url(String index, String shardNum, String from, String to) {
        String s = "curl -XPOST 'http://10.9.19.141:9201/_cluster/reroute' -d '{\"commands\": [ {\"move\":{\"index\": \""+index +"\", \"shard\" : "+shardNum+",\"from_node\": \""+from+"\", \"to_node\" : \""+to+"\"}}]}'";
        System.out.println(s);
    }

    //executeCMD(new String[]{"curl","-s","-u","mqMonitor:mqMonitor",allUrl});

    private String executeCMD(String[] cmd) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.redirectErrorStream(true);
        Process p = pb.start();

        StringBuilder output = new StringBuilder();

        // read the standard output
        BufferedReader stdout = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;
        while ((line = stdout.readLine()) != null) {
            output.append(line);
        }
        int ret = p.waitFor();
        stdout.close();
        String result = output.toString();

        if (ret != 0) {
            throw new RuntimeException(output.toString());
        }else{
            return result.substring(result.indexOf('['));
        }
    }






}
