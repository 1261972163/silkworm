package com.jengine.store.db.canal;

import com.alibaba.otter.canal.instance.core.CanalInstance;
import com.alibaba.otter.canal.instance.core.CanalInstanceGenerator;
import com.alibaba.otter.canal.instance.manager.CanalInstanceWithManager;
import com.alibaba.otter.canal.instance.manager.model.Canal;
import com.alibaba.otter.canal.instance.manager.model.CanalParameter;
import com.alibaba.otter.canal.protocol.ClientIdentity;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.server.embedded.CanalServerWithEmbedded;
import org.apache.commons.lang.StringUtils;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * content
 *
 * @author bl07637
 * @date 9/30/2016
 * @since 0.1.0
 */
public class CanalEmbedSelector {


    private CanalServerWithEmbedded canalServer;
    private ClientIdentity          clientIdentity;

    private int  batchSize    = 1000; // lines read from binlog per time
    private Long batchTimeout = -1L; // timeout when reading from binlog

    public CanalEmbedSelector(Long pipelineId, String destination, short clientId, String zkClustersString, Long   zkClusterId) {
        canalServer = new CanalServerWithEmbedded();
        startServer(pipelineId, destination, clientId, zkClustersString, zkClusterId);
    }

    private void startServer(Long pipelineId, String destination, short clientId, String zkClustersString, Long   zkClusterId) {
        String filter = "";
        canalServer.setCanalInstanceGenerator(new CanalInstanceGenerator() {
            @Override
            public CanalInstance generate(String destination) {
                String[] zkClusters = StringUtils.split(zkClustersString, ";");
                CanalParameter parameter = new CanalParameter();
                parameter.setZkClusterId(zkClusterId);
                parameter.setZkClusters(Arrays.asList(zkClusters));
                parameter.setSlaveId(10000 + pipelineId);
                List<InetSocketAddress> dbAddresses = new ArrayList<InetSocketAddress>();
                dbAddresses.add(new InetSocketAddress("10.45.11.85", 3306));
                parameter.setDbAddresses(dbAddresses);
                parameter.setDefaultDatabaseName("test2");
                parameter.setDbUsername("canal");
                parameter.setDbPassword("canal4M!");
                Canal canal = new Canal();
                canal.setName(destination);
                canal.setId(pipelineId);
                canal.setCanalParameter(parameter);
                // 设置下slaveId，保证多个piplineId下重复引用时不重复
                long slaveId = 10000;// 默认基数
                if (canal.getCanalParameter().getSlaveId() != null) {
                    slaveId = canal.getCanalParameter().getSlaveId();
                }
                canal.getCanalParameter().setSlaveId(slaveId + pipelineId);
                canal.getCanalParameter().setDdlIsolation(true);
                canal.getCanalParameter().setFilterTableError(false);

                CanalInstanceWithManager instance = new CanalInstanceWithManager(canal, filter) {

                };

                return instance;
            }
        });
        canalServer.start();
        canalServer.start(destination);
        this.clientIdentity = new ClientIdentity(destination, clientId, filter);
        canalServer.subscribe(clientIdentity);// 发起一次订阅
    }

    public Message selector() {
        Message message = null;
        while (true) {
            message = canalServer.getWithoutAck(clientIdentity, batchSize, batchTimeout, TimeUnit.MILLISECONDS);
            if (message == null || message.getId() == -1L) { // 代表没数据
                continue;
            } else {
                break;
            }
        }
        return message;
    }
}
