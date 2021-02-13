package com.example.demo.imiConnectApi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Destination {

@JsonProperty("msisdn")
    List<String> msisdn;
@JsonProperty("correlationid")
    private String correlationId;

}
