package com.jengine.plugin.solr;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.CoreAdminResponse;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nouuid on 2015/5/8.
 */
public class SolrServiceImpl implements SolrService {

    protected final Log logger = LogFactory.getLog(getClass());

    private static final String NOUUID_SOLR_PRE = "NOUUID-";
    private static SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");

    private SolrConfig solrConfig;
    private static CloudSolrClient solrClient;

    public SolrServiceImpl(SolrConfig solrConfig) {
        this.solrConfig = solrConfig;
        try {
            solrClient = new CloudSolrClient(solrConfig.getSOLR_URL());
            solrClient.setDefaultCollection("collection1");
            createCollectionPerDay();
        } catch (Exception e) {
            logger.error("请检查服务器或端口是否开启!", e);
        }
    }

    @Override
    public boolean isConfigurated() {
        return solrConfig!=null;
    }

    @Override
    public QueryResponse query(SolrQuery query) {
        try {
            return solrClient.query(query);
        } catch (SolrServerException | IOException e) {
            logger.error("", e);
        }
        return null;
    }

    @Override
    public void createCollection(String collectionName) {
        CoreAdminResponse aResponse = null;
        try {
            aResponse = CoreAdminRequest.getStatus(collectionName, solrClient);
        } catch (SolrServerException | IOException e) {
            logger.error("", e);
        }
        if (aResponse.getCoreStatus(collectionName).size() < 1) {
            UpdateRequest request = new UpdateRequest("/admin/collections");
            request.setParam("action", "CREATE");
            request.setParam("name", collectionName);
            request.setParam("numShards", solrConfig.getNUM_SHARDS());
            request.setParam("replicationFactor", solrConfig.getREPLICATION_FACTOR());
            request.setParam("maxShardsPerNode", solrConfig.getMAX_SHARDS_PERNODE());
            request.setParam("collection.configName", solrConfig.getSOLR_CONFIG_NAME());
            try {
                solrClient.request(request);
            } catch (SolrServerException | IOException  e) {
                logger.error("", e);
            }
        }
    }

    @Override
    public void createCollectionPerDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        for (int i = 0; i < 5; i++) {
            createCollection(getCollectionNameByDate(calendar.getTime()));
            calendar.add(Calendar.DATE, 1);
            logger.info("-------- solr collection " + calendar.getTime() + "was added.");
        }

        solrClient.setDefaultCollection(getSolrCollectionName());
    }

    private String getCollectionNameByDate(Date now) {
        return NOUUID_SOLR_PRE + dayFormat.format(now);
    }

    private String getSolrCollectionName() {
        return NOUUID_SOLR_PRE + dayFormat.format(new Date());
    }

}
