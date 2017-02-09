package com.jengine.store;

import com.alibaba.otter.canal.protocol.Message;
import com.jengine.store.db.canal.CanalEmbedSelector;
import org.junit.Test;
/**
 * content
 *
 * @author bl07637
 * @date 9/30/2016
 * @since 0.1.0
 */
public class CanalEmbedSelectorTest {

    @Test
    public void test() {
        Long pipelineId = 1L;
        String destination = "test";
        short clientId = 1;
        String zkClustersString = "10.45.11.84:2181/test";
        Long   zkClusterId = 1L;
        CanalEmbedSelector canalEmbedSelector = new CanalEmbedSelector(pipelineId, destination, clientId, zkClustersString, zkClusterId);

        Message message = canalEmbedSelector.selector();
        System.out.println("---------" + message.getId());
    }
}
