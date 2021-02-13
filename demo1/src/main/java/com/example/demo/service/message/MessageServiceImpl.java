package com.example.demo.service.message;

import com.example.demo.MessageStatus;

import com.example.demo.controller.KafkaController;
import com.example.demo.model.Message;
import com.example.demo.model.UserSmsInput;
import com.example.demo.repository.MessageRepository;
import com.example.demo.service.kafka.Producer;
import com.example.demo.service.message.MessageService;
import com.example.demo.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    Producer producer;

    @Autowired
    RedisService redisService;
    @Autowired
    KafkaController kafkaController;


    @Override
     public Message sendSms(UserSmsInput userSmsInput) {
//        if(redisService.checkIfExist(userSmsInput.getPhoneNumber()))
//            return "Blacklisted";
        Message message=new Message();
        message.setMessage(userSmsInput.getMessage());
        message.setPhoneNumber((userSmsInput.getPhoneNumber()));
        message.setId(UUID.randomUUID().toString());
        message.setStatus(MessageStatus.QUEUED);
        messageRepository.save(message);
        System.out.println("Before producer");
       // producer.sendMessageId(message);
kafkaController.sendToKafka(message);
        System.out.println("After producer");

        return message;

    }

    @Override
    public Optional<Message> getMessageById(String id) {


        return messageRepository.findById(id);


    }
}