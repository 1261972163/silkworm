package com.jengine.transport.serialize.json;

import org.junit.Test;

import java.util.HashMap;

/**
 * content
 *
 * @author nouuid
 * @date 12/14/2016
 * @since 0.1.0
 */
public class JsonUtilDemo {
    @Test
    public void test() {
        HashMap<String, Object> mdMap = new HashMap<String, Object>();
        mdMap.put("weight", 10);
        System.out.println(JsonUtil.toJson(mdMap));
    }

    @Test
    public void test2() {
        class Student{
            private String name;
            private int age;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }
        }
        Student student = new Student();
        student.name = "name1";
        student.age = 1;
        System.out.println(JsonUtil.toJson(student));
    }
}
