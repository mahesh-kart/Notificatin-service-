package com.example.demo.service.kafka;

import com.example.demo.model.Message;
import jdk.nashorn.internal.runtime.ErrorManager;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Service
public class Producer {
    @Autowired
    private KafkaTemplate<Message,String> kafkaTemplate;
    @Value("${kafka.topic}")
    String Topic;
    public void sendMessageId(Message message){

        kafkaTemplate.send(Topic, message.getId());
    }
}

