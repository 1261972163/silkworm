package com.jengine.data.elasticsearch.mydataframe;


/**
 * @author nouuid
 * @date 8/9/2016
 * @description
 */
public class Table {
  private String databaseName; // databaseName name
  private String tableName;
  private String tag;
  private TableStructure tableStructure;
  public String getDatabaseName() {
    return databaseName;
  }

  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public TableStructure getTableStructure() {
    return tableStructure;
  }

  public void setTableStructure(TableStructure tableStructure) {
    this.tableStructure = tableStructure;
  }

}
