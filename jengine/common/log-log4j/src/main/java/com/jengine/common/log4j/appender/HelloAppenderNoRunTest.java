package com.jengine.common.log4j.appender;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * content
 *
 * @author nouuid
 * @date 8/10/2017
 * @since 0.1.0
 */
public class HelloAppenderNoRunTest {

    public static void main(String[] args) {
        String log4jFilePath = HelloAppenderNoRunTest.class.getResource("/").getPath();
        System.out.println(log4jFilePath);
        PropertyConfigurator.configure(log4jFilePath + "META-INF/appender/log4j.properties");
        Logger log = LoggerFactory.getLogger(HelloAppenderNoRunTest.class) ;
        log.info("I am ready.") ;
    }
}
