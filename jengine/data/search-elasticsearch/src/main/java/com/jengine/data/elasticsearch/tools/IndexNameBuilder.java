package com.jengine.data.elasticsearch.tools;

import com.best.bingo.common.exception.BingoException;
import com.best.bingo.common.utils.StringUtils;

/**
 * @author bl07637
 * @date 8/11/2016
 * @description
 */
public class IndexNameBuilder {

  private String databaseName;
  private String tableName;
  private String tag;
  private String blockValue;

  public IndexNameBuilder() {

  }

  public IndexNameBuilder(String databaseName, String tableName) {
    if (StringUtils.isBlank(databaseName)) {
      throw new BingoException("database name is blank");
    }
    if (StringUtils.isBlank(tableName)) {
      throw new BingoException("table name is blank.");
    }
    this.databaseName = databaseName;
    this.tableName = tableName;
  }

  public IndexNameBuilder setDatabase(String databaseName) {
    this.databaseName = databaseName;
    return this;
  }

  public IndexNameBuilder setTable(String tableName) {
    this.tableName = tableName;
    return this;
  }

  public IndexNameBuilder setTag(String tag) {
    this.tag = tag;
    return this;
  }

  public IndexNameBuilder setBlock(String blockValue) {
    this.blockValue = blockValue;
    return this;
  }

  public String build() {
    if (StringUtils.isBlank(databaseName) || StringUtils.isBlank(tableName)) {
      throw new BingoException("database is blank or table is blank.");
    }
    String indexName = databaseName + "-" + tableName;
    if (StringUtils.isNotBlank(tag)) {
      indexName = tag + "-" + indexName;
    }
    if (StringUtils.isNotBlank(blockValue)) {
      indexName = indexName + "-" + blockValue;
    }
    return indexName;
  }

  public String buildMappingIndexName() {
    if (StringUtils.isBlank(databaseName) || StringUtils.isBlank(tableName)) {
      throw new BingoException("database is blank or table is blank.");
    }
    String indexName = databaseName + "-" + tableName;
    if (StringUtils.isNotBlank(tag)) {
      indexName = tag + "-" + indexName;
    }
    return "m." + indexName;
  }

//  public String build3() {
//    if (StringUtils.isBlank(blockValue)) {
//      return databaseName + "-" + tableName;
//    }
//    return databaseName + "-" + tableName + "-" + blockValue;
//  }
}