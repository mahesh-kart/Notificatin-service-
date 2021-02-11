package com.example.demo.controller;




import com.example.demo.model.DateInput;
import com.example.demo.service.elastic.EsService;
import com.example.demo.model.EsModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/elastic")
public class EsController {
//
//    @Autowired
//    EsService esService;

//
//    @GetMapping("message/{messageText}")
//    public Page<EsModel> findMessageByText(@PathVariable String messageText)
//    {
//        return esService.findMessageByText(messageText);
//    }
//
//    @GetMapping("date")
//    public Page<EsModel> findMessageByDate(@RequestBody DateInput dateInput)
//    {
//        return esService.findMessageByDate(dateInput);
//    }
//
//    @PostMapping("save")
//    public EsModel saveMessage(@RequestBody EsModel esModel)
//    {
//
//        System.out.println("1");
//        return esService.saveSms(esModel);
//    }


@Autowired
EsService esService;

    @PostMapping
    public EsModel sendSms(@RequestBody EsModel esModel){
        esService.save(esModel);
        return esModel;
    }
    @GetMapping("/date")
    public Page<EsModel> getAllBetweenDate(@RequestBody DateInput dateInput){

        return esService.findByDate(dateInput);



    }
    @GetMapping("/message/{messageText}")
    public Page<EsModel> getAllByText(@PathVariable String messageText){
        return esService.findByMessage(messageText);
    }
}

