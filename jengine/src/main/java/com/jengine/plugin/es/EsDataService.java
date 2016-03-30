package com.jengine.plugin.es;

import com.jengine.java.util.DateUtil;

import java.util.*;

/**
 * @author bl07637
 * @date 3/25/2016
 * @description
 */
public class EsDataService {

    /**
     * data from http://{es_host}/_aliases
     * data instance:
     *      {"xingng-w-20160325":{"aliases":{}},"xingng-w-20160324":{"aliases":{}},"xingng-w-20160323":{"aliases":{}}}
     * parse data instance to get:
     *      xingng-w-20160325, xingng-w-20160324, xingng-w-20160323
     */
    public List<String> xingngEsAliasesJsonParse(String jsonData) {
        jsonData = jsonData.substring(1, jsonData.length()-2);
        String[] parts = jsonData.split(",");
        List<String> indicesNames = new ArrayList<String>();
        String indexName = null;
        for (String part : parts) {
            indexName = part.substring(0, part.indexOf(":"));
            indexName = indexName.substring(1, indexName.length()-1);
            indicesNames.add(indexName);
        }
        return indicesNames;
    }

    /**
     * filter index names by mark symbol and date string.
     *
     * index name pattern: xingng-{mark symbol}-{date string}
     * mark symbol pattern: [a-z], the first lowercase of condition name
     * date string pattern: \d{8}, formatted by SimpleDateFormat("yyyyMMdd").
     *
     * matches:
     * 1. in 30 days, abandon
     * 2. between 30 and 90 days, save index name in which mark symbol is not in filterSet
     * 3. out of 90 days, save
     *
     * @param filterSet
     * @param indexNames
     */
    public List<String> filterIndexName(Set<String> filterSet, List<String> indexNames) {
        final String XINGNG_INDEX_PATTERN = "xingng-[a-z]-\\d{8}";

        // date range
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.setTimeInMillis(c.getTimeInMillis()-30*24*60*60*1000l);
        String thirtyDaysAgo = DateUtil.simpleDateFormat.format(c.getTime());
        c.setTimeInMillis(c.getTimeInMillis()-60*24*60*60*1000l);
        String ninetyDaysAgo = DateUtil.simpleDateFormat.format(c.getTime());

        // deleteIndicesNames
        List<String> filteredIndicesNames = new ArrayList<String>();
        for (String indexName : indexNames) {
            if (!indexName.matches(XINGNG_INDEX_PATTERN)) {
                continue;
            }

            String[] parts = indexName.split("-");
            if (parts[2].compareTo(ninetyDaysAgo)<0) {
                filteredIndicesNames.add(indexName);
            } else if (parts[2].compareTo(ninetyDaysAgo)>=0 && parts[2].compareTo(thirtyDaysAgo)<0) {
                if (!filterSet.contains(parts[1])) {
                    filteredIndicesNames.add(indexName);
                }
            }
        }
        return filteredIndicesNames;
    }
}
