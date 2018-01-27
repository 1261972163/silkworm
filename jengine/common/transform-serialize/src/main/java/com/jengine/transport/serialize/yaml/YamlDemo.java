package com.jengine.transport.serialize.yaml;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * content
 *
 * @author nouuid
 * @date 4/18/2017
 * @since 0.1.0
 */
public class YamlDemo {

    @org.junit.Test
    public void test() throws FileNotFoundException {
        String filepath = YamlDemo.class.getResource("/").getPath() + "yamlConfig.yml";
        InputStream input = new FileInputStream(filepath);
        Yaml yaml = new Yaml();
        Object result = yaml.load(input);
        System.out.println(result.getClass());
        System.out.println( result);

        System.out.println("parse:");
        Map<String, Object> objectMap = (Map<String, Object>) result;
        for (String key : objectMap.keySet()) {
            System.out.println(key + " = " + objectMap.get(key) + " / " + objectMap.get(key).getClass());
        }

        System.out.println("end.");
    }

    @org.junit.Test
    public void test2() throws FileNotFoundException {
        String filepath = YamlDemo.class.getResource("/").getPath() + "yamlConfig2.yml";
        InputStream input = new FileInputStream(filepath);
        Yaml yaml = new Yaml();
        Object result = yaml.load(input);

        if (result instanceof LinkedHashMap) {
            Map<String, Object> objectMap = (Map<String, Object>) result;

        }

        System.out.println("# end.");
    }

    @org.junit.Test
    public void test3() throws Exception {
        String filepath = YamlDemo.class.getResource("/").getPath() + "yamlConfig3.yml";
        InputStream input = new FileInputStream(filepath);
        Yaml yaml = new Yaml();
        Object yamlObject = yaml.load(input);
        if (!(yamlObject instanceof LinkedHashMap)) {
            throw new Exception("server config has error. path=" + filepath);
        }
        Map<String, Object> serverConfProps = (Map<String, Object>) yamlObject;
        Object kafkaConfs = serverConfProps.get("kafka");
        Object indexConfs = serverConfProps.get("index");
        Object serverConfs = serverConfProps.get("server");
        if (kafkaConfs instanceof LinkedHashMap) {
            Map<String, Object> objectMap = (Map<String, Object>) kafkaConfs;
            for (String key : objectMap.keySet()) {
                System.out.println(key + " = " + objectMap.get(key));
            }
        }
        System.out.println("-------- 1");
        if (indexConfs instanceof LinkedHashMap) {
            Map<String, Object> objectMap = (Map<String, Object>) indexConfs;
            for (String key : objectMap.keySet()) {
                System.out.println(key + " = " + objectMap.get(key));
            }
        }
        System.out.println("-------- 2");
        if (serverConfs instanceof LinkedHashMap) {
            Map<String, Object> objectMap = (Map<String, Object>) serverConfs;
            for (String key : objectMap.keySet()) {
                System.out.println(key + " = " + objectMap.get(key));
            }
        }

        System.out.println("# end.");
    }

    private void parse(Object data) {
        if (data instanceof LinkedHashMap) {
            Map<String, Object> objectMap = (Map<String, Object>) data;
            for (String key : objectMap.keySet()) {
                parse(objectMap.get(key));
            }
        } else if (data instanceof ArrayList) {
            ArrayList<Object> objectList = (ArrayList<Object>) data;
            for (Object o : objectList) {
                parse(o);
            }
        } else {
            System.out.println(data);
        }
    }
}
