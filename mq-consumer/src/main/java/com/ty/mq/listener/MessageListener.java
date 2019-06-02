package com.ty.mq.listener;

import com.ty.mq.bean.OrderNotifyEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Slf4j
/***
 * messageModel 属性默认是广播：BROADCASTING 消费需要手动改为： CLUSTERING
 */
public class MessageListener {

    @Component
    @RocketMQMessageListener(topic = "test-topic-1", consumerGroup = "my-consumer_test-topic-1",messageModel = MessageModel.CLUSTERING)
    public static class MyConsumer1 implements RocketMQListener<String> {
        public void onMessage(String message) {
            log.info("received message: {}", message);
        }
    }

    @Component
    @RocketMQMessageListener(topic = "test-topic-2", consumerGroup = "my-consumer_test-topic-2")
    public static class MyConsumer2 implements RocketMQListener<OrderNotifyEvent> {
        public void onMessage(OrderNotifyEvent notifyEvent) {
            log.info("received message: {}", notifyEvent);
        }
    }

    @Component
    @RocketMQMessageListener(topic = "test-topic-1", selectorExpression = "paid",consumerGroup = "my-consumer_test-topic-1-tag",messageModel = MessageModel.CLUSTERING)
    public static class MyTagConsumer implements RocketMQListener<String> {
        public void onMessage(String message) {
            log.info("received message: {}", message);
        }
    }

    @Component
    @RocketMQMessageListener(topic = "test-topic-async-1", consumerGroup = "my-consumer_test-topic-async-1")
    public static class MyAsyncConsumer1 implements RocketMQListener<String> {
        public void onMessage(String message) {
            log.info("received message: {}", message);
        }
    }

    @Component
    @RocketMQMessageListener(topic = "test-topic-async-2", consumerGroup = "my-consumer_test-topic-async-2")
    public static class MyAsyncConsumer2 implements RocketMQListener<String> {
        public void onMessage(String message) {
            log.info("received message: {}", message);
        }
    }


    @Component
    @RocketMQMessageListener(topic = "test-topic-async-3", consumerGroup = "my-consumer_test-topic-async-3")
    public static class MyAsyncConsumer3 implements RocketMQListener<String> {
        public void onMessage(String message) {
            log.info("received message: {}", message);
        }
    }

    @Component
    @RocketMQMessageListener(topic = "test-topic-oneway", consumerGroup = "my-consumer_test-topic-one")
    public static class MyOneWayConsumer implements RocketMQListener<String> {
        public void onMessage(String message) {
            log.info("received message: {}", message);
        }
    }

    @Component
    @RocketMQMessageListener(topic = "test-topic-trans", consumerGroup = "my-consumer_test_trans_consumer")
    public class TransactionalConsumer implements RocketMQListener<String> {
        @Override
        public void onMessage(String message) {
            System.out.printf("------- TransactionalConsumer received: %s \n", message);
        }
    }

    @Component
    @RocketMQMessageListener(topic = "test-topic-sync", consumerGroup = "my-consumer_test_sync_consumer")
    public class SycnConsumer implements RocketMQListener<String> {
        @Override
        public void onMessage(String message) {
            System.out.printf("------- TransactionalConsumer received: %s \n", message);
        }
    }

}
