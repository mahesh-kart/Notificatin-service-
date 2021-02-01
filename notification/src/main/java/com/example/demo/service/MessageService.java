package com.example.demo.service;

import com.example.demo.model.Message;

import java.util.Optional;

public interface MessageService {

    public String sendSms(Message message);
    public Optional<Message> getMessageById(String id);

}
