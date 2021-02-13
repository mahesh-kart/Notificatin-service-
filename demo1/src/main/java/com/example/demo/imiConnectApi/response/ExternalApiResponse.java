package com.example.demo.imiConnectApi.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

import java.security.PrivateKey;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExternalApiResponse {

    @JsonProperty("code")
    private String code;
    @JsonProperty("transid")
    private String transId;
    @JsonProperty("description")
    private String description;
    @JsonProperty("correlationid")
    private String correlationid;


}
