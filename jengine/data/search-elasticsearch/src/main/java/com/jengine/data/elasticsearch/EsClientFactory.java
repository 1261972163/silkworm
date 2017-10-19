package com.jengine.data.elasticsearch;

import com.jengine.data.elasticsearch.mydataframe.EsConfig;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * content
 *
 * @author bl07637
 * @date 5/2/2017
 * @since 0.1.0
 */
public class EsClientFactory {
  private static final Logger logger = LoggerFactory.getLogger(EsClientFactory.class);

//    private static ConcurrentHashMap<String, TransportClient> esClientMap = new ConcurrentHashMap<String, TransportClient>();

  private TransportClient client;

  public EsClientFactory(EsConfig esConfig) throws Exception {
    if (StringUtils.isBlank(esConfig.getCluster()) || StringUtils.isBlank(esConfig.getUrl())) {
      throw new Exception("cluster or url is blank");
    }
    Settings settings = Settings.builder().put("cluster.name", esConfig.getCluster()).build();
    client = new PreBuiltTransportClient(settings);
    String[] servers = esConfig.getUrl().split(",");
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

  public TransportClient getClient() {
    return client;
  }

//    public static synchronized TransportClient getClient(EsConfig esConfig) throws Exception {
//        if (StringUtils.isBlank(esConfig.getCluster()) || StringUtils.isBlank(esConfig.getUrl())) {
//            throw new Exception("cluster or url is blank");
//        }
//        TransportClient client = null;
//        String key = esConfig.getCluster() + esConfig.getUrl();
//        if (esClientMap.contains(key)) {
//            esClientMap.get(key);
//        } else {
//            try {
//                Settings settings = Settings.builder().put("cluster.name", esConfig.getCluster()).build();
//                client = new PreBuiltTransportClient(settings);
//                String[] servers = esConfig.getUrl().split(",");
//                for (String server : servers) {
//                    String[] hostPort = server.split(":");
//                    if (hostPort == null) {
//                        continue;
//                    }
//                    String host = null;
//                    String port = null;
//                    if (hostPort.length == 0) {
//                        host = hostPort[0];
//                        port = "9300";
//                    } else if (hostPort.length > 1) {
//                        host = hostPort[0];
//                        port = hostPort[1];
//                    }
//                    try {
//                        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), Integer.parseInt(port)));
//                    } catch (UnknownHostException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//                esClientMap.put(key, client);
//            } catch (Exception e) {
//               logger.error("", e);
//            }
//        }
//        return client;
//    }
}
