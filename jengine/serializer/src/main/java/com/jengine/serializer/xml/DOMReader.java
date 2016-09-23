package com.jengine.serializer.xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;


/**
 * DOM实现方法：随机访问
 * 基于DOM的XML分析器将一个XML文档转换成一个对象模型的集合（通常称DOM树），应用程序正是通过对这个对象模型的操作，来实现对XML文档数据的操作。
 * 由于DOM分析器把整个XML文档转化成DOM树放在了内存中，因此，当文档比较大或者结构比较复杂时，对内存的需求就比较高。而且，对于结构复杂的树的遍历也是一项耗时的操作。
 * Created by nouuid on 2015/4/30.
 */
public class DOMReader {

    public static final Log logger = LogFactory.getLog(DOMReader.class);

    private DocumentBuilder builder;

    public DOMReader() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.error("", e);
        }
    }

    public Document getDocument(File file) {
        Document doc = null;
        try {
            doc = builder.parse(file);
        } catch (SAXException | IOException e) {
            logger.error("", e);
        }
        return doc;
    }

    public NodeList getNodeList(Document doc, String tagName) {
        return doc==null ? null : doc.getElementsByTagName(tagName);
    }

}
