package com.sxzq.lt.rocketmq.consumer;

import com.alibaba.rocketmq.common.message.Message;

/**
 * Created by lt on 2018/4/23.
 */
public interface ConsumerService {
    /**
     * 消费消息
     * @param message
     * @return
     */
    boolean consume(Message message);
}
