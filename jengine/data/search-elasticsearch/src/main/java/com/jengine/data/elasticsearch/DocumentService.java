package com.jengine.data.elasticsearch;


import com.jengine.data.elasticsearch.mydataframe.Doc;
import com.jengine.data.elasticsearch.mydataframe.PageShow;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.InternalMappedTerms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bl07637
 * @date 8/4/2016
 * @description
 */
public class DocumentService {

  private static final Log logger = LogFactory.getLog(DocumentService.class);

  private TransportClient transportClient;

  public DocumentService(TransportClient client) {
    this.transportClient = client;
  }

  /**
   * @param doc
   * @param foreceId
   * @return
   * @throws Exception
   */
  public boolean add(Doc doc, boolean foreceId) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("DOC_ADD=" + doc);
    }
    boolean res = false;
    if (!validate(doc)) {
      throw new RuntimeException("Doc is invalidate.");
    }
    if (foreceId && StringUtils.isBlank(doc.getId())) {
      throw new RuntimeException("id is blank.");
    }
    IndexRequestBuilder indexRequestBuilder = transportClient.prepareIndex()
        .setIndex(doc.getIndex())
        .setType(doc.getType())
        .setSource(doc.getSource());
    if (StringUtils.isNotBlank(doc.getRouting())) {
      indexRequestBuilder.setRouting(doc.getRouting());
    }
    if (StringUtils.isNotBlank(doc.getId())) {
      indexRequestBuilder.setId(doc.getId());
    }
    IndexResponse indexResponse = indexRequestBuilder.get();
    if (indexResponse != null && indexResponse.getResult().equals(DocWriteResponse.Result.CREATED)) {
      res = true;
    }
    return res;
  }

  /**
   * @param docs
   * @param foreceId
   * @return
   * @throws Exception
   */
  public boolean add(List<Doc> docs, boolean foreceId) throws Exception {
    boolean res = false;
    BulkRequestBuilder bulkRequest = transportClient.prepareBulk();
    boolean isEmpty = true;
    for (Doc doc : docs) {
      if (logger.isDebugEnabled()) {
        logger.debug("DOC_ADD2=" + doc);
      }
      if (!validate(doc)) {
        throw new RuntimeException("Doc is invalidate.");
      }
      if (foreceId && StringUtils.isBlank(doc.getId())) {
        throw new RuntimeException("id is blank.");
      }
      IndexRequestBuilder indexRequestBuilder = transportClient.prepareIndex()
          .setIndex(doc.getIndex())
          .setType(doc.getType())
          .setSource(doc.getSource());
      if (StringUtils.isNotBlank(doc.getRouting())) {
        indexRequestBuilder.setRouting(doc.getRouting());
      }
      if (StringUtils.isNotBlank(doc.getId())) {
        indexRequestBuilder.setId(doc.getId());
      }
      bulkRequest.add(indexRequestBuilder);
      isEmpty = isEmpty ? false : isEmpty;
    }
    if (isEmpty) {
      logger.info("no data was put to elasticsearch.");
    } else {
      BulkResponse bulkResponse = bulkRequest.get();
      if (bulkResponse != null && !bulkResponse.hasFailures()) {
        res = true;
      } else {
        if (bulkResponse != null) {
          throw new Exception(bulkResponse.buildFailureMessage());
        } else {
          throw new Exception("bulk insert error.");
        }
      }
    }
    return res;
  }

  public boolean add(Doc doc) throws Exception {
    return add(doc, true);
  }

  public boolean add(List<Doc> docs) throws Exception {
    return add(docs, true);
  }

  /**
   * 验证doc是否具有有效信息
   */
  private boolean validate(Doc doc) {
    if (doc.getIndex() != null && doc.getSource() != null && !doc.getSource().isEmpty()) {
      return true;
    }
    return false;
  }

  /**
   * @param index
   * @param type
   * @param docId
   * @param routing
   * @return
   * @throws IOException
   */
  public boolean delete(String index, String type, String docId, String routing) throws IOException {
    if (logger.isInfoEnabled()) {
      logger.info("DOC_DELETE=" + index + "," + type + "," + docId + "," + routing);
    }
    boolean res = false;
    DeleteRequestBuilder deleteRequestBuilder = transportClient.prepareDelete()
        .setIndex(index)
        .setType(type)
        .setId(docId);
    if (StringUtils.isNotBlank(routing)) {
      deleteRequestBuilder.setRouting(routing);
    }
    DeleteResponse deleteResponse = deleteRequestBuilder.get();
    if (deleteResponse != null && deleteResponse.getResult().equals(DocWriteResponse.Result.DELETED)) {
      res = true;
    }
    return res;
  }

  /**
   * @param index
   * @param type
   * @param docId
   * @param updatesMap
   * @param routing
   * @return
   * @throws Exception
   */
  public boolean update(String index, String type, String docId, Map<String, String> updatesMap, String routing) throws Exception {
    if (logger.isInfoEnabled()) {
      logger.info("DOC_UPDATE=" + index + "," + type + "," + docId + "," + routing + "," + updatesMap);
    }
    boolean res = false;
    if (StringUtils.isBlank(docId)) {
      throw new RuntimeException("id is blank.");
    }
    UpdateRequestBuilder updateRequestBuilder = transportClient.prepareUpdate();
    updateRequestBuilder.setIndex(index)
        .setType(type)
        .setId(docId)
        .setDoc(updatesMap);
    if (StringUtils.isNotBlank(routing)) {
      updateRequestBuilder.setRouting(routing);
    }
    UpdateResponse updateResponse = updateRequestBuilder.get();
    if (updateResponse != null && updateResponse.getResult().equals(DocWriteResponse.Result.UPDATED)) {
      res = true;
    }
    return res;
  }

  /**
   * @param index
   * @param type
   * @param id
   * @param routing
   * @return
   * @throws IOException
   */
  public String get(String index, String type, String id, String routing) throws IOException {
    if (logger.isDebugEnabled()) {
      logger.debug("DOC_GET=" + index + "," + type + "," + id + "," + routing);
    }
    GetRequestBuilder getRequestBuilder = transportClient.prepareGet();
    getRequestBuilder.setIndex(index)
        .setType(type)
        .setId(id);
    if (StringUtils.isNotBlank(routing)) {
      getRequestBuilder.setRouting(routing);
    }
    GetResponse getResponse = getRequestBuilder.get();
    String res = null;
    if (getResponse != null) {
      res = getResponse.getSourceAsString();
    }
    return res;
  }

  public PageShow<String> query(List<String> indices, List<String> types, String routing, QueryBuilder queryBuilder, int from, int size, String sortField, SortOrder sortOrder) {
    return query(indices, types, routing, queryBuilder, from, size, sortField, sortOrder, false);
  }

  public PageShow<String> queryId(List<String> indices, List<String> types, String routing, QueryBuilder queryBuilder, int from, int size, String sortField, SortOrder sortOrder) {
    return query(indices, types, routing, queryBuilder, from, size, sortField, sortOrder, true);
  }

  private PageShow<String> query(List<String> indices, List<String> types, String routing, QueryBuilder queryBuilder, int from, int size, String sortField, SortOrder sortOrder, boolean onlyId) {
    if (logger.isDebugEnabled()) {
      logger.debug("DOC_QUERY_ID=" + indices + "," + types + "," + "," + routing + "," + queryBuilder.toString() + "," + from + "," + size);
    }
    if (logger.isDebugEnabled()) {
      logger.debug("queryBuilder:" + queryBuilder.toString());
    }
    if (indices == null || indices.size() <= 0) {
      throw new RuntimeException("indices is blank.");
    }
    if (types == null || types.size() <= 0) {
      throw new RuntimeException("types is blank.");
    }
    String[] indexStrArray = indices.toArray(new String[indices.size()]);
    String[] typeStrArray = types.toArray(new String[types.size()]);
    SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch();
    searchRequestBuilder.setIndices(indexStrArray)
        .setTypes(typeStrArray)
        .setRouting(routing)
        .setQuery(queryBuilder)
//                .setScroll(new TimeValue(10))
        .setExplain(true);
    if (from >= 0 && size <= 1000) {
      searchRequestBuilder.setFrom(from)
          .setSize(size);
    }
    if (StringUtils.isNotBlank(sortField)) {
      searchRequestBuilder.addSort(sortField, sortOrder);
    }
    SearchResponse searchResponse = searchRequestBuilder.get();
    long fullSize = 0L;
    List<String> docList = null;
    if (searchResponse != null) {
      SearchHits searchHits = searchResponse.getHits();
      docList = new ArrayList<String>();
      fullSize = searchHits.getTotalHits();
      for (SearchHit hit : searchHits) {
        if (onlyId) {
          docList.add(hit.getId());
        } else {
          docList.add(hit.getSourceAsString());
        }
      }
    }

    PageShow pageShow = new PageShow<String>();
    pageShow.setFullSize(fullSize);
    pageShow.setList(docList);

    return pageShow;
  }

  public SearchResponse count(List<String> indices, List<String> types, String routing, QueryBuilder queryBuilder) {
    if (indices == null || indices.size() <= 0) {
      throw new RuntimeException("indices is blank.");
    }
    String[] indexStrArray = indices.toArray(new String[indices.size()]);
    SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch();
    searchRequestBuilder.setIndices(indexStrArray);
    if (types != null && types.size() > 0) {
      String[] typeStrArray = types.toArray(new String[types.size()]);
      searchRequestBuilder.setTypes(typeStrArray);
    }
    if (StringUtils.isNotBlank(routing)) {
      searchRequestBuilder.setRouting(routing);
    }
    searchRequestBuilder.setSource(new SearchSourceBuilder().size(0).query(queryBuilder));
    SearchResponse searchResponse = searchRequestBuilder.get();
    return searchResponse;
  }

  public Map<String, Map<String, String>> aggregationCount(List<String> indices, List<String> types, String routing, QueryBuilder queryBuilder, List<AggregationBuilder> aggregations) {
    if (indices == null || indices.size() <= 0) {
      throw new RuntimeException("indices is blank.");
    }

    String[] indexStrArray = indices.toArray(new String[indices.size()]);
    SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch();
    searchRequestBuilder.setIndices(indexStrArray);
    if (types != null && types.size() > 0) {
      String[] typeStrArray = types.toArray(new String[types.size()]);
      searchRequestBuilder.setTypes(typeStrArray);
    }
    searchRequestBuilder.setRouting(routing);
    searchRequestBuilder.setQuery(queryBuilder);
    for (AggregationBuilder aggregationBuilder : aggregations) {
      searchRequestBuilder.addAggregation(aggregationBuilder);
    }
    SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
    Map<String, Aggregation> aggregationMap = searchResponse.getAggregations().asMap();
    Map<String, Map<String, String>> aggregationCountRes = new HashMap<String, Map<String, String>>();
    for (String aggregationKey : aggregationMap.keySet()) {
      InternalMappedTerms internalMappedTerms = (InternalMappedTerms) aggregationMap.get(aggregationKey);
      List<org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket> buckets = internalMappedTerms.getBuckets();
      Map<String, String> bucketCountRes = new HashMap<String, String>();
      for (org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket bucket : buckets) {
        bucketCountRes.put(bucket.getKeyAsString(), bucket.getDocCount() + "");
      }
      aggregationCountRes.put(aggregationKey, bucketCountRes);
    }
    return aggregationCountRes;
  }

//    /**
//     *
//     * @param index
//     * @param type
//     * @param routing
//     * @param queryBuilder
//     * @return
//     */
//    public List<String> queryByFilter(String index, String type, String routing, QueryBuilder queryBuilder) {
//        if (logger.isDebugEnabled()) {
//            logger.debug("DOC_QUERY_BY_FILTER=" + index + "," + type + "," + "," + routing + "," + queryBuilder.toString());
//        }
//        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch();
//        searchRequestBuilder.setIndices(index)
//                .setTypes(type)
//                .setPostFilter(queryBuilder);
//        if (StringUtils.isNotBlank(routing)) {
//            searchRequestBuilder.setRouting(routing);
//        }
//        SearchResponse searchResponse = searchRequestBuilder.get();
//        List<String> docList = null;
//        if (searchResponse!=null) {
//            SearchHits searchHits = searchResponse.getHits();
//            docList = new ArrayList<String>();
//            for (SearchHit hit : searchHits) {
//                docList.add(hit.getSourceAsString());
//            }
//        }
//        return docList;
//    }

}
