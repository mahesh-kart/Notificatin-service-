package com.example.demo.service.elastic;



import com.example.demo.model.DateInput;
import com.example.demo.model.EsModel;
import com.example.demo.repository.EsRepository;


import com.example.demo.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.DataInput;
import java.util.List;

@Service

public class EsServiceImpl implements EsService {

//    @Autowired
//    EsRepository esRepository;
//
//    @Autowired
//    ElasticsearchRestTemplate elasticsearchRestTemplate;
//
//
//    @Override
//    public EsModel saveSms(EsModel esModel) {
//        System.out.println("2");
//        esRepository.save(esModel);
//        return esModel;
//    }
//
//    @Override
//    public Page<EsModel> findMessageByText(String text) {
//
//        return esRepository.findByMessage(text, PageRequest.of(0,2));
//
//    }
//
//    @Override
//    public Page<EsModel> findMessageByDate(DateInput dateInput) {
//        System.out.println(dateInput.getStartDate());
//        System.out.println(dateInput.getEndDate());
//
//
//
//        return  null;
//
//    }

@Autowired
EsRepository esRepository;


    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    Helper helper;

    @Override
    public void save(EsModel eSmodel) {
        esRepository.save(eSmodel);
    }

    @Override
    public Page<EsModel> findByMessage(String messageText) {

        return esRepository.findByMessage(messageText,PageRequest.of(0,2));
    }

    @Override
    public Page<EsModel> findByDate(DateInput dateInput) {
//
        long startEpoch= helper.DateConverter(dateInput.getStartDate());
        long endEpoch=helper.DateConverter(dateInput.getEndDate());

        return esRepository.findAllByCreatedAtBetween(startEpoch,endEpoch,PageRequest.of(0,2));



    }

}
