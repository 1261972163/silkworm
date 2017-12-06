package com.jengine.data.elasticsearch.mydataframe.so;

/**
 * @author nouuid
 * @date 8/4/2016
 * @description
 */
public class SearcherSOBuilder {

  private SearcherSO searcherSO = null;

  public SearcherSOBuilder() {
    searcherSO = new SearcherSO();
  }

  public SearcherSO setQueryCondition(BooleanSO queryCondition) {
    searcherSO.setBooleanSO(queryCondition);
    return searcherSO;
  }

  public SearcherSO setRangeSO(RangeSO rangeSO) {
    searcherSO.setRangeSO(rangeSO);
    return searcherSO;
  }

  public SearcherSO setSortSO(SortSO sortSO) {
    searcherSO.setSortSO(sortSO);
    return searcherSO;
  }

  public SearcherSO setPageSO(PageSO pageSO) {
    searcherSO.setPageSO(pageSO);
    return searcherSO;
  }

}
