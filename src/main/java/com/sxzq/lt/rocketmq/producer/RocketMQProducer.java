package com.sxzq.lt.rocketmq.producer;

import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

/**
 * Created by lt on 2018/4/24.
 */
public interface RocketMQProducer {

    SendResult sendMessage(Message msg);

    SendResult sendMessage(Message msg, long timeout);
}
