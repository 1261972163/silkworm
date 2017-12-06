package com.jengine.data.elasticsearch.tools;


import com.best.bingo.common.dataframe.Table;
import com.best.bingo.common.dataframe.schema.TableStructure;

/**
 * @author nouuid
 * @date 8/9/2016
 * @description
 */
public class TableBuilder {
  private String databaseName; // databaseName name
  private String tableName;
  private String tag;
  private TableStructure tableStructure;

  public TableBuilder(String databaseName, String tableName, String tag, TableStructure tableStructure) {
    this.databaseName = databaseName;
    this.tableName = tableName;
    this.tag = tag;
    this.tableStructure = tableStructure;
  }

  public Table build() {
    Table table = new Table();
    table.setDatabaseName(databaseName);
    table.setTableName(tableName);
    table.setTag(tag);
    table.setTableStructure(tableStructure);
    return table;
  }

}
