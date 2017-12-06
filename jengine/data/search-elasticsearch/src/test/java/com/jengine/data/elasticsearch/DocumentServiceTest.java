package com.jengine.data.elasticsearch;

import com.jengine.data.elasticsearch.mydataframe.Doc;
import com.jengine.data.elasticsearch.mydataframe.PageShow;

import com.jengine.transport.serialize.json.JsonUtil;

import junit.framework.Assert;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author nouuid
 * @date 8/10/2016
 * @description
 */
public class DocumentServiceTest extends EsClientTest {

  @Test
  public void add() throws Exception {
    String fieldName = "field";
    // add with id, with routing
    try {
      String fieldValue = UUID.randomUUID().toString();
      Map<String, String> inData = new HashMap<String, String>();
      inData.put(fieldName, fieldValue);
      String routing = fieldValue;
      documentService.add(new Doc(indexName, type, "1", routing, inData));
      System.out.println("insert data=[" + fieldValue + "]");
      Thread.sleep(5 * 1000);
      String docStr = documentService.get(indexName, type, "1", routing);
      Map<String, String> docMap = JsonUtil.formJson(Map.class, docStr);
      Assert.assertEquals(fieldValue, docMap.get(fieldName));
    } finally {
      after();
    }

    // add with id, without routing
    try {
      createIndex();
      String fieldValue = UUID.randomUUID().toString();
      Map<String, String> inData = new HashMap<String, String>();
      inData.put("field", fieldValue);
      documentService.add(new Doc(indexName, type, "1", null, inData));
      System.out.println("insert data=[" + fieldValue + "]");
      Thread.sleep(5 * 1000);
      String docStr = documentService.get(indexName, type, "1", null);
      Map<String, String> docMap = JsonUtil.formJson(Map.class, docStr);
      Assert.assertEquals(fieldValue, docMap.get("field"));
    } finally {
      after();
    }

    // add without id, with routing
    try {
      createIndex();
      String fieldValue = UUID.randomUUID().toString();
      Map<String, String> inData = new HashMap<String, String>();
      inData.put(fieldName, fieldValue);
      String id = null;
      String routing = fieldValue;
      documentService.add(new Doc(indexName, type, id, routing, inData), false);
      System.out.println("insert data=[" + fieldValue + "]");
      Thread.sleep(5 * 1000);

      List<String> indices = new ArrayList<String>();
      List<String> types = new ArrayList<String>();
      indices.add(indexName);
      types.add(type);
      QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery(fieldName, fieldValue);
      PageShow<String> pageShow = documentService.query(indices, types, routing, queryBuilder, 0, 5, null, null);
      List<String> docStrs = pageShow.getList();
      Assert.assertEquals(1, docStrs.size());
      Map<String, String> docMap = JsonUtil.formJson(Map.class, docStrs.get(0));
      Assert.assertEquals(fieldValue, docMap.get(fieldName));
    } finally {
      after();
    }

    // add without id, without routing
    try {
      createIndex();
      String fieldValue = UUID.randomUUID().toString();
      Map<String, String> inData = new HashMap<String, String>();
      inData.put("field", fieldValue);
      String id = null;
      String routing = null;
      documentService.add(new Doc(indexName, type, id, routing, inData), false);
      System.out.println("insert data=[" + fieldValue + "]");
      Thread.sleep(5 * 1000);

      List<String> indices = new ArrayList<String>();
      List<String> types = new ArrayList<String>();
      indices.add(indexName);
      types.add(type);
      QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery(fieldName, fieldValue);
      PageShow<String> pageShow = documentService.query(indices, types, routing, queryBuilder, 0, 5, null, null);
      List<String> docStrs = pageShow.getList();
      Assert.assertEquals(1, docStrs.size());
      Map<String, String> docMap = JsonUtil.formJson(Map.class, docStrs.get(0));
      Assert.assertEquals(fieldValue, docMap.get(fieldName));
    } finally {
      after();
    }
  }

  @Test
  public void addList() throws Exception {
    String fieldName = "field";
    // add with id, with routing
    try {
      String fieldValue1 = UUID.randomUUID().toString();
      Map<String, String> inData1 = new HashMap<String, String>();
      inData1.put(fieldName, fieldValue1);
      String routing1 = fieldValue1;

      String fieldValue2 = UUID.randomUUID().toString();
      Map<String, String> inData2 = new HashMap<String, String>();
      inData2.put(fieldName, fieldValue2);
      String routing2 = fieldValue2;

      List<Doc> docs = new ArrayList<Doc>();
      docs.add(new Doc(indexName, type, "1", routing1, inData1));
      docs.add(new Doc(indexName, type, "2", routing2, inData2));

      documentService.add(docs);
      System.out.println("insert data=[" + fieldValue1 + "]");
      System.out.println("insert data=[" + fieldValue2 + "]");
      Thread.sleep(5 * 1000);
      String docStr1 = documentService.get(indexName, type, "1", routing1);
      Map<String, String> docMap1 = JsonUtil.formJson(Map.class, docStr1);
      Assert.assertEquals(fieldValue1, docMap1.get(fieldName));

      String docStr2 = documentService.get(indexName, type, "2", routing2);
      Map<String, String> docMap2 = JsonUtil.formJson(Map.class, docStr2);
      Assert.assertEquals(fieldValue2, docMap2.get(fieldName));
    } finally {
      after();
    }

    // add with id, without routing
    try {
      createIndex();
      String fieldValue1 = UUID.randomUUID().toString();
      Map<String, String> inData1 = new HashMap<String, String>();
      inData1.put(fieldName, fieldValue1);

      String fieldValue2 = UUID.randomUUID().toString();
      Map<String, String> inData2 = new HashMap<String, String>();
      inData2.put(fieldName, fieldValue2);

      List<Doc> docs = new ArrayList<Doc>();
      docs.add(new Doc(indexName, type, "1", null, inData1));
      docs.add(new Doc(indexName, type, "2", null, inData2));

      documentService.add(docs);
      System.out.println("insert data=[" + fieldValue1 + "]");
      System.out.println("insert data=[" + fieldValue2 + "]");
      Thread.sleep(5 * 1000);
      String docStr1 = documentService.get(indexName, type, "1", null);
      Map<String, String> docMap1 = JsonUtil.formJson(Map.class, docStr1);
      Assert.assertEquals(fieldValue1, docMap1.get(fieldName));

      String docStr2 = documentService.get(indexName, type, "2", null);
      Map<String, String> docMap2 = JsonUtil.formJson(Map.class, docStr2);
      Assert.assertEquals(fieldValue2, docMap2.get(fieldName));
    } finally {
      after();
    }

    // add without id, with routing
    try {
      createIndex();
      String fieldValue1 = UUID.randomUUID().toString();
      Map<String, String> inData1 = new HashMap<String, String>();
      inData1.put(fieldName, fieldValue1);
      String routing1 = fieldValue1;

      String fieldValue2 = UUID.randomUUID().toString();
      Map<String, String> inData2 = new HashMap<String, String>();
      inData2.put(fieldName, fieldValue2);
      String routing2 = fieldValue2;

      List<Doc> docs = new ArrayList<Doc>();
      docs.add(new Doc(indexName, type, null, routing1, inData1));
      docs.add(new Doc(indexName, type, null, routing2, inData2));

      documentService.add(docs, false);
      System.out.println("insert data=[" + fieldValue1 + "]");
      System.out.println("insert data=[" + fieldValue2 + "]");
      Thread.sleep(5 * 1000);

      List<String> indices = new ArrayList<String>();
      List<String> types = new ArrayList<String>();
      indices.add(indexName);
      types.add(type);
      try {
        QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery(fieldName, fieldValue1);
        PageShow<String> pageShow = documentService.query(indices, types, routing1, queryBuilder, 0, 5, null, null);
        List<String> docStrs = pageShow.getList();
        Assert.assertEquals(1, docStrs.size());
        Map<String, String> docMap = JsonUtil.formJson(Map.class, docStrs.get(0));
        Assert.assertEquals(fieldValue1, docMap.get(fieldName));
      } finally {

      }

      try {
        QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery(fieldName, fieldValue2);
        PageShow<String> pageShow = documentService.query(indices, types, routing2, queryBuilder, 0, 5, null, null);
        List<String> docStrs = pageShow.getList();
        Assert.assertEquals(1, docStrs.size());
        Map<String, String> docMap = JsonUtil.formJson(Map.class, docStrs.get(0));
        Assert.assertEquals(fieldValue2, docMap.get(fieldName));
      } finally {

      }
    } finally {
      after();
    }

    // add without id, without routing
    try {
      createIndex();
      String fieldValue1 = UUID.randomUUID().toString();
      Map<String, String> inData1 = new HashMap<String, String>();
      inData1.put(fieldName, fieldValue1);

      String fieldValue2 = UUID.randomUUID().toString();
      Map<String, String> inData2 = new HashMap<String, String>();
      inData2.put(fieldName, fieldValue2);

      List<Doc> docs = new ArrayList<Doc>();
      docs.add(new Doc(indexName, type, null, null, inData1));
      docs.add(new Doc(indexName, type, null, null, inData2));

      documentService.add(docs, false);
      System.out.println("insert data=[" + fieldValue1 + "]");
      System.out.println("insert data=[" + fieldValue2 + "]");
      Thread.sleep(5 * 1000);

      List<String> indices = new ArrayList<String>();
      List<String> types = new ArrayList<String>();
      indices.add(indexName);
      types.add(type);
      try {
        QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery(fieldName, fieldValue1);
        PageShow<String> pageShow = documentService.query(indices, types, null, queryBuilder, 0, 5, null, null);
        List<String> docStrs = pageShow.getList();
        Assert.assertEquals(1, docStrs.size());
        Map<String, String> docMap = JsonUtil.formJson(Map.class, docStrs.get(0));
        Assert.assertEquals(fieldValue1, docMap.get(fieldName));
      } finally {

      }

      try {
        QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery(fieldName, fieldValue2);
        PageShow<String> pageShow = documentService.query(indices, types, null, queryBuilder, 0, 5, null, null);
        List<String> docStrs = pageShow.getList();
        Assert.assertEquals(1, docStrs.size());
        Map<String, String> docMap = JsonUtil.formJson(Map.class, docStrs.get(0));
        Assert.assertEquals(fieldValue2, docMap.get(fieldName));
      } finally {

      }


    } finally {
      after();
    }
  }

  @Test
  public void delete() throws Exception {
    String fieldName = "field";
    // delete with id, with routing
    try {
      String fieldValue = UUID.randomUUID().toString();
      Map<String, String> inData = new HashMap<String, String>();
      inData.put(fieldName, fieldValue);
      String routing = fieldValue;
      documentService.add(new Doc(indexName, type, "1", routing, inData));
      System.out.println("insert data=[" + fieldValue + "]");
      Thread.sleep(5 * 1000);
      String docStr = documentService.get(indexName, type, "1", routing);
      Map<String, String> docMap = JsonUtil.formJson(Map.class, docStr);
      Assert.assertEquals(fieldValue, docMap.get(fieldName));

      documentService.delete(indexName, type, "1", routing);
      Thread.sleep(2000);
      String docStr2 = documentService.get(indexName, type, "1", routing);
      Assert.assertEquals(null, docStr2);
    } finally {
      after();
    }

    // delete with id, without routing
    try {
      createIndex();
      String fieldValue = UUID.randomUUID().toString();
      Map<String, String> inData = new HashMap<String, String>();
      inData.put(fieldName, fieldValue);
      documentService.add(new Doc(indexName, type, "1", null, inData));
      System.out.println("insert data=[" + fieldValue + "]");
      Thread.sleep(5 * 1000);
      String docStr = documentService.get(indexName, type, "1", null);
      Map<String, String> docMap = JsonUtil.formJson(Map.class, docStr);
      Assert.assertEquals(fieldValue, docMap.get(fieldName));

      documentService.delete(indexName, type, "1", null);
      Thread.sleep(2000);
      String docStr2 = documentService.get(indexName, type, "1", null);
      Assert.assertEquals(null, docStr2);
    } finally {
      after();
    }
  }

  @Test
  public void update() throws Exception {
    String fieldName = "field";
    // update with id, with routing
    try {
      String fieldValue = UUID.randomUUID().toString();
      Map<String, String> inData = new HashMap<String, String>();
      inData.put(fieldName, fieldValue);
      String routing = fieldValue;
      documentService.add(new Doc(indexName, type, "1", routing, inData));
      System.out.println("insert data=[" + fieldValue + "]");
      Thread.sleep(5 * 1000);
      String docStr = documentService.get(indexName, type, "1", routing);
      Map<String, String> docMap = JsonUtil.formJson(Map.class, docStr);
      Assert.assertEquals(fieldValue, docMap.get(fieldName));

      String newFieldValue = fieldValue + "x";
      Map<String, String> updates = new HashMap<String, String>();
      updates.put(fieldName, newFieldValue);
      documentService.update(indexName, type, "1", updates, routing);
      Thread.sleep(2000);
      String docStr2 = documentService.get(indexName, type, "1", routing);
      Map<String, String> docMap2 = JsonUtil.formJson(Map.class, docStr2);
      Assert.assertEquals(newFieldValue, docMap2.get(fieldName));
    } finally {
      after();
    }

    // update with id, without routing
    try {
      createIndex();
      String fieldValue = UUID.randomUUID().toString();
      Map<String, String> inData = new HashMap<String, String>();
      inData.put(fieldName, fieldValue);
      String routing = null;
      documentService.add(new Doc(indexName, type, "1", routing, inData));
      System.out.println("insert data=[" + fieldValue + "]");
      Thread.sleep(5 * 1000);
      String docStr = documentService.get(indexName, type, "1", routing);
      Map<String, String> docMap = JsonUtil.formJson(Map.class, docStr);
      Assert.assertEquals(fieldValue, docMap.get(fieldName));

      String newFieldValue = fieldValue + "x";
      Map<String, String> updates = new HashMap<String, String>();
      updates.put(fieldName, newFieldValue);
      documentService.update(indexName, type, "1", updates, routing);
      Thread.sleep(2000);
      String docStr2 = documentService.get(indexName, type, "1", routing);
      Map<String, String> docMap2 = JsonUtil.formJson(Map.class, docStr2);
      Assert.assertEquals(newFieldValue, docMap2.get(fieldName));
    } finally {
      after();
    }
  }

  @Test
  public void get() throws Exception {
    String fieldName = "field";
    // get with id, with routing
    try {
      String fieldValue = UUID.randomUUID().toString();
      Map<String, String> inData = new HashMap<String, String>();
      inData.put(fieldName, fieldValue);
      String routing = fieldValue;
      documentService.add(new Doc(indexName, type, "1", routing, inData));
      System.out.println("insert data=[" + fieldValue + "]");
      Thread.sleep(5 * 1000);
      String docStr = documentService.get(indexName, type, "1", routing);
      Map<String, String> docMap = JsonUtil.formJson(Map.class, docStr);
      Assert.assertEquals(fieldValue, docMap.get(fieldName));
    } finally {
      after();
    }

    // get with id, without routing
    try {
      createIndex();
      String fieldValue = UUID.randomUUID().toString();
      Map<String, String> inData = new HashMap<String, String>();
      inData.put(fieldName, fieldValue);
      String routing = null;
      documentService.add(new Doc(indexName, type, "1", routing, inData));
      System.out.println("insert data=[" + fieldValue + "]");
      Thread.sleep(5 * 1000);
      String docStr = documentService.get(indexName, type, "1", routing);
      Map<String, String> docMap = JsonUtil.formJson(Map.class, docStr);
      Assert.assertEquals(fieldValue, docMap.get(fieldName));
    } finally {
      after();
    }
  }

  @Test
  public void query() throws Exception {
    String fieldName = "field";
    // query with routing
    try {
      String fieldValue = UUID.randomUUID().toString();
      Map<String, String> inData = new HashMap<String, String>();
      inData.put(fieldName, fieldValue);
      String routing = fieldValue;
      documentService.add(new Doc(indexName, type, "1", routing, inData));
      System.out.println("insert data=[" + fieldValue + "]");
      Thread.sleep(5 * 1000);

      List<String> indices = new ArrayList<String>();
      List<String> types = new ArrayList<String>();
      indices.add(indexName);
      types.add(type);
      QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery(fieldName, fieldValue);
      PageShow<String> pageShow = documentService.query(indices, types, routing, queryBuilder, 0, 5, null, null);
      List<String> docStrs = pageShow.getList();
      Assert.assertEquals(1, docStrs.size());
      Map<String, String> docMap = JsonUtil.formJson(Map.class, docStrs.get(0));
      Assert.assertEquals(fieldValue, docMap.get(fieldName));
    } finally {
      after();
    }

    // query without routing
    try {
      String fieldValue = UUID.randomUUID().toString();
      Map<String, String> inData = new HashMap<String, String>();
      inData.put(fieldName, fieldValue);
      String routing = null;
      documentService.add(new Doc(indexName, type, "1", routing, inData));
      System.out.println("insert data=[" + fieldValue + "]");
      Thread.sleep(5 * 1000);

      List<String> indices = new ArrayList<String>();
      List<String> types = new ArrayList<String>();
      indices.add(indexName);
      types.add(type);
      QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery(fieldName, fieldValue);
      PageShow<String> pageShow = documentService.query(indices, types, routing, queryBuilder, 0, 5, null, null);
      List<String> docStrs = pageShow.getList();
      Assert.assertEquals(1, docStrs.size());
      Map<String, String> docMap = JsonUtil.formJson(Map.class, docStrs.get(0));
      Assert.assertEquals(fieldValue, docMap.get(fieldName));
    } finally {
      after();
    }
  }


//    @Test
//    public void query() throws Exception {
//        // insert document
//        String fieldName = "field";
//        String fieldValue = UUID.randomUUID().toString();
//        String routing = null;
//        Map<String, String> inData = new HashMap<String, String>();
//        inData.put(fieldName, fieldValue);
//        documentService.insert(new Doc(indexName, type, null, inData));
//        System.out.println("insert data=[" + fieldValue + "]");
//        Thread.sleep(5 * 1000);
//
//        // query document docId
//        JestResult jestResult = documentService.query(indexName, indexName, queryBody(fieldName, fieldValue), routing);
//        String actualField1 = null;
//        if (jestResult != null) {
//            List<HashMap> data = (ArrayList<HashMap>) jestResult.getSourceAsObjectList(HashMap.class);
//            actualField1 = (String) (data.get(0).get(fieldName));
//        }
//        System.out.println("query document, field1=" + actualField1);
//        Assert.assertEquals(fieldValue, actualField1);
//    }
//
//    @Test
//    public void queryWithRouting() throws Exception {
//        // insert document
//        String fieldName = "field";
//        String fieldValue = UUID.randomUUID().toString();
//        String routing = "test";
//        Map<String, String> inData = new HashMap<String, String>();
//        inData.put(fieldName, fieldValue);
//        documentService.insert(new Doc(indexName, type, routing, inData));
//        System.out.println("insert data=[" + fieldValue + "]");
//        Thread.sleep(5 * 1000);
//
//        // query document docId
//        JestResult jestResult = documentService.query(indexName, indexName, queryBody(fieldName, fieldValue), routing);
//        String actualField1 = null;
//        if (jestResult != null) {
//            List<HashMap> data = (ArrayList<HashMap>) jestResult.getSourceAsObjectList(HashMap.class);
//            actualField1 = (String) (data.get(0).get(fieldName));
//        }
//        System.out.println("query document, " + fieldName + "=" + actualField1);
//        Assert.assertEquals(fieldValue, actualField1);
//    }
//
//    private String queryBody(String fieldName, String value) {
//        String queryBody = "{\n" +
//                "  \"query\": {\n" +
//                "    \"bool\": {\n" +
//                "      \"must\": {\n" +
//                "        \"match\": {\n" +
//                "          \"" + fieldName + "\": {\n" +
//                "            \"query\": \"" + value + "\",\n" +
//                "            \"type\": \"phrase\"\n" +
//                "          }\n" +
//                "        }\n" +
//                "      }\n" +
//                "    }\n" +
//                "  }\n" +
//                "}";
//        return queryBody;
//    }

}
