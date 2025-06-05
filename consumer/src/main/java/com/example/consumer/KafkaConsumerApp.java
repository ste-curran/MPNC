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

@Service
class KafkaService {
    @Autowired
    private MessageRepository messageRepository;

    @KafkaListener(topics = "test", groupId = "my-group")
    public void listen(String message) {
        System.out.println("Received: " + message);
        // Save the message to MySQL
        MessageEntity entity = new MessageEntity(message);
        messageRepository.save(entity);
        System.out.println("Saved to MySQL: " + message);
    }
}
