package com.roughsea.demo.jms.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jms")
@Slf4j
public class JmsController {

    @Autowired
    @Qualifier("topicJmsTemplate")
    private JmsTemplate topicJmsTemplate;

    @Autowired
    @Qualifier("queueJmsTemplate")
    private JmsTemplate queueJmsTemplate;

    @Value("${spring.activemq.queue.name}")
    private String queue;

    @Value("${destination.topic}")
    private String topic;

    @GetMapping("/topic/{message}")
    public ResponseEntity<Void> sendToTopic(@PathVariable String message){
        topicJmsTemplate.convertAndSend(topic, message);
        log.info("send <" + message + "> to topic");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/queue/{message}")
    public ResponseEntity<Void> sendToQueue(@PathVariable String message){
        queueJmsTemplate.convertAndSend(queue, message);
        log.info("send <" + message + "> to queue");
        return ResponseEntity.ok().build();
    }
}