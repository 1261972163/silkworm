package com.jengine.transport.serialize.xml;

import java.io.File;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/**
 * JDOM实现方法
 * 基于树型结构，利用纯JAVA的技术对XML文档实现解析、生成、序列化以及多种操作。
 * Created by nouuid on 2015/4/30.
 */
public class JDOMReader {
    public void test(File file) {
        long lasting = System.currentTimeMillis();
        try {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(file);
            Element foo = doc.getRootElement();
            List allChildren = foo.getChildren();
            for (int i = 0; i < allChildren.size(); i++) {
                System.out.print("编号："+ ((Element) allChildren.get(i)).getChild("ID").getText());
                System.out.print(" 姓名："+ ((Element) allChildren.get(i)).getChild("Name").getText());
                System.out.println(" 性别："+ ((Element) allChildren.get(i)).getChild("Sex").getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("运行时间：" +(System.currentTimeMillis() - lasting) + "毫秒");
    }
}
