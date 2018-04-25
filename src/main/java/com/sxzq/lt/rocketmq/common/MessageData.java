package com.sxzq.lt.rocketmq.common;

import java.io.Serializable;


public class MessageData<T> implements Serializable {


    private static final long serialVersionUID = 3620052330592367897L;
    /**
     * 调用跟踪唯一标识号
     */
    private String requestId;

    /**
     * 值为uuid,
     */
    private String uuid;

    /**
     * 消息产生时间戳
     */
    private long timestamp;

    /**
     * 消息内容，数据格式为json字符串
     */
    private T data;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
