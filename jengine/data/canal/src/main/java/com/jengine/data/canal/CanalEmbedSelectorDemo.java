package com.jengine.data.canal;

import com.alibaba.otter.canal.protocol.Message;
import org.junit.Test;

/**
 * content
 *
 * @author nouuid
 * @date 9/30/2016
 * @since 0.1.0
 */
public class CanalEmbedSelectorDemo {

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
