package com.jengine.data.elasticsearch;

import com.jengine.data.elasticsearch.mydataframe.EsConfig;

import junit.framework.Assert;

import org.elasticsearch.client.transport.TransportClient;
import org.junit.After;
import org.junit.Before;

/**
 * content
 *
 * @author bl07637
 * @date 6/12/2017
 * @since 0.1.0
 */
public abstract class EsClientTest {
  protected static String indexName = "test1";
  protected static String type = "test1type1";
  protected TransportClient client;
  protected DocumentService documentService;
  protected IndexService indexService;
  protected MappingService mappingService;
  protected TemplateService templateService;

  @Before
  public void before() throws Exception {
    initES();
    createIndex();
//        createMapping();
    Thread.sleep(5 * 1000);
  }

  private void initES() throws Exception {
    EsConfig esConfig = new EsConfig();
    esConfig.setCluster("es5-localtest");
    esConfig.setUrl("10.45.11.84:3100");
    client = new EsClientFactory(esConfig).getClient();
    indexService = new IndexService(client);
    mappingService = new MappingService(client);
    documentService = new DocumentService(client);
    templateService = new TemplateService(client);
  }

  public void createIndex() throws Exception {
    if (indexService.exists(indexName)) {
      indexService.delete(indexName);
      Thread.sleep(5 * 1000);
    }
    indexService.create(indexName);
    System.out.println("create index=" + indexName);
    Thread.sleep(5 * 1000);
    Assert.assertTrue(indexService.exists(indexName));
  }

  @After
  public void after() throws Exception {
    if (indexService.exists(indexName)) {
      indexService.delete(indexName);
    }
  }
}
