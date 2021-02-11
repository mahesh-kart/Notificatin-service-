package com.example.demo.service.elastic;



import com.example.demo.model.DateInput;
import com.example.demo.model.EsModel;
import org.springframework.data.domain.Page;

import java.io.DataInput;
import java.util.Date;
import java.util.List;

public interface EsService {

//
//    public EsModel saveSms(EsModel esMode);
//    public Page<EsModel> findMessageByText(String text);
//    public Page<EsModel>findMessageByDate(DateInput dateInput);

    public void save(EsModel esModel);
    public Page<EsModel> findByMessage(String messageText);
    public Page<EsModel> findByDate(DateInput dateInput);


}
