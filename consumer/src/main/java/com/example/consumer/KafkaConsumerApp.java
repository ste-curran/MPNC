package com.example.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class KafkaConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumerApp.class, args);
    }
}

