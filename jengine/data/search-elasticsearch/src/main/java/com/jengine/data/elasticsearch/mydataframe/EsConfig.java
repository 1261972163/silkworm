package com.jengine.data.elasticsearch.mydataframe;

/**
 * @author nouuid
 * @date 8/4/2016
 * @description
 */
public class EsConfig {

  private String cluster;
  private String url;
  private String user = "admin";
  private String passward = "admin";
  private int templateShards = 5;
  private int templateReplicas = 0;
  private String tag;

  public String getCluster() {
    return cluster;
  }

  public void setCluster(String cluster) {
    this.cluster = cluster;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPassward() {
    return passward;
  }

  public void setPassward(String passward) {
    this.passward = passward;
  }

  public int getTemplateShards() {
    return templateShards;
  }

  public void setTemplateShards(int templateShards) {
    this.templateShards = templateShards;
  }

  public int getTemplateReplicas() {
    return templateReplicas;
  }

  public void setTemplateReplicas(int templateReplicas) {
    this.templateReplicas = templateReplicas;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }
}
