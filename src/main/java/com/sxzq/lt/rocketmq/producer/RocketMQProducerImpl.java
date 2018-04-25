package com.sxzq.lt.rocketmq.producer;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendCallback;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lt on 2018/4/24.
 */
public class RocketMQProducerImpl extends DefaultMQProducer implements RocketMQProducer {

    private static final Logger logger = LoggerFactory.getLogger(RocketMQProducerImpl.class);

    public RocketMQProducerImpl() {
    }

    public RocketMQProducerImpl(String producerGroup) {
        super(producerGroup);
    }

    @Override
    public void shutdown() {
        super.shutdown();
    }

    @Override
    public void start() {
        try {
            super.start();
        } catch (MQClientException e) {
            logger.error("rocketmq start producer error", e);
        }
    }

    @Override
    public SendResult sendMessage(Message msg) {
        try {
            return send(msg);
        } catch (Exception e) {
            logger.error("rocketmq send message error", e);
            return null;
        }
    }


    @Override
    public SendResult sendMessage(Message msg, long timeout) {
        try {
            return send(msg, timeout);
        } catch (Exception e) {
            logger.error("rocketmq send message error", e);
            return null;
        }
    }

    @Override
    public void send(Message msg, SendCallback sendCallback) throws MQClientException, RemotingException, InterruptedException {
        super.send(msg, sendCallback);
    }
}
