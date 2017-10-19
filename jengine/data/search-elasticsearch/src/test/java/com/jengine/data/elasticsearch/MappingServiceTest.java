package com.jengine.data.elasticsearch;

import com.jengine.data.elasticsearch.mydataframe.AnalyzerType;
import com.jengine.data.elasticsearch.mydataframe.DataType;
import com.jengine.data.elasticsearch.mydataframe.RecordType;
import com.jengine.data.elasticsearch.mydataframe.StoreType;
import com.jengine.data.elasticsearch.mydataframe.TableStructure;
import com.jengine.data.elasticsearch.tools.RecordTypeBuilder;

import junit.framework.Assert;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author bl07637
 * @date 8/9/2016
 * @description
 */
public class MappingServiceTest extends EsClientTest {

  @Test
  public void create() throws Exception {
    Map<String, RecordType> properties = new HashMap<String, RecordType>();
    properties.put("field1", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    TableStructure dataStructure = new TableStructure();
    dataStructure.setProperties(properties);
    mappingService.create(indexName, type, dataStructure);
    Thread.sleep(10 * 1000);
    System.out.println("create mapping=" + indexName);
    Assert.assertTrue(mappingService.exists(indexName, type));
  }

  @Test
  public void create2() throws Exception {
    // create without source
    Map<String, RecordType> properties = new HashMap<String, RecordType>();
    properties.put("field1", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());

    Map<String, String> _source = new HashMap<String, String>();
    _source.put("enabled", "false");

    TableStructure dataStructure = new TableStructure();
    dataStructure.set_source(_source);
    dataStructure.setProperties(properties);
    mappingService.create(indexName, type, dataStructure);
    Thread.sleep(10 * 1000);
    System.out.println("create mapping=" + indexName);

    Assert.assertTrue(mappingService.exists(indexName, type));
  }

  @Test
  public void create3() throws Exception {
    Map<String, RecordType> properties = new HashMap<String, RecordType>();
    properties.put("id", new RecordTypeBuilder().setDateType(DataType.INTEGER).setStoreType(StoreType.TRUE).build());
    properties.put("id", new RecordTypeBuilder().setDateType(DataType.INTEGER).setStoreType(StoreType.TRUE).build());
    properties.put("VERSION", new RecordTypeBuilder().setDateType(DataType.INTEGER).setStoreType(StoreType.TRUE).build());
    properties.put("createTime", new RecordTypeBuilder().setDateType(DataType.DATE).setStoreType(StoreType.TRUE).build());
    properties.put("lastUpdateTime", new RecordTypeBuilder().setDateType(DataType.DATE).setStoreType(StoreType.TRUE).build());
    properties.put("creater", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("updater", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("udf1", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("udf2", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("udf3", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("udf4", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("discountAmount", new RecordTypeBuilder().setDateType(DataType.FLOAT).setStoreType(StoreType.TRUE).build());
    properties.put("feebackPoints", new RecordTypeBuilder().setDateType(DataType.INTEGER).setStoreType(StoreType.TRUE).build());
    properties.put("deliverStatus", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("telephone", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("province", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("mobilePhone", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("freight", new RecordTypeBuilder().setDateType(DataType.FLOAT).setStoreType(StoreType.TRUE).build());
    properties.put("orderStatus", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("receiver", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("payStatus", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("paymentMode", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("distributMode", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("buyerRemark", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("city", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("orderCode", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("street", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("memberId", new RecordTypeBuilder().setDateType(DataType.INTEGER).setStoreType(StoreType.TRUE).build());
    properties.put("deliverTime", new RecordTypeBuilder().setDateType(DataType.DATE).setStoreType(StoreType.TRUE).build());
    properties.put("actualAmount", new RecordTypeBuilder().setDateType(DataType.FLOAT).setStoreType(StoreType.TRUE).build());
    properties.put("skusAmount", new RecordTypeBuilder().setDateType(DataType.FLOAT).setStoreType(StoreType.TRUE).build());
    properties.put("totalAmount", new RecordTypeBuilder().setDateType(DataType.FLOAT).setStoreType(StoreType.TRUE).build());
    properties.put("export", new RecordTypeBuilder().setDateType(DataType.SHORT).setStoreType(StoreType.TRUE).build());
    properties.put("county", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("township", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("pointDeductAmount", new RecordTypeBuilder().setDateType(DataType.FLOAT).setStoreType(StoreType.TRUE).build());
    properties.put("couponDeductAmount", new RecordTypeBuilder().setDateType(DataType.FLOAT).setStoreType(StoreType.TRUE).build());
    properties.put("activeDeductAmount", new RecordTypeBuilder().setDateType(DataType.FLOAT).setStoreType(StoreType.TRUE).build());
    properties.put("sellerRemark", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("payTime", new RecordTypeBuilder().setDateType(DataType.DATE).setStoreType(StoreType.TRUE).build());
    properties.put("memberName", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("memberType", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("nameCn", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("orderSource", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("areaId", new RecordTypeBuilder().setDateType(DataType.INTEGER).setStoreType(StoreType.TRUE).build());
    properties.put("areaCode", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("areaName", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("availableDeliverTime", new RecordTypeBuilder().setDateType(DataType.DATE).setStoreType(StoreType.TRUE).build());
    properties.put("pushStatus", new RecordTypeBuilder().setDateType(DataType.SHORT).setStoreType(StoreType.TRUE).build());
    properties.put("pushErrorMessage", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("pushUser", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("pushTime", new RecordTypeBuilder().setDateType(DataType.DATE).setStoreType(StoreType.TRUE).build());
    properties.put("actualReturnPrice", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("collectionPrice", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("paidReturnPrice", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("returnPrice", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("totalPrice", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("orderProgress", new RecordTypeBuilder().setDateType(DataType.INTEGER).setStoreType(StoreType.TRUE).build());
    properties.put("progressDeliveringTime", new RecordTypeBuilder().setDateType(DataType.DATE).setStoreType(StoreType.TRUE).build());
    properties.put("deliverConfirm", new RecordTypeBuilder().setDateType(DataType.SHORT).setStoreType(StoreType.TRUE).build());
    properties.put("deliverConfirmTime", new RecordTypeBuilder().setDateType(DataType.DATE).setStoreType(StoreType.TRUE).build());
    properties.put("deliverName", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("deliverMobile", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("deliverCarType", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("deliverCarNumber", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("franchiseeName", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("salesManName", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    properties.put("finishTime", new RecordTypeBuilder().setDateType(DataType.DATE).setStoreType(StoreType.TRUE).build());

    TableStructure dataStructure = new TableStructure();
    dataStructure.setProperties(properties);
    mappingService.create(indexName, type, dataStructure);
    Thread.sleep(10 * 1000);
    System.out.println("create mapping=" + indexName);

    Assert.assertTrue(mappingService.exists(indexName, type));
  }

  @Test
  public void exists() throws Exception {
    Map<String, RecordType> properties = new HashMap<String, RecordType>();
    properties.put("field1", new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    TableStructure dataStructure = new TableStructure();
    dataStructure.setProperties(properties);
    mappingService.create(indexName, type, dataStructure);
    Thread.sleep(10 * 1000);
    System.out.println("create mapping=" + indexName);
    Assert.assertTrue(mappingService.exists(indexName, type));
  }

  @Test
  public void get() throws Exception {
    String fieldName = "field1";
    // put mapping
    Map<String, RecordType> properties = new HashMap<String, RecordType>();
    properties.put(fieldName, new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    TableStructure dataStructure = new TableStructure();
    dataStructure.setProperties(properties);
    mappingService.create(indexName, type, dataStructure);
    Thread.sleep(10 * 1000);
    System.out.println("create mapping=" + indexName);
    Assert.assertTrue(mappingService.exists(indexName, type));

    // get mapping
    LinkedHashMap mapping = mappingService.get(indexName, type);
    LinkedHashMap mapping2 = (LinkedHashMap) mapping.get(fieldName);
    Assert.assertEquals(DataType.TEXT.toValue(), mapping2.get("type"));
    Assert.assertEquals(StoreType.TRUE.toValue(), mapping2.get("store") + "");
    Assert.assertEquals(AnalyzerType.KEYWORD.toValue(), mapping2.get("analyzer"));
  }

  @Test
  public void update() throws Exception {
    String fieldName = "field1";
    // put mapping
    Map<String, RecordType> properties = new HashMap<String, RecordType>();
    properties.put(fieldName, new RecordTypeBuilder().setDateType(DataType.TEXT).setAnalyzerType(AnalyzerType.KEYWORD).setStoreType(StoreType.TRUE).build());
    TableStructure dataStructure = new TableStructure();
    dataStructure.setProperties(properties);
    mappingService.create(indexName, type, dataStructure);
    Thread.sleep(10 * 1000);
    System.out.println("create mapping=" + indexName);
    Assert.assertTrue(mappingService.exists(indexName, type));

    // get mapping
    LinkedHashMap mapping = mappingService.get(indexName, type);
    LinkedHashMap mapping2 = (LinkedHashMap) mapping.get(fieldName);
    Assert.assertEquals(DataType.TEXT.toValue(), mapping2.get("type"));
    Assert.assertEquals(StoreType.TRUE.toValue(), mapping2.get("store"));
    Assert.assertEquals(AnalyzerType.KEYWORD.toValue(), mapping2.get("analyzer"));

  }
}
