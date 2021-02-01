package com.example.demo.service;

import com.example.demo.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    @Autowired
    MessageService messageService;

    @KafkaListener(topics = "${kafka.topic}")
    public void consumeMessageId(String messageId)
    {
        System.out.println("Message id received from producer is " +messageId);
        System.out.println(messageService.getMessageById(messageId));

    }
}
