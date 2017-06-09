package com.jengine.common.utils;

import org.junit.Test;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;

/**
 * content
 *
 * @author nouuid
 * @date 9/29/2016
 * @since 0.1.0
 */
public class PropertiesUtil {

    String     startTimeFilePath = PropertiesUtil.class.getResource("/test.properties").getPath();
    Properties properties        = new Properties();

    @Test
    public void loadStartTime() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(startTimeFilePath);
            properties.load(fis);
            printProperties();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printProperties() {
        Enumeration pipelineIds = properties.propertyNames();
        while (pipelineIds.hasMoreElements()) {
            String key = (String) pipelineIds.nextElement();
            System.out.print(Long.parseLong(key) + "=");
            System.out.print(Long.parseLong(properties.getProperty(key)));
            System.out.println();
        }
    }

    @Test
    public void writeStartTime() {
        loadStartTime();
        System.out.println("--------------------");
        Long pipelineId = 2L;
        Long startTimeStamp = 245L;
        properties.setProperty(Long.toString(pipelineId), Long.toString(startTimeStamp));
        OutputStream out = null;
        try {
            out = new FileOutputStream(startTimeFilePath);
            properties.store(out, "Update " + pipelineId + " name");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printProperties();
    }
}
