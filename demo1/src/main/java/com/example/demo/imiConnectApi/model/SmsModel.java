package com.example.demo.imiConnectApi.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.internal.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmsModel {


    @JsonProperty("deliverychannel")
    private String deliveryChannel;
@JsonProperty("channels")
    private Channels channels;
@JsonProperty("destination")
    List<Destination> destination;


}
