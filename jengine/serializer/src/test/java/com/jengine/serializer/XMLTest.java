package com.jengine.serializer;

import com.jengine.serializer.xml.DOM4JReader;
import com.jengine.serializer.xml.DOMReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.Iterator;

/**
 * Created by nouuid on 2015/5/7.
 */
public class XMLTest {

    public static final Log logger = LogFactory.getLog(XMLTest.class);
    File file = null;

    @Before
    public void before() {
        String filepath = XMLTest.class.getResource("/").getPath() + "xml/student.xml";
        logger.info("filepath: " + filepath);
        file = new File(filepath);
    }

    @Test
    public void domReaderTest() {
        logger.info("starting.");
        DOMReader domReader = new DOMReader();
        Document doc = domReader.getDocument(file);
        String tagName = "Student";
        NodeList nodeList = domReader.getNodeList(doc, tagName);

        long starttime = System.currentTimeMillis();
        for(int i=0; i < nodeList.getLength(); i++) {
            System.out.print("ID:"+ doc.getElementsByTagName("ID").item(i).getFirstChild().getNodeValue() + " ");
            System.out.print("Name:"+ doc.getElementsByTagName("Name").item(i).getFirstChild().getNodeValue() + " ");
            System.out.println("Sex:"+ doc.getElementsByTagName("Sex").item(i).getFirstChild().getNodeValue());
        }
        logger.info("domReaderTest run costs " + (System.currentTimeMillis() - starttime) + "ms");
        logger.info("finished.");
    }

    @Test
    public void dom4jReaderTest() {
        logger.info("starting.");
        DOM4JReader dom4JReader = new DOM4JReader(file);
        String elementName = "Student";
        Iterator iterator = dom4JReader.getEelementIterator(elementName);

        long starttime = System.currentTimeMillis();
        try {

            Element foo;
            while (iterator.hasNext()) {
                foo = (Element) iterator.next();
                System.out.print(foo.elementText("ID") + " ");
                System.out.print(foo.elementText("Name") + " ");
                System.out.println(foo.elementText("Sex"));
            }
        } catch (Exception e) {
            logger.info("", e);
        }
        logger.info("domReaderTest run costs " + (System.currentTimeMillis() - starttime) + "ms");
        logger.info("finished.");
    }

    @Test
    public void jdomReaderTest() {

    }

    @Test
    public void saxReaderTest() {

    }
}

class XMLTestObject {
    private String name;
    private Boolean sex;
    private int age;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Boolean getSex() {
        return sex;
    }
    public void setSex(Boolean sex) {
        this.sex = sex;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
