package com.example.demo.imiConnectApi.service;

import com.example.demo.imiConnectApi.model.SmsModel;
import com.example.demo.imiConnectApi.response.ExternalApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.awt.*;


@Service
public class SmsSenderImpl implements SmsSender {

    @Value("${imiconnect.url}")
    private String url;
    @Value("${imiconnect.api.key}")
    private String key;


    @Override
    public String sendSms(SmsModel smsModel) {


        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(url)
                .defaultHeader("key", key)
                .build();
//        String response = restTemplate.postForObject(url,
//                smsModel,
//                String.class);

        try {
            String response = restTemplate.postForObject(url, smsModel, String.class);
            System.out.println(response);
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

