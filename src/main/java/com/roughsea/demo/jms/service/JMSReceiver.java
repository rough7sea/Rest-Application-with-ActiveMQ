package com.roughsea.demo.jms.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JMSReceiver {

    @JmsListener(destination = "${spring.activemq.queue.name}", containerFactory = "jmsQueueListenerContainerFactory")
    public void receiveMessageFromQueue(String message){
        log.info("Receive from queue : <" + message + ">");
    }

    @JmsListener(destination = "${destination.topic}", containerFactory = "jmsTopicListenerContainerFactory")
    public void receiveMessageFromTopic(String message){
        log.info("Receive from topic : <" + message + ">");
    }

}