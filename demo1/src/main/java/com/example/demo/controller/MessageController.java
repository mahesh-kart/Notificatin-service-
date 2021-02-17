package com.example.demo.controller;

import com.example.demo.MessageStatus;
import com.example.demo.model.Message;
import com.example.demo.model.UserSmsInput;
import com.example.demo.response.DataResponse;
import com.example.demo.response.ErrorResponse;
import com.example.demo.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/sms")
public class MessageController {

    @Autowired
    MessageService messageService;


    @PostMapping("/send")
    public ResponseEntity<Object> sendSms(@RequestBody UserSmsInput userSmsInput)
    {
        if(userSmsInput.getMessage().isEmpty() || userSmsInput.getMessage().trim().isEmpty()) {

            return new ResponseEntity<>(new ErrorResponse("INVALID_REQUEST","message is mandatory"), HttpStatus.BAD_REQUEST);
        }
        if(userSmsInput.getPhoneNumber().isEmpty() || userSmsInput.getPhoneNumber().trim().isEmpty())
            return new ResponseEntity<>(new ErrorResponse("INVALID_REQUEST","phone_number is mandatory"), HttpStatus.BAD_REQUEST);


        try {
            Message message = messageService.sendSms(userSmsInput);

            DataResponse successResponse = new DataResponse();
            successResponse.setRequestId(message.getId());
//           message.setStatus(MessageStatus.SUCCESS);
            message=messageService.getMessageById(message.getId()).get();
            MessageStatus messageStatus = message.getStatus();
            System.out.println(messageStatus);
            System.out.println(MessageStatus.SUCCESS);


                DataResponse dataResponse = new DataResponse();
            dataResponse.setComments("Successfully Sent");
            dataResponse.setRequestId(message.getId());

                return new ResponseEntity<>(dataResponse, HttpStatus.OK);

        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(new ErrorResponse(ex.getLocalizedMessage(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }

    @GetMapping("/{request_id}")
    public ResponseEntity<Object>  getMessageById(@PathVariable String request_id){
       try{

            if (!messageService.getMessageById(request_id).isPresent()) {
                return new ResponseEntity<>(new ErrorResponse("INVALID_REQUEST", "request_id not found"), HttpStatus.NOT_FOUND);
            }
            else
            {
                DataResponse data = new DataResponse(messageService.getMessageById(request_id).get());
                return new ResponseEntity<>(data,HttpStatus.OK);
            }


    }
        catch(Exception ex){
            return new ResponseEntity<>(new ErrorResponse(ex.getLocalizedMessage(), ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
//    return messageService.getMessageById(request_id);
    }

}

//  kafka, zookeeper server start
//  bin/zookeeper-server-start.sh config/zookeeper.properties;
//  bin/kafka-server-start.sh config/server.properties
//  ./bin/elasticsearch
//  src/redis-server
// sudo lsof -i :9091
//kill -9 29335












