package com.jengine.data.elasticsearch.mydataframe;

import java.util.Map;

/**
 * content
 *
 * @author nouuid
 * @date 6/12/2017
 * @since 0.1.0
 */
public class Doc {

  private String index;
  private String type;
  private String id;
  private String routing;
  private Map<String, String> source;

  public Doc(String index, String type, String id, String routing, Map<String, String> source) {
    this.index = index;
    this.type = type;
    this.id = id;
    this.routing = routing;
    this.source = source;
  }

  public String getIndex() {
    return index;
  }

  public void setIndex(String index) {
    this.index = index;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getRouting() {
    return routing;
  }

  public void setRouting(String routing) {
    this.routing = routing;
  }

  public Map<String, String> getSource() {
    return source;
  }

  public void setSource(Map<String, String> source) {
    this.source = source;
  }

  @Override
  public String toString() {
    return "{index:" + index + ",type:" + type + ",id:" + id + ",routing:" + routing + ",source:" + source + "}";
  }
}
