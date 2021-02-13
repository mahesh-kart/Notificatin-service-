package com.example.demo.controller;

import com.example.demo.model.Message;
import com.example.demo.service.kafka.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class KafkaController {
    private final Producer producer;

    @Autowired
    public KafkaController(Producer producer)
    {
        this.producer=producer;
    }

    public void sendToKafka(Message message)
    {
        this.producer.sendMessageId(message);
    }
}
