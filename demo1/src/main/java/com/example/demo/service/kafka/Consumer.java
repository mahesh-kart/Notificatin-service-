package com.example.demo.service.kafka;

import com.example.demo.MessageStatus;

import com.example.demo.exception.DatabaseCrashException;
import com.example.demo.exception.InvalidRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.imiConnectApi.model.Channels;
import com.example.demo.imiConnectApi.model.Destination;
import com.example.demo.imiConnectApi.model.Sms;
import com.example.demo.imiConnectApi.model.SmsModel;
import com.example.demo.imiConnectApi.response.ExternalApiResponse;
import com.example.demo.imiConnectApi.service.SmsSender;
import com.example.demo.imiConnectApi.statusCodesEnum.ExternalApiStatus;
import com.example.demo.model.EsModel;
import com.example.demo.model.Message;
import com.example.demo.repository.EsRepository;
import com.example.demo.repository.MessageRepository;
import com.example.demo.service.elastic.EsService;
import com.example.demo.service.message.MessageService;
import com.example.demo.service.redis.RedisService;
import com.google.gson.Gson;
import org.hibernate.cfg.CreateKeySecondPass;
import org.springframework.beans.ExtendedBeanInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@EnableConfigurationProperties
public class Consumer {
    @Autowired
    MessageService messageService;
    @Autowired
    RedisService redisService;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    EsRepository esRepository;

    @Autowired
    EsService esService;

    @Autowired
    SmsSender smsSender;



    @KafkaListener (topics="${kafka.topic}",groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessageId(String messageId) throws NotFoundException{

        try
        {
            if(!messageRepository.findById(messageId).isPresent())
                throw new NotFoundException("Cannot find the message in DB");
            Optional<Message> message = messageRepository.findById(messageId);
            Message messageObject=message.get();
            if(redisService.checkIfExist(messageObject.getPhoneNumber()))
            {
                messageObject.setStatus(MessageStatus.FAILURE);
                messageObject.setFailureComments("BlackListed NUmber");
                messageObject.setFailureCode("200");
                System.out.println("Blacklisted");
                //messageRepository.deleteById(messageObject.getId());
                messageRepository.save(messageObject);
            }
            else
            {
                // perfom 3 part api
                // set staus of message accoprding to response of api
                // do elastic save here\

                List<String> phoneNumber=new ArrayList<>();
                phoneNumber.add(messageObject.getPhoneNumber());

                Sms sms =Sms.builder().text(messageObject.getMessage()).build();

                Channels channels=Channels.builder().sms(sms).build();

                Destination destination=Destination.builder()
                        .msisdn(phoneNumber)
                        .correlationId(messageObject.getId())
                        .build();

                List<Destination> destinationList=new ArrayList<>();
                destinationList.add(destination);

                SmsModel smsModel=SmsModel.builder()
                        .channels(channels)
                        .deliveryChannel("sms")
                        .destination(destinationList)
                        .build();

                esRepository.save(new EsModel(messageObject));
            String response= smsSender.sendSms(smsModel);

                System.out.println(response);
                Gson gson = new Gson();
                ExternalApiResponse externalApiResponse = gson.fromJson(response,ExternalApiResponse.class);
                System.out.println(externalApiResponse.getCode());
                System.out.println(externalApiResponse.getTransId());
                String re=externalApiResponse.getCode();
                System.out.println(externalApiResponse.getCode().getClass().getName());
                System.out.println(externalApiResponse.getCode().equals("1001"));
      if(externalApiResponse.getCode().equals("1001"))
      {
          System.out.println("Not Blacklisted");
          messageObject.setStatus(MessageStatus.SUCCESS);
          messageRepository.save(messageObject);
      }
     // messageRepository.deleteById(messageObject.getId());

             // System.out.println("Not Blacklisted");
            }

        }
        catch (InvalidRequestException ex)
        {
            throw new DatabaseCrashException("kafka error");
        }

    }
}
