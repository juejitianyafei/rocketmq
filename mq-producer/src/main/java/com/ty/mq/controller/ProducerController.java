package com.ty.mq.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/producer")
@AllArgsConstructor
public class ProducerController {

    private final RocketMQTemplate rocketMQTemplate;

    /*************************异步消息***************/
    /**
     * 简单消息
     * @return
     */
    @GetMapping("/sendMsg")
    public String sendMessage(){
        rocketMQTemplate.convertAndSend("test-topic-1", "Hello, World!");
//        rocketMQTemplate.send("test-topic-1", MessageBuilder.withPayload("Hello, World! I'm from spring message").build());
//        rocketMQTemplate.convertAndSend("test-topic-2", new OrderNotifyEvent("T_001", new BigDecimal("88.00")));
        return "success";
    }

    /**
     * 简单消息_tag
     * @return
     */
    @GetMapping("/sendTagMsg")
    public String sendTagMessage(){
        rocketMQTemplate.convertAndSend("test-topic-1:paid", "paid:Hello, World!");
        return "success";
    }

    /**
     * 异步消息
     * @return
     */
    @GetMapping("/sendAsyncMsg")
    public String sendAsyncMessage(){
        //简单异步消息
        rocketMQTemplate.asyncSend("test-topic-async-1", "我是async消息", new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("SendCallback result is :" + sendResult.toString());
            }

            @Override
            public void onException(Throwable throwable) {
                log.error(throwable.getMessage());

            }
        });
        rocketMQTemplate.asyncSend("test-topic-async-2",
                MessageBuilder.withPayload("Hello, World! I'm from spring async message").build(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("SendCallback result is :" + sendResult.toString());
            }

            @Override
            public void onException(Throwable throwable) {
                log.error(throwable.getMessage());
            }
        });
        //异步消息设置消息
        rocketMQTemplate.asyncSend("test-topic-async-3", "异步消息延时5s", new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("SendCallback result is :" + sendResult.toString());
            }

            @Override
            public void onException(Throwable throwable) {
                log.error(throwable.getMessage());
            }
        },5000);
        return "success";
    }

    /**
     * 单向消息(只发送请求不等待应答)
     * @return
     */
    @GetMapping("/sendOneWayMsg")
    public String sendOneWayMsg(){
        rocketMQTemplate.sendOneWay("test-topic-oneway","我是oneWay消息消息!");
        return "success";
    }

    /**
     * 事务消息
     * @return
     */
    @GetMapping("/sendTransMsg")
    public String sendTransMessage(){
        Message msg = MessageBuilder.withPayload("Hello RocketMQ 我是事务消息").
                setHeader(RocketMQHeaders.KEYS, "KEY_TRANS" ).build();
        TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction("my-group-trans", "test-topic-trans", msg,"trans ags");
        log.info("TransactionSendResult is {}",sendResult);
        System.out.printf("------ send Transactional msg body = %s , sendResult=%s %n",
                msg.getPayload(), sendResult.getSendStatus());

        return "success";
    }
    /**************************同步************************/
    /**
     * 同步消息
     * @return
     */
    @GetMapping("/sendSyncMsg")
    public String sendSyncMsg(){
        Map<String,Object> message = new HashMap();
        message.put("name","zhangsan");
        message.putIfAbsent("age",3);
        message.putIfAbsent("age",5);
        message.putIfAbsent("desc","同步消息");
        SendResult sendResult1 = rocketMQTemplate.syncSend("test-topic-sync",message);
        SendResult sendResult2 = rocketMQTemplate.syncSend("test-topic-sync",message,5000);
        log.info("SendResult1 is:{}",sendResult1);
        log.info("SendResult2 is:{}",sendResult2);
        return "success";
    }

    /**
     * 同步顺序消息
     * 与@link syncsend（string，object）相同，使用指定的hashkey按顺序发送
     *使用键选择队列
     * @return
     */
    @GetMapping("/sendSyncOrderMsg")
    public String sendSyncOrderMsg(){
        SendResult sendResult1 = rocketMQTemplate.syncSendOrderly("test-topic-sync","message1","orderid");
        SendResult sendResult5 = rocketMQTemplate.syncSendOrderly("test-topic-sync","message5","productid");
        SendResult sendResult2 = rocketMQTemplate.syncSendOrderly("test-topic-sync","message2","orderid");
        SendResult sendResult3 = rocketMQTemplate.syncSendOrderly("test-topic-sync","message3","productid");
        SendResult sendResult6 = rocketMQTemplate.syncSendOrderly("test-topic-sync","message6","null");
        return "success";
    }
}
