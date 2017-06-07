package com.jengine.data.search.solr;

/**
 * Created by nouuid on 2015/5/8.
 */
public class SolrConfig {

    private String SOLR_URL;

    private String REQUEST_DIRNAME;

    private String RESPONSE_DIRNAME;

    private String DEFAULT_INVOCATION_RECEIVERS;

    private String NUM_SHARDS;

    private String REPLICATION_FACTOR;

    private String MAX_SHARDS_PERNODE;

    private String SOLR_CONFIG_NAME;

    public String getSOLR_URL() {
        return SOLR_URL;
    }

    public void setSOLR_URL(String sOLR_URL) {
        SOLR_URL = sOLR_URL;
    }

    public String getREQUEST_DIRNAME() {
        return REQUEST_DIRNAME;
    }

    public void setREQUEST_DIRNAME(String rEQUEST_DIRNAME) {
        REQUEST_DIRNAME = rEQUEST_DIRNAME;
    }

    public String getRESPONSE_DIRNAME() {
        return RESPONSE_DIRNAME;
    }

    public void setRESPONSE_DIRNAME(String rESPONSE_DIRNAME) {
        RESPONSE_DIRNAME = rESPONSE_DIRNAME;
    }

    public String getDEFAULT_INVOCATION_RECEIVERS() {
        return DEFAULT_INVOCATION_RECEIVERS;
    }

    public void setDEFAULT_INVOCATION_RECEIVERS(String dEFAULT_INVOCATION_RECEIVERS) {
        DEFAULT_INVOCATION_RECEIVERS = dEFAULT_INVOCATION_RECEIVERS;
    }

    public String getNUM_SHARDS() {
        return NUM_SHARDS;
    }

    public void setNUM_SHARDS(String nUM_SHARDS) {
        NUM_SHARDS = nUM_SHARDS;
    }

    public String getREPLICATION_FACTOR() {
        return REPLICATION_FACTOR;
    }

    public void setREPLICATION_FACTOR(String rEPLICATION_FACTOR) {
        REPLICATION_FACTOR = rEPLICATION_FACTOR;
    }

    public String getMAX_SHARDS_PERNODE() {
        return MAX_SHARDS_PERNODE;
    }

    public void setMAX_SHARDS_PERNODE(String mAX_SHARDS_PERNODE) {
        MAX_SHARDS_PERNODE = mAX_SHARDS_PERNODE;
    }

    public String getSOLR_CONFIG_NAME() {
        return SOLR_CONFIG_NAME;
    }

    public void setSOLR_CONFIG_NAME(String sOLR_CONFIG_NAME) {
        SOLR_CONFIG_NAME = sOLR_CONFIG_NAME;
    }
}
