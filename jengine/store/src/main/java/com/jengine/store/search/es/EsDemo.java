package com.jengine.store.search.es;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.*;

/**
 * @author nouuid
 * @date 3/25/2016
 * @description
 */
public class EsDemo {

    protected final Log logger = LogFactory.getLog(getClass());

    private EsService esService;

    @org.junit.Before
    public void before() {
        esService = new EsService();
    }

    @org.junit.After
    public void after() {
        esService.close();
    }

    @org.junit.Test
    public void index() {
        List<LogModel> logModels = new LinkedList<LogModel>();
        for (int j=0; j<1; j++) {
            for (int i = 0; i < 10; i++) {
                LogModel vo = buildMonitorLog();
//                logger.info("[" + i + "] -----> " + vo.toString());
                logModels.add(vo);
            }
            esService.indexing(logModels);
            logger.info("[" + j + "]");
        }
        logger.info("end.");
    }

    public static LogModel buildMonitorLog() {
        Random random = new Random();
        Random typeRandom = new Random();

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
//        Date date = new Date();

        LogModel logModel = new LogModel();
        logModel.setId("id-" + randomStr() + random.nextInt());
        logModel.setInterfaceName("interfaceName-" + randomStr() + random.nextInt());
        logModel.setMethodName("methodName-" + randomStr() + random.nextInt());
        logModel.setAppId("appid-" + randomStr() + random.nextInt());
        logModel.setRemoteHost("remoteHost-" + randomStr() + random.nextInt());
        logModel.setDate(date);
        logModel.setLocalHost("localHost-" + randomStr() + random.nextInt());
        logModel.setLocalAppId(randomStr());
        logModel.setFlag(true);
        logModel.setType(typeRandom.nextInt(4));
        logModel.setLog("log-" + randomStr() + random.nextInt());
        logModel.setUuid(UUID.randomUUID().toString());
        logModel.setGlobalID("globalID-" + randomStr() + random.nextInt());
        logModel.setKeyword("keyword-" + randomStr() + random.nextInt());

        return logModel;
    }

    public static char[] ALPHABET = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z'};

    private static String randomStr() {
        Random random = new Random();
        return "" + ALPHABET[random.nextInt(26)] +
                ALPHABET[random.nextInt(26)] +
                ALPHABET[random.nextInt(26)] +
                ALPHABET[random.nextInt(26)];
    }

}

class EsService {
    protected final Log logger = LogFactory.getLog(getClass());

    private JestClient        client;
    private JestClientFactory factory;

    private String index = "test1";


    public EsService() {
        factory = new JestClientFactory();
        String[] servers = {"http://10.9.18.237:9201"};
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder(Arrays.asList(servers))
                .defaultCredentials("", "")
                .multiThreaded(true)
                .maxTotalConnection(3000)
                .connTimeout(20000)
                .readTimeout(30000)
                .build());
        client = factory.getObject();
    }

    public void indexing(List<LogModel> logModels) {
        Bulk.Builder bulkBuilder = new Bulk.Builder();
        try {
            for (LogModel searchEngineMonitorLog : logModels) {
                bulkBuilder.addAction(new Index.Builder(searchEngineMonitorLog).index(index).type("xingnglog").build());
            }
            client.execute(bulkBuilder.build());
        } catch (IOException e) {
            logger.error("error in executing Bulk.", e);
        }
    }

    public void close() {
        client.shutdownClient();
    }
}
