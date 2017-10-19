package com.jengine.data.elasticsearch;

import com.jengine.data.elasticsearch.mydataframe.AnalyzerType;
import com.jengine.data.elasticsearch.mydataframe.DataType;
import com.jengine.data.elasticsearch.mydataframe.RecordType;
import com.jengine.data.elasticsearch.mydataframe.StoreType;
import com.jengine.data.elasticsearch.tools.RecordTypeBuilder;

import com.jengine.transport.serialize.json.JsonUtil;

import junit.framework.Assert;

import org.elasticsearch.cluster.metadata.IndexTemplateMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.compress.CompressedXContent;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bl07637
 * @date 8/9/2016
 * @description
 */
public class TemplateServiceTest extends EsClientTest {

  @Test
  public void create() throws Exception {
    String templateName = indexName + "-" + type;
    // delete
    if (templateService.exists(templateName)) {
      templateService.delete(templateName);
    }
    // create
    Map<String, RecordType> properties = new HashMap<String, RecordType>();
//        properties.put("field", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
//        properties.put("field", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.IK).setStoreType(StoreType.TRUE).build());
    properties.put("field", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.PINYIN).setStoreType(StoreType.TRUE).build());
    String templatePattern = templateName + "-*";
    try {
      templateService.create(templateName, templatePattern, templateName, properties);
      Thread.sleep(10 * 1000);
      System.out.println("create template=" + templateName);
      Assert.assertTrue(templateService.exists(templateName));
    } finally {
      // delete
      if (templateService.exists(templateName)) {
        templateService.delete(templateName);
      }
    }
  }

  @Test
  public void exists() throws Exception {
    String templateName = indexName + "-" + type;
    // delete
    if (templateService.exists(templateName)) {
      templateService.delete(templateName);
    }
    // create
    Map<String, RecordType> properties = new HashMap<String, RecordType>();
    properties.put("field", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    String templatePattern = templateName + "-*";
    try {
      templateService.create(templateName, templatePattern, templateName, properties);
      Thread.sleep(10 * 1000);
      System.out.println("create template=" + templateName);
      Assert.assertTrue(templateService.exists(templateName));
    } finally {
      // delete
      if (templateService.exists(templateName)) {
        templateService.delete(templateName);
      }
    }
  }

  @Test
  public void delete() throws Exception {
    String templateName = indexName + "-" + type;
    // delete
    if (templateService.exists(templateName)) {
      templateService.delete(templateName);
    }
    // create
    Map<String, RecordType> properties = new HashMap<String, RecordType>();
    properties.put("field", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    String templatePattern = templateName + "-*";
    try {
      templateService.create(templateName, templatePattern, templateName, properties);
      Thread.sleep(5 * 1000);
      System.out.println("create template=" + templateName);
      Assert.assertTrue(templateService.exists(templateName));

      Thread.sleep(5 * 1000);
      templateService.delete(templateName);
      Assert.assertFalse(templateService.exists(templateName));
    } finally {
      // delete
      if (templateService.exists(templateName)) {
        templateService.delete(templateName);
      }
    }
  }

  @Test
  public void get() throws Exception {
    String templateName = indexName + "-" + type;
    // delete
    if (templateService.exists(templateName)) {
      templateService.delete(templateName);
    }
    // create
    Map<String, RecordType> properties = new HashMap<String, RecordType>();
    properties.put("field", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    String templatePattern = templateName + "-*";
    try {
      templateService.create(templateName, templatePattern, templateName, properties);
      Thread.sleep(5 * 1000);
      System.out.println("create template=" + templateName);
      Assert.assertTrue(templateService.exists(templateName));

      Thread.sleep(5 * 1000);
      List<IndexTemplateMetaData> templates = templateService.get(templateName);
      ImmutableOpenMap<String, CompressedXContent> immutableOpenMap = templates.get(0).getMappings();
      String x = ((CompressedXContent) (immutableOpenMap.get(templateName))).string();
      LinkedHashMap map = JsonUtil.formJson(LinkedHashMap.class, x);
      LinkedHashMap resMap = (LinkedHashMap) (((LinkedHashMap) map.get(templateName)).get("properties"));
      Assert.assertEquals(DataType.TEXT.toValue(), ((LinkedHashMap) resMap.get("field")).get("type"));
      Assert.assertEquals(AnalyzerType.KEYWORD.toValue(), ((LinkedHashMap) resMap.get("field")).get("analyzer"));
      Assert.assertEquals(StoreType.TRUE.toValue(), ((LinkedHashMap) resMap.get("field")).get("store"));
    } finally {
      // delete
      if (templateService.exists(templateName)) {
        templateService.delete(templateName);
      }
    }
  }

}
