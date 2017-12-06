package com.jengine.data.elasticsearch.tools;

import com.jengine.data.elasticsearch.mydataframe.RecordType;
import com.jengine.data.elasticsearch.mydataframe.TableStructure;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nouuid
 * @date 8/5/2016
 * @description
 */
public class TableStructureBuilder {
  private Map<String, RecordType> properties; // <field name, field config>

  public TableStructureBuilder(Map<String, RecordType> properties) {
    this.properties = properties;
  }

  public TableStructure build() {
    Map<String, String> _source = new HashMap<String, String>();
    _source.put("enabled", "true");
    TableStructure tableStructure = new TableStructure();
    tableStructure.setProperties(properties);
    tableStructure.set_source(_source);
    return tableStructure;
  }
}
