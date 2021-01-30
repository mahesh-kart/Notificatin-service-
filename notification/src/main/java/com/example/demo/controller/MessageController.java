package com.example.demo.controller;

import com.example.demo.model.Message;
import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/sms")
public class MessageController {

@Autowired
    MessageService messageService;

@PostMapping("/send")
    public String send_sms(@RequestBody Message message)
{
    return messageService.send_sms(message);
}

@GetMapping("/{request_id}")
    public Optional<Message> getMessageById(@PathVariable String request_id)
{
    return messageService.getMessageById(request_id);
}

}
