package com.jengine.data.elasticsearch;

import com.jengine.data.elasticsearch.mydataframe.TableStructure;
import com.jengine.transport.serialize.json.JsonUtil;

import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author nouuid
 * @date 8/8/2016
 * @description
 */
public class MappingService {

  private static final Logger logger = LoggerFactory.getLogger(MappingService.class);

  private TransportClient client;

  public MappingService(TransportClient client) {
    this.client = client;
  }

  public boolean create(String index, String type, TableStructure dataStructure) throws Exception {
    if (logger.isInfoEnabled()) {
      logger.info("MAPPING_ADD=" + JsonUtil.toJson(dataStructure));
    }
    boolean res = false;
    PutMappingRequest mappingRequest = Requests.putMappingRequest(index).type(type).source(JsonUtil.toJson(dataStructure));
    PutMappingResponse putMappingResponse = client.admin().indices().putMapping(mappingRequest).actionGet();
    if (putMappingResponse != null && putMappingResponse.isAcknowledged()) {
      res = true;
    }
    return res;
  }

  public LinkedHashMap get(String index, String type) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("MAPPING_GET=" + index + "," + type);
    }
    GetMappingsRequestBuilder getMappingsRequestBuilder = client.admin().indices().prepareGetMappings(index).setTypes(type);
    GetMappingsResponse getMappingsResponse = getMappingsRequestBuilder.get();
    LinkedHashMap res = null;
    if (getMappingsResponse != null) {
      ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> indexTemplateMetaDatas = getMappingsResponse.getMappings();
      if (indexTemplateMetaDatas != null && indexTemplateMetaDatas.get(index) != null) {
        MappingMetaData mappingMetaData = indexTemplateMetaDatas.get(index).get(type);
        if (mappingMetaData != null) {
          Map<String, Object> sourceAsMap = mappingMetaData.getSourceAsMap();
          if (sourceAsMap != null && sourceAsMap.get("properties") != null) {
            res = (LinkedHashMap) sourceAsMap.get("properties");
          }
        }
      }
    }
    return res;
  }

  public boolean exists(String index, String type) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("MAPPING_EXISTS=" + index + "," + type);
    }
    if (get(index, type) != null) {
      return true;
    }
    return false;
  }
}
