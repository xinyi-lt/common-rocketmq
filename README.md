#common-rocketmq介绍

该项目对rocketmq在实际项目开发中做了简单的封装，使用简单，common-rocketmq是基于JDK8的，下面是common-rocketmq结构：

包含5个类和一个XML配置文件

```
rocketmq-----common-------MessageData（消息传输DTO）
		|
		|---producer-------RocketMQProducer（生产者接口）
		|             |
		|             |---RocketMQProducerImpl（生产者接口实现）
		|---consumer------RocketMQConsumer（消费者）
					  |
					  |---ConsumerService（消费者业务逻辑需要实现的接口）

```



common-rocketmq.xml


#使用举例

demo的github地址：https://github.com/xinyi-lt/common-rocketmq-demo

**1.maven依赖**

```java
<dependency>
	<groupId>com.sxzq.lt</groupId>
	<artifactId>common-rocketmq</artifactId>
	<version>1.0-SNAPSHOT</version>
</dependency>
```


**2.producer生产者**

- 配置文件引入



```java
    <import resource="classpath*:conf/common-rocketmq.xml" />
```


- 代码中注入producer



```java
        RocketMQProducer producer = applicationContext.getBean(RocketMQProducer.class);
```
- 
  发送消息



```java
        //消息传输DTO
        MessageData<UserInfo> messageData = new MessageData<>();

        //时间戳
        messageData.setTimestamp(System.currentTimeMillis());
        //幂等控制根据UUID来控制
        messageData.setUuid(UUID.randomUUID().toString());

        UserInfo userInfo = new UserInfo();
        //useinfo数据

        messageData.setData(userInfo);

        logger.info("start to send message data:{}", JSON.toJSONString(messageData));

        //发送消息
        SendResult result = producer.sendMessage(
                new Message(MQConstant.MQ_TOPIC_PRODUCER_DEMO,
                MQConstant.MQ_TAG_PRODUCER_DEMO_FIRST,
                UUID.randomUUID().toString(),
                JSON.toJSONString(messageData).getBytes(Charset.forName("utf-8"))));

        logger.info("send message complete result:{} ", result);
```


**3.consumer消费者**

- 配置文件引入 <import resource="classpath*:conf/common-rocketmq.xml"/>



- 实现common-rocketmq中的ConsumerService的接口



```java
        public class DemoConsumerService implements ConsumerService {
           @Override
            public boolean consume(Message message) {
   
                  //消费者业务逻辑
                  return consumeResult;
            }
        }
```

- 
  DemoConsumerService添加到spring容器进行管理



```java
       <bean id="demoConsumerService" class="com.sxzq.lt.demo.consumer.DemoConsumerService"/>
```
- 
  创建该业务实例的消费者



```java
         <bean id="demoConsumer" class="com.sxzq.lt.rocketmq.consumer.RocketMQConsumer"
              init-method="start" destroy-method="shutdown">  
                 <!--consumerGroup -->
                 <constructor-arg value="MQ_CONSUMER_GROUP_DEMO_1" index="0"></constructor-arg>
                 <!-- topic -->
                 <constructor-arg value="MQ_TOPIC_PRODUCER_DEMO" index="1"></constructor-arg>
                 <!-- topic's subExpression -->
                 <constructor-arg value="MQ_TAG_PRODUCER_DEMO_1" index="2"></constructor-arg>
                 <property name="namesrvAddr" value="127.0.0.1:9876"></property>
                 <property name="consumeFromWhere" ref="CONSUME_FROM_FIRST_OFFSET"/>
                 <!--消费者业务逻辑实现-->
                 <property name="consumerHandler" ref="demoConsumerService"/>
                 <!--集群模式，默认为集群消费，不需要配置-->
                 <property name="messageModel" ref="CLUSTERING"/>
        </bean>
```
