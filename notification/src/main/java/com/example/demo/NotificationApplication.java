package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationApplication.class, args);
	}

}




//
//http://localhost:9091/v1/sms/send
//		{
//
//		"phoneNumber":"7779895245952",
//		"message": "jhvkjv  gyftfy yfttx lnjjnbh iohgyuftf9"
//		}

//
//bin/zookeeper-server-start.sh config/zookeeper.properties
//		bin/kafka-server-start.sh config/server.properties