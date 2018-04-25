package com.sxzq.lt.rocketmq.consumer;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by lt on 2018/4/24.
 */
public class RocketMQConsumer extends DefaultMQPushConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RocketMQConsumer.class);

    public RocketMQConsumer(String consumerGroup, String topic, String subExpression) {
        super(consumerGroup);

        try {
            super.subscribe(topic, subExpression);
        } catch (MQClientException e) {
            logger.error("rocketmq subcribe topic failure", e);
        }
    }

    public void setConsumerHandler(final ConsumerService consumerService){
        super.registerMessageListener((MessageListenerConcurrently)(msgs, consumeConcurrentlyContext) -> {
            MessageExt msg = msgs.get(0);

            try {
                if (consumerService.consume(msg)) {
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                } else {
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }catch (Throwable e) {
                logger.error("consume message error", e);
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });
    }

    @Override
    public void start(){
        try {
            super.start();
        } catch (MQClientException e) {
            logger.error("rocketmq start consumer error", e);
        }
    }

    @Override
    public void shutdown() {
        super.shutdown();
    }

}
