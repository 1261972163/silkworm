package com.jengine.data.elasticsearch.tools;

import com.jengine.data.elasticsearch.mydataframe.DataType;
import com.jengine.data.elasticsearch.mydataframe.RecordType;
import com.jengine.data.elasticsearch.mydataframe.TableStructure;
import com.jengine.data.elasticsearch.mydataframe.so.BooleanSO;
import com.jengine.data.elasticsearch.mydataframe.so.FieldCondition;
import com.jengine.data.elasticsearch.mydataframe.so.SearcherSO;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author bl07637
 * @date 8/11/2016
 * @description
 */
public class RecordQueryBodyBuilder {

  private SearcherSO searcherSO = null;

  private TableStructure tableStructure;

  public RecordQueryBodyBuilder(TableStructure tableStructure, SearcherSO searcherSO) {
    this.tableStructure = tableStructure;
    this.searcherSO = searcherSO;
  }

  public String build() {
    String queryBody = null;
    if (tableStructure == null || tableStructure.getProperties() == null || tableStructure.getProperties().isEmpty()) {
      return null;
    }
    Map<String, RecordType> dataProperties = tableStructure.getProperties();

    SearchSourceBuilder ssb = new SearchSourceBuilder();
    // condition
    BoolQueryBuilder boolQueryBuilder = buildBoolQueryBuilder(dataProperties, searcherSO);
    String rangeField = searcherSO.getRangeSO().getRangeField();
    if (searcherSO.getRangeSO().isActive() && StringUtils.isNotBlank(rangeField) && dataProperties.containsKey(rangeField)) {
      String rangeFrom = searcherSO.getRangeSO().getRangeFrom();
      String rangeTo = searcherSO.getRangeSO().getRangeTo();
      if (StringUtils.isNotBlank(rangeFrom) && StringUtils.isNotBlank(rangeTo)) {
        long from = Long.parseLong(rangeFrom);
        long to = Long.parseLong(rangeTo);
        if (rangeFrom.equals(rangeTo)) {
          boolQueryBuilder.must(QueryBuilders.matchPhraseQuery(rangeField, from));
        } else {
          boolQueryBuilder.must(QueryBuilders.rangeQuery(rangeField).from(from).to(to));
        }
      }
    }
    // query
    ssb.query(boolQueryBuilder);
    // paging
    int pageSize = searcherSO.getPageSO().getPageSize();
    int pageNumber = searcherSO.getPageSO().getPageNumber();
    if (pageSize > 0 && pageNumber > 0) {
      ssb.size(pageSize).from((pageNumber - 1) * pageSize);
    }
    // sorting
    String sortField = searcherSO.getSortSO().getField();
    if (searcherSO.getSortSO().isActive() && StringUtils.isNotBlank(sortField) && dataProperties.containsKey(sortField)) {
      if (SortOrder.ASC.equals(searcherSO.getSortSO().getOrder())) {
        ssb.sort(sortField, SortOrder.ASC);
      } else {
        ssb.sort(sortField, SortOrder.DESC);
      }
    }
    queryBody = ssb.toString();

    return queryBody;
  }

  /**
   * build BoolQueryBuilder in order to bool query
   */
  private BoolQueryBuilder buildBoolQueryBuilder(Map<String, RecordType> dataProperties, SearcherSO searcherSO) {
    BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
    BooleanSO queryCondition = searcherSO.getBooleanSO();
    ArrayList<FieldCondition> mustConditions = queryCondition.getMust();
    ArrayList<FieldCondition> shouldConditions = queryCondition.getShould();
    ArrayList<FieldCondition> mustnotConditions = queryCondition.getMustnot();

    if (mustConditions != null && !mustConditions.isEmpty()) {
      typeBuild(dataProperties, mustConditions, boolQueryBuilder, "must");
    }
    if (shouldConditions != null && !shouldConditions.isEmpty()) {
      typeBuild(dataProperties, shouldConditions, boolQueryBuilder, "should");
    }
    if (mustnotConditions != null && !mustnotConditions.isEmpty()) {
      typeBuild(dataProperties, mustnotConditions, boolQueryBuilder, "must_not");
    }
    if (shouldConditions.size() > 0) {
      boolQueryBuilder.minimumNumberShouldMatch(1);
    }
    return boolQueryBuilder;
  }

  private void typeBuild(Map<String, RecordType> dataProperties, ArrayList<FieldCondition> conditions, BoolQueryBuilder boolQueryBuilder, String boolType) {
    QueryBuilder queryBuilder = null;
    for (FieldCondition fieldCondition : conditions) {
      if (dataProperties.containsKey(fieldCondition.getField())) {
        String fieldName = fieldCondition.getField();
        String condition = fieldCondition.getCondition();
        boolean isWildcard = fieldCondition.isWildcardActive();

        String fieldType = StringUtils.upperCase(dataProperties.get(fieldName).getType().toValue());
        if (DataType.TEXT.toString().equals(fieldType)) {
          if (isWildcard) {
            queryBuilder = QueryBuilders.wildcardQuery(fieldName, condition);
          } else {
            queryBuilder = QueryBuilders.matchPhraseQuery(fieldName, condition);
          }
        } else if (DataType.BYTE.toString().equals(fieldType) ||
            DataType.SHORT.toString().equals(fieldType) ||
            DataType.INTEGER.toString().equals(fieldType) ||
            DataType.LONG.toString().equals(fieldType) ||
            DataType.FLOAT.toString().equals(fieldType) ||
            DataType.DOUBLE.toString().equals(fieldType) ||
            DataType.BOOLEAN.toString().equals(fieldType) ||
            DataType.DATE.toString().equals(fieldType)) {
          queryBuilder = QueryBuilders.termQuery(fieldName, Integer.parseInt(condition));
        }
        if (queryBuilder == null) {
          continue;
        }
        if ("must".equals(boolType)) {
          boolQueryBuilder.must(queryBuilder);
        } else if ("should".equals(boolType)) {
          boolQueryBuilder.should(queryBuilder);
        } else if ("must_not".equals(boolType)) {
          boolQueryBuilder.mustNot(queryBuilder);
        } else {
          continue;
        }
      }
    }
  }


}
