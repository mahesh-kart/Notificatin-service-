package com.example.demo.controller;




import com.example.demo.model.DateInput;
import com.example.demo.model.MessageInput;
import com.example.demo.service.elastic.EsService;
import com.example.demo.model.EsModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/elastic")
public class EsController {

@Autowired
EsService esService;

    @PostMapping
    public EsModel sendSms(@RequestBody EsModel esModel){
        esService.save(esModel);
        return esModel;
    }
    @GetMapping("/date")
    public Page<EsModel> getAllBetweenDate(@RequestBody DateInput dateInput,@RequestParam Optional<Integer> page){

        return esService.findByDate(dateInput,page);



    }
    @GetMapping("/message")
    public Page<EsModel> getAllByText(@RequestBody MessageInput messageInput, @RequestParam Optional<Integer> page){

        return esService.findByMessage(messageInput.getMessageInput(),page);
    }
//
//    @GetMapping("/all")
//    public Page<EsModel> getAll()
//    {
//        return esService.getAll();
//    }
}

