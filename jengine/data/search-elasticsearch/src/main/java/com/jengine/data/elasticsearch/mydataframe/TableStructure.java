package com.jengine.data.elasticsearch.mydataframe;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author bl07637
 * @date 8/5/2016
 * @description
 */
public class TableStructure {

  private Map<String, String> _source = new HashMap<String, String>();
  /**
   * RecordType can not be modified
   */
  private Map<String, RecordType> properties; // <field name, field config>
  private Set<String> updateIgnores;

  public Map<String, String> get_source() {
    return _source;
  }

  public void set_source(Map<String, String> _source) {
    this._source = _source;
  }

  public Map<String, RecordType> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, RecordType> properties) {
    this.properties = properties;
  }

  public Set<String> getUpdateIgnores() {
    return updateIgnores;
  }

  public void setUpdateIgnores(Set<String> updateIgnores) {
    this.updateIgnores = updateIgnores;
  }
}
