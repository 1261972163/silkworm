package com.jengine.store.bingo;

/**
 * content
 *
 * @author bl07637
 * @date 11/22/2016
 * @since 0.1.0
 */
public class BingoDemo {

    public static void main(String[] args) {
//        // BingoClient初始化
//        EsConfig esConfig = new EsConfig();
//        esConfig.setUrl("http://10.45.11.85:9200");
//        String schemaPath = BingoDemo.class.getResource("/").getPath() + "schema.xml";;
//        BingoClient bingoClient = new BingoClientImpl(esConfig, schemaPath);
//        // 查询条件
//        String databaseName = "pydelo";
//        String tableName = "t_beststore_order";
//        SearcherSO searcherSO = new SearcherSO();
//        searcherSO.getPageSO().setPageSize(100);
//        searcherSO.getPageSO().setPageNumber(1);
//        Map<String, FieldCondition> mustConditions = new HashMap<String, FieldCondition>();
//        mustConditions.put("pushUser", new FieldCondition("沈*", true));
//
//        Map<String, FieldCondition> shouldConditions = new HashMap<String, FieldCondition>();
//        shouldConditions.put("memberType", new FieldCondition("便利*", true));
//
//        Map<String, FieldCondition> mustnotConditions = new HashMap<String, FieldCondition>();
//        mustnotConditions.put("id", new FieldCondition("166325"));
//
////        searcherSO.setMustConditions(mustConditions);
//        searcherSO.setShouldConditions(shouldConditions);
////        searcherSO.setMustnotConditions(mustnotConditions);
//        // 查询结果为id
//        PageList<Integer> pageList = bingoClient.selectId(databaseName, tableName, searcherSO);
//        // 结果处理
//        System.out.println("fullSize:" + pageList.getFullSize());
//        System.out.println("pageNumber:" + pageList.getPageNumber());
//        System.out.println("pageSize:" + pageList.getPageSize());
////        for (Integer id : pageList.getList()) {
////            System.out.println("----" + id);
////        }
    }
}
