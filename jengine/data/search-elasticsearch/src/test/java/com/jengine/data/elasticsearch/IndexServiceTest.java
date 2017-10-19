package com.jengine.data.elasticsearch;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bl07637
 * @date 8/9/2016
 * @description
 */
public class IndexServiceTest extends EsClientTest {

  @Test
  public void create() throws Exception {
    while (indexService.exists(indexName)) {
      indexService.delete(indexName);
      Thread.sleep(1 * 1000);
    }

    indexService.create(indexName);
    Assert.assertTrue(indexService.exists(indexName));

    indexService.delete(indexName);
    Thread.sleep(1 * 1000);
    Assert.assertFalse(indexService.exists(indexName));
  }

  @Test
  public void delete() throws Exception {
    while (indexService.exists(indexName)) {
      indexService.delete(indexName);
      Thread.sleep(1 * 1000);
    }

    indexService.create(indexName);
    Assert.assertTrue(indexService.exists(indexName));

    indexService.delete(indexName);
    Thread.sleep(1 * 1000);
    Assert.assertFalse(indexService.exists(indexName));
  }

  @Test
  public void exists() throws Exception {
    while (indexService.exists(indexName)) {
      indexService.delete(indexName);
      Thread.sleep(1 * 1000);
    }

    indexService.create(indexName);
    Assert.assertTrue(indexService.exists(indexName));

    indexService.delete(indexName);
    Thread.sleep(1 * 1000);
    Assert.assertFalse(indexService.exists(indexName));
  }

  @Test
  public void existsList() throws Exception {
    String indexName1 = "test1";
    String indexName2 = "test2";

    List<String> indexNames = new ArrayList<String>();
    indexNames.add(indexName1);
    indexNames.add(indexName2);

    for (String indexName : indexNames) {
      if (indexService.exists(indexName)) {
        indexService.delete(indexName);
      }
    }
    Thread.sleep(1 * 1000);
    Assert.assertFalse(indexService.exists(indexNames));

    try {
      for (String indexName : indexNames) {
        indexService.create(indexName);
      }
      Thread.sleep(1 * 1000);
      Assert.assertTrue(indexService.exists(indexNames));
    } finally {
      if (indexService.exists(indexName2)) {
        indexService.delete(indexName2);
      }
      Thread.sleep(3 * 1000);
      Assert.assertFalse(indexService.exists(indexName2));
      Assert.assertFalse(indexService.exists(indexNames));
    }
  }

  @Test
  public void list() throws Exception {
    String indexName1 = "test1";
    String indexName2 = "test2";

    List<String> indexNames = indexService.list();
    if (indexNames != null && indexNames.size() > 0) {
      for (String indexName : indexNames) {
        if (indexService.exists(indexName)) {
          indexService.delete(indexName);
        }
      }
    }
    Thread.sleep(1 * 1000);

    indexNames = new ArrayList<String>();
    indexNames.add(indexName1);
    indexNames.add(indexName2);
    try {
      for (String indexName : indexNames) {
        indexService.create(indexName);
      }
      Thread.sleep(1 * 1000);
      List<String> tmpList = indexService.list();
      if (tmpList.size() == indexNames.size()) {
        if ((tmpList.get(0) == indexNames.get(0) && tmpList.get(1) == indexNames.get(1))
            || (tmpList.get(0) == indexNames.get(1) && tmpList.get(1) == indexNames.get(0))) {
          Assert.assertTrue(true);
        } else {
          Assert.assertFalse(false);
        }
      }
      Assert.assertFalse(false);
    } finally {
      if (indexNames != null && indexNames.size() > 0) {
        for (String indexName : indexNames) {
          if (indexService.exists(indexName)) {
            indexService.delete(indexName);
          }
        }
      }
    }

  }


}
