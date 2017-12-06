package com.jengine.data.elasticsearch.mydataframe.so;

import java.util.ArrayList;
import java.util.List;

/**
 * content
 *
 * @author nouuid
 * @date 4/13/2017
 * @since 0.1.0
 */
public class BooleanSO {
  private List<FieldCondition> must = new ArrayList<FieldCondition>();        // must，表达与（and）逻辑运算
  private List<FieldCondition> should = new ArrayList<FieldCondition>();      // should，表达或（or）逻辑运算
  private List<FieldCondition> mustnot = new ArrayList<FieldCondition>();     // mustnot，表达非（not）逻辑运算

  public void addMust(FieldCondition fieldCondition) {
    must.add(fieldCondition);
  }

  public void addShould(FieldCondition fieldCondition) {
    should.add(fieldCondition);
  }

  public void addMustnot(FieldCondition fieldCondition) {
    mustnot.add(fieldCondition);
  }

  public ArrayList<FieldCondition> getMust() {
    ArrayList<FieldCondition> fieldConditions = new ArrayList<FieldCondition>();
    fieldConditions.addAll(must);
    return fieldConditions;
  }

  public ArrayList<FieldCondition> getShould() {
    ArrayList<FieldCondition> fieldConditions = new ArrayList<FieldCondition>();
    fieldConditions.addAll(should);
    return fieldConditions;
  }

  public ArrayList<FieldCondition> getMustnot() {
    ArrayList<FieldCondition> fieldConditions = new ArrayList<FieldCondition>();
    fieldConditions.addAll(mustnot);
    return fieldConditions;
  }

  public void clear() {
    must.clear();
    should.clear();
    mustnot.clear();
  }
}
