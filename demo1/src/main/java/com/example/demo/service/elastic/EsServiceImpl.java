package com.example.demo.service.elastic;



import com.example.demo.model.DateInput;
import com.example.demo.model.EsModel;
import com.example.demo.repository.EsRepository;


import com.example.demo.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.DataInput;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service

public class EsServiceImpl implements EsService {



@Autowired
EsRepository esRepository;


    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    Helper helper;

    @Autowired
    ElasticsearchRepository elasticsearchRepository;

    @Override
    public void save(EsModel eSmodel) {
        esRepository.save(eSmodel);


    }

    @Override
    public Page<EsModel> findByMessage(String messageText, Optional<Integer> page) {

        return esRepository.findByMessage(messageText,  PageRequest.of(page.orElse(0),2));
    }

    @Override
    public Page<EsModel> findByDate(DateInput dateInput,Optional<Integer> page) {
//
        long startEpoch= helper.DateConverter(dateInput.getStartDate());
        long endEpoch=helper.DateConverter(dateInput.getEndDate());

        return esRepository.findAllByCreatedAtBetween(startEpoch,endEpoch,PageRequest.of(page.orElse(0),2));



    }
//    @Override
//    public Page<EsModel>getAll()
//    {
//        return (Page<EsModel>) esRepository.findAll();
//    }

}
