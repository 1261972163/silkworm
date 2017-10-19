package com.jengine.data.elasticsearch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.transport.TransportClient;

import java.util.Arrays;
import java.util.List;

/**
 * @author nouuid
 * @date 8/4/2016
 * @description
 */
public class IndexService {

  private static final Log logger = LogFactory.getLog(IndexService.class);

  private TransportClient client;

  public IndexService(TransportClient client) {
    this.client = client;
  }

  public boolean exists(String index) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("INDEX_EXISTS=" + index);
    }
    boolean res = false;
    IndicesExistsRequest indicesExistsRequest = new IndicesExistsRequest(index);
    IndicesExistsResponse indicesExistsResponse = client.admin().indices().exists(indicesExistsRequest).actionGet();
    if (indicesExistsResponse != null && indicesExistsResponse.isExists()) {
      res = true;
    }
    return res;
  }

  public boolean exists(List<String> indices) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("INDEX_EXISTS=" + indices);
    }
    boolean res = false;
    String[] arrayStr = indices.toArray(new String[indices.size()]);
    IndicesExistsRequest indicesExistsRequest = new IndicesExistsRequest(arrayStr);
    IndicesExistsResponse indicesExistsResponse = client.admin().indices().exists(indicesExistsRequest).actionGet();
    if (indicesExistsResponse != null && indicesExistsResponse.isExists()) {
      res = true;
    }
    return res;
  }

  public boolean create(String index) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("INDEX_CREATE=" + index);
    }
    boolean res = false;
    CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
    CreateIndexResponse createIndexResponse = client.admin().indices().create(createIndexRequest).actionGet();
    if (createIndexResponse != null && createIndexResponse.isAcknowledged()) {
      res = true;
    }
    return res;
  }

  public boolean delete(String index) {
    if (logger.isDebugEnabled()) {
      logger.debug("INDEX_DELETE=" + index);
    }
    boolean res = false;
    DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
    DeleteIndexResponse deleteIndexResponse = client.admin().indices().delete(deleteIndexRequest).actionGet();
    if (deleteIndexResponse != null && deleteIndexResponse.isAcknowledged()) {
      res = true;
    }
    return res;
  }

  public List<String> list() {
    String[] indices = client.admin().cluster().prepareState().execute().actionGet().getState().getMetaData().getConcreteAllIndices();
    List<String> res = null;
    if (indices != null && indices.length > 0) {
      res = Arrays.asList(indices);
    }
    return res;
  }

}
