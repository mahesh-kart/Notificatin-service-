package com.example.demo.service;

import com.example.demo.model.Message;
import com.example.demo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    Producer producer;



    @Override
    public String sendSms(Message message) {


        message.setId(UUID.randomUUID().toString());
        message.setStatus("Queued");
        messageRepository.save(message);
        producer.sendMessageId(message);
        return HttpStatus.ACCEPTED+ "  "+message.toString();

    }

    @Override
    public Optional<Message> getMessageById(String id) {


        return messageRepository.findById(id);


    }
}
