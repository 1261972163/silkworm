package com.jengine.feature.serialize;

import com.jengine.feature.serialize.xml.DOM4JReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.jengine.feature.serialize.xml.DOMReader;

import java.io.File;
import java.util.Iterator;

/**
 * Created by nouuid on 2015/5/7.
 */
public class XMLTest {

    public static final Log logger = LogFactory.getLog(XMLTest.class);

    @Test
    public void test() {
        String filepath = XMLTest.class.getResource("/").getPath() + "xml/student.xml";
        logger.info("filepath: " + filepath);

        File file = new File(filepath);
        domReaderTest(file);
        dom4jReaderTest(file);
        dom4jReaderTest(file);
        jdomReaderTest(file);
        saxReaderTest(file);

    }

    public void domReaderTest(File file) {
        logger.info("-----------------domReaderTest start-------------------");
        DOMReader domReader = new DOMReader();
        Document doc = domReader.getDocument(file);
        String tagName = "Student";
        NodeList nodeList = domReader.getNodeList(doc, tagName);

        long starttime = System.currentTimeMillis();
        for(int i=0; i < nodeList.getLength(); i++) {
            System.out.print("��ţ�"+ doc.getElementsByTagName("ID").item(i).getFirstChild().getNodeValue());
            System.out.print(" ������"+ doc.getElementsByTagName("Name").item(i).getFirstChild().getNodeValue());
            System.out.println(" �Ա�"+ doc.getElementsByTagName("Sex").item(i).getFirstChild().getNodeValue());
        }
        logger.info("domReaderTest run costs��" + (System.currentTimeMillis() - starttime) + "ms");
        logger.info("-----------------domReaderTest end-------------------");
    }

    public void dom4jReaderTest(File file) {
        logger.info("-----------------dom4jReaderTest start-------------------");
        DOM4JReader dom4JReader = new DOM4JReader(file);
        String elementName = "Student";
        Iterator iterator = dom4JReader.getEelementIterator(elementName);

        long starttime = System.currentTimeMillis();
        try {

            Element foo;
            while (iterator.hasNext()) {
                foo = (Element) iterator.next();
                System.out.print("��ţ�"+ foo.elementText("ID"));
                System.out.print(" ������"+ foo.elementText("Name"));
                System.out.println(" �Ա�"+ foo.elementText("Sex"));
            }
        } catch (Exception e) {
            logger.info("", e);
        }
        logger.info("domReaderTest run costs��" + (System.currentTimeMillis() - starttime) + "ms");
        logger.info("-----------------dom4jReaderTest end-------------------");
    }

    public void jdomReaderTest(File file) {

    }

    public void saxReaderTest(File file) {

    }
}
