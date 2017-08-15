package com.jengine.search.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

/**
 * Created by nouuid on 2015/5/8.
 */
public interface SolrService {

  /**
   * 是否存在配置信息
   */
  public boolean isConfigurated();

  /**
   * 查询
   */
  public QueryResponse query(SolrQuery query);

  /**
   * 创建collection
   */
  public void createCollection(String collectionName);

  /**
   * 创建将来的5个collection，每天1个
   */
  public void createCollectionPerDay();
}
