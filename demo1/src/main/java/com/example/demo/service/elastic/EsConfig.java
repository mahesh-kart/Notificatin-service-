package com.example.demo.service.elastic;





import javafx.scene.NodeBuilder;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;

//@Configuration

//@ComponentScan(basePackages = { "com.example.elastic.*" })
//@EnableJpaRepositories(basePackages = "com.example.elastic.repository")
//@EntityScan(basePackages = "com.example.elastic")
//public class EsConfig extends Ela{
//
//
//
//        @Bean
//        public RestHighLevelClient client() {
//            ClientConfiguration clientConfiguration
//                    = ClientConfiguration.builder()
//                    .connectedTo("localhost:9200")
//                    .build();
//
//            return RestClients.create(clientConfiguration).rest();
//        }
//
//        @Bean
//        public ElasticsearchOperations elasticsearchTemplate() {
//            return new ElasticsearchRestTemplate(client());
//        }
//    }
//
//
//


import java.io.IOException;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
@EnableElasticsearchRepositories(basePackages = "com.example.demo.repository")
@Configuration
public class EsConfig {

    /**
     *  index builder bean to add
     **/

    @Bean
    public RestHighLevelClient elasticsearchClient() {


        RestHighLevelClient client = new RestHighLevelClient(RestClient
                .builder(new HttpHost("localhost",9200,"http")));

        return client;
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }


}