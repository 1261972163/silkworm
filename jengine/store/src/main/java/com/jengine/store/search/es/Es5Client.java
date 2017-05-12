package com.jengine.store.search.es;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * EsClient
 *
 * @author nouuid
 * @date 4/12/2017
 * @since 0.1.0
 */
public class Es5Client {
    private static final Logger logger = LoggerFactory.getLogger(Es5Client.class);

    private TransportClient client;

    public Es5Client(String cluster, String url) throws Exception {
        if (StringUtils.isBlank(cluster) || StringUtils.isBlank(url)) {
            throw new Exception("cluster or url is blank");
        }

        Settings settings = Settings.builder().put("cluster.name", cluster).build();
        client = new PreBuiltTransportClient(settings);
        String[] servers = url.split(",");
        for (String server : servers) {
            String[] hostPort = server.split(":");
            if (hostPort == null) {
                continue;
            }
            String host = null;
            String port = null;
            if (hostPort.length == 0) {
                host = hostPort[0];
                port = "9300";
            } else if (hostPort.length > 1) {
                host = hostPort[0];
                port = hostPort[1];
            }
            try {
                client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), Integer.parseInt(port)));
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void index(String index, String type, String id, String source) throws Exception {
        IndexResponse response = client.prepareIndex(index, type, id)
                .setSource(source)
                .setSource()
                .get();
        logger.debug(response.toString());
    }



    public void index(List<IndexMessage> indexMessages) throws Exception {
        if (indexMessages.size()<=0) {
            return;
        }
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (IndexMessage indexMessage : indexMessages) {
            if (StringUtils.isBlank(indexMessage.getRouting())) {
                bulkRequest.add(client.prepareIndex(indexMessage.getIndex(), indexMessage.getType(), indexMessage.getId()).setSource(indexMessage.getSource()));
            } else {
                bulkRequest.add(client.prepareIndex(indexMessage.getIndex(), indexMessage.getType(), indexMessage.getId()).setRouting(indexMessage.getRouting()).setSource(indexMessage.getSource()));
            }
        }
        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.hasFailures()) {
            throw new Exception(bulkResponse.buildFailureMessage());
        }
    }

    public void stop() {
        if (client == null) {
            client.close();
            client = null;
        }
    }


}
