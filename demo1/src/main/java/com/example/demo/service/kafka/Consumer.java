package com.example.demo.service.kafka;

import com.example.demo.MessageStatus;

import com.example.demo.exception.DatabaseCrashException;
import com.example.demo.exception.InvalidRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Blacklist;
import com.example.demo.model.Message;
import com.example.demo.repository.MessageRepository;
import com.example.demo.service.message.MessageService;
import com.example.demo.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.util.Optional;

@Service
public class Consumer {
    @Autowired
    MessageService messageService;
    @Autowired
    RedisService redisService;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    ElasticsearchRepository elasticsearchRepository;


    @KafkaListener (topics="${kafka.topic}")
    public void consumeMessageId(String messageId) throws NotFoundException{

        try
        {
            Optional<Message> message=Optional.ofNullable(messageRepository.findById(messageId).orElse(null));
            Message messageObject=message.get();
            if(messageObject==null)
            {
                throw new NotFoundException("Cannot find the message in db");
            }

            if(redisService.checkIfExist(messageObject.getPhoneNumber()))
            {
                messageObject.setStatus(MessageStatus.FAILURE);
                messageObject.setFailureComments("BlackListed NUmber");
                messageObject.setFailureCode("400");
                System.out.println("Blacklisted");
                messageRepository.save(messageObject);
            }
            else
            {
                // perfom 3 part api
                // set staus of message accoprding to response of api
                // do elastic save here
                elasticsearchRepository.save(messageObject);
                System.out.println("Not Blacklisted");
            }

        }
        catch (InvalidRequestException ex)
        {
            throw new DatabaseCrashException("kafka error");
        }



    }
}
