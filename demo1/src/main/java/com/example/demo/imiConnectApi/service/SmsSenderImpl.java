package com.example.demo.imiConnectApi.service;

import com.example.demo.imiConnectApi.model.SmsModel;
import com.example.demo.imiConnectApi.response.ExternalApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.*;


@Service
public class SmsSenderImpl implements SmsSender {


    @Override
    public String sendSms(SmsModel smsModel) {

        String url = "https://api.imiconnect.in/resources/v1/messaging";
        String key = "93ceffda-5941-11ea-9da9-025282c394f2";
        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(url)
                .defaultHeader("key", key)
                .build();
//        String response = restTemplate.postForObject(url,
//                smsModel,
//                String.class);

        try {
            String response = restTemplate.postForObject(url, smsModel, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);
            JsonNode responseNode = rootNode.path("response");
            JsonNode transid = rootNode.at("/response/transid");

            if (transid.toString().length() > 0) {
                return mapper.treeToValue(responseNode, ExternalApiResponse.class).toString();
            } else {
                return responseNode.get(0).toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();


        }


        return "null";
    }
}

