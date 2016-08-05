package com.jengine.plugin;

import com.jengine.plugin.es.EsDataService;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author nouuid
 * @date 3/25/2016
 * @description
 */
public class EsDataServiceTest {

    EsDataService esDataService = new EsDataService();

    @Test
    public void xingngEsAliasesJsonParseTest() {
        String json = "{\"xingng-w-20160324\":{\"aliases\":{}},\"xingng-w-20160323\":{\"aliases\":{}}}";
        List<String> indicesNames = esDataService.xingngEsAliasesJsonParse(json);
        String expected = "[xingng-w-20160324, xingng-w-20160323]";
        Assert.assertEquals(expected, indicesNames.toString());
    }

    @Test
    public void filterIndexNameTest() {
        // filterSet
        List<String> noexpiredAppidList = new ArrayList<>();
        noexpiredAppidList.add("adi");
        noexpiredAppidList.add("edi");

        Set<String> filterSet = new HashSet<String>();
        for (String appid : noexpiredAppidList) {
            String letter = appid.charAt(0) + "";
            if (filterSet.contains(letter)) {
                continue;
            }
            filterSet.add(letter);
        }

        // indexNames
        List<String> indexNames = null;
        indexNames = new ArrayList<>();
        // 90 ago
        indexNames.add("xingng-a-20150101");//
        indexNames.add("xingng-b-20150101");//
        // 90~30
        indexNames.add("xingng-a-20160203");
        indexNames.add("xingng-b-20160203");//
        // in 30
        indexNames.add("xingng-a-20160301");
        indexNames.add("xingng-b-20160301");

        List<String> filteredIndicesNames = esDataService.filterIndexName(filterSet, indexNames);
        String expected = "[xingng-a-20150101, xingng-b-20150101, xingng-b-20160203]";
        Assert.assertEquals(expected, filteredIndicesNames.toString());
    }
}
